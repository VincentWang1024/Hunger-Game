package service.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RestClient {

    public static final String JSON_TYPE = "application/json";

    public WebResponse put(String url, String type, String content) throws UnsupportedEncodingException{
        return sendRequest("PUT", url, type, content.getBytes(StandardCharsets.UTF_8));
    }

    private WebResponse sendRequest(String method, String url, String type, byte[] content){
        try{
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "MyDodgeRestClient/1.0");
            if(type != null){
                connection.setRequestProperty("Content-Type", type);
                connection.setDoOutput(true);
                connection.getOutputStream().write(content, 0, content.length);
            }
            int responseCode = connection.getResponseCode();
            String response = "";
            if(200 <= responseCode && responseCode <= 299){
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while((line = reader.readLine()) != null) response += line;
            }else{
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                String line;
                while( (line = reader.readLine()) != null ) response += line;
            }
            return new WebResponse(responseCode, response.toString());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}

