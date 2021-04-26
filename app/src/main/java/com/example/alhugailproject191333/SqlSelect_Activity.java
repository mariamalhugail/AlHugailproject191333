package com.example.mobilefinal;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SqlSelect_Activity extends AppCompatActivity {

// array
ArrayList<User> user;
    EditText useridinput;
    ListView listv;
    TableLayout t;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectsql);
        DataBaseHelperActivity dataBaseHelperActivity =new DataBaseHelperActivity(this);
        listv=findViewById(R.id.listview);
        useridinput=findViewById(R.id.inputuserid);
        Button select=findViewById(R.id.sqlselectbutton);

// button select function:
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid=useridinput.getText()+"";
                Cursor cur;
                if (userid.isEmpty()){
                    cur= dataBaseHelperActivity.selectAll();
                }else{
                    cur= dataBaseHelperActivity.selectByUserID(Integer.valueOf(userid));
                }
                if (cur==null){
                    Toast.makeText(SqlSelect_Activity.this,"Records Not found.",Toast.LENGTH_SHORT).show();
                    return;
                }
                user.clear();
                do {
                    User userr = new User();
                    userr.firstName=cur.getString(0);
                    userr.lastName=cur.getString(1);
                    userr.phoneNumber=cur.getString(2);
                    userr.emailAddress=cur.getString(3);
                    userr.userId=cur.getInt(4);
                    user.add(userr);
                }while (cur.moveToNext());
  ((BaseAdapter)listv.getAdapter()).notifyDataSetChanged();
            } });

        // list view:
        listv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User u= user.get(position);

                Toast.makeText(SqlSelect_Activity.this,u.getFirstname()+" "+ u.getLastname(),Toast.LENGTH_SHORT).show();
            }
        });
             user=new ArrayList<>();
            listv.setAdapter(new BaseAdapter() {
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
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                User uinfo=user.get(position);
      t=(TableLayout) LayoutInflater.from(SqlSelect_Activity.this).inflate(R.layout.list_item,parent,false);

      // text view from list item activity:
                TextView useridoutput=t.findViewById(R.id.userview);
                TextView nameoutput=t.findViewById(R.id.nameview);
                TextView phoneoutput=t.findViewById(R.id.phoneview);
                TextView emailuotput=t.findViewById(R.id.emailview);

                useridoutput.setText(""+uinfo.getUserId());
                nameoutput.setText(uinfo.getFirstname()+" "+uinfo.getLastname());
                phoneoutput.setText(uinfo.getPhoneNumber());
                emailuotput.setText(uinfo.getEmailaddress());

                return t;   }    });

    }
}