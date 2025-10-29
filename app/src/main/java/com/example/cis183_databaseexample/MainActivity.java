package com.example.cis183_databaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    DatabaseHelper dbHelper;

    //GUI
    EditText et_j_userId;
    Button btn_j_login;
    Button btn_j_register;
    Button btn_j_findId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //GUI
        et_j_userId    = findViewById(R.id.et_v_userId);
        btn_j_login    = findViewById(R.id.btn_v_login);
        btn_j_register = findViewById(R.id.btn_v_register);
        btn_j_findId   = findViewById(R.id.btn_v_findId);

        //make new instance of the dbHelper
        dbHelper = new DatabaseHelper(this);

        //initialize all of the tables with dummy data
        //there is logic in this function to ensure this is not done more than once.
        dbHelper.initAllTables();

        //just used for testing
        checkTableRecordCount();

        //setup button click listeners
        setButtonClickListeners();
    }

    //for testing purposes
    private void checkTableRecordCount()
    {
        Log.d("Users Record Count: ", dbHelper.countRecordsFromTable(dbHelper.getUserTableName()) + "");
        Log.d("Posts Record Count: ", dbHelper.countRecordsFromTable(dbHelper.getPostsTableName()) + "");
    }

    private void setButtonClickListeners()
    {
        btn_j_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //get the id that was entered by the user
                int enteredId = Integer.parseInt(et_j_userId.getText().toString());
                //we want to ask the database if this is a viable userID
                //if yes: log them in and take them to the welcome page
                //if no: give error
                dbHelper.getAllUserDataGivenId(enteredId);

                //because the database sets the sessiongData (who is logged int)
                //we can look to session data to ensure someone is logged in
                if(SessionData.getLoggedInUser() != null)
                {
                    //User u = SessionData.getLoggedInUser();
                    //Log.d("Logged in: ", u.getFname() + " " + u.getLname());
                    startActivity(new Intent(MainActivity.this, WelcomePage.class));
                }
                else {
                    //user does not exists
                    //error message
                    //I would make some kind of invisible error message and make it
                    //visible if I reached this part
                }
            }
        });
    }
}