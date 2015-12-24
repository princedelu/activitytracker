package fr.sco.activitytracker.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExecutorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = LoggerFactory.getLogger(ExecutorException.class);

    public ExecutorException(String message){
        super(message);
        log();
    }

    public ExecutorException(String message, Throwable cause){
        super(message,cause);
        log();
    }

    private void log(){
        logger.error("Erreur d execution");
        logger.error(this.getMessage());
        if (this.getCause()!=null){
            logger.error("Exception initiale", this.getCause());
        }
    }
}
