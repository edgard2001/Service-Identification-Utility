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
			for (int j = i; j < containedClasses.size(); j++) {
				if (currentClass.hasRelationship(containedClasses.get(j)))
					numRelationships++;
			}
		}

		int numClasses = containedClasses.size();
		int numPossible = UtilMaths.combinations(numClasses, 2);

		return numRelationships / (float) numPossible;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name).append(" ").append(cohesion).append("\n");
		for (ServiceClass aClass : containedClasses) {
			sb.append(aClass.getName()).append("\n");
		}
		return sb.toString();
	}
}
