package com.maliros.giftcard.activities;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;

import com.maliros.giftcard.R;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class WindowTransitionsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_window_transitions);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.text_fade_fast)
    public void startFadeFastTransition() {
        Intent intent = new Intent(WindowTransitionsActivity.this, TransitionInActivity.class);
        intent.putExtra(
                TransitionInActivity.EXTRA_TRANSITION, TransitionInActivity.TRANSITION_FADE_FAST);
        startActivityWithOptions(intent);
    }



    private void startActivityWithOptions(Intent intent) {
        ActivityOptions transitionActivity =
                ActivityOptions.makeSceneTransitionAnimation(WindowTransitionsActivity.this);
        startActivity(intent, transitionActivity.toBundle());
    }

}
