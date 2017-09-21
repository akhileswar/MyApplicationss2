package com.akhil.breuna.myapplicationss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import android.view.*;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    public EditText t1,t2,t3;
public TextView ban1;
public Button n,n1;
    public ProgressDialog dd;
    public FirebaseAuth mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        mref = FirebaseAuth.getInstance();

        t1=(EditText)findViewById(R.id.editText);
        t2=(EditText)findViewById(R.id.editText2);
        t3=(EditText)findViewById(R.id.editText5);

        ban1=(TextView)findViewById(R.id.textView);
        n=(Button)findViewById(R.id.button2);
        n1=(Button)findViewById(R.id.button3);
        dd=new ProgressDialog(this);
//
t1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        ban1.setText(null);
    }
});
        t2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ban1.setText(null);
            }
        });
        t3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                ban1.setText(null);
            }
        });

//
n.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String email = t1.getText().toString()+"@gmail.com";
        String password = t2.getText().toString();
        String password1 = t3.getText().toString();
        int len=password.length();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(password1)) {

            ban1.setText("fill all fields");
        } else {
            if(len>=6) {
                if (password.equals(password1)) {
                    dd.setMessage("Signing-Up....");
                    dd.show();
                    mref.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                dd.hide();
                                Toast.makeText(MainActivity.this, "sign-up success", Toast.LENGTH_LONG).show();

                                t1.setText(null);
                                t2.setText(null);
                                t3.setText(null);
                                ban1.setText(null);
                            } else {
                                ban1.setText("username Already Exists");
                                dd.hide();
                            }
                        }
                    });
                } else {
                    t2.setText(null);
                    t3.setText(null);
                    ban1.setText("please-confirm password");

                }
            }else
                ban1.setText("password atleast 6-characters");

        }
    }
});

      //
       n1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,Sign.class));

           }
       });

            }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mref.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(MainActivity.this,Buttons.class));

        }


    }




}