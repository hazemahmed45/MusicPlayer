package com.example.hazem.musicplayer.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hazem.musicplayer.Base.MusicPlayerApplication;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class StringUtil {

    private static final String UTF_8 = "UTF-8";

    public static String getStringRes(@StringRes int stringRes) {
        return MusicPlayerApplication.GetInstance().getString(stringRes);
    }
    public static String getStringRes(@StringRes int stringRes, Object... formatArgs) {
        return MusicPlayerApplication.GetInstance().getString(stringRes, formatArgs);
    }

    public static String replaceLink(String src, String link) {
        return src.replace("link", link);
    }

//    public static String [] getStringArrayRes(@StringRes int stringArrayRes)
//    {
//        return HotelsApplication.get().getResources().getStringArray(stringArrayRes);
//    }
    public static boolean isValidEmail(String email) {
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        return m.matches();

    }

    public static boolean isEmpty(String value) {
        return (value == null) || value.trim().equals("");
    }

    public static boolean isValidMobileNumber(String value) {
        if (isEmpty(value)) {
            return false;
        } else {
            if (value.length() == 11) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static boolean isEditTextFieldsEmpty(List<EditText> editTextList) {
        int counter = 0;
        for (int i = 0; i < editTextList.size(); i++) {

            if (StringUtil.isEmpty(editTextList.get(i).getText().toString())) {
                counter++;
            }

            editTextList.get(i).setError(null);
        }
        if (counter > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns a sha1 hash of the passed string
     *
     * @param value the string that should be hashed
     * @return the hashed string
     */

    public static String sha1(String value) {
        String sha1 = "";
        Formatter formatter = new Formatter();
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(value.getBytes(UTF_8));

            for (byte b : crypt.digest()) {
                formatter.format("%02x", b);
            }
            sha1 = formatter.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            formatter.close();
        }
        return sha1;
    }

    public static String getRandomString() {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static final void generateKeyHash(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    "com.amamin",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    public static String getTxtFromView(TextView textView) {
        return textView.getText().toString();
    }

    public static boolean isPriceDefined(String price) {
        if (!isEmpty(price) && !price.equals("0") && !price.equals("0.0") && !price.equals("0.00")) {
            return true;
        } else {
            return false;
        }
    }

    public static String intentToString(Intent intent) {
        if (intent == null) {
            return null;
        }

        return intent.toString() + " " + bundleToString(intent.getExtras());
    }

    public static String bundleToString(Bundle bundle) {
        StringBuilder out = new StringBuilder("Bundle[");

        if (bundle == null) {
            out.append("null");
        } else {
            boolean first = true;
            for (String key : bundle.keySet()) {
                if (!first) {
                    out.append(", ");
                }

                out.append(key).append('=');

                Object value = bundle.get(key);

                if (value instanceof int[]) {
                    out.append(Arrays.toString((int[]) value));
                } else if (value instanceof byte[]) {
                    out.append(Arrays.toString((byte[]) value));
                } else if (value instanceof boolean[]) {
                    out.append(Arrays.toString((boolean[]) value));
                } else if (value instanceof short[]) {
                    out.append(Arrays.toString((short[]) value));
                } else if (value instanceof long[]) {
                    out.append(Arrays.toString((long[]) value));
                } else if (value instanceof float[]) {
                    out.append(Arrays.toString((float[]) value));
                } else if (value instanceof double[]) {
                    out.append(Arrays.toString((double[]) value));
                } else if (value instanceof String[]) {
                    out.append(Arrays.toString((String[]) value));
                } else if (value instanceof CharSequence[]) {
                    out.append(Arrays.toString((CharSequence[]) value));
                } else if (value instanceof Parcelable[]) {
                    out.append(Arrays.toString((Parcelable[]) value));
                } else if (value instanceof Bundle) {
                    out.append(bundleToString((Bundle) value));
                } else {
                    out.append(value);
                }

                first = false;
            }
        }

        out.append("]");
        return out.toString();
    }
}
