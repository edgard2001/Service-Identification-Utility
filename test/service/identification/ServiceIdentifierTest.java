package service.identification;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import service.Service;
import service.classes.ServiceClass;
import service.classes.ServiceClassType;


class ServiceIdentifierTest {

	@Test
	void test1() {
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
		
		ServiceIdentifier identifier = new ServiceIdentifier(originalServices, 0.6f);
		ArrayList<Service> services = identifier.identifyServices();
		if (services == null)
			fail("services is null");
		for (Service service : services) {
			System.out.println(service);
		}
	}
	
	@Test
	void test2() {
		ArrayList<ServiceClass> classes = new ArrayList<ServiceClass>();
		ServiceClass class1 = new ServiceClass("ObjectMovementController", ServiceClassType.CLASS);
		ServiceClass class2 = new ServiceClass("SetUpTestBuckets", ServiceClassType.CLASS);
		ServiceClass class3 = new ServiceClass("BucketOrganization", ServiceClassType.CLASS);
		ServiceClass class4 = new ServiceClass("BucketManagement", ServiceClassType.CLASS);
		ServiceClass class5 = new ServiceClass("Bucket", ServiceClassType.CLASS);
		ServiceClass class6 = new ServiceClass("MyObject", ServiceClassType.CLASS);
		ServiceClass.addBidirectionalClassRelationship(class1, class6);
		ServiceClass.addBidirectionalClassRelationship(class2, class4);
		ServiceClass.addBidirectionalClassRelationship(class2, class5);
		ServiceClass.addBidirectionalClassRelationship(class3, class4);
		ServiceClass.addBidirectionalClassRelationship(class4, class5);
		ServiceClass.addBidirectionalClassRelationship(class5, class6);
		classes.add(class1);
		classes.add(class2);
		classes.add(class3);
		classes.add(class4);
		classes.add(class5);
		classes.add(class6);
		ArrayList<Service> originalServices = new ArrayList<Service>();
		originalServices.add(new Service("OriginalService1", classes));
		
		ServiceIdentifier identifier = new ServiceIdentifier(originalServices, 0.6f);
		ArrayList<Service> services = identifier.identifyServices();
		if (services == null)
			fail("services is null");
		for (Service service : services) {
			System.out.println(service);
		}
	}

}
