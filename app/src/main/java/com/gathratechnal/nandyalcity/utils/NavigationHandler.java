package com.gathratechnal.nandyalcity.utils;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import com.gathratechnal.nandyalcity.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NavigationHandler {


    private static NavigationHandler instance = new NavigationHandler();
    public String currentFragmentTag;
    public Fragment currentFragment;
    private ProgressDialog mProgressDialog;

    private NavigationHandler() {

    }

    public static NavigationHandler getInstance() {
        return instance;
    }

    public void navigateToFragment(int containerID, FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, Fragment fragment) {
        if (fragment != null) {
            String backStateName = fragment.getClass().getName();
            String fragmentTag = backStateName;
            if (fragmentManager != null && fragmentTransaction != null) {
                boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
                if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
                    setCurrentFragment(fragment);
                    setCurrentFragmentTag(fragmentTag);
                    fragmentTransaction.replace(containerID, fragment, fragmentTag);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.addToBackStack(backStateName);
                    fragmentTransaction.commit();
                }
            }
        }
    }


    public void navigateToFragment(int containerID, FragmentManager fragmentManager, FragmentTransaction fragmentTransaction, Fragment fragment, Bundle bundle) {
        if (fragment != null) {
            if (bundle != null) {
                fragment.setArguments(bundle);
            }
            String backStateName = fragment.getClass().getName();
            String fragmentTag = backStateName;
            if (fragmentManager != null && fragmentTransaction != null) {
                try {
                    boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
                    if (!fragmentPopped && fragmentManager.findFragmentByTag(fragmentTag) == null) { //fragment not in back stack, create it.
                        setCurrentFragment(fragment);
                        setCurrentFragmentTag(fragmentTag);
                        fragmentTransaction.replace(containerID, fragment, fragmentTag);
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                        fragmentTransaction.addToBackStack(backStateName);
                        fragmentTransaction.commit();
                    }
                } catch (Exception e) {
                    setCurrentFragment(fragment);
                    setCurrentFragmentTag(fragmentTag);
                    fragmentTransaction.replace(containerID, fragment, fragmentTag);
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    fragmentTransaction.addToBackStack(backStateName);
                    fragmentTransaction.commit();
                    // commitAndExecutePendingTrans(fragmentManager, fragmentTransaction);
                }

            }

        }

    }


    public String getCurrentFragmentTag() {
        return currentFragmentTag;
    }

    public void setCurrentFragmentTag(String currentFragmentTag) {
        this.currentFragmentTag = currentFragmentTag;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public void setCurrentFragment(Fragment currentFragment) {
        this.currentFragment = currentFragment;
    }

    public void showDialog(Activity activty, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activty);
        // Get the layout inflater

        builder.setTitle(R.string.app_name).setMessage(msg)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public Bitmap getCapturedImage(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return thumbnail;
    }

    @SuppressWarnings("deprecation")
    public Bitmap getBitMapFromGalleryResult(Activity activity, Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bm;
    }

    public void showProgressDialog(Activity activty) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(activty);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Loading please wait...");
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void selectImage(final Activity activity) {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Take Photo")) {
                    cameraIntent(activity);

                } else if (items[item].equals("Choose from Gallery")) {
                    galleryIntent(activity);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void galleryIntent(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        activity.startActivityForResult(Intent.createChooser(intent, "Select File"), 5432);
    }

    private void cameraIntent(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(intent, 3214);
    }

    public byte[] getBytes(Activity activity, Intent data) throws IOException {
        if (data != null && data.getData() != null) {
            InputStream is = activity.getContentResolver().openInputStream(data.getData());
            ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

            int buffSize = 1024;
            byte[] buff = new byte[buffSize];

            int len = 0;
            while ((len = is.read(buff)) != -1) {
                byteBuff.write(buff, 0, len);
            }

            return byteBuff.toByteArray();
        }
        return null;
    }


}
