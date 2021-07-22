package com.solomonm.yeogiisso.map.marker;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.service.Service_MarkerDetail_MiniMap;
import com.solomonm.yeogiisso.static_init.Static_Method;

public class Ui_MarkerDetail_Dialog extends Dialog {
    public static Context Context_Ui_MarkerDetail_Dialog;
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 0;
    Static_Method Static_Device_Info;

    private ImageView iv_markerdetail_minimap, iv_markerdetail_close;
    private TextView tv_markerdetail_storename, tv_markerdetail_number, tv_markerdetail_findroad, tv_markerdetail_menu, tv_markerdetail_storeinfo;
    private ViewFlipper vf_markerdetail_detailinfo_mainframe;

    String storeName, storeSnippet;
    double storeYpos, storeXpos;

    private void initView()
    {
        Static_Device_Info = (Static_Method)Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();

        this.iv_markerdetail_minimap = (ImageView)findViewById(R.id.iv_markerdetail_minimap);
        this.iv_markerdetail_close = (ImageView)findViewById(R.id.iv_markerdetail_close);
        this.tv_markerdetail_storename = (TextView)findViewById(R.id.tv_markerdetail_storename);
        this.tv_markerdetail_number = (TextView)findViewById(R.id.tv_markerdetail_number);
        this.tv_markerdetail_findroad = (TextView)findViewById(R.id.tv_markerdetail_findroad);
        this.tv_markerdetail_menu = (TextView)findViewById(R.id.tv_markerdetail_menu);
        this.tv_markerdetail_storeinfo = (TextView)findViewById(R.id.tv_markerdetail_storeinfo);
        this.vf_markerdetail_detailinfo_mainframe = (ViewFlipper)findViewById(R.id.vf_markerdetail_detailinfo_mainframe);
    }

    public Ui_MarkerDetail_Dialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_map_markerdetail_dialog);
        Context_Ui_MarkerDetail_Dialog = context;

        initView();

        iv_markerdetail_minimap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //로그인 후 이용가능하도록 수정할것

                if(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.isServiceRunningCheck("com.solomonm.yeogiisso.service.Service_MarkerDetail_MiniMap") == true)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_MarkerDetail_Dialog",
                            "minimap_restart",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_minimap_restart),
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_no),
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_restart),
                            "false"
                    );
                }
                else
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_MarkerDetail_Dialog_End();
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).checkpermission_service_system_alert_window();
                }
            }
        });

        tv_markerdetail_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_markerdetail_menu.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.white));
                tv_markerdetail_menu.setBackgroundColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.main_minor_color));
                tv_markerdetail_storeinfo.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.black));
                tv_markerdetail_storeinfo.setBackgroundColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.white));
                vf_markerdetail_detailinfo_mainframe.setDisplayedChild(0);
            }
        });

        tv_markerdetail_storeinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_markerdetail_menu.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.black));
                tv_markerdetail_menu.setBackgroundColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.white));
                tv_markerdetail_storeinfo.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.white));
                tv_markerdetail_storeinfo.setBackgroundColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.main_minor_color));
                vf_markerdetail_detailinfo_mainframe.setDisplayedChild(1);
            }
        });

        //final String[] words = new String[] {"사랑", "감사", "이해", "성공", "노력", "행운"};
        final String[] words = new String[] {"내 위치에서", "위치 직접지정"};
        tv_markerdetail_findroad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] findroad = {"내 위치에서"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Ui_Main_Activity.Context_UiMainActivity);
                new AlertDialog.Builder(Ui_Main_Activity.Context_UiMainActivity)
                        .setTitle("길찾기 방식 선택")
                        .setSingleChoiceItems(
                                words, 0, new DialogInterface.OnClickListener() {
                                    @Override public void onClick(DialogInterface dialog, int which) {
                                        //Toast.makeText(CONTEXT_MarkerDetailDialog, "words : " + words[which], Toast.LENGTH_SHORT).show();
                                        findroad[0] = words[which];
                                    }
                                }
                        )
                        //.setNeutralButton("닫기",null)
                        .setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(Ui_Main_Activity.Context_UiMainActivity, "선택한 방식 : " + findroad[0], Toast.LENGTH_SHORT).show();
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_MarkerDetail_Dialog_End();
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).GetDirections_MainMap();
                                    }
                                })
                        //.setNegativeButton("취소", null)
                        .setNeutralButton("취소",null)
                        .show();
            }
        });

        iv_markerdetail_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_MarkerDetail_Dialog_End();
            }
        });

    }

    public void setArguments(Bundle args) {
        storeName = args.getString("storeName");
        storeSnippet = args.getString("storeSnippet");
        storeYpos = args.getDouble("storeYpos");
        storeXpos = args.getDouble("storeXpos");

        Static_Device_Info.setstoreName(storeName);
        Static_Device_Info.setstoreSnippet(storeSnippet);
        Static_Device_Info.setstoreYpos(storeYpos);
        Static_Device_Info.setstoreXpos(storeXpos);

        tv_markerdetail_storename.setText(storeName);

        if(args.getString("result") != null)
        {
            String result = args.getString("result");
            String parameter_data = args.getString("parameter_data");
            switch (result)
            {
                case "cancel" :
                    if(parameter_data.equals("minimap_restart"))
                    {
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                    }
                    break;
                case "no" :
                    break;
                case "ok" :
                    if(parameter_data.equals("minimap_restart"))
                    {
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start();
                        Ui_Main_Activity.Context_UiMainActivity.stopService(new Intent(Ui_Main_Activity.Context_UiMainActivity, Service_MarkerDetail_MiniMap.class));

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Ui_Main_Activity.Context_UiMainActivity.startService(new Intent(Ui_Main_Activity.Context_UiMainActivity, Service_MarkerDetail_MiniMap.class));
                            }
                        }, 2000);
                    }
                    break;
            }
        }
    }
}
