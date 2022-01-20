package service;

import java.util.ArrayList;

import service.classes.ServiceClass;
import service.util.UtilMaths;

public class Service {

	private String name;
	private float cohesion;
	private ArrayList<ServiceClass> containedClasses;

	public Service(String name, ArrayList<ServiceClass> classes) {
		this.name = name;
		containedClasses = classes;
		cohesion = calculateCohesion();
	}

	public String getName() {
		return name;
	}

	public float getCohesion() {
		return cohesion;
	}
	
	public ArrayList<ServiceClass> getClasses() {
		ArrayList<ServiceClass> classes = new ArrayList<ServiceClass>(containedClasses);
		return classes;
	}
	
	private float calculateCohesion() {
		int numRelationships = 0;
		for (int i = 0; i < containedClasses.size(); i++) {
			ServiceClass currentClass = containedClasses.get(i);
			numRelationships += containedClasses.get(i).getNumRelationship();
			for (int j = 0; j < i; j++) {
				if (currentClass.hasRelationship(containedClasses.get(j)))
					numRelationships--;
			}
		}

		int numClasses = containedClasses.size();
		int numPossible = UtilMaths.combinations(numClasses, 2);

		return numRelationships / (float) numPossible;
	}
}
