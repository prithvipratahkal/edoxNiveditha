package com.example.sunshine.edocx.app;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.READ_CONTACTS;
import static java.lang.Thread.sleep;

public class register extends AppCompatActivity {
    UserInformation user;
    String email,phnum,name,pass;
    FirebaseDatabase reff= FirebaseDatabase.getInstance();
    DatabaseReference mDatabase =reff.getReference("userDB");
    //Firebase ff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  Firebase.setAndroidContext(this);
        setContentView(R.layout.activity_register3);
        final Button button=findViewById(R.id.button);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               View focus=null;
                boolean cancel=true;
                //Toast.makeText(register.this,"entry point",Toast.LENGTH_LONG).show();
                EditText edEmail = (EditText)findViewById(R.id.Email);
                String email= edEmail.getText().toString();
                Pattern p;
                p = Pattern.compile("^[a-z0-9]+@[a-z]+\\.[a-z]+$");
                Matcher m = p.matcher(email);
                if(!m.matches()){
                    edEmail.setError("Invalid email");
                    focus=edEmail;
                    cancel=false;
                }
                EditText  edPh = (EditText)findViewById(R.id.PhNum);
                String phnum= edPh.getText().toString();
                p=Pattern.compile("\\+[0-9]{12}");
                m = p.matcher(phnum);
                if(!m.matches()){
                    edPh.setError("Invalid phone number");
                    focus=edPh;
                    cancel=false;
                }


                EditText edName = (EditText)findViewById(R.id.Name);
                String name= edName.getText().toString();
                p=Pattern.compile("[A-Za-z]+");
                m = p.matcher(name);
                if(!m.matches()){
                    edName.setError("Invalid name");
                    focus=edName;
                    cancel=false;
                }


                EditText  edPass = (EditText)findViewById(R.id.Password);
                String pass= edPass.getText().toString();
                p=Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,20}$");
                m = p.matcher(pass);
                if(!m.matches()){
                    edPass.setError("Password must contain at least one upper case letter, one lower case letter and a digit");
                    focus=edPass;
                    cancel=false;
                }



                EditText edCon = (EditText)findViewById(R.id.ConfirmPass);
                String cPass= edCon.getText().toString();
                if(!cPass.equals(pass))
                {
                    edCon.setError("Password must match");
                    focus=edCon;
                    cancel=false;
                }
                if(cancel==false)
                {
                    focus.requestFocus();
                    return;
                }

        //        ff= new Firebase("https://edocx-1a864.firebaseio.com/userDB");
                //Toast.makeText(register.this, "before user", Toast.LENGTH_LONG).show();
                user = new UserInformation(email,name,phnum,pass);
                //getValues();
                //Toast.makeText(register.this, "after getvalues", Toast.LENGTH_LONG).show();
                //DatabaseReference ref =  mDatabase.getRef("userDB");
                 String userId = mDatabase.push().getKey();
                //Toast.makeText(register.this, "userID"+userId, Toast.LENGTH_LONG).show();
                 //mDatabase.setValue(userId);
                 mDatabase.child(userId).setValue(user);
                // mDatabase.child(userId).setValue(user);
               // DatabaseReference ref = mDatabase.child("userDB").push();
                Toast.makeText(register.this, "Registration Successful", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));



            }
        });}
/*private void getValues()
{
    Toast.makeText(register.this, "inside getValues", Toast.LENGTH_LONG).show();
    user.setEmail(email);
    user.setName(name);
    user.setPass(pass);
    user.setPhone_num(phnum);


}*/
}
