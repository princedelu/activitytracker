package fr.sco.activitytracker.metier;

import java.util.ArrayList;

public class InstanceProcessusBO {
	
	private String id;
	
	private ArrayList<StepBO> listStep;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ArrayList<StepBO> getListStep() {
		if (listStep==null){
			listStep = new ArrayList<StepBO>();
		}
		return listStep;
	}

	public void setListStep(ArrayList<StepBO> listStep) {
		this.listStep = listStep;
	}
	
	

}
