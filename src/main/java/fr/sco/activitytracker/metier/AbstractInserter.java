package fr.sco.activitytracker.metier;

import java.util.ArrayList;
import java.util.Map;

import fr.sco.activitytracker.config.EventStepTracker;
import fr.sco.activitytracker.exception.ExecutorException;

public abstract class AbstractInserter {

	public abstract void insertEvent(Map<String, String> mapBody) throws ExecutorException;

	public abstract ArrayList<InstanceProcessusBO> getInstanceProcessus(Processus processus, Map<String, String> mapBody)
			throws ExecutorException;

	public abstract InstanceProcessusBO createInstanceProcessus(Processus processus, Map<String, String> mapBody)
			throws ExecutorException;

	public abstract StepBO addStepInstanceProcessus(InstanceProcessusBO iP, EventStepTracker est) throws ExecutorException;
	
	public abstract void desactivateStepInstanceProcessus(StepBO sBO) throws ExecutorException;
}
