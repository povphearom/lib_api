package knd.com.lib_api.base;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

import knd.com.lib_api.R;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by phearom on 2/2/17.
 */

public abstract class BaseDialogFragment extends AppCompatDialogFragment implements EasyPermissions.PermissionCallbacks {
    private static final int RC_SETTINGS_SCREEN_PERM = 123;
    protected static final int RC_APP_PERM = 124;
    private static final String TAG = BaseDialogFragment.class.getSimpleName();
    protected IntentFilter mIntentFilter;
    protected boolean didFullscreen;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, final Intent intent) {
            if (intent == null) return;
            if (TextUtils.isEmpty(intent.getAction())) return;
            if (null == getActivity()) return;
            onReceiveNotification(intent);
        }
    };

    public boolean isFullscreen() {
        return this.didFullscreen;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        InputMethodManager mInputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, getStyle());
        mIntentFilter = new IntentFilter();
    }

    @Override
    public void onStart() {
        super.onStart();
        setFullScreenMode(false);
        if (getContext() != null)
            getContext().registerReceiver(receiver, mIntentFilter);
    }

    @Override
    public void onStop() {
        if (getContext() != null)
            getContext().unregisterReceiver(receiver);
        super.onStop();
    }

    public abstract int getDialogWidth();

    public abstract int getDialogHeight();

    public abstract int getStyle();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AppCompatDialog(getContext()) {
            @Override
            public void onBackPressed() {
                BaseDialogFragment.this.onBackPressed();
                super.onBackPressed();
            }
        };
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void setFullScreenMode(boolean isFullscreen) {
        if (isFullscreen) {
            prepareFullscreenUI(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, 10f);
        } else {
            prepareFullscreenUI(getDialogWidth(), getDialogHeight(), 10f);
        }
        didFullscreen = isFullscreen;
    }

    protected void toggleFullscreen() {
        didFullscreen = !didFullscreen;
        setFullScreenMode(didFullscreen);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void prepareFullscreenUI(int w, int h, float elevation) {
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setLayout(w, h);
            if (getView() != null)
                getView().setElevation(elevation);
        }
    }

    protected void onBackPressed() {
        dismiss();
    }

    public interface DialogFragmentCallback<T> {
        void onResult(T result);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this)
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setRationale(getString(R.string.rationale_ask_again))
                    .setPositiveButton("Settings")
                    .setNegativeButton("Cancel")
                    .setRequestCode(RC_SETTINGS_SCREEN_PERM)
                    .build()
                    .show();
        }
    }

    public void onReceiveNotification(Intent intent) {

    }

    public int getRealHeight() {
        if (getDialog().getWindow() == null) return 0;
        return getDialog().getWindow().getDecorView().getHeight();
    }

    public int getRealWidth() {
        if (getDialog().getWindow() == null) return 0;
        return getDialog().getWindow().getDecorView().getWidth();
    }
}
