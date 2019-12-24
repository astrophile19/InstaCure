package com.hospital.appointment_booking.InstaCure.Patient;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.Patient.View_Report.View_Report;
import com.hospital.appointment_booking.InstaCure.R;


public class Appointment extends AppCompatActivity {

    String username,password,user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment);

        Bundle bb = getIntent().getExtras();
        if (bb != null) {
            username = bb.getString("username");

            password = bb.getString("password");
            user_type = bb.getString("user_type");
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
            case R.id.b_new:
                i = new Intent(Appointment.this, New_Appointment.class);
                break;
            case R.id.b_wait:
                i = new Intent(Appointment.this, Wait_Appointment.class);
                break;
            default:
                i = new Intent(Appointment.this, Confirmed_Appointment.class);
                break;
        }
        i.putExtras(b);
        startActivity(i);
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
                startActivity(new Intent(Appointment.this, Login.class));
                Message.message(Appointment.this,"Logging Out");
                return true;
            case  R.id.menu_mainmenu:
                startActivity(new Intent(Appointment.this, Patient.class));
                Message.message(Appointment.this,"MAIN MENU");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
