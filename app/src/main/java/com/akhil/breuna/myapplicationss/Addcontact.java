package com.akhil.breuna.myapplicationss;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Addcontact extends AppCompatActivity {
    Button add;
    TextView t1,t2,t3;
    EditText e,e1;


public int l=0;

    ProgressDialog m;
    TextView t;
    FirebaseAuth mAuth;
    Firebase mref;
    FirebaseAuth.AuthStateListener mAuthstate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);
        mref=new Firebase("https://myapply-cc0e7.firebaseio.com/");
        mAuth=FirebaseAuth.getInstance();
        setContentView(R.layout.addcontact);
        add = (Button) findViewById(R.id.button);

        t1=(TextView)findViewById(R.id.textView2);
        t3=(TextView)findViewById(R.id.textView4);
        t2=(TextView)findViewById(R.id.textView3);
        e=(EditText)findViewById(R.id.editText);
        e1=(EditText)findViewById(R.id.editText3);


        m=new ProgressDialog(this);

        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("message");

        e.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if(hasFocus){
                    t1.setText("Enter contact-name");
                    t2.setText(null);
t3.setText(null);
                }
            }
        });

        e1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                t2.setText("Enter-phone number");
                t1.setText(null);
t3.setText(null);
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                l=0;
                final String name=e.getText().toString();
                final String phone=e1.getText().toString();

                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(phone)) {
                    final FirebaseUser user = mAuth.getCurrentUser();

                    mref.child("contacts").child(message).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren()){

                                String namer=ds.child("name").getValue(String.class);

                                if(namer.equals(name)){

                                    l++;
                                }
                            }

                            if(l==0){
                                mref.child("contacts").child(message).child(name ).child("name").setValue(name);
                                mref.child("contacts").child(message).child(name ).child("phone").setValue(phone);
                                Toast.makeText(Addcontact.this, "Added to your-contacts...", Toast.LENGTH_LONG).show();
                                t2.setText(null);
                                t1.setText(null);
                                e1.setText(null);
                                e.setText(null);
                                t3.setText(null);
                            }
                            else{
                                Toast.makeText(Addcontact.this, "contact-Name already exist...", Toast.LENGTH_LONG).show();
                                t2.setText(null);
                                t1.setText(null);
                                e1.setText(null);
                                e.setText(null);
                                t3.setText(null);
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }else
                    t3.setText("Please Fill All Fields");
            }
        });




    }


}
