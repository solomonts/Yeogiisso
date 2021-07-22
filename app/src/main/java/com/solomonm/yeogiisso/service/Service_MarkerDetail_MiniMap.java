package com.solomonm.yeogiisso.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.static_init.Static_Method;

public class Service_MarkerDetail_MiniMap extends Service implements OnMapReadyCallback{
    public static Context Context_Service_MarkerDetail_MiniMap;
    Static_Method Static_Device_Info;

    Bundle savedInstanceState = null;
    GoogleMap gMap;

    WindowManager mWindowManager;
    WindowManager.LayoutParams mParams;
    View mView;
    ImageView iv_minimap_close;
    MapView mapView;

    private float START_X, START_Y;
    private int PREV_X, PREV_Y;
    private int MAX_X = -1, MAX_Y = -1;
    private int x, y;

    private Double ypos_gps, xpos_gps, ypos_store, xpos_store;

    private View.OnTouchListener mViewTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:                //사용자 터치 다운이면
                    if(MAX_X == -1)
                        setMaxPosition();
                    START_X = event.getRawX();                    //터치 시작 점
                    START_Y = event.getRawY();                    //터치 시작 점
                    PREV_X = mParams.x;                            //뷰의 시작 점
                    PREV_Y = mParams.y;                            //뷰의 시작 점

                    break;
                case MotionEvent.ACTION_MOVE:
                    x = (int)(event.getRawX() - START_X);    //이동한 거리
                    y = (int)(event.getRawY() - START_Y);    //이동한 거리

                    //터치해서 이동한 만큼 이동 시킨다
                    mParams.x = PREV_X - x; //좌측 상단으로 뷰 위치 변경시 + 로 변경
                    mParams.y = PREV_Y - y; //좌측 상단으로 뷰 위치 변경시 + 로 변경

                    optimizePosition();        //뷰의 위치 최적화
                    mWindowManager.updateViewLayout(mView, mParams);
                    break;

                    /*
                case MotionEvent.ACTION_UP:
                    if((int)(event.getRawX() - START_X) <= 5 && (int)(event.getRawY() - START_Y) <= 5) //뷰를 거의 이동하지 않았으면 컨트롤러 출력
                    {
                        if(ll_controller_mainframe.getVisibility() == View.VISIBLE)
                        {
                            ll_controller_mainframe.setVisibility(View.GONE);
                        }
                        else if(ll_controller_mainframe.getVisibility() == View.GONE)
                        {
                            ll_controller_mainframe.setVisibility(View.VISIBLE);
                        }
                    }
                    break;
                     */
            }

            return false;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        if(Ui_Main_Activity.Context_UiMainActivity == null)
        {
            onDestroy();
        }
        else
        {
            Context_Service_MarkerDetail_MiniMap = this;
            Static_Device_Info = (Static_Method)Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();

            // Android O 이상일 경우 Foreground 서비스를 실행
            // Notification channel 설정.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                final String strId = getString(R.string.noti_channel_id);
                final String strTitle = getString(R.string.app_name);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel channel = notificationManager.getNotificationChannel(strId);
                if (channel == null) {
                    channel = new NotificationChannel(strId, strTitle, NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(channel);
                }
                Notification notification = new NotificationCompat.Builder(this, strId).build();
                startForeground(1, notification);
            }

            int LAYOUT_FLAG;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_PHONE;
            }

            DisplayMetrics device_display = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getApplicationContext().getResources().getDisplayMetrics();
            int deviceWidth = device_display.widthPixels;
            //int deviceHeight = device_display.heightPixels;
            mParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    //deviceWidth/2,
                    //(deviceWidth/2)/2,
                    //WindowManager.LayoutParams.TYPE_PHONE,
                    LAYOUT_FLAG,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    PixelFormat.TRANSLUCENT);
            mParams.gravity = Gravity.RIGHT | Gravity.BOTTOM;

            LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mView = inflate.inflate(R.layout.ui_minimap_layout, null);
            iv_minimap_close = (ImageView) mView.findViewById(R.id.iv_minimap_close);
            mapView = (MapView) mView.findViewById(R.id.mapView);

            mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            mWindowManager.addView(mView, mParams);

            mapView.onCreate(savedInstanceState);
            mapView.onResume();
            //initializeMap();
            mapView.getMapAsync(this);

            mView.setOnTouchListener(mViewTouchListener);
            mView.setFilterTouchesWhenObscured(true);

            iv_minimap_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDestroy();
                }
            });
        }
    }

    private void setMaxPosition() {
        DisplayMetrics matrix = new DisplayMetrics();
        mWindowManager.getDefaultDisplay().getMetrics(matrix);
        MAX_X = matrix.widthPixels - mView.getWidth();
        MAX_Y = matrix.heightPixels - mView.getHeight();
    }

    private void optimizePosition() {
        if(mParams.x > MAX_X) mParams.x = MAX_X;
        if(mParams.y > MAX_Y) mParams.y = MAX_Y;
        if(mParams.x < 0) mParams.x = 0;
        if(mParams.y < 0) mParams.y = 0;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        setMaxPosition();
        optimizePosition();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(Ui_Main_Activity.Context_UiMainActivity != null)
        {
            ypos_gps = Static_Device_Info.getlat_gps();
            xpos_gps = Static_Device_Info.getlng_gps();
            ypos_store = Static_Device_Info.getstoreYpos();
            xpos_store = Static_Device_Info.getstoreXpos();
        }
        else
        {
            onDestroy();
        }

        return Service.START_NOT_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopSelf();
        if(mWindowManager != null) {
            if(mView != null) {
                mWindowManager.removeView(mView);
                mView = null;
            }
            mWindowManager = null;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        MapsInitializer.initialize(this);

        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // 위도 경도를 관리하는 객체를 생성
        LatLng position=new LatLng(ypos_gps, xpos_gps);

        // 현재 위치를 설정한다.
        CameraUpdate update1= CameraUpdateFactory.newLatLng(position);
        gMap.moveCamera(update1);

        // 확대
        CameraUpdate update2=CameraUpdateFactory.zoomTo(13);
        gMap.animateCamera(update2);

        // 현재 위치 표시
        gMap.setMyLocationEnabled(true);
        gMap.getUiSettings().setMyLocationButtonEnabled(false);

        gMap.addMarker(new MarkerOptions().position(new LatLng(ypos_store, xpos_store)).title("가게이름"));

        gMap.getUiSettings().setMyLocationButtonEnabled(false);
        gMap.getUiSettings().setMapToolbarEnabled(false);
        gMap.getUiSettings().setCompassEnabled(false);
        gMap.getUiSettings().setZoomControlsEnabled(false);
        gMap.getUiSettings().setAllGesturesEnabled(false);
        gMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        gMap.getUiSettings().setRotateGesturesEnabled(false);
        gMap.getUiSettings().setScrollGesturesEnabled(false);
        gMap.getUiSettings().setTiltGesturesEnabled(false);
        gMap.getUiSettings().setZoomGesturesEnabled(false);

    }

}
