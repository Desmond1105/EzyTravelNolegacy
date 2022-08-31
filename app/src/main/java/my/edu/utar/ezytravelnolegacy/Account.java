package my.edu.utar.ezytravelnolegacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class Account extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        // Set Account selected
        bottomNavigationView.setSelectedItemId(R.id.nav_account);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_account:
                        return true;
                    case R.id.nav_checklist:
                        Intent int1 = new Intent(Account.this, Checklist.class);
                        startActivity(int1);
                        return true;
                    case R.id.nav_planner:
                        Intent int2 = new Intent(Account.this, Planner.class);
                        startActivity(int2);
                        return true;
                    case R.id.nav_spending:
                        Intent int3 = new Intent(Account.this, Spending.class);
                        startActivity(int3);
                        return true;
                }
                return false;
            }
        });
    }
}