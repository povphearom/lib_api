package knd.com.lib_api.outline;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class CircleOutlineProvider extends ViewOutlineProvider {
    @Override
    public void getOutline(View view, Outline outline) {
        outline.setOval(0, 0, view.getWidth(), view.getHeight());
    }
}