package a3.mobile.engineer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

public class JSONParser {

    String charset = "UTF-8";
    HttpURLConnection conn;
    DataOutputStream wr;
    StringBuilder result = new StringBuilder();
    URL urlObj;
    JSONObject jObj = null;
    JSONArray jArr = null;
    StringBuilder sbParams;
    String paramsString;
    String errMessage;
    Boolean bErr = false;

    public JSONArray makeHttpRequest(String url, String method,
                                      HashMap<String, String> params) {

        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0){
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();
            }
            i++;
        }

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(5000);

                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                errMessage = e.getMessage().toString();
                return makeErrResponse(errMessage);
            }
        }
        else if(method.equals("GET")){
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);

                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setConnectTimeout(15000);

                conn.connect();

            } catch (IOException e) {
                return makeErrResponse(errMessage);
            }

        }

        try {
            //Receive the response from the server
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            Log.d("JSON Parser", "result: " + result.toString());

        } catch (IOException e) {
            bErr = true;
            errMessage = e.getMessage();
            e.printStackTrace();
        }

        conn.disconnect();

        Boolean bArrErr = false;
        // try parse the string to a JSON object
        try {
            jArr = new JSONArray(result.toString());
        } catch (JSONException e) {
            bErr = true;
            errMessage = e.getMessage();
            Log.e("JSON Parser", "Error parsing array " + e.toString());
            e.printStackTrace();
        }

        if (bErr == true) {
                Log.d("JSON Parser", "ERROR");
                return makeErrResponse(errMessage);
        }

        Log.d("JSON Parser", "COMPLETE");
        // return JSON Array
        return jArr;
    }

    protected JSONArray makeErrResponse(String errMeessage) {
        // вернуть статус из описание ошибки
        JSONArray jArr = new JSONArray();
        JSONObject jErr = new JSONObject();
        try {
            jErr.put("error", errMessage);
            jArr.put(jErr);
            return jArr;
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data from result " + e.toString());
            e.printStackTrace();
        }
        return null;
    }
}
