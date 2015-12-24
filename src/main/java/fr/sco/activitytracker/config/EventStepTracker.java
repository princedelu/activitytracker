package fr.sco.activitytracker.config;

import fr.sco.activitytracker.config.bind.processus.Processus;

public class EventStepTracker {
	
	private String name;
	
	private Processus processus;
	
	private String step;
	
	private String stepOrigine;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Processus getProcessus() {
		return processus;
	}

	public void setProcessus(Processus processus) {
		this.processus = processus;
	}

	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	public String getStepOrigine() {
		return stepOrigine;
	}

	public void setStepOrigine(String stepOrigine) {
		this.stepOrigine = stepOrigine;
	}
	
	

}
