package knd.com.lib_api.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afinos.api.adapter.CountryListAdapter;
import com.afinos.api.listener.DialogCallBackEventListener;
import com.afinos.api.listener.TextSearchEvent;
import com.afinos.api.server.rest.OnResponseCallback;
import com.afinos.easydialog.EasyDialog;
import com.afinos.studyspaced.R;
import com.afinos.studyspaced.databinding.AlertDialogPermissionRoomBinding;
import com.afinos.studyspaced.databinding.AlertLoginDropboxBinding;
import com.afinos.studyspaced.databinding.AlertLoginFacebookBinding;
import com.afinos.studyspaced.databinding.AlertLoginGplusBinding;
import com.afinos.studyspaced.databinding.AlertLoginLocalBinding;
import com.afinos.studyspaced.databinding.AlertUserSummaryBinding;
import com.afinos.studyspaced.databinding.PackageOfferDetailDialogBinding;
import com.afinos.studyspaced.model.Country;
import com.afinos.studyspaced.model.realm.UserRealm;
import com.afinos.studyspaced.viewmodel.user.VmUser;
import com.dropbox.core.android.Auth;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.ArrayList;

import static com.afinos.api.utils.Strings.getString;

/**
 * @Copy Right 2012-2016, Afinos, Inc., or its affiliates
 * @Author: Afinos Team
 * @Created By pisey on 14-Sep-16 3:47 PM
 */

public class AlertUtils {
    private static AlertUtils instance;
    private Context mContext;
    private AlertDialog mDialog;

    private void initDialog(Context context) {
        mContext = context;
        mDialog = new AlertDialog.Builder(context).create();
    }

    public static AlertUtils init(Context context) {
        if (instance == null) {
            instance = new AlertUtils();
        }
        instance.initDialog(context);
        return instance;
    }

    public void info(String title, String message) {
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(true);
        mDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mDialog.show();
    }

    public void info1(String message) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        AlertDialogPermissionRoomBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.alert_dialog_permission_room, null, false);
        binding.textMessage.setText(message);
        binding.actionOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.setView(binding.getRoot());
        mDialog.show();
    }

    public void info(String message) {
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mDialog.show();
    }

    public void makeSure(String title, String message, DialogInterface.OnClickListener onOKListener) {
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", onOKListener);
        mDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mDialog.show();
    }

    public void deleteDialog(String title, String message, DialogInterface.OnClickListener onOKListener) {
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", onOKListener);
        mDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mDialog.show();
    }

    public void deleteDialog(String title, String message, DialogInterface.OnClickListener onOKListener, DialogInterface.OnClickListener onCancelListener) {
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Delete", onOKListener);
        mDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", onCancelListener);
        mDialog.show();
    }

    public void confirmDialog(String title, String message, String positiveTitle, String negativeTitle, DialogInterface.OnClickListener onOKListener) {
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setButton(AlertDialog.BUTTON_POSITIVE, positiveTitle, onOKListener);
        mDialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeTitle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mDialog.show();
    }

    public void viewProfile(UserRealm userRealm, View.OnClickListener submitListener) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        AlertUserSummaryBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.alert_user_summary, null, false);

        VmUser userViewModel = new VmUser(userRealm);
        binding.setUser(userViewModel);
        binding.btnActionExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        binding.btnActionSubmit.setOnClickListener(submitListener);
        mDialog.setView(binding.getRoot());
        mDialog.show();
    }

    public void roomSetting() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        AlertUserSummaryBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.alert_user_summary, null, false);

        binding.btnActionExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });
        mDialog.show();
    }

    public void loginLocalContact() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        AlertLoginLocalBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.alert_login_local, null, false);

        mDialog.setView(binding.getRoot());
        mDialog.show();
    }

    public void loginFacebookContact(final OnResponseCallback onResponseCallback) {
        Log.d("AlertUtils", "facebook");
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        CallbackManager mCallbackManager = CallbackManager.Factory.create();
        AlertLoginFacebookBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.alert_login_facebook, null, false);
//        LoginButton loginButton = binding.facebookLogin;
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(final LoginResult loginResult) {
//                Log.d("AlertUtils", "facebook:onSuccess:" + loginResult);
//                onResponseCallback.onResponseSuccess("success");
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d("AlertUtils", "facebook:onCancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d("AlertUtils", "facebook:onError", error);
//            }
//        });

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                Log.d("AlertUtils", "facebook:onSuccess:" + loginResult);
                onResponseCallback.onResponseSuccess("success");
            }

            @Override
            public void onCancel() {
                Log.d("AlertUtils", "facebook:onCancel");
//                hideProgressDialog();
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("AlertUtils", "facebook:onError", error);
//                hideProgressDialog();
            }
        });


        mDialog.setView(binding.getRoot());
        mDialog.show();
    }

    public void loginDropbox() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        AlertLoginDropboxBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.alert_login_dropbox, null, false);
        binding.btnActionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.startOAuth2Authentication(mContext, getString(R.string.app_key_dropbox));
                mDialog.dismiss();
            }
        });

        mDialog.setView(binding.getRoot());
        mDialog.show();
    }


    public void loginGplusContact() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        AlertLoginGplusBinding binding = DataBindingUtil.inflate(layoutInflater, R.layout.alert_login_gplus, null, false);

        mDialog.setView(binding.getRoot());
        mDialog.show();
    }

    public void countryChooser(final DialogCallBackEventListener listener) {
        ((Activity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        final CountryListAdapter adapter = new CountryListAdapter(mContext);
        Country country;
        for (String s : mContext.getResources().getStringArray(R.array.CountriesCode)) {
            String[] g = s.split(",");
            country = new Country();
            country.setName(AppUtils.getZipCode(g[1]).trim());
            country.setZipCode(g[0]);
            country.setCountryCode(g[1]);
            adapter.addItem(country);
        }
        ListView listView = new ListView(mContext);
        listView.setSelector(android.R.color.transparent);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Country c = (Country) adapter.getItem(position);
                listener.onItemClick(c.getZipCode(), c.getCountryCode(), c.getName());
                ((Activity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                mDialog.dismiss();
            }
        });

        AppCompatEditText editText = new AppCompatEditText(mContext);
        editText.setBackgroundColor(ContextCompat.getColor(mContext, R.color.low_light));
        editText.setPadding(12, 12, 12, 12);
        editText.setHint("Filter Country");
        editText.addTextChangedListener(new TextSearchEvent(300) {
            @Override
            public void onSearch(String s, boolean isTyping) {
                if (!isTyping)
                    adapter.searchFilter(s);
            }
        });

        LinearLayoutCompat linearLayoutCompat = new LinearLayoutCompat(mContext);
        linearLayoutCompat.setOrientation(LinearLayoutCompat.VERTICAL);
        LinearLayoutCompat.LayoutParams layoutParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayoutCompat.LayoutParams listLayoutParams = new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1);
        linearLayoutCompat.addView(editText, layoutParams);
        linearLayoutCompat.addView(listView, listLayoutParams);

        mDialog.setTitle("Choose Country");
        mDialog.setView(linearLayoutCompat);
        mDialog.show();
    }

    public void confirmDialog(String title, String message, DialogInterface.OnClickListener onOKListener) {
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", onOKListener);
        mDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Skip", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mDialog.show();
    }

    public void confirmExit(String title, String message, DialogInterface.OnClickListener onOKListener) {
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", onOKListener);
        mDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mDialog.show();
    }

    public void confirmExits(String title, String message, DialogInterface.OnClickListener onOKListener) {
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", onOKListener);
        mDialog.show();
    }

    public void makeCardDefaultDialog(String title, String message, DialogInterface.OnClickListener onOKListener) {
        mDialog.setTitle(title);
        mDialog.setMessage(message);
        mDialog.setCancelable(false);
        mDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", onOKListener);
        mDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        mDialog.show();
    }

    public void showToast(String toastMessage) {
        Toast toast = Toast.makeText(mContext, toastMessage, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void setItemsEasyDialog(final View v, ArrayList<String> arrayList, final ListView.OnItemClickListener onItemClickListener) {
        final EasyDialog easyDialog = new EasyDialog(mContext);
        ListView listView = new ListView(mContext);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onItemClickListener.onItemClick(adapterView, view, i, l);
                easyDialog.dismiss();
            }
        });
        //listView.setLayoutParams(new ViewGroup.LayoutParams(v.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));
        listView.setLayoutParams(new ViewGroup.LayoutParams(v.getMeasuredWidth(), AppUtils.dpToPixel(v.getContext(), 250)));

        easyDialog
                .setLayout(listView)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setBackgroundColor(ContextCompat.getColor(mContext, R.color.low_light))
                .setLocationByAttachedView(v)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 500, 300, -100, -50, 50, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 200, 0, 200)
                .setAnimationAlphaShow(500, 0.2f, 1.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(false)
                .setMarginLeftAndRight(24, 24)
                .show();
    }

    public void setItemsEasyDialog(final View v, String[] arrayString, final ListView.OnItemClickListener onItemClickListener) {
        final EasyDialog easyDialog = new EasyDialog(mContext);
        ListView listView = new ListView(mContext);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayString);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onItemClickListener.onItemClick(adapterView, view, i, l);
                easyDialog.dismiss();
            }
        });
        listView.setLayoutParams(new ViewGroup.LayoutParams(v.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));

        easyDialog
                .setLayout(listView)
                .setGravity(EasyDialog.GRAVITY_BOTTOM)
                .setBackgroundColor(ContextCompat.getColor(mContext, R.color.low_light))
                .setLocationByAttachedView(v)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 500, 300, -100, -50, 50, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 200, 0, 200)
                .setAnimationAlphaShow(500, 0.2f, 1.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(false)
                .setMarginLeftAndRight(24, 24)
                .show();
    }

    public void setItemsEasyDialog(final View v, int gravity, String[] arrayString, final ListView.OnItemClickListener onItemClickListener) {
        final EasyDialog easyDialog = new EasyDialog(mContext);
        ListView listView = new ListView(mContext);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayString);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onItemClickListener.onItemClick(adapterView, view, i, l);
                easyDialog.dismiss();
            }
        });
        listView.setLayoutParams(new ViewGroup.LayoutParams(v.getMeasuredWidth(), ViewGroup.LayoutParams.WRAP_CONTENT));

        easyDialog
                .setLayout(listView)
                .setGravity(gravity)
                .setBackgroundColor(ContextCompat.getColor(mContext, R.color.low_light))
                .setLocationByAttachedView(v)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 500, 300, -100, -50, 50, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 200, 0, 200)
                .setAnimationAlphaShow(500, 0.2f, 1.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(false)
                .setMarginLeftAndRight(24, 24)
                .show();
    }

    public void setItemsEasyDialog(final View v, int gravity, String title, String content) {
        final EasyDialog easyDialog = new EasyDialog(mContext);
        PackageOfferDetailDialogBinding packageOfferDetailDialogBinding = DataBindingUtil.inflate(easyDialog.getDialog().getLayoutInflater(), R.layout.package_offer_detail_dialog, null, false);
        packageOfferDetailDialogBinding.txvTitle.setText(title);
        packageOfferDetailDialogBinding.txvContent.setText(!TextUtils.isEmpty(content) ? content : "Unknown Description");
        packageOfferDetailDialogBinding.getRoot().setLayoutParams(new ViewGroup.LayoutParams(v.getMeasuredWidth() + AppUtils.dpToPixel(mContext, 200), ViewGroup.LayoutParams.WRAP_CONTENT));

        easyDialog
                .setLayout(packageOfferDetailDialogBinding.getRoot())
                .setGravity(gravity)
                .setBackgroundColor(ContextCompat.getColor(mContext, R.color.low_light))
                .setLocationByAttachedView(v)
                .setAnimationTranslationShow(EasyDialog.DIRECTION_Y, 500, 300, -100, -50, 50, 0)
                .setAnimationTranslationDismiss(EasyDialog.DIRECTION_Y, 200, 0, 200)
                .setAnimationAlphaShow(500, 0.2f, 1.0f)
                .setTouchOutsideDismiss(true)
                .setMatchParent(false)
                .setMarginLeftAndRight(24, 24)
                .show();
    }
}

