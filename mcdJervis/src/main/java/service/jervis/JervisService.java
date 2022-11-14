package service.jervis;

import service.core.*;

import java.rmi.RemoteException;

/**
 * Implementation of the AuldFellas insurance quotation service.
 * 
 * @author Rem
 *
 */
public class JervisService extends AbstractFoodService {

	@Override
	public Food generateFood(HungerInfo info) throws RemoteException {
		int quantity = makeFood(10,50);
		return new McdFood(quantity);
	}
}
