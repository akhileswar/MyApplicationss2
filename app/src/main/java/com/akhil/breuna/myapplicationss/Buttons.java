package com.akhil.breuna.myapplicationss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.content.Intent;
import android.widget.*;
import android.view.*;

import com.google.firebase.auth.FirebaseAuth;

public class Buttons extends AppCompatActivity {
    Button add,view,se,out,del;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buttons);

        view=(Button)findViewById(R.id.button10);
        add=(Button)findViewById(R.id.button11);
        se=(Button)findViewById(R.id.button12);
        out=(Button)findViewById(R.id.button13);
        del=(Button)findViewById(R.id.button6);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Buttons.this,groups.class));
                finish();
            }
        });

        se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Buttons.this,Searchcontact.class));
                finish();
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Buttons.this,MainActivity.class));
                finish();
                Toast.makeText(Buttons.this,"sign-out success",Toast.LENGTH_LONG).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Buttons.this,groupforadd.class));

            }
        });


        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Buttons.this,adminpin.class));

            }
        });
    }
}
