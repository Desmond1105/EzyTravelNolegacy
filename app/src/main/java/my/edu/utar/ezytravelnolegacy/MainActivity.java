package my.edu.utar.ezytravelnolegacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set Planner selected
        bottomNavigationView.setSelectedItemId(R.id.nav_planner);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_account:
                        Intent int1 = new Intent(MainActivity.this, Account.class);
                        startActivity(int1);
                        return true;
                        case R.id.nav_checklist:
                            Intent int2 = new Intent(MainActivity.this, Checklist.class);
                            startActivity(int2);
                        return true;
                    case R.id.nav_spending:
                        Intent int3 = new Intent(MainActivity.this, Spending.class);
                        startActivity(int3);
                        return true;
                    case R.id.nav_planner:
                        Intent int4 = new Intent(MainActivity.this, TripActivity.class);
                        startActivity(int4);
                        return true;
                }
                return false;
            }
        });
    }
}
