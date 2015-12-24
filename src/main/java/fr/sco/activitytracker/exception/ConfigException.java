package fr.sco.activitytracker.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Created by stiffler on 14/03/15.
 */
public class ConfigException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigException.class);

    public ConfigException(String message){
        super(message);
        log();
    }

    public ConfigException(String message, Throwable cause){
        super(message,cause);
        log();
    }

    private void log(){
        logger.error("Erreur de configuration");
        logger.error(this.getMessage());
        if (this.getCause()!=null){
            logger.error("Exception initiale", this.getCause());
        }
        logger.error("Le lancement du tracker doit être effectué avec un argument (Path du file properties de configuration)");
        logger.error("Le fichier properties doit contenir les informations suivantes :");
        logger.error("Type de lancement : Valeurs possibles ALL,...");

    }
}
