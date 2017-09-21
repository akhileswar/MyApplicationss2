package com.akhil.breuna.myapplicationss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class Del extends AppCompatActivity {
    Button d;
    TextView t1,t2;
    EditText e;
public int l=0;
    FirebaseAuth mAuth;
    Firebase mref;
    FirebaseAuth.AuthStateListener mAuthstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del);

        Firebase.setAndroidContext(this);
        mref=new Firebase("https://myapply-cc0e7.firebaseio.com/");
        mAuth=FirebaseAuth.getInstance();

        d = (Button) findViewById(R.id.button7);
        t1=(TextView)findViewById(R.id.textView9);
        e=(EditText)findViewById(R.id.editText7);
        t2=(TextView)findViewById(R.id.textView10);

        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("message");

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l=0;
                final String search=e.getText().toString();

                if(!TextUtils.isEmpty(search)){

                  final  FirebaseUser user=mAuth.getCurrentUser();

                    mref.child("contacts").child(message).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren()){

                                String namer=ds.child("name").getValue(String.class);

                                if(namer.equalsIgnoreCase(search)){
                                    l++;
                                }
                            }

                            if(l==0){
                                Toast.makeText(Del.this, "contact didn't Exist...", Toast.LENGTH_LONG).show();
                            }
                            if(l==1){
                                mref.child("contacts").child(message).child(search ).getRef().setValue(null);
                                 e.setText(null);
                                Toast.makeText(Del.this, "Contact "+search+" Deleted....", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }else {
                    t2.setText("Enter Some text Above");
                }
            }
        });

    }
        }
