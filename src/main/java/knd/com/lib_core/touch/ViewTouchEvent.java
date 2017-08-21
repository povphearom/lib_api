package knd.com.lib_core.touch;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by phearom on 7/19/17.
 */

public class ViewTouchEvent implements View.OnTouchListener {
    public static final int INVALID_POINTER = -1;

    private float dX, dY;
    private View mView;
    private Context mContext;

    private Callback mCallback;

    public ViewTouchEvent(Context context, Callback callback) {
        this.mContext = context;
        this.mCallback = callback;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (view == null) return true;
        mView = view;
        if (!mCallback.canHandle(mView, event.getPointerId(0)))
            return true;

        mView.bringToFront();
        if (!mCallback.canHandleMove(mView, event))
            return true;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = mView.getX() - event.getRawX();
                dY = mView.getY() - event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float cx = mCallback.claimViewPositionX(mView, event.getRawX() + dX);
                float cy = mCallback.claimViewPositionY(mView, event.getRawY() + dY);
                mView.setX(cx);
                mView.setY(cy);
                mCallback.onViewMoving(mView, event, cx, cy);
                break;
            case MotionEvent.ACTION_UP:
                cx = mCallback.claimViewPositionX(mView, event.getRawX() + dX);
                cy = mCallback.claimViewPositionY(mView, event.getRawY() + dY);
                mCallback.onViewMoveEnded(mView, cx, cy);
                break;
        }
        mView.requestLayout();
        mView.invalidate();
        mView = null;
        return true;
    }

    static public abstract class Callback {

        public abstract boolean canHandle(View v, int pointerId);

        public int claimViewPositionX(View v, float dx) {
            return (int) dx;
        }

        public int claimViewPositionY(View v, float dy) {
            return (int) dy;
        }

        public boolean canHandleMove(View v, MotionEvent event) {
            return true;
        }

        public void onViewMoving(View v, MotionEvent event, float dx, float dy) {

        }

        public void onViewMoveEnded(View v, float dx, float dy) {

        }
    }
}
