package kz.sud.cabinet.util;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    public static JSONObject getJsonObject(String urlStr) throws Exception {
        return getJsonObject(urlStr, null);
    }

    public static JSONObject getJsonObject(String urlStr, List<Integer> correctStatuses) throws Exception{
        if(correctStatuses == null || correctStatuses.isEmpty()) {
            correctStatuses = new ArrayList<Integer>();
            correctStatuses.add(200);
        }

        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        int responseCode = con.getResponseCode();
        if(correctStatuses.contains(new Integer(responseCode))) {
            BufferedReader in = new BufferedReader(new InputStreamReader(responseCode == 200 ? con.getInputStream() : con.getErrorStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return new JSONObject(response.toString());
        }
        else {
            System.out.println(urlStr + " return " + responseCode);
            throw new Exception("Incorrect http response");
        }
    }
}
