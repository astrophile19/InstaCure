package com.hospital.appointment_booking.InstaCure.Patient.View_Report;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.hospital.appointment_booking.InstaCure.Doctor.Doctor;
import com.hospital.appointment_booking.InstaCure.Doctor.Doctor_Patient.Write_Report;
import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.Patient.Patient;
import com.hospital.appointment_booking.InstaCure.R;


public class Final_View_Report extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_view_report);

        Bundle bb = getIntent().getExtras();
        String report = null;
        if (bb != null) {
            report = bb.getString("report");
        }
        TextView final_report = (TextView) findViewById(R.id.tv_report);
        final_report.setText(report);

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
                startActivity(new Intent(Final_View_Report.this, Login.class));
                Message.message(Final_View_Report.this,"Logging Out");
                return true;
            case  R.id.menu_mainmenu:
                startActivity(new Intent(Final_View_Report.this, Patient.class));
                Message.message(Final_View_Report.this,"MAIN MENU");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
