//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.12.22 à 06:49:46 PM CET 
//


package fr.sco.activitytracker.config.bind;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{}listModule"/&gt;
 *         &lt;element ref="{}listComponent"/&gt;
 *         &lt;element ref="{}listDefProcessus"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "listModule",
    "listComponent",
    "listDefProcessus"
})
@XmlRootElement(name = "config")
public class Config {

    @XmlElement(required = true)
    protected ListModule listModule;
    @XmlElement(required = true)
    protected ListComponent listComponent;
    @XmlElement(required = true)
    protected ListDefProcessus listDefProcessus;

    /**
     * Obtient la valeur de la propriété listModule.
     * 
     * @return
     *     possible object is
     *     {@link ListModule }
     *     
     */
    public ListModule getListModule() {
        return listModule;
    }

    /**
     * Définit la valeur de la propriété listModule.
     * 
     * @param value
     *     allowed object is
     *     {@link ListModule }
     *     
     */
    public void setListModule(ListModule value) {
        this.listModule = value;
    }

    /**
     * Obtient la valeur de la propriété listComponent.
     * 
     * @return
     *     possible object is
     *     {@link ListComponent }
     *     
     */
    public ListComponent getListComponent() {
        return listComponent;
    }

    /**
     * Définit la valeur de la propriété listComponent.
     * 
     * @param value
     *     allowed object is
     *     {@link ListComponent }
     *     
     */
    public void setListComponent(ListComponent value) {
        this.listComponent = value;
    }

    /**
     * Obtient la valeur de la propriété listDefProcessus.
     * 
     * @return
     *     possible object is
     *     {@link ListDefProcessus }
     *     
     */
    public ListDefProcessus getListDefProcessus() {
        return listDefProcessus;
    }

    /**
     * Définit la valeur de la propriété listDefProcessus.
     * 
     * @param value
     *     allowed object is
     *     {@link ListDefProcessus }
     *     
     */
    public void setListDefProcessus(ListDefProcessus value) {
        this.listDefProcessus = value;
    }

}
