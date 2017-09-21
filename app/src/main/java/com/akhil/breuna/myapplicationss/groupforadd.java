package com.akhil.breuna.myapplicationss;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
public class groupforadd extends AppCompatActivity {
    public ListView lview;
    EditText e;Button b;
    public ProgressDialog p;

    FirebaseAuth mAuth;
    Firebase mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupforadd);

        Firebase.setAndroidContext(this);
        mref=new Firebase("https://myapply-cc0e7.firebaseio.com/");
        mAuth=FirebaseAuth.getInstance();
p=new ProgressDialog(this);
        lview=(ListView)findViewById(R.id.listView);
        e=(EditText)findViewById(R.id.editText9);
        b= (Button) findViewById(R.id.button8);

        final ArrayList<String> ar=new ArrayList<String>();
        final ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ar);

        final FirebaseUser user=mAuth.getCurrentUser();
p.setMessage("Loading....");
        p.show();
        mref.child("user_places").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String str=ds.child("place").getValue(String.class);

                    ar.add(str);

                }

                lview.setAdapter(adapter);
                p.hide();
                lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    public void onItemClick(AdapterView<?> adapter, View myView, int pos, long mylng) {
                        String selectedFromList =(lview.getItemAtPosition(pos).toString());
                        // this is your selected item

                        Intent i=new Intent(groupforadd.this,Addcontact.class);
                        i.putExtra("message",selectedFromList);
                        startActivity(i);

                    }
                });


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


//

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=e.getText().toString();
                if(!TextUtils.isEmpty(text)){
                    mref.child("user_places").child(text+"id").child("place").setValue(text);
                    Toast.makeText(groupforadd.this," group Added",Toast.LENGTH_SHORT).show();
                    e.setText(null);
                    ar.add(text);
                    lview.setAdapter(adapter);
                }else
                    Toast.makeText(groupforadd.this,"enter the group",Toast.LENGTH_SHORT).show();
            }
        });
//
//on create
    }
}
