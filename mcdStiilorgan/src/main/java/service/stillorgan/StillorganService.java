package service.stillorgan;

import service.core.*;

import java.rmi.RemoteException;
import java.util.Random;

/**
 * Implementation of the AuldFellas insurance quotation service.
 * 
 * @author Rem
 *
 */
public class StillorganService extends AbstractFoodService {

	private Random random = new Random();



	@Override
	public Food makeFood(HungerInfo info) throws RemoteException {
		int productivity= random.nextInt(info.getReqQuantity());
		return new McdFood(productivity);
	}
}
