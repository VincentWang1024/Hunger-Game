import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import service.core.Franchise;
import service.core.NgoInfo;
import service.messages.NgoRequest;

public class NgoClient {

    public static void main(String[] args) {

		RestTemplate restTemplate = new RestTemplate();
		for(NgoInfo ngo : ngosInfo){
  
            HttpEntity<NgoInfo> request = new HttpEntity<>(ngo);
			NgoRequest ngoReq = restTemplate.postForObject("http://localhost:8000/ngofoodrequest",request, NgoRequest.class);
			displayProfile(ngo);
            for(Franchise f : ngoReq.getFranchises()){
                System.out.println("Franchise Name : "+ f.getFranchiseName());
                System.out.println("Branch Name : "+ f.getFranchiseBranchName()); 
                System.out.println("Food Available : " + f.getTotalBranchFood());
                
            }
            System.out.println("/n");
		}

        //client : name
        //should have foloowing things
        //1. MCD - Branch Name - Ammount left in the branch
        //2. Dominos -Branch Name - Ammount left in the branch 
		

	}

    	/**
	 * Display a quotation nicely - note that the assumption is that the quotation will follow
	 * immediately after the profile (so the top of the quotation box is missing).
	 * 
	 * @param quotation
	 */
	// public static void displayResult(FoodResponse response) {
	// 	System.out.println(
	// 			"| Company: " + String.format("%1$-26s", response.getQuantity()) );
	// 	System.out.println("|=================================================================================================================|");
	// }

    public static void displayProfile(NgoInfo info) {
                System.out.println(
                                "|=================================================================================================================|");
                System.out.println(
                                "|                                     |                                     |                                     |");
                System.out.println(
                                "| Name: " + String.format("%1$-29s", info.getName()));
                System.out.println(
                                "| License Number: " + String.format("%1$-19s", info.getLicenseNumber()));
                System.out.println(
                                "|                                     |                                     |                                     |");
                System.out.println(
                                "|=================================================================================================================|");
        }
// this.name = name;
// 		this.licenseNumber = licenseNumber;
    /**
	 * Test Data
	 */
	public static final NgoInfo[] ngosInfo = {
		new NgoInfo("Irish Refugee Council","PQR254/1", "D01 NX74"),
        new NgoInfo("National Youth Council of Ireland", "PQR254/2", "D02 V327"),
        new NgoInfo("Amnesty International Ireland","PQR254/3","D02 T883"),
        new NgoInfo("Volunteer Ireland", "PQR254/4", "D02 HW77"),
        new NgoInfo("Immigrant Council of Ireland", "PQR254/5", "D07 XN29")		
	};
    
}