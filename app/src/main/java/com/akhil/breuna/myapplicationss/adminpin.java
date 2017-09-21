package com.akhil.breuna.myapplicationss;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.*;
import android.view.*;

public class adminpin extends AppCompatActivity {
Button b;
    EditText e;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminpin);
        b=(Button)findViewById(R.id.button7);
        e=(EditText)findViewById(R.id.editText7);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str=e.getText().toString();
                if(!TextUtils.isEmpty(str)){
                    if(str.equals("4321" +
                            "")){

                        startActivity(new Intent(adminpin.this,groupfordelete.class));
                    }else
                        Toast.makeText(adminpin.this,"enter valid pin",Toast.LENGTH_SHORT).show();

                }else
                    Toast.makeText(adminpin.this,"enter pin",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
