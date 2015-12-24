package fr.sco.activitytracker.routeBuilder;

import java.text.MessageFormat;

import org.apache.camel.builder.RouteBuilder;

import fr.sco.activitytracker.Converter;
import fr.sco.activitytracker.Executor;
import fr.sco.activitytracker.config.ConfigTracker;

/**
 * Created by stiffler on 14/03/15.
 */
public class RouteBuilderTracker extends RouteBuilder {

	private ConfigTracker configTracker;

	public RouteBuilderTracker(ConfigTracker configTracker) {
		super();
		this.configTracker = configTracker;
	}

	public void configure() {
		String pathBase = "";
		String broker = "";
		String brokerValue = "";

		if (configTracker.getModuleMap().get("fileListener")!=null){
			pathBase = configTracker.getModuleMap().get("fileListener").getValue();
		}
		if (configTracker.getModuleMap().get("broker")!=null){
			broker = configTracker.getModuleMap().get("broker").getType();
			brokerValue = configTracker.getModuleMap().get("broker").getValue();
		}	

		for (String event : configTracker.getEventMap().keySet()) {
			String brokerString = MessageFormat.format(brokerValue, event,event);
			if (!"".equals(pathBase) && !"".equals(broker)) {
				
				from("file:" + pathBase + "in\\" + event + "?delete=true")
						.bean(new Converter(),"exec")
						.to(broker + ":" + brokerString);
			}
			if (!"".equals(pathBase) && "".equals(broker)) {
				from("file:" + pathBase + "in\\" + event + "?delete=true")
						.setHeader("eventName", constant(event))
						.bean(new Executor(configTracker), "exec");
			}
			if (!"".equals(broker)) {
				from(broker + ":" + brokerString)
						.setHeader("eventName", constant(event))
						.bean(new Executor(configTracker), "exec");
			}
		}
	}
}
