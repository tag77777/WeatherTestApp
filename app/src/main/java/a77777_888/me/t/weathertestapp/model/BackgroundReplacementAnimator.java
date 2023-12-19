package a77777_888.me.t.weathertestapp.model;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import java.util.Objects;

@SuppressWarnings("unused")

public class BackgroundReplacementAnimator {
     private ImageView first, second;
    private @DrawableRes int lastDrawableRes = 0;
    private final ValueAnimator animator = ValueAnimator.ofFloat(0f,1f);
    private long delay = 1000L;
    private long duration = 2000L;
    private final Handler handler = new Handler(Objects.requireNonNull(Looper.myLooper()));

    public BackgroundReplacementAnimator(ImageView first, ImageView second) {
         this.first = first; this.second = second;
         setupAnimator();
     }

     public void animate(@DrawableRes int drawableRes) {
        if (drawableRes != lastDrawableRes) {
            first.setImageResource(drawableRes);
            lastDrawableRes = drawableRes;

            handler.postDelayed(animator::start, delay);
        }
     }

     private void setupAnimator() {
        first.setAlpha(0f);
        second.setAlpha(0f);
         animator
             .setDuration(duration)
             .addUpdateListener(
                 animation -> {
                     first.setAlpha((Float)animation.getAnimatedValue());
                     second.setAlpha(1f - (Float)animation.getAnimatedValue());
                 }
            );
         animator.addListener(
             new Animator.AnimatorListener() {
                 @Override
                 public void onAnimationStart(@NonNull Animator animation) {}

                 @Override
                 public void onAnimationEnd(@NonNull Animator animation) {
                    ImageView tmp = first;
                    first = second;
                    second = tmp;
                 }

                 @Override
                 public void onAnimationCancel(@NonNull Animator animation) {
                    first.setAlpha(1f); second.setAlpha(0f);
                    onAnimationEnd(animation);
                 }

                 @Override
                 public void onAnimationRepeat(@NonNull Animator animation) {}
             }
         );
     }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
