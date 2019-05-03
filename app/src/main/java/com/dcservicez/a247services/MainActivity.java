package com.dcservicez.a247services;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.dcservicez.a247services.Extras.View_Mnagae;
import com.dcservicez.a247services.debugs.Debug;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends Activity {

   private EditText fullName, mobileNo, email, pass, Cpassword;
     Spinner spinner_gndr;
    private  RadioGroup radioSp;
    private Context context;
    RadioButton rd_btn;

    Debug debug;

    String srvc,srvc_exp,srvc_des;

  //  ArrayList<String> chategories = new ArrayList<>();
  //  ArrayList<String> nodes = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getvies();
        context=this;
        fill_genders();
        debug=new Debug(context);
        listiners();



        // Write a message to the database here im again
     /*   FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference categories_ref = database.getReference("Users");

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==92333){
            Intent i=data;
            srvc=i.getStringExtra("svrc");
            srvc_des=i.getStringExtra("svrc_exp");
            srvc_exp=i.getStringExtra("svrc_desc");

        }
    }

    void listiners() {
            radioSp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if(i==R.id.radioBtn_yes){
                        startActivityForResult(new Intent(context,Signup_sp.class),92333);
                    }
//                    debug.print("chosed"+i);
                }
            });
    }

    void fill_genders(){
        String []genders_data={"Male","Female","Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                context, R.layout.spinner_item, genders_data);
        spinner_gndr.setAdapter(adapter);

        spinner_gndr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this,chategories.get(position),Toast.LENGTH_SHORT).show();
                debug.print("selected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void getvies() {
        fullName = (EditText) findViewById(R.id.edt_fullName);
        mobileNo = (EditText) findViewById(R.id.edt_mobile);
        email = (EditText) findViewById(R.id.edt_email);
        pass = (EditText) findViewById(R.id.edt_pass);
        Cpassword = (EditText) findViewById(R.id.confirm_password);
        spinner_gndr = (Spinner) findViewById(R.id.spn_gender);
        radioSp = (RadioGroup) findViewById(R.id.radio_sp);
         rd_btn=(RadioButton)findViewById(R.id.radioBtn_yes);
    }


    public void register_srvc(boolean isService) {


        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference("Users").child(srvc);
        String key = firebaseDatabase.push().getKey();


        firebaseDatabase.child(key).child("fullname").setValue(fullName.getText().toString());
        firebaseDatabase.child(key).child("mobile_no").setValue(mobileNo.getText().toString());
        firebaseDatabase.child(key).child("Email").setValue(email.getText().toString());
        firebaseDatabase.child(key).child("password").setValue(pass.getText().toString());
        firebaseDatabase.child(key).child("service_des").setValue(srvc_des);
        firebaseDatabase.child(key).child("service_exp").setValue(srvc_exp);



    }

    boolean isValid(){

        View_Mnagae viewMnagae=new View_Mnagae(context);
        viewMnagae.remove_error(fullName);
        viewMnagae.remove_error(mobileNo);
        viewMnagae.remove_error(email);
        viewMnagae.remove_error(pass);
        viewMnagae.remove_error(Cpassword);

        if(fullName.getText().toString().isEmpty()){
            fullName.setError("Enter your name here ");
            return false;
        }

        if(mobileNo.getText().toString().isEmpty()){
            mobileNo.setError("Enter your phone# here ");
            return false;
        }

        if(email.getText().toString().isEmpty()){
            email.setError("Enter your email here ");
            return false;
        }

        if(pass.getText().toString().isEmpty()){
            pass.setError("Enter password here ");
            return false;
        }
        if(!pass.getText().toString().equals(Cpassword.getText().toString())){
            Cpassword.setError("Confirm pass is not matched");
            return false;
        }

        return true;


    }


    public void SignUp(View view) {
        //here check all the fileds are correctly filled


        try {

            if(true){//isValid()){
                //now check if service provider or not

                if(rd_btn.isChecked()){
                    ///go for sp
//                startActivityForResult(new Intent(context,Signup_sp.class),9233);
                    register_srvc(true);
                }else{
                    register_srvc(false);

                }
            }
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