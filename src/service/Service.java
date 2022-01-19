package service;

import java.util.ArrayList;

import service.classes.ServiceClass;
import service.cohesion.ServiceCohesionCalculator;

public class Service {
	
	private String name;
	private float cohesion;

	private ArrayList<ServiceClass> containedClasses;
	
	public Service(String name) {
		this.name = name;
		containedClasses = new ArrayList<ServiceClass>();
	}
	
	public int getClassesCount() {
		return containedClasses.size();
	}
	
	public void updateCohesion() {
		cohesion = ServiceCohesionCalculator.calculateServiceCohesion(this);
	}
}
