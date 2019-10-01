
package ru.javaops.masterjava.xml.schema;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="fullName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *       &lt;attribute name="flag" use="required" type="{arhirand}flagType" />
 *       &lt;attribute name="city" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "email",
    "fullName"
})
@XmlRootElement(name = "User", namespace = "archirand")
public class User {

    @XmlElement(namespace = "archirand", required = true)
    protected String email;
    @XmlElement(namespace = "archirand", required = true)
    protected String fullName;
    @XmlAttribute(name = "flag", required = true)
    protected FlagType flag;
    @XmlAttribute(name = "city", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object city;

}
