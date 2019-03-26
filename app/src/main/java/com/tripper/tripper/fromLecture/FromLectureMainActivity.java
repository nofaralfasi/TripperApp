package com.tripper.tripper.fromLecture;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tripper.tripper.R;

import java.util.HashMap;
import java.util.Map;

// I added 26.3

public class FromLectureMainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_fcm);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button subscribeButton = findViewById(R.id.addUser);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> childUpdates = new HashMap<>();
                User user = new User(1236,"Sami",89);
                childUpdates.put("/students/"+ user.ID+"/", user.toJson());
                mDatabase.updateChildren(childUpdates);
            }
        });

        Button logTokenButton = findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

}