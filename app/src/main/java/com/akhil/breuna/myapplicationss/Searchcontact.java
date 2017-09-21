package com.akhil.breuna.myapplicationss;

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
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Searchcontact extends AppCompatActivity {
EditText e;Button b;
    ListView listview;
    TextView t;
    FirebaseAuth mAuth;
    Firebase mref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchcontact);

        Firebase.setAndroidContext(this);
        mref=new Firebase("https://myapply-cc0e7.firebaseio.com/");
        mAuth=FirebaseAuth.getInstance();

        e=(EditText)findViewById(R.id.editText6);
        b= (Button) findViewById(R.id.button9);
        listview=(ListView)findViewById(R.id.lopen);
        t=(TextView)findViewById(R.id.textView8);
        final ArrayList<String> ar=new ArrayList<String>();
        final ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,ar);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
ar.clear();
               final String search=e.getText().toString();

                if(!TextUtils.isEmpty(search)){

                    FirebaseUser user=mAuth.getCurrentUser();

                    mref.child("contacts").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int k=0;
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {

                               for(DataSnapshot dss : ds.getChildren()){
                                    String namer = dss.child("name").getValue(String.class);

                                    if (namer.contains(search)) {
                                        k++;
                                        String val = dss.child("name").getValue(String.class);
                                        String ph = dss.child("phone").getValue(String.class);
                                        String h = val.concat(" - " + ph);
                                        ar.add(h);

                                    }
                                }
                            }
                            listview.setAdapter(adapter);
                            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                public void onItemClick(AdapterView<?> adapter, View myView, int pos, long mylng) {
                                    String selectedFromList =(listview.getItemAtPosition(pos).toString());
                                    // this is your selected item

                                    String[] num=new String[2];
                                    num=selectedFromList.split("-");
                                    //int ph_num=Integer.parseInt(num[1]);


                                    Uri number = Uri.parse("tel:"+ selectedFromList);
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(callIntent);



                                    //    Toast.makeText(Dsplaycontact.this, num[1], Toast.LENGTH_SHORT).show();
                                }
                            });
                            if(k==0){
                                t.setText("    No Such contact Exist");

                            }else
                            t.setText("    Your-Contacts");
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {


                        }
                    });


                }else {
                    t.setText("Enter Some text in search");
                }
            }
        });


    }
}
