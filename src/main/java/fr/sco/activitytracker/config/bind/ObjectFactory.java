//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.11 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2015.12.22 à 06:49:46 PM CET 
//


package fr.sco.activitytracker.config.bind;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the fr.sco.activitytracker.config.bind package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: fr.sco.activitytracker.config.bind
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Config }
     * 
     */
    public Config createConfig() {
        return new Config();
    }

    /**
     * Create an instance of {@link ListModule }
     * 
     */
    public ListModule createListModule() {
        return new ListModule();
    }

    /**
     * Create an instance of {@link Module }
     * 
     */
    public Module createModule() {
        return new Module();
    }

    /**
     * Create an instance of {@link ListComponent }
     * 
     */
    public ListComponent createListComponent() {
        return new ListComponent();
    }

    /**
     * Create an instance of {@link Component }
     * 
     */
    public Component createComponent() {
        return new Component();
    }

    /**
     * Create an instance of {@link ListDefProcessus }
     * 
     */
    public ListDefProcessus createListDefProcessus() {
        return new ListDefProcessus();
    }

    /**
     * Create an instance of {@link DefProcessus }
     * 
     */
    public DefProcessus createDefProcessus() {
        return new DefProcessus();
    }

}
