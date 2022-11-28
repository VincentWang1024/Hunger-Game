package service.stillorgan;

import service.core.AbstractFoodService;

public class StillorganService extends AbstractFoodService {

	String serviceName;

	public StillorganService(String serviceName) {
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
