package com.hospital.appointment_booking.InstaCure;

//import android.annotation.NonNull;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;


public class CustomListViewAdapter extends ArrayAdapter<RowItem> {

    private Context context;

    public CustomListViewAdapter(Context context, int resourceId, List<RowItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView tv_p_name;
        TextView tv_d_name;
        TextView tv_problem;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        ViewHolder holder = null;
        RowItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            if (mInflater != null) {
                convertView = mInflater.inflate(R.layout.custom_adapter, null);

                holder = new ViewHolder();
                holder.tv_d_name = (TextView) convertView.findViewById(R.id.d_name_adapter);
                holder.tv_p_name = (TextView) convertView.findViewById(R.id.p_name_adapter);
                holder.tv_problem = (TextView) convertView.findViewById(R.id.problem_adapter);


                convertView.setTag(holder);
            }
        } else
            holder = (ViewHolder) convertView.getTag();

        if (rowItem != null) {
            if (holder != null) {
                holder.tv_d_name.setText(rowItem.getDoctor());
                holder.tv_p_name.setText(rowItem.getPatient());
                holder.tv_problem.setText(rowItem.getProblem());
            }
        }
        return convertView;
    }

}
