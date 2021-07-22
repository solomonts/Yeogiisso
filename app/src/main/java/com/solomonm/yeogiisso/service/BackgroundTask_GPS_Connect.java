package com.solomonm.yeogiisso.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.static_init.Static_Function;
import com.solomonm.yeogiisso.static_init.Static_Method;
import com.solomonm.yeogiisso.static_init.Static_initView;

public class BackgroundTask_GPS_Connect extends Service {
    public static Context Context_BackgroundTask_GPS_Connect;
    Static_Function initFunction;
    Static_initView initView;
    Static_Method Static_Device_Info;

    LocationManager mLocMan;

    public void init()
    {
        if(Ui_Main_Activity.Context_UiMainActivity != null)
        {
            Context_BackgroundTask_GPS_Connect = getApplicationContext();
            initFunction = new Static_Function();
            initView = new Static_initView();
            Static_Device_Info = (Static_Method)Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();

            mLocMan = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Ui_Main_Activity.Context_UiMainActivity != null)
                {
                    if(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.pref.getString("AppDefaultSetting_Permission_GPS", "false").equals("true"))
                    {
                        if(!mLocMan.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                            Static_Device_Info.setcheckfunction_gps("false");
                            //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End(); //로딩화면 종료
                            initFunction.Ui_AlertDialog_Dialog_Start(
                                    "BackgroundTask_GPS_Connect",
                                    "gps_connect_gpsoff",
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_gps_off),
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alertcancelbtn_gps_off),
                                    "empty",
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alertokbtn_gps_off),
                                    "true"
                            );
                        }
                        else
                        {
                            Static_Device_Info.setcheckfunction_gps("true");
                            //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End(); //로딩화면 종료


                            //실시간GPS 켜져있으면 GPS위치 갱신
                            if(((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).initView.tv_btn_bottommenu3_realtime_gps.getVisibility() == View.GONE && ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).initView.iv_btn_bottommenu3_realtime_gps.getBackground().getConstantState().equals(ContextCompat.getDrawable(Ui_Main_Activity.Context_UiMainActivity, R.drawable.ic_bottommenu_realtime_gps_on).getConstantState()))
                            {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getMyLocation();
                            }

                            stopSelf();
                            Ui_Main_Activity.Context_UiMainActivity.startService(new Intent(Ui_Main_Activity.Context_UiMainActivity, BackgroundTask_GPS_Connect.class));
                        }
                    }
                }
                else
                {
                    stopSelf();
                }
            }
        }, 5000);

        return Service.START_NOT_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void setArguments(Bundle args) {
        String result = args.getString("result");
        String parameter_data = args.getString("parameter_data");

        switch (parameter_data)
        {
            case "gps_connect_gpsoff":
                switch (result)
                {
                    case "cancel" :
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        stopSelf();
                        break;
                    case "no" :
                        break;
                    case "ok" :
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        // GPS 설정 화면으로 이동
                        //Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        //Ui_Main_Activity.Context_UiMainActivity.startActivity(gpsOptionsIntent);
                        stopSelf();
                        //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(); //로딩화면 출력
                        Ui_Main_Activity.Context_UiMainActivity.startService(new Intent(Ui_Main_Activity.Context_UiMainActivity, BackgroundTask_GPS_Connect.class));
                        break;
                }
                break;
            case "gps_connect_gpson":
                break;
        }
    }

}
