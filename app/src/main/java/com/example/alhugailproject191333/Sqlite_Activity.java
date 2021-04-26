package com.example.mobilefinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Sqlite_Activity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference dataref;

    private void setup(){
        database = FirebaseDatabase.getInstance();
        dataref = database.getReference("users");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        setup();
        DataBaseHelperActivity dataBaseHelperActivity =new DataBaseHelperActivity(this);
// Edittexts:
       EditText firstname= (EditText)findViewById(R.id.firstnameinputt);
        EditText lastname= (EditText)findViewById(R.id.lastnameinputt);
        EditText phonenum= (EditText)findViewById(R.id.phoneinput);
        EditText emaill= (EditText)findViewById(R.id.emailinputt);
        EditText userid= (EditText)findViewById(R.id.useridinputt);
// Buttons:
        Button insert=(Button)findViewById(R.id.insertbttn);
        Button update=(Button)findViewById(R.id.updatebttn);
        Button selectopt=(Button)findViewById(R.id.selectbttn);
        Button delete=(Button)findViewById(R.id.deletebttn);
        Button firebaseinsert=(Button)findViewById(R.id.firebaseinbttn);

// insert button function
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstn= firstname.getText()+"";
                String lastn= lastname.getText()+"";
                String phonee= phonenum.getText()+"";
                String email= emaill.getText()+"";
                String userrid= userid.getText()+"";

                if ( firstn.isEmpty() || lastn.isEmpty() || phonee.isEmpty() || email.isEmpty() || userrid.isEmpty()){
                    Toast.makeText(Sqlite_Activity.this,"Fill all required fields",Toast.LENGTH_SHORT).show(); }
                 int x= dataBaseHelperActivity.insertdata(firstn,lastn,phonee,email,Integer.valueOf(userrid));
                if (x!=-1){
                    Toast.makeText(Sqlite_Activity.this,"Inserted RecordRecord Successfully",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Sqlite_Activity.this,"Inserted Record Unsuccessfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
        // update button function:
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstn= firstname.getText()+"";
                String lastn= lastname.getText()+"";
                String phonee= phonenum.getText()+"";
                String email= emaill.getText()+"";
                String userrid= userid.getText()+"";

                if (
                        firstn.isEmpty() ||
                                lastn.isEmpty() ||
                                phonee.isEmpty() ||
                                email.isEmpty() ||
                                userrid.isEmpty()){
                    Toast.makeText(Sqlite_Activity.this,"Fill required all fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                int i= dataBaseHelperActivity.updateUserinfo(firstn,lastn,phonee,email,Integer.valueOf(userrid));
                if (i>0){
                    Toast.makeText(Sqlite_Activity.this,"Updated Record Successfully",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Sqlite_Activity.this,"Updated Record Unsuccessfully",Toast.LENGTH_SHORT).show();
                }
            }
        });
        // select option button function :
        selectopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Sqlite_Activity.this, SqlSelect_Activity.class));
            }
        });
// delete button function:
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId=userid.getText()+"";
                if (userId.isEmpty() ){
                    Toast.makeText(Sqlite_Activity.this,"Fill User Id Required field",Toast.LENGTH_SHORT).show();
                    return;
                }
                int i= dataBaseHelperActivity.deleteByUserID(Integer.valueOf(userId));
                if (i>0){
                    Toast.makeText(Sqlite_Activity.this,"deleted Record Successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Sqlite_Activity.this,"Deleted Record Unsuccessfully",Toast.LENGTH_SHORT).show();

                }
            }
        });
// fire base button function :
      firebaseinsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId=userid.getText()+"";
                if (userId.isEmpty() ){
                    Toast.makeText(Sqlite_Activity.this,"User id required field",Toast.LENGTH_SHORT).show();
                    return;
                }
                dataref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot childData:snapshot.getChildren()){
                            if (childData.child("userId").getValue(Integer.class)==Integer.valueOf(userId)){
                                    User u=snapshot.child(childData.getKey()).getValue(User.class);
                                    int i= dataBaseHelperActivity.insertdata(u);

                                    if (i!=-1){
                                        Toast.makeText(Sqlite_Activity.this,"Inserted Record Successfully",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Sqlite_Activity.this,"Inserted Record Unsuccessfully",Toast.LENGTH_SHORT).show();
                                    }
                                    return;
                            }
                        }
                        Toast.makeText(Sqlite_Activity.this,"User ID not found",Toast.LENGTH_SHORT).show();
                        return;

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
 }  }); }   });   }}