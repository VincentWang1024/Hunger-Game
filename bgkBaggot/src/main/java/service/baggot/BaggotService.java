package service.baggot;

import service.core.*;

import java.rmi.RemoteException;

/**
 * Implementation of the AuldFellas insurance quotation service.
 * 
 * @author Rem
 *
 */
public class BaggotService extends AbstractFoodService {

	@Override
	public Food generateFood(HungerInfo info) throws RemoteException {
		int quantity = makeFood(2,50);
		return new BgkFood(quantity);
	}
}
