package com.gathratechnal.nandyalcity.utils;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NormalizePhoneNumber {

    private PhoneNumberUtil.PhoneNumberType numberType;

    //Remove any special characters from the phone number
    private Pattern mPhonePattern;

    private boolean isNumberValid = false;

    public String normalizePhone(String phone, String countryName) {
        if (phone != null && phone.length() > 0) {
            try {
                PhoneNumberUtil instance = PhoneNumberUtil.getInstance();
                Phonenumber.PhoneNumber number = instance.parse(getOnlyDigits(phone), countryName);

                numberType = instance.getNumberType(number);

                isNumberValid = instance.isValidNumber(number);

                return isNumberValid
                        ? instance.format(number, PhoneNumberUtil.PhoneNumberFormat.E164) : "";

            } catch (NumberParseException e) {
                return phone;
            }
        }
        return "";
    }

    private String getOnlyDigits(String phone) {
        if (mPhonePattern == null) {
            mPhonePattern = Pattern.compile("[^0-9+]");
        }
        if (phone != null) {
            Matcher matcher = mPhonePattern.matcher(phone);
            return matcher.replaceAll("");
        }
        return "";
    }

    public boolean isNumberValid() {
        return isNumberValid;
    }

    public boolean isMobileNumber() {
        return numberType == PhoneNumberUtil.PhoneNumberType.MOBILE
                || numberType == PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE;
    }

    public String normalizeToLocalPhone(String phone, String countryName) {
        try {
            PhoneNumberUtil instance = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber number = instance.parse(phone, countryName);

            numberType = instance.getNumberType(number);

            return instance.isValidNumber(number)
                    ? instance.format(number, PhoneNumberUtil.PhoneNumberFormat.NATIONAL) : "";

        } catch (NumberParseException e) {
            return phone;
        }
    }

}
