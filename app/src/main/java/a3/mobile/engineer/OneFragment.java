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

    private Context context;
    private View view;

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

        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        /*for (int i=0; i<filterData.size(); i++) {
            Map<String, Object> item = filterData.get(i);
            menu.addSubMenu(0, i, i,
                    item.get(Filter.ATTRIBUTE_NAME).toString());
        }*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("onOptionsItemSelected", item.toString());

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


        //ListView listView = (ListView) view.findViewById(R.id.mainListView);


        /*SimpleAdapter adapter = new SimpleAdapter(this.getContext(), incidentListArray,
                R.layout.item_incident, new String[]{Request.COL_REQUEST_NUMBER, Request.COL_NAME},
                new int[]{R.id.itemNumber, R.id.itemDescription});
        listView.setAdapter(adapter);*/

        //listView.setOnItemClickListener(itemClickListener);
    }

}
