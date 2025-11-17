package com.example.cis183_databaseexample;

import android.content.Intent;
import android.os.Bundle;
import android.se.omapi.Session;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class WelcomePage extends AppCompatActivity
{
    Button btn_j_back;
    Button btn_j_post;
    Button btn_j_update;
    ListView lv_j_posts;
    TextView tv_j_welcome;
    TextView tv_j_numPosts;

    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //GUI
        btn_j_back = findViewById(R.id.btn_v_welcome_back);
        btn_j_post = findViewById(R.id.btn_v_welcome_makePost);
        btn_j_update = findViewById(R.id.btn_v_welcome_updateUserData);
        tv_j_welcome = findViewById(R.id.tv_v_welcome_wMessage);
        tv_j_numPosts = findViewById(R.id.tv_v_welcome_numPosts);
        lv_j_posts = findViewById(R.id.lv_v_welcome_listPosts);

        db = new DatabaseHelper(this);

        //setup button click listeners
        buttonClickListeners();

        //set welcome message
        setWelcomeMessage();

        //set number of posts message
        setNumberOfPostsMessage();

        //set the listview
        setListViewWithPosts();
        String fname = "";
        String lname = "";
        String category = "Dogs";
        db.GetAllPostsGivenCriteria(fname, lname, category);

    }

    private void buttonClickListeners()
    {
        //Back Button Click Event
        btn_j_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });

        //Post Button Click Event
        btn_j_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });

        //Update user click event
        btn_j_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //start the update page.
                startActivity(new Intent(WelcomePage.this, UpdateUser.class));
            }
        });
    }

    private void setWelcomeMessage()
    {
        //we are going to have to get session data to understand who is logged.
        String message = "Welcome " + SessionData.getLoggedInUser().getFname();
        tv_j_welcome.setText(message);
    }

    private void setNumberOfPostsMessage()
    {
        //call database
        //count how many posts the user has and return number to here.
        int numPosts = db.getNumPosts();
        tv_j_numPosts.setText("# of Posts: " + numPosts);

    }

    private void setListViewWithPosts()
    {
        ArrayList<String> posts = db.getAllPosts();

        for(int i = 0; i < posts.size(); i++)
        {
            Log.d("Posts: ", posts.get(i));
        }
    }
}