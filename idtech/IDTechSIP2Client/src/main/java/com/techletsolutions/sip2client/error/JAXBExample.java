package com.techletsolutions.sip2client.error;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
 
 
public class JAXBExample {
 
    public static void main(String[] args) {
        TechletResponse resp = new TechletResponse();
        resp.setCode(430);
        resp.setMessage("Hello World!");
        jaxbObjectToXML(resp);
    }
 
    /**
     * @param resp
     */
    private static void jaxbObjectToXML(TechletResponse resp) {
/*        try {
            JAXBContext context = JAXBContext.newInstance(TechletResponse.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // Write to System.out for debugging
            m.marshal(resp, System.out);
            // Write to File
            // m.marshal(resp, new File(FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }*/
        
        
        try {
            JAXBContext context = JAXBContext.newInstance(TechletPatronInformation.class);
            Marshaller m = context.createMarshaller();
            //for pretty-print XML in JAXB
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            // Write to System.out for debugging
            
            TechletPatronInformation info = new TechletPatronInformation();
            info.setId("ASDASDA");
            info.setName("Yashpal Meena");
            info.setEmail("Email Address");
            info.addChargedItem(new TechletItemInformation());
            TechletItemInformation item = new TechletItemInformation();
            item.setItemIdentifier("DFREST");
            info.addChargedItem(item);
            m.marshal(info, System.out);
            // Write to File
            // m.marshal(resp, new File(FILE_NAME));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}