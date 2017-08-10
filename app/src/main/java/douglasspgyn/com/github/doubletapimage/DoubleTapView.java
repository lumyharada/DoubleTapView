package douglasspgyn.com.github.doubletapimage;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Douglas on 08/08/17.
 */

public class DoubleTapView extends RelativeLayout {

    private Context context;
    private onDoubleTapEventListener doubleTapEventListener;
    private boolean doubleTapEnable = true;

    private View rootView;
    private ImageView animatedView;

    private String animatedViewBackgroundColor;
    private Drawable animatedViewDrawable;
    private int animatedViewMeasure;

    public DoubleTapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DoubleTapView);
        init(context, typedArray);
    }

    /**
     * Start and set the view including the xml style
     *
     * @param context    view context
     * @param typedArray xml style
     */
    private void init(Context context, TypedArray typedArray) {
        this.context = context;
        getTypedArray(typedArray);

        rootView = inflate(context, R.layout.double_view, this);
        animatedView = (ImageView) rootView.findViewById(R.id.animated_view);

        setAnimatedView(context);
        enableDoubleTap();
    }

    /**
     * Get the xml style to set the view
     *
     * @param typedArray xml style
     */
    private void getTypedArray(TypedArray typedArray) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56, getResources().getDisplayMetrics());
        animatedViewBackgroundColor = typedArray.getString(R.styleable.DoubleTapView_animatedViewBackgroundColor) != null ? typedArray.getString(R.styleable.DoubleTapView_animatedViewBackgroundColor) : "#" + Integer.toHexString(ContextCompat.getColor(getContext(), R.color.colorAccent));
        animatedViewDrawable = typedArray.getDrawable(R.styleable.DoubleTapView_animatedViewDrawable) != null ? typedArray.getDrawable(R.styleable.DoubleTapView_animatedViewDrawable) : ContextCompat.getDrawable(context, R.drawable.transparent_view);
        animatedViewMeasure = (int) typedArray.getDimension(R.styleable.DoubleTapView_animatedViewMeasure, px);

        typedArray.recycle();
    }

    /**
     * Set the animated view (background color, drawable and measure)
     *
     * @param context view context
     */
    private void setAnimatedView(Context context) {
        LayerDrawable layerDrawable = (LayerDrawable) ContextCompat.getDrawable(context, R.drawable.animated_view);

        GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable.findDrawableByLayerId(R.id.animated_background);
        gradientDrawable.setColor(Color.parseColor(animatedViewBackgroundColor));

        layerDrawable.setDrawableByLayerId(R.id.animated_drawable, animatedViewDrawable);
        animatedView.setImageDrawable(layerDrawable);

        animatedView.getLayoutParams().height = animatedView.getLayoutParams().width = animatedViewMeasure;
    }

    /**
     * Create the double tap listener
     *
     * @param eventListener double tap listener to callback
     */
    public void setOnDoubleTapEventListener(onDoubleTapEventListener eventListener) {
        doubleTapEventListener = eventListener;
    }

    /**
     * Remove the existent double tap listener
     */
    public void removeOnDoubleTapEventListener() {
        doubleTapEventListener = null;
    }

    /**
     * Get the double tap listener, if exist
     *
     * @return double tap event listener
     */
    public onDoubleTapEventListener getDoubleTapEventListener() {
        return doubleTapEventListener;
    }

    /**
     * Enable the double tap
     */
    public void enableDoubleTap() {
        setDoubleTap(context);
        doubleTapEnable = true;
    }

    /**
     * Disable the double tap
     */
    public void disableDoubleTap() {
        setOnTouchListener(null);
        doubleTapEnable = false;
    }

    /**
     * Check if the double tap is enable
     *
     * @return if double tap is enable
     */
    private boolean isDoubleTapEnable() {
        return doubleTapEnable;
    }

    /**
     * Set the touch listener to enable the double tap animation and callback
     *
     * @param context view context
     */
    private void setDoubleTap(Context context) {
        final GestureDetector gestureDetector = new GestureDetector(context, new GestureListener(context, this));
        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return gestureDetector.onTouchEvent(motionEvent);
            }
        });
    }

    /**
     * Getter used on GestureListener to animate
     *
     * @return the imageview that will be animated
     */
    protected ImageView getAnimatedView() {
        return animatedView;
    }

    /**
     * Set the animated view background color
     *
     * @param color animated view background color
     * @return the instance of the view
     */
    public DoubleTapView setAnimatedBackgroundColor(int color) {
        animatedViewBackgroundColor = "#" + Integer.toHexString(ContextCompat.getColor(context, color));
        setAnimatedView(context);
        return this;
    }

    /**
     * Set the animated view drawable
     *
     * @param drawable animated view drawable
     * @return the instance of the view
     */
    public DoubleTapView setAnimatedDrawable(int drawable) {
        setAnimatedDrawable(ContextCompat.getDrawable(context, drawable));
        return this;
    }

    /**
     * Set the animated view drawable
     *
     * @param drawable animated view drawable
     * @return the instance of the view
     */
    public DoubleTapView setAnimatedDrawable(Drawable drawable) {
        animatedViewDrawable = drawable != null ? drawable : ContextCompat.getDrawable(context, R.drawable.transparent_view);
        setAnimatedView(context);
        return this;
    }


    /**
     * Set the animated view size (height and width)
     *
     * @param px measure used to define the height and width
     * @return the instance of the view
     */
    public DoubleTapView setAnimatedMeasure(int px) {
        animatedViewMeasure = px;
        setAnimatedView(context);
        return this;
    }

    /**
     * Callback of double tap event
     */
    public interface onDoubleTapEventListener {
        void onDoubleTap();
    }
}