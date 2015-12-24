package fr.sco.activitytracker.dao;

import org.apache.kafka.common.config.ConfigException;

import fr.sco.activitytracker.metier.AbstractInserter;

public class InserterFactory {
	
	public static AbstractInserter getInserter(String type,String value) throws ConfigException{
		AbstractInserter result = null;
		
		if ("mongodb".equals(type)) {
			result = new MongoInserter(value);
		}
		
		return result;
	}

}
