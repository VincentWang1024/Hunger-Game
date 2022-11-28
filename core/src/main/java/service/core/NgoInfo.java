package service.core;

public class NgoInfo {
	private String name;
	private String licenseNumber;
	private String location;

	// constructors
	public NgoInfo(String name, String licenseNumber, String location) {
		this.name = name;
		this.licenseNumber = licenseNumber;
		this.location = location;
	}

	public NgoInfo() {

	}

	// getter and setter methods
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLicenseNumber() {
		return licenseNumber;
	}

	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}