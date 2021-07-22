package com.solomonm.yeogiisso.utils;

import android.Manifest;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.drawer.setting.profile.Ui_ChangeProfile_Pic_Activity;
import com.solomonm.yeogiisso.management.store.Ui_StoreManagement_Store_Registration_Activity;
import com.solomonm.yeogiisso.static_init.Static_Method;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadPic {

    static String uploadFilePath = null;
    static String upLoadServerUri = null;

    public static void onLoadingPicture(Context context)
    {
        int GALLERY_INTENT_CALLED = 1;
        int GALLERY_KITKAT_INTENT_CALLED = 1;
        int PERMISSIONS_REQUEST_CODE = 1000;
        String[] PERMISSIONS  = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Static_Method Static_Device_Info;
        Static_Device_Info = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();

        Static_Device_Info.setpermission_storage("false");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //퍼미션 상태 확인
            if (!hasPermissions(PERMISSIONS, context)) {
                //퍼미션 허가 안되어있다면 사용자에게 요청
                Static_Device_Info.setpermission_storage("false");
                ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
            else {
                Static_Device_Info.setpermission_storage("true");
                if (Build.VERSION.SDK_INT < 19){
                    Intent intent = new Intent();
                    //intent.setType("image/jpeg");
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    //startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)),GALLERY_INTENT_CALLED);
                    ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    //intent.setType("image/jpeg");
                    intent.setType("image/*");
                    ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
                }
            }
        }
        else
        {
            Static_Device_Info.setpermission_storage("true");
            if (Build.VERSION.SDK_INT <19){
                Intent intent = new Intent();
                //intent.setType("image/jpeg");
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)),GALLERY_INTENT_CALLED);
                ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);
            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                //intent.setType("image/jpeg");
                intent.setType("image/*");
                ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
            }
        }
    }

    private static boolean hasPermissions(String[] permissions, Context context) {
        int result;

        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (String perms : permissions){
            result = ContextCompat.checkSelfPermission(context, perms);
            if (result == PackageManager.PERMISSION_DENIED){
                //허가 안된 퍼미션 발견
                return false;
            }
        }
        //모든 퍼미션이 허가되었음
        return true;
    }

    public static void loadSomeStreamAsynkTask(Context context, Uri originalUri, ImageView imageView) {
        //Glide.with(MainActivity.Context_MainActivity).load(originalUri).into(iv_profile);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), originalUri);
            // Log.d(TAG, String.valueOf(bitmap));
            imageView.setImageBitmap(bitmap);

            uploadFilePath = originalUri.toString();

            if(Build.VERSION.SDK_INT < 19)
            {
                //URL주소 변환_JellyBean
                getRealPathFromURI_Jellybean(context, originalUri);
            }
            else
            {
                //URL주소 변환
                getRealPathFromURI(context, originalUri);
            }

            //사진 정보 읽어오기
            try
            {
                ExifInterface exif = new ExifInterface(uploadFilePath);
                ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).showExif(exif, uploadFilePath);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                /*
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                        "Ui_ChangeProfile_Activity",
                        "IOException",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0301_profileupload_file_read_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0302_profileupload_file_read_errorcode),
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_change_1MSG0103_change_cancel),
                        "empty",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                        "false"
                );
                 */
            }

        } catch (IOException e) {
            e.printStackTrace();
            /*
            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                    "Ui_ChangeProfile_Activity",
                    "IOException",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0301_profileupload_file_read_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0302_profileupload_file_read_errorcode),
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                    "empty",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                    "false"
            );
             */
        }
    }
    private static String getRealPathFromURI_Jellybean(Context context, Uri contentURI) {
        String filePath;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null)
        {
            filePath = contentURI.getPath();
        }
        else
        {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            filePath = cursor.getString(idx);
            cursor.close();
        }

        uploadFilePath = filePath;
        return filePath;
    }

    //URL주소 변환
    public static String getRealPathFromURI(final Context context, final Uri uri) {

        // DocumentProvider
        if (DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    uploadFilePath = Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                } else {
                    String SDcardpath = getRemovableSDCardPath(context).split("/Android")[0];
                    uploadFilePath = SDcardpath +"/"+ split[1];
                    return SDcardpath +"/"+ split[1];
                }
            }

            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                uploadFilePath = getDataColumn(context, contentUri, null, null);
                return getDataColumn(context, contentUri, null, null);
            }

            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                uploadFilePath = getDataColumn(context, contentUri, selection, selectionArgs);
                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    public static String getRemovableSDCardPath(Context context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return storages[1].toString();
        else
            return "";
    }
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

}
