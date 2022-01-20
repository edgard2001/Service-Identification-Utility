package service.identification;

import java.util.ArrayList;
import java.util.Arrays;

import service.Service;
import service.classes.ServiceClass;
import service.util.UtilMaths;

public class ServiceIdentifier {

	private ArrayList<ServiceClass> classes;
	private ArrayList<Service> services;
	private final float cohesionCutOff;

	public ServiceIdentifier(ArrayList<ServiceClass> classes) {
		this(classes, 0.75f);
	}

	public ServiceIdentifier(ArrayList<ServiceClass> classes, float cohesionCutOff) {
		this.classes = classes;
		this.cohesionCutOff = cohesionCutOff;
		services = new ArrayList<Service>();
	}

	public ArrayList<Service> calculateServiceCohesion(ArrayList<ServiceClass> classes) {

		// Step 1
		addSingletonServices();
		ArrayList<Service> originalServices;
		do {
			originalServices = new ArrayList<Service>(services);
			int numStepOneServices = services.size();
			// Step 2
			addAllPossiblePairs();
			// Step 3
			removeStepTwoServicesBelowCutoff(numStepOneServices);
			// Step 4
			Service selectedService = getHighestCohesionService();
			// Step 5
			removeConstituentServices(selectedService.getName());
			// Step 6
			removeDuplicateConstituentClasses(selectedService);
			// Step 7
			removeAllNonUniqueBelowCutOff();
			// Step 8
		} while (services.size() > 1);
		// Step 9
		float originalCohesion = averageServiceCohesion(originalServices);
		float newCohesion = averageServiceCohesion(services);
		if (originalCohesion > newCohesion)
			services = originalServices;
		// Step 10
		

		return services;
	}

	private void addSingletonServices() {
		for (int i = 0; i < services.size(); i++) {
			services.add(new Service("Service" + i, new ArrayList<ServiceClass>(Arrays.asList(classes.get(i)))));
		}
	}

	private void addAllPossiblePairs() {
		for (int i = 0; i < services.size(); i++) {
			Service service1 = services.get(i);
			for (int j = i; j < services.size(); j++) {
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
				services.remove(service);
			}
		}
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

	public void removeConstituentServices(String serviceName) {
		for (int i = 0; i < services.size(); i++) {
			String serviceName1 = services.get(i).getName();
			for (int j = i; j < services.size(); j++) {
				String serviceName2 = classes.get(j).getName();
				if (serviceName.equals(serviceName1 + " " + serviceName2)) {
					services.set(i, null);
					services.set(j, null);
				}
			}
		}
		int numRemoved = 0;
		while (services.remove(null));
		services.trimToSize();
		/*
		for (int i = 0; i < services.size() - numRemoved; i++) {
			if (services.get(i) == null) {
				services.remove(i);
				numRemoved++;
				i--;
			}
		}
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
					classes.remove(aClass);
					service = new Service(service.getName(), serviceClasses);
				}
			}
		}
	}

	public void removeAllNonUniqueBelowCutOff() {
		int[] classOccurances = new int[classes.size()];
		for (int i = 0; i < classes.size(); i++) {
			for (Service service : services) {
				if (service.getClasses().contains(classes.get(i)))
					classOccurances[i]++;
			}
		}
		for (Service service : services) {
			if (service.getCohesion() > cohesionCutOff)
				continue;
			boolean uniqueClass = false;
			for (ServiceClass aClass : service.getClasses()) {
				if (classOccurances[classes.indexOf(aClass)] == 1) {
					uniqueClass = true;
					break;
				}
			}
			if (!uniqueClass)
				service = null;
		}
		while (services.remove(null));
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
