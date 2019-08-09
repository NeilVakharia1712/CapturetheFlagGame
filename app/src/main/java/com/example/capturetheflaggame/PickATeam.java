package com.example.capturetheflaggame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PickATeam extends WearableActivity {

    private TextView mTextView;
    private Button mBtn1;
    private Button mBtn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_ateam);

        mTextView = (TextView) findViewById(R.id.text);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn2 = (Button) findViewById(R.id.btn2);

        mBtn1.setBackgroundColor(Color.parseColor("#FFC0CB"));
        mBtn2.setBackgroundColor(Color.parseColor("#ffffff"));

        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickATeam.this , MainActivity.class));
            }
        });

        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PickATeam.this , Main2Activity.class));
            }
        });


        // Enables Always-on
        setAmbientEnabled();
    }
}
