//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.2-hudson-jaxb-ri-2.2-63- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.11.11 at 09:58:01 AM MEZ 
//


package net.sf.ahtutils.xml.mail;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.sf.ahtutils.xml.mail package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.sf.ahtutils.xml.mail
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link To }
     * 
     */
    public To createTo() {
        return new To();
    }

    /**
     * Create an instance of {@link EmailAddress }
     * 
     */
    public EmailAddress createEmailAddress() {
        return new EmailAddress();
    }

    /**
     * Create an instance of {@link Mail }
     * 
     */
    public Mail createMail() {
        return new Mail();
    }

    /**
     * Create an instance of {@link Mails }
     * 
     */
    public Mails createMails() {
        return new Mails();
    }

    /**
     * Create an instance of {@link Bcc }
     * 
     */
    public Bcc createBcc() {
        return new Bcc();
    }

    /**
     * Create an instance of {@link Tracker }
     * 
     */
    public Tracker createTracker() {
        return new Tracker();
    }

    /**
     * Create an instance of {@link From }
     * 
     */
    public From createFrom() {
        return new From();
    }

    /**
     * Create an instance of {@link Cc }
     * 
     */
    public Cc createCc() {
        return new Cc();
    }

    /**
     * Create an instance of {@link Header }
     * 
     */
    public Header createHeader() {
        return new Header();
    }

}
