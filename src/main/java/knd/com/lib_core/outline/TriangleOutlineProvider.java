package knd.com.lib_core.outline;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class TriangleOutlineProvider extends ViewOutlineProvider {
    @Override
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(view.getWidth()/2, 0, view.getWidth()/2, view.getHeight(),2f);
    }
}