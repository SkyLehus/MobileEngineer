package a3.mobile.engineer;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import a3.mobile.engineer.classes.Request;


class SSMService extends AsyncTask<String, String, JSONArray> {


    public interface AsyncResponse {
        void processFinish(JSONArray output);
    }

    public AsyncResponse delegate = null;

    JSONParser jsonParser = new JSONParser();



    private static final String API_KEY = "e8e6a311d54985a067ece5a008da280b";
    private static final String TARGET_URL = "http://avb.a3ssm.ru/api/"; //"http://beta.a3ssm.ru/api/"; // "http://rt.atrinity.ru/api/request"; //"http://avb.a3ssm.ru/api/request";//
    public static final String TARGET_OBJECT_REQUEST = "request";

    private String mTargetObject;


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


        pDialog = new ProgressDialog(context);
        pDialog.setMessage(progressMessage);
        //pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
    }


    public void checkConnection() {
        mTargetObject = TARGET_OBJECT_REQUEST;
        params.put("Action","CHECK_AUTH");
        params.put("Fields[FilterID]", "123");
        this.execute();
    }

    public void getFilterList(String targetObject) {
        mTargetObject = targetObject;
        params.put("Action", "GET_FILTER_LIST");
        params.put("ObjectCode", "300");
        this.execute();
    }

    public void getList(String targetObject, String FilterID) {
        mTargetObject = targetObject;
        params.put("Action", "GET_LIST");
        params.put("Fields[FilterID]", FilterID);
        params.put("Columns", Request.getListColumns());
        this.execute();
    }

    public void getInfo(String targetObject, String RequestID, String RequestNumber) {
        // TODO: реализация метода получения детальной информации по объекту (заявки)
    }

    public void addNewRequest(String comment) {
        // TODO: реализация метода создания нового объекта (заявки)
    }

    public void addAttachment(String RequestID) {

    }

    public void addComment() {
        // TODO: реализация метода добавления комментария к объекту (заявке)
    }
    public void resolve() {
        // TODO: реализация метода решения заявки
    }
    public void assign(){
        // TODO: реализация метода назначения группы/исполнителя заявки
    }
    public void toWork(){
        // TODO: реализация метода изменения статуса заявки "В работе"
    }
    public void getTeamMembers() {
        // TODO: реализация метода получпения списка состава группы исполнителей по заявке
    }
    public void cancel() {
        // TODO: реализация метода отмены заявки
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d("SSMSERVICE", "onPreExecute");


        //pDialog.show();
    }

    @Override @Nullable
    protected JSONArray doInBackground(String... args) {

        try {

            Log.d("SSMSERVICE","doInBackground");
            Log.d("SSMSERVICE","Start gettting data");

            JSONArray json = jsonParser.makeHttpRequest(
                    TARGET_URL + mTargetObject, "POST", params);
            Log.d("SSMSERVICE","Gettting data ends");

            if (json != null) {
                Log.d("JSON result", json.toString());
                return json;
            }

        } catch (Exception e) {
            JSONArray errResult = JSONParser.makeErrResponse(e.toString());
            Log.d("JSON Exception", e.toString());
            e.printStackTrace();
            return errResult;
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
        Log.d("SSMSERVICE", "Progress update " + Arrays.toString(values));
    }

    protected void hideProgress() {
        if (pDialog != null && pDialog.isShowing()) {
            // !!! ПРИЛОЖЕНИЕ ПАДАЕТ ПРИ ВЫЗОВЕ dismiss, ДИАЛОГ ЗАКРЫВАЕТСЯ САМ, КАК ТОЛЬКО ЗАВЕРШИТСЯ ASYNCTASK
            pDialog.dismiss();
            // !!!
            Log.d("SSMSERVICE", "Progress dialog finished");
        }
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
