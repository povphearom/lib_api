package knd.com.lib_core.utils;

import android.text.TextUtils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Copy Right 2012-2016, Afinos, Inc., or its affiliates
 * @Author: Afinos Team
 **/
public class ValidationUtils {

    public static boolean validatePassword(String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }


    public static boolean validateEmail(String email) {

        String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean validateName(String name) {

        String namePattern = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]*[a-zA-Z0-9]$";

        Pattern pattern = Pattern.compile(namePattern);
        Matcher matcher = pattern.matcher(name);

        return matcher.matches();
    }


    // these 3 method use for cut 0 front number phone
    public static String zeroHead(String str) {
        String str1;
        String c[] = str.split("");
        if (c[1].equalsIgnoreCase("0")) {
            str1 = str.substring(1);
        } else {
            str1 = str;
        }
        return str1;
    }

    public static boolean isPhoneNumber(String userName) {
        String phoneValidate = "^[0-9]*$";
        boolean check;
        Pattern p;
        Matcher m;
        p = Pattern.compile(phoneValidate);
        m = p.matcher(userName);
        check = m.matches();
        return check;
    }

    public static String cutZero(String str) {
        String num;
        if (isPhoneNumber(str)) {
            num = zeroHead(str);
        } else {
            num = str;
        }
        return num;
    }

    public static boolean checkPhoneNumberValid(String phoneNumber, String mCountryCode) {
        boolean isValid = false;
        if (!TextUtils.isEmpty(phoneNumber)) {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            try {
                Phonenumber.PhoneNumber swissNumberProto = phoneUtil.parse(phoneNumber, mCountryCode);
                isValid = phoneUtil.isValidNumber(swissNumberProto);
                if (!isValid) {
                    return isValid;
                }
            } catch (NumberParseException e) {
                return isValid;
            }
        }
        return isValid;

    }

    public static boolean checkIsYoutubeUrl(String youtubeUrl) {
        String expression = "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
        CharSequence input = youtubeUrl;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    public static String getYoutubeVideoId(String youtubeUrl) {
        String video_id = "";
        String expression = "^.*((youtu.be" + "\\/)" + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*"; // var regExp = /^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/;
        CharSequence input = youtubeUrl;
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            String groupIndex1 = matcher.group(7);
            if (groupIndex1 != null && groupIndex1.length() == 11)
                video_id = groupIndex1;
        }

        return video_id;
    }

    public static String getYoutubeThumbnailUrl(String videoUrl) {
        String imgUrl = "http://img.youtube.com/vi/"+getYoutubeVideoId(videoUrl) + "/0.jpg";
        return imgUrl;
    }


}
