package my.edu.utar.ezytravelnolegacy;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOEmployee {

    private DatabaseReference databaseReference;
    public DAOEmployee(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Employee.class.getSimpleName());

    }
    public void add(String emp)
    {
        databaseReference.push().setValue(emp);
    }


}
