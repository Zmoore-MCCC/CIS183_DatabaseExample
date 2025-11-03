package com.example.cis183_databaseexample;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String database_name = "Blog.db";
    private static final String users_table_name = "Users";
    private static final String posts_table_name = "Posts";
    public DatabaseHelper(Context c)
    {
        //we will use this to create the database
        //it accepts: the context, the name of the database, factory (leave null), and version number
        //If your database becomes corrupt or the information in the database is wrong
        //change the version number
        //super is used to call the functionality of the base class SQLiteOpenHelper and
        //then executes the extended (DatabaseHelper)
        super(c, database_name, null, 1);
    }

    //this is called when a new database
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //this is where we will create the tables in our database
        //Create table in the database
        //execute the sql statement on the database that was passed to the function called db
        //This can be tricky because we have to write our sql statement as strings
        //CREATE TABLE Users (userId integer primary key autoincrement not null, fname varchar(50), lname varchar(50), email varchar(50));
        db.execSQL("CREATE TABLE " + users_table_name + " (userId integer primary key autoincrement not null, fname varchar(50), lname varchar(50), email varchar(50));");
        db.execSQL("CREATE TABLE " + posts_table_name + " (postId integer primary key autoincrement not null, userId integer, category varchar(50), postData varchar(255), foreign key (userId) references " + users_table_name + " (userId));");
    }

    //this is called when a new database version is created
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        //delete the tables in the db if they exist
        db.execSQL("DROP TABLE IF EXISTS " + users_table_name + ";");
        db.execSQL("DROP TABLE IF EXISTS " + posts_table_name + ";");

        //recreate the tables
        onCreate(db);
    }

    public String getUserTableName()
    {
        return users_table_name;
    }

    public String getPostsTableName()
    {
        return posts_table_name;
    }

    //initialize all tables with dummy data
    public void initAllTables()
    {
        initUsers();
        initPosts();
    }

    //this function will only be used once to add dummy data to my users table
    private void initUsers()
    {
        //this will do its own error checking
        //we only want to add the data if nothing is currently in the table
        //this wil ensure we do not add the data more than once.
        if(countRecordsFromTable(users_table_name) == 0)
        {
            //get a writeable version of the database
            //we need a writeable version because we are going to write to the database
            SQLiteDatabase db = this.getWritableDatabase();

            //insert dummy data into the user table if there is nothing in the table
            //we do not want to do this more than once so it needs to be surrounded with the if
            //statement above.
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Zackary', 'Moore', 'zmoore@monroecccc.edu');");
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Shannon', 'Thomas', 'sthomas@umich.edu');");
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Gabriel', 'Smith', 'BigB@gmail.com');");
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Harrison', 'Moore', 'hsm@yahoo.com');");
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Tito', 'Williams', 'Tito_Boy@company.gov');");
            db.execSQL("INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('Willow', 'Branch', 'Willow_Branch@hotmail.com');");

            //close the database
            db.close();
        }
    }

    private void initPosts()
    {
        //this will do its own error checking
        //we only want to add the data if nothing is currently in the table
        //this wil ensure we do not add the data more than once.
        if(countRecordsFromTable(posts_table_name) == 0)
        {
            //get a writable version of the database
            //we need a writable version because we are going to write to the database
            SQLiteDatabase db = this.getWritableDatabase();

            //insert dummy data into the posts table if there is nothing in the table
            //we do not want to do this more than once so it needs to be surrounded by an if statement
            //the user ids (pk) for the dummy data will be added manully using the following key based
            //on the dummy data entered for the users:
            //|1 - Zackary| 2 - Shannon| 3 - Gabriel| 4 - Harrison| 5 - Tito| 6 - Willow|
            //when a user posts the userId will populate automatically based off which user is logged in

            db.execSQL("INSERT INTO " + posts_table_name + " (userId, category, postData) VALUES (1, 'Technology', 'This is my first post about tech. I am posting about my new computer.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userId, category, postData) VALUES (2, 'Gardening', 'I love gardening.  This is my first post about gardening.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userId, category, postData) VALUES (2, 'Gardening', 'Corn.  This year I got a bunch of differnet types of corn to grow in my garden.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userId, category, postData) VALUES (3, 'Baeball', 'I love baseball.  I am playing cathcer, pitch, and outfield.  I got a few homeruns this year');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userId, category, postData) VALUES (3, 'Gaming', 'Videogames are so fun.  My favorite videogame right now is Good Job.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userId, category, postData) VALUES (4, 'Cartoons', 'Bluey is the best cartoon in the World!  I could watch it all day if my parents let me.');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userId, category, postData) VALUES (5, 'Squirrels', 'I hate squirrels!');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userId, category, postData) VALUES (6, 'Squirrels', 'I love squirrels!');");
            db.execSQL("INSERT INTO " + posts_table_name + " (userId, category, postData) VALUES (1, 'Teaching', 'I enjoy teaching my students about Software Engineering');");

            db.close();
        }

    }

    public int countRecordsFromTable(String tableName)
    {
        //get an instance of the a readable database
        //we only need readable because we are not adding anything to the database with this action
        SQLiteDatabase db = this.getReadableDatabase();

        //count the number of entries in the table that was passed to the function
        //this is a built-in function
        int numRows = (int) DatabaseUtils.queryNumEntries(db, tableName);

        //whenever you open the database you need to close it
        db.close();

        return numRows;
    }

    public void getAllUserDataGivenId(int userId)
    {
        //if the userId was found in the table then this userId has user data associated with it
        if(userIdExists(userId))
        {
            //I will retrieve all of this informating from the user table given the id passed to me
            User loggedInUser = new User();

            //query to get the user data
            String selectAll = "SELECT * FROM " + users_table_name + " WHERE userId = '" + userId + "';";

            //get a readable version of the database
            SQLiteDatabase db = this.getReadableDatabase();

            //run the query
            Cursor cursor = db.rawQuery(selectAll, null);

            //see if there was anything returned
            if(cursor != null)
            {
                //move the cursor to the first record
                cursor.moveToFirst();

                //parse the record into individual columns
                loggedInUser.setId(cursor.getInt(0));
                loggedInUser.setFname(cursor.getString(1));
                loggedInUser.setLname(cursor.getString(2));
                loggedInUser.setEmail(cursor.getString(3));

                SessionData.setLoggedInUser(loggedInUser);
            }
            else
            {
                SessionData.setLoggedInUser(null);
            }
        }
    }

    public boolean userIdExists(int userId)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        //I want to count the number of records that contain the userId that was passed to me
        //There are only two viable values for the count query to return to me:
        //0 - the userId was not found
        //1 - the userId was found
        //we cannot get anything else returned because the userId is the primary key
        //This ensures that no two people can have the same userId

        //SELECT count(userId) FROM users WHERE userId = '1';
        String checkUserId = "SELECT count(userId) FROM " + users_table_name + " WHERE userId = '" + userId + "';";

        //run the query
        //cursor could return more than one value depending on the query that we run
        //in this case it should only return one value
        Cursor cursor = db.rawQuery(checkUserId, null);


        //move the cursor to the first returned value
        cursor.moveToFirst();
        //we are only expecting one thing returned to us so the index should be 0
        int count = cursor.getInt(0);

        db.close();


        //if the count is not zero the userId was found in the database;
        if(count != 0)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public int getNumPosts()
    {
        int numPosts = 0;
        String selectStatement = "SELECT COUNT(userId) FROM " + posts_table_name + " WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectStatement, null);

        if(cursor != null)
        {
            cursor.moveToFirst();
            //give getInt 0 for the first thing that is returned.  This should always return
            //on thing because I am using a count function in sql
            //using getInt because count will return an int

            numPosts = cursor.getInt(0);
        }
        else
        {
            //this would be an error for userId not existing.
        }

        db.close();

        return numPosts;

    }

    public ArrayList<String> getAllPosts()
    {
        String selectStatement = "SELECT postData FROM " + posts_table_name + " WHERE userId = '" + SessionData.getLoggedInUser().getId() + "';";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectStatement, null);

        ArrayList<String> posts = new ArrayList<>();

        if(cursor.moveToFirst())
        {
            //we need a loop for this because we do not know how many
            //posts a user can have (0-n)
            do
            {
                //this is only column 0 because we are only gettign postData
                //returned to us by the query.
                String post = cursor.getString(0);
                posts.add(post);
            }
            while(cursor.moveToNext());
        }

        db.close();
        return posts;
    }

    public void addUserToDB(User u)
    {
        //get an instance of a writeable database
        SQLiteDatabase db = this.getWritableDatabase();

        //we need out sql insert statement to look like this:
        //INSERT INTO users (fname, lname, email) VALUES ('Bobby','Smith','BSmith@gmail.com');
        String insertUser = "INSERT INTO " + users_table_name + " (fname, lname, email) VALUES ('" + u.getFname() + "','" + u.getLname() + "','" + u.getEmail() + "');";

        db.execSQL(insertUser);

        db.close();
    }

    public ArrayList<Integer> findUserGivenCriteria(String f, String l)
    {
        ArrayList<Integer> listUsers = new ArrayList<>();

        String selectStatement = "SELECT userId FROM " + users_table_name + " WHERE ";

        //write java code to complete the selectStatement
        //the user left this blank and do not care about this criteria
        if(f.isEmpty())
        {
            selectStatement += "fname is not null and ";
        }
        else
        {
            selectStatement += "fname = '" + f + "' and ";
        }

        if(l.isEmpty())
        {
            selectStatement += "lname is not null;";
        }
        else
        {
            selectStatement += "lname = '" + l + "';";
        }

        //SELECT userId FROM users where fname is not null and lname is not null;
        //SELECT userId FROM users where fname is not null and lname = l;
        //SELECT userId FROM users where fname = f and lname is not null;
        //SELECT userId FROM users where fname = f and lname = l;

    }
}
