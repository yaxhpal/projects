package com.techletsolutions.sip2client.utils;

import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.techletsolutions.sip2client.error.TechletPatronInformation;

public class JAXBUtil {

	public static void print(Object jaxbElement, OutputStream os) throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(TechletPatronInformation.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		m.marshal(jaxbElement, os);
	}
}
