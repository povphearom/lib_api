package knd.com.lib_api.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.afinos.api.application.MyApp;
import com.afinos.api.permission.usage.PermissionChecker;
import com.afinos.studyspaced.R;

import java.io.File;
import java.util.Locale;
import java.util.Random;

/**
 * @Copy Right 2012-2016, Afinos, Inc., or its affiliates
 * @Author: Afinos Team
 * @Created by phearom on 10/26/16 2:59 PM
 **/
public class AppUtils {
    private String[] colors = {"#ff5747", "#ff5964", "#bf9043", "#ff9500", "#f0dc3c", "#a2e61c", "#02de71", "#00d1ce", "#19b5fc", "#2094fa", "#987cf7", "#f78ac0"};
    private static AppUtils instance;
    private Context mContext;

    public static AppUtils init(Context context) {
        if (instance == null)
            instance = new AppUtils();
        instance.setContext(context);
        return instance;
    }

    public static String getZipCode(String ssid) {
        Locale locale = new Locale("", ssid);
        return locale.getDisplayName().trim();
    }

    public boolean hasPermission(String permission) {
        if (checkVersion()) {
            return (ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    private boolean checkVersion() {
        return (Build.VERSION.SDK_INT > 22);
    }

    private void setContext(Context context) {
        this.mContext = context;
    }

    public String getRealPathFromURI(Uri contentURI) {
        String result = null;
        Cursor cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            result = contentURI.getPath();
        } else {
            if (cursor.moveToFirst()) {
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                result = cursor.getString(idx);
            }
            cursor.close();
        }
        return result;
    }

    public String getRealPathFromContentURI(Uri uri) {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(mContext.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            boolean isGoogle = isGooglePhotosUri(uri);
            if (isGoogle)
                return uri.getLastPathSegment();
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = mContext.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }


    public boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public boolean isGoogleDriveUri(Uri uri) {
        return "com.google.android.apps.docs.storage".equals(uri.getAuthority());
    }


    private boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public void deleteFile(Uri uri) {
        File file = new File(getRealPathFromURI(uri));
        file.delete();
        mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(getRealPathFromURI(uri)))));
    }

    public File getAlbumStorageDir(String albumName) {
        File file = new File(mContext.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES), albumName);
        if (!file.mkdirs()) {
            Log.e("App", "Directory not created");
        }
        return file;
    }

    public Bitmap generateAvatar(int res) {
        Bitmap bitmap = BitmapFactory.decodeResource(MyApp.init().getResources(), res);
        bitmap = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(getRandomColor());

        final Rect rect = new Rect(0, 0, bitmap.getWidth() + 10, bitmap.getHeight() + 10);
        final RectF rectF = new RectF(rect);
        canvas.drawOval(rectF, paint);

        return bitmap;
    }

    public int getRandomColor() {
        Random rnd = new Random();
        return Color.parseColor(colors[rnd.nextInt(colors.length - 1)]);
    }

    public Bitmap generateAvatar(String name) {
        if (TextUtils.isEmpty(name))
            name = "?";
        name = name.toUpperCase(Locale.US);
        Bitmap bitmap = Bitmap.createBitmap(getSize(), getSize(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint2 = new Paint();
        paint2.setColor(getRandomColor());

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        canvas.drawOval(rectF, paint2);

        Paint paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(getTextSize());
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextScaleX(1);
        canvas.drawText(name, canvas.getWidth() / 2,
                ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2)), paint);

        return bitmap;
    }

    private int getSize() {
        return Resources.getSystem().getDimensionPixelSize(android.R.dimen.notification_large_icon_height);
    }

    private int getTextSize() {
        return MyApp.init().getResources().getDimensionPixelSize(R.dimen.icon_text_size);
    }

    public int getSize(int id) {
        return mContext.getResources().getDimensionPixelSize(id);
    }

    public int getFullWidth() {
        return mContext.getResources().getDisplayMetrics().widthPixels;
    }

    public int getFullHeight() {
        return mContext.getResources().getDisplayMetrics().heightPixels;
    }

    public void showKeyboard(View v, boolean show) {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (show)
            inputMethodManager.showSoftInput(v, 0);
        else {
            inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    public static int dpToPixel(Context context, float dp) {
        if (context == null)
            return 0;
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }

    public static SpannableStringBuilder getSpanTextColor(String s, int c) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        builder.append(s);
        builder.setSpan(new ForegroundColorSpan(c), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    public static void openSettingDetail(final Context context, String... permission) {
        if (permission == null) return;
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : permission) {
            stringBuilder.append(",");
            stringBuilder.append(s);
        }

        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Permission warning");
        dialog.setMessage("You has denied permission of (" + stringBuilder.substring(1) + "). Do you want to allow it?");
        dialog.setCancelable(true);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return;
                PermissionChecker.openSettingsScreen(context);
            }
        });
        dialog.show();
    }

    public int getNavigationBarHeight(int orientation) {
        if (!hasNavBar()) return 0;
        Resources resources = mContext.getResources();
        int id = resources.getIdentifier(orientation == Configuration.ORIENTATION_PORTRAIT ? "navigation_bar_height" : "navigation_bar_height_landscape",
                "dimen", "android");
        if (id > 0) {
            return resources.getDimensionPixelSize(id);
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public boolean hasNavBar() {
        Resources resources = mContext.getResources();
        int id = resources.getIdentifier("config_showNavigationBar", "bool", "android");
        return id > 0 && resources.getBoolean(id);
    }

    public static int getSelectableBackgroundRes(Context context) {
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        return outValue.resourceId;
    }
}
