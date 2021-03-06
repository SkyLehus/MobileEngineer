package a3.mobile.engineer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

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
        finish();
    }

    private void clearFields() {
        AutoCompleteTextView eLogin = (AutoCompleteTextView)findViewById(R.id.editLogin) ;
        AutoCompleteTextView ePassw = (AutoCompleteTextView)findViewById(R.id.editPassword);
        AutoCompleteTextView ePin = (AutoCompleteTextView)findViewById(R.id.editPin);
        eLogin.setText(""); ePassw.setText(""); ePin.setText("");
    }

}
