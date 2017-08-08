package com.example.hazem.musicplayer.utils;

import android.text.TextUtils;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Ahmed on 08/09/2015.
 */
public class CredentialValidator {

    public static final Pattern PASS_PATTERN = Pattern.compile("^.{6,}$") ;
    public static final Pattern PHONE_PATTERN = Pattern.compile("^.{8,13}$") ;
    public static final Pattern EMPTY_PATTERN = Pattern.compile("^(?!\\s*$).+");
    public static final Pattern USER_NAME_PATTERN = Pattern.compile("[a-zA-Z\\s]+");

    public static boolean isNameValid(final String username) {
        String regx = "^[\\p{L} .'-]+$";
        Pattern pattern = Pattern.compile(regx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(username);
        return matcher.find() && !TextUtils.isEmpty(username) && username.length() > 10;
    }

    public static boolean isEmpty(EditText editText){
        return TextUtils.isEmpty(editText.getText().toString());
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }


}
