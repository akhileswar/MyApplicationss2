package com.akhil.breuna.myapplicationss;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
public class groups extends Activity {
public ListView lview;
    EditText e;Button b;
    //public TextView tex;
ProgressDialog p;
    FirebaseAuth mAuth;
    Firebase mref;
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        Firebase.setAndroidContext(this);
        mref=new Firebase("https://myapply-cc0e7.firebaseio.com/");
        mAuth=FirebaseAuth.getInstance();

    lview=(ListView)findViewById(R.id.listView);


        final ArrayList<String> ar=new ArrayList<String>();
        final ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ar);


        FirebaseUser user=mAuth.getCurrentUser();

        p=new ProgressDialog(this);

        p.setMessage("Loading-Contacts...");
        p.show();

        mref.child("user_places").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String str=ds.child("place").getValue(String.class);

                   // Toast.makeText(groups.this,str, Toast.LENGTH_SHORT).show();
                    ar.add(str);

                }


                    lview.setAdapter(adapter);

                    p.hide();

                    lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> adapter, View myView, int pos, long mylng) {
                            String selectedFromList = (lview.getItemAtPosition(pos).toString());
                            // this is your selected item

                            Intent i = new Intent(groups.this, Dsplaycontact.class);
                            i.putExtra("message", selectedFromList);
                            startActivity(i);

                        }
                    });


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

//

//
//on create
    }
}
