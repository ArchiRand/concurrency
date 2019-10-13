package ru.javaops.masterjava.xml.utils;

import com.google.common.io.Resources;
import org.junit.Assert;
import org.junit.Test;
import ru.javaops.masterjava.xml.MainXML;
import ru.javaops.masterjava.xml.schema.CityType;
import ru.javaops.masterjava.xml.schema.ObjectFactory;
import ru.javaops.masterjava.xml.schema.Payload;
import ru.javaops.masterjava.xml.schema.User;
import ru.javaops.masterjava.xml.utils.jaxb.JaxbParser;
import ru.javaops.masterjava.xml.utils.jaxb.Schemas;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import java.util.List;

public class JaxbParserTest {

    private static final JaxbParser JAXB_PARSER = new JaxbParser(ObjectFactory.class);

    static {
        JAXB_PARSER.setSchema(Schemas.ofClasspath("payload.xsd"));
    }

    @Test
    public void testPayload() throws Exception {
//        JaxbParserTest.class.getResourceAsStream("/city.xml")
        Payload payload = JAXB_PARSER.unmarshal(
                Resources.getResource("payload.xml").openStream());
        String strPayload = JAXB_PARSER.marshal(payload);
        JAXB_PARSER.validate(strPayload);
        System.out.println(strPayload);
    }

    @Test
    public void testCity() throws Exception {
        JAXBElement<CityType> cityElement = JAXB_PARSER.unmarshal(Resources.getResource("city.xml").openStream());
        CityType city = cityElement.getValue();
        JAXBElement<CityType> cityElement2 =
                new JAXBElement<>(new QName("archirand", "City"), CityType.class, city);
        String strCity = JAXB_PARSER.marshal(cityElement2);
        JAXB_PARSER.validate(strCity);
        System.out.println(strCity);
    }

    @Test
    public void test() throws Exception {
        MainXML mainXML = new MainXML();
        List<User> users = mainXML.getUsersByProject("topjava");
        Assert.assertEquals(2, users.size());
//        System.out.println(mainXML.toHtml(users, "topjava"));
    }
}
