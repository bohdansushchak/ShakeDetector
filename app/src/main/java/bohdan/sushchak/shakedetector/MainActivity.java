package bohdan.sushchak.shakedetector;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.Objects;

import rx.Observable;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    private Observable<String> mMagicObservable;
    private Subscription mShakeSubscription;

    private TextView textView;
    private ConstraintLayout magicBallLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.toolbarTitle);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.screenBrightness = 0;
        getWindow().setAttributes(params);

        textView = findViewById(R.id.tvAnswer);
        magicBallLayout = findViewById(R.id.magicBallLayout);

        MagicClass magicClass = new MagicClass(this);
        mMagicObservable = magicClass.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShakeSubscription = mMagicObservable.subscribe(this::shacked);
    }

    private void shacked(String answer) {

        Animation shakeAnim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake);
        long duration = shakeAnim.getDuration();
        magicBallLayout.startAnimation(shakeAnim);

        Animation showText = AnimationUtils.loadAnimation(MainActivity.this, R.anim.show_text);
        showText.setStartOffset(duration);
        textView.startAnimation(showText);
        textView.setText(answer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShakeSubscription.unsubscribe();
    }
}
