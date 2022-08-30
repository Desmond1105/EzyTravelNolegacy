package my.edu.utar.ezytravelnolegacy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        DAOEmployee dao = new DAOEmployee();
        EditText et = new EditText(this);
        et = findViewById(R.id.testing_et);

        Button btn = (Button) findViewById(R.id.btn_1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = new EditText(MainActivity.this);
                et = findViewById(R.id.testing_et);

                dao.add(et.getText().toString());
            }
        });


    }
}