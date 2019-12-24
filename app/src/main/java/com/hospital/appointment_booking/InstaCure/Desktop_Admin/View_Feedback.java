package com.hospital.appointment_booking.InstaCure.Desktop_Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

@SuppressWarnings("ALL")
public class View_Feedback extends AppCompatActivity {
    ListView lv_feed;
    ArrayList<String> feed = new ArrayList<>();
    ArrayList<String> uname = new ArrayList<>();
    ArrayList<String> pass = new ArrayList<>();
    DatabaseHelper db = new DatabaseHelper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__feedback);

        lv_feed = (ListView) findViewById(R.id.lv_viewallfeedback);

        Cursor y = db.checkduplicates_in_user_credentials("", "", "all_feedback");

        if(!y.moveToFirst()) {
            Message.message(View_Feedback.this, "No Feedback at the moment");
            finish();
        }

        else{
            while(true){

                    feed.add(y.getString(2));
                    uname.add(y.getString(0));
                    pass.add(y.getString(1));

                if (y.isLast())
                    break;
                y.moveToNext();

            }

            ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, feed);
            lv_feed.setAdapter(adapter);

        }
        lv_feed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                boolean by = db.delete_feedback(uname.get(position), pass.get(position));

                if (by) {
                    Message.message(View_Feedback.this, "Feedback Deleted");
                    finish();
                } else {
                    Message.message(View_Feedback.this, "Feedback Cannot Be deleted Try again");
                }
            }
        });



    }




    //LOGOUT  FROM ADMIN | MAINMENU//////////
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
                startActivity(new Intent(View_Feedback.this, Login.class));
                Message.message(View_Feedback.this,"Logging Out");
                return true;
            case  R.id.menu_mainmenu:
                startActivity(new Intent(View_Feedback.this, Admin.class));
                Message.message(View_Feedback.this,"MAIN MENU");
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
