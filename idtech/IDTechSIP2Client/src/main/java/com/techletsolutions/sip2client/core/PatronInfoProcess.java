package com.techletsolutions.sip2client.core;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ceridwen.circulation.SIP.messages.ACSStatus;
import com.ceridwen.circulation.SIP.messages.Message;
import com.ceridwen.circulation.SIP.messages.PatronInformation;
import com.ceridwen.circulation.SIP.messages.PatronInformationResponse;
import com.ceridwen.circulation.SIP.transport.SocketConnection;
import com.ceridwen.circulation.SIP.types.flagfields.Summary;
import com.ceridwen.circulation.SIP.types.flagfields.SupportedMessages;
import com.techletsolutions.sip2client.conf.Msg;
import com.techletsolutions.sip2client.error.Errors;
import com.techletsolutions.sip2client.error.TechletPatronInformation;
import com.techletsolutions.sip2client.exceptions.SIP2ClientException;

public class PatronInfoProcess extends SIP2Process {

	final static Logger logger = LoggerFactory.getLogger(PatronInfoProcess.class);

	private String patronId;

	private TechletPatronInformation patronInfo;

	public PatronInfoProcess(SocketConnection connection, Message message, String patronId) {
		super(connection, message);
		this.patronId = patronId;
	}

	@Override
	public int execute() {

		ItemInfoProcess itemInfoProcess = new ItemInfoProcess(connection, response, null); 

		// Check if the server can support checkin
		if (!((ACSStatus) response).getSupportedMessages().isSet(SupportedMessages.PATRON_INFORMATION)) {
			logger.error(Msg.get("patroninfo.notsupported.error"), response.toString());
			return Errors.ERROR_PATRON_INFORMATION_SUPPORTED;
		}

		// The code below would be the normal way of creating the request
		PatronInformation patronInformationRequest = new PatronInformation();
		patronInformationRequest.setPatronIdentifier(patronId);
		patronInformationRequest.getSummary().set(Summary.CHARGED_ITEMS);
		patronInformationRequest.setTransactionDate(new Date());

		try {
			response = connection.send(patronInformationRequest);
		} catch (Exception e) {
			logger.error(Msg.get("patroninfo.connection.error"), e);
			return new SIP2ClientException(e).getError();

		}

		if (!(response instanceof PatronInformationResponse)) {
			logger.error(Msg.get("patroninfo.reponse.invalid.error"), response.toString());
			return Errors.ERROR_INVALID_PATRON_INFORMATION_REQUEST;
		} else {
			if (((PatronInformationResponse) response).isValidPatron()) {
				logger.info("Patron information received successfully.");
			} else {
				logger.warn(Msg.get("patroninfo.reponse.invalid.error"), ((PatronInformationResponse) response).getScreenMessage());
				return Errors.ERROR_INVALID_PATRON;
			}
		}

		try {
			PatronInformationResponse patronInfoResponse = (PatronInformationResponse)response;
			patronInfo = new TechletPatronInformation();
			patronInfo.setId(patronInfoResponse.getPatronIdentifier());
			patronInfo.setName(patronInfoResponse.getPersonalName());
			patronInfo.setEmail(patronInfoResponse.getEmailAddress());
			patronInfo.setHomeAddress(patronInfoResponse.getHomeAddress());
			patronInfo.setChargedItemsCount(patronInfoResponse.getChargedItemsCount());
			patronInfo.setFinesAndCharges(patronInfoResponse.getFeeAmount());
			if (patronInfoResponse.getChargedItemsCount() > 0) {
				String[] chargedItems = patronInfoResponse.getChargedItems();
				for (String chargedItem : chargedItems) {
					itemInfoProcess.setItemId(chargedItem);
					itemInfoProcess.execute();
					patronInfo.addChargedItem(itemInfoProcess.getItemInfo());
				}
			}
		} catch (Exception e) {
			logger.error(Msg.get("patroninfo.unknown.error"), e);
			return Errors.UNKNOWN_ERROR;
		}
		return super.execute();
	}

	public TechletPatronInformation getPatronInfo() {
		return patronInfo;
	}
}
