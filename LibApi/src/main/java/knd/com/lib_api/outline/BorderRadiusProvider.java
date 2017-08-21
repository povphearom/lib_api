package knd.com.lib_api.outline;

import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class BorderRadiusProvider extends ViewOutlineProvider {
    private float roundCorner;

    public BorderRadiusProvider() {
        roundCorner = 5;
    }

    public BorderRadiusProvider(int round) {
        roundCorner = round;
    }

    public void setRoundCorner(float roundCorner) {
        this.roundCorner = roundCorner;
    }

    @Override
    public void getOutline(View view, Outline outline) {
        outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), roundCorner);
    }
}