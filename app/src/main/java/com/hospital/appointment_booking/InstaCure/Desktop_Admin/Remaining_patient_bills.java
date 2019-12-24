package com.hospital.appointment_booking.InstaCure.Desktop_Admin;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hospital.appointment_booking.InstaCure.DatabaseHelper;
import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.R;

import java.util.ArrayList;


public class Remaining_patient_bills extends AppCompatActivity {

    String username, password, user_type;
    DatabaseHelper dbh = new DatabaseHelper(this);
    ArrayList<String> p_name = new ArrayList<>();
    ArrayList<String> ppass = new ArrayList<>();
    ArrayList<String> fee = new ArrayList<>();
    ArrayList<String> pro = new ArrayList<>();
    ListView lv_remiaing_bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.remaining_patient_bills);

        Bundle bb = getIntent().getExtras();
        if (bb != null) {
            username = bb.getString("username");
            password = bb.getString("password");
            user_type = bb.getString("user_type");
        }
        lv_remiaing_bills = (ListView) findViewById(R.id.lv_remaining_bills);
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, "all_pending_appointment");

        if (y!=null && y.moveToFirst()) {
            while (true) {
                if (y.getString(4).equals("F")) {
                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y.getString(0), y.getString(1), getResources().getString(R.string.user_credentials));

                    if (z1.moveToNext()) {
                        p_name.add(z1.getString(1) + " " + "  ( 300/- )");


                    }
                }

                if (y.isLast())
                    break;
                y.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, p_name);
            lv_remiaing_bills.setAdapter(adapter);
        } else {
            Message.message(Remaining_patient_bills.this, " No Patient with unpaid Bills");
            finish();
        }
/*
        lv_remiaing_bills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                boolean by = dbh.delete_bills(p_name.get(position), ppass.get(position),pro.get(position),fee.get(position));

                if (by) {
                    Message.message(Remaining_patient_bills.this, "Remaining Bills Cleared");
                    finish();
                } else {
                    Message.message(Remaining_patient_bills.this, "Remaining Bills Not Cleared try Again");
                }
            }
        });

*/


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
                startActivity(new Intent(Remaining_patient_bills.this, Login.class));
                Message.message(Remaining_patient_bills.this,"Logging Out");
                return true;
            case  R.id.menu_mainmenu:
                startActivity(new Intent(Remaining_patient_bills.this, Admin.class));
                Message.message(Remaining_patient_bills.this,"MAIN MENU");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }







}
