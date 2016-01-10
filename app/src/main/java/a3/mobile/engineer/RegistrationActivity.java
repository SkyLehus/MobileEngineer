package a3.mobile.engineer;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegistrationActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void onRegister(View view) {

        AutoCompleteTextView eLogin = (AutoCompleteTextView) findViewById(R.id.editLogin);
        AutoCompleteTextView ePassw = (AutoCompleteTextView) findViewById(R.id.editPassword);
        AutoCompleteTextView ePin = (AutoCompleteTextView) findViewById(R.id.editPin);


        String mLogin = eLogin.getText().toString();
        String mPassw = ePassw.getText().toString();

        SSMService ssm = new SSMService(RegistrationActivity.this, mLogin, mPassw,
                new SSMService.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        if (output != null) {
                            Log.d("REGISTARTION", "OK");

                            try {
                                JSONObject item = output.getJSONObject(0);

                                if (item.has("error")) {
                                    String err_string = item.getString("error");
                                    Toast.makeText(RegistrationActivity.this, err_string, Toast.LENGTH_LONG).show();
                                    return;
                                }

                                // Успешная регистрация, сохраняем логин в локальную базу
                                DatabaseHepler db = new DatabaseHepler(RegistrationActivity.this, null, null, 1);
                                db.setParamVal("Login", item.getString("Login"));
                                db.setParamVal("UserID", item.getString("UserID"));

                                // TODO: сделать шифрование пароля по ПИН коду
                                AutoCompleteTextView ePassw = (AutoCompleteTextView) findViewById(R.id.editPassword);
                                AutoCompleteTextView ePin = (AutoCompleteTextView) findViewById(R.id.editPin);
                                db.setParamVal("Password", ePassw.getText().toString());

                            } catch (JSONException e) {
                                Log.e("REGISTARTION FAILED", e.toString());
                                e.printStackTrace();
                                String err_string = e.getMessage().toString();
                                Toast.makeText(RegistrationActivity.this, err_string, Toast.LENGTH_LONG).show();
                                return;
                            }

                            // очистка введенных данных
                            clearFields();

                            finish();
                        } else {
                            Log.d("REGISTARTION FAILED", "NO DATA");
                            String no_data_string = getResources().getString(R.string.error_login_failed);
                            Toast.makeText(RegistrationActivity.this, no_data_string, Toast.LENGTH_LONG).show();
                        }
                    }
                });

        ssm.checkConnection();
    }

    private void clearFields() {
        AutoCompleteTextView eLogin = (AutoCompleteTextView) findViewById(R.id.editLogin);
        AutoCompleteTextView ePassw = (AutoCompleteTextView) findViewById(R.id.editPassword);
        AutoCompleteTextView ePin = (AutoCompleteTextView) findViewById(R.id.editPin);
        eLogin.setText("");
        ePassw.setText("");
        ePin.setText("");
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Registration Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                //Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://a3.mobile.engineer/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Registration Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
               // Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://a3.mobile.engineer/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
