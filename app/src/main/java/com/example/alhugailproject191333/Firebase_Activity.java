package com.example.mobilefinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import com.google.firebase.database.IgnoreExtraProperties;
public class Firebase_Activity extends AppCompatActivity {

    // ------//
 RequestQueue requestQueueq;
 FirebaseDatabase database;
 DatabaseReference dataref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_activity);
        setup();
        requestQueueq= Volley.newRequestQueue(this);
        requestQueueq.add(Helper.weather(this));
        // button  text call
        Button selectbtn=(Button)findViewById(R.id.selectoptbtn);
        Button insertbtn=(Button)findViewById(R.id.insertbtn);
        Button deletebtn=(Button)findViewById(R.id.deletebtn);
        Button updatebtn=(Button)findViewById(R.id.updatebtn);
        // edit text call
        EditText firstnameinput =(EditText) findViewById(R.id.firstnameinput);
        EditText lastnameinput=(EditText) findViewById(R.id.lastnameinput);
        EditText phonenuminput=(EditText) findViewById(R.id.phonenuminput);
        EditText emailinput=(EditText) findViewById(R.id.emailinput);
        EditText useridinput=(EditText) findViewById(R.id.useridinput);

//select btn:
        selectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Firebase_Activity.this,FirebaseList.class));
            }
        });
//insert btn:
        insertbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fistname= firstnameinput.getText()+"";
                String lastname= lastnameinput.getText()+"";
                String phonenum= phonenuminput.getText()+"";
                String Email= emailinput.getText()+"";
                String userid= useridinput.getText()+"";

                if (fistname.isEmpty() || lastname.isEmpty() || phonenum.isEmpty() || Email.isEmpty() || userid.isEmpty()){
                    Toast.makeText(Firebase_Activity.this,"Fill All Required fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                insertUser(Email,fistname,lastname,phonenum,Integer.valueOf(userid));
            }
        });
        // delete btn:
        deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid=useridinput.getText()+"";
                if (userid.isEmpty()){
                    Toast.makeText(Firebase_Activity.this,"Fill User Id Required field",Toast.LENGTH_SHORT).show();
                    return;
                }
                delete(Integer.valueOf(userid));
            }
        });
//update btn:
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fistname= firstnameinput.getText()+"";
                String lastname= lastnameinput.getText()+"";
                String phonenum= phonenuminput.getText()+"";
                String Email= emailinput.getText()+"";
                String userid= useridinput.getText()+"";
                HashMap<String,Object> map=new HashMap<>();

                if (userid.isEmpty()){
                    Toast.makeText(Firebase_Activity.this,"Fill User Id Required field",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!fistname.isEmpty()){
                    map.put("firstName",fistname);
                }
                if (!lastname.isEmpty()){
                    map.put("lastName",lastname);
                }
                if (!phonenum.isEmpty()){
                    map.put("phoneNumber",phonenum);
                }
                if (!Email.isEmpty()){
                    map.put("emailAddress",Email);
                }
                if (!userid.isEmpty()){
                    map.put("userId",Integer.valueOf(userid));
                }
                update(Integer.valueOf(userid),map);
            }}); }


    private void setup(){
        database = FirebaseDatabase.getInstance();
        dataref = database.getReference("users");

    }
    //--------------------insert void method ----------------------------------------------------//
    private void insertUser(String email,String fName,String lName,String phone,int userId){

        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot child:snapshot.getChildren()){
                    if (child.child("userId").getValue(Integer.class)==userId){
                        Toast.makeText(Firebase_Activity.this,"Record with User ID "+userId+" was found.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                int initialCount=(int)snapshot.getChildrenCount();
                while(snapshot.hasChild(initialCount+"")){
                    initialCount++;
                }
                DatabaseReference insertintoRef= dataref.child(initialCount+"");


                insertintoRef.child("emailAddress").setValue(email);
                insertintoRef.child("firstName").setValue(fName);
                insertintoRef.child("lastName").setValue(lName);
                insertintoRef.child("phoneNumber").setValue(phone);
                insertintoRef.child("userId").setValue(userId);
                Toast.makeText(Firebase_Activity.this,"Record Inserted Successfully",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void updateUserAllDetails(String email,String fName,String lName,String phone,int userId){
        Map<String,Object> stringObjectMap=new HashMap<>();
        stringObjectMap.put("emailAddress",email);
        stringObjectMap.put("firstName",fName);
        stringObjectMap.put("lastName",lName);
        stringObjectMap.put("phoneNumber",phone);
        stringObjectMap.put("userId",userId);

        update(userId,stringObjectMap);
    }
    //--------------------delete void method ----------------------------------------------------//
    private void delete(int uid){
        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot child:snapshot.getChildren()){
                    if (child.child("userId").getValue(Integer.class)==uid){
                        dataref.child(child.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Firebase_Activity.this,"The record Deleted successfully.",Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Firebase_Activity.this,"error: "+e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                        return;
                    }
                }
                Toast.makeText(Firebase_Activity.this,"User Id not found",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

//--------------------update void method ----------------------------------------------------//
    private void update(int userid,Map<String,Object> keyValMap){

        dataref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot child:snapshot.getChildren()){
                    if (child.child("userId").getValue(Integer.class)==userid){
                        dataref.child(child.getKey()).updateChildren(keyValMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(Firebase_Activity.this,"Record Updated Successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Firebase_Activity.this,"error: "+ e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                        return;
                    }
                }
                Toast.makeText(Firebase_Activity.this,"User Id not found",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @IgnoreExtraProperties
    class User{

        String firstName;
        String lastName;
        String emailAddress;
        String phoneNumber;
        int userId;



        public User(){

        }

        public int getUserId()
        {
            return userId;
        }

        public void setUserId(int userId)
        {
            this.userId = userId;
        }

        public String getEmailAddress()
        {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress)
        {
            this.emailAddress = emailAddress;
        }

        public String getPhoneNumber() {

            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber)
        {
            this.phoneNumber = phoneNumber;
        }

        public String getFirstName()
        {
            return firstName;
        }

        public void setFirstName(String firstName)
        {
            this.firstName = firstName;
        }

        public String getLastName()
        {
            return lastName;
        }

        public void setLastName(String lastName)
        {
            this.lastName = lastName;
        }
    }



}

