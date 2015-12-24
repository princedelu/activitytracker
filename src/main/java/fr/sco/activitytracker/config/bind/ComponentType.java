//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.12.22 à 06:49:46 PM CET 
//


package fr.sco.activitytracker.config.bind;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour componentType.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * <p>
 * <pre>
 * &lt;simpleType name="componentType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="activemq"/&gt;
 *     &lt;enumeration value="mongodb"/&gt;
 *     &lt;enumeration value="kafka"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "componentType")
@XmlEnum
public enum ComponentType {

    @XmlEnumValue("activemq")
    ACTIVEMQ("activemq"),
    @XmlEnumValue("mongodb")
    MONGODB("mongodb"),
    @XmlEnumValue("kafka")
    KAFKA("kafka");
    private final String value;

    ComponentType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ComponentType fromValue(String v) {
        for (ComponentType c: ComponentType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
