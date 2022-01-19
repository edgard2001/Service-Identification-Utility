package service.cohesion;

import service.Service;

public class ServiceCohesionCalculator {
	
	public static float calculateServiceCohesion(Service service) {
		int numPossible = service.getClassesCount() * (service.getClassesCount() - 1) / 2;
		return 0.0f / numPossible;
	}

}
