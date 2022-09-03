package my.edu.utar.ezytravelnolegacy;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Day {
    public Day() { }

    public Day(String dayNum, String planNum) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = db.getReference("Plan").child(planNum);

        Activity activity = new Activity(0, "test", "test", "test", "1:00 AM");
        databaseReference.child(dayNum).child("Activity0").setValue(activity);
    }
}
