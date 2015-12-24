package fr.sco.activitytracker.utils;

import java.util.HashMap;
import java.util.Map;

public class StringUtils {

	public static Map<String, String> toHashMap(String text,String sepMaster,String sepKeyValue) {

		/**
		 * This String text could vary this could be like
		 * "key1=value1,key2=value2,key3=value3" or could be using any kind of
		 * seperator. The seperator used in this case is ',' This could be any
		 * character but you should keep in mind as what is it.
		 */
		Map<String, String> map = new HashMap<String, String>();

		// Seperator is specified here, to split string on this basis
		for (String keyValue : text.split(sepMaster)) {

			// Here the each part is futher splitted taking in account the equal
			// sign '=' which demarcates the key
			// and valuefor the hashmap
			String[] pairs = keyValue.split(sepKeyValue, 2);

			// Those key and values are then put into hashmap
			map.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);

		} // Successfully String to hashmap transformation is completed
		
		return map;
	}

}
