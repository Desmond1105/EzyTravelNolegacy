package my.edu.utar.ezytravelnolegacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TripActivity extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);

        ImageButton add = findViewById(R.id.button);
        displayTrip();

        EditText prompt = new EditText(this);
        EditText prompt2 = new EditText(this);
        prompt2.setInputType(InputType.TYPE_CLASS_NUMBER);
        TextView promptDesc = new TextView(this);
        promptDesc.setText("Name the trip");
        TextView promptDesc2 = new TextView(this);
        promptDesc2.setText("Duration of trip");
        AlertDialog.Builder builder = new AlertDialog.Builder(TripActivity.this);
        LinearLayout ll1 = new LinearLayout(this);
        ll1.setOrientation(LinearLayout.VERTICAL);
        ll1.addView(promptDesc);
        ll1.addView(prompt);
        ll1.addView(promptDesc2);
        ll1.addView(prompt2);
        builder.setTitle("Create a trip");
        builder.setView(ll1);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                insertTrip(prompt.getText().toString(), Integer.parseInt(prompt2.getText().toString()));
                Toast.makeText(TripActivity.this, "New trip added", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        ListView listTripName = findViewById(R.id.listView);
        listTripName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String text = (String) adapterView.getItemAtPosition(i);
                DatabaseReference databaseReference = db.getReference("Trip");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Trip> tripList = new ArrayList<>();
                        for(DataSnapshot ds: snapshot.getChildren()){
                            Trip trip = ds.getValue(Trip.class);
                            tripList.add(trip);
                        }

                        for(int i = 0; i < tripList.size(); i++){
                            if(tripList.get(i).getTripName() == text){
                                Intent intent = new Intent(TripActivity.this, PlanTrip.class);
                                String planNum = "plan" + tripList.get(i).getTripNum();
                                intent.putExtra("planID", planNum);
                                intent.putExtra("duration", tripList.get(i).getDuration());
                                startActivity(intent);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
        // Initialize and assign variable
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        // Set Account selected
        bottomNavigationView.setSelectedItemId(R.id.nav_planner);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_planner:
                        return true;
                    case R.id.nav_account:
                        Intent int1 = new Intent(TripActivity.this, Account.class);
                        startActivity(int1);
                        return true;
                    case R.id.nav_checklist:
                        Intent int2 = new Intent(TripActivity.this, Checklist.class);
                        startActivity(int2);
                        return true;
                    case R.id.nav_spending:
                        Intent int3 = new Intent(TripActivity.this, Spending.class);
                        startActivity(int3);
                        return true;
                }
                return false;
            }

        });
    }

    public void insertTrip(String name, int duration){
        ArrayList<Trip> tripList = new ArrayList<>();
        DatabaseReference databaseReference = db.getReference("Trip");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tripList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    Trip trip = ds.getValue(Trip.class);
                    tripList.add(trip);
                }

                int tripNum;
                if (tripList.size() == 0){
                    tripNum  = 1;
                }
                else{
                    tripNum  = tripList.get(tripList.size() - 1).getTripNum() + 1;
                }

                Trip trip = new Trip(tripNum, name, duration);
                String planNum = "plan" + Integer.toString(tripNum);
                Plan plan = new Plan(planNum, duration);
                databaseReference.child(Integer.toString(tripNum)).setValue(trip);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void displayTrip(){
        ListView listTripName = findViewById(R.id.listView);
        ArrayList<String> tripList = new ArrayList<>();
        DatabaseReference databaseReference = db.getReference("Trip");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tripList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    String tripName = ds.getValue(Trip.class).getTripName();
                    tripList.add(tripName);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                        android.R.layout.simple_list_item_1, tripList);
                listTripName.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}