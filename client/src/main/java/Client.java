import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;
import service.core.NgoInfo;

public class Client {


    public static ArrayList<String> branchNames = new ArrayList<String>();
    public static void main(String[] args) {

        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("|--------------------------------------------------------------|");

        System.out.println("");
        System.out.println("Welcome to Feed Hunger Application\n");
        Boolean flag = true;

       // RestTemplate restTemplate = new RestTemplate();
        Map<Integer, NgoInfo> cache = new HashMap<Integer,NgoInfo>();
        

       String storeName ="";

        while(flag){

            System.out.println("|--------------------------------------------------------------|");
            System.out.println("\n\n");

            System.out.println("  Please choose one of the below options");
            System.out.println("|--------------------------------------------------------------|");
            System.out.println("a. To get the overall information");
            System.out.println("b. To send the order confirmation");
            System.out.println("c. Exit");
            System.out.println("|--------------------------------------------------------------|");
            System.out.println("");


            Scanner sc = new Scanner(System.in);
            char option = sc.next().charAt(0);

            switch(option){
                
                case 'a':
                    /**
                     * API to get the information availble from all the stores
                     */
                    try {

                        URL url = new URL("http://54.216.203.127:8080/applications");

                        // Open a connection to the URL
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                        // Set the request method to GET
                        connection.setRequestMethod("GET");

                        // Set the request headers
                        connection.setRequestProperty("Content-Type", "application/json");
                        connection.setRequestProperty("Authorization", "Bearer YOUR_API_TOKEN");

                        
                            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        StringBuffer response = new StringBuffer();
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                            // System.out.println(inputLine.toString());

                        }
                        in.close();
                            

                        // Send the request and read the response
                        

                        // Parse the JSON array from the response
                        JSONArray jsonArray = new JSONArray(response.toString());
                        // Convert the JSON array into an ArrayList of JSON objects
                        ArrayList<JSONObject> list = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            list.add(jsonArray.getJSONObject(i));
                        }

                        System.out.println(" Please find the requested information from different Food Franchises");
                        // Iterate through the list and print each object
                        for (JSONObject obj : list) {
                            for (String key : obj.keySet()) {
                                if(key.equals("foodInfo")||key.equals("franchiseName")){
                                    // Get the value of each key101
                                    Object value = obj.get(key);
                                    if(value instanceof JSONArray){
                                        JSONArray nestedJsonArray = new JSONArray(value.toString());
                                        // Convert the JSON array into an ArrayList of JSON objects
                                        ArrayList<JSONObject> internalJsonList = new ArrayList<>();
                                        for (int i = 0; i < nestedJsonArray.length(); i++) {
                                            internalJsonList.add(nestedJsonArray.getJSONObject(i));
                                        }
                                        displayJasonArray(internalJsonList);
                                    }else{
                                        // Print the key and value
                                        System.out.println(key + ": " + value);
                                    }
                                }

                            }
                            System.out.println(" ");
                        }
                    } catch (Exception e) {
                            // TODO: handle exception
                        }


                    break;


                case 'b' :
                    /**
                     *  API to send the orderconfirmation
                     */
                    

                        int clientID = 101;
                        for(NgoInfo ngo : ngosInfo){
                            System.out.println("Client ID : "+clientID);
                            displayProfile(ngo);
                            cache.put(clientID,ngo);
                            clientID++;

                        }

                        System.out.println("");
                        System.out.println("");
                        System.out.println("");
                        System.out.println("Enter your Client ID");
                        int clientIdNumber = sc.nextInt();  
                        if(cache.containsKey(clientIdNumber)){
                            System.out.println("");
                            System.out.println("Enter the branchName to confirm the order");
                            storeName = sc.next();
                            if(branchNames.contains(storeName)){

                                try{
                                System.out.println("");
                                System.out.println("");
                                System.out.println("Client ID : "+clientIdNumber);
                                displayProfile(cache.get(clientIdNumber));
//                                URL url = new URL("http://localhost:8080/confirmation");
                                URL url = new URL("http://54.216.203.127:8080/confirmation");

                                // Open a connection to the URL
                                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                                // Set the request method to GET
                                connection.setRequestMethod("GET");

                                // Set the request headers
                                connection.setRequestProperty("Content-Type", "application/json");
                                connection.setRequestProperty("Authorization", "Bearer YOUR_API_TOKEN");

                                
                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                                String inputLine;
                                StringBuffer response = new StringBuffer();
                                while ((inputLine = in.readLine()) != null) {
                                    response.append(inputLine);

                                }
                                in.close();
                                    

                                // Send the request and read the response
                                

                                // Parse the JSON array from the response
                                JSONObject jsonObject = new JSONObject(response.toString());
                                // Convert the JSON array into an ArrayList of JSON objects
                                    for (String key : jsonObject.keySet()) {

                                        // Get the value of each key
                                        Object value = jsonObject.get(key);
                                    
                                        // Print the key and value
                                        System.out.println(value + " by "+storeName);
                                        }
                                        
                                    System.out.println(" ");
                            }catch(Exception e){
                                //handle it
                            }

                            }else{
                                System.out.println("Invalid Input. Please get the overall information and enter the store name correctly");
                            }

                        }else{
                            System.out.println("Invalid Client ID. Please enter the client ID correctly");
                        }
                        
                        
                       
                    break;

                case 'c' :
                    flag = false;

                    System.out.println(" ");
                    System.out.println("");
                    System.out.println("Thank you !!!");
                    System.exit(0);
                    break;

                default :
                    System.out.println("Please enter the valid input");
                    break;

            }
            
        System.out.println(" ");
        System.out.println("");
        System.out.println("Thank you !!!");

        System.out.println("");

        }
    }

    /**
     * Display a quotation nicely - note that the assumption is that the quotation will follow
     * immediately after the profile (so the top of the quotation box is missing).
     *
     * @param
     */

    public static void displayProfile(NgoInfo info) {
        System.out.println(
                "|=================================================================================================================|");
        System.out.println(
                "| Name: " + String.format("%1$-29s", info.getName()));
        System.out.println(
                "| License Number: " + String.format("%1$-19s", info.getLicenseNumber()));
        System.out.println(
                "|=================================================================================================================|");
    }
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

    public static void displayJasonArray(ArrayList<JSONObject> list){


        // Iterate through the list and print each object
        for (JSONObject obj : list) {
            for (String key : obj.keySet()) {
                // Get the value of each key
                Object value = obj.get(key);
                if(value instanceof JSONArray){
                    JSONArray nestedJsonArray = new JSONArray(value.toString());
                    // Convert the JSON array into an ArrayList of JSON objects
                    ArrayList<JSONObject> internalJsonList = new ArrayList<>();
                    for (int i = 0; i < nestedJsonArray.length(); i++) {
                        internalJsonList.add(nestedJsonArray.getJSONObject(i));
                    }
                    displayJasonArray(internalJsonList);
                }else{
                    if(key.equals("branch")){
                        // Print the key and value
                        branchNames.add(value.toString());
                        System.out.println(key + ": " + value);
                    }else{
                        // Print the key and value
                        System.out.println(key + ": " + value+" kg");
                    }
                   
                }
            }
        }
    }

}
