package my.edu.utar.ezytravelnolegacy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Checklist2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist2);

        Button add_button = (Button) findViewById(R.id.add_button2);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText cl_ed = (EditText) findViewById(R.id.checklist_ET);
                writeNewPost(cl_ed.getText().toString());
            }
        });
    }

    private void writeNewPost(String cl_name){
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        String [] beauty = {"makeup", "makeup remover", "hair dryer"};
        String [] clothes = {"tops", "shorts", "pajamas"};
        String [] eyewear = {"sunglasses", "contact lens", "solution"};
        String [] medication = {"first-aid kit", "panadol" ,"allergy medication"};
        String [] tech = {"phone charge", "headphone", "travel adapter"};
        String [] toiletries = {"toohpaste", "toothbrush", "shmapoo"};
        String [] document = {"passport", "money & card", "booking confirmation"};
        Boolean temp = false;


        for(int x = 0; x < 3; x++) {

            mDatabase.child("CheckList").child(cl_name).child("Beauty").child(beauty[x]).child("check").setValue(temp);
            mDatabase.child("CheckList").child(cl_name).child("Clothes").child(clothes[x]).child("check").setValue(temp);
            mDatabase.child("CheckList").child(cl_name).child("Glasses & Eyewear").child(eyewear[x]).child("check").setValue(temp);
            mDatabase.child("CheckList").child(cl_name).child("Medication").child(medication[x]).child("check").setValue(temp);
            mDatabase.child("CheckList").child(cl_name).child("Tech & Electronics").child(tech[x]).child("check").setValue(temp);
            mDatabase.child("CheckList").child(cl_name).child("Toiletries").child(toiletries[x]).child("check").setValue(temp);
            mDatabase.child("CheckList").child(cl_name).child("Travel Document").child(document[x]).child("check").setValue(temp);

        }

        mDatabase.child("CheckList").child(cl_name).child("cl_name");


        Toast.makeText(Checklist2.this, "Added", Toast.LENGTH_SHORT).show();
        Intent goBack = new Intent(getApplicationContext(), Checklist.class);
        startActivity(goBack);

    }
}
