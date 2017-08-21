package knd.com.lib_core.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;

import com.afinos.studyspaced.BuildConfig;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by itphe on 6/7/2017.
 */

public class FileSave {
    private static Uri mFileUri;
    private Context mContext;
    private OnFileSaveCallback mOnFileSaveCallback;

    private String mExt;

    public FileSave(Context context, OnFileSaveCallback onFileSaveCallback) {
        this.mContext = context;
        this.mOnFileSaveCallback = onFileSaveCallback;
    }

    public void execute(Uri uri, String ext) {
        this.mExt = ext;
        Glide.with(mContext)
                .load(uri)
                .asBitmap()
                .toBytes()
                .atMost()
                .skipMemoryCache(true)
                .into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(byte[] resource, GlideAnimation<? super byte[]> ignore) {
                        new SaveAsFileTask().execute(resource);
                    }

                    @Override
                    public void onLoadFailed(Exception ex, Drawable ignore) {
                        if (ex != null)
                            ex.printStackTrace();
                    }
                })
        ;
    }

    private class SaveAsFileTask extends AsyncTask<byte[], Void, File> {
        @Override
        protected File doInBackground(byte[]... params) {
            try {
                File target = createFile(mContext, mExt);
                OutputStream out = new FileOutputStream(target);
                out.write(params[0]);
                return target;
            } catch (IOException ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(@Nullable File result) {
            if (mOnFileSaveCallback != null)
                mOnFileSaveCallback.onSaved(mFileUri);
        }
    }

    public static File createFile(Context context, String ext) throws IOException {
        String imageFileName = System.currentTimeMillis() + "_";

        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        File image = File.createTempFile(
                imageFileName,
                ext,
                storageDir
        );

        if (Build.VERSION.SDK_INT >= 24) {
            mFileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", image);
        } else {
            mFileUri = Uri.fromFile(image);
        }
        return image;
    }

    public static File createPathFile(Context context, String fileName) throws IOException {
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        String name = fileName.substring(0, fileName.lastIndexOf(".") - 1);
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        File image = File.createTempFile(
                name,
                ext,
                storageDir
        );

        if (Build.VERSION.SDK_INT >= 24) {
            mFileUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", image);
        } else {
            mFileUri = Uri.fromFile(image);
        }
        return image;
    }

    public static Uri getFileUri() {
        return mFileUri;
    }

    public interface OnFileSaveCallback {
        void onSaved(Uri uri);
    }
}
