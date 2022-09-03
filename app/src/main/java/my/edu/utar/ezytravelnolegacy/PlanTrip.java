package my.edu.utar.ezytravelnolegacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class PlanTrip extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    RecycleViewAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_trip);


        int duration = getIntent().getIntExtra("duration", 0);
        String planNum = getIntent().getStringExtra("planID");
        Intent intent = new Intent(PlanTrip.this, ActivityForm.class);
        Spinner listDay = findViewById(R.id.spinner);
        ImageButton addActivity = findViewById(R.id.button);
        RecyclerView recyclerView = findViewById(R.id.recycleViewShowActivity);

        addActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("planID", planNum);
                intent.putExtra("indicator", "add");
                startActivity(intent);
            }
        });

        listDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String day = listDay.getSelectedItem().toString();
                intent.putExtra("day", day);

                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(PlanTrip.this));
                ArrayList<Activity> activityList = new ArrayList<>();
                myAdapter = new RecycleViewAdapter(PlanTrip.this, activityList);
                recyclerView.setAdapter(myAdapter);
                DatabaseReference databaseReference = db.getReference("Plan").child(planNum).child(day);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        activityList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            Activity activity = ds.getValue(Activity.class);
                            activityList.add(activity);
                        }

                        rearrange(activityList);

                        myAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        setSpinner(duration);
    }

    public void setSpinner(int duration){
        Spinner listDay = findViewById(R.id.spinner);
        String[] days = new String[duration];

        for(int i = 1; i <= duration; i++){
            days[i - 1] = "Day" + i;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1, days);
        listDay.setAdapter(adapter);
    }

    public void rearrange(ArrayList<Activity> activityList){
        ArrayList<Activity> am = new ArrayList<>();
        ArrayList<Activity> pm = new ArrayList<>();
        for(int i = 0; i < activityList.size(); i++){
            String[] time = activityList.get(i).getTime().split(" ");
            if(time[1] == "AM"){
                am.add(activityList.get(i));
            }
            else{
                pm.add(activityList.get(i));
            }
        }
        activityList.clear();

        while(am.size() != 0) {
            int smallest = 0;
            for (int i = 0; i < am.size(); i++) {
                String[] time = am.get(i).getTime().split(" ");
                String[] small = am.get(smallest).getTime().split(" ");
                int compare = time[0].compareTo(small[0]);
                if (time[0].compareTo("12:00") < 0 && compare < 0) {
                    smallest = i;
                } else if (time[0].compareTo("12:00") >= 0) {
                    if (small[0].compareTo("12:00") < 0) {
                        smallest = i;
                    } else if (compare < 0) {
                        smallest = i;
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            activityList.add(am.get(smallest));
            am.remove(smallest);
        }

        while(pm.size() != 0) {
            int smallest = 0;
            for (int i = 0; i < pm.size(); i++) {
                String[] time = pm.get(i).getTime().split(" ");
                String[] small = pm.get(smallest).getTime().split(" ");
                int compare = time[0].compareTo(small[0]);
                if (time[0].compareTo("12:00") < 0 && compare < 0) {
                    smallest = i;
                } else if (time[0].compareTo("12:00") >= 0) {
                    if (small[0].compareTo("12:00") < 0) {
                        smallest = i;
                    } else if (compare < 0) {
                        smallest = i;
                    } else {
                        continue;
                    }
                } else {
                    continue;
                }
            }
            activityList.add(pm.get(smallest));
            pm.remove(smallest);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(PlanTrip.this, ActivityForm.class);
        Spinner listDay = findViewById(R.id.spinner);
        String planNum = getIntent().getStringExtra("planID");
        String day = listDay.getSelectedItem().toString();
        intent.putExtra("indicator", "update");
        intent.putExtra("planID", planNum);
        intent.putExtra("day", day);
        DatabaseReference databaseReference = db.getReference("Plan").child(planNum).child(day);

        ArrayList<Activity> activityList = new ArrayList<>();

        switch(item.getItemId()){
            case 121:
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        activityList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            Activity activity = ds.getValue(Activity.class);
                            activityList.add(activity);
                        }
                        rearrange(activityList);
                        int num = activityList.get(item.getGroupId()).getActivityNum();
                        intent.putExtra("actNum", num);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
                return true;
            case 122:
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        activityList.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            Activity activity = ds.getValue(Activity.class);
                            activityList.add(activity);
                        }
                        rearrange(activityList);
                        int num = activityList.get(item.getGroupId()).getActivityNum();
                        intent.putExtra("actNum", num);
                        String activityNum = "Activity" + Integer.toString(num);
                        databaseReference.child(activityNum).removeValue();
                        Toast.makeText(PlanTrip.this, "Activity Deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
}