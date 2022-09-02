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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class Spending extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);

        Button addButton = findViewById(R.id.button3);
        EditText spendList = findViewById(R.id.listName);
        EditText spendAmount = findViewById(R.id.spendAmount);
        EditText spendDes = findViewById(R.id.spendDescription);
        CalendarView spendDate = findViewById(R.id.calendarView);
        Button checkSpending = findViewById(R.id.button);

        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
        String selectedDate = sdf.format(new Date(spendDate.getDate()));


        addButton.setOnClickListener(new View.OnClickListener() { // add new card
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                reference = firebaseDatabase.getReference("Spending");

                String sName = spendList.getText().toString();
                String sDescription = spendDes.getText().toString();
                String sAmount = spendAmount.getText().toString();
                String sDate = selectedDate;

                spendingHelper helperClass= new spendingHelper(sName,sDescription,sAmount, sDate);

                reference.child(sDate).setValue(helperClass);
                Toast.makeText(Spending.this, "Added New Spending",Toast.LENGTH_LONG).show();
            }
        });

        checkSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkSpend = new Intent(Spending.this, spendingTable.class);
                startActivity(checkSpend);
            }
        });

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        // Set Account selected
        bottomNavigationView.setSelectedItemId(R.id.nav_spending);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_spending:
                        return true;
                    case R.id.nav_account:
                        Intent int1 = new Intent(Spending.this, Account.class);
                        startActivity(int1);
                        return true;
                    case R.id.nav_checklist:
                        Intent int2 = new Intent(Spending.this, Checklist.class);
                        startActivity(int2);
                        return true;
                    case R.id.nav_planner:
                        Intent int3 = new Intent(Spending.this, Planner.class);
                        startActivity(int3);
                        return true;
                }
                return false;
            }
        });
    }

}