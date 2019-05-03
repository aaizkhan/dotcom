package com.dcservicez.a247services;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends Activity {

    EditText fullName, mobileNo, email, pass, Cpassword;
    Spinner spn_gender;
    RadioGroup radioSp;
    String item;
    String spCondition;

       ArrayList<String> chategories = new ArrayList<>();
        ArrayList<String> nodes = new ArrayList<>();

    String[] items = new String[] {"-Select-","Male", "Female","Other"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getvies();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_gender.setAdapter(adapter);


        spn_gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (parent.getItemAtPosition(position)=="-Select-"){
                    return;
                }else if (parent.getItemAtPosition(position)=="Male"){

                    item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                    return;
                }else if (parent.getItemAtPosition(position)=="Female"){

                    item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                    return;
                }else if (parent.getItemAtPosition(position)=="Other"){

                    item = parent.getItemAtPosition(position).toString();

                    Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
                    return;
                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });







        // Write a message to the database here im again
 /*       FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference categories_ref = database.getReference("Catogery");

        categories_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    chategories.add(snapshot.getValue().toString());
                    nodes.add(snapshot.getKey().toString());
                }

                final int size = chategories.size();
                String[] temp_strings = new String[size];

                for (int i = 0; i < size; i++) {
                    temp_strings[i] = chategories.get(i);
                }

                Toast.makeText(MainActivity.this, "nodes_size" + size, Toast.LENGTH_SHORT).show();


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
       */



    }


    void listiners() {

    }

    void getvies() {
        fullName = (EditText) findViewById(R.id.edt_fullName);
        mobileNo = (EditText) findViewById(R.id.edt_mobile);
        email = (EditText) findViewById(R.id.edt_email);
        pass = (EditText) findViewById(R.id.edt_pass);
        Cpassword = (EditText) findViewById(R.id.confirm_password);
        spn_gender = (Spinner) findViewById(R.id.spn_gender);
        radioSp = (RadioGroup) findViewById(R.id.radio_sp);
    }


    public void register(String fullname, String mobile, String email, String pass, String CPassword) {

        int i = 0;

        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users");
        String key = firebaseDatabase.push().getKey();


        firebaseDatabase.child(key).child("fullname").setValue(fullname);
        firebaseDatabase.child(key).child("mobile no").setValue(mobile);
        firebaseDatabase.child(key).child("Email").setValue(email);
        firebaseDatabase.child(key).child("password").setValue(pass);
        firebaseDatabase.child(key).child("confirmPassword").setValue(CPassword);
        firebaseDatabase.child(key).child("Gender").setValue(item);
       // firebaseDatabase.child(key).child("Gender").


    }

    public void SignUp(View view) {





            try {

                register(fullName.getText().toString(), mobileNo.getText().toString(), email.getText().toString(), pass.getText().toString(), Cpassword.getText().toString());
            } catch (Exception e) {
                Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

            }
        }



    public void back_signUp(View view) {
        Intent intent=new Intent(MainActivity.this,SignIn.class);
        startActivity(intent);
        finish();
    }



}

