package service.grafton;

import service.core.*;

import java.rmi.RemoteException;

/**
 * Implementation of the AuldFellas insurance quotation service.
 * 
 * @author Rem
 *
 */
public class GraftonService extends AbstractFoodService {

	@Override
	public Food generateFood(HungerInfo info) throws RemoteException {
		int quantity = makeFood(12,50);
		return new BgkFood(quantity);
	}
}
