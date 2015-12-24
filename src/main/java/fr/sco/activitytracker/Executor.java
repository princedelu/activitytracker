package fr.sco.activitytracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.sco.activitytracker.config.ConfigTracker;
import fr.sco.activitytracker.config.EventStepTracker;
import fr.sco.activitytracker.exception.ExecutorException;
import fr.sco.activitytracker.metier.AbstractInserter;
import fr.sco.activitytracker.metier.InstanceProcessusBO;
import fr.sco.activitytracker.metier.Processus;
import fr.sco.activitytracker.metier.StepBO;
import fr.sco.activitytracker.utils.StringUtils;

public class Executor {

	private ConfigTracker configTracker;

	private static final Logger logger = LoggerFactory.getLogger(Executor.class);

	public Executor(ConfigTracker configTracker) {
		super();
		this.configTracker = configTracker;
	}

	public void exec(Exchange exchange) {

		try {
			String body = exchange.getIn().getBody(String.class);
			String eventName = exchange.getIn().getHeader("eventName", String.class);

			Map<String, String> mapBody = StringUtils.toHashMap(body, ";", "=");

			checkEvent(exchange, eventName, mapBody);

			fireEvent(mapBody);

		} catch (ExecutorException ee) {

		}
	}

	private void checkEvent(Exchange exchange, String eventName, Map<String, String> mapBody) throws ExecutorException {
		// Récupération du nom de l'évènement

		logger.debug("Event : " + eventName);

		if (mapBody.get("eventName") == null) {
			throw new ExecutorException("L evenement ne possède pas de nom => Controle impossible");
		} else {
			if (!mapBody.get("eventName").equals(eventName)) {
				throw new ExecutorException("L'evenement n'est pas dans la bonne eventqueue");
			}
		}
	}

	private void fireEvent(Map<String, String> mapBody) throws ExecutorException {

		Processus processus = null;
		
		AbstractInserter inserter = configTracker.getInserter();

		String eventName = mapBody.get("eventName");

		inserter.insertEvent(mapBody);

		HashMap<Processus, List<EventStepTracker>> processusEvent = new HashMap<Processus, List<EventStepTracker>>();

		// Parcours de tous les steps pour construction de la liste des
		// processus à traiter
		for (EventStepTracker est : configTracker.getEventMap().get(eventName)) {
			
			processus = new Processus();
			processus.setPackageName(est.getProcessus().getPackageName() );
			processus.setName(est.getProcessus().getName());
			processus.setVersion(est.getProcessus().getVersion());
			
			if (processusEvent.get(processus) == null) {
				ArrayList<EventStepTracker> listEst = new ArrayList<EventStepTracker>();
				listEst.add(est);
				processusEvent.put(processus, listEst);
			} else {
				processusEvent.get(processus).add(est);
			}
		}

		// Traitement de chaque processus
		Iterator<Processus> iterator = processusEvent.keySet().iterator();
		while (iterator.hasNext()) {
			processus = iterator.next();

			ArrayList<InstanceProcessusBO> listIp = inserter.getInstanceProcessus(processus, mapBody);

			// A t on trouvé des instances de processus
			if (listIp == null) {
				// Aucune instance de processus

				boolean createInstance = false;
				// Existe t il un step qui créé l instance
				for (EventStepTracker est : processusEvent.get(processus)) {
					if ("BEGIN".equals(est.getStepOrigine())) {
						createInstance = true;
						break;
					}
				}

				InstanceProcessusBO iP = null;

				// Creation uniquement d'une instance
				if (createInstance) {
					
					iP = inserter.createInstanceProcessus(processus, mapBody);
					logger.debug("Création de l'instance de processus : " + iP.getId());
					// Ajout des step associés
					for (EventStepTracker est : processusEvent.get(processus)) {
						if ("BEGIN".equals(est.getStepOrigine())) {
							logger.debug("Insertion du step : " + est.getName());
							inserter.addStepInstanceProcessus(iP, est);
						}
					}
				}
			} else {
				// Il y a des instances de processus
				for (InstanceProcessusBO iPBO : listIp) {
					ArrayList<StepBO> currentListStepBO = new ArrayList<StepBO>();

					for (StepBO currentStepBO : iPBO.getListStep()) {
						currentListStepBO.add(currentStepBO);
					}

					for (EventStepTracker est : configTracker.getEventMap().get(eventName)) {
						boolean stepToInsert = false;
						// Parcours de tous les steps actifs pour cette instance de processus
						for (StepBO sBO : iPBO.getListStep()) {
							if (est.getStepOrigine().equals(sBO.getName()) || "ALL".equals(est.getStepOrigine())) {
								// Désactivation de l'état précédent
								inserter.desactivateStepInstanceProcessus(sBO);
								logger.debug("Désactivation du step " + sBO.getId() + " pour le nom " + sBO.getName());
								currentListStepBO.remove(sBO);
								stepToInsert = true;
							}
						}
						
						// Vérification si le step n'est pas déjà actif pour cette instance de processus
						boolean stepExist = false;
						for (StepBO currentStepBO : currentListStepBO) {
							if (currentStepBO.getName().equals(est.getStep())) {
								stepExist = true;
							}
						}
						
						// Ajout du step
						if (!stepExist && stepToInsert) {
							StepBO addedStepBO = inserter.addStepInstanceProcessus(iPBO, est);
							currentListStepBO.add(addedStepBO);
							logger.debug(
									"Ajout du step " + addedStepBO.getId() + " pour le nom " + addedStepBO.getName());
						}

					}
				}
			}

		}

	}

}
