package a3.mobile.engineer;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

/**
 * Created by localadmin on 14.01.2016.
 */
public class IncidentListAdapter extends SimpleAdapter {

    final Context mContext;
    private static LayoutInflater mInflater = null;
    final  List<? extends Map<String, ?>> mData;

    public IncidentListAdapter(Context context, List<? extends Map<String, ?>> data,
                               @LayoutRes int resource, String[] from, @IdRes int[] to) {
        super(context,data,resource,from,to);
        mContext = context;
        mInflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mData = data;
    }

    @Override
    public View getView (int position, View view, ViewGroup parent) {

/*
        LayoutInflater inflater=
                ((LayoutInflater) mContext).getLayoutInflater();
        View row=inflater.inflate(resource,parent,false);*/


        View rowView = mInflater.inflate(R.layout.item_incident, null);

        TextView itemPriority = (TextView) rowView.findViewById(R.id.itemPriority);
        //Drawable drw = itemPriority.getBackground();


        String priority = itemPriority.getText().toString();
        itemPriority.setBackgroundResource(R.drawable.circle_priority_2);

        /*if (priority == "1") itemPriority.setBackgroundResource(R.drawable.circle_priority_1);
        if (priority == "2") itemPriority.setBackgroundResource(R.drawable.circle_priority_2);
        if (priority == "3") itemPriority.setBackgroundResource(R.drawable.circle_priority_3);
        if (priority == "4") itemPriority.setBackgroundResource(R.drawable.circle_priority_4);

        itemPriority.setText( [position]);*/

        return super.getView(position, rowView, parent);
    }

}
