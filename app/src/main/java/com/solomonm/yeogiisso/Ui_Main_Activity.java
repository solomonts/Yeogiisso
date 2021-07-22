package com.solomonm.yeogiisso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.kakao.sdk.user.UserApiClient;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.drawer.option.search.Ui_DrawerOption_Search_Dialog;
import com.solomonm.yeogiisso.drawer.option.search.Ui_DrawerOption_Search_Dialog_LocalSearch_List;
import com.solomonm.yeogiisso.drawer.setting.certification.Ui_DrawerSetting_Certification_Activity;
import com.solomonm.yeogiisso.drawer.setting.profile.Ui_ChangeProfile_Activity;
import com.solomonm.yeogiisso.loading.Ui_FullScreenTutorial_Activity;
import com.solomonm.yeogiisso.loading.Ui_Splash_Activity;
import com.solomonm.yeogiisso.login.Ui_Login_Activity;
import com.solomonm.yeogiisso.management.store.Ui_StoreManagement_Activity;
import com.solomonm.yeogiisso.map.nearbylist.NearbyMarket_List;
import com.solomonm.yeogiisso.map.nearbylist.NearbyMarket_ListAdapter;
import com.solomonm.yeogiisso.service.BackgroundTask_GPS_Connect;
import com.solomonm.yeogiisso.service.BackgroundTask_SERVER_Connect;
import com.solomonm.yeogiisso.service.Service_MarkerDetail_MiniMap;
import com.solomonm.yeogiisso.static_init.Static_Function;
import com.solomonm.yeogiisso.static_init.Static_Method;
import com.solomonm.yeogiisso.static_init.Static_initView;
import com.solomonm.yeogiisso.utils.DirectionsJSONParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Ui_Main_Activity extends AppCompatActivity implements OnMapReadyCallback {

    public static Context Context_UiMainActivity;
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 0;
    private int GALLERY_INTENT_CALLED = 1;
    private int GALLERY_KITKAT_INTENT_CALLED = 1;
    public Static_initView initView;
    public Static_Method initMethod;
    public Static_Function initFunction;
    Static_Method Static_Device_Info, Static_User_Info, Static_Search_Detail, Static_Map_Setting;

    String target = "";
    public GoogleMap gMap;
    FragmentManager fm;
    SupportMapFragment map_frag;
    private Polyline mPolyline;
    String onResumeAction = "";

    private NearbyMarket_ListAdapter nearbymarket_listadapter;
    private List<NearbyMarket_List> nearbymarket_userList;
    private List<NearbyMarket_List> nearbymarket_saveList;
    String result_nearbymarketmarker = null;

    //체크할 권한 배열
    String[] permission_list = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };
    // 현재 사용자 위치
    Location myLocation;
    // 위치 정보를 관리하는 매니저
    LocationManager manager;

    //지도 반경 원
    CircleOptions MapCircle;
    Circle circle;

    //attr 색상 관리
    int text_attrColor_black_softgray;

    //검색어 입력여부 확인
    int searchbox_length=0;
    String searchbox_contents="";

    public void init() {
        initView = new Static_initView();
        initMethod = new Static_Method();
        initFunction = new Static_Function();
        initFunction.pref = ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getSharedPreferences("pref", Context.MODE_PRIVATE);
        Static_Device_Info = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
        Static_User_Info = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
        Static_Search_Detail = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
        Static_Map_Setting = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context_UiMainActivity = this;
        //초기실행언어/초기실행테마/초기디바이스크기 설정
        Static_Function initFunction_setAppInitialRun = new Static_Function();
        initFunction_setAppInitialRun.AppInitialRun_Language();
        initFunction_setAppInitialRun.AppInitialRun_Theme();
        initFunction_setAppInitialRun.AppInitialRun_DeviceInfo();
        initFunction_setAppInitialRun.AppInitialRun_MapSetting();
        //레이아웃 설정
        setContentView(R.layout.ui_main_activity);

        Intent startLoadingActivity = new Intent(Ui_Main_Activity.this, Ui_Splash_Activity.class);
        startActivity(startLoadingActivity);

        init();
        initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity); //로딩화면 시작

        //UI순서 설정
        setSupportActionBar(initView.toolbar);
        initView.layout_main_mainwait.bringToFront();
        initView.layout_main.bringToFront();
        initView.drawer_option.bringToFront();
        initView.drawer_setting.bringToFront();

        //구글 맵 객체를 추출
        fm = getSupportFragmentManager();
        map_frag = (SupportMapFragment) fm.findFragmentById(R.id.layout_main_mapfrag);
        map_frag.getMapAsync(this);

        //attr 색상 관리
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = Ui_Main_Activity.Context_UiMainActivity.getTheme();
        theme.resolveAttribute(R.attr.color_common_black_softgray, typedValue, true);
        TypedArray arr =
                Ui_Main_Activity.Context_UiMainActivity.obtainStyledAttributes(typedValue.data, new int[]{
                        R.attr.color_common_black_softgray
                });
        text_attrColor_black_softgray = arr.getColor(0, -1);

        //인터넷연결상태 및 서버연결상태 확인
        Ui_Main_Activity.Context_UiMainActivity.startService(new Intent(Ui_Main_Activity.Context_UiMainActivity, BackgroundTask_SERVER_Connect.class));
        //GPS권한체크 및 GPS수신
        checkPermission("getMyLocation");

        initView.cardview_btn_drawer_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_drawer_main.openDrawer(initView.drawer_option);
            }
        });

        initView.iv_btn_drawer_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_drawer_main.closeDrawers();
                initView.layout_drawer_main.openDrawer(initView.drawer_setting);
            }
        });

        initView.iv_btn_drawer_option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_drawer_main.closeDrawers();
                initView.layout_drawer_main.openDrawer(initView.drawer_option);
            }
        });

        initView.iv_btn_drawer_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_drawer_main.closeDrawers();
            }
        });

        initView.layout_btn_start_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchbox_length = initView.et_search_contents.getText().toString().length();
                searchbox_contents = initView.et_search_contents.getText().toString();
                onResumeAction = "searchmap_searchstore";
                onResume();
            }
        });

        initView.tv_drawersetting_changelanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFunction.Ui_DrawerSetting_Language_Dialog_Start();
            }
        });

        initView.tv_drawersetting_changetheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFunction.Ui_DrawerSetting_Theme_Dialog_Start();
            }
        });

        initView.cardview_btn_call_bottommenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //0.5초간 버튼클릭 방지
                initFunction.onFreeze(null, null, null, null, initView.cardview_btn_call_bottommenu, 500);

                if (initView.layout_bottomlayout_bottommenuframe.getVisibility() == View.INVISIBLE) {
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_right_to_left);
                    initView.layout_bottomlayout_bottommenuframe.startAnimation(animSlide);
                    initView.layout_bottomlayout_bottommenuframe.setVisibility(View.VISIBLE);
                    initView.iv_btn_call_bottommenu.setBackgroundResource(R.drawable.ic_arrow_right);

                    if (initView.layout_bottomlayout_mainframe.getHeight() >= initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Height", 0) - initView.toolbar.getHeight() - 60) {
                        initView.layout_bottomlayout_mainframe.setLayoutParams(new LinearLayout.LayoutParams(initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0), (int) initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Height", 0) - initView.toolbar.getHeight() - 60));
                    }
                } else {
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_left_to_right);
                    initView.layout_bottomlayout_bottommenuframe.startAnimation(animSlide);
                    initView.layout_bottomlayout_bottommenuframe.setVisibility(View.INVISIBLE);
                    initView.iv_btn_call_bottommenu.setBackgroundResource(R.drawable.ic_dot_menu);

                    if (initView.cardview_btn_bottommenu1_setmylocation.getLayoutParams().width >= initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0) / 6) {
                        initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu1_setmylocation);
                    }
                    if (initView.cardview_btn_bottommenu2_searchmap_mapposition.getLayoutParams().width >= initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0) / 6) {
                        initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu2_searchmap_mapposition);
                    }
                    if (initView.cardview_btn_bottommenu3_realtime_gps.getLayoutParams().width >= initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0) / 6) {
                        initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu3_realtime_gps);
                    }

                }
            }
        });

        initView.cardview_btn_bottommenu1_setmylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //0.5초간 버튼클릭 방지
                //initFunction.onFreeze(null, null, null, null, initView.cardview_btn_bottommenu1_setmylocation, 500);
                //initFunction.onFreeze(null, null, null, null, initView.cardview_btn_bottommenu2_searchmap_mapposition, 500);
                //initFunction.onFreeze(null, null, null, null, initView.cardview_btn_bottommenu3_realtime_gps, 500);

                if (initView.cardview_btn_bottommenu1_setmylocation.getLayoutParams().width < initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0) / 6) {
                    initFunction.onChangeBottomMenuSize("scaleUp", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu1_setmylocation);
                } else {
                    if (initView.tv_btn_bottommenu1_setmylocation.getVisibility() == View.VISIBLE && initView.iv_btn_bottommenu1_setmylocation.getVisibility() == View.GONE) {
                        //GPS off일 경우
                        if (initFunction.pref.getString("AppDefaultSetting_Permission_GPS", "false").equals("false") || Static_Device_Info.getcheckfunction_gps().equals("false")) {
                            initFunction.Ui_AlertDialog_Dialog_Start(
                                    "Ui_Main_Activity",
                                    "resetting_gps",
                                    ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_gps_connect),
                                    ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                    "empty",
                                    ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alertokbtn_gps_connect),
                                    "false"
                            );
                        } else {
                            // 위도 경도를 관리하는 객체를 생성
                            LatLng position = new LatLng(Static_Device_Info.getlat_gps(), Static_Device_Info.getlng_gps());

                            // 현재 위치를 설정한다.
                            CameraUpdate update1 = CameraUpdateFactory.newLatLng(position);
                            gMap.moveCamera(update1);

                            // 확대
                            CameraUpdate update2 = CameraUpdateFactory.zoomTo(initFunction.pref.getFloat("AppDefaultSetting_Map_Zoom_Level", 14.f));
                            gMap.animateCamera(update2);
                        }
                    }
                    initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu1_setmylocation);
                }
            }
        });

        initView.cardview_btn_bottommenu2_searchmap_mapposition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //0.5초간 버튼클릭 방지
                //initFunction.onFreeze(null, null, null, null, initView.cardview_btn_bottommenu1_setmylocation, 500);
                //initFunction.onFreeze(null, null, null, null, initView.cardview_btn_bottommenu2_searchmap_mapposition, 500);
                //initFunction.onFreeze(null, null, null, null, initView.cardview_btn_bottommenu3_realtime_gps, 500);

                if (initView.cardview_btn_bottommenu2_searchmap_mapposition.getLayoutParams().width < initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0) / 6) {
                    initFunction.onChangeBottomMenuSize("scaleUp", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu2_searchmap_mapposition);
                } else {
                    if (initView.tv_btn_bottommenu2_searchmap_mapposition.getVisibility() == View.VISIBLE && initView.iv_btn_bottommenu2_searchmap_mapposition.getVisibility() == View.GONE) {
                        //GPS off일 경우
                        if (initFunction.pref.getString("AppDefaultSetting_Permission_GPS", "false").equals("false") || Static_Device_Info.getcheckfunction_gps().equals("false")) {
                            initFunction.Ui_AlertDialog_Dialog_Start(
                                    "Ui_Main_Activity",
                                    "resetting_gps",
                                    ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_gps_connect),
                                    ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                    "empty",
                                    ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alertokbtn_gps_connect),
                                    "false"
                            );
                        } else {
                            gMap.clear();
                            Call_BackgroundTask_NearbyMarketMarker("searchmap_mapposition");
                            // 반경 원 (내부 배경색 없음) (현재 지도화면 중앙위치 기준으로 생성)
                            LatLng position = new LatLng(Static_Device_Info.getlat_mapcenter(), Static_Device_Info.getlng_mapcenter());
                            MapCircle = new CircleOptions().center(position) //원점
                                    .radius(initFunction.pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000))      //반지름 단위 : m
                                    .strokeWidth(1.5f)
                                    .strokeColor(Color.parseColor("#FF0000"));
                            //.fillColor(Color.argb(20, 51, 51, 51));
                            if (circle != null) {
                                circle.remove();
                            }
                            circle = gMap.addCircle(MapCircle);
                        }
                    }
                    initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu2_searchmap_mapposition);
                    //target = "";
                }
            }
        });

        initView.cardview_btn_bottommenu3_realtime_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //0.5초간 버튼클릭 방지
                //initFunction.onFreeze(null, null, null, null, initView.cardview_btn_bottommenu1_setmylocation, 500);
                //initFunction.onFreeze(null, null, null, null, initView.cardview_btn_bottommenu2_searchmap_mapposition, 500);
                //initFunction.onFreeze(null, null, null, null, initView.cardview_btn_bottommenu3_realtime_gps, 500);

                if (initView.cardview_btn_bottommenu3_realtime_gps.getLayoutParams().width < initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0) / 6) {
                    initFunction.onChangeBottomMenuSize("scaleUp", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu3_realtime_gps);
                } else {
                    if (initView.tv_btn_bottommenu3_realtime_gps.getVisibility() == View.VISIBLE && initView.iv_btn_bottommenu3_realtime_gps.getBackground().getConstantState().equals(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_off).getConstantState())) {
                        initFunction.Ui_AlertDialog_Dialog_Start(
                                "Ui_Main_Activity",
                                "gps_turn_on",
                                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_bottommenu_alerttitle_realtime_gps_turn_on),
                                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_no),
                                "empty",
                                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_yes),
                                "false"
                        );
                    } else if (initView.tv_btn_bottommenu3_realtime_gps.getVisibility() == View.VISIBLE && initView.iv_btn_bottommenu3_realtime_gps.getBackground().getConstantState().equals(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_on).getConstantState())) {
                        initFunction.Ui_AlertDialog_Dialog_Start(
                                "Ui_Main_Activity",
                                "gps_turn_off",
                                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_bottommenu_alerttitle_realtime_gps_turn_off),
                                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_no),
                                "empty",
                                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_yes),
                                "false"
                        );
                    }
                }
            }
        });

        initView.tv_draweroption_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Static_User_Info.getUserInfo_user_id() == null) {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                            Intent startUiLoginAcitvity = new Intent(Ui_Main_Activity.this, Ui_Login_Activity.class);
                            startActivity(startUiLoginAcitvity);
                            overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                        }
                    }, 1000);

                    //기존 자체서비스 호출
                    //initFunction.Ui_Login_Dialog_Start();
                }
            }
        });

        initView.tv_draweroption_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFunction.Ui_AlertDialog_Dialog_Start(
                        "Ui_Main_Activity",
                        "logout",
                        ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_logout),
                        ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_no),
                        "empty",
                        ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_yes),
                        "false"
                );
            }
        });

        /*
        initView.tv_drawersetting_changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Static_User_Info.getUserInfo_user_id() != null) {
                    Intent startChangeProfileActivity = new Intent(Ui_Main_Activity.this, Ui_ChangeProfile_Activity.class);
                    startActivity(startChangeProfileActivity);
                    overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                } else {
                    initView.tv_drawersetting_changeprofile.setClickable(false);
                    Toast.makeText(Ui_Main_Activity.this, getString(R.string.text_drawer_login_title), Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initView.tv_drawersetting_changeprofile.setClickable(true);
                        }
                    }, 2000);

                }
            }
        });
         */
        initView.tv_drawersetting_certification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Static_User_Info.getUserInfo_user_id() != null) {
                    Intent startCertificationActivity = new Intent(Ui_Main_Activity.this, Ui_DrawerSetting_Certification_Activity.class);
                    startActivity(startCertificationActivity);
                    overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                } else {
                    initView.tv_drawersetting_certification.setClickable(false);
                    Toast.makeText(Ui_Main_Activity.this, getString(R.string.text_drawer_login_title), Toast.LENGTH_SHORT).show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initView.tv_drawersetting_certification.setClickable(true);
                        }
                    }, 2000);

                }
            }
        });

        initView.layout_draweroption_searchmenu_call_mainframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_draweroption_searchmenu_call_mainframe.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView.layout_draweroption_searchmenu_call_mainframe.setClickable(true);
                    }
                }, 500);
                if (initView.layout_draweroption_searchmenu_mainframe.getVisibility() == View.VISIBLE) {
                    initView.btn_draweroption_search_ok.setVisibility(View.GONE);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_down_to_right);
                    initView.iv_draweroption_searchmenu_call_mainframe.startAnimation(animSlide);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_down_to_up);
                    initView.layout_draweroption_searchmenu_mainframe.startAnimation(animSlide_mainframe);
                    Handler handler_mainframe = new Handler();
                    handler_mainframe.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initView.layout_draweroption_searchmenu_mainframe.setVisibility(View.GONE);
                        }
                    }, 300);
                } else if (initView.layout_draweroption_searchmenu_mainframe.getVisibility() == View.GONE) {
                    initView.btn_draweroption_search_ok.setVisibility(View.VISIBLE);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_down);
                    initView.iv_draweroption_searchmenu_call_mainframe.startAnimation(animSlide);
                    initView.layout_draweroption_searchmenu_mainframe.setVisibility(View.VISIBLE);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_up_to_down);
                    initView.layout_draweroption_searchmenu_mainframe.startAnimation(animSlide_mainframe);
                }
            }
        });

        initView.layout_draweroption_mapsetting_call_mainframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_draweroption_mapsetting_call_mainframe.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView.layout_draweroption_mapsetting_call_mainframe.setClickable(true);
                    }
                }, 500);
                if (initView.layout_draweroption_mapsetting_mainframe.getVisibility() == View.VISIBLE) {
                    initView.btn_draweroption_change_ok.setVisibility(View.GONE);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_down_to_right);
                    initView.iv_draweroption_mapsetting_call_mainframe.startAnimation(animSlide);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_down_to_up);
                    initView.layout_draweroption_mapsetting_mainframe.startAnimation(animSlide_mainframe);
                    Handler handler_mainframe = new Handler();
                    handler_mainframe.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initView.layout_draweroption_mapsetting_mainframe.setVisibility(View.GONE);
                        }
                    }, 300);
                } else if (initView.layout_draweroption_mapsetting_mainframe.getVisibility() == View.GONE) {
                    initView.btn_draweroption_change_ok.setVisibility(View.VISIBLE);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_down);
                    initView.iv_draweroption_mapsetting_call_mainframe.startAnimation(animSlide);
                    initView.layout_draweroption_mapsetting_mainframe.setVisibility(View.VISIBLE);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_up_to_down);
                    initView.layout_draweroption_mapsetting_mainframe.startAnimation(animSlide_mainframe);
                }
            }
        });

        initView.cardview_btn_call_searchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.cardview_btn_call_searchbox.setEnabled(false);
                initView.cardview_layout_searchbox.setVisibility(View.VISIBLE);
                if (initView.iv_btn_search.getBackground().getConstantState().equals(ContextCompat.getDrawable(Ui_Main_Activity.this, R.drawable.ic_search).getConstantState())) {
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_right_to_left);
                    initView.cardview_layout_searchbox.startAnimation(animSlide);
                    initView.iv_btn_search.setBackgroundResource(R.drawable.ic_close);
                    if(initView.tv_btn_start_search_alert.getVisibility() == View.VISIBLE)
                    {
                        initView.tv_btn_start_search_alert.setVisibility(View.GONE);
                        initView.et_search_contents.setText(searchbox_contents);
                    }
                } else if (initView.iv_btn_search.getBackground().getConstantState().equals(ContextCompat.getDrawable(Ui_Main_Activity.this, R.drawable.ic_close).getConstantState())) {
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_left_to_right);
                    initView.cardview_layout_searchbox.startAnimation(animSlide);
                    initView.iv_btn_search.setBackgroundResource(R.drawable.ic_search);
                    if(searchbox_length > 0)
                    {
                        if(initView.tv_btn_start_search_alert.getVisibility() == View.GONE)
                        {
                            initView.tv_btn_start_search_alert.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        if(initView.tv_btn_start_search_alert.getVisibility() == View.VISIBLE)
                        {
                            initView.tv_btn_start_search_alert.setVisibility(View.GONE);
                        }
                    }
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView.cardview_btn_call_searchbox.setEnabled(true);
                    }
                }, 500);
            }
        });

        initView.layout_draweroption_searchmenu_area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_draweroption_searchmenu_area.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView.layout_draweroption_searchmenu_area.setClickable(true);
                    }
                }, 500);
                if (initView.layout_draweroption_searchmenu_area_mainframe.getVisibility() == View.VISIBLE) {
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_down_to_right);
                    initView.iv_draweroption_searchmenu_area.startAnimation(animSlide);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_down_to_up);
                    initView.layout_draweroption_searchmenu_area_mainframe.startAnimation(animSlide_mainframe);
                    Handler handler_mainframe = new Handler();
                    handler_mainframe.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initView.layout_draweroption_searchmenu_area_mainframe.setVisibility(View.GONE);
                        }
                    }, 300);
                } else if (initView.layout_draweroption_searchmenu_area_mainframe.getVisibility() == View.GONE) {
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_down);
                    initView.iv_draweroption_searchmenu_area.startAnimation(animSlide);
                    initView.layout_draweroption_searchmenu_area_mainframe.setVisibility(View.VISIBLE);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_up_to_down);
                    initView.layout_draweroption_searchmenu_area_mainframe.startAnimation(animSlide_mainframe);
                }
            }
        });

        initView.layout_draweroption_mapsetting_dist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_draweroption_mapsetting_dist.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView.layout_draweroption_mapsetting_dist.setClickable(true);
                    }
                }, 500);
                if (initView.layout_draweroption_mapsetting_dist_mainframe.getVisibility() == View.VISIBLE) {
                    initView.seekbar_draweroption_mapsetting_dist.setEnabled(false); //스키바 비활성화
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_down_to_right);
                    initView.iv_draweroption_mapsetting_dist.startAnimation(animSlide);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_down_to_up);
                    initView.layout_draweroption_mapsetting_dist_mainframe.startAnimation(animSlide_mainframe);
                    Handler handler_mainframe = new Handler();
                    handler_mainframe.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initView.layout_draweroption_mapsetting_dist_mainframe.setVisibility(View.GONE);
                        }
                    }, 300);
                } else if (initView.layout_draweroption_mapsetting_dist_mainframe.getVisibility() == View.GONE) {
                    initView.seekbar_draweroption_mapsetting_dist.setEnabled(true); //스키바 활성화
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_down);
                    initView.iv_draweroption_mapsetting_dist.startAnimation(animSlide);
                    initView.layout_draweroption_mapsetting_dist_mainframe.setVisibility(View.VISIBLE);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_up_to_down);
                    initView.layout_draweroption_mapsetting_dist_mainframe.startAnimation(animSlide_mainframe);
                }
            }
        });

        initView.layout_draweroption_searchmenu_price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_draweroption_searchmenu_price.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView.layout_draweroption_searchmenu_price.setClickable(true);
                    }
                }, 500);
                if (initView.layout_draweroption_searchmenu_price_mainframe.getVisibility() == View.VISIBLE) {
                    initView.et_draweroption_searchmenu_price_set_stprice.setEnabled(false);
                    initView.et_draweroption_searchmenu_price_set_edprice.setEnabled(false);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_down_to_right);
                    initView.iv_draweroption_searchmenu_price.startAnimation(animSlide);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_down_to_up);
                    initView.layout_draweroption_searchmenu_price_mainframe.startAnimation(animSlide_mainframe);
                    Handler handler_mainframe = new Handler();
                    handler_mainframe.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initView.layout_draweroption_searchmenu_price_mainframe.setVisibility(View.GONE);
                        }
                    }, 300);
                } else if (initView.layout_draweroption_searchmenu_price_mainframe.getVisibility() == View.GONE) {
                    initView.et_draweroption_searchmenu_price_set_stprice.setEnabled(true);
                    initView.et_draweroption_searchmenu_price_set_edprice.setEnabled(true);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_down);
                    initView.iv_draweroption_searchmenu_price.startAnimation(animSlide);
                    initView.layout_draweroption_searchmenu_price_mainframe.setVisibility(View.VISIBLE);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_up_to_down);
                    initView.layout_draweroption_searchmenu_price_mainframe.startAnimation(animSlide_mainframe);
                }
            }
        });

        initView.layout_draweroption_searchmenu_cate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_draweroption_searchmenu_cate.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView.layout_draweroption_searchmenu_cate.setClickable(true);
                    }
                }, 500);
                if (initView.layout_draweroption_searchmenu_cate_mainframe.getVisibility() == View.VISIBLE) {
                    initView.sp_draweroption_searchmenu_cate_main.setClickable(false);
                    initView.sp_draweroption_searchmenu_cate_middle.setClickable(false);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_down_to_right);
                    initView.iv_draweroption_searchmenu_cate.startAnimation(animSlide);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_down_to_up);
                    initView.layout_draweroption_searchmenu_cate_mainframe.startAnimation(animSlide_mainframe);
                    Handler handler_mainframe = new Handler();
                    handler_mainframe.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initView.layout_draweroption_searchmenu_cate_mainframe.setVisibility(View.GONE);
                        }
                    }, 300);
                } else if (initView.layout_draweroption_searchmenu_cate_mainframe.getVisibility() == View.GONE) {
                    initView.sp_draweroption_searchmenu_cate_main.setClickable(true);
                    initView.sp_draweroption_searchmenu_cate_middle.setClickable(true);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_down);
                    initView.iv_draweroption_searchmenu_cate.startAnimation(animSlide);
                    initView.layout_draweroption_searchmenu_cate_mainframe.setVisibility(View.VISIBLE);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_up_to_down);
                    initView.layout_draweroption_searchmenu_cate_mainframe.startAnimation(animSlide_mainframe);
                }
            }
        });

        initView.seekbar_draweroption_mapsetting_dist.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //thumb가 움직일 때 마다 호출
                //Log.e("스키바 현재 위치 : " , String.valueOf(progress));
                if (progress <= 100) {
                    initView.seekbar_draweroption_mapsetting_dist.setProgress(100);
                    initView.tv_draweroption_mapsetting_dist_hopedist.setText(getString(R.string.drawermenu_option_mapsetting_radius) + " : 100M");
                    return;
                } else {
                    initView.tv_draweroption_mapsetting_dist_hopedist.setText(getString(R.string.drawermenu_option_mapsetting_radius) + " : " + String.valueOf(progress) + "M");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //thumb가 움직이기 시작할 때 호출
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //thumb가 멈출 때 호출
                Static_Map_Setting.setdraweroption_searchmenu_dist(seekBar.getProgress());
            }
        });

        initView.layout_draweroption_mapsetting_mapmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_draweroption_mapsetting_mapmode.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView.layout_draweroption_mapsetting_mapmode.setClickable(true);
                    }
                }, 500);
                if (initView.layout_draweroption_mapsetting_mapmode_mainframe.getVisibility() == View.VISIBLE) {
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_down_to_right);
                    initView.iv_draweroption_mapsetting_mapmode.startAnimation(animSlide);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_down_to_up);
                    initView.layout_draweroption_mapsetting_mapmode_mainframe.startAnimation(animSlide_mainframe);
                    Handler handler_mainframe = new Handler();
                    handler_mainframe.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb1.setEnabled(false);
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb2.setEnabled(false);
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb3.setEnabled(false);
                            initView.layout_draweroption_mapsetting_mapmode_mainframe.setVisibility(View.GONE);
                        }
                    }, 300);
                } else if (initView.layout_draweroption_mapsetting_mapmode_mainframe.getVisibility() == View.GONE) {
                    initView.radiobutton_draweroption_mapsetting_mapmode_rb1.setEnabled(true);
                    initView.radiobutton_draweroption_mapsetting_mapmode_rb2.setEnabled(true);
                    initView.radiobutton_draweroption_mapsetting_mapmode_rb3.setEnabled(true);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_down);
                    initView.iv_draweroption_mapsetting_mapmode.startAnimation(animSlide);
                    initView.layout_draweroption_mapsetting_mapmode_mainframe.setVisibility(View.VISIBLE);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_up_to_down);
                    initView.layout_draweroption_mapsetting_mapmode_mainframe.startAnimation(animSlide_mainframe);
                }
            }
        });

        initView.layout_draweroption_mapsetting_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initView.layout_draweroption_mapsetting_etc.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView.layout_draweroption_mapsetting_etc.setClickable(true);
                    }
                }, 500);
                if (initView.layout_draweroption_mapsetting_etc_mainframe.getVisibility() == View.VISIBLE) {
                    initView.layout_draweroption_mapsetting_etc_thickness_scaleup.setClickable(false);
                    initView.layout_draweroption_mapsetting_etc_thickness_scaledown.setClickable(false);
                    initView.layout_draweroption_mapsetting_etc_zoom_scaleup.setClickable(false);
                    initView.layout_draweroption_mapsetting_etc_zoom_scaledown.setClickable(false);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_down_to_right);
                    initView.iv_draweroption_mapsetting_etc.startAnimation(animSlide);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_down_to_up);
                    initView.layout_draweroption_mapsetting_etc_mainframe.startAnimation(animSlide_mainframe);
                    Handler handler_mainframe = new Handler();
                    handler_mainframe.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            initView.layout_draweroption_mapsetting_etc_mainframe.setVisibility(View.GONE);
                        }
                    }, 300);
                } else if (initView.layout_draweroption_mapsetting_etc_mainframe.getVisibility() == View.GONE) {
                    initView.layout_draweroption_mapsetting_etc_thickness_scaleup.setClickable(true);
                    initView.layout_draweroption_mapsetting_etc_thickness_scaledown.setClickable(true);
                    initView.layout_draweroption_mapsetting_etc_zoom_scaleup.setClickable(true);
                    initView.layout_draweroption_mapsetting_etc_zoom_scaledown.setClickable(true);
                    Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_right_to_down);
                    initView.iv_draweroption_mapsetting_etc.startAnimation(animSlide);
                    initView.layout_draweroption_mapsetting_etc_mainframe.setVisibility(View.VISIBLE);
                    Animation animSlide_mainframe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_up_to_down);
                    initView.layout_draweroption_mapsetting_etc_mainframe.startAnimation(animSlide_mainframe);
                }
            }
        });

        initView.layout_draweroption_mapsetting_etc_thickness_scaleup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(initView.tv_draweroption_mapsetting_etc_thickness.getText().toString()) < 10) {
                    int thickness_level = Integer.parseInt(initView.tv_draweroption_mapsetting_etc_thickness.getText().toString());
                    thickness_level = thickness_level + 1;
                    initView.tv_draweroption_mapsetting_etc_thickness.setText("" + thickness_level);
                }
            }
        });

        initView.layout_draweroption_mapsetting_etc_thickness_scaledown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(initView.tv_draweroption_mapsetting_etc_thickness.getText().toString()) > 1) {
                    int thickness_level = Integer.parseInt(initView.tv_draweroption_mapsetting_etc_thickness.getText().toString());
                    thickness_level = thickness_level - 1;
                    initView.tv_draweroption_mapsetting_etc_thickness.setText("" + thickness_level);
                }
            }
        });

        initView.layout_draweroption_mapsetting_etc_zoom_scaleup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().substring(0, initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().length() - 2)) < 30) {
                    int zoom_level = Integer.parseInt(initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().substring(0, initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().length() - 2));
                    zoom_level = zoom_level + 1;
                    initView.tv_draweroption_mapsetting_etc_zoom.setText(zoom_level + ".f");
                }
            }
        });

        initView.layout_draweroption_mapsetting_etc_zoom_scaledown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().substring(0, initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().length() - 2)) > 10) {
                    int zoom_level = Integer.parseInt(initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().substring(0, initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().length() - 2));
                    zoom_level = zoom_level - 1;
                    initView.tv_draweroption_mapsetting_etc_zoom.setText(zoom_level + ".f");
                }
            }
        });

        initView.btn_draweroption_search_ok.setOnClickListener(new View.OnClickListener() {

            String localUrl = "";
            Double localLat = 0.0;
            Double localLng = 0.0;
            Geocoder geocoder = new Geocoder(Ui_Main_Activity.Context_UiMainActivity);

            @Override
            public void onClick(View v) {
                initView.btn_draweroption_search_ok.setClickable(false);
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity);

                gMap.clear();

                //주소값 구하기
                if(Static_Search_Detail.getlocalsearch_ctp_code().equals("") || Static_Search_Detail.getlocalsearch_sig_code().equals("") || initView.tv_draweroption_searchmenu_area_emd.getText().toString().equals(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.text_option_localsearch_none)))
                {
                    Toast.makeText(Ui_Main_Activity.Context_UiMainActivity, R.string.text_option_start_search_none, Toast.LENGTH_SHORT).show();
                    localUrl = initView.tv_draweroption_searchmenu_area_applylocal.getText().toString();
                }
                else
                {
                    localUrl = initView.tv_draweroption_searchmenu_area_ctp.getText().toString()+" "+initView.tv_draweroption_searchmenu_area_sig.getText().toString()+" "+initView.tv_draweroption_searchmenu_area_emd.getText().toString();
                }

                //위도, 경도 구하기
                List<Address> list = null;
                try {
                    list = geocoder.getFromLocationName(
                            localUrl, // 지역 이름
                            1); // 읽을 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("ERROR","입출력 오류 - 서버에서 주소변환시 에러발생");
                }
                if (list != null) {
                    if (list.size() == 0) {
                        Log.e("ERROR","해당되는 주소 정보는 없습니다");
                    } else {
                        localLat = list.get(0).getLatitude();
                        localLng = list.get(0).getLongitude();
                    }
                }

                // 위도 경도를 관리하는 객체를 생성
                LatLng position = new LatLng(localLat, localLng);

                // 현재 위치를 설정한다.
                CameraUpdate update1 = CameraUpdateFactory.newLatLng(position);
                gMap.moveCamera(update1);

                // 확대
                CameraUpdate update2 = CameraUpdateFactory.zoomTo(initFunction.pref.getFloat("AppDefaultSetting_Map_Zoom_Level", 14.f));
                gMap.animateCamera(update2);

                gMap.getUiSettings().setMyLocationButtonEnabled(false);

                // 반경 원 (내부 배경색 없음)
                MapCircle = new CircleOptions().center(position) //원점
                        .radius(initFunction.pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000))      //반지름 단위 : m
                        .strokeWidth(initFunction.pref.getInt("AppDefaultSetting_Map_Thickness", 2))
                        .strokeColor(Color.parseColor("#FF0000"));

                if(circle != null)
                {
                    circle.remove();
                }
                circle = gMap.addCircle(MapCircle);

                //마커추가, 리스트업데이트
                nearbymarket_userList.clear();
                nearbymarket_saveList.clear();
                String serverUrl = Ui_Main_Activity.Context_UiMainActivity.getString(R.string.server_api_list_storelist);

                StringRequest stringRequest= new StringRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            int LocalSearchMarker_List_Count = 0;
                            int LocalSearchMarker_List_Null_Count = 0;
                            Double stor_xpos = null, stor_ypos = null, xpos = null, ypos = null, distance = null;
                            String str_dist = null, str_nm = null, sls_st_tm = null, sls_ed_tm = null, menu_des = null, sls_dw = null;
                            while (LocalSearchMarker_List_Count < jsonArray.length()) {
                                JSONObject object = jsonArray.getJSONObject(LocalSearchMarker_List_Count);
                                str_dist = object.getString("str_dist");
                                str_nm = object.getString("str_nm");
                                sls_st_tm = object.getString("sls_st_tm");
                                sls_ed_tm = object.getString("sls_ed_tm");
                                //menu_des = object.getString("menu_des");
                                sls_dw = object.getString("sls_dw");
                                ypos = object.getDouble("ypos");
                                xpos = object.getDouble("xpos");
                                distance = object.getDouble("distance");

                                NearbyMarket_List nearby_list = new NearbyMarket_List(
                                        sls_st_tm,
                                        ypos,
                                        xpos,
                                        distance,
                                        sls_ed_tm,
                                        str_dist,
                                        str_nm,
                                        //menu_des,
                                        sls_dw
                                );

                                nearbymarket_userList.add(nearby_list);
                                nearbymarket_saveList.add(nearby_list);

                                LocalSearchMarker_List_Count++;
                                LocalSearchMarker_List_Null_Count++;

                                gMap.addMarker(new MarkerOptions().position(new LatLng(ypos, xpos)).title(str_nm).snippet(str_dist));
                                gMap.setOnMarkerClickListener(markerClickListener);
                                gMap.setOnInfoWindowClickListener(infoWindowClickListener);

                            }
                            if (LocalSearchMarker_List_Null_Count == 0) //서버에 자료가 존재하지 않으면
                            {
                                nearbymarket_listadapter.notifyDataSetChanged();
                                initFunction.Ui_AlertDialog_Dialog_Start(
                                        "Ui_Main_Activity",
                                        "show_nearbymarket",
                                        ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_list_alerttitle_nodata),
                                        //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                        "empty",
                                        "empty",
                                        ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                        "false"
                                );
                            } else //출력완료
                            {
                                nearbymarket_listadapter.notifyDataSetChanged();
                                //Toast.makeText(Ui_Main_Activity.this, "출력 완료", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) //서버 에러
                        {
                            nearbymarket_listadapter.notifyDataSetChanged();
                            e.printStackTrace();
                            //Toast.makeText(Ui_Main_Activity.this, "서버 에러", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, "ERROR", Toast.LENGTH_SHORT).show();
                    }
                }){
                    @Override //response를 UTF8로 변경해주는 소스코드
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        try {
                            String utf8String = new String(response.data, "UTF-8");
                            return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                        } catch (UnsupportedEncodingException e) {
                            // log error
                            return Response.error(new ParseError(e));
                        } catch (Exception e) {
                            // log error
                            return Response.error(new ParseError(e));
                        }
                    }

                    //POST 방식으로 보낼 데이터를
                    //리턴해주는 콜백 메소드
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        HashMap<String, String> datas= new HashMap<>();
                        datas.put("yPos", localLat+"");
                        datas.put("xPos", localLng+"");
                        datas.put("yPos_mypostion", localLat+"");
                        datas.put("xPos_mypostion", localLng+"");
                        datas.put("dist_level", initFunction.pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000)+"");
                        return datas;

                    }
                };
                RequestQueue requestQueue= Volley.newRequestQueue(Ui_Main_Activity.Context_UiMainActivity);
                requestQueue.add(stringRequest);


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView.btn_draweroption_search_ok.setClickable(true);
                        //드로어메뉴닫기
                        if (initView.layout_drawer_main.isDrawerVisible(initView.drawer_option) == true) {
                            initView.layout_drawer_main.closeDrawers();
                        }
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                    }
                }, 1000);

            }
        });

        initView.btn_draweroption_change_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                        SharedPreferences.Editor editor = initFunction.pref.edit();

                        //검색반경 저장
                        editor.putInt("AppDefaultSetting_Map_Dist_Level", Static_Map_Setting.getdraweroption_searchmenu_dist()); //단위 : 미터
                        //지도모드 저장
                        if (initView.radiobutton_draweroption_mapsetting_mapmode_rb1.isChecked() == true) {
                            editor.putString("AppDefaultSetting_Map_Mode", "MAP_TYPE_NORMAL");
                            //적용되어있는모드 최신화
                            initView.tv_draweroption_mapsetting_mapmode_applymapmode.setText("(" + getString(R.string.drawermenu_option_mapsetting_mapmode_normal) + ")");
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb1.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.main_concept_color));
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb2.setTextColor(text_attrColor_black_softgray);
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb3.setTextColor(text_attrColor_black_softgray);
                        } else if (initView.radiobutton_draweroption_mapsetting_mapmode_rb2.isChecked() == true) {
                            editor.putString("AppDefaultSetting_Map_Mode", "MAP_TYPE_TERRAIN");
                            //적용되어있는모드 최신화
                            initView.tv_draweroption_mapsetting_mapmode_applymapmode.setText("(" + getString(R.string.drawermenu_option_mapsetting_mapmode_terrain) + ")");
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb1.setTextColor(text_attrColor_black_softgray);
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb2.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.main_concept_color));
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb3.setTextColor(text_attrColor_black_softgray);
                        } else if (initView.radiobutton_draweroption_mapsetting_mapmode_rb3.isChecked() == true) {
                            editor.putString("AppDefaultSetting_Map_Mode", "MAP_TYPE_HYBRID");
                            //적용되어있는모드 최신화
                            initView.tv_draweroption_mapsetting_mapmode_applymapmode.setText("(" + getString(R.string.drawermenu_option_mapsetting_mapmode_hybrid) + ")");
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb1.setTextColor(text_attrColor_black_softgray);
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb2.setTextColor(text_attrColor_black_softgray);
                            initView.radiobutton_draweroption_mapsetting_mapmode_rb3.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.main_concept_color));
                        }
                        //원 두께 저장
                        editor.putInt("AppDefaultSetting_Map_Thickness", Integer.parseInt(initView.tv_draweroption_mapsetting_etc_thickness.getText().toString()));
                        //줌 레벨 저장
                        editor.putFloat("AppDefaultSetting_Map_Zoom_Level", Integer.parseInt(initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().substring(0, initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().length() - 2)));
                        editor.commit();

                        //적용되어있는거리 최신화
                        initView.tv_draweroption_mapsetting_dist_applydist.setText("(" + initFunction.pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000) + "M)");
                        //기타설정값 최신화
                        initView.tv_draweroption_mapsetting_etc_applyetc.setText("(" + Ui_Main_Activity.Context_UiMainActivity.getString(R.string.drawermenu_option_mapsetting_etc_thickness) + "-" + Integer.parseInt(initView.tv_draweroption_mapsetting_etc_thickness.getText().toString()) + ", " + Ui_Main_Activity.Context_UiMainActivity.getString(R.string.drawermenu_option_mapsetting_etc_zoom_level) + "-" + Integer.parseInt(initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().substring(0, initView.tv_draweroption_mapsetting_etc_zoom.getText().toString().length() - 2)) + ".f)");

                        //검색반경 메뉴 최소화
                        if (initView.layout_draweroption_mapsetting_dist_mainframe.getVisibility() == View.VISIBLE) {
                            initView.layout_draweroption_mapsetting_dist.callOnClick();
                        }
                        //지도모드 메뉴 최소화
                        if (initView.layout_draweroption_mapsetting_mapmode_mainframe.getVisibility() == View.VISIBLE) {
                            initView.layout_draweroption_mapsetting_mapmode.callOnClick();
                        }
                        //기타설정 메뉴 최소화
                        if (initView.layout_draweroption_mapsetting_etc_mainframe.getVisibility() == View.VISIBLE) {
                            initView.layout_draweroption_mapsetting_etc.callOnClick();
                        }

                        //드로어메뉴닫기
                        if (initView.layout_drawer_main.isDrawerVisible(initView.drawer_option) == true) {
                            initView.layout_drawer_main.closeDrawers();
                        }

                        //맵 리셋
                        gMap.clear();
                        checkPermission("getMyLocation");
                    }
                }, 1000);
            }
        });

        initView.tv_draweroption_call_store_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startStoreManagementActivity = new Intent(Ui_Main_Activity.this, Ui_StoreManagement_Activity.class);
                startActivity(startStoreManagementActivity);
                overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
            }
        });

        initView.layout_draweroption_searchmenu_area_ctp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFunction.Ui_DrawerOption_Search_Dialog_Start(initFunction.pref.getString("AppDefaultSetting_Language", "en"), "ctp", "empty", "empty");
            }
        });

        initView.layout_draweroption_searchmenu_area_sig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Static_Search_Detail.getlocalsearch_ctp_code() != "")
                {
                    initFunction.Ui_DrawerOption_Search_Dialog_Start(initFunction.pref.getString("AppDefaultSetting_Language", "en"), "sig", Static_Search_Detail.getlocalsearch_ctp(), Static_Search_Detail.getlocalsearch_ctp_code());
                }
            }
        });

        initView.layout_draweroption_searchmenu_area_emd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Static_Search_Detail.getlocalsearch_sig_code() != "")
                {
                    initFunction.Ui_DrawerOption_Search_Dialog_Start(initFunction.pref.getString("AppDefaultSetting_Language", "en"), "emd", Static_Search_Detail.getlocalsearch_sig(), Static_Search_Detail.getlocalsearch_sig_code());
                }
            }
        });

        //드로어 옵션메뉴 기본설정
        initFunction.AppInitialRun_DrawerOptionSetting();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initView.layout_btn_bottomlayout_move.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        Rect rect = new Rect();
                        v.getGlobalVisibleRect(rect);

                        initView.mMaxDist = initView.layout_bottomlayout_mainframe.getHeight() / 2;

                        float y = rect.top + initView.mMaxDist;

                        initView.mCenter = new PointF(initView.layout_bottomlayout_mainframe.getWidth(), y);

                        float b = event.getY();
                        float d = event.getRawY();

                        if (initView.mCenter.y > event.getRawY()) {
                            b = initView.mCenter.y - d + initView.layout_bottomlayout_mainframe.getHeight() / 2;
                        }

                        if(event.getAction() == event.ACTION_DOWN)
                        {
                            initView.layout_btn_bottomlayout_move.setBackground(ContextCompat.getDrawable(Ui_Main_Activity.this ,R.drawable.custom_border_bg_topradius_leftright_for_click));
                            initView.view_btn_bottomlayout_move.setBackgroundColor(ContextCompat.getColor(Ui_Main_Activity.this ,R.color.main_minor_color));
                        }

                        if (event.getAction() == event.ACTION_MOVE) {
                            if (initView.iv_btn_call_bottommenu.getBackground().getConstantState().equals(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_dot_menu).getConstantState())) {
                                if (b <= (initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Height", 0) + initView.layout_bottomlayout_bottommenuframe.getHeight() - initView.toolbar.getHeight() - 60)) {
                                    initView.layout_bottomlayout_mainframe.setLayoutParams(new LinearLayout.LayoutParams(initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0), (int) b));
                                }
                            } else {
                                if (b <= (initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Height", 0) - initView.toolbar.getHeight() - 60)) {
                                    initView.layout_bottomlayout_mainframe.setLayoutParams(new LinearLayout.LayoutParams(initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0), (int) b));
                                }
                            }

                        }

                        if(event.getAction() == event.ACTION_UP)
                        {
                            initView.layout_btn_bottomlayout_move.setBackground(ContextCompat.getDrawable(Ui_Main_Activity.this ,R.drawable.custom_border_bg_topradius_leftright));
                            initView.view_btn_bottomlayout_move.setBackgroundColor(ContextCompat.getColor(Ui_Main_Activity.this ,R.color.main_concept_color));
                        }
                        return true;
                    }
                });
            }
        });

        //Log.e("getKeyHash", ""+getKeyHash(Ui_Main_Activity.this));

    }

    /*
    public static String getKeyHash(final Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null)
                return null;

            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
     */

    public void setArguments(Bundle args) {
        init();
        String result = args.getString("result");
        String parameter_data = args.getString("parameter_data");
        switch (result) {
            case "cancel":
                switch (parameter_data) {
                    case "gps_turn_on":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu3_realtime_gps);
                        initView.iv_btn_bottommenu3_realtime_gps.setBackground(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_off));
                        break;
                    case "gps_turn_off":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu3_realtime_gps);
                        initView.iv_btn_bottommenu3_realtime_gps.setBackground(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_on));
                        break;
                    case "show_nearbymarket":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        //Call_BackgroundTask_NearbyMarketMarker("nearbymarket_marker");
                        break;
                    case "gps_permission_denied":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        //GPS ON/OFF 체크
                        Ui_Main_Activity.Context_UiMainActivity.startService(new Intent(Ui_Main_Activity.Context_UiMainActivity, BackgroundTask_GPS_Connect.class));
                        break;
                    case "resetting_gps":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "logout":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                }
                break;
            case "no":
                break;
            case "ok":
                switch (parameter_data) {
                    case "ChangeLanguage":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "ChangeTheme":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "gps_turn_on":
                        //GPS켜져있는지 체크
                        //GPS OFF일 경우 -> GPS재설정 / GPS ON일 경우 실시간GPS추적 시작 및 완료메시지
                        if (initFunction.pref.getString("AppDefaultSetting_Permission_GPS", "false").equals("false") || Static_Device_Info.getcheckfunction_gps().equals("false")) {
                            ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                            initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu3_realtime_gps);
                            initView.iv_btn_bottommenu3_realtime_gps.setBackground(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_off));
                            initFunction.Ui_AlertDialog_Dialog_Start(
                                    "Ui_Main_Activity",
                                    "resetting_gps",
                                    ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_gps_connect),
                                    ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                    "empty",
                                    ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alertokbtn_gps_connect),
                                    "false"
                            );
                        } else {
                            Toast.makeText(Ui_Main_Activity.Context_UiMainActivity, "실시간으로 GPS정보를 수신합니다", Toast.LENGTH_SHORT).show();
                            ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                            initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu3_realtime_gps);
                            initView.iv_btn_bottommenu3_realtime_gps.setBackground(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_on));
                            initView.tv_btn_bottommenu3_realtime_gps.setText(initFunction.getStringByLocal(((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity), R.string.text_bottommenu_realtime_gps_turn_off, initFunction.pref.getString("AppDefaultSetting_Language", "en")));
                        }
                        break;
                    case "gps_turn_off":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu3_realtime_gps);
                        initView.iv_btn_bottommenu3_realtime_gps.setBackground(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_off));
                        initView.tv_btn_bottommenu3_realtime_gps.setText(initFunction.getStringByLocal(((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity), R.string.text_bottommenu_realtime_gps_turn_on, initFunction.pref.getString("AppDefaultSetting_Language", "en")));
                        break;
                    case "show_nearbymarket":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "gps_permission_denied":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        checkPermission("getMyLocation");
                        break;
                    case "resetting_gps":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        //initFunction.Ui_FullScreenWait_Dialog_Start();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //initFunction.Ui_FullScreenWait_Dialog_End();
                            }
                        }, 4500);
                        checkPermission("getMyLocation");
                        break;
                    case "storage_permission_denied":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "logout":
                        ((Ui_AlertDialog_Activity) Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity);
                        Handler handler_logout = new Handler();
                        handler_logout.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                initFunction.Ui_FullScreenWait_Dialog_End();


                                /*
                                if (Static_User_Info.getUserInfo_user_id() != null) {
                                    Static_User_Info.setUserInfo_user_id(null);
                                    Static_User_Info.setUserInfo_user_nickname(null);
                                    Static_User_Info.setUserInfo_user_auth(null);
                                    ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).onResume();
                                }
                                 */
                                UserApiClient.getInstance().logout(error -> {
                                    if (error != null) {
                                        Log.e("LOG : ", "로그아웃 실패, SDK에서 토큰 삭제됨", error);
                                    }else{
                                        Log.e("LOG : ", "로그아웃 성공, SDK에서 토큰 삭제됨");
                                    }
                                    return null;
                                });

                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).Static_User_Info.setUserInfo_user_id(null);
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).Static_User_Info.setUserInfo_user_nickname(null);
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).Static_User_Info.setUserInfo_user_profile_pic(null);
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).Static_User_Info.setUserInfo_user_type(null);
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).Static_User_Info.setUserInfo_user_auth(null);
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).Static_User_Info.setUserInfo_user_number(0);
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).Static_User_Info.setUserInfo_user_quit_yn(null);

                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).onResume();

                            }
                        }, 1000);
                        break;
                }
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        map_frag.onResume();

        if (Static_User_Info.getUserInfo_user_id() != null) {
            initView.tv_draweroption_login.setText(Static_User_Info.getUserInfo_user_nickname());
            Glide.with(Ui_Main_Activity.Context_UiMainActivity).load(Static_User_Info.getUserInfo_user_profile_pic()).into(initView.iv_draweroption_profilepic);
            initView.tv_draweroption_logout.setVisibility(View.VISIBLE);
            if (Static_User_Info.getUserInfo_user_auth().equals("O")) {
                initView.layout_draweroption_addonbutton.setVisibility(View.VISIBLE);
            }
            //드로어-설정-회원정보수정버튼 활성화
            /*
            if (initView.tv_drawersetting_changeprofile.getVisibility() == View.GONE) {
                initView.tv_drawersetting_changeprofile.setVisibility(View.VISIBLE);
            }
             */
            //드로어-설정-인증센터버튼 활성화
            if (initView.tv_drawersetting_certification.getVisibility() == View.GONE) {
                initView.tv_drawersetting_certification.setVisibility(View.VISIBLE);
            }
            //드로어-설정-1:1문의버튼 활성화
            if (initView.tv_drawersetting_qna.getVisibility() == View.GONE) {
                initView.tv_drawersetting_qna.setVisibility(View.VISIBLE);
            }
            //드로어-설정-회원탈퇴버튼 활성화
            if (initView.tv_drawersetting_withdrawal.getVisibility() == View.GONE) {
                initView.tv_drawersetting_withdrawal.setVisibility(View.VISIBLE);
            }
        } else {
            initView.tv_draweroption_login.setText(getString(R.string.text_drawer_login_title));
            Glide.with(Ui_Main_Activity.Context_UiMainActivity).load(R.drawable.ic_icon_appicon).into(initView.iv_draweroption_profilepic);
            initView.tv_draweroption_logout.setVisibility(View.GONE);
            initView.layout_draweroption_addonbutton.setVisibility(View.GONE);
            //드로어-설정-회원정보수정버튼 비활성화
            /*
            if (initView.tv_drawersetting_changeprofile.getVisibility() == View.VISIBLE) {
                initView.tv_drawersetting_changeprofile.setVisibility(View.GONE);
            }
             */
            //드로어-설정-인증센터버튼 비활성화
            if (initView.tv_drawersetting_certification.getVisibility() == View.VISIBLE) {
                initView.tv_drawersetting_certification.setVisibility(View.GONE);
            }
            //드로어-설정-1:1문의버튼 비활성화
            if (initView.tv_drawersetting_qna.getVisibility() == View.VISIBLE) {
                initView.tv_drawersetting_qna.setVisibility(View.GONE);
            }
            //드로어-설정-회원탈퇴버튼 비활성화
            if (initView.tv_drawersetting_withdrawal.getVisibility() == View.VISIBLE) {
                initView.tv_drawersetting_withdrawal.setVisibility(View.GONE);
            }
        }

        if (gMap != null) {
            gMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                                        /*
                                        LatLng location_center = cameraPosition.target;
                                         */
                                        /*
                                        double latitude = cameraPosition.target.latitude;
                                        double longitude = cameraPosition.target.longitude;
                                         */
                                        /*
                                        double latitude = location.latitude;
                                        double longitude = location.longitude;
                                         */
                    Static_Device_Info.setlat_mapcenter(cameraPosition.target.latitude);
                    Static_Device_Info.setlng_mapcenter(cameraPosition.target.longitude);
                    //Toast.makeText(Ui_Main_Activity.Context_UiMainActivity, initMethod.getlat_mapcenter()+"AAAAAA"+initMethod.getlng_mapcenter(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        if(onResumeAction.equals("searchmap_searchstore"))
        {
            onNearbyMarketMarker(onResumeAction);
            onResumeAction = "";
        }
    }

    public void onResizeBottomLayout() {
        if (initView.layout_bottomlayout_mainframe.getHeight() >= initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Height", 0) - initView.toolbar.getHeight() - 60) {
            initView.layout_bottomlayout_mainframe.setLayoutParams(new LinearLayout.LayoutParams(initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0), initView.layout_bottomlayout_mainframe.getHeight()));
        } else {
            initView.layout_bottomlayout_mainframe.setLayoutParams(new LinearLayout.LayoutParams(initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0), initView.layout_bottomlayout_mainframe.getHeight()));
        }
        if (initView.layout_bottomlayout_mainframe.getHeight() >= initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Height", 0) - initView.toolbar.getHeight() - 60) {
            initView.layout_bottomlayout_mainframe.setLayoutParams(new LinearLayout.LayoutParams(initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Width", 0), (int) initFunction.pref.getInt("AppDefaultSetting_DeviceSize_Height", 0) - initView.toolbar.getHeight() - 60));
        }
        Animation animSlide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_right_to_left);
        initView.layout_bottomlayout_bottommenuframe.startAnimation(animSlide);
        initView.layout_bottomlayout_bottommenuframe.setVisibility(View.VISIBLE);
        initView.iv_btn_call_bottommenu.setBackgroundResource(R.drawable.ic_arrow_right);

        if (initView.cardview_btn_bottommenu1_setmylocation.getLayoutParams().width > Static_Device_Info.getbottommenu2_width()) {
            initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu1_setmylocation);
        }
        if (initView.cardview_btn_bottommenu2_searchmap_mapposition.getLayoutParams().width > Static_Device_Info.getbottommenu3_width()) {
            initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu2_searchmap_mapposition);
        }
        if (initView.cardview_btn_bottommenu3_realtime_gps.getLayoutParams().width > Static_Device_Info.getbottommenu1_width()) {
            initFunction.onChangeBottomMenuSize("scaleDown", initView.cardview_btn_bottommenu1_setmylocation, initView.cardview_btn_bottommenu2_searchmap_mapposition, initView.cardview_btn_bottommenu3_realtime_gps, initView.cardview_btn_bottommenu3_realtime_gps);
        }
    }

    public void onAppRestart(String state) {
        Intent intent = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        //Intent intent = new Intent(Activity_Main.this, Activity_Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        finishAffinity();
        //Process.killProcess(Process.myPid());
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        initFunction.onRestartLanguage(); //화면전환시 언어설정 유지
        initFunction.onChangeConfiguration(newConfig); //화면전환시 다이얼로그 크기변경 (다이얼로그 종료후재실행)
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.optionmenu, menu);

        initView.drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_optionmenu_shop);
        initView.toolbar.setOverflowIcon(initView.drawable);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.optionmenu_shop:
                initView.drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_optionmenu_shop);
                initView.toolbar.setOverflowIcon(initView.drawable);
                break;
            case R.id.optionmenu_option1:
                initView.drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_optionmenu_option1);
                initView.toolbar.setOverflowIcon(initView.drawable);
                break;
            case R.id.optionmenu_option2:
                initView.drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_optionmenu_option2);
                initView.toolbar.setOverflowIcon(initView.drawable);
                break;
            case R.id.optionmenu_option3:
                initView.drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_optionmenu_option3);
                initView.toolbar.setOverflowIcon(initView.drawable);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (initView.layout_drawer_main.isDrawerVisible(initView.drawer_option) == true) {
            initView.layout_drawer_main.closeDrawers();
        } else if (initView.layout_drawer_main.isDrawerVisible(initView.drawer_setting) == true) {
            initView.layout_drawer_main.closeDrawers();
        } else {
            initFunction.AlertDialog_AppFinish();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        MapsInitializer.initialize(this);

        // 지도 모드 변경
        switch (initFunction.pref.getString("AppDefaultSetting_Map_Mode", "MAP_TYPE_NORMAL")) {
            case "MAP_TYPE_NORMAL":
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case "MAP_TYPE_TERRAIN":
                googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case "MAP_TYPE_HYBRID":
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
        }

        /*
        Double lat = 37.530355;
        Double lng = 126.89766;

        LatLng position = new LatLng(lat, lng);

        //CameraUpdate update1= CameraUpdateFactory.newLatLng(position);
        //map.moveCamera(update1);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.title("현재위치");
        markerOptions.snippet("위치");

        // 반경 1KM원
        @SuppressLint("ResourceType") CircleOptions circle1KM = new CircleOptions().center(position) //원점
                .radius(1000)      //반지름 단위 : m
                //.strokeWidth(0f)  //선너비 0f : 선없음
                .strokeWidth(1.5f)
                .strokeColor(Color.parseColor("#FF0000"))
                //.fillColor(Color.parseColor("#ffff0000")); //배경색
                //.fillColor(Color.parseColor("#880000ff"));
                .fillColor(Color.argb(20, 51, 51, 51));

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14.5f);

        googleMap.animateCamera(cameraUpdate);
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lng)).title("현재 위치"));
        googleMap.addCircle(circle1KM);
         */
    }

    @Override
    protected void onStart() {
        super.onStart();
        map_frag.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        map_frag.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        map_frag.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        map_frag.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        map_frag.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //map_frag.onSaveInstanceState(outState);
    }

    public void Call_BackgroundTask_NearbyMarketMarker(String target) {
        switch (target) {
            case "nearbymarket_marker":
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new BackgroundTask_NearbyMarketMarker().execute();
                    }
                });
                break;
            case "searchmap_mapposition":
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new BackgroundTask_SearchMap_MapPosition().execute();
                    }
                });
                break;
        }
    }

    class BackgroundTask_NearbyMarketMarker extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            target = Ui_Main_Activity.Context_UiMainActivity.getString(R.string.server_api_list_storelist) + "?yPos=" + Static_Device_Info.getlat_gps() + "&xPos=" + Static_Device_Info.getlng_gps() + "&yPos_mypostion=" + Static_Device_Info.getlat_gps() + "&xPos_mypostion=" + Static_Device_Info.getlng_gps() + "&dist_level=" + initFunction.pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000);

            Static_Device_Info.setlat_searchstore_center(Static_Device_Info.getlat_gps());
            Static_Device_Info.setlng_searchstore_center(Static_Device_Info.getlng_gps());

            //target = getString(R.string.server_api_list_storelist)+"?yPos="+Static_Device_Info.getlat_gps()+"&xPos="+Static_Device_Info.getlng_gps()+"&yPos_mypostion="+Static_Device_Info.getlat_gps()+"&xPos_mypostion="+Static_Device_Info.getlng_gps()+"&dist_level="+initFunction.pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000);
            //target = "http://192.168.0.132:8080/consumer/searchStore?xPos=126.89525&yPos=37.526758";
            /*
            if(et_search.getText().toString().length() > 0)
            {
                target = "http://192.168.0.149:8080/testPPostId.do?reqName="+et_search.getText().toString();
            }
            else if(map_center_searching == 1)
            {
                target = "http://192.168.0.149:8080/consumer/searchStore?xPos="+lng_mapcenter+"&yPos="+lat_mapcenter+"&xPos_mypostion="+lng+"&yPos_mypostion="+lat;
            }
            else
            {
                //target = "http://192.168.0.149:8080/searchPosition.do?xPos=126.89766&yPos=37.530355";
                target = "http://192.168.0.149:8080/consumer/searchStore?xPos="+lng+"&yPos="+lat+"&xPos_mypostion="+lng+"&yPos_mypostion="+lat;
                //target = "http://192.168.0.149:8080/consumer/searchStore?xPos=126.89766&yPos=37.530355";
            }
             */
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            result_nearbymarketmarker = result;
            if (result != null) {
                //새로고침
                //onResume();
                onNearbyMarketMarker("empty");
            }
        }
    }

    class BackgroundTask_SearchMap_MapPosition extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            target = Ui_Main_Activity.Context_UiMainActivity.getString(R.string.server_api_list_storelist) + "?yPos=" + Static_Device_Info.getlat_mapcenter() + "&xPos=" + Static_Device_Info.getlng_mapcenter() + "&yPos_mypostion=" + Static_Device_Info.getlat_gps() + "&xPos_mypostion=" + Static_Device_Info.getlng_gps() + "&dist_level=" + initFunction.pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000);

            Static_Device_Info.setlat_searchstore_center(Static_Device_Info.getlat_mapcenter());
            Static_Device_Info.setlng_searchstore_center(Static_Device_Info.getlng_mapcenter());

        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result) {
            result_nearbymarketmarker = result;
            if (result != null) {
                //새로고침
                //onResume();
                onNearbyMarketMarker("empty");
            }
        }
    }

    public void onNearbyMarketMarker(String action) {

        if(action.equals("searchmap_searchstore"))
        {
            gMap.clear();

            // 반경 원 (내부 배경색 없음) (현재 GPS위치 기준으로 생성)
            LatLng position = new LatLng(Static_Device_Info.getlat_searchstore_center(), Static_Device_Info.getlng_searchstore_center());
            MapCircle = new CircleOptions().center(position) //원점
                    .radius(initFunction.pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000))      //반지름 단위 : m
                    .strokeWidth(initFunction.pref.getInt("AppDefaultSetting_Map_Thickness", 2))
                    .strokeColor(Color.parseColor("#FF0000"));
            //.fillColor(Color.argb(20, 51, 51, 51));

            if(circle != null)
            {
                circle.remove();
            }
            circle = gMap.addCircle(MapCircle);
        }

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(Ui_Main_Activity.this);
        verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        initView.rv_nearbymarket_list.setLayoutManager(new LinearLayoutManager(Ui_Main_Activity.this, LinearLayoutManager.VERTICAL, false));
        nearbymarket_userList = new ArrayList<NearbyMarket_List>();
        nearbymarket_saveList = new ArrayList<NearbyMarket_List>();
        nearbymarket_listadapter = new NearbyMarket_ListAdapter(Ui_Main_Activity.this, nearbymarket_userList, Ui_Main_Activity.this, nearbymarket_saveList);
        initView.rv_nearbymarket_list.setAdapter(nearbymarket_listadapter);
        if (result_nearbymarketmarker != null) {
            try {
                JSONArray jsonArray = new JSONArray(result_nearbymarketmarker);
                int NearbyMarketMarker_List_Count = 0;
                int NearbyMarketMarker_List_Null_Count = 0;
                Double stor_xpos = null, stor_ypos = null, xpos = null, ypos = null, distance = null;
                String str_dist = null, str_nm = null, sls_st_tm = null, sls_ed_tm = null, menu_des = null, sls_dw = null;
                while (NearbyMarketMarker_List_Count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(NearbyMarketMarker_List_Count);
                    str_dist = object.getString("str_dist");
                    str_nm = object.getString("str_nm");
                    sls_st_tm = object.getString("sls_st_tm");
                    sls_ed_tm = object.getString("sls_ed_tm");
                    //menu_des = object.getString("menu_des");
                    sls_dw = object.getString("sls_dw");
                    ypos = object.getDouble("ypos");
                    xpos = object.getDouble("xpos");
                    distance = object.getDouble("distance");

                    NearbyMarket_List nearby_list = new NearbyMarket_List(
                            sls_st_tm,
                            ypos,
                            xpos,
                            distance,
                            sls_ed_tm,
                            str_dist,
                            str_nm,
                            //menu_des,
                            sls_dw
                    );

                    if(action.equals("searchmap_searchstore"))
                    {
                        //띄어쓰기있으면 붙여서 한번더 검사
                        String none_space = initView.et_search_contents.getText().toString().replaceAll(" ", "");

                        if(str_nm.contains(initView.et_search_contents.getText().toString()) || str_nm.contains(none_space))
                        {
                            nearbymarket_userList.add(nearby_list);
                            nearbymarket_saveList.add(nearby_list);

                            NearbyMarketMarker_List_Count++;
                            NearbyMarketMarker_List_Null_Count++;

                            gMap.addMarker(new MarkerOptions().position(new LatLng(ypos, xpos)).title(str_nm).snippet(str_dist));
                            this.gMap.setOnMarkerClickListener(markerClickListener);
                            this.gMap.setOnInfoWindowClickListener(infoWindowClickListener);
                        }
                        else
                        {
                            NearbyMarketMarker_List_Count++;
                        }

                    }
                    else
                    {
                        nearbymarket_userList.add(nearby_list);
                        nearbymarket_saveList.add(nearby_list);

                        NearbyMarketMarker_List_Count++;
                        NearbyMarketMarker_List_Null_Count++;

                        gMap.addMarker(new MarkerOptions().position(new LatLng(ypos, xpos)).title(str_nm).snippet(str_dist));
                        this.gMap.setOnMarkerClickListener(markerClickListener);
                        this.gMap.setOnInfoWindowClickListener(infoWindowClickListener);
                    }

                }
                if (NearbyMarketMarker_List_Null_Count == 0) //서버에 자료가 존재하지 않으면
                {
                    initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Main_Activity",
                            "show_nearbymarket",
                            ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_list_alerttitle_nodata),
                            //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "false"
                    );
                } else //출력완료
                {
                    //Toast.makeText(Ui_Main_Activity.this, "출력 완료", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) //서버 에러
            {
                e.printStackTrace();
                //Toast.makeText(Ui_Main_Activity.this, "서버 에러", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkPermission(String action) {
        //GPS권한상태 저장
        SharedPreferences.Editor editor = initFunction.pref.edit();
        editor.putString("AppDefaultSetting_Permission_GPS", "false");
        editor.commit();

        //Static_Device_Info.setpermission_gps("false");
        switch (action) {
            case "getMyLocation": //GPS수신
                boolean isGrant = false;
                for (String str : permission_list) {
                    if (ContextCompat.checkSelfPermission(Ui_Main_Activity.Context_UiMainActivity, str) == PackageManager.PERMISSION_GRANTED) {
                    } else {
                        isGrant = false;
                        break;
                    }
                }
                if (isGrant == false) {
                    ActivityCompat.requestPermissions(((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity), permission_list, 0);
                }
                break;
        }
    }

    // 사용자가 권한 허용/거부 버튼을 눌렀을 때 호출되는 메서드
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isGrant = true;
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                isGrant = false;

                //initFunction.Ui_FullScreenWait_Dialog_End();

                //GPS권한 거부
                if (((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).initFunction.pref.getString("AppDefaultSetting_Permission_GPS", "false").equals("false")) {
                    initFunction.Ui_FullScreenWait_Dialog_End();

                    //GPS수신거부시 앱 기능 제한
                    initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Main_Activity",
                            "gps_permission_denied",
                            ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_permission_alerttitle_gps_denied),
                            ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_permission_alertcancelbtn_gps_denied),
                            "empty",
                            ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_permission_alertokbtn_gps_denied),
                            "true"
                    );
                    //GPS수신불가시 기본세팅
                    // 위도 경도를 관리하는 객체를 생성
                    LatLng position = new LatLng(37.566712, 126.978149);
                    // 현재 위치를 설정한다.
                    CameraUpdate update1 = CameraUpdateFactory.newLatLng(position);
                    gMap.moveCamera(update1);
                    // 확대
                    CameraUpdate update2 = CameraUpdateFactory.zoomTo(initFunction.pref.getFloat("AppDefaultSetting_Map_Zoom_Level", 14.f));
                    gMap.animateCamera(update2);
                    //
                    initFunction.Ui_FullScreenWait_Dialog_End();
                    //첫 실행일 경우 튜토리얼화면 출력
                    if (initFunction.pref.getString("AppDefaultSetting_AppLaunching", "first").equals("first") && Static_Device_Info.getlook_tutorial() <= 0) {
                        Static_Device_Info.setlook_tutorial(1);
                        Intent startTutorialScreen = new Intent(Static_Function.Context_Static_Function, Ui_FullScreenTutorial_Activity.class);
                        Ui_Main_Activity.Context_UiMainActivity.startActivity(startTutorialScreen);
                    }
                }

                //저장소접근권한 거부
                if (Static_Device_Info.getpermission_storage().equals("false")) {
                    initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Main_Activity",
                            "storage_permission_denied",
                            ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_permission_alerttitle_storage_denied),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "false"
                    );
                }

                break;
            }
        }
        // 모든 권한을 허용했다면 사용자 위치를 측정한다.
        if (isGrant == true) {
            //GPS권한상태 저장
            SharedPreferences.Editor editor = initFunction.pref.edit();
            editor.putString("AppDefaultSetting_Permission_GPS", "true");
            editor.commit();
            //저장소권한상태 저장
            Static_Device_Info.setpermission_storage("true");

            if (((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).initFunction.isServiceRunningCheck("com.solomonm.yeogiisso.service.BackgroundTask_GPS_Connect") == false) {
                //GPS ON/OFF 체크
                Ui_Main_Activity.Context_UiMainActivity.startService(new Intent(Ui_Main_Activity.Context_UiMainActivity, BackgroundTask_GPS_Connect.class));
            }

            getMyLocation();
        }
    }

    // 현재 위치를 가져온다.
    public void getMyLocation() {
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // 권한이 모두 허용되어 있을 때만 동작하도록 한다.
        int chk1 = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        int chk2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (chk1 == PackageManager.PERMISSION_GRANTED && chk2 == PackageManager.PERMISSION_GRANTED) {
            myLocation = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            showMyLocation();
        }
        // 새롭게 위치를 측정한다.
        GpsListener listener = new GpsListener();
        if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, listener);
        }
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, listener);
        }
    }

    // GPS Listener
    class GpsListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            // 현재 위치 값을 저장한다.
            myLocation = location;
            // 위치 측정을 중단한다.
            manager.removeUpdates(this);
            // 지도를 현재 위치로 이동시킨다.
            showMyLocation();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }
    }

    public void showMyLocation() {

        // LocationManager.GPS_PROVIDER 부분에서 null 값을 가져올 경우를 대비하여 장치
        if (myLocation == null) {
            return;
        }
        /*
        if(initFunction.pref.getString("AppDefaultSetting_Permission_GPS", "false").equals("false") || Static_Device_Info.getcheckfunction_gps().equals("false"))
        {
            if(initView.iv_btn_bottommenu3_realtime_gps.getBackground().getConstantState().equals(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_on)))
            {
                initView.iv_btn_bottommenu3_realtime_gps.setBackground(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_off));
            }
        }
         */

        // 지도 모드 변경
        switch (initFunction.pref.getString("AppDefaultSetting_Map_Mode", "MAP_TYPE_NORMAL")) {
            case "MAP_TYPE_NORMAL":
                if (gMap.getMapType() != GoogleMap.MAP_TYPE_NORMAL) {
                    gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
                break;
            case "MAP_TYPE_TERRAIN":
                if (gMap.getMapType() != GoogleMap.MAP_TYPE_TERRAIN) {
                    gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                }
                break;
            case "MAP_TYPE_HYBRID":
                if (gMap.getMapType() != GoogleMap.MAP_TYPE_HYBRID) {
                    gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                }
                break;
        }

        // 현재 위치값을 추출한다.
        Static_Device_Info.setlat_gps(myLocation.getLatitude());
        Static_Device_Info.setlng_gps(myLocation.getLongitude());
        // 위도 경도를 관리하는 객체를 생성
        LatLng position = new LatLng(Static_Device_Info.getlat_gps(), Static_Device_Info.getlng_gps());

        // 현재 위치를 설정한다.
        CameraUpdate update1 = CameraUpdateFactory.newLatLng(position);
        //gMap.moveCamera(update1);

        // 확대
        CameraUpdate update2 = CameraUpdateFactory.zoomTo(initFunction.pref.getFloat("AppDefaultSetting_Map_Zoom_Level", 14.f));
        //gMap.animateCamera(update2);

        // 현재 위치 표시
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        gMap.setMyLocationEnabled(true);

        gMap.getUiSettings().setMyLocationButtonEnabled(false);
        //gMap.addMarker(new MarkerOptions().position(new LatLng(Static_Device_Info.getlat_gps(), Static_Device_Info.getlng_gps())).title("현재 위치"));

        // 반경 원 (내부 배경색 없음) (현재 GPS위치 기준으로 생성)
        MapCircle = new CircleOptions().center(position) //원점
                .radius(initFunction.pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000))      //반지름 단위 : m
                .strokeWidth(initFunction.pref.getInt("AppDefaultSetting_Map_Thickness", 2))
                .strokeColor(Color.parseColor("#FF0000"));
                //.fillColor(Color.argb(20, 51, 51, 51));

        if(circle != null)
        {
            circle.remove();
        }
        circle = gMap.addCircle(MapCircle);
        //gMap.addCircle(MapCircle);

        if(!initView.iv_btn_bottommenu3_realtime_gps.getBackground().getConstantState().equals(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_on).getConstantState()))
        {
            gMap.moveCamera(update1);
            gMap.animateCamera(update2);
            Call_BackgroundTask_NearbyMarketMarker("nearbymarket_marker");
        }

        /*
        DrawRouteMaps.getInstance(this)
                .draw(position, position2, map);
         */

        initFunction.Ui_FullScreenWait_Dialog_End();

        //첫 실행일 경우 튜토리얼화면 출력
        if(initFunction.pref.getString("AppDefaultSetting_AppLaunching", "first").equals("first") && Static_Device_Info.getlook_tutorial() <= 0)
        {
            Static_Device_Info.setlook_tutorial(1);
            Intent startTutorialScreen = new Intent(Static_Function.Context_Static_Function, Ui_FullScreenTutorial_Activity.class);
            Ui_Main_Activity.Context_UiMainActivity.startActivity(startTutorialScreen);
        }

        //
        //드로어옵션메뉴 - 검색조건 (기본지역 설정-현재위치)
        Locale mLocale = new Locale(initFunction.pref.getString("AppDefaultSetting_Language", "en"));
        Geocoder geocoder = new Geocoder(this, mLocale);
        List<Address> list = null;
        try {
            list = geocoder.getFromLocation(
                    myLocation.getLatitude(), // 위도
                    myLocation.getLongitude(), // 경도
                    1); // 얻어올 값의 개수
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ERROR", "입출력 오류 - 서버에서 주소변환시 에러발생");
        }
        if (list != null) {
            if (list.size()==0) {
                Log.e("ERROR", "해당되는 주소 정보는 없습니다");
            } else {
                String ctp, sig, emd;
                if(list.get(0).getAdminArea() == null)
                {
                    ctp = list.get(0).getSubAdminArea();
                }
                else
                {
                    ctp = list.get(0).getAdminArea();
                }
                Static_Search_Detail.setlocalsearch_ctp(ctp);
                if(list.get(0).getLocality() == null)
                {
                    sig = list.get(0).getSubLocality();
                }
                else
                {
                    sig = list.get(0).getLocality();
                }
                Static_Search_Detail.setlocalsearch_sig(sig);
                if(list.get(0).getThoroughfare() == null)
                {
                    emd = list.get(0).getSubThoroughfare();
                }
                else
                {
                    emd = list.get(0).getThoroughfare();
                }
                Static_Search_Detail.setlocalsearch_emd(emd);

                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_searchmenu_area_applylocal.setText("("+ctp+" "+sig+" "+emd+")");

                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_searchmenu_area_ctp.setText(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.text_option_localsearch_set_ctp));
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_searchmenu_area_sig.setText(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.text_option_localsearch_none));
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_searchmenu_area_emd.setText(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.text_option_localsearch_none));
            }
        }



    }

    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            String markerId = marker.getId();

            LatLng location = marker.getPosition();
            Toast.makeText(Ui_Main_Activity.Context_UiMainActivity, "마커 클릭 Marker ID : "+markerId+"("+location.latitude+","+location.longitude+")", Toast.LENGTH_SHORT).show();

            // 하나의 윈도우인포창 설정하기 - 마커클릭시 하나의 창뜬다.
            gMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }
                @Override
                public View getInfoContents(Marker marker) {
                    LinearLayout info = new LinearLayout(Ui_Main_Activity.Context_UiMainActivity);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(Ui_Main_Activity.Context_UiMainActivity);
                    title.setTextColor(Color.BLACK);
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(Ui_Main_Activity.Context_UiMainActivity);
                    snippet.setTextColor(Color.GRAY);
                    snippet.setGravity(Gravity.CENTER);
                    snippet.setText(marker.getSnippet());

                    TextView snippet2 = new TextView(Ui_Main_Activity.Context_UiMainActivity);
                    snippet2.setTextColor(ContextCompat.getColor(Ui_Main_Activity.this, R.color.main_concept_color));
                    snippet2.setGravity(Gravity.CENTER);
                    snippet2.setText("["+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_markerdetail_view_moreinfo)+"]");

                    info.addView(title);
                    info.addView(snippet);
                    info.addView(snippet2);

                    return info;
                }
            });

            return false;
        }
    };

    GoogleMap.OnInfoWindowClickListener infoWindowClickListener = new GoogleMap.OnInfoWindowClickListener() {
        @Override
        public void onInfoWindowClick(Marker marker) {
            //String markerId = marker.getId();
            String storeName = marker.getTitle();
            String storeSnippet = marker.getSnippet();
            double storeYpos = marker.getPosition().latitude;
            double storeXpos = marker.getPosition().longitude;
            //Toast.makeText(Context_MainActivity, "정보창 클릭\nMarker ID : "+markerId+"\nMarker Title : "+marker.getTitle()+"\nMarker Snippet : "+marker.getSnippet()+"\nMarker Position(Latitude) : "+marker.getPosition().latitude+"\nMarker Position(Longitude) : "+marker.getPosition().longitude, Toast.LENGTH_SHORT).show();
            initFunction.Ui_MarkerDetail_Dialog_Start(storeName, storeSnippet, storeYpos, storeXpos);
        }
    };

    //마시멜로우 이상일 경우 다른앱 위에 그리기 권한 설정
    public void checkpermission_service_system_alert_window() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {   // 마시멜로우 이상일 경우
            if (!Settings.canDrawOverlays(this)) {              // 다른앱 위에 그리기 체크
                Uri uri = Uri.fromParts("package" , getPackageName(), null);
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, uri);
                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
            } else {
                startMain();
            }
        } else {
            startMain();
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //미니맵 서비스
        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!Settings.canDrawOverlays(this)) {
                finish();
            } else {
                startMain();
            }
        }
    }

    void startMain(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //startForegroundService(new Intent(this, AwindowService.class));
            Intent intentService = new Intent(Ui_Main_Activity.this, Service_MarkerDetail_MiniMap.class);
            intentService.putExtra("ypos_gps", Static_Device_Info.getlat_gps());
            intentService.putExtra("xpos_gps", Static_Device_Info.getlng_gps());
            startForegroundService(intentService);
        } else {
            //startService(new Intent(this, AwindowService.class));
            Intent intentService = new Intent(Ui_Main_Activity.this, Service_MarkerDetail_MiniMap.class);
            intentService.putExtra("ypos_gps", Static_Device_Info.getlat_gps());
            intentService.putExtra("xpos_gps", Static_Device_Info.getlng_gps());
            startService(intentService);
        }
    }

    //경로 표시 (메인지도)
    public void GetDirections_MainMap()
    {
        /*
        LatLng origin = new LatLng(37.526758, 126.89525);
        LatLng destination = new LatLng(37.534035, 126.902633);

        new DrawRouteMaps().getInstance(this).draw(origin, destination, gMap);
        DrawMarker.getInstance(this).draw(gMap, origin, R.drawable.ic_close, "Origin Location");
        DrawMarker.getInstance(this).draw(gMap, destination, R.drawable.ic_check, "Destination Location");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        gMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
         */

        // Getting URL to the Google Directions API
        LatLng origin = new LatLng(37.526758, 126.89525);
        LatLng destination = new LatLng(37.534035, 126.902633);
        String url = getDirectionsUrl(origin, destination);

        DownloadTask downloadTask = new DownloadTask();

        // Start downloading json data from Google Directions API
        downloadTask.execute(url);
    }

    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Key
        String key = "key=" + getString(R.string.google_maps_key_directions);

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+key;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters;

        return url;
    }

    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb  = new StringBuffer();

            String line = "";
            while( ( line = br.readLine())  != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.d("Exception on download", e.toString());
        }finally{
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    /** A class to download data from Google Directions URL */
    private class DownloadTask extends AsyncTask<String, Void, String> {

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
                Log.d("DownloadTask","DownloadTask : " + data);
            }catch(Exception e){
                Log.d("Background Task",e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Directions in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;

            // Traversing through all the routes
            for(int i=0;i<result.size();i++){
                points = new ArrayList<LatLng>();
                lineOptions = new PolylineOptions();

                // Fetching i-th route
                List<HashMap<String, String>> path = result.get(i);

                // Fetching all the points in i-th route
                for(int j=0;j<path.size();j++){
                    HashMap<String,String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                // Adding all the points in the route to LineOptions
                lineOptions.addAll(points);
                lineOptions.width(8);
                lineOptions.color(Color.RED);
            }

            // Drawing polyline in the Google Map for the i-th route
            if(lineOptions != null) {
                if(mPolyline != null){
                    mPolyline.remove();
                }
                mPolyline = gMap.addPolyline(lineOptions);

            }else
                Toast.makeText(getApplicationContext(),"No route is found", Toast.LENGTH_LONG).show();
        }
    }

}
