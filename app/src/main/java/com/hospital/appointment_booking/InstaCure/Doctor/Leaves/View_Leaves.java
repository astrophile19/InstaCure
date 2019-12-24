package com.hospital.appointment_booking.InstaCure.Doctor.Leaves;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hospital.appointment_booking.InstaCure.DatabaseHelper;
import com.hospital.appointment_booking.InstaCure.Doctor.Doctor;
import com.hospital.appointment_booking.InstaCure.Doctor.Specialization;
import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.R;

import java.util.ArrayList;


public class View_Leaves extends AppCompatActivity {

    String username, password, user_type;
    ListView lvy,lvn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_leaves);

        Bundle bb = getIntent().getExtras();
        if (bb != null) {
            username = bb.getString("username");

            password = bb.getString("password");
            user_type = bb.getString("user_type");
        }
        lvy = (ListView) findViewById(R.id.lvy);
        lvn = (ListView) findViewById(R.id.lvn);

        DatabaseHelper dbh = new DatabaseHelper(this);
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.doctor_leaves));

        if (y!=null && y.moveToFirst()) {
            ArrayList<String> ay = new ArrayList<>();
            ArrayList<String> an = new ArrayList<>();

            if (y.getString(5).equals("N"))
                an.add("FROM: " + y.getString(3) + " TO: " + y.getString(4));
            else
                ay.add("FROM: " + y.getString(3) + " TO: " + y.getString(4));

            if (!y.isLast()) {
                y.moveToNext();
                while (true) {
                    if (y.getString(5).equals("N"))
                        an.add("FROM: " + y.getString(3) + " TO: " + y.getString(4));
                    else
                        ay.add("FROM: " + y.getString(3) + " TO: " + y.getString(4));

                    if (y.isLast())
                        break;

                    y.moveToNext();
                }
            }

            ArrayAdapter adaptery = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ay);
            ArrayAdapter adaptern = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, an);
            lvy.setAdapter(adaptery);
            lvn.setAdapter(adaptern);
        } else {
            Message.message(View_Leaves.this, "Sorry You have No Applications");
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
                startActivity(new Intent(View_Leaves.this, Login.class));
                Message.message(View_Leaves.this,"Logging Out");
                return true;
            case  R.id.menu_mainmenu:
                startActivity(new Intent(View_Leaves.this, Doctor.class));
                Message.message(View_Leaves.this,"MAIN MENU");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
