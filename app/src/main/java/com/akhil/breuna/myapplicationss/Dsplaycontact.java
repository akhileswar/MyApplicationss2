package com.akhil.breuna.myapplicationss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import  android.view.*;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import static com.akhil.breuna.myapplicationss.R.styleable.View;
import static java.security.AccessController.getContext;

public class
Dsplaycontact extends AppCompatActivity {
    ListView listview;
    FirebaseAuth mAuth;
    public ProgressDialog p;
    Firebase mref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.displaycontact);
        Firebase.setAndroidContext(this);
        mref=new Firebase("https://myapply-cc0e7.firebaseio.com/");
        mAuth=FirebaseAuth.getInstance();
        listview=(ListView)findViewById(R.id.lopen);
        final ArrayList<String> ar=new ArrayList<String>();
        final ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ar);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("message");


        FirebaseUser user=mAuth.getCurrentUser();
        p=new ProgressDialog(this);

        p.setMessage("Loading-Contacts...");
        p.show();


        mref.child("contacts").child(message).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds :dataSnapshot.getChildren()){
                   String val= ds.child("name").getValue(String.class);
                    String ph=ds.child("phone").getValue(String.class);
                    String h=val.concat("-"+ph);
                    ar.add(h);


                }

                listview.setAdapter(adapter);
                p.hide();

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View myView, int pos, long mylng) {
                        String selectedFromList =(listview.getItemAtPosition(pos).toString());
                        // this is your selected item

                        String[] num=new String[2];
                        num=selectedFromList.split("-");
                        //int ph_num=Integer.parseInt(num[1]);


                        Uri number = Uri.parse("tel:"+
                                selectedFromList);
                        Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                        callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(callIntent);



                    //    Toast.makeText(Dsplaycontact.this, num[1], Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
