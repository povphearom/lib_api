package knd.com.lib_core.utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

/**
 * Created by itphe on 12/13/2016.
 */

public class MediaPlayerUtil {
    private static MediaPlayer instance;

    public static void play(Context context, Uri source, boolean repeat) {
        if (instance == null)
            instance = MediaPlayer.create(context, source);
        instance.setLooping(repeat);
        instance.start();
    }

    public static void destroy() {
        if (instance == null) return;
        instance.stop();
        instance.release();
        instance = null;
    }
}
