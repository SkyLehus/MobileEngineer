package a3.mobile.engineer;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import a3.mobile.engineer.classes.Filter;
import a3.mobile.engineer.classes.Request;


public class OneFragment extends Fragment {

    private static final String INC_NUMBER = "number"; // Верхний текст
    private static final String DESCRIPTION = "description"; // ниже главного
    //private ArrayList<HashMap<String, Object>> incidentListArray;
    private ArrayList<Map<String, Object>> incidentListArray;

    private Context context;
    private View view;
    private String mFilterID; // код ракурса

    private ArrayList<Map<String, Object>> filterData;



    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getActivity();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_one, container, false);

        // нужно для вызова события onCreateOptionsMenu
        //setHasOptionsMenu(true);

        /*ListView listView = (ListView) view.findViewById(R.id.mainListView);
        fillList(listView);*/

        loadFilterMenu();

        return view;
    }


    private void loadFilterMenu() {
        DatabaseHepler db = new DatabaseHepler(getActivity(), null, null, 1);
        String login = db.getParamVal("Login");
        String passw = db.getParamVal("Password");

        SSMService ssm = new SSMService(context, login, passw,
                new SSMService.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        if (output != null) {
                            Log.d("GET_FILTER_LIST", "OK");

                            try {
                                JSONObject item = output.getJSONObject(0);

                                if (item.has("error")) {
                                    String err_string = item.getString("error");
                                    Toast.makeText(context, err_string, Toast.LENGTH_LONG).show();
                                    return;
                                }



                                // упаковываем данные в понятную для адаптера структуру
                                filterData = new ArrayList<Map<String, Object>>(
                                        output.length());
                                Map<String, Object> m;
                                for (int i = 0; i < output.length(); i++) {

                                    // по умполчанию применить первый ракурс в списке
                                    if (i==0) mFilterID = item.getString(Filter.ATTRIBUTE_ID);

                                    item = output.getJSONObject(i);
                                    m = new HashMap<String, Object>();
                                    m.put(Filter.ATTRIBUTE_ID , item.getString(Filter.ATTRIBUTE_ID));
                                    m.put(Filter.ATTRIBUTE_NAME, item.getString(Filter.ATTRIBUTE_NAME));
                                    filterData.add(m);
                                }


                                // массив имен атрибутов, из которых будут читаться данные
                                String[] from = { Filter.ATTRIBUTE_ID, Filter.ATTRIBUTE_NAME};

                                // нужно для вызова события onCreateOptionsMenu
                                setHasOptionsMenu(true);

                                refresh();

                            } catch (JSONException e) {
                                Log.e("", e.toString());
                                String errMessage = e.getMessage().toString();
                                Toast.makeText(getActivity(), errMessage, Toast.LENGTH_LONG).show();
                            }

                        } else {
                            String error_no_filter_data = getResources().getString(R.string.error_no_filter_data);
                            Toast.makeText(getActivity(), error_no_filter_data, Toast.LENGTH_LONG).show();
                        }
                    }
                });


        ssm.getFilterList(SSMService.TARGET_OBJECT_REQUEST);
    }


    private void refresh() {
        DatabaseHepler db = new DatabaseHepler(getActivity(), null, null, 1);
        String login = db.getParamVal("Login");
        String passw = db.getParamVal("Password");

        SSMService ssm = new SSMService(context, login, passw,
                new SSMService.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        if (output != null) {
                            Log.d("GET_LIST", "OK");

                            try {
                                JSONObject item = output.getJSONObject(0);

                                if (item.has("error")) {
                                    String err_string = item.getString("error");
                                    Toast.makeText(context, err_string, Toast.LENGTH_LONG).show();
                                    return;
                                }

                                // упаковываем данные в понятную для адаптера структуру
                                incidentListArray = new ArrayList<Map<String, Object>>(
                                        output.length());
                                Map<String, Object> m;
                                for (int i = 0; i < output.length(); i++) {
                                    item = output.getJSONObject(i);
                                    m = new HashMap<String, Object>();
                                    m.put(Request.COL_REQUEST_NUMBER , item.getString(Request.COL_REQUEST_NUMBER));
                                    m.put(Request.COL_NAME, item.getString(Request.COL_NAME));
                                    incidentListArray.add(m);
                                }

                                fillList();
                                // массив имен атрибутов, из которых будут читаться данные
                                //String[] from = { Filter.ATTRIBUTE_ID, Filter.ATTRIBUTE_NAME};

                            } catch (JSONException e) {
                                Log.e("", e.toString());
                                String errMessage = e.getMessage().toString();
                                Toast.makeText(getActivity(), errMessage, Toast.LENGTH_LONG).show();
                            }

                        } else {
                            String error_no_filter_data = getResources().getString(R.string.error_no_filter_data);
                            Toast.makeText(getActivity(), error_no_filter_data, Toast.LENGTH_LONG).show();
                        }
                    }
                });


        ssm.getList(SSMService.TARGET_OBJECT_REQUEST, mFilterID);

    }

    private void applyFilter(int filterID) {
        Map<String, Object> item = filterData.get(filterID);
        mFilterID = item.get(Filter.ATTRIBUTE_ID).toString();
        refresh();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        for (int i=0; i<filterData.size(); i++) {
            Map<String, Object> item = filterData.get(i);
            menu.addSubMenu(0, i, i,
                    item.get(Filter.ATTRIBUTE_NAME).toString());
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("onOptionsItemSelected", item.toString());
        applyFilter(item.getItemId());
        return super.onOptionsItemSelected(item);
    }

    private void fillList() {
        //ListView listView = (ListView) findViewById(R.id.mainListView);
        // Inflate the layout for this fragment




        // создаем массив списков
        /*incidentListArray = new ArrayList<Map<String, Object>>();
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

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000450");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000451");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000454");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000578");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000558");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000558");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000558");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000558");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000558");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000558");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000558");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);

        hm = new HashMap<>();
        hm.put(INC_NUMBER, "INC000000558");
        hm.put(DESCRIPTION, "Болеет за Барселону");
        incidentListArray.add(hm);*/


        ListView listView = (ListView) view.findViewById(R.id.mainListView);


        SimpleAdapter adapter = new SimpleAdapter(this.getContext(), incidentListArray,
                R.layout.item_incident, new String[]{Request.COL_REQUEST_NUMBER, Request.COL_NAME},
                new int[]{R.id.itemNumber, R.id.itemDescription});
        listView.setAdapter(adapter);

        //listView.setOnItemClickListener(itemClickListener);
    }

}
