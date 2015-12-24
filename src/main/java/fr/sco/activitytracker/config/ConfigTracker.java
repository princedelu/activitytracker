package fr.sco.activitytracker.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import fr.sco.activitytracker.config.bind.Config;
import fr.sco.activitytracker.config.bind.DefProcessus;
import fr.sco.activitytracker.config.bind.Module;
import fr.sco.activitytracker.config.bind.processus.Link;
import fr.sco.activitytracker.config.bind.processus.Processus;
import fr.sco.activitytracker.config.bind.processus.Step;
import fr.sco.activitytracker.dao.InserterFactory;
import fr.sco.activitytracker.exception.ConfigException;
import fr.sco.activitytracker.metier.AbstractInserter;

/**
 * Created by stiffler on 27/03/15.
 */
public class ConfigTracker {

	private Config config;

	private List<Processus> listProcessus;

	private HashMap<String, List<EventStepTracker>> eventMap;

	private HashMap<String, Module> moduleMap;
	
	private AbstractInserter inserter;

	public ConfigTracker() {
	}

	public void configure(String filePath) throws ConfigException {

		// Chargement du fichier initiale
		loadConfig(filePath);

		// Action pre chargement des processus
		preProcessus();

		// Chargement des fichiers processus
		loadProcessus();

		// Action post chargement des processus
		postProcessus();
	}

	@SuppressWarnings("restriction")
	public void loadConfig(String filePath) throws ConfigException {

		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new ConfigException("Le fichier indiqué en paramètre n'existe pas");
			}
			JAXBContext jc = JAXBContext.newInstance("fr.sco.activitytracker.config.bind");
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			if (unmarshaller.getSchema() != null) {
				throw new ConfigException("Le fichier XML en entrée ne respecte pas le schéma");
			}
			config = (Config) unmarshaller.unmarshal(file);
		} catch (JAXBException je) {
			throw new ConfigException("Problème de chargement du fichier XML", je);
		} catch (NullPointerException npe) {
			throw new ConfigException("Le fichier ne peut pas être chargé", npe);
		}
	}

	@SuppressWarnings("restriction")
	public void loadProcessus() throws ConfigException {
		for (DefProcessus defProcessus : config.getListDefProcessus().getDefProcessus()) {

			// Vérification du fichier
			try {
				File file = new File(defProcessus.getFile());
				if (!file.exists()) {
					throw new ConfigException("Le fichier processus : " + defProcessus.getFile() + " n'existe pas");
				}
				JAXBContext jc = JAXBContext.newInstance("fr.sco.activitytracker.config.bind.processus");
				Unmarshaller unmarshaller = jc.createUnmarshaller();
				if (unmarshaller.getSchema() != null) {
					throw new ConfigException("Le fichier XML processus en entrée ne respecte pas le schéma");
				}
				Processus processus = (Processus) unmarshaller.unmarshal(file);
				checkSteps(processus);
				getListProcessus().add(processus);
			} catch (JAXBException je) {
				throw new ConfigException("Problème de chargement du fichier XML processus", je);
			} catch (NullPointerException npe) {
				throw new ConfigException("Le fichier processus ne peut pas être chargé", npe);
			}
		}
	}

	public void checkSteps(Processus processus) throws ConfigException {
		boolean resultIntermedaire;

		// Vérification qu'un step d'origine d'évènements existe
		for (Step step : processus.getListStep().getStep()) {
			for (Link link : step.getListLink().getLink()) {
				resultIntermedaire = false;
				List<EventStepTracker> element;

				// Construction de la liste des évènements
				if (!eventMap.containsKey(link.getEventName())) {
					element = new ArrayList<EventStepTracker>();
					EventStepTracker est = new EventStepTracker();
					est.setName(link.getEventName());
					est.setProcessus(processus);
					est.setStep(step.getName());
					est.setStepOrigine(link.getStepNameOrigine());
					element.add(est);
					eventMap.put(link.getEventName(), element);
				} else {
					element = eventMap.get(link.getEventName());
					EventStepTracker est = new EventStepTracker();
					est.setName(link.getEventName());
					est.setProcessus(processus);
					est.setStep(step.getName());
					est.setStepOrigine(link.getStepNameOrigine());
					element.add(est);
				}

				// Vérification si le step d'origine existe
				if (link.getStepNameOrigine().equals("BEGIN") || link.getStepNameOrigine().equals("ALL")) {
					resultIntermedaire = true;
				} else {
					for (Step stepIn : processus.getListStep().getStep()) {
						if (link.getStepNameOrigine().equals(stepIn.getName())) {
							resultIntermedaire = true;
						}
					}
				}
				if (!resultIntermedaire) {
					throw new ConfigException("Un step origine n'existe pas");
				}
			}
		}

		// Vérification qu'un évènement provient
	}

	public void preProcessus() throws ConfigException {
		// Création de la chaine des évènements
		eventMap = new HashMap<String, List<EventStepTracker>>();
	}

	public void postProcessus() throws ConfigException {
		// Création de la chaine des évènements
		moduleMap = new HashMap<String, Module>();

		// Construction de la map des modules
		for (Module module : config.getListModule().getModule()) {
			moduleMap.put(module.getName(), module);
		}

		if (moduleMap.get("database") == null) {
			throw new ConfigException("Le module database est inexistant");
		}

		Module databaseModule = moduleMap.get("database");
		inserter = InserterFactory.getInserter(databaseModule.getType(),databaseModule.getValue());
	}

	public Config getConfig() {
		return config;
	}

	public List<Processus> getListProcessus() {
		if (listProcessus == null) {
			listProcessus = new ArrayList<Processus>();
		}
		return listProcessus;
	}

	public HashMap<String, List<EventStepTracker>> getEventMap() {
		return eventMap;
	}

	public void setEventMap(HashMap<String, List<EventStepTracker>> eventMap) {
		this.eventMap = eventMap;
	}

	public HashMap<String, Module> getModuleMap() {
		return moduleMap;
	}

	public AbstractInserter getInserter() {
		return inserter;
	}
}
