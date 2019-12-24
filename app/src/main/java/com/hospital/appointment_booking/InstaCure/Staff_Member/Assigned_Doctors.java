package com.hospital.appointment_booking.InstaCure.Staff_Member;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hospital.appointment_booking.InstaCure.DatabaseHelper;
import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.Patient.Bills;
import com.hospital.appointment_booking.InstaCure.Patient.Patient;
import com.hospital.appointment_booking.InstaCure.R;

import java.util.ArrayList;


public class Assigned_Doctors extends AppCompatActivity {

    String username, password, user_type;
    DatabaseHelper dbh = new DatabaseHelper(this);
    ArrayList<String> d_name = new ArrayList<>();
    ListView lv_bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_assigned_doctors);

        Bundle bb = getIntent().getExtras();
        if (bb != null) {
            username = bb.getString("username");
            password = bb.getString("password");
            user_type = bb.getString("user_type");
        }
        lv_bills = (ListView) findViewById(R.id.lv_assigned_doctors);

        Cursor y = dbh.checkduplicates_in_user_credentials("", "", getResources().getString(R.string.staff));

        if (y!=null && y.moveToFirst()) {
            while (true) {
                if (y.getString(4).equals("Y")) {

                    DatabaseHelper dbh1 = new DatabaseHelper(this);
                    Cursor z1 = dbh1.checkduplicates_in_user_credentials(y.getString(2), y.getString(3), getResources().getString(R.string.user_credentials));

                    if (z1.moveToNext()) {
                        d_name.add("Dr. " + z1.getString(1) + " " + z1.getString(2));
                    }
                }

                if (y.isLast())
                    break;
                y.moveToNext();
            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, d_name);
            lv_bills.setAdapter(adapter);
        } else {
            Message.message(Assigned_Doctors.this, "Sorry You have No Bills Right, Now");
            finish();
        }
    }

    //LOGOUT  FROM DOCTOR  | MAINMENU//////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        MenuInflater inflater1 = getMenuInflater();
        inflater1.inflate(R.menu.menu_backtomain, menu);

        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_logout:
                startActivity(new Intent(Assigned_Doctors.this, Login.class));
                Message.message(Assigned_Doctors.this, "Logging Out");
                return true;
            case R.id.menu_mainmenu:
                startActivity(new Intent(Assigned_Doctors.this, Patient.class));
                Message.message(Assigned_Doctors.this, "MAIN MENU");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
