package com.techletsolutions.sip2client.core;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.ItemInformation;
import com.ceridwen.circulation.SIP.messages.ItemInformationResponse;
import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.ceridwen.circulation.SIP.types.flagfields.SupportedMessages;
import com.techletsolutions.sip2client.conf.Msg;
import com.techletsolutions.sip2client.error.Errors;
import com.techletsolutions.sip2client.error.TechletItemInformation;
import com.techletsolutions.sip2client.exceptions.SIP2ClientException;

/**
 * This class contains the methods to carry out Item information process in SelfCheck
 * client.
 * @author Yashpal Meena
 * @email yashpal@techletsolutions.com 
 *
 */
public class ItemInfoProcess extends SIP2Process {

	final static Logger logger = LoggerFactory.getLogger(ItemInfoProcess.class);

	private String itemId;

	private TechletItemInformation itemInfo;

	public ItemInfoProcess(SocketConnection connection, Message message, String itemId) {
		super(connection, message);
		this.itemId = itemId;
	}

	@Override
	public int execute() {
		// Check if the server can support checkin
		if (!((ACSStatus) response).getSupportedMessages().isSet(SupportedMessages.ITEM_INFORMATION)) {
			logger.error(Msg.get("iteminfo.notsupported.error"), response.toString());
			return Errors.ERROR_ITEM_INFORMATION_SUPPORTED;
		}

		// The code below would be the normal way of creating the request
		ItemInformation itemInformationRequest = new ItemInformation();
		itemInformationRequest.setItemIdentifier(itemId);
		itemInformationRequest.setTransactionDate(new Date());

		Message serverResponse = null;
		try {
			serverResponse = connection.send(itemInformationRequest);
		} catch (Exception e) {
			logger.error(Msg.get("iteminfo.connection.error"), e);
			return new SIP2ClientException(e).getError();
		}

		if (!(serverResponse instanceof ItemInformationResponse)) {
			logger.error(Msg.get("iteminfo.reponse.invalid.error"), serverResponse.toString());
			return Errors.ERROR_INVALID_ITEM_INFORMATION_REQUEST;
		}

		try {
			//serverResponse.xmlEncode(System.out);
			logger.info("Item information received successfully.");
		} catch (Exception e) {
			logger.debug("Could not get item information.");
		}

		ItemInformationResponse itemInformationResponse = (ItemInformationResponse)serverResponse;
		itemInfo = new TechletItemInformation();
		itemInfo.setItemIdentifier(itemInformationResponse.getItemIdentifier());
		itemInfo.setTitleIdentifier(itemInformationResponse.getTitleIdentifier());
		itemInfo.setDueDate(itemInformationResponse.getDueDate());
		try {
			itemInfo.setFeeAmount(Double.parseDouble(itemInformationResponse.getFeeAmount()));
		} catch (Exception e) {
			logger.warn("Could not set fee amount.");
		}
		itemInfo.setItemProperties(itemInformationResponse.getItemProperties());
		itemInfo.setScreenMessage(itemInformationResponse.getScreenMessage());
		return super.execute();
	}

	public TechletItemInformation getItemInfo() {
		return itemInfo;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
}
