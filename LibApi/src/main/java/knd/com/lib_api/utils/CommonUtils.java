package knd.com.lib_api.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;

import com.afinos.api.application.MyApp;
import com.afinos.api.key.K;
import com.afinos.studyspaced.R;
import com.afinos.studyspaced.model.Country;
import com.afinos.studyspaced.model.realm.GeoRealm;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by pisey on 03-Nov-16.
 */

public class CommonUtils {

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ALLOWED_GEOLOCATION_ORIGINS);
            return !TextUtils.isEmpty(locationProviders);
        }


    }


    public static void checkGPSStatus(final Context context) {
        LocationManager locationManager = null;
        boolean gps_enabled = false;
        boolean network_enabled = false;
        if (locationManager == null) {
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        }
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        if (!gps_enabled && !network_enabled) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("GPS not enabled, please allow your location now.");
            dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //this will navigate user to the device location settings screen
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                }
            });
            dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = dialog.create();
            alert.show();
        }
    }

    public static String capitalizeFirstLetter(String original) {
        if (original == null || original.length() == 0) {
            return original;
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static String getStringEmpty(String str) {
        String string = "";
        if (!TextUtils.isEmpty(str))
            string = str;
        return string;
    }

    public static String getContentType(String url) {
        String extension = url.substring(url.lastIndexOf("."));
        Log.i("MimType", "Extention : " + extension);
        if (extension.equalsIgnoreCase(".m4a")) {
            return "audio/x-m4a";
        } else if (extension.equalsIgnoreCase(".mp3")) {
            return "audio/mp3";
        }
        String mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(extension);
        String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap);
        return mimeType;
    }

    public static String getMimeTypeForUri(Context context, Uri uri) {
        if (uri.getScheme() == null) return null;
        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            ContentResolver cr = context.getContentResolver();
            return cr.getType(uri);
        } else if (uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            String extension = MimeTypeMap.getFileExtensionFromUrl(uri.getPath()).toLowerCase();
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        } else {
            Log.d("URI ContentType", "Could not determine mime type for Uri " + uri);
            return null;
        }
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String formatDate(long milli) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        return dateFormat.format(new Date(milli));
    }

    public static boolean isFile(String path) {
        if (!TextUtils.isEmpty(path)) {
            try {
                File file = new File(path);
                return file.isFile();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    public static String getExtension(String fileName) {
        String encoded;
        try {
            encoded = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            encoded = fileName;
        }
        return MimeTypeMap.getFileExtensionFromUrl(encoded).toLowerCase();
    }

    public static String getFilenameFromUrl(String fileUrl) {
        try {
            String encoded = URLEncoder.encode(fileUrl, "UTF-8");
            return encoded.substring(-encoded.lastIndexOf("/"));
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public static boolean isTablet(Context context) {
        TelephonyManager manager =
                (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (manager.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
            //Tablet
            return true;
        } else {
            //Mobile
            return false;
        }
    }


    public static boolean isTabletDevice(Context activityContext) {

        boolean device_large = ((activityContext.getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE);
        DisplayMetrics metrics = new DisplayMetrics();
        Activity activity = (Activity) activityContext;
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        if (device_large) {
            //Tablet
            if (metrics.densityDpi == DisplayMetrics.DENSITY_DEFAULT) {
                return true;
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM) {
                return true;
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_TV) {
                return true;
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_HIGH) {
                return true;
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_280) {
                return true;
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH) {
                return true;
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_400) {
                return true;
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_XXHIGH) {
                return true;
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_560) {
                return true;
            } else if (metrics.densityDpi == DisplayMetrics.DENSITY_XXXHIGH) {
                return true;
            }
        } else {
            //Mobile
        }
        return false;
    }

    public static String getCountryName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address result;

            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0).getCountryName();
            }
            return null;
        } catch (IOException ignored) {
            //do something
            return "";
        }


    }

    public static String getCountryCode(String countryName) {

        // Get all country codes in a string array.
        String[] isoCountryCodes = Locale.getISOCountries();
        Map<String, String> countryMap = new HashMap<>();

        // Iterate through all country codes:
        for (String code : isoCountryCodes) {
            // Create a locale using each country code
            Locale locale = new Locale("", code);
            // Get country name for each code.
            String name = locale.getDisplayCountry();
            // Map all country names and codes in key - value pairs.
            countryMap.put(name, code);
        }
        // Get the country code for the given country name using the map.
        // Here you will need some validation or better yet
        // a list of countries to give to user to choose from.
        String countryCode = countryMap.get(countryName); // "NL" for Netherlands.

        return countryCode;

    }

    public static String getCountryName(String countryCode) {
        Locale loc = new Locale("", countryCode);
        return (!TextUtils.isEmpty(loc.getDisplayCountry()) ? loc.getDisplayCountry() : "");
    }

    public static String getZipCodeFromLocation(Location location) {
        Address address = getAddressFromLocation(location);
        return address.getPostalCode() == null ? "" : address.getPostalCode();
    }

    public static Address getAddressFromLocation(Location location) {
        Geocoder geocoder = new Geocoder(MyApp.init().getApplicationContext());
        Address address = new Address(Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            if (addresses.size() > 0) {
                address = addresses.get(0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

    public static String getZipCode(String countryCode) {
        Country country;
        String zipCode = "";
        for (String s : MyApp.init().getApplicationContext().getResources().getStringArray(R.array.CountriesCode)) {
            String[] g = s.split(",");
            country = new Country();
            country.setName(AppUtils.getZipCode(g[1]).trim());
            country.setZipCode(g[0]);
            country.setCountryCode(g[1]);
            zipCode = country.getZipCode();
            if (countryCode.equals(country.getCountryCode())) {
                break;
            }
        }
        return zipCode;
    }

    public static void hideKeyboard(Context context) {
        View view = new View(context);
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(view.getWindowToken(), imm.HIDE_IMPLICIT_ONLY);
    }

    public static ArrayList getYearArray() {
        ArrayList<String> years = new ArrayList<String>();
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1950; i <= thisYear; i++) {
            years.add(Integer.toString(i));
        }
        return years;
    }

    public static String getShowYearMonth(Date first, Date last) {
        String str = "";
        int year = getDiffYears(first, last);
        int month = getCalculateMonthDistance(first, last);
        if (year == 0) {
            if (month == 1) {
                str = 1 + " Month";
            } else {
                str = month + " Months";
            }
        } else {
            if (year == 1)
                str = year + " Year";
            else
                str = year + " Years";
        }
        return str;
    }

    public static int getDiffYears(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    public static int getCalculateMonthDistance(Date start, Date end) {
        int year1 = getYear(start);
        int year2 = getYear(end);
        int month1 = getMonth(start);
        int month2 = getMonth(end);
        int totalMonth = Math.abs(12 * (year1 - year2) + (month1 - month2));
        return totalMonth != 0 ? totalMonth : 1;
    }

    private static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    private static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static List<Country> getCountries(Context context) {
        List<Country> countryList = new ArrayList<>();
        Country country;
        for (String s : context.getResources().getStringArray(R.array.CountriesCode)) {
            String[] g = s.split(",");
            country = new Country();
            country.setName(AppUtils.getZipCode(g[1]).trim());
            country.setZipCode(g[0]);
            country.setCountryCode(g[1]);
            countryList.add(country);
        }
        return countryList;
    }

    public static SpannableString getSpannableString(Context mContext, String str) {
        SpannableString sb = new SpannableString(str);
        sb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_orange)), 0, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public static SpannableString getSpannableString(Context mContext, String str, int colorId) {
        SpannableString sb = new SpannableString(str);
        sb.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.color_orange)), 0, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }

    public static int getCardSymbol(String cardType) {
        if (TextUtils.isEmpty(cardType)) return R.drawable.ic_credit_debit_card;
        switch (cardType.toUpperCase()) {
            case K.EvaluatedType.AMERICAN_EXPRESS:
                return R.drawable.ic_american_exp;
            case K.EvaluatedType.MASTERCARD:
                return R.drawable.ic_master_card;
            case K.EvaluatedType.VISA:
                return R.drawable.ic_visa;
            case K.EvaluatedType.DINERS_CLUB:
                return R.drawable.ic_dinner_clup;
            case K.EvaluatedType.DISCOVER:
                return R.drawable.ic_discover_card;
            case K.EvaluatedType.JCB:
                return R.drawable.ic_jcb_card;
            default:
                return R.drawable.ic_credit_debit_card;
        }

    }

    public static String loadJSONFromAsset(Context context, String jsonFile) {
        String json = null;
        try {
            InputStream is = context.getResources().getAssets().open(jsonFile);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static List<Country> getLanguageData(Context context) {
        List<Country> languageList = new ArrayList<>();
        Country language;
        try {
            JSONArray jsonArray = new JSONArray(CommonUtils.loadJSONFromAsset(context, "lanuguage.json"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                language = new Country();
                language.setName(jsonObject.getString("name"));
                language.setCountryCode(jsonObject.getString("code"));
                languageList.add(language);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return languageList;
    }

    public static GeoRealm getLangLateByCountryCode(Context context, String countryCode) {
        GeoRealm geoRealm = new GeoRealm();
        try {
            JSONObject jsonObject = new JSONObject(CommonUtils.loadJSONFromAsset(context, "countrycode_latlong.json"));
            JSONObject jsonObject1 = jsonObject.getJSONObject(countryCode);
            geoRealm.setLat(Double.parseDouble(jsonObject1.getString("lat")));
            geoRealm.setLon(Double.parseDouble(jsonObject1.getString("long")));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return geoRealm;
    }

    public static String getTimeZone() {
        TimeZone timeZone = TimeZone.getDefault();
        return timeZone.getID();

    }

    public static int getAge(Date dateOfBirth) {

        Calendar today = Calendar.getInstance();
        Calendar birthDate = Calendar.getInstance();

        int age = 0;

        birthDate.setTime(dateOfBirth);
        if (birthDate.after(today)) {
            throw new IllegalArgumentException("Can't be born in the future");
        }

        age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ((birthDate.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                (birthDate.get(Calendar.MONTH) > today.get(Calendar.MONTH))) {
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        } else if ((birthDate.get(Calendar.MONTH) == today.get(Calendar.MONTH)) &&
                (birthDate.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH))) {
            age--;
        }

        return age;
    }

    public static boolean isAllNull(Iterable<?> list) {
        for (Object obj : list) {
            if (obj != null)
                return false;
        }

        return true;
    }

    public static float randomNextFloat(float min, float max) {
        Random random = new Random();
        return min + random.nextFloat() * (max - min);
    }


}
