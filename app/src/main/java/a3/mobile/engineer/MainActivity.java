package a3.mobile.engineer;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final private int RESULT_CODE = 0;
    private static final String INC_NUMBER = "number"; // Верхний текст
    private static final String DESCRIPTION = "description"; // ниже главного
    private ArrayList<HashMap<String, Object>> incidentListArray;


    private ViewPager mViewPager;

    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // версия сборки
        String versionName = "";
        Context context = getApplicationContext();
        PackageManager packageManager = context.getPackageManager();
        String packageName = context.getPackageName();

        try {
            versionName = packageManager.getPackageInfo(packageName, 0).versionName;

        } catch (PackageManager.NameNotFoundException e) {
            versionName = "-1";
            Log.i("getPackageInfo", e.getMessage().toString());
        }


        // версия сборки в локальной БД
        DatabaseHepler db = new DatabaseHepler(this, null, null, 1);
        //String versionDB = db.getParamVal("VERSION");
        String login = db.getParamVal("Login");

        if (login == null) {
            // переход к регистрации
            Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
            startActivityForResult(intent, RESULT_CODE);
        } else {
            refreshData();
        }
    }

    private void refreshData() {

        SSMService ssm = new SSMService(MainActivity.this, "y_rykov", "123",
                new SSMService.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        if (output != null) {
                            //Toast.makeText(RegistrationActivity.this, output, Toast.LENGTH_LONG).show();
                            Log.d("REGISTARTION", "OK");

                            try {
                                JSONObject item = output.getJSONObject(0);

                                item.getString("Login");
                                item.getString("UserID");
                            } catch (JSONException e) {
                                Log.e("", e.toString());
                            }

                        } else {
                            Log.d("REGISTARTION", "FAILED");
                            String err_string = getResources().getString(R.string.error_no_data);
                            Toast.makeText(MainActivity.this, err_string, Toast.LENGTH_LONG).show();
                        }
                    }
                });
        ssm.getFilterList();

    }

    private void refreshDataHandler() {
        ListView listView = (ListView) findViewById(R.id.mainListView);

        // создаем массив списков
        incidentListArray = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> hm;

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000111"); // Название
        hm.put(DESCRIPTION, "Рыжий и хитрый"); // Описание
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000111");
        hm.put(DESCRIPTION, "Слушает да ест");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000521");
        hm.put(DESCRIPTION, "Спит и мурлыкает");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000478");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        SimpleAdapter adapter = new SimpleAdapter(this, incidentListArray,
                R.layout.item_incident, new String[]{INC_NUMBER, DESCRIPTION},
                new int[]{R.id.itemNumber, R.id.itemDescription});
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(itemClickListener);
    }

    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap<String, Object> itemHashMap = (HashMap <String, Object>) parent.getItemAtPosition(position);
            String titleItem = itemHashMap.get(INC_NUMBER).toString();
            String descriptionItem = itemHashMap.get(DESCRIPTION).toString();
            Toast.makeText(getApplicationContext(),
                    "Вы выбрали " + titleItem + ". Он " + descriptionItem, Toast.LENGTH_SHORT).show();
        }
    };





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       refreshDataHandler();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_shop) {

        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_requests) {

        } else if (id == R.id.nav_approve) {

        } else if (id == R.id.nav_change_pin) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
