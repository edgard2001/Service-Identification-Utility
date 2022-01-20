package service.identification;

import java.util.ArrayList;
import java.util.Arrays;

import service.Service;
import service.classes.ServiceClass;

public class ServiceIdentifier {

	private ArrayList<Service> originalServices;
	private ArrayList<ServiceClass> classes;
	private ArrayList<Service> services;
	private final float cohesionCutOff;

	public ServiceIdentifier(ArrayList<Service> originalServices) {
		this(originalServices, 0.75f);
	}

	public ServiceIdentifier(ArrayList<Service> originalServices, float cohesionCutOff) {
		this.originalServices = originalServices;
		classes = new ArrayList<ServiceClass>();
		for (Service service : originalServices) {
			for (ServiceClass aClass : service.getClasses()) {
				if (!classes.contains(aClass))
					classes.add(aClass);
			}
		}
		this.cohesionCutOff = cohesionCutOff;
		services = new ArrayList<Service>();
	}

	public ArrayList<Service> identifyServices() {

		if (classes.size() == 0)
			return null;

		// Step 1
		addSingletonServices();
		int numStepOneServices;
		do {
			numStepOneServices = services.size();
			// Step 2
			addAllPossiblePairs(numStepOneServices);
			// Step 3
			removeStepTwoServicesBelowCutoff(numStepOneServices);
			// Step 4
			Service selectedService = getHighestCohesionService();
			// Step 5
			removeConstituentServices(selectedService.getName(), numStepOneServices);
			// Step 6
			removeDuplicateConstituentClasses(selectedService);
			// Step 7
			removeAllNonUniqueServicesBelowCutOff();
			// Step 8
		} while (services.size() - numStepOneServices > 1);
		// Step 9
		float originalCohesion = averageServiceCohesion(originalServices);
		float newCohesion = averageServiceCohesion(services);
		if (originalCohesion > newCohesion)
			services = originalServices;
		// Step 10
		

		return services;
	}

	private void addSingletonServices() {
		for (int i = 0; i < classes.size(); i++) {
			services.add(new Service("Service" + i, new ArrayList<ServiceClass>(Arrays.asList(classes.get(i)))));
		}
	}

	private void addAllPossiblePairs(int numStepOneServices) {
		for (int i = 0; i < numStepOneServices; i++) {
			Service service1 = services.get(i);
			for (int j = i+1; j < numStepOneServices; j++) {
				Service service2 = services.get(j);
				ArrayList<ServiceClass> newClasses = new ArrayList<ServiceClass>();
				newClasses.addAll(service1.getClasses());
				newClasses.addAll(service2.getClasses());
				services.add(new Service(service1.getName() + " " + service2.getName(), newClasses));
			}
		}
	}

	private void removeStepTwoServicesBelowCutoff(int numStepOneServices) {
		for (int i = numStepOneServices; i < services.size(); i++) {
			Service service = services.get(i);
			if (service.getCohesion() < cohesionCutOff) {
				services.remove(i--);
			}
		}
		services.trimToSize();
	}

	private Service getHighestCohesionService() {
		float highestCohesion = -1.0f;
		int highestCohesionServiceIndex = -1;
		for (int i = 0; i < services.size(); i++) {
			float cohesion = services.get(i).getCohesion();
			if (cohesion > highestCohesion) {
				highestCohesion = cohesion;
				highestCohesionServiceIndex = i;
			}
		}
		return services.get(highestCohesionServiceIndex);
	}

	public void removeConstituentServices(String serviceName, int numStepOneServices) {
		boolean deleted = false;
		for (int i = 0; i < numStepOneServices; i++) {
			String serviceName1 = services.get(i).getName();
			for (int j = 0; j < numStepOneServices; j++) {
				String serviceName2 = services.get(j).getName();
				if (serviceName.equals(serviceName1 + " " + serviceName2)) {
					services.set(j, null);
					services.set(i, null);
					deleted = true;
					break;
				}
			}
			if (deleted)
				break;
		}
		while (services.remove(null)) {
		}
		services.trimToSize();
		/*
		 * int numRemoved = 0; for (int i = 0; i < services.size() - numRemoved; i++) {
		 * if (services.get(i) == null) { services.remove(i); numRemoved++; i--; } }
		 */

	}

	public void removeDuplicateConstituentClasses(Service selectedService) {
		for (int i = 0; i < services.size(); i++) {
			Service service = services.get(i);
			if (service.equals(selectedService))
				continue;
			for (ServiceClass aClass : selectedService.getClasses()) {
				ArrayList<ServiceClass> serviceClasses = service.getClasses();
				if (classes.contains(aClass)) {
					serviceClasses.remove(aClass);
					services.set(i, new Service(service.getName(), serviceClasses));
				}
			}
		}
	}

	public void removeAllNonUniqueServicesBelowCutOff() {
		int[] classOccurances = new int[classes.size()];
		for (int i = 0; i < classes.size(); i++) {
			for (Service service : services) {
				if (service.getClasses().contains(classes.get(i)))
					classOccurances[i]++;
			}
		}
		for (int i = 0; i < services.size(); i++) {
			Service service = services.get(i);
			if (service.getCohesion() > cohesionCutOff)
				continue;
			boolean uniqueClass = false;
			for (ServiceClass aClass : service.getClasses()) {
				if (classOccurances[classes.indexOf(aClass)] == 1) {
					uniqueClass = true;
					break;
				}
			}
			if (!uniqueClass) {
				for (ServiceClass aClass : service.getClasses()) {
					classOccurances[classes.indexOf(aClass)]--;
				}
				services.set(i, null);
			}
		}
		while (services.remove(null)) {
		}
		services.trimToSize();
	}

	public float averageServiceCohesion(ArrayList<Service> servicesList) {
		float average = 0.0f;
		for (Service serivce : servicesList) {
			average += serivce.getCohesion();
		}
		return average / servicesList.size();
	}

}
