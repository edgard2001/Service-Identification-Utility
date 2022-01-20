package service.classes;

import java.util.ArrayList;

public class ServiceClass {

	private String name;
	private ServiceClassType type;
	private ArrayList<ServiceClass> relatedClasses;

	public ServiceClass(String name, ServiceClassType type) {
		this.name = name;
		this.type = type;
		relatedClasses = new ArrayList<ServiceClass>();
	}

	public String getName() {
		return name;
	}
	
	public ServiceClassType getType() {
		return type;
	}
	
	public static void addBidirectionalClassRelationship(ServiceClass class1, ServiceClass class2) {
		if (class1.equals(class2))
			return;
		if (!class1.relatedClasses.contains(class2))
			class1.relatedClasses.add(class2);
		if (!class2.relatedClasses.contains(class1))
			class2.relatedClasses.add(class1);
	}
	
	public int getNumRelationships() {
		return relatedClasses.size();
	}

	public boolean hasRelationship(ServiceClass aClass) {
		return relatedClasses.contains(aClass);
	}

}
