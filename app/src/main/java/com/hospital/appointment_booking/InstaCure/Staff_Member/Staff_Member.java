package com.hospital.appointment_booking.InstaCure.Staff_Member;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.hospital.appointment_booking.InstaCure.DatabaseHelper;
import com.hospital.appointment_booking.InstaCure.Feedback;
import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.Personal_Info;
import com.hospital.appointment_booking.InstaCure.R;


public class Staff_Member extends AppCompatActivity {
    String username, password, user_type;
    DatabaseHelper dbh;
    TextView sname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.staff_member);

        dbh = new DatabaseHelper(this);
        sname = (TextView) findViewById(R.id.tv_s_name);


        Bundle bb = getIntent().getExtras();
        if (bb != null) {
            username = bb.getString("username");

            password = bb.getString("password");
            user_type = bb.getString("user_type");
        }
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password, getResources().getString(R.string.user_credentials));

        if (y!=null && y.moveToFirst()) {
            String name = y.getString(1);
            sname.setText(name);
        }
    }

    public void onClick(View view) {

        Intent i;
        Bundle b = new Bundle();
        b.putString("username", username);
        b.putString("password", password);
        b.putString("user_type", user_type);

        switch (view.getId()) {
            case R.id.b_s_info:
                i = new Intent(Staff_Member.this, Personal_Info.class);
                break;
            case R.id.b_s_assigned_doctor:
                i = new Intent(Staff_Member.this, Assigned_Doctors.class);
                break;
            default:
                i = new Intent(Staff_Member.this, Feedback.class);
                break;
        }

        i.putExtras(b);
        startActivity(i);
    }



    //LOGOUT  FROM STAFF//////////
    @Override
    public  boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_logout:
                startActivity(new Intent(Staff_Member.this, Login.class));
                Message.message(Staff_Member.this,"Logging Out");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
