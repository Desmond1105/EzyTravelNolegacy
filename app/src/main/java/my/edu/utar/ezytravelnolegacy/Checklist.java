package my.edu.utar.ezytravelnolegacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class Checklist extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);
// Initialize and assign variable
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        // Set Account selected
        bottomNavigationView.setSelectedItemId(R.id.nav_checklist);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_checklist:
                        return true;
                    case R.id.nav_account:
                        Intent int1 = new Intent(Checklist.this, Account.class);
                        startActivity(int1);
                        return true;
                    case R.id.nav_planner:
                        Intent int2 = new Intent(Checklist.this, Planner.class);
                        startActivity(int2);
                        return true;
                    case R.id.nav_spending:
                        Intent int3 = new Intent(Checklist.this, Spending.class);
                        startActivity(int3);
                        return true;
                }
                return false;
            }
        });
    }
}