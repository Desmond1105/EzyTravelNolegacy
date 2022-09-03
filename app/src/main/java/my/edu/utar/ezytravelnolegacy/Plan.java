package my.edu.utar.ezytravelnolegacy;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Plan {
    public Plan(String planNum, int duration) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference("Plan");

        for(int i = 0; i < duration; i++){
            String dayNum = "Day" + (i + 1);
            Day day = new Day(dayNum, planNum);
        }

    }
}
