package com.example.mobilefinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseList extends AppCompatActivity {
    ListView listv;
    ArrayList<User> fetchedUser =new ArrayList<>();
    ArrayList<User> user =new ArrayList<>();
    int useridDisplay = -1;
    DatabaseReference dataref;
    FirebaseDatabase firedatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_list);
        setup();
// edit text :
        EditText selecteditx =(EditText) findViewById(R.id.useridselectinput);

        TextWatcher textw =new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length()==0){
                    useridDisplay=-1;
                }else{
                    useridDisplay=Integer.valueOf(editable.toString());
                }  update();  }    };
        selecteditx.addTextChangedListener(textw);
        listv=findViewById(R.id.firebaselistview);
        listv.setAdapter(new BaseAdapter() {

// get the view:
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                User user1=user.get(position);
                System.out.println(user1);
                TableLayout table=(TableLayout) LayoutInflater.from(FirebaseList.this).inflate(R.layout.list_item,parent,false);
                TextView out_uid=table.findViewById(R.id.userview);
                TextView out_name=table.findViewById(R.id.nameview);
                TextView out_phone=table.findViewById(R.id.phoneview);
                TextView out_email=table.findViewById(R.id.emailview);
                out_uid.setText(""+user1.userId);
                out_name.setText(user1.firstName+" "+user1.lastName);
                out_phone.setText(user1.phoneNumber);
                out_email.setText(user1.emailAddress);

                return table;
            }

            @Override
            public int getCount()
            {
                return user.size();
            }

            @Override
            public Object getItem(int position)
            {

                return user.get(position);
            }

            @Override
            public long getItemId(int position)
            {
                return 0;
            }
        });

        dataref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             fetchedUser=snapshot.getValue(new GenericTypeIndicator<ArrayList<User>>() {});
             ArrayList<User> newUsersList=new ArrayList<>();
                for (User x:fetchedUser){
                    if (x!=null){
                        newUsersList.add(x);
                    }
                }
                fetchedUser=newUsersList;
                update();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("FETCH",error.getMessage());
                Toast.makeText(FirebaseList.this,"Error: "+error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });  }
    private void setup(){
        firedatabase = FirebaseDatabase.getInstance();
        dataref = firedatabase.getReference("users");

    }

    private void update(){
        if (useridDisplay!=-1){
            for (User u:fetchedUser) {
                if (useridDisplay==u.getUserId()){
                    user.clear();
                    user.add(u);
                }
            }
        }else{
            user.clear();
            user.addAll(fetchedUser);
        }
        ((BaseAdapter)listv.getAdapter()).notifyDataSetChanged();
    }


}