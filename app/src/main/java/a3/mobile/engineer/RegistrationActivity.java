package a3.mobile.engineer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;

public class RegistrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void onRegister(View view){

        AutoCompleteTextView eLogin = (AutoCompleteTextView)findViewById(R.id.editLogin);
        AutoCompleteTextView ePassw = (AutoCompleteTextView)findViewById(R.id.editPassword);
        AutoCompleteTextView ePin = (AutoCompleteTextView)findViewById(R.id.editPin);




        clearFields();

        SSMService ssm = new SSMService(RegistrationActivity.this, "Administrator", "Qwerty123",
                new SSMService.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        if (output != null){

                            DatabaseHepler db = new DatabaseHepler(RegistrationActivity.this, null, null, 1);
                            db.updateFilters(output);

                            //Toast.makeText(RegistrationActivity.this, output, Toast.LENGTH_LONG).show();
                        }
                    }
                });
        ssm.getFilterList();

        finish();
    }

    private void clearFields() {
        AutoCompleteTextView eLogin = (AutoCompleteTextView)findViewById(R.id.editLogin) ;
        AutoCompleteTextView ePassw = (AutoCompleteTextView)findViewById(R.id.editPassword);
        AutoCompleteTextView ePin = (AutoCompleteTextView)findViewById(R.id.editPin);
        eLogin.setText(""); ePassw.setText(""); ePin.setText("");
    }

}
