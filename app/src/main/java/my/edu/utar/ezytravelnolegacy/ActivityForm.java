package my.edu.utar.ezytravelnolegacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityForm extends AppCompatActivity {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    final Calendar myCalendar= Calendar.getInstance();
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Spinner spinner = findViewById(R.id.spinner1);
        Button dateBtn = findViewById(R.id.button1);
        Button showTime = findViewById(R.id.button2);
        TextView time = findViewById(R.id.textView4);
        TextView arrival = findViewById(R.id.textView5);
        Button arrivalTime = findViewById(R.id.button3);
        Button submit = findViewById(R.id.button4);
        Button update = findViewById(R.id.button5);
        EditText desc = findViewById(R.id.editTextTextPersonName);
        String day = getIntent().getStringExtra("day");
        String planNum = getIntent().getStringExtra("planID");
        String indicator = getIntent().getStringExtra("indicator");
        int num = getIntent().getIntExtra("actNum", 0);
        String activityNum = "Activity" + Integer.toString(num);

        String[] activities = {"Accommodation", "Transportation", "Custom"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseContext(),
                android.R.layout.simple_list_item_1, activities);
        spinner.setAdapter(adapter);

        if(indicator.equals("add")){
            submit.setVisibility(View.VISIBLE);
            update.setVisibility(View.GONE);
        }
        else{
            submit.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
        }

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH,month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ActivityForm.this, date, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        showTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker(showTime);
            }
        });

        arrivalTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker(arrivalTime);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String activityType = spinner.getSelectedItem().toString();

                if(activityType == "Accommodation"){
                    time.setText("Check-In Time");
                    arrival.setVisibility(View.GONE);
                    arrivalTime.setVisibility(View.GONE);
                }
                else if(activityType == "Transportation"){
                    time.setText("Departure Time");
                    arrival.setVisibility(View.VISIBLE);
                    arrivalTime.setVisibility(View.VISIBLE);
                }
                else{
                    time.setText("Time");
                    arrival.setVisibility(View.GONE);
                    arrivalTime.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = db.getReference("Plan").child(planNum).child(day);
                String activityType = spinner.getSelectedItem().toString();
                ArrayList<Activity> activityList = new ArrayList<>();

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        activityList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Activity activity = ds.getValue(Activity.class);
                            activityList.add(activity);
                        }

                        if(activityList.size() == 1 && activityList.get(0).getActivityNum() == 0){
                            databaseReference.child("Activity0").removeValue();
                            activityList.clear();
                        }

                        int actNum;
                        if(activityList.size() == 0){
                            actNum = 1;
                        }
                        else{
                            actNum = activityList.get(activityList.size() - 1).getActivityNum() + 1;
                        }

                        Activity activity = new Activity(actNum,
                                activityType, desc.getText().toString(), dateBtn.getText().toString(),
                                showTime.getText().toString());
                        String activityNum = "Activity" + Integer.toString(actNum);
                        databaseReference.child(activityNum).setValue(activity);

                        if(activityType == "Transportation"){
                            databaseReference.child(activityNum).child("arrivalTime").setValue(arrivalTime.getText());
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(ActivityForm.this, "Added Sucessfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReference = db.getReference("Plan").child(planNum).child(day).child(activityNum);
                String activityType = spinner.getSelectedItem().toString();
                Activity activity = new Activity(num,
                        activityType, desc.getText().toString(), dateBtn.getText().toString(),
                        showTime.getText().toString());
                databaseReference.setValue(activity);

                if(activityType == "Transportation"){
                    databaseReference.child("arrivalTime").setValue(arrivalTime.getText());
                }
                finish();
                Toast.makeText(ActivityForm.this, "Data Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void timePicker(Button btn){
        TimePickerDialog timePickerDialog = new TimePickerDialog(ActivityForm.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        hour = i;
                        minute = i1;
                        String time = hour + ":" + minute;
                        SimpleDateFormat f24Hours = new SimpleDateFormat("HH:mm");
                        try {
                            Date date = f24Hours.parse(time);
                            SimpleDateFormat f12Hours = new SimpleDateFormat("hh:mm aa");
                            btn.setText(f12Hours.format(date));
                        }
                        catch(ParseException e){
                            e.printStackTrace();
                        }
                    }
                },12,0,false);
        timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        timePickerDialog.updateTime(hour, minute);
        timePickerDialog.show();
    }

    private void updateLabel(){
        Button dateBtn = findViewById(R.id.button1);
        String myFormat= "MM/dd/yy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        dateBtn.setText(dateFormat.format(myCalendar.getTime()));
    }
}