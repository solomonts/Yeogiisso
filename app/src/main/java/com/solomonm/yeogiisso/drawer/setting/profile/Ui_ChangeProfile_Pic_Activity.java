package com.solomonm.yeogiisso.drawer.setting.profile;

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
import com.solomonm.yeogiisso.static_init.Static_Method;
import com.solomonm.yeogiisso.utils.Utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Ui_ChangeProfile_Pic_Activity extends AppCompatActivity {
    public static Context Context_Ui_ChangeProfile_Activity;

    private int GALLERY_INTENT_CALLED = 1;
    private int GALLERY_KITKAT_INTENT_CALLED = 1;
    final int PERMISSIONS_REQUEST_CODE = 1000;
    Static_Method Static_Device_Info;

    int serverResponseCode = 0;
    String uploadFilePath = null;
    String upLoadServerUri = null;
    //String[] PERMISSIONS  = {android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String[] PERMISSIONS  = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private LinearLayout layout_draweroption_mainframe, layout_draweroption_innerframe;
    private ImageView iv_changeprofile_profile;
    private Button btn_changeprofile_cancel, btn_changeprofile_ok;

    private void initView()
    {
        this.layout_draweroption_mainframe = (LinearLayout)findViewById(R.id.layout_draweroption_mainframe);
        this.layout_draweroption_innerframe = (LinearLayout)findViewById(R.id.layout_draweroption_innerframe);
        this.iv_changeprofile_profile = (ImageView)findViewById(R.id.iv_changeprofile_profile);
        this.btn_changeprofile_cancel = (Button)findViewById(R.id.btn_changeprofile_cancel);
        this.btn_changeprofile_ok = (Button)findViewById(R.id.btn_changeprofile_ok);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_draweroption_changeprofile_pic_activity);
        Context_Ui_ChangeProfile_Activity = this;

        initView();
        Utils.setStatusBarColor(this, Utils.StatusBarColorType.TRANSPARENT_STATUS_BAR);

        layout_draweroption_mainframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
            }
        });

        layout_draweroption_innerframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        iv_changeprofile_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUploadPicture();
            }
        });

        btn_changeprofile_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
            }
        });

        btn_changeprofile_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_changeprofile_ok.setEnabled(false);

                /*
                //기존 프로필사진이 있는지 체크

                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                        "Ui_ChangeProfile_Activity",
                        "change_profile_alertmsg",
                        "기존의 이미지를 교체하시겠습니까?",
                        "취소",
                        "empty",
                        "예",
                        "false"
                );
                 */

                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_ChangeProfile_Pic_Activity.Context_Ui_ChangeProfile_Activity);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        upload();
                    }
                }, 1000);
            }
            public void upload()
            {
                new Thread(new Runnable() {
                    public void run() {
                        //upLoadServerUri = getString(R.string.server_api_consumer_fileupload);
                        //uploadFile(uploadFilePath);
                    }
                }).start();
            }
        });
    }

    public void onUploadPicture()
    {
        Static_Device_Info = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();

        Static_Device_Info.setpermission_storage("false");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //퍼미션 상태 확인
            if (!hasPermissions(PERMISSIONS)) {
                //퍼미션 허가 안되어있다면 사용자에게 요청
                Static_Device_Info.setpermission_storage("false");
                requestPermissions(PERMISSIONS, PERMISSIONS_REQUEST_CODE);
            }
            else {
                Static_Device_Info.setpermission_storage("true");
                if (Build.VERSION.SDK_INT < 19){
                    Intent intent = new Intent();
                    //intent.setType("image/jpeg");
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    //startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)),GALLERY_INTENT_CALLED);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    //intent.setType("image/jpeg");
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
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
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_INTENT_CALLED);
            } else {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                //intent.setType("image/jpeg");
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_KITKAT_INTENT_CALLED);
            }
        }
    }

    public void loadSomeStreamAsynkTask(Uri originalUri) {
        //Glide.with(MainActivity.Context_MainActivity).load(originalUri).into(iv_profile);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), originalUri);
            // Log.d(TAG, String.valueOf(bitmap));
            ImageView imageView = (ImageView)findViewById(R.id.iv_changeprofile_profile);
            iv_changeprofile_profile.setImageBitmap(bitmap);
            btn_changeprofile_ok.setEnabled(true);

            uploadFilePath = originalUri.toString();

            if(Build.VERSION.SDK_INT < 19)
            {
                getRealPathFromURI_Jellybean(originalUri);
            }
            else
            {
                getRealPathFromURI(Context_Ui_ChangeProfile_Activity, originalUri);
            }

            //사진 정보 읽어오기
            try
            {
                ExifInterface exif = new ExifInterface(uploadFilePath);
                showExif(exif);
            }
            catch (IOException e)
            {
                btn_changeprofile_ok.setEnabled(true);
                e.printStackTrace();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                        "Ui_ChangeProfile_Activity",
                        "IOException",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0301_profileupload_file_read_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0302_profileupload_file_read_errorcode),
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_change_1MSG0103_change_cancel),
                        "empty",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                        "false"
                );
            }

        } catch (IOException e) {
            btn_changeprofile_ok.setEnabled(true);
            e.printStackTrace();
            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                    "Ui_ChangeProfile_Activity",
                    "IOException",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0301_profileupload_file_read_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0302_profileupload_file_read_errorcode),
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                    "empty",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                    "false"
            );
        }
    }

    public int uploadFile(String sourceFileUri) {

        //sourceFileUri = sourceFileUri.substring(sourceFileUri.lastIndexOf("/") +1);
        String fileName = sourceFileUri;

        HttpURLConnection conn = null;
        DataOutputStream dos = null;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";

        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File sourceFile = new File(sourceFileUri);

        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "Source File not exist :"
                    +uploadFilePath);
            btn_changeprofile_ok.setEnabled(true);
            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                    "Ui_ChangeProfile_Activity",
                    "file_not_exist",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0401_profileupload_file_load_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0402_profileupload_file_load_errorcode),
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                    "empty",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                    "false"
            );
            return 0;
        }
        else
        {
            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                URL url = new URL(upLoadServerUri);

                ///
                fileName = fileName.substring(fileName.lastIndexOf("/") +1);
                ///

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", fileName);

                dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {
                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //Toast.makeText(Ui_ChangeProfile_Activity.Context_Ui_ChangeProfile_Activity, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                            btn_changeprofile_ok.setEnabled(true);
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                            finish();
                            overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                    "Ui_ChangeProfile_Activity",
                                    "file_upload_complete",
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_fileupload_success),
                                    "empty",
                                    "empty",
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                    "false"
                            );
                        }
                    });
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        //Toast.makeText(Ui_ChangeProfile_Activity.Context_Ui_ChangeProfile_Activity, "MalformedURLException",Toast.LENGTH_SHORT).show();
                        btn_changeprofile_ok.setEnabled(true);
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                "Ui_ChangeProfile_Activity",
                                "malformed_url_exception",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0501_profileupload_malformed_url_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0502_profileupload_malformed_url_errorcode),
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                "empty",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                "false"
                        );
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        //Toast.makeText(Ui_ChangeProfile_Activity.Context_Ui_ChangeProfile_Activity, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                        btn_changeprofile_ok.setEnabled(true);
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                "Ui_ChangeProfile_Activity",
                                "server_exception",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0601_profileupload_server_connect_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0602_profileupload_server_connect_errorcode),
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                "empty",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                "false"
                        );
                    }
                });
                Log.e("Upload file Exception", "Exception : " + e.getMessage(), e);
            }
            return serverResponseCode;
        } // End else block
    }

    private boolean hasPermissions(String[] permissions) {
        int result;

        //스트링 배열에 있는 퍼미션들의 허가 상태 여부 확인
        for (String perms : permissions){
            result = ContextCompat.checkSelfPermission(this, perms);
            if (result == PackageManager.PERMISSION_DENIED){
                //허가 안된 퍼미션 발견
                return false;
            }
        }
        //모든 퍼미션이 허가되었음
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch(requestCode){
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0) {
                    /*
                    boolean cameraPermissionAccepted = grantResults[0]
                            == PackageManager.PERMISSION_GRANTED;
                     */
                    boolean diskPermissionAccepted = grantResults[0]
                            == PackageManager.PERMISSION_GRANTED;

                    /*
                    if (!cameraPermissionAccepted && !diskPermissionAccepted)
                        Toast.makeText(Ui_ChangeProfile_Activity.this, "퍼미션을 허용해주세요", Toast.LENGTH_SHORT).show();
                     */
                    if (!diskPermissionAccepted)
                        Toast.makeText(Ui_ChangeProfile_Pic_Activity.this, getString(R.string.text_allow_access_storage), Toast.LENGTH_SHORT).show();
                    /*
                    else
                    {
                        Intent mainIntent = new Intent(Main.this, Main.class);
                        startActivity(mainIntent);
                        finish();
                    }
                     */
                }
                break;
        }
    }


    //URL주소 변환
    public String getRealPathFromURI(final Context context, final Uri uri) {

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

    private void showExif(ExifInterface exif) {

        String myAttribute = "[Exif information] : ";

        myAttribute += exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
        myAttribute += " / ";
        myAttribute += exif.getAttribute(ExifInterface.TAG_DATETIME);

        /*
        myAttribute += getTagString(ExifInterface.TAG_DATETIME, exif);
        myAttribute += getTagString(ExifInterface.TAG_FLASH, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LATITUDE_REF, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE, exif);
        myAttribute += getTagString(ExifInterface.TAG_GPS_LONGITUDE_REF, exif);
        myAttribute += getTagString(ExifInterface.TAG_IMAGE_LENGTH, exif);
        myAttribute += getTagString(ExifInterface.TAG_IMAGE_WIDTH, exif);
        myAttribute += getTagString(ExifInterface.TAG_MAKE, exif);
        myAttribute += getTagString(ExifInterface.TAG_MODEL, exif);
        myAttribute += getTagString(ExifInterface.TAG_ORIENTATION, exif);
        myAttribute += getTagString(ExifInterface.TAG_WHITE_BALANCE, exif);
         */

        //tv_uploadimage.setText(myAttribute);
    }

    private String getRealPathFromURI_Jellybean(Uri contentURI) {
        String filePath;
        Cursor cursor = Context_Ui_ChangeProfile_Activity.getContentResolver().query(contentURI, null, null, null, null);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (null == data) return;
        Uri originalUri = null;
        if (requestCode == GALLERY_INTENT_CALLED) {
            originalUri = data.getData();
        } else if (requestCode == GALLERY_KITKAT_INTENT_CALLED) {
            originalUri = data.getData();
            final int takeFlags = data.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            // Check for the freshest data.
            getContentResolver().takePersistableUriPermission(originalUri, takeFlags);
        }
        loadSomeStreamAsynkTask(originalUri);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
    }

    public void setArguments(Bundle args) {
        String result = args.getString("result");
        String parameter_data = args.getString("parameter_data");
        switch (result)
        {
            case "cancel" :
                switch (parameter_data)
                {
                    case "IOException":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_ChangeProfile_Pic_Activity.Context_Ui_ChangeProfile_Activity);
                        Handler handler_IOException = new Handler();
                        handler_IOException.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                iv_changeprofile_profile.callOnClick();
                            }
                        }, 1000);
                        break;
                    case "file_not_exist":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_ChangeProfile_Pic_Activity.Context_Ui_ChangeProfile_Activity);
                        Handler handler_file_not_exist = new Handler();
                        handler_file_not_exist.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                btn_changeprofile_ok.callOnClick();
                            }
                        }, 1000);
                        break;
                    case "malformed_url_exception":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_ChangeProfile_Pic_Activity.Context_Ui_ChangeProfile_Activity);
                        Handler handler_malformed_url_exception  = new Handler();
                        handler_malformed_url_exception.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                btn_changeprofile_ok.callOnClick();
                            }
                        }, 1000);
                        break;
                    case "server_exception":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_ChangeProfile_Pic_Activity.Context_Ui_ChangeProfile_Activity);
                        Handler handler_server_exception   = new Handler();
                        handler_server_exception.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                btn_changeprofile_ok.callOnClick();
                            }
                        }, 1000);
                        break;
                }
                break;
            case "no" :
                break;
            case "ok" :
                if(parameter_data.equals("file_upload_complete"))
                {
                    ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                    //finish();
                    //overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                }
                else
                {
                    ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                }
                break;
        }
    }
}
