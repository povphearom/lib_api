package knd.com.lib_api.touch;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

public class ScalableGestureDetector extends BaseGestureDetector {

    private ScaleGestureDetector mScaleGestureDetector;
    private final ScaleGestureDetector.OnScaleGestureListener mListener;

    @Override
    protected void handleStartProgressEvent(int actionCode, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
    }

    @Override
    protected void handleInProgressEvent(int actionCode, MotionEvent event) {
        mScaleGestureDetector.onTouchEvent(event);
    }

    public ScalableGestureDetector(Context context, ScaleGestureDetector.OnScaleGestureListener listener) {
        super(context);
        mListener = listener;
        mScaleGestureDetector = new ScaleGestureDetector(context, listener);
    }

    public static class SimpleOnScaleGestureListener implements ScaleGestureDetector.OnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            return false;
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

        }
    }
}