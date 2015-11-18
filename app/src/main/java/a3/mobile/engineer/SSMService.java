package a3.mobile.engineer;


/**
 * Created by Alexey.Babkin on 28.10.2015.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


class SSMService extends AsyncTask<String, String, JSONArray> {


    public interface AsyncResponse {
        void processFinish(JSONArray output);
    }

    public AsyncResponse delegate = null;

    JSONParser jsonParser = new JSONParser();



    private static final String API_KEY = "e8e6a311d54985a067ece5a008da280b";
    private static final String TARGET_URL = "http://rt.atrinity.ru/api/request";

    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private Context context;
    private String login;
    private String password;
    private ProgressDialog pDialog;
    public String progressMessage = "Загрузка...";

    private HashMap<String, String> params = new HashMap<>();

    public SSMService (Context context, String login, String password, AsyncResponse delegate){

        this.delegate = delegate;

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
        params.put("Action", "GET_FILTER_LIST");
        this.execute();
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
        super.onPreExecute();
        Log.d("SSMSERVICE","onPreExecute");
        pDialog = new ProgressDialog(context);
        pDialog.setMessage(progressMessage);
        //pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    @Override
    protected JSONArray doInBackground(String... args) {

        try {
            Log.d("SSMSERVICE","doInBackground");

            JSONArray json = jsonParser.makeHttpRequest(
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

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d("SSMSERVICE", "Progress update " + values.toString());
    }

    protected void hideProgress() {
        //if (pDialog != null && pDialog.isShowing()) {
            // !!! ПРИЛОЖЕНИЕ ПАДАЕТ ПРИ ВЫЗОВЕ dismiss, ДИАЛОГ ЗАКРЫВАЕТСЯ САМ, КАК ТОЛЬКО ЗАВЕРШИТСЯ ASYNCTASK
            // pDialog.dismiss();
            // !!!
            Log.d("SSMSERVICE", "Progress dialog finished");
        //}
    }

    @Override
    protected void onPostExecute(JSONArray json) {
        super.onPostExecute(json);

        Log.d("SSMSERVICE","onPostExecute");

        int success = 0;
        String message = "";

        hideProgress();

        if (json != null) {
            /*Toast.makeText(context, json.toString(),
                    Toast.LENGTH_LONG).show();*/

            /*try {
                //success = json.getInt(TAG_SUCCESS);
                //message = json.getString(TAG_MESSAGE);
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            Log.d("SSMSERVICE", "json exist");
            delegate.processFinish(json);

        } else {
            Log.d("SSMSERVICE", "NULL");
            delegate.processFinish(null);
        }

        /*if (success == 1) {
            Log.d("SSMService Success!", message);
            delegate.processFinish(json.toString());
        }else{
            Log.d("SSMService Failure", message);
            delegate.processFinish(json.toString());
        }*/
    }

}
