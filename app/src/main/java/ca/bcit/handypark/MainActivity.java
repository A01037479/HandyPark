package ca.bcit.handypark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(btnLstnr);
    }

    private View.OnClickListener btnLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EditText etDestination = findViewById(R.id.etDestination);
            String dest = etDestination.getText().toString();
            // Explicit intent
            Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
            intent.putExtra("dest", "You searched for " + dest);
            startActivity(intent);
        }
    };
}
