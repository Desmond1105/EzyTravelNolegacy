package my.edu.utar.ezytravelnolegacy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import my.edu.utar.ezytravelnolegacy.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new PlannerFragment());

        binding.btmNgvBar.setOnItemSelectedListener(item ->{

            switch(item.getItemId()){
                case R.id.nav_account:
                    replaceFragment((new AccountFragment()));
                    break;
                case R.id.nav_spending:
                    replaceFragment(new SpendingFragment());
                    break;
                case R.id.nav_checklist:
                    replaceFragment(new ChecklistFragment());
                    break;
                case R.id.nav_planner:
                    replaceFragment(new PlannerFragment());
                    break;
            }

            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}