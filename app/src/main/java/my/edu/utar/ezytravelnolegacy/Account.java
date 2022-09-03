package my.edu.utar.ezytravelnolegacy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Account extends AppCompatActivity {

    private Button logout;
    private String userID;
    private FirebaseUser user;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView welcomeTextView = (TextView) findViewById(R.id.welcome);
        final TextView fullNameTextView = (TextView) findViewById(R.id.fullName);
        final TextView emailTextView = (TextView) findViewById(R.id.emailAddress);
        final TextView ageTextView = (TextView) findViewById(R.id.age);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User userProfile = snapshot.getValue(User.class);

                if(userProfile!=null){
                    String fullName = userProfile.fullName;
                    String email= userProfile.email;
                    String age = userProfile.age;

                    welcomeTextView.setText("Welcome to EzyTravel, " + fullName + " !");
                    fullNameTextView.setText(fullName);
                    emailTextView.setText(email);
                    ageTextView.setText(age);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Account.this, "Something went wrong! Please try again!",
                        Toast.LENGTH_LONG).show();

            }
        });

        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Account.this,UserProfile.class));
            }
        });

// Initialize and assign variable
        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);

        // Set Account selected
        bottomNavigationView.setSelectedItemId(R.id.nav_account);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_planner:
                        Intent int1 = new Intent(Account.this, Planner.class);
                        startActivity(int1);
                        return true;
                    case R.id.nav_account:
                        return true;
                    case R.id.nav_checklist:
                        Intent int2 = new Intent(Account.this, Checklist.class);
                        startActivity(int2);
                        return true;
                    case R.id.nav_spending:
                        Intent int3 = new Intent(Account.this, Spending.class);
                        startActivity(int3);
                        return true;
                }
                return false;
            }
        });
    }
}