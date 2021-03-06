package com.solomonm.yeogiisso.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.static_init.Static_Function;
import com.solomonm.yeogiisso.static_init.Static_Method;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class BackgroundTask_SERVER_Connect  extends Service {
    public static Context Context_BackgroundTask_SERVER_Connect;
    Static_Function initFunction;
    Static_Method Static_Device_Info;

    int networkStatus;
    String server_rul;
    int server_port;
    String server_run;

    public void init()
    {
        if(Ui_Main_Activity.Context_UiMainActivity != null)
        {
            Context_BackgroundTask_SERVER_Connect = Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
            initFunction = new Static_Function();

            Static_Device_Info = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
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

        if(Ui_Main_Activity.Context_UiMainActivity != null)
        {
            server_rul = Ui_Main_Activity.Context_UiMainActivity.getResources().getString(R.string.server_info_url);
            server_port = Ui_Main_Activity.Context_UiMainActivity.getResources().getInteger(R.integer.server_info_port);
            server_run = Static_Device_Info.getcheckfunction_run();

            if("false".equals(Static_Device_Info.getcheckfunction_run()))
            {
                Static_Device_Info.setcheckfunction_run("true"); //???????????? ?????? ?????????

                //????????????????????????
                new BackgroundTask_SERVER_Connect.Server_connect().execute();

                //5?????? ??????????????????
                Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        Server_Connect_Check();
                        Static_Device_Info.setcheckfunction_run("false"); //???????????????????????? ??????
                        stopSelf(); //???????????????
                    }
                }, 5000);
            }
        }
        else
        {
            stopSelf();
        }

        return Service.START_NOT_STICKY;
        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    class Server_connect extends AsyncTask<Void, Void, String> {
        String result;

        @Override
        protected String doInBackground(Void... voids) {
            init();

            Socket soc = new Socket(); //????????????
            result = "";
            SocketAddress adr = new InetSocketAddress(server_rul, server_port); //????????? ?????? ip????????? port??????
            try
            {
                soc.connect(adr, getResources().getInteger(R.integer.server_timeout_limit)); // ?????? ??????, timeout??? 3???
                if (soc.isConnected() == true) //?????? ????????? ????????????
                {
                    result = "true";
                    Static_Device_Info.setcheckfunction_serverStatus(result);
                }
                soc.close();
            }
            catch (IOException e) //?????? ????????? ????????? ????????????
            {
                result = "false";
                Static_Device_Info.setcheckfunction_serverStatus(result);
            }

            //?????? ?????????????????????
            networkStatus = Class_NetworkStatus.getConnectivityStatus(BackgroundTask_SERVER_Connect.this);

            return null;
        }
    }

    public void Server_Connect_Check() {
        init();

        //????????? ????????????
        if ("false".equals(Static_Device_Info.getcheckfunction_serverStatus()) || Static_Device_Info.getcheckfunction_serverStatus() == null)
        {
            //????????? ???????????? ???????????? ???
            if (networkStatus != Class_NetworkStatus.TYPE_MOBILE && networkStatus != Class_NetworkStatus.TYPE_WIFI)
            {
                //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End(); //???????????? ??????
                initFunction.Ui_AlertDialog_Dialog_Start(
                        "BackgroundTask_SERVER_Connect",
                        "error_network_off",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0101_server_connect_network_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0102_server_connect_network_errorcode),
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                        "empty",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                        "true"
                );
            }
            //????????? ???????????? ?????????????????? ???
            else
            {
                //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End(); //???????????? ??????
                initFunction.Ui_AlertDialog_Dialog_Start(
                        "BackgroundTask_SERVER_Connect",
                        "error_server_off",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0201_server_connect_server_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0202_server_connect_server_errorcode),
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                        "empty",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                        "true"
                );
            }
        }
        else if("true".equals(Static_Device_Info.getcheckfunction_serverStatus()))
        {
            //?????????????????? ??? ???????????? ???????????? ????????? ??????
            if(Static_Device_Info.getcount() > 0)
            {
                //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End(); //???????????? ??????
                initFunction.Ui_AlertDialog_Dialog_Start(
                        "BackgroundTask_SERVER_Connect",
                        "retry_ok",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_connect_reconnect),
                        "empty",
                        "empty",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                        "false"
                );
                Static_Device_Info.setcount(0);
            }
            //30?????? ???????????? ????????????
            Handler handler = new Handler();
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    Static_Device_Info.setcount(0);
                    Ui_Main_Activity.Context_UiMainActivity.startService(new Intent(Ui_Main_Activity.Context_UiMainActivity, BackgroundTask_SERVER_Connect.class));
                }
            }, 30000);
        }
        else
        {
            //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End(); //???????????? ??????
            //???????????????(??????????????? ??????)
            initFunction.Ui_AlertDialog_Dialog_Start(
                    "BackgroundTask_SERVER_Connect",
                    "unusual_approach",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title),
                    "empty",
                    "empty",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                    "true"
            );
        }
    }

    public void setArguments(Bundle args) {
        init();

        String result = args.getString("result");
        String parameter_data = args.getString("parameter_data");

        switch (parameter_data)
        {
            case "error_network_off":
                switch (result)
                {
                    case "cancel" :
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ActivityCompat.finishAffinity(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity));
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).moveTaskToBack(true);
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case "no" :
                        break;
                    case "ok" :
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        Static_Device_Info.setcount(Static_Device_Info.getcount()+1);
                        //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(); //???????????? ??????
                        Ui_Main_Activity.Context_UiMainActivity.startService(new Intent(Ui_Main_Activity.Context_UiMainActivity, BackgroundTask_SERVER_Connect.class));
                        break;
                }
                break;
            case "error_server_off":
                switch (result)
                {
                    case "cancel" :
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ActivityCompat.finishAffinity(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity));
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).moveTaskToBack(true);
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                    case "no" :
                        break;
                    case "ok" :
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        Static_Device_Info.setcount(Static_Device_Info.getcount()+1);
                        //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(); //???????????? ??????
                        Ui_Main_Activity.Context_UiMainActivity.startService(new Intent(Ui_Main_Activity.Context_UiMainActivity, BackgroundTask_SERVER_Connect.class));
                        break;
                }
                break;
            case "retry_ok":
                switch (result)
                {
                    case "cancel" :
                        break;
                    case "no" :
                        break;
                    case "ok" :
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        Static_Device_Info.setcount(0);
                        break;
                }
                break;
            case "unusual_approach":
                switch (result)
                {
                    case "cancel" :
                        break;
                    case "no" :
                        break;
                    case "ok" :
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ActivityCompat.finishAffinity(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity));
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).moveTaskToBack(true);
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).finish();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        break;
                }
                break;
        }
    }

}
