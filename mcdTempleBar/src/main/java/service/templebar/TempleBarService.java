package service.templebar;

import service.core.AbstractFoodService;

public class TempleBarService extends AbstractFoodService {

	String serviceName;

	public TempleBarService(String serviceName) {
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
