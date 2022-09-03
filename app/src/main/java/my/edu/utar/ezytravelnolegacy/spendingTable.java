package my.edu.utar.ezytravelnolegacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class spendingTable extends AppCompatActivity {

    private static final String TAG ="Spending";
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spending);

        Button addButton = findViewById(R.id.button3);
        EditText spendList = findViewById(R.id.listName);
        EditText spendAmount = findViewById(R.id.spendAmount);
        EditText spendDes = findViewById(R.id.spendDescription);
        TextView spendDate = findViewById(R.id.selectDate);
        Button checkSpending = findViewById(R.id.button);
        Button clearText = findViewById(R.id.clearButton);
        Button deleteButton = findViewById(R.id.button4);



        clearText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spendList.setText("");
                spendAmount.setText("");
                spendDes.setText("");
                spendDate.setText("Select Date");
            }
        });
        spendDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        spendingTable.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month +=1;
                Log.d(TAG,"onDateSet: dd/mm/yyyy: "+ day +"" + month +"" + year);
                String date = day + "/"+ month +"/" +year;

                spendDate.setText(date);

            }
        };



        addButton.setOnClickListener(new View.OnClickListener() { // add new card
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();

                String sName = spendList.getText().toString();
                String sDescription = spendDes.getText().toString();
                double sAmount = Double.parseDouble(spendAmount.getText().toString());

                String date2 = "";
                String[] split_str = spendDate.getText().toString().split("/");
                for(int i =0;i<split_str.length;i++){
                    date2 += split_str[i];
                }
                String sDate = date2;

                spendingHelper helperClass= new spendingHelper(sName,sDescription,sAmount, sDate);


                mDatabase.child("Spending").child(helperClass.getTripName()).child(sDate).
                        child(helperClass.getSpendDescription()).setValue(helperClass.getSpendAmount());
                Toast.makeText(spendingTable.this, "Added New Spending",Toast.LENGTH_LONG).show();
            }
        });

        checkSpending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent checkSpend = new Intent(spendingTable.this, Spending.class);
                startActivity(checkSpend);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase;
                mDatabase = FirebaseDatabase.getInstance().getReference();

                String sName = spendList.getText().toString();
                String sDescription = spendDes.getText().toString();
                double sAmount = Double.parseDouble(spendAmount.getText().toString());

                String date2 = "";
                String[] split_str = spendDate.getText().toString().split("/");
                for(int i =0;i<split_str.length;i++){
                    date2 += split_str[i];
                }
                String sDate = date2;

                spendingHelper helperClass= new spendingHelper(sName,sDescription,sAmount, sDate);


                mDatabase.child("Spending").child(helperClass.getTripName()).child(sDate)
                        .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data : snapshot.getChildren()){
                            String cmp = data.getKey();

                            if(sDescription.equals(cmp)){
                                mDatabase.child("Spending").child(helperClass.getTripName()).child(sDate)
                                        .child(helperClass.getSpendDescription()).removeValue();

                                Toast.makeText(spendingTable.this, "Deleted", Toast.LENGTH_SHORT).show();
                                break;
                            }
                            else{
                                Toast.makeText(spendingTable.this, "Item not Found, Delete Unsuccessfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                mDatabase.child("CheckList").child(sp.getSelectedItem().toString()).child(spinner.getSelectedItem().toString())
//                String cmp = data.getKey();

//                if(item.getText().toString().equals(cmp)){
//                    mDatabase.child("CheckList").child(sp.getSelectedItem().toString())
//                            .child(spinner.getSelectedItem().toString()).child(item.getText().toString())
//                            .removeValue();
//                    Toast.makeText(Checklist3.this, "Deleted", Toast.LENGTH_SHORT).show();
//                    break;
//                }
//                else{
//                    Toast.makeText(Checklist3.this, "Item not Found, Delete Unsuccessfully", Toast.LENGTH_SHORT).show();
//                }
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
                        Intent int1 = new Intent(spendingTable.this, Account.class);
                        startActivity(int1);
                        return true;
                    case R.id.nav_checklist:
                        Intent int2 = new Intent(spendingTable.this, Checklist.class);
                        startActivity(int2);
                        return true;
                    case R.id.nav_planner:
                        Intent int3 = new Intent(spendingTable.this, Planner.class);
                        startActivity(int3);
                        return true;
                }
                return false;
            }

        });

    }
}
