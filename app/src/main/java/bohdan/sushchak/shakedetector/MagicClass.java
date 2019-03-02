package bohdan.sushchak.shakedetector;

import android.content.Context;
import android.support.annotation.NonNull;


import java.util.concurrent.ThreadLocalRandom;

import rx.Observable;

public class MagicClass {

    private Context context;
    private String[] answers;

    MagicClass(@NonNull Context context){
         this.context = context;
         answers = context.getResources().getStringArray(R.array.answers);
    }

    public Observable<String> create(){
        return ShakeDetector.create(context)
                .map(buf -> getAnswer());
    }

    private String getAnswer() {
        answers = context.getResources().getStringArray(R.array.answers);
        int randomNum = ThreadLocalRandom.current().nextInt(0, answers.length + 1);
        return answers[randomNum];
    }
}
