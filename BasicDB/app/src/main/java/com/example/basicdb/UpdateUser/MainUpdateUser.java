package com.example.basicdb.UpdateUser;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicdb.AddUser.MainAddUser;
import com.example.basicdb.DeleteUser.MainDeleteUser;
import com.example.basicdb.MainActivity;
import com.example.basicdb.UpdateUser.MainUpdateUser;
import com.example.basicdb.DBHelper;
import com.example.basicdb.User;
import com.example.basicdb.R;

public class MainUpdateUser extends AppCompatActivity {

    private long userIDL;
    private User user;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

       // Button btnKaydet = (Button)findViewById(R.id.btn_Kaydet);

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:

                    Toast.makeText(MainUpdateUser.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanHome = new Intent(MainUpdateUser.this, MainActivity.class);
                    startActivity(intocanHome);

                    return true;
                case R.id.kayit_ekle:

                    Toast.makeText(MainUpdateUser.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanAdd = new Intent(MainUpdateUser.this, MainAddUser.class);
                    startActivity(intocanAdd);

                    return true;
                case R.id.navigation_update:

                    return true;
                case R.id.navigation_delete:

                    Toast.makeText(MainUpdateUser.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanDelete = new Intent(MainUpdateUser.this, MainDeleteUser.class);
                    startActivity(intocanDelete);
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_user);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button btnGetir = (Button)findViewById(R.id.btn_gtr);

        btnGetir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = ((EditText) findViewById(R.id.reg_id)).getText().toString();
                userIDL =Long.valueOf(userId);

                DBHelper helper = DBHelper.getInstance(getApplicationContext());
                user = helper.getUser(userIDL);

                EditText textIsim = (EditText)findViewById(R.id.reg_isim);
                EditText textpass = (EditText)findViewById(R.id.reg_pass);

                textIsim.setText(user.getUserName().toString());
                textpass.setText(user.getPassword().toString());
            }
        });

        Button btnKaydet = (Button)findViewById(R.id.btn_Kaydet);
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText textIsim = (EditText)findViewById(R.id.reg_isim);
                EditText textpass = (EditText)findViewById(R.id.reg_pass);
                user.setUserName(textIsim.getText().toString());
                user.setPassword(textpass.getText().toString());

                DBHelper helper = DBHelper.getInstance(getApplicationContext());
                int result = helper.updateUser(user);

                if (result > 0) {
                    Toast.makeText(MainUpdateUser.this,"Yeni kullanıcı güncellendi", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainUpdateUser.this,"Kullanıcı güncellenirken hata oluştu", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    protected void onDestroy() {
        super.onDestroy();
        DBHelper openHelper = DBHelper.getInstance(getApplicationContext());
        if (openHelper != null) {
            openHelper.closeDB();
        }
    }
}
