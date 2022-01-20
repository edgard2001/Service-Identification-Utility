package service.identification;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import service.Service;
import service.classes.ServiceClass;
import service.classes.ServiceClassType;


class ServiceIdentifierTest {

	@Test
	void testStep1() {
		ArrayList<ServiceClass> classes = new ArrayList<ServiceClass>();
		ServiceClass class1 = new ServiceClass("ServiceClass", ServiceClassType.CLASS);
		ServiceClass class2 = new ServiceClass("ServiceClassType", ServiceClassType.CLASS);
		ServiceClass class3 = new ServiceClass("Service", ServiceClassType.CLASS);
		ServiceClass class4 = new ServiceClass("ServiceType", ServiceClassType.CLASS);
		ServiceClass class5 = new ServiceClass("UtilMaths", ServiceClassType.CLASS);
		ServiceClass class6 = new ServiceClass("ServiceIdentifier", ServiceClassType.CLASS);
		ServiceClass.addBidirectionalClassRelationship(class1, class2);
		ServiceClass.addBidirectionalClassRelationship(class3, class1);
		ServiceClass.addBidirectionalClassRelationship(class3, class4);
		ServiceClass.addBidirectionalClassRelationship(class3, class5);
		ServiceClass.addBidirectionalClassRelationship(class6, class1);
		ServiceClass.addBidirectionalClassRelationship(class6, class3);
		classes.add(class1);
		classes.add(class2);
		classes.add(class3);
		classes.add(class4);
		classes.add(class5);
		classes.add(class6);
		ArrayList<Service> originalServices = new ArrayList<Service>();
		originalServices.add(new Service("OriginalService1", classes));
		
		ServiceIdentifier identifier = new ServiceIdentifier(originalServices);
		ArrayList<Service> services = identifier.identifyServices();
		if (services == null)
			fail("services is null");
		for (Service service : services) {
			System.out.println(service);
		}
	}

}
