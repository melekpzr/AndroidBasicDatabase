package com.example.basicdb.DeleteUser;

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
import android.widget.Toast;

import com.example.basicdb.AddUser.MainAddUser;
import com.example.basicdb.DBHelper;
import com.example.basicdb.MainActivity;
import com.example.basicdb.R;
import com.example.basicdb.UpdateUser.MainUpdateUser;
import com.example.basicdb.User;

public class MainDeleteUser extends AppCompatActivity {

    private long userIDL;
    private User user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    // mTextMessage.setText(R.string.title_home);
                    Toast.makeText(MainDeleteUser.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanHome = new Intent(MainDeleteUser.this, MainActivity.class);
                    startActivity(intocanHome);

                    return true;
                case R.id.kayit_ekle:
                    // mTextMessage.setText(R.string.title_home);
                    Toast.makeText(MainDeleteUser.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanAdd = new Intent(MainDeleteUser.this, MainAddUser.class);
                    startActivity(intocanAdd);

                    return true;
                case R.id.navigation_update:
                    // mTextMessage.setText(R.string.title_dashboard);
                    Toast.makeText(MainDeleteUser.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanUpdate = new Intent(MainDeleteUser.this, MainUpdateUser.class);
                    startActivity(intocanUpdate);
                    return true;
                case R.id.navigation_delete:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_user);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        Button btnGetir = (Button)findViewById(R.id.delete_btn_gtr);

        btnGetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = ((EditText) findViewById(R.id.delete_id)).getText().toString();
                userIDL =Long.valueOf(userId);

                DBHelper helper = DBHelper.getInstance(getApplicationContext());
                user = helper.getUser(userIDL);

                EditText textIsim = (EditText)findViewById(R.id.delete_isim);
                EditText textpass = (EditText)findViewById(R.id.delete_pass);

                textIsim.setText(user.getUserName().toString());
                textpass.setText(user.getPassword().toString());
            }
        });

        Button btnSil = (Button)findViewById(R.id.delete_btn_sil);
        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper helper = DBHelper.getInstance(getApplicationContext());
                helper.deleteUser((int)userIDL);
                try{
                    helper.deleteUser((int)userIDL);
                    Toast.makeText(MainDeleteUser.this,"Kullanıcı silindi", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    Toast.makeText(MainDeleteUser.this,"Kullanıcı silinirken hata oluştu", Toast.LENGTH_SHORT).show();
                }


               /* if (result > 0) {
                    Toast.makeText(MainDeleteUser.this,"Kullanıcı silindi", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainDeleteUser.this,"Kullanıcı silinirken hata oluştu", Toast.LENGTH_SHORT).show();
                }*/

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
