package knd.com.lib_core.utils;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by phearom on 2/6/17.
 */

public final class CallUtils {
    private static CallUtils instance;

    private AudioManager mAudioManager;

    private CallUtils(Context context) {
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Window mWindow;
        if (context instanceof Activity)
            mWindow = ((Activity) context).getWindow();
    }

    public static CallUtils init(Context context) {
        if (instance == null)
            instance = new CallUtils(context);
        return instance;
    }

    public static void setAllowScreenLock(Window window, boolean allow) {
        if (allow) {
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            window.clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        }
    }

    public void setSpeakerOn(boolean speakerOn) {
        if (speakerOn)
            mAudioManager.setMode(AudioManager.MODE_NORMAL);
        else
            mAudioManager.setMode(AudioManager.MODE_IN_CALL);
        mAudioManager.setSpeakerphoneOn(speakerOn);
    }

    public void setMicOn(boolean micOn) {
        mAudioManager.setMode(AudioManager.MODE_IN_CALL);
        mAudioManager.setMicrophoneMute(micOn);
    }

    public boolean isSpeakerOn() {
        return mAudioManager.isSpeakerphoneOn();
    }

    public void toggleSpeaker() {
        setSpeakerOn(!isSpeakerOn());
    }

    public void toggleMic() {
        mAudioManager.setMode(AudioManager.MODE_IN_CALL);
        mAudioManager.setMicrophoneMute(!isMuted());
    }

    public boolean isMuted() {
        return mAudioManager.isMicrophoneMute();
    }
}
