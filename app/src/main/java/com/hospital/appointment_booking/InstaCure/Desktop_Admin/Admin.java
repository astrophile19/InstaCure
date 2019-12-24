package com.hospital.appointment_booking.InstaCure.Desktop_Admin;


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
import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.Personal_Info;
import com.hospital.appointment_booking.InstaCure.R;

public class Admin extends AppCompatActivity {
    String username,password,user_type;
    DatabaseHelper dbh;
    TextView daname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.desktop_admin);

        dbh = new DatabaseHelper(this);
        daname = (TextView) findViewById(R.id.tv_da_name);


        Bundle bb = getIntent().getExtras();
        if (bb != null) {
            username = bb.getString("username");
            password = bb.getString("password");
            user_type = bb.getString("user_type");
        }
        Cursor y = dbh.checkduplicates_in_user_credentials(username, password,getResources().getString(R.string.user_credentials));

        if (y!=null && y.moveToFirst()) {
            String name = y.getString(1);
            daname.setText(name);
        }
    }

    public void onClick(View view){

        Intent i;
        Bundle b = new Bundle();
        b.putString("username",username);
        b.putString("password",password);
        b.putString("user_type",user_type);

        switch (view.getId())
        {
            case R.id.b_da_info:
                i = new Intent(Admin.this, Personal_Info.class);
                break;
            case R.id.b_da_patient_appointment:
                i = new Intent(Admin.this, Grant_appointment.class);
                break;
            case R.id.b_da_remaining_bills:
                i = new Intent(Admin.this, Remaining_patient_bills.class);
                break;
            case R.id.b_da_assign:
                i = new Intent(Admin.this, Assign_Staff.class);
                break;
            default:
                i = new Intent(Admin.this, Delete_Users.class);
                break;
        }

        i.putExtras(b);
        startActivity(i);
    }



    //LOGOUT  FROM ADMIN//////////
    @Override
    public  boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_logout, menu);
        MenuInflater inflater2 =getMenuInflater();
        inflater2.inflate(R.menu.menu_feedback,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_logout:
                startActivity(new Intent(Admin.this, Login.class));
                Message.message(Admin.this,"Logging Out");
                return true;
            case  R.id.menu_feedback:
                startActivity(new Intent(Admin.this, View_Feedback.class));
                Message.message(Admin.this,"View Feedback");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


