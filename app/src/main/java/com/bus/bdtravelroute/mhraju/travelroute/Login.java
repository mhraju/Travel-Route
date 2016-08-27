package com.bus.bdtravelroute.mhraju.travelroute;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {


    Switch logInSwitch;
    EditText userNameET;
    EditText passwordET;
    TextView logInNoteTV;
    TextView signUpNoteTV;
    Button signUpButton;
    Button logInButton;
    String userName;
    String password;
    String loggedInUserName;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    com.bus.bdtravelroute.mhraju.travelroute.database.UserManager userManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();
        loggedInUserName = preferences.getString("userName", "unknownUser");

        if (!loggedInUserName.contentEquals("unknownUser")) {
            Intent intent = new Intent(Login.this, SearchActivity.class);
            startActivity(intent);
            finish();
        }

        userManager = new com.bus.bdtravelroute.mhraju.travelroute.database.UserManager(this);

        logInSwitch = (Switch) findViewById(R.id.logInSwitch);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        logInButton = (Button) findViewById(R.id.logInButton);

        userNameET = (EditText) findViewById(R.id.userNameET);
        passwordET = (EditText) findViewById(R.id.passwordET);

        logInNoteTV = (TextView) findViewById(R.id.loginInNoteTV);
        signUpNoteTV = (TextView) findViewById(R.id.signUpNoteTV);


        logInSwitch.setChecked(false);
        signUpButton.setVisibility(View.INVISIBLE);
        logInButton.setVisibility(View.VISIBLE);
        logInNoteTV.setTypeface(null, Typeface.BOLD);
        signUpNoteTV.setTypeface(null, Typeface.NORMAL);

        logInSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    logInButton.setVisibility(View.INVISIBLE);
                    signUpButton.setVisibility(View.VISIBLE);
                    logInNoteTV.setTypeface(null, Typeface.NORMAL);
                    signUpNoteTV.setTypeface(null, Typeface.BOLD);
                } else {
                    signUpButton.setVisibility(View.INVISIBLE);
                    logInButton.setVisibility(View.VISIBLE);
                    logInNoteTV.setTypeface(null, Typeface.BOLD);
                    signUpNoteTV.setTypeface(null, Typeface.NORMAL);
                }
            }
        });


    }


    public void logIn(View view) {
        userName = userNameET.getText().toString();
        password = passwordET.getText().toString();

        User user = new User(userName, password);
        if (userManager.checkIfUserExistedWithPassword(null)) {
            editor.putString("userName", userName).apply();
            Intent intent = new Intent(Login.this, SearchActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(Login.this, "No User Found, Please sign up to continue", Toast.LENGTH_SHORT).show();

        }
    }

    public void signUp(View view) {
        userName = userNameET.getText().toString();
        password = passwordET.getText().toString();
        User user = new User(userName, password);


        if (userManager.checkIfUserExisted(null)) {
            Toast.makeText(Login.this, "User Exists", Toast.LENGTH_SHORT).show();
        } else {
            boolean inserted = userManager.addUser(user);
            if (inserted) {
                Toast.makeText(Login.this, "inserted", Toast.LENGTH_SHORT).show();
                logInSwitch.setChecked(false);
                signUpButton.setVisibility(View.INVISIBLE);
                logInButton.setVisibility(View.VISIBLE);
                logInNoteTV.setTypeface(null, Typeface.BOLD);
                signUpNoteTV.setTypeface(null, Typeface.NORMAL);
            }
        }

    }


}
