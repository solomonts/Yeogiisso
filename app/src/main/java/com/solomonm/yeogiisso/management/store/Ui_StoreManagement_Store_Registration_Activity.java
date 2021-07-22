package com.solomonm.yeogiisso.management.store;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.drawer.setting.profile.Ui_ChangeProfile_Activity;
import com.solomonm.yeogiisso.drawer.setting.profile.Ui_ChangeProfile_Pic_Activity;
import com.solomonm.yeogiisso.service.BackgroundTask_SERVER_Connect;
import com.solomonm.yeogiisso.service.Class_NetworkStatus;
import com.solomonm.yeogiisso.signup.SignupRequest;
import com.solomonm.yeogiisso.signup.Ui_Signup_Dialog;
import com.solomonm.yeogiisso.static_init.Static_Function_Management;
import com.solomonm.yeogiisso.static_init.Static_Method;
import com.solomonm.yeogiisso.static_init.Static_initView_Management;
import com.solomonm.yeogiisso.utils.DevicesUUID;
import com.solomonm.yeogiisso.utils.UploadPic;
import com.solomonm.yeogiisso.utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Time;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Ui_StoreManagement_Store_Registration_Activity extends AppCompatActivity implements OnMapReadyCallback {
    public static Context Context_Ui_StoreManagement_Store_Registration_Activity;
    static Static_initView_Management static_initview_management;
    static Static_Function_Management Static_function_management;
    static Ui_StoreManagement_Activity ui_storeManagement_activity;
    static Static_Method static_method;
    static SharedPreferences pref;

    String store_address_current_location = "";

    //사진업로드
    final int PERMISSIONS_REQUEST_CODE = 1000;
    final int GALLERY_INTENT_CALLED = 1;
    final int GALLERY_KITKAT_INTENT_CALLED = 1;
    static String uploadpic_info_picname = null;
    String upLoadServerUri = null;
    static String uploadfilepath = null;
    int serverResponseCode = 0;

    GoogleMap gMap;
    FragmentManager fm;
    SupportMapFragment map_frag;
    ArrayAdapter storecate_main_adapter, storecate_sub_adapter;

    Geocoder mGeoCoder;
    List<Address> mResultList = null;
    Double lat;
    Double lng;
    Double lat_marker = 0.0;
    Double lng_marker = 0.0;
    String storecate_main = null, storecate_sub = null;

    static String monday="true";
    static String tuesday="true";
    static String wednesday="true";
    static String thursday="true";
    static String friday="true";
    static String saturday="false";
    static String sunday="false";

    String store_name = "", store_dist = "", store_openweek = "", store_opentime = "", store_endtime = "", store_explanation = "";
    Double store_lat = 0.0, store_lng = 0.0;
    int store_number = 0;

    private void initView()
    {
        static_initview_management = new Static_initView_Management();
        Static_function_management = new Static_Function_Management();
        ui_storeManagement_activity = new Ui_StoreManagement_Activity();
        static_method = (Static_Method)Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
        pref = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getSharedPreferences("pref", Context.MODE_PRIVATE);

        static_initview_management.np_store_registration_opentime_h.setMinValue(0);
        static_initview_management.np_store_registration_opentime_h.setMaxValue(23);
        static_initview_management.np_store_registration_opentime_h.setValue(12);
        //static_initview_management.np_store_registration_opentime_h.setWrapSelectorWheel(false);
        static_initview_management.np_store_registration_opentime_h.setWrapSelectorWheel(true);
        static_initview_management.np_store_registration_opentime_h.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        static_initview_management.np_store_registration_opentime_m.setMinValue(0);
        static_initview_management.np_store_registration_opentime_m.setMaxValue(59);
        static_initview_management.np_store_registration_opentime_m.setValue(30);
        //static_initview_management.np_store_registration_opentime_m.setWrapSelectorWheel(false);
        static_initview_management.np_store_registration_opentime_m.setWrapSelectorWheel(true);
        static_initview_management.np_store_registration_opentime_m.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        static_initview_management.np_store_registration_endtime_h.setMinValue(0);
        static_initview_management.np_store_registration_endtime_h.setMaxValue(23);
        static_initview_management.np_store_registration_endtime_h.setValue(12);
        //static_initview_management.np_store_registration_endtime_h.setWrapSelectorWheel(false);
        static_initview_management.np_store_registration_endtime_h.setWrapSelectorWheel(true);
        static_initview_management.np_store_registration_endtime_h.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        static_initview_management.np_store_registration_endtime_m.setMinValue(0);
        static_initview_management.np_store_registration_endtime_m.setMaxValue(59);
        static_initview_management.np_store_registration_endtime_m.setValue(30);
        //static_initview_management.np_store_registration_endtime_m.setWrapSelectorWheel(false);
        static_initview_management.np_store_registration_endtime_m.setWrapSelectorWheel(true);
        static_initview_management.np_store_registration_endtime_m.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_management_store_registration_activity);
        Context_Ui_StoreManagement_Store_Registration_Activity = this;

        initView();

        Intent getintent = getIntent();
        store_address_current_location = getintent.getStringExtra("str_dist");
        static_initview_management.tv_store_registration_store_address.setText(store_address_current_location);

        mGeoCoder = new Geocoder(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity);

        storecate_main_adapter = ArrayAdapter.createFromResource(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, R.array.array_store_registration_storecate_main, R.layout.support_simple_spinner_dropdown_item);
        storecate_main_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        static_initview_management.sp_store_registration_storecate_main.setAdapter(storecate_main_adapter);
        static_initview_management.sp_store_registration_storecate_main.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //선택되면
                switch (position)
                {
                    case 1:
                        storecate_sub_adapter = ArrayAdapter.createFromResource(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, R.array.array_store_registration_storecate_sub_1, R.layout.support_simple_spinner_dropdown_item);
                        storecate_main = static_initview_management.sp_store_registration_storecate_main.getSelectedItem().toString();
                        break;
                    case 2:
                        storecate_sub_adapter = ArrayAdapter.createFromResource(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, R.array.array_store_registration_storecate_sub_2, R.layout.support_simple_spinner_dropdown_item);
                        storecate_main = static_initview_management.sp_store_registration_storecate_main.getSelectedItem().toString();
                        break;
                    case 3:
                        storecate_sub_adapter = ArrayAdapter.createFromResource(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, R.array.array_store_registration_storecate_sub_3, R.layout.support_simple_spinner_dropdown_item);
                        storecate_main = static_initview_management.sp_store_registration_storecate_main.getSelectedItem().toString();
                        break;
                    case 4:
                        storecate_sub_adapter = ArrayAdapter.createFromResource(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, R.array.array_store_registration_storecate_sub_4, R.layout.support_simple_spinner_dropdown_item);
                        storecate_main = static_initview_management.sp_store_registration_storecate_main.getSelectedItem().toString();
                        break;
                    case 5:
                        storecate_sub_adapter = ArrayAdapter.createFromResource(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, R.array.array_store_registration_storecate_sub_5, R.layout.support_simple_spinner_dropdown_item);
                        storecate_main = static_initview_management.sp_store_registration_storecate_main.getSelectedItem().toString();
                        break;
                    default:
                        storecate_sub_adapter = ArrayAdapter.createFromResource(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, R.array.array_store_registration_storecate_sub_0, R.layout.support_simple_spinner_dropdown_item);
                        storecate_main = null;
                        break;
                }
                storecate_sub_adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                static_initview_management.sp_store_registration_storecate_sub.setAdapter(storecate_sub_adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //아무것도 선택되지 않은 상태일 때
            }
        });
        static_initview_management.sp_store_registration_storecate_sub.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(storecate_main != null)
                {
                    storecate_sub = static_initview_management.sp_store_registration_storecate_sub.getSelectedItem().toString();
                }
                else
                {
                    storecate_sub = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //아무것도 선택되지 않은 상태일 때
            }
        });

        static_initview_management.tv_store_registration_store_uploadpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadPic.onLoadingPicture(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity);
            }
        });

        static_initview_management.tv_store_registration_store_address_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                static_initview_management.tv_store_registration_store_address.setText(mResultList.get(0).getAddressLine(0));
                try
                {
                    mResultList = mGeoCoder.getFromLocation(
                            static_method.getlat_gps(), //위도
                            static_method.getlng_gps(), //경도
                            1); //얻어올 값의 개수
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Log.e("ERROR", "입출력 오류 - 서버에서 주소변환시 에러발생");
                }
                if(mResultList != null)
                {
                    if(mResultList.size() == 0)
                    {
                        Toast.makeText(Ui_StoreManagement_Store_Registration_Activity.this, "해당되는 주소 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        static_initview_management.tv_store_registration_store_address_notice.setText("(현재위치)");
                        static_initview_management.tv_store_registration_store_address.setText(mResultList.get(0).getAddressLine(0));
                        static_initview_management.tv_store_registration_store_address_choice_map.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.black));
                        static_initview_management.tv_store_registration_store_address_location.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.main_concept_color));
                    }
                }

                static_initview_management.tv_store_registration_store_address_location.setEnabled(false);
            }
        });

        static_initview_management.tv_store_registration_store_address_choice_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(static_initview_management.layout_store_registration_map_mainframe.getVisibility() == View.GONE)
                {
                    static_initview_management.layout_store_registration_map_mainframe.setVisibility(View.VISIBLE);
                }
                if(static_initview_management.layout_store_registration_map_button_mainframe.getVisibility() == View.GONE)
                {
                    static_initview_management.layout_store_registration_map_button_mainframe.setVisibility(View.VISIBLE);
                }
                if(static_initview_management.button_store_registration_enrollment.getVisibility() == View.VISIBLE)
                {
                    static_initview_management.button_store_registration_enrollment.setVisibility(View.GONE);
                }
                fm = getSupportFragmentManager();
                map_frag =(SupportMapFragment)fm.findFragmentById(R.id.layout_store_registration_map);
                map_frag.getMapAsync(Ui_StoreManagement_Store_Registration_Activity.this);
            }
        });

        static_initview_management.tv_store_registration_change_openweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Static_function_management.Ui_StoreManagement_Store_Registration_OpenWeek_Dialog_Start(monday, tuesday, wednesday, thursday, friday, saturday, sunday);
            }
        });

        static_initview_management.button_store_registration_map_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gMap.clear();

                if(static_initview_management.layout_store_registration_map_mainframe.getVisibility() == View.VISIBLE)
                {
                    static_initview_management.layout_store_registration_map_mainframe.setVisibility(View.GONE);
                }
                if(static_initview_management.layout_store_registration_map_button_mainframe.getVisibility() == View.VISIBLE)
                {
                    static_initview_management.layout_store_registration_map_button_mainframe.setVisibility(View.GONE);
                }
                if(static_initview_management.button_store_registration_enrollment.getVisibility() == View.GONE)
                {
                    static_initview_management.button_store_registration_enrollment.setVisibility(View.VISIBLE);
                }
                static_initview_management.button_store_registration_map_button_ok.setEnabled(false);
            }
        });

        static_initview_management.button_store_registration_map_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gMap.clear();

                if(static_initview_management.layout_store_registration_map_mainframe.getVisibility() == View.VISIBLE)
                {
                    static_initview_management.layout_store_registration_map_mainframe.setVisibility(View.GONE);
                }
                if(static_initview_management.layout_store_registration_map_button_mainframe.getVisibility() == View.VISIBLE)
                {
                    static_initview_management.layout_store_registration_map_button_mainframe.setVisibility(View.GONE);
                }
                if(static_initview_management.button_store_registration_enrollment.getVisibility() == View.GONE)
                {
                    static_initview_management.button_store_registration_enrollment.setVisibility(View.VISIBLE);
                }
                static_initview_management.button_store_registration_map_button_ok.setEnabled(false);

                if(lat_marker > 0 && lng_marker > 0)
                {
                    try
                    {
                        mResultList = mGeoCoder.getFromLocation(
                                lat_marker, //위도
                                lng_marker, //경도
                                1); //얻어올 값의 개수

                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        Log.e("ERROR", "입출력 오류 - 서버에서 주소변환시 에러발생");
                    }
                    if(mResultList != null)
                    {
                        if(mResultList.size() == 0)
                        {
                            Toast.makeText(Ui_StoreManagement_Store_Registration_Activity.this, "해당되는 주소 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            static_initview_management.tv_store_registration_store_address_location.setEnabled(true);

                            static_initview_management.tv_store_registration_store_address_notice.setText("(직접지정)");
                            static_initview_management.tv_store_registration_store_address.setText(mResultList.get(0).getAddressLine(0));
                            static_initview_management.tv_store_registration_store_address_choice_map.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.main_concept_color));
                            static_initview_management.tv_store_registration_store_address_location.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.black));
                        }
                    }
                }
                else
                {
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                                           //잘못된 접근//
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                }
            }
        });

        static_initview_management.button_store_registration_enrollment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //모든 정보 취합

                StringBuffer temp = new StringBuffer();
                Random rnd = new Random();
                for (int i = 0; i < 10; i++) {
                    int rIndex = rnd.nextInt(3);
                    switch (rIndex) {
                        case 0:
                            // a-z
                            temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                            break;
                        case 1:
                            // A-Z
                            temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                            break;
                        case 2:
                            // 0-9
                            temp.append((rnd.nextInt(10)));
                            break;
                    }
                }
                String temp_modify;
                if(temp.toString().length() > 10)
                {
                    temp_modify = temp.toString().substring(0, 10);
                }
                else
                {
                    temp_modify = temp.toString();
                }

                //점포사진 업로드
                //uploadpic_info_picname = uploadpic_info_picname;
                if(uploadpic_info_picname == null || uploadpic_info_picname.equals(""))
                {
                    //uploadpic_info_picname = temp_modify+"_empty-pic.none";
                    uploadpic_info_picname = temp_modify+"_empty.none";
                }
                else
                {
                    //uploadpic_info_picname = temp_modify+"_"+uploadpic_info_picname;
                    uploadpic_info_picname = temp_modify;
                }

                //점포이름
                store_name = static_initview_management.et_store_registration_store_name.getText().toString();

                //점포위치
                store_dist = static_initview_management.tv_store_registration_store_address.getText().toString();
                if(lat_marker <= 0.0 && lng_marker <= 0.0)
                {
                    store_lat = static_method.getlat_gps();
                    store_lng = static_method.getlng_gps();
                }
                else
                {
                    store_lat = lat_marker;
                    store_lng = lng_marker;
                }

                //업종 구분
                //storecate_main = storecate_main;
                //storecate_sub = storecate_sub;

                //영업 요일
                store_openweek = static_initview_management.tv_store_registration_store_openweek.getText().toString();

                //오픈 시간
                store_opentime = static_initview_management.np_store_registration_opentime_h.getValue()+":"+static_initview_management.np_store_registration_opentime_m.getValue();

                //마감 시간
                store_endtime = static_initview_management.np_store_registration_endtime_h.getValue()+":"+static_initview_management.np_store_registration_endtime_m.getValue();

                //대표 번호
                if(static_initview_management.et_store_registration_number.getText().toString() == null || static_initview_management.et_store_registration_number.getText().toString().equals(""))
                {
                    store_number = 0;
                }
                else
                {
                    store_number = Integer.parseInt(static_initview_management.et_store_registration_number.getText().toString());
                }

                //점포 설명
                if(static_initview_management.et_store_registration_store_explanation.getText().toString() == null || static_initview_management.et_store_registration_store_explanation.getText().toString().equals(""))
                {
                    store_explanation = "";
                }
                else
                {
                    store_explanation = static_initview_management.et_store_registration_store_explanation.getText().toString();
                }

                if(store_name.length() <= 0)
                {
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                    // 이름을 입력해 주세요
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                    //////////////////////////////////////////////////////////
                }
                else
                {
                    //점포 이름 검증
                    String store_name_verification = store_name.replaceAll(" ", "");
                    //한글 or 영어 or 숫자가 포함됨
                    if(store_name_verification.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣a-zA-Z0-9]+.*")) {
                        //
                    }
                    else
                    {
                        //////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////
                        // 올바른 이름을 입력해주세요
                        //////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////
                        //////////////////////////////////////////////////////////
                        return;
                    }

                    //대표 번호 검증
                    if(Integer.toString(store_number).matches(".*[0-9].*"))
                    {

                    }
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity != null)
                        {
                            try
                            {
                                JSONObject jsonObject = new JSONObject(response);

                                String success = jsonObject.getString("success");

                                if(success.equals("true"))
                                {






                                    new Thread(new Runnable() {
                                        public void run() {
                                            upLoadServerUri = getString(R.string.server_api_store_registration_upload);
                                            uploadFile(uploadfilepath);
                                        }
                                    }).start();







                                    Toast.makeText(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, "등록 성공", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                    //((Ui_StoreManagement_Activity)Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity).onResume();
                                }
                                else if(success.equals("false"))
                                {
                                    Toast.makeText(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, "등록 실패", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }
                                else
                                {
                                    Toast.makeText(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, "비정상적인 접근", Toast.LENGTH_SHORT).show();
                                    onBackPressed();
                                }

                            }
                            catch (Exception e)
                            {
                                e.printStackTrace();
                                //Toast.makeText(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, "서버 에러", Toast.LENGTH_SHORT).show();
                                //onBackPressed();
                            }
                        }
                        else
                        {
                            //
                        }
                    }
                };
                Ui_StoreManagement_Store_Registration_Request ui_storemanagement_store_registration_request = new Ui_StoreManagement_Store_Registration_Request(static_method.getUserInfo_user_id(), store_name, uploadpic_info_picname, store_openweek, store_opentime, store_endtime, storecate_main, storecate_sub, store_number, store_explanation, store_dist, store_lat, store_lng,  responseListener);
                RequestQueue queue = Volley.newRequestQueue(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity);
                queue.add(ui_storemanagement_store_registration_request);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
        Context_Ui_StoreManagement_Store_Registration_Activity = null;
        monday="true";
        tuesday="true";
        wednesday="true";
        thursday="true";
        friday="true";
        saturday="false";
        sunday="false";
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
                        Toast.makeText(Ui_StoreManagement_Store_Registration_Activity.this, getString(R.string.text_allow_access_storage), Toast.LENGTH_SHORT).show();
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
        UploadPic.loadSomeStreamAsynkTask(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity, originalUri, static_initview_management.iv_store_registration_store_pic);
    }

    public static void showExif(ExifInterface exif, String uploadFilePath) {

        String uploadfile_name[] = uploadFilePath.split("/");
        for(int i=0; i<uploadfile_name.length; i++)
        {
            uploadpic_info_picname = uploadfile_name[i];
            uploadpic_info_picname = uploadfile_name[i];
        }

        Static_Method Static_Device_Info;
        Static_Device_Info = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
        Static_Device_Info.settest(uploadFilePath);

        uploadfilepath = uploadFilePath;

        /*
        String myAttribute = "[Exif information] : ";

        myAttribute += exif.getAttribute(ExifInterface.TAG_IMAGE_LENGTH);
        myAttribute += " / ";
        myAttribute += exif.getAttribute(ExifInterface.TAG_DATETIME);
         */

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
                    +uploadfilepath);
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
                            Toast.makeText(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity, "성공", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity, "실패", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity, "에러1", Toast.LENGTH_SHORT).show();
                    }
                });

                Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity, "에러2", Toast.LENGTH_SHORT).show();
                    }
                });
                Log.e("Upload file Exception", "Exception : " + e.getMessage(), e);
            }
            return serverResponseCode;
        } // End else block
    }

    public void onFinish()
    {
        finish();
        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
        Context_Ui_StoreManagement_Store_Registration_Activity = null;
        monday="true";
        tuesday="true";
        wednesday="true";
        thursday="true";
        friday="true";
        saturday="false";
        sunday="false";
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;

        gMap.getUiSettings().setMyLocationButtonEnabled(false);
        gMap.getUiSettings().setMapToolbarEnabled(false);
        gMap.getUiSettings().setCompassEnabled(false);
        gMap.getUiSettings().setZoomControlsEnabled(false);
        gMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        gMap.getUiSettings().setAllGesturesEnabled(true);
        gMap.getUiSettings().setRotateGesturesEnabled(true);
        gMap.getUiSettings().setScrollGesturesEnabled(true);
        gMap.getUiSettings().setTiltGesturesEnabled(true);
        //gMap.getUiSettings().setZoomGesturesEnabled(false);

        MapsInitializer.initialize(this);
        switch (pref.getString("AppDefaultSetting_Map_Mode", "MAP_TYPE_NORMAL"))
        {
            case "MAP_TYPE_NORMAL":
                if(gMap.getMapType() != GoogleMap.MAP_TYPE_NORMAL)
                {
                    gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                break;
            case "MAP_TYPE_TERRAIN":
                if(gMap.getMapType() != GoogleMap.MAP_TYPE_TERRAIN)
                {
                    gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
                break;
            case "MAP_TYPE_HYBRID":
                if(gMap.getMapType() != GoogleMap.MAP_TYPE_HYBRID)
                {
                    gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
                break;
        }

        lat = static_method.getlat_gps();
        lng = static_method.getlng_gps();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), pref.getFloat("AppDefaultSetting_Map_Zoom_Level", 14.f));
        googleMap.animateCamera(cameraUpdate);

        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng point) {
                /*
                Point screentPt = gMap.getProjection().toScreenLocation(point); //현재 위도와 경도에서 화면 포인트를 알려준다
                LatLng latLng = gMap.getProjection().fromScreenLocation(screentPt); //현재 화면에 찍힌 포인트로부터 위도와 경도를 알려준다

                Toast.makeText(Ui_StoreManagement_Store_Registration_Activity.this, "맵좌표 : 좌표 - 위도"+String.valueOf(point.latitude)+"경도"+String.valueOf(point.longitude), Toast.LENGTH_SHORT).show();
                Toast.makeText(Ui_StoreManagement_Store_Registration_Activity.this, "화면좌표 : 좌표 - X"+String.valueOf(screentPt.x)+"경도"+String.valueOf(screentPt.y), Toast.LENGTH_SHORT).show();

                Log.e("AAAAAAAAAAAAAA : ", "맵좌표 : 좌표 - 위도"+String.valueOf(point.latitude)+"경도"+String.valueOf(point.longitude));
                Log.e("BBBBBBBBBBBBBB : ", "화면좌표 : 좌표 - X"+String.valueOf(screentPt.x)+"경도"+String.valueOf(screentPt.y));
                 */

                gMap.clear();
                lat_marker = 0.0;
                lng_marker = 0.0;
                static_initview_management.button_store_registration_map_button_ok.setEnabled(true);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.title("찍은 좌표");
                lat_marker = point.latitude; //위도
                lng_marker = point.longitude; //경도
                markerOptions.position(new LatLng(lat_marker, lng_marker));
                gMap.addMarker(markerOptions);
            }
        });

    }

    public static void setArguments(Bundle args) {
        monday = args.getString("monday");
        tuesday = args.getString("tuesday");
        wednesday = args.getString("wednesday");
        thursday = args.getString("thursday");
        friday = args.getString("friday");
        saturday = args.getString("saturday");
        sunday = args.getString("sunday");

        static_initview_management.tv_store_registration_store_openweek.setText("");
        if(monday.equals("true")){ static_initview_management.tv_store_registration_store_openweek.setText(static_initview_management.tv_store_registration_store_openweek.getText().toString()+"월"); }
        if(tuesday.equals("true")){ static_initview_management.tv_store_registration_store_openweek.setText(static_initview_management.tv_store_registration_store_openweek.getText().toString()+"화"); }
        if(wednesday.equals("true")){ static_initview_management.tv_store_registration_store_openweek.setText(static_initview_management.tv_store_registration_store_openweek.getText().toString()+"수"); }
        if(thursday.equals("true")){ static_initview_management.tv_store_registration_store_openweek.setText(static_initview_management.tv_store_registration_store_openweek.getText().toString()+"목"); }
        if(friday.equals("true")){ static_initview_management.tv_store_registration_store_openweek.setText(static_initview_management.tv_store_registration_store_openweek.getText().toString()+"금"); }
        if(saturday.equals("true")){ static_initview_management.tv_store_registration_store_openweek.setText(static_initview_management.tv_store_registration_store_openweek.getText().toString()+"토"); }
        if(sunday.equals("true")){ static_initview_management.tv_store_registration_store_openweek.setText(static_initview_management.tv_store_registration_store_openweek.getText().toString()+"일"); }
    }
}
