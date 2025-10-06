package com.example.database;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText e1, e2;
    Button b1;
    SQLiteDatabase db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        b1 = findViewById(R.id.b1);

        db = openOrCreateDatabase("SAMPLEDB", MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS STUDENT(NAME VARCHAR, ROLLNO VARCHAR)");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("INSERT INTO STUDENT VALUES('" + e1.getText().toString() + "', '" + e2.getText().toString() + "');");

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Success");
                builder.setMessage("Submitted the data");
                builder.show();
            }
        });
    }
}
