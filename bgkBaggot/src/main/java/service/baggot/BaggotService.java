package service.baggot;

import service.core.AbstractFoodService;

public class BaggotService extends AbstractFoodService {

	String serviceName;

	public BaggotService(String serviceName) {
		this.serviceName = serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public int generateFood() {
		return getFoodQuantity();
	}

}