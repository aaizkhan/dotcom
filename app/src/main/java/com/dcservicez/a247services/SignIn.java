package com.dcservicez.a247services;

import android.content.Context;
import android.content.Intent;
import android.app.AlertDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dcservicez.a247services.Notifiers.Dilouges;
import com.dcservicez.a247services.Prefs.Prefs;
import com.dcservicez.a247services.debugs.Debug;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignIn extends AppCompatActivity {

    EditText edt_email,edt_pass;
    Dilouges dilouges;
    Context context;
    Prefs prefs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        getViews();
        context=this;
        dilouges=new Dilouges(context);
        prefs=new Prefs(context);


        Intent intent=new Intent(SignIn.this,Sp_Profile.class);
        startActivity(intent);
        finish();

    }

    void getViews(){
        edt_email=(EditText)findViewById(R.id.edt_signin_email);
        edt_pass=(EditText)findViewById(R.id.edt_signin_password);
    }


    public void redirect_signUp(View view) {
        Intent intent=new Intent(SignIn.this,MainActivity.class);
        startActivity(intent);
//        finish();
    }

    public void login(final  View view){
      final AlertDialog d= (AlertDialog) dilouges.prograss("Login..","wait..",null);
       d.show();
       view.setEnabled(false);

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Users");
        String email =  edt_email.getText().toString().replace(".", ","); // firebaseDatabase.push().getKey();
        prefs.email(email);
        databaseReference.child(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                d.hide();

                if(edt_pass.getText().toString().equals(dataSnapshot.child("password").getValue().toString())){
                    new Debug(context).print("Login success");
                    try {
                        prefs.sverc_type(dataSnapshot.child("service").getValue().toString());
                        new Debug(context).print("you are sp"+dataSnapshot.child("service").getValue().toString());
                    } catch (Exception e) {
                      prefs.sverc_type("");
                        new Debug(context).print("not  sp");
                    }
                    startActivity(new Intent(context,search_service.class));
//                    finish();


                }else {
                    new Debug(context).print("Login not success");
                    view.setEnabled(true);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
