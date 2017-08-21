package knd.com.lib_api.listener;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by phearom on 7/19/17.
 */

public abstract class OnEdgeParentViewScaleListener implements View.OnTouchListener {
    final static private String TAG = "ViewScale";
    private float dX, dY;
    private float touchX = -1, touchY = -1;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ((InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromInputMethod(v.getWindowToken(), 0);
        View parentView = (View) v.getParent();
        if (parentView == null) return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                dX = event.getX();
                dY = event.getY();

                touchX = event.getRawX();
                touchY = event.getRawY();
                Log.v(TAG, "= " + dY);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                float currentX = event.getX();
                float currentY = event.getY();

                if (Math.abs(dX - currentX) > Math.abs(dY
                        - currentY)) {
                    Log.v(TAG, "x");
                    if (dX < currentX) {
                        Log.v(TAG, "right");
                        float offsetX = Math.abs(event.getRawX() - touchX);
                        onScaleRight(parentView, offsetX);
                    }

                    if (dX > currentX) {
                        Log.v(TAG, "left");
                        float offsetX = Math.abs(event.getRawX() - touchX);
                        onScaleLeft(parentView, offsetX);
                    }
                } else {
                    Log.v(TAG, "y ");
                    if (dY < currentY) {
                        Log.v(TAG, "down");
                        float offsetY = Math.abs(event.getRawY() - touchY);
                        onScaleDown(parentView, offsetY);
                    }
                    if (dY > currentY) {
                        Log.v(TAG, "up");
                        float offsetY = Math.abs(event.getRawY() - touchY);
                        onScaleUp(parentView, offsetY);
                    }
                }

                touchX = event.getRawX();
                touchY = event.getRawY();

                break;
            }
        }
        parentView.invalidate();
        parentView.requestLayout();
        return true;
    }

    public abstract void onScaleLeft(View parent, float offsetX);

    public abstract void onScaleUp(View parent, float offsetY);

    public abstract void onScaleRight(View parent, float offsetX);

    public abstract void onScaleDown(View parent, float offsetY);
}
