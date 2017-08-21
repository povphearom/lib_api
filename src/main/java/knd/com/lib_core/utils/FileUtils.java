package knd.com.lib_core.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;

import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

/**
 * Created by Afinos on 1/12/2017.
 */

public class FileUtils {
    public FileUtils() {
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }

    public static String getMimeType(Context context, Uri uri) {
        return context.getContentResolver().getType(uri);
    }

    public static String getMimeType(String url) {
        return URLConnection.guessContentTypeFromName(url);
    }

    public static boolean isImageFromUrl(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static boolean isDoc(String mimeType) {
        return mimeType != null && (mimeType.contains("doc") || mimeType.contains("docx") || mimeType.contains("gdoc"));
    }

    public static boolean isSheet(String mimeType) {
        return mimeType != null && (mimeType.contains("xls") || mimeType.contains("xlsx") || mimeType.contains("gsheet"));
    }

    public static boolean isPdf(String mimeType) {
        return mimeType != null && mimeType.contains("pdf");
    }

    public static boolean isImageFromUri(Context context, Uri uri) {
        String mimeType = context.getContentResolver().getType(uri);
        return mimeType != null && mimeType.startsWith("image");
    }

    public static String getMediaDuration(String url) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(url);
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInmillisec = Long.parseLong(time);
        long duration = timeInmillisec / 1000;
        long hours = duration / 3600;
        long minutes = (duration - hours * 3600) / 60;
        long seconds = duration - (hours * 3600 + minutes * 60);
        return "" + hours + " " + minutes + "" + seconds;
    }

    public static String getCurrentDuration(long current) {
        String textCurrentDuration = "";
        long m = TimeUnit.MILLISECONDS.toMinutes(current);
        long s = TimeUnit.MILLISECONDS.toSeconds(current);
        if (m > 0)
            textCurrentDuration = String.format("%02d:%02d", m, s / m);
        else
            textCurrentDuration = String.format("00:%02d", s);
        return textCurrentDuration;
    }

    public static Bitmap retrieveVideoFrameFromVideo(String p_videoPath)
            throws Throwable {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = null;
        try {
            retriever = new MediaMetadataRetriever();
            retriever.setDataSource(p_videoPath);
            bitmap = retriever.getFrameAtTime();
        } catch (Exception m_e) {
            throw new Throwable(
                    "Exception in retrieveVideoFrameFromVideo(String p_videoPath)"
                            + m_e.getMessage());
        } finally {
            if (retriever != null) {
                retriever.release();
            }
        }
        return bitmap;
    }
}
