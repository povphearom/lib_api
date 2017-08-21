package knd.com.lib_api.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

/**
 * Created by itphe on 12/13/2016.
 */

public class RingToneUtil {
    private static Ringtone instance;

    public static void play(Context context) {
        Uri ringSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/chill");
        if (instance == null)
            instance = RingtoneManager.getRingtone(context, ringSound);
        instance.play();
    }

    public static void destroy() {
        if (instance == null) return;
        instance.stop();
    }
}
