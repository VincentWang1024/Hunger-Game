package service.core;

import java.io.Serializable;

/**
 * Class to store the quotations returned by the quotation services
 * 
 * @author Rem
 *
 */

public class Quotation implements Serializable {

	private double quantity;

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	private String branch;

	public Quotation(String branch, int quantity) {
		this.quantity = quantity;
		this.branch = branch;
	}

	public Quotation() {
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Quotation{" +
				"quantity=" + quantity +
				", branch='" + branch + '\'' +
				'}';
	}
}
