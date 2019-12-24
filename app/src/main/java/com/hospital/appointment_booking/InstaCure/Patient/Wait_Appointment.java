package com.hospital.appointment_booking.InstaCure.Patient;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.hospital.appointment_booking.InstaCure.CustomListViewAdapter;
import com.hospital.appointment_booking.InstaCure.DatabaseHelper;
import com.hospital.appointment_booking.InstaCure.Desktop_Admin.Grant_appointment;
import com.hospital.appointment_booking.InstaCure.Desktop_Admin.View_Feedback;
import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.R;
import com.hospital.appointment_booking.InstaCure.RowItem;

import java.util.ArrayList;
import java.util.List;


public class Wait_Appointment extends AppCompatActivity {

    String username,password,user_type;
    ListView lv_unapp;
    List<String> u_p;
    List<String> p_p;
    List<RowItem> rowItems;
    ArrayList<String> doc = new ArrayList<>();
    ArrayList<String> pat = new ArrayList<>();
    ArrayList<String> pro = new ArrayList<>();


    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wait_appointment);

        lv_unapp = (ListView) findViewById(R.id.lv_unapprovedapp);
        u_p = new ArrayList<>();
        p_p = new ArrayList<>();

        final DatabaseHelper dbh = new DatabaseHelper(this);
        Cursor y = dbh.checkduplicates_in_user_credentials("", "", getResources().getString(R.string.all_pending_appointment));

        Bundle bb = getIntent().getExtras();
        if (bb != null) {
            username = bb.getString("username");

            password = bb.getString("password");
            user_type = bb.getString("user_type");

        }


        if (y!=null && y.moveToFirst()) {
            Log.e("In Grantymovefirst", "looping");
            while (true) {
                //pateinet approvl has three mode W - wait, A - approved, F - finished

                if (y.getString(4).equals("W")) {
                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y.getString(2), y.getString(3), getResources().getString(R.string.user_credentials));
                    DatabaseHelper dbh2 = new DatabaseHelper(this);
                    Cursor z2 = dbh2.checkduplicates_in_user_credentials(y.getString(0), y.getString(1), getResources().getString(R.string.user_credentials));
                    u_p.add(y.getString(0));
                    p_p.add(y.getString(1));

                    if (z1.moveToNext()) {
                        pat.add(z1.getString(1));
                    }

                    if (z2.moveToNext()) {
                        doc.add(z2.getString(1) );
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
            lv_unapp.setAdapter(adapter);
        } else {
            Message.message(Wait_Appointment.this, "No Pending Apppointments");
            finish();
        }

    }




    //LOGOUT  FROM DOCTOR  | MAINMENU//////////
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
                startActivity(new Intent(Wait_Appointment.this, Login.class));
                Message.message(Wait_Appointment.this,"Logging Out");
                return true;
            case  R.id.menu_mainmenu:
                startActivity(new Intent(Wait_Appointment.this, Patient.class));
                Message.message(Wait_Appointment.this,"MAIN MENU");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
