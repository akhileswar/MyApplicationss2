package com.akhil.breuna.myapplicationss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign extends AppCompatActivity {
    public EditText tt1, tt2;
    public TextView bans,bans1;
    public Button nn;
    public ProgressDialog ds;

    public FirebaseAuth mreff;
    public Firebase.AuthStateListener mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        Firebase.setAndroidContext(this);
        mreff = FirebaseAuth.getInstance();


        tt1= (EditText) findViewById(R.id.editText3);
        tt2 = (EditText) findViewById(R.id.editText4);
        bans1 = (TextView) findViewById(R.id.textView5);
        nn = (Button) findViewById(R.id.button4);
        ds = new ProgressDialog(this);

        tt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                bans1.setText(null);
            }
        });
        tt2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                bans1.setText(null);
            }
        });



        nn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = tt1.getText().toString()+"@gmail.com";
                String password = tt2.getText().toString();


                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                    bans1.setText("fill all fields");

                } else {
                    ds.setMessage("Signing-In....");
                    ds.show();
                    mreff.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user=mreff.getCurrentUser();
                                String userid=user.getUid();
                                ds.hide();
                                Toast.makeText(Sign.this,"sign-in success.....",Toast.LENGTH_LONG).show();
                                tt1.setText(null);
                                tt2.setText(null);


                                startActivity(new Intent(Sign.this,Buttons.class));
                                finish();
                            } else {
                                ds.hide();
                                bans1.setText("invalid username or password");
                                tt1.setText(null);
                                tt2.setText(null);
                                return;
                            }
                        }
                    });
                }
            }
        });
        // on create
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    //end
}

