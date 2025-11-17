package com.example.cis183_databaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateUser extends AppCompatActivity
{
    EditText et_j_fname;
    EditText et_j_lname;
    EditText et_j_email;
    Button btn_j_back;
    Button btn_j_update;

    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //GUI
        et_j_fname = findViewById(R.id.et_v_update_fname);
        et_j_lname = findViewById(R.id.et_v_update_lname);
        et_j_email = findViewById(R.id.et_v_update_email);

        btn_j_back = findViewById(R.id.btn_v_update_back);
        btn_j_update = findViewById(R.id.btn_v_update_updateData);

        db = new DatabaseHelper(this);

        buttonClickSetup();
        fillEditTextsWithUserData();
    }

    private void buttonClickSetup()
    {
        //back button
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //go back to welcome page and do not go to database to make any updates
            }
        });

        //update button
        btn_j_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //call database and update the data for the given user given
                //the data entered into the edittexts
                //1. get the data from the textboxes
                //2. create a user and pass it to the database
                //3. update the database
                //4. update SessionData
                User u = new User();

                u.setId(SessionData.getLoggedInUser().getId());
                u.setFname(et_j_fname.getText().toString());
                u.setLname(et_j_lname.getText().toString());
                u.setEmail(et_j_email.getText().toString());

                db.updateUser(u);

                SessionData.setLoggedInUser(u);

                startActivity(new Intent(UpdateUser.this, WelcomePage.class));
            }
        });
    }

    private void fillEditTextsWithUserData()
    {
        et_j_fname.setText(SessionData.getLoggedInUser().getFname());
        et_j_lname.setText(SessionData.getLoggedInUser().getLname());
        et_j_email.setText(SessionData.getLoggedInUser().getEmail());
    }
}