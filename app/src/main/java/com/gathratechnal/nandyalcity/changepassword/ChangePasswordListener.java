package com.gathratechnal.nandyalcity.changepassword;

import com.gathratechnal.nandyalcity.utils.ResponseModel;

/**
 * Created by arunmididoddy on 12/27/2017.
 */

public interface ChangePasswordListener {

     void onPasswordChangePostExecute(ResponseModel responseModel, String message);
}
