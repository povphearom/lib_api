package knd.com.lib_api.listener;

import android.support.design.widget.AppBarLayout;

/**
 * Created by Dell on 11/18/2016.
 */

public abstract class OnMyAppBarOffsetChangedListener implements AppBarLayout.OnOffsetChangedListener {
    private State state;

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (state != State.EXPANDED) {
                onStateChange(State.EXPANDED);
            }
            state = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (state != State.COLLAPSED) {
                onStateChange(State.COLLAPSED);
            }
            state = State.COLLAPSED;
        } else {
            if (state != State.IDLE) {
                onStateChange(State.IDLE);
            }
            state = State.IDLE;
        }
    }

    public State getState() {
        return this.state;
    }

    public void onStateChange(State toolbarChange) {
    }

    public enum State {
        COLLAPSED,
        EXPANDED,
        IDLE
    }
}
