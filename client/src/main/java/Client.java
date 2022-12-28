//import exception.GlobalErrorCode;
//import exception.InvalidLicenseNoException;
//import exception.InvalidUserNameException;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;
import service.core.ClientAppliation;
import service.core.ClientInfo;
import service.core.Quotation;

import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        for (ClientInfo client: clients) {
            // exception check
            Pattern pattern = Pattern.compile("^[A-Z]{3}\\d{3}\\/\\d{1}$");
            Matcher matcher = pattern.matcher(client.getLicenseNumber());
            //invalid license number check!
//            if(!matcher.find()) throw new InvalidLicenseNoException("incorrect license number!", GlobalErrorCode.ERROR_INVALID_LicenseNo);
            //invalid username check!
//            if(client.getName() == "") throw new InvalidUserNameException("illegal username!", GlobalErrorCode.ERROR_INVALID_NAME);

            HttpEntity request = new HttpEntity<>(null);
            ClientAppliation clientAppliation =
                    restTemplate.postForObject("http://localhost:8087/applications",
                            request, ClientAppliation.class);
            System.out.println(clientAppliation.getApplicationNumber());
            clientAppliation.getList().forEach(e ->
                    System.out.println(e.getQuantity())
        );
        }
    }


    /**
     * Display the client info nicely.
     *
     * @param info
     */
    public static void displayProfile(ClientInfo info) {
        System.out.println("|=================================================================================================================|");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println(
                "| Name: " + String.format("%1$-29s", info.getName()) +
                        " | Gender: " + String.format("%1$-27s", (info.getGender()==ClientInfo.MALE?"Male":"Female")) +
                        " | Age: " + String.format("%1$-30s", info.getAge())+" |");
        System.out.println(
                "| License Number: " + String.format("%1$-19s", info.getLicenseNumber()) +
                        " | No Claims: " + String.format("%1$-24s", info.getNoClaims()+" years") +
                        " | Penalty Points: " + String.format("%1$-19s", info.getPoints())+" |");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Display a quotation nicely - note that the assumption is that the quotation will follow
     * immediately after the profile (so the top of the quotation box is missing).
     *
     * @param quotation
     */
//    public static void displayQuotation(Quotation quotation) {
//        System.out.println(
//                "| Company: " + String.format("%1$-26s", quotation.getCompany()) +
//                        " | Reference: " + String.format("%1$-24s", quotation.getReference()) +
//                        " | Price: " + String.format("%1$-28s", NumberFormat.getCurrencyInstance().format(quotation.getQuantity()))+" |");
//        System.out.println("|=================================================================================================================|");
//    }

    /**
     * Test Data
     */
    public static final ClientInfo[] clients = {
            //test data-> null name
//            new ClientInfo("", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"),
            //test data-> error license number
//            new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/l"),
            new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"),
            new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 0, 2, "ABC123/4"),
            new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 16, 10, 0, "HMA304/9"),
            new ClientInfo("Rem Collier", ClientInfo.MALE, 44, 5, 3, "COL123/3"),
            new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 4, 7, "QUN987/4"),
            new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 5, 2, "XYZ567/9")
    };
}
