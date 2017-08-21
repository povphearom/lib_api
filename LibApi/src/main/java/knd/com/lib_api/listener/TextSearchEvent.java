package knd.com.lib_api.listener;

import android.os.Handler;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by phearom on 2/24/16.
 */
public abstract class TextSearchEvent implements SearchView.OnQueryTextListener, TextWatcher {
    private final Handler timeoutHandler = new Handler();
    private int TYPING_TIMEOUT = 5000; // 5 seconds timeout
    private boolean isTyping = false;
    private String newText;
    private final Runnable typingTimeout = new Runnable() {
        public void run() {
            isTyping = false;
            onSearch(newText, isTyping);
        }
    };

    public TextSearchEvent(int timeout) {
        if (timeout > 0)
            this.TYPING_TIMEOUT = timeout;
    }

    @Override
    public boolean onQueryTextSubmit(String newText) {
        onSearch(newText, true);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        this.newText = newText;
        timeoutHandler.removeCallbacks(typingTimeout);
        if (newText.length() > 0) {
            // schedule the timeout
            timeoutHandler.postDelayed(typingTimeout, TYPING_TIMEOUT);
            if (!isTyping) {
                isTyping = true;
                onSearch(newText, isTyping);
            }
        } else {
            isTyping = false;
            onSearch(newText, isTyping);
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        this.newText = s.toString();
        timeoutHandler.removeCallbacks(typingTimeout);
        if (newText.length() > 0) {
            timeoutHandler.postDelayed(typingTimeout, TYPING_TIMEOUT);
            if (!isTyping) {
                isTyping = true;
                onSearch(newText, isTyping);
            }
        } else {
            isTyping = false;
            onSearch(newText, isTyping);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    public abstract void onSearch(String s, boolean isTyping);
}
