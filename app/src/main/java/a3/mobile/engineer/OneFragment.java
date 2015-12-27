package a3.mobile.engineer;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ActionMode;
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

import a3.mobile.engineer.MainActivity;
import a3.mobile.engineer.R;
import a3.mobile.engineer.SSMService;


public class OneFragment extends Fragment {

    private static final String INC_NUMBER = "number"; // Верхний текст
    private static final String DESCRIPTION = "description"; // ниже главного
    private ArrayList<HashMap<String, Object>> incidentListArray;

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

        ListView listView = (ListView) view.findViewById(R.id.mainListView);
        fillList(listView);

        loadFilterMenu();

        return view;
    }


    private void loadFilterMenu() {
        SSMService ssm = new SSMService(context, "y_rykov", "123",
                new SSMService.AsyncResponse() {
                    @Override
                    public void processFinish(JSONArray output) {
                        if (output != null) {
                            Log.d("REGISTARTION", "OK");

                            try {
                                JSONObject item = output.getJSONObject(0);

                                if (item.has("error")) {
                                    String err_string = item.getString("error");
                                    Toast.makeText(context, err_string, Toast.LENGTH_LONG).show();
                                    return;
                                }



                                // упаковываем данные в понятную для адаптера структуру
                                ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                                        output.length());
                                Map<String, Object> m;
                                for (int i = 0; i < output.length(); i++) {
                                    item = output.getJSONObject(i);
                                    m = new HashMap<String, Object>();
                                    m.put(Filter.ATTRIBUTE_ID , item.getString(Filter.ATTRIBUTE_ID));
                                    m.put(Filter.ATTRIBUTE_NAME, item.getString(Filter.ATTRIBUTE_NAME));
                                    data.add(m);
                                }


                                // массив имен атрибутов, из которых будут читаться данные
                                String[] from = { Filter.ATTRIBUTE_ID, Filter.ATTRIBUTE_NAME};




                                //getActivity().getMenuInflater().inflate(R.menu.menu_fragment, menu);

                                //Menu filterMenu = (Menu) view.getCo findViewById(R.id. );
                                /*// массив ID View-компонентов, в которые будут вставлять данные
                                int[] to = { R.id.tvText, R.id.cbChecked, R.id.ivImg };

                                // создаем адаптер
                                SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item,
                                        from, to);

                                // определяем список и присваиваем ему адаптер
                                lvSimple = (ListView) findViewById(R.id.lvSimple);
                                lvSimple.setAdapter(sAdapter);*/

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        //inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);

        //public MenuItem add(int groupId, int itemId, int order, CharSequence title);
        menu.add(0, 0, 0,
                "Фрагмент один меню 1")
                //.setIcon(R.drawable.your_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add(0, 1, 1,
                "Фрагмент один меню 2")
                //.setIcon(R.drawable.your_icon)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    }

    private void fillList(ListView listView) {
        //ListView listView = (ListView) findViewById(R.id.mainListView);
        // Inflate the layout for this fragment




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
        incidentListArray.add(hm);


        SimpleAdapter adapter = new SimpleAdapter(this.getContext(), incidentListArray,
                R.layout.item_incident, new String[]{INC_NUMBER, DESCRIPTION},
                new int[]{R.id.itemNumber, R.id.itemDescription});
        listView.setAdapter(adapter);

        //listView.setOnItemClickListener(itemClickListener);
    }

}
