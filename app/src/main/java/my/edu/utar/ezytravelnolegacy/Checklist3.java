package my.edu.utar.ezytravelnolegacy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Checklist3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist3);
        ArrayList<String> list = new ArrayList<>();

        Spinner sp = (Spinner) findViewById(R.id.spinner2);
        ArrayAdapter aa1 = new ArrayAdapter(this,R.layout.selected_item, list);
        retrieve_Spinner(list, aa1);
        aa1.setDropDownViewResource(R.layout.dropdown_item);
        sp.setAdapter(aa1);



        String[] cat = { "Beauty", "Clothes", "Glasses & EyeWear", "Medication", "Tech & Electronics" +
                "Toiletries", "Travel Document"};
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter aa = new ArrayAdapter(this,R.layout.selected_item, cat);
        aa.setDropDownViewResource(R.layout.dropdown_item);
        spinner.setAdapter(aa);

        Intent intent = getIntent();
        String intent_value = intent.getExtras().getString("value");
        TextView title_tv = (TextView) findViewById(R.id.title_tv);

        EditText item = (EditText) findViewById(R.id.item_et);
        boolean temp = false;




        if(intent_value.equals("1")){
            title_tv.setText("Add Check List Item");

            Button submit_btn = (Button) findViewById(R.id.submit_btn);
            submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    mDatabase.child("CheckList").child(sp.getSelectedItem().toString())
                            .child(spinner.getSelectedItem().toString()).child(item.getText().toString())
                            .child("check").setValue(temp);

                    Toast.makeText(Checklist3.this, "Added", Toast.LENGTH_SHORT).show();

                    Intent goBack = new Intent(Checklist3.this, Checklist.class);
                    startActivity(goBack);
                }
            });
        }
        else if(intent_value.equals("2")){
            title_tv.setText("Delete Check List Item");

            Button submit_btn = (Button) findViewById(R.id.submit_btn);
            submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DatabaseReference mDatabase;
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    mDatabase.child("CheckList").child(sp.getSelectedItem().toString())
                            .child(spinner.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot data : snapshot.getChildren()){

                                String cmp = data.getKey();

                                if(item.getText().toString().equals(cmp)){
                                    mDatabase.child("CheckList").child(sp.getSelectedItem().toString())
                                            .child(spinner.getSelectedItem().toString()).child(item.getText().toString())
                                            .removeValue();
                                    Toast.makeText(Checklist3.this, "Deleted", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                                else{
                                    Toast.makeText(Checklist3.this, "Item not Found, Delete Unsuccessfully", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(Checklist3.this, "Fail to get data", Toast.LENGTH_SHORT).show();

                        }
                    });



                    Intent goBack = new Intent(Checklist3.this, Checklist.class);
                    startActivity(goBack);

                }
            });
        }
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
                Toast.makeText(Checklist3.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}