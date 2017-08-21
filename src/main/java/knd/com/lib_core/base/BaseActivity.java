package knd.com.lib_core.base;

import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.UUID;

import knd.com.lib_core.R;
import knd.com.lib_core.sp.P;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by phearom on 7/13/16.
 */
@SuppressWarnings("unchecked")
public abstract class BaseActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private View mDecorView;

    @AnimRes
    protected abstract int getOverlayAnimIn();

    @AnimRes
    protected abstract int getOverlayAnimOut();

    public View getDecorView() {
        return this.mDecorView;
    }

    public void showOverlay(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(getOverlayAnimIn(), getOverlayAnimOut())
                .replace(mDecorView.getId(), fragment).commit();
    }

    public void removeOverlay() {
        BaseFragment f = (BaseFragment) getSupportFragmentManager().findFragmentById(mDecorView.getId());
        if (f != null) {
            getSupportFragmentManager().beginTransaction().remove(f).commit();
        }
    }

    protected boolean onFragmentBackPressed() {
        BaseFragment f = (BaseFragment) getSupportFragmentManager().findFragmentById(mDecorView.getId());
        if (f != null) {
            f.onBackPressed();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDecorView = getWindow().getDecorView();
        mDecorView.setId(View.generateViewId());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
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
                    .setRequestCode(P.RC_SETTINGS_SCREEN_PERM)
                    .build()
                    .show();
        }
    }

    protected String generateId() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
