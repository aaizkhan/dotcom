package com.dcservicez.a247services;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends Activity {

    EditText fullName,mobileNo,email,password,confirmPassword;
    Spinner spinner;

    ArrayList<String> chategories=new ArrayList<>();
    ArrayList<String> nodes=new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getvies();

        // Write a message to the database
        FirebaseApp.initializeApp(this);
         FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference categories_ref = database.getReference("categories");
        categories_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot:dataSnapshot.getChildren()) {
                    chategories.add(snapshot.getValue().toString());
                    nodes.add(snapshot.getKey().toString());
                }

                final int size=chategories.size();
                String[] temp_strings=new String[size];

                for(int i=0;i<size;i++){
                    temp_strings[i]=chategories.get(i);
                }

                Toast.makeText(MainActivity.this,"nodes_size"+size,Toast.LENGTH_SHORT).show();


                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        MainActivity.this, android.R.layout.simple_spinner_item, temp_strings);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


//        Spinner sItems = (Spinner) findViewById(R.id.spinner);
                spinner.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,chategories.get(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void listiners(){

    }
    void getvies(){
        fullName=(EditText)findViewById(R.id.edt_fullName);
        mobileNo=(EditText)findViewById(R.id.edt_mobile);
        spinner=(Spinner) findViewById(R.id.spn_gender);
    }

    void register_user(int i,String name,String phone){
        DatabaseReference firebaseDatabase=FirebaseDatabase.getInstance().getReference("service_provider").child(nodes.get(i));
        String key=firebaseDatabase.push().getKey();

        firebaseDatabase.child(key).child("name").setValue(name);
        firebaseDatabase.child(key).child("phone").setValue(phone);


    }

    public void register_click(View view){

        try {

            register_user(spinner.getSelectedItemPosition(),fullName.getText().toString(),mobileNo.getText().toString());
        }catch (Exception e){
            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
        }


    }
}
