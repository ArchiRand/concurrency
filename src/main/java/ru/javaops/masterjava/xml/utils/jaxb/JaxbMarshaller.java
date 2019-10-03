package ru.javaops.masterjava.xml.utils.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.validation.Schema;
import java.io.StringWriter;
import java.io.Writer;

public class JaxbMarshaller {

    private Marshaller marshaller;

    public JaxbMarshaller(JAXBContext ctx) throws JAXBException {
        this.marshaller = ctx.createMarshaller();
        // Свойство, указывающие необходимо ли при мапинге в XML соблюдать выравнивание и отступы
        this.marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        this.marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        // Иногда требуется вывести XML без заголовка <xml version=»1.0″ encoding=»UTF-8″ standalone=»yes»?>
        // Чтобы сделать это нужно маршаллеру указать свойство JAXB_FRAGMENT = true
        this.marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
    }

    // Для установки дополнительных свойств маршаллеру
    public void setProperty(String prop, Object value) throws PropertyException {
        marshaller.setProperty(prop, value);
    }

    // Schema - это механизм валидации для документа, который будет сгенерирован
    // Если в метод передать null это выключит валидацию
    public synchronized void setSchema(Schema schema) {
        marshaller.setSchema(schema);
    }

    //
    public String marshal(Object instance) throws JAXBException {
        StringWriter sw = new StringWriter();
        marshal(instance, sw);
        return sw.toString();
    }

    public synchronized void marshal(Object instance, Writer writer) throws JAXBException {
        marshaller.marshal(instance, writer);
    }
}
