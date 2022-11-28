package service.jervis;

import service.core.AbstractFoodService;

public class JervisService extends AbstractFoodService {

	String serviceName;

	public JervisService(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public int generateFood() {
		return getFoodQuantity();
	}

}
