package fr.sco.activitytracker.camel.component;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.camel.Component;
import org.apache.camel.Endpoint;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.component.kafka.KafkaEndpoint;

import fr.sco.activitytracker.config.bind.ComponentType;

/**
 * Created by stiffler on 19/03/15.
 */
public class ComponentFactory {

	public static Component getComponent(fr.sco.activitytracker.config.bind.Component cc) {
		Component result = null;

		if (cc.getType().equals(ComponentType.ACTIVEMQ)) {
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
			connectionFactory.setBrokerURL(cc.getUrl());

			// and register it into the CamelContext
			ActiveMQComponent component = new ActiveMQComponent();
			component.setConnectionFactory(connectionFactory);

			result = component;
		}

		if (cc.getType().equals(ComponentType.KAFKA)) {

			KafkaComponent component = new KafkaComponent();

			KafkaEndpoint kep = new KafkaEndpoint(cc.getUrl(), component);

			result = component;
		}

		return result;
	}
}
