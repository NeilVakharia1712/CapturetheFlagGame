package com.example.capturetheflaggame;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.wear.widget.BoxInsetLayout;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.Requirement;
import com.estimote.mustard.rx_goodness.rx_requirements_wizard.RequirementsWizardFactory;
import com.estimote.proximity_sdk.api.EstimoteCloudCredentials;
import com.estimote.proximity_sdk.api.ProximityObserver;
import com.estimote.proximity_sdk.api.ProximityObserverBuilder;
import com.estimote.proximity_sdk.api.ProximityZone;
import com.estimote.proximity_sdk.api.ProximityZoneBuilder;
import com.estimote.proximity_sdk.api.ProximityZoneContext;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class Main2Activity extends WearableActivity {

    private TextView mTextView;
    private TextView mScore;
    private int gameScore;
    private BoxInsetLayout mLayout;
    private ProximityObserver proximityObserver;
    private FrameLayout frameLayout;
    private TextView show_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // mTextView = (TextView) findViewById(R.id.text);
        mScore = (TextView) findViewById(R.id.score);
        show_score = (TextView) findViewById(R.id.show_score);

        mLayout = (BoxInsetLayout) findViewById(R.id.layout);
        gameScore = 0;
        frameLayout = (FrameLayout) findViewById(R.id.frame);


        // Enables Always-on
        setAmbientEnabled();

        EstimoteCloudCredentials cloudCredentials =
                new EstimoteCloudCredentials("estimote-proximity-wearabl-6q5", "15b8eaaf3fd8a9a0e188b2bcf08665e8");

        this.proximityObserver = new ProximityObserverBuilder(getApplicationContext(), cloudCredentials).onError(new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {
                Log.e("app", "proximity observer error: " + throwable);
                return null;
            }
        }).withBalancedPowerMode().build();

        final ProximityZone zone = new ProximityZoneBuilder().forTag("TiiltLab1").inCustomRange(0.7)
                .onEnter(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        ++gameScore;
                        String Location = context.getAttachments().get("Location");
                        Log.d("app", "Welcome to " + Location);
                        // mTextView.setText("You are at the  "+ Location);
                        String msg = Integer.toString(gameScore);
                        mScore.setText("");
                        show_score.setText(msg);
                        frameLayout.setBackgroundColor(Color.parseColor("#FF0000"));
                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        long[] vibrationPattern = {0, 500, 50, 300};
                        //-1 - don't repeat
                        final int indexInPatternToRepeat = -1;
                        vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);
                        return null;
                    }
                }).onExit(new Function1<ProximityZoneContext, Unit>() {
                    @Override
                    public Unit invoke(ProximityZoneContext context) {
                        Log.d("app", "Bye bye, come again!");
                        // mTextView.setText("");
                        // frameLayout.setBackgroundColor(Color.parseColor("#000000"));

                        return null;

                    }
                }).onContextChange(new Function1<Set<? extends ProximityZoneContext>, Unit>() {
                    @Override
                    public Unit invoke(Set<? extends ProximityZoneContext> contexts) {
                        List<String> Locations = new ArrayList<>();
                        for (ProximityZoneContext context : contexts) {
                            Locations.add(context.getAttachments().get("Location"));
                        }
                        Log.d("app", "In range of: " + Locations );
                        // mTextView.setText("You are near the "+Locations.toString().replace("["," ").replace("]", ""));
                        frameLayout.setBackgroundColor(Color.parseColor("#000000"));
                        return null;
                    }
                })
                .build();

        RequirementsWizardFactory
                .createEstimoteRequirementsWizard()
                .fulfillRequirements(this,
                        // onRequirementsFulfilled
                        new Function0<Unit>() {
                            @Override public Unit invoke() {
                                Log.d("app", "requirements fulfilled");
                                proximityObserver.startObserving(zone);
                                return null;
                            }
                        },
                        // onRequirementsMissing
                        new Function1<List<? extends Requirement>, Unit>() {
                            @Override public Unit invoke(List<? extends Requirement> requirements) {
                                Log.e("app", "requirements missing: " + requirements);
                                return null;
                            }
                        },
                        // onError
                        new Function1<Throwable, Unit>() {
                            @Override public Unit invoke(Throwable throwable) {
                                Log.e("app", "requirements error: " + throwable);
                                return null;
                            }
                        });




    }
}
