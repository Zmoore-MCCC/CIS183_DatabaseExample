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

public class Register extends AppCompatActivity
{
    EditText et_j_fname;
    EditText et_j_lname;
    EditText et_j_email;
    Button btn_j_back;
    Button btn_j_register;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        et_j_fname = findViewById(R.id.et_v_reg_fname);
        et_j_lname = findViewById(R.id.et_v_reg_lname);
        et_j_email = findViewById(R.id.et_v_reg_email);

        btn_j_back = findViewById(R.id.btn_v_reg_back);
        btn_j_register = findViewById(R.id.btn_v_reg_register);

        db = new DatabaseHelper(this);

        buttonClickListeners();
    }

    private void buttonClickListeners()
    {
        btn_j_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String fname = et_j_fname.getText().toString();
                String lname = et_j_lname.getText().toString();
                String email = et_j_email.getText().toString();

                if(!fname.isEmpty() && !lname.isEmpty() && !email.isEmpty())
                {

                    User u = new User();
                    u.setFname(fname);
                    u.setLname(lname);
                    u.setEmail(email);
                    db.addUserToDB(u);
                }
            }
        });

        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Register.this, MainActivity.class));
            }
        });
    }
}