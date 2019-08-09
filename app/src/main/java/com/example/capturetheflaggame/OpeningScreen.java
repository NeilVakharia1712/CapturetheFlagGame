package com.example.capturetheflaggame;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OpeningScreen extends WearableActivity {

    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_screen);

        mTextView = (TextView) findViewById(R.id.text);
        mButton = (Button) findViewById(R.id.myButton);

        // Enables Always-on
        setAmbientEnabled();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OpeningScreen.this , PickATeam.class));
            }
        });
    }
}
