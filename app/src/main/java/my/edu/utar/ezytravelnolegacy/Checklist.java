package my.edu.utar.ezytravelnolegacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Checklist extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        ImageButton btn = (ImageButton) findViewById(R.id.add_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toAdd = new Intent(getApplicationContext(),Checklist2.class);
                startActivity(toAdd);

            }
        });

        ArrayList<String> list = new ArrayList<>();

        Spinner sp = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.selected_item, list);
        retrieve_Spinner(list, aa);
        aa.setDropDownViewResource(R.layout.dropdown_item);
        sp.setAdapter(aa);

        Button check_button = (Button) findViewById(R.id.check_btn);
        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retrieve_checklist(sp);
            }
        });

        Button add_checklist = (Button) findViewById(R.id.add_checklist);
        add_checklist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent add = new Intent(Checklist.this, Checklist3.class);
                add.putExtra("value", "1");
                startActivity(add);
            }
        });

        Button dlt_button = (Button) findViewById(R.id.delete_btn);
        dlt_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dlt = new Intent(Checklist.this, Checklist3.class);
                dlt.putExtra("value", "2");
                startActivity(dlt);
            }
        });

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
                        Intent int2 = new Intent(Checklist.this, TripActivity.class);
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


    private void retrieve_Spinner(ArrayList<String> list, ArrayAdapter aa){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("CheckList");

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    String result = dataSnapshot.getKey().toString();
                    list.add(result);
                }
                aa.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Checklist.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void retrieve_checklist(Spinner sp){
        String selected_spinner = sp.getSelectedItem().toString();
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference().child("CheckList").child(selected_spinner);


        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot1) {
                for(DataSnapshot dataSnapshot : snapshot1.getChildren()){
                    String result = dataSnapshot.getKey().toString();

                    if(result.equals("Beauty")){
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("CheckList").child(selected_spinner).child("Beauty");
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String result1 = "";

                                for(DataSnapshot data : snapshot.getChildren()){

                                    result1 += data.getKey().toString() + "\n";
                                    TextView beauty_tv = (findViewById(R.id.beauty_tv2));
                                    beauty_tv.setText(result1);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Checklist.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if(result.equals("Clothes")){
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("CheckList").child(selected_spinner).child("Clothes");
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String result = "";
                                for(DataSnapshot data : snapshot.getChildren()){
                                    result += data.getKey().toString() + "\n";
                                    TextView clothes_tv = (findViewById(R.id.clothes_tv2));
                                    clothes_tv.setText(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Checklist.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else if(result.equals("Glasses & Eyewear")){
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("CheckList").child(selected_spinner).child("Glasses & Eyewear");
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String result = "";
                                for(DataSnapshot data : snapshot.getChildren()){
                                    result += data.getKey().toString() + "\n";
                                    TextView eye_tv = (findViewById(R.id.eyewear_tv2));
                                    eye_tv.setText(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Checklist.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else if(result.equals("Medication")){
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("CheckList").child(selected_spinner).child("Medication");
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String result = "";
                                for(DataSnapshot data : snapshot.getChildren()){
                                    result += data.getKey().toString() + "\n";
                                    TextView medi_tv = (findViewById(R.id.medi_tv2));
                                    medi_tv.setText(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Checklist.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                    else if(result.equals("Tech & Electronics")){
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("CheckList").child(selected_spinner).child("Tech & Electronics");
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String result = "";
                                for(DataSnapshot data : snapshot.getChildren()){
                                    result += data.getKey().toString() + "\n";
                                    TextView tech_tv = (findViewById(R.id.tech_tv2));
                                    tech_tv.setText(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Checklist.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else if(result.equals("Toiletries")){
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("CheckList").child(selected_spinner).child("Toiletries");
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String result = "";
                                for(DataSnapshot data : snapshot.getChildren()){
                                    result += data.getKey().toString() + "\n";
                                    TextView toi_tv = (findViewById(R.id.toi_tv2));
                                    toi_tv.setText(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Checklist.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                    else if(result.equals("Travel Document")){
                        DatabaseReference mDatabase;
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("CheckList").child(selected_spinner).child("Travel Document");
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String result = "";
                                for(DataSnapshot data : snapshot.getChildren()){
                                    result += data.getKey().toString() + "\n";
                                    TextView doc_tv = (findViewById(R.id.doc_tv2));
                                    doc_tv.setText(result);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(Checklist.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Checklist.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });



    }
}