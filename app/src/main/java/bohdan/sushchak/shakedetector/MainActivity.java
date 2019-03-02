package bohdan.sushchak.shakedetector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import rx.Observable;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    private Observable<?> mShakeObservable;
    private Subscription mShakeSubscription;

    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textview);
        
        mShakeObservable = ShakeDetector.create(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mShakeSubscription = mShakeObservable.subscribe((object) -> beep());
    }

    private void beep() {
        textView.setText("shaked!");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mShakeSubscription.unsubscribe();
    }
}
