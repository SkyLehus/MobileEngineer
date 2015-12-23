package layout;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import a3.mobile.engineer.R;


public class OneFragment extends Fragment {

    private static final String INC_NUMBER = "number"; // Верхний текст
    private static final String DESCRIPTION = "description"; // ниже главного
    private ArrayList<HashMap<String, Object>> incidentListArray;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);

        ListView listView = (ListView) view.findViewById(R.id.mainListView);
        fillList(listView);

        return view;
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

        SimpleAdapter adapter = new SimpleAdapter(this.getContext(), incidentListArray,
                R.layout.item_incident, new String[]{INC_NUMBER, DESCRIPTION},
                new int[]{R.id.itemNumber, R.id.itemDescription});
        listView.setAdapter(adapter);

        //listView.setOnItemClickListener(itemClickListener);
    }

}
