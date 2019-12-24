package com.hospital.appointment_booking.InstaCure.Doctor.Doctor_Patient;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hospital.appointment_booking.InstaCure.DatabaseHelper;
import com.hospital.appointment_booking.InstaCure.Doctor.Doctor;
import com.hospital.appointment_booking.InstaCure.Doctor.Specialization;
import com.hospital.appointment_booking.InstaCure.Login;
import com.hospital.appointment_booking.InstaCure.Message;
import com.hospital.appointment_booking.InstaCure.R;


public class Write_Report extends AppCompatActivity {

    String username, password, user_type, p_username, p_password, problem, rp, fee;
    DatabaseHelper dbh;
    EditText etr;
    TextView pro;
    Button b_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_report);

        Bundle bb = getIntent().getExtras();
        if (bb != null) {
            username = bb.getString("username");
            password = bb.getString("password");
            user_type = bb.getString("user_type");
            p_username = bb.getString("p_username");
            p_password = bb.getString("p_password");
            problem = bb.getString("problem");
            fee = bb.getString("fees");
        }
        pro = (TextView) findViewById(R.id.tv_problem);
        etr = (EditText) findViewById(R.id.et_report);
        b_submit = (Button) findViewById(R.id.b_submit_report);
        dbh = new DatabaseHelper(this);
        pro.setText(problem);

        b_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rp = etr.getText().toString();

                //patient approval has three mode W - wait, A - approved, F - finished
                boolean b = dbh.update_doctor_patient(p_username, p_password, username, password, "F", problem, fee, rp);

                if (b) {
                    Message.message(Write_Report.this, "Report uploaded successfully");
                    finish();
                } else {
                    Message.message(Write_Report.this, "Report Not Uploaded, Try again");
                }
            }
        });


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
                startActivity(new Intent(Write_Report.this, Login.class));
                Message.message(Write_Report.this,"Logging Out");
                return true;
            case  R.id.menu_mainmenu:
                startActivity(new Intent(Write_Report.this, Doctor.class));
                Message.message(Write_Report.this,"MAIN MENU");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
