package bohdan.sushchak.shakedetector;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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
        getSupportActionBar().setTitle(R.string.toolbarTitle);

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
        textView.setText(answer);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShakeSubscription.unsubscribe();
    }
}
