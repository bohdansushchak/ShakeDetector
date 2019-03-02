package bohdan.sushchak.shakedetector;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;

import rx.Observable;
import rx.android.MainThreadSubscription;

public class SensorEventObservableFactory {

    public static Observable<SensorEvent> createSensorEventObservable(@NonNull Sensor sensor, @NonNull SensorManager sensorManager) {
        return Observable.create(subscriber -> {
            MainThreadSubscription.verifyMainThread();

            SensorEventListener listener = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if(subscriber.isUnsubscribed()){
                        return;
                    }
                    subscriber.onNext(event);
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {
                }
            };
           sensorManager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_GAME);

           subscriber.add(new MainThreadSubscription() {
               @Override
               protected void onUnsubscribe() {
                   sensorManager.unregisterListener(listener);
               }
           });
        });
    }
}
