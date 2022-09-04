package my.edu.utar.ezytravelnolegacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import android.content.Intent;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;



public class Spending extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending_table);

        Button addButton=findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Spending.this, spendingTable.class);
                startActivity(intent);
            }
        });

        ArrayList<String> list1 = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();


        Spinner spinTrip =  findViewById(R.id.getTrip);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list1);
        getTrip_spinner(list1, aa);
        aa.setDropDownViewResource(R.layout.dropdown_item);
        spinTrip.setAdapter(aa);

        Spinner spinDate =  findViewById(R.id.getDate);
        ArrayAdapter bb = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, list2);
        spinDate.setAdapter(bb);



        spinTrip.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getDate_spinner(list2, bb, spinTrip);
                bb.setDropDownViewResource(R.layout.dropdown_item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getSpendingData(spinTrip,spinDate);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        // Set Account selected
        bottomNavigationView.setSelectedItemId(R.id.nav_spending);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

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
                        Intent int3 = new Intent(Spending.this, TripActivity.class);
                        startActivity(int3);
                        return true;
                }
                return false;
            }

        });
    }

    private void getTrip_spinner(ArrayList<String> list1, ArrayAdapter aa) {
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Spending");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String result = dataSnapshot.getKey().toString();

                    list1.add(result);

                }
                aa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Spending.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDate_spinner(ArrayList<String> list2, ArrayAdapter bb, Spinner spinTrip) {

        String selected_spinner = spinTrip.getSelectedItem().toString();


        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Spending").child(selected_spinner);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    String result = dataSnapshot.getKey().toString();

                    list2.add(result);

                }
                bb.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Spending.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void getSpendingData(Spinner spinTrip, Spinner spinDate) {

        String selected_spinner = spinTrip.getSelectedItem().toString();
        String selected_spinner2 = spinDate.getSelectedItem().toString();


        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Spending").child(selected_spinner).child(selected_spinner2);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String result1 = "";
                String result2 ="";
                Double result3 = 0.0;
                for (DataSnapshot data : snapshot.getChildren()) {

                    result1 += data.getKey().toString() + "\n";
                    result2 += data.getValue().toString() + "\n";
                    result3 += data.getValue(Double.class);

                    TextView showSpending = findViewById(R.id.showSpending);
                    showSpending.setText(result1);
                    TextView showPrice = findViewById(R.id.showPrice);
                    showPrice.setText(result2);
                    TextView showTotal = findViewById(R.id.spendTotal);
                    showTotal.setText(result3.toString());

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Spending.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}