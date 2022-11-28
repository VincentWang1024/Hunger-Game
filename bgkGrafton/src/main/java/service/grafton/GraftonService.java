package service.grafton;

import service.core.AbstractFoodService;

/**
 * Implementation of the AuldFellas insurance quotation service.
 * 
 * @author Rem
 *
 */
public class GraftonService extends AbstractFoodService {

	String serviceName;

	public GraftonService(String serviceName) {
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
