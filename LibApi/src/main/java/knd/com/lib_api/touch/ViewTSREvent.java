package knd.com.lib_api.touch;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by phearom on 7/19/17.
 */

public class ViewTSREvent {
    private ScaleGestureDetector mScaleDetector;
    private RotateGestureDetector mRotateGestureDetector;
    private GestureDetector mGestureDetector;
    private float mScaleFactor = 1.f;
    private float mRotate;
    private View mView;
    private Context mContext;
    private Callback mCallback;

    private InputMethodManager mInputMethodManager;

    private MotionEvent mMotionEvent;

    private float sX, sY;
    private float dX, dY;

    public ViewTSREvent(Context context, View v, Callback callback) {
        this.mContext = context;
        this.mView = v;
        this.mCallback = callback;

        mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        mRotateGestureDetector = new RotateGestureDetector(context, new RotateListener());
        mGestureDetector = new GestureDetector(context, new MyGestureListener());
    }

    /*Get*/

    public float getRotate() {
        return mRotate;
    }

    public float getScaleFactor() {
        return mScaleFactor;
    }

    public void setScaleDetector(float scaleFactor) {
        mScaleFactor = scaleFactor;
    }

    public void setRotate(float rotate) {
        mRotate = rotate;
    }

    /*End*/

    public boolean onTouchEvent(MotionEvent event) {
        if (mView == null) return true;
        mInputMethodManager.hideSoftInputFromInputMethod(mView.getWindowToken(), 0);
        mMotionEvent = event;
        mView.bringToFront();

        mGestureDetector.onTouchEvent(event);
//        if (mGestureDetector.isLongpressEnabled())
//            return true;

        if (mCallback.canHandleScale(mView, event))
            mScaleDetector.onTouchEvent(event);
        if (mCallback.canHandleRotate(mView, event))
            mRotateGestureDetector.onTouchEvent(event);

        if (mScaleDetector.isInProgress() || mRotateGestureDetector.isInProgress())
            return true;

        if (event.getPointerCount() > 1) return true;

        if (!mCallback.canHandleMove(mView, event))
            return true;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                sX = event.getRawX();
                sY = event.getRawY();
                mCallback.onViewStart(mView, event, ViewState.MOVE);
                break;
            case MotionEvent.ACTION_MOVE:
                float offsetX = event.getRawX() - sX;
                float offsetY = event.getRawY() - sY;

                dX = mCallback.claimViewPositionX(mView, mView.getX() + offsetX);
                dY = mCallback.claimViewPositionY(mView, mView.getY() + offsetY);
                mView.setX(dX);
                mView.setY(dY);
                sX = event.getRawX();
                sY = event.getRawY();
                mCallback.onViewChange(mView, event, ViewState.MOVE);
                break;
            case MotionEvent.ACTION_UP:
                mCallback.onViewReleased(mView, event, ViewState.MOVE);
                break;
        }
        ViewCompat.postInvalidateOnAnimation(mView);
        return true;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            mScaleFactor *= detector.getScaleFactor();
            mScaleFactor = Math.max(0.5f, Math.min(mScaleFactor, 3f));
            updateView();
            mCallback.onViewChange(mView, mMotionEvent, ViewState.SCALE);
            return false;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            super.onScaleEnd(detector);
            mCallback.onViewReleased(mView, mMotionEvent, ViewState.SCALE);
        }

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            mCallback.onViewStart(mView, mMotionEvent, ViewState.SCALE);
            return true;
        }
    }

    private class RotateListener extends RotateGestureDetector.SimpleOnRotateGestureListener {
        @Override
        public boolean onRotate(RotateGestureDetector detector) {
            mRotate -= detector.getRotationDegreesDelta();
            updateView();
            mCallback.onViewChange(mView, mMotionEvent, ViewState.ROTATE);
            return false;
        }

        @Override
        public void onRotateEnd(RotateGestureDetector detector) {
            super.onRotateEnd(detector);
            mCallback.onViewReleased(mView, mMotionEvent, ViewState.ROTATE);
        }

        @Override
        public boolean onRotateBegin(RotateGestureDetector detector) {
            mCallback.onViewStart(mView, mMotionEvent, ViewState.ROTATE);
            return true;
        }
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            mCallback.onLongPressed(e);
        }
    }

    public void updateView() {
        mView.setScaleX(mScaleFactor);
        mView.setScaleY(mScaleFactor);
        mView.setRotation(mRotate);
        ViewCompat.postInvalidateOnAnimation(mView);
    }

    static public abstract class Callback {
        public boolean canHandleScale(View v, MotionEvent event) {
            return false;
        }

        public boolean canHandleRotate(View v, MotionEvent event) {
            return false;
        }

        public boolean canHandleMove(View v, MotionEvent event) {
            return true;
        }

        public int claimViewPositionX(View v, float dx) {
            return (int) dx;
        }

        public int claimViewPositionY(View v, float dy) {
            return (int) dy;
        }

        public void onViewStart(View v, MotionEvent event, ViewState viewState) {
        }

        public void onViewChange(View v, MotionEvent event, ViewState viewState) {
        }

        public void onViewReleased(View v, MotionEvent event, ViewState viewState) {
        }

        public void onLongPressed(MotionEvent e) {

        }
    }
}
