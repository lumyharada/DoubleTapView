package douglasspgyn.com.github.doubletapview;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * Created by Douglas on 08/08/17.
 */

class GestureListener extends GestureDetector.SimpleOnGestureListener {

    private Context context;
    private DoubleTapView doubleTapView;

    GestureListener(Context context, DoubleTapView doubleTapView) {
        this.context = context;
        this.doubleTapView = doubleTapView;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Animation pulse = AnimationUtils.loadAnimation(context, doubleTapView.getAnimatedViewAnimation());
        doubleTapView.getAnimatedView().startAnimation(pulse);

        if (doubleTapView.getDoubleTapEventListener() != null) {
            doubleTapView.getDoubleTapEventListener().onDoubleTap();
        }

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        if (doubleTapView.getDoubleTapEventListener() != null) {
            doubleTapView.getSingleTapEventListener().onSingleTap();
        }
        return true;
    }
}
