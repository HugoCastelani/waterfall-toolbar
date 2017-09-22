package com.hugocastelani.waterfalltoolbar.main;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.hugocastelani.waterfalltoolbar.R;
import com.hugocastelani.waterfalltoolbar.library.WaterfallToolbar;
import com.hugocastelani.waterfalltoolbar.sample.SampleActivity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private DiscreteSeekBar mInitialElevationSeekBar;
    private DiscreteSeekBar mFinalElevationSeekBar;
    private DiscreteSeekBar mScrollFinalPositionSeekBar;

    private Button mDoneButton;
    private Button mRestoreDefaultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        prepareViews();
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mInitialElevationSeekBar = (DiscreteSeekBar) findViewById(R.id.initial_elevation_seek_bar);
        mFinalElevationSeekBar = (DiscreteSeekBar) findViewById(R.id.final_elevation_seek_bar);
        mScrollFinalPositionSeekBar = (DiscreteSeekBar) findViewById(R.id.scroll_final_position_seek_bar);
        mDoneButton = (Button) findViewById(R.id.done_button);
        mRestoreDefaultButton = (Button) findViewById(R.id.restore_default_button);
    }

    private void prepareViews() {
        // prepare toolbar
        setSupportActionBar(mToolbar);

        // prepare initial elevation seek bar
        mInitialElevationSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {}
            @Override public void onStartTrackingTouch(DiscreteSeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                final int newValue = mInitialElevationSeekBar.getProgress();
                mFinalElevationSeekBar.setMin(newValue);
                mFinalElevationSeekBar.setMax(newValue + 10);
                mFinalElevationSeekBar.invalidate();
            }
        });

        // prepare restore default button
        mRestoreDefaultButton.setOnClickListener(view -> {
            mFinalElevationSeekBar.setMin(0);
            mFinalElevationSeekBar.setMax(10);
            mInitialElevationSeekBar.setProgress(WaterfallToolbar.DEFAULT_INITIAL_ELEVATION);

            mFinalElevationSeekBar.setMin(WaterfallToolbar.DEFAULT_INITIAL_ELEVATION);
            mFinalElevationSeekBar.setMax(WaterfallToolbar.DEFAULT_INITIAL_ELEVATION + 10);
            mFinalElevationSeekBar.setProgress(WaterfallToolbar.DEFAULT_FINAL_ELEVATION);

            mScrollFinalPositionSeekBar.setProgress(WaterfallToolbar.DEFAULT_SCROLL_FINAL_ELEVATION);
        });

        // prepare done button
        mDoneButton.setOnClickListener(view -> {
            final Bundle bundle = new Bundle();
            bundle.putInt("initial_elevation", mInitialElevationSeekBar.getProgress());
            bundle.putInt("final_elevation", mFinalElevationSeekBar.getProgress());
            bundle.putInt("scroll_final_position", mScrollFinalPositionSeekBar.getProgress());

            final Intent intent = new Intent(this, SampleActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
        });
    }
}
