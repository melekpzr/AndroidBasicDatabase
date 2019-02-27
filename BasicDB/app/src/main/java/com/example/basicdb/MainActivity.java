package com.example.basicdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basicdb.AddUser.MainAddUser;
import com.example.basicdb.DeleteUser.MainDeleteUser;
import com.example.basicdb.UpdateUser.MainUpdateUser;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter adaptor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.kayit_ekle:

                    Toast.makeText(MainActivity.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanAdd = new Intent(MainActivity.this, MainAddUser.class);
                    startActivity(intocanAdd);

                    return true;
                case R.id.navigation_update:

                    Toast.makeText(MainActivity.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanUpdate = new Intent(MainActivity.this, MainUpdateUser.class);
                    startActivity(intocanUpdate);
                    return true;
                case R.id.navigation_delete:

                    Toast.makeText(MainActivity.this,"Loading..", Toast.LENGTH_SHORT).show();
                    Intent intocanDelete = new Intent(MainActivity.this, MainDeleteUser.class);
                    startActivity(intocanDelete);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final ListView userListView = (ListView) findViewById(R.id.userListView);

        DBHelper helper = DBHelper.getInstance(getApplicationContext());
        adaptor = helper.tumKayitlar(getApplicationContext());
        userListView.setAdapter(adaptor);


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
