package applica.feneal.services.skebby;

import applica.feneal.domain.model.utils.skebby.ParametricSkebbySmsConfiguration;
import applica.feneal.domain.model.utils.skebby.SkebbyParamtericRecipient;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class SkebbyNewApiSmsSender {


    private String[] authenticateUserKeyAndToken(String username, String Password) throws Exception {
        String[] userData = new String[0];
        try {
            URL url = new URL("https://api.skebby.it/API/v1.0/REST/login?username="+username+"&password="+Password+"");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                // Print the possible error contained in body response
                String error = "";
                String output;
                BufferedReader errorbuffer = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                while ((output = errorbuffer.readLine()) != null) {
                    error += output;
                }
                System.out.println("Error! HTTP error code : " + conn.getResponseCode() +
                        ", Body message : " + error);
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String response = "";
            String output;
            while ((output = br.readLine()) != null) {
                response += output;
            }
            userData = response.split(";");
            String user_key = userData[0];
            String access_token = userData[1];
            System.out.println("user_key: " + user_key);
            System.out.println("Access_token: " + access_token);
            conn.disconnect();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        return userData;
    }


    public void sendSms(String username, String password, ParametricSkebbySmsConfiguration configuration) throws Exception {

        String[] authData = authenticateUserKeyAndToken(username,password);

        try{
            URL url = new URL("https://api.skebby.it/API/v1.0/REST/paramsms");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestProperty("user_key", authData[0]);
            // Use this when using Session Key authentication
            conn.setRequestProperty("Session_key", authData[1]);


            conn.setRequestMethod("POST");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoOutput(true);


            ObjectMapper dd = new ObjectMapper();
            String payload = dd.writeValueAsString(configuration);



            OutputStream os = conn.getOutputStream();
            os.write(payload.getBytes());
            os.flush();

            if (conn.getResponseCode() != 201) {
                // Print the possible error contained in body response
                String error = "";
                String output;
                BufferedReader errorbuffer = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                while ((output = errorbuffer.readLine()) != null) {
                    error += output;
                }
                System.out.println("Error! HTTP error code : " + conn.getResponseCode() +
                        ", Body message : " + error);
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br =
                    new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String response = "";
            String output;
            while ((output = br.readLine()) != null) {
                response += output;
            }

            ObjectMapper f = new ObjectMapper();

            conn.disconnect();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


}
