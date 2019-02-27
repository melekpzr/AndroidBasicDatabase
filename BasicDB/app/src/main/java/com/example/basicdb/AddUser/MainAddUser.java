package com.example.basicdb.AddUser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicdb.DBHelper;
import com.example.basicdb.DeleteUser.MainDeleteUser;
import com.example.basicdb.MainActivity;
import com.example.basicdb.UpdateUser.MainUpdateUser;
import com.example.basicdb.User;
import com.example.basicdb.R;

public class MainAddUser extends AppCompatActivity {

    private TextView mTextMessage;
    ArrayAdapter adaptor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    // mTextMessage.setText(R.string.title_home);
                    Toast.makeText(MainAddUser.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanHome = new Intent(MainAddUser.this, MainActivity.class);
                    startActivity(intocanHome);

                    return true;
                case R.id.kayit_ekle:

                    return true;
                case R.id.navigation_update:
                    // mTextMessage.setText(R.string.title_update);
                    Toast.makeText(MainAddUser.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanUpdate = new Intent(MainAddUser.this, MainUpdateUser.class);
                    startActivity(intocanUpdate);

                    return true;
                case R.id.navigation_delete:
                    Toast.makeText(MainAddUser.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanDelete = new Intent(MainAddUser.this, MainDeleteUser.class);
                    startActivity(intocanDelete);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_user);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button btnRegister = (Button) findViewById(R.id.btnRegister);
        Button btnGet = (Button) findViewById(R.id.btnGet);
        final ListView userListView = (ListView) findViewById(R.id.userList);

        DBHelper helper = DBHelper.getInstance(getApplicationContext());
        adaptor = helper.tumKayitlar(getApplicationContext());
        userListView.setAdapter(adaptor);
        // userListView.setTextFilterEnabled(true);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = ((EditText) findViewById(R.id.reg_userName)).getText().toString();
                String password = ((EditText) findViewById(R.id.reg_password)).getText().toString();
                User user = new User();
                user.setUserName(userName);
                user.setPassword(password);
                DBHelper helper = DBHelper.getInstance(getApplicationContext());
                long result = helper.createUser(user);

                if (result > 0) {
                    Toast.makeText(MainAddUser.this,"Yeni kullanıcı eklendi", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainAddUser.this,"Kullanıcı eklenirken hata oluştu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DBHelper helper = DBHelper.getInstance(getApplicationContext());

                // List<User> userList = helper.getAllUsers();


                //ArrayAdapter<User> veriAdaptoru=new ArrayAdapter<User>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, isimler);
                //(C) adımı
                // userListView.setAdapter(veriAdaptoru);

                adaptor = helper.tumKayitlar(getApplicationContext());
                userListView.setAdapter(adaptor);
               /* String userNames = "";
                for (User user : userList) {
                    userNames += user.getUserName();
                }*/
                //    TextView resultView = (TextView) findViewById(R.id.resultMessage);
                //   resultView.setText(userNames);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DBHelper openHelper = DBHelper.getInstance(getApplicationContext());
        if (openHelper != null) {
            openHelper.closeDB();
        }
    }
}
