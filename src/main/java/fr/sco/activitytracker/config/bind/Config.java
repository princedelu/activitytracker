//
// Ce fichier a �t� g�n�r� par l'impl�mentation de r�f�rence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apport�e � ce fichier sera perdue lors de la recompilation du sch�ma source. 
// G�n�r� le : 2015.12.22 � 06:49:46 PM CET 
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
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
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
     * Obtient la valeur de la propri�t� listModule.
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
     * D�finit la valeur de la propri�t� listModule.
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
     * Obtient la valeur de la propri�t� listComponent.
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
     * D�finit la valeur de la propri�t� listComponent.
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
     * Obtient la valeur de la propri�t� listDefProcessus.
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
     * D�finit la valeur de la propri�t� listDefProcessus.
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
