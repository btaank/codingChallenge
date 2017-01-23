package com.example.bharti.codingchallenge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    EditText pass;
    EditText username;
    private String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void login(View view) {
        pass = (EditText)findViewById(R.id.editText2);
        username = (EditText)findViewById(R.id.editText);
        String userName = username.getText().toString();
        Password = pass.getText().toString();

        if(userName.length() == 0)
            Toast.makeText(getApplicationContext(), "Username cannot be empty",Toast.LENGTH_SHORT).show();
        else if(Password.length() < 7)
            Toast.makeText(getApplicationContext(), "Password should have more than 7 characters",Toast.LENGTH_SHORT).show();
        else if(Pattern.matches(".*[A-Z]",Password))
            Toast.makeText(getApplicationContext(), "Password should have atleast 1 uppercase letter ",Toast.LENGTH_SHORT).show();
        else if(Pattern.matches("./d",Password))
            Toast.makeText(getApplicationContext(), "Password should have atleast 1 number",Toast.LENGTH_SHORT).show();
        else if (Pattern.matches("[&@!#+]",Password))
        {
            Toast.makeText(getApplicationContext(), "Password cannot have special characters ", Toast.LENGTH_SHORT).show();
        }


        else
        {
            Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, FirstPage.class));
        }
    }
}
