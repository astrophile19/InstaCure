package com.hospital.appointment_booking.InstaCure.Desktop_Admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hospital.appointment_booking.InstaCure.CustomListViewAdapter;
import com.hospital.appointment_booking.InstaCure.DatabaseHelper;
import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.R;
import com.hospital.appointment_booking.InstaCure.RowItem;

import java.util.ArrayList;
import java.util.List;


public class Grant_appointment extends AppCompatActivity {

    ListView lv_appointment;
    List<String> u_p;
    List<String> p_p;
    List<RowItem> rowItems;
    ArrayList<String> doc = new ArrayList<>();
    ArrayList<String> pat = new ArrayList<>();
    ArrayList<String> pro = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grant_appointment);

        lv_appointment = (ListView) findViewById(R.id.lv_pending_appontments);
        u_p = new ArrayList<>();
        p_p = new ArrayList<>();

        final DatabaseHelper dbh = new DatabaseHelper(this);
        Cursor y = dbh.checkduplicates_in_user_credentials("", "", getResources().getString(R.string.all_pending_appointment));

        if (y!=null && y.moveToFirst()) {
            Log.e("In Grantymovefirst", "looping");
            while (true) {

                //pateinet approvl has three mode W - wait, A - approved, F - finished

                if (y.getString(4).equals("W")) {
                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y.getString(0), y.getString(1), getResources().getString(R.string.user_credentials));
                    DatabaseHelper dbh2 = new DatabaseHelper(this);
                    Cursor z2 = dbh2.checkduplicates_in_user_credentials(y.getString(2), y.getString(3), getResources().getString(R.string.user_credentials));
                    u_p.add(y.getString(0));
                    p_p.add(y.getString(1));

                    if (z1.moveToNext()) {
                        pat.add(z1.getString(1) );
                    }

                    if (z2.moveToNext()) {
                        doc.add(z2.getString(1));
                    }
                    pro.add(y.getString(5));

                    dbh1.close();
                    dbh2.close();
                }

                if (y.isLast())
                    break;

                y.moveToNext();
            }

            rowItems = new ArrayList<>();

                for (int i = 0; i < doc.size(); i++) {
                    RowItem item = new RowItem(doc.get(i), pat.get(i), pro.get(i));
                    rowItems.add(item);
                }

                CustomListViewAdapter adapter = new CustomListViewAdapter(this, R.layout.custom_adapter, rowItems);
                lv_appointment.setAdapter(adapter);
            } else {
                Message.message(Grant_appointment.this, "No Pending Apppointments");
                finish();
            }

        lv_appointment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor x = dbh.checkduplicates_in_user_credentials(u_p.get(position), p_p.get(position), pro.get(position));
                boolean y = false;
                if (x!=null && x.moveToFirst()) {
                    y = dbh.update_doctor_patient(x.getString(0), x.getString(1), x.getString(2), x.getString(3), "A", x.getString(5), x.getString(6), x.getString(7));
                }

                if (y) {
                    Message.message(Grant_appointment.this, "Application Approved");
                    finish();
                } else {
                    Message.message(Grant_appointment.this, "Not Approved");
                }
            }
        });
    }

    //LOGOUT  FROM ADMIN  | MAINMENU//////////
    @Override
    public  boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        MenuInflater inflater1 =getMenuInflater();
        inflater1.inflate(R.menu.menu_backtomain,menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_logout:
                startActivity(new Intent(Grant_appointment.this, Login.class));
                Message.message(Grant_appointment.this,"Logging Out");
                return true;
            case  R.id.menu_mainmenu:
                startActivity(new Intent(Grant_appointment.this, Admin.class));
                Message.message(Grant_appointment.this,"MAIN MENU");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
