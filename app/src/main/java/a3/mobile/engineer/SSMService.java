package a3.mobile.engineer;


/**
 * Created by Alexey.Babkin on 28.10.2015.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

class SSMSErvice extends AsyncTask<String, String, JSONObject> {

    JSONParser jsonParser = new JSONParser();



    private static final String API_KEY = "e8e6a311d54985a067ece5a008da280b";
    private static final String TARGET_URL = "http://rt.atrinity.ru/api/request";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private Context context;
    private String login;
    private String password;
    private ProgressDialog pDialog;
    private ProgressDialog progressDialog;

    private HashMap<String, String> params = new HashMap<>();

    public SSMSErvice(Context context, String login, String password){
        this.context=context;
        this.login=login;
        this.password=password;

        params.put("ApiKey", API_KEY);
        params.put("Login", login);
        params.put("Password", password);
    }


    public void checkConnection() {
        params.put("Action","CHECK_AUTH");
        params.put("Fields[FilterID]", "123");

        this.execute();
    }

    public void getFilterList() {

    }

    public void getList(String FilterID) {

    }

    public void getInfo(String RequestID, String RequestNumber) {

    }

    public void addNewRequest(String comment) {

    }

    public void addAttachment(String RequestID) {

    }

    public void addComment() {

    }
    public void resolve() {

    }
    public void assign(){

    }
    public void toWork(){

    }
    public void getTeamMembers() {

    }
    public void cancel() {

    }


    @Override
    protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Загрузка...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected JSONObject doInBackground(String... args) {

        try {
            Log.d("request", "starting");

            JSONObject json = jsonParser.makeHttpRequest(
                    TARGET_URL, "POST", params);

            if (json != null) {
                Log.d("JSON result", json.toString());

                return json;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(JSONObject json) {

        int success = 0;
        String message = "";

        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }

        if (json != null) {
            Toast.makeText(context, json.toString(),
                    Toast.LENGTH_LONG).show();

            try {
                success = json.getInt(TAG_SUCCESS);
                message = json.getString(TAG_MESSAGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (success == 1) {
            Log.d("Success!", message);
        }else{
            Log.d("Failure", message);
        }
    }

}
