package com.solomonm.yeogiisso.dialog.alertdialog;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.drawer.setting.profile.Ui_ChangeProfile_Pic_Activity;
import com.solomonm.yeogiisso.drawer.setting.language.Ui_DrawerSetting_Language_Dialog;
import com.solomonm.yeogiisso.drawer.setting.theme.Ui_DrawerSetting_Theme_Dialog;
import com.solomonm.yeogiisso.login.Ui_Login_Activity;
import com.solomonm.yeogiisso.login.Ui_Login_Dialog;
import com.solomonm.yeogiisso.map.marker.Ui_MarkerDetail_Dialog;
import com.solomonm.yeogiisso.service.BackgroundTask_GPS_Connect;
import com.solomonm.yeogiisso.service.BackgroundTask_SERVER_Connect;
import com.solomonm.yeogiisso.signup.Ui_Signup_Activity;
import com.solomonm.yeogiisso.signup.Ui_Signup_Dialog;
import com.solomonm.yeogiisso.static_init.Static_Function;
import com.solomonm.yeogiisso.utils.Utils;

public class Ui_AlertDialog_Activity extends AppCompatActivity {

    public static Context Context_Ui_AlertDialog_Dialog;
    Bundle args = new Bundle();
    Intent getIntentExtra;

    private LinearLayout layout_dialog_alertdialog_background, layout_dialog_alertdialog_mainframe;
    private TextView tv_alert_title, tv_cancelbtn_title, tv_nobtn_title, tv_okbtn_title;
    String where="", parameter_data="", alert_title="", cancelbtn_title="", nobtn_title="", okbtn_title="", cancel_enabled="";

    private void initView()
    {
        this.layout_dialog_alertdialog_background = (LinearLayout)findViewById(R.id.layout_dialog_alertdialog_background);
        this.layout_dialog_alertdialog_mainframe = (LinearLayout)findViewById(R.id.layout_dialog_alertdialog_mainframe);
        this.tv_alert_title = (TextView)findViewById(R.id.tv_alert_title);
        this.tv_cancelbtn_title = (TextView)findViewById(R.id.tv_cancelbtn_title);
        this.tv_nobtn_title = (TextView)findViewById(R.id.tv_nobtn_title);
        this.tv_okbtn_title = (TextView)findViewById(R.id.tv_okbtn_title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context_Ui_AlertDialog_Dialog = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_alertdialog_activity);

        getIntentExtra = getIntent();
        initView();
        setArguments();

        Utils.setStatusBarColor(this, Utils.StatusBarColorType.TRANSPARENT_STATUS_BAR);

        layout_dialog_alertdialog_mainframe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        layout_dialog_alertdialog_background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cancel_enabled.equals("false"))
                {
                    finish();
                    overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                }
            }
        });

        tv_cancelbtn_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_nobtn_title.setEnabled(false);
                tv_okbtn_title.setEnabled(false);
                args.putString("result", "cancel");
                args.putString("parameter_data", parameter_data);
                start_arg();
            }
        });

        tv_nobtn_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_cancelbtn_title.setEnabled(false);
                tv_okbtn_title.setEnabled(false);
                args.putString("result", "no");
                args.putString("parameter_data", parameter_data);
                start_arg();
            }
        });

        tv_okbtn_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_cancelbtn_title.setEnabled(false);
                tv_nobtn_title.setEnabled(false);
                args.putString("result", "ok");
                args.putString("parameter_data", parameter_data);
                start_arg();
            }
        });

    }

    public void setArguments() {
        where = getIntentExtra.getStringExtra("where");
        parameter_data = getIntentExtra.getStringExtra("parameter_data");

        alert_title = getIntentExtra.getStringExtra("alert_title");
        if(alert_title.equals("empty"))
        {
            tv_alert_title.setVisibility(View.INVISIBLE);
            tv_alert_title.setEnabled(false);
        }
        else
        {
            tv_alert_title.setText(alert_title);
        }

        cancelbtn_title = getIntentExtra.getStringExtra("cancelbtn_title");
        if(cancelbtn_title.equals("empty"))
        {
            tv_cancelbtn_title.setVisibility(View.INVISIBLE);
            tv_cancelbtn_title.setEnabled(false);
        }
        else
        {
            tv_cancelbtn_title.setText(cancelbtn_title);
        }

        nobtn_title = getIntentExtra.getStringExtra("nobtn_title");
        if(nobtn_title.equals("empty"))
        {
            tv_nobtn_title.setVisibility(View.INVISIBLE);
            tv_nobtn_title.setEnabled(false);
        }
        else
        {
            tv_nobtn_title.setText(nobtn_title);
        }

        okbtn_title = getIntentExtra.getStringExtra("okbtn_title");
        if(okbtn_title.equals("empty"))
        {
            tv_okbtn_title.setVisibility(View.INVISIBLE);
            tv_okbtn_title.setEnabled(false);
        }
        else
        {
            tv_okbtn_title.setText(okbtn_title);
        }

        cancel_enabled = getIntentExtra.getStringExtra("cancel_enabled");

        args.putString("where", where);
        args.putString("parameter_data", parameter_data);
        args.putString("alert_title", alert_title);
        args.putString("cancelbtn_title", cancelbtn_title);
        args.putString("nobtn_title", nobtn_title);
        args.putString("okbtn_title", okbtn_title);
    }

    public void start_arg()
    {
        switch (where)
        {
            case "Ui_DrawerSetting_Language_Dialog" :
                Ui_DrawerSetting_Language_Dialog ui_drawersetting_language_dialog;
                ui_drawersetting_language_dialog = new Ui_DrawerSetting_Language_Dialog(Context_Ui_AlertDialog_Dialog);
                ui_drawersetting_language_dialog.setArguments(args);
                break;
            case "Static_Function" :
                Static_Function static_function;
                static_function = new Static_Function();
                static_function.setArguments(args);
                break;
            case "Ui_DrawerSetting_Theme_Dialog" :
                Ui_DrawerSetting_Theme_Dialog ui_drawersetting_theme_dialog;
                ui_drawersetting_theme_dialog = new Ui_DrawerSetting_Theme_Dialog(Context_Ui_AlertDialog_Dialog);
                ui_drawersetting_theme_dialog.setArguments(args);
                break;
            case "Ui_Main_Activity" :
                Ui_Main_Activity ui_main_activity;
                ui_main_activity = new Ui_Main_Activity();
                ui_main_activity.setArguments(args);
                break;
            case "BackgroundTask_GPS_Connect":
                BackgroundTask_GPS_Connect backgroundTask_gps_connect;
                backgroundTask_gps_connect = new BackgroundTask_GPS_Connect();
                backgroundTask_gps_connect.setArguments(args);
                break;
            case "BackgroundTask_SERVER_Connect":
                BackgroundTask_SERVER_Connect backgroundTask_server_connect;
                backgroundTask_server_connect = new BackgroundTask_SERVER_Connect();
                backgroundTask_server_connect.setArguments(args);
                break;
            case "Ui_MarkerDetail_Dialog":
                Ui_MarkerDetail_Dialog ui_markerDetail_dialog;
                ui_markerDetail_dialog = new Ui_MarkerDetail_Dialog(Context_Ui_AlertDialog_Dialog);
                ui_markerDetail_dialog.setArguments(args);
                break;
            case "Ui_Login_Activity":
                Ui_Login_Activity ui_login_activity;
                ui_login_activity = new Ui_Login_Activity();
                ui_login_activity.setArguments(args);
                break;
            case "Ui_Signup_Activity":
                Ui_Signup_Activity ui_signup_activity;
                ui_signup_activity = new Ui_Signup_Activity();
                ui_signup_activity.setArguments(args);
                break;
            case "Ui_ChangeProfile_Activity":
                Ui_ChangeProfile_Pic_Activity ui_changeProfile_Pic_activity;
                ui_changeProfile_Pic_activity = new Ui_ChangeProfile_Pic_Activity();
                ui_changeProfile_Pic_activity.setArguments(args);
                break;
        }
    }

    public void call_AlertDialog_finish()
    {
        finish();
        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(cancel_enabled.equals("false"))
        {
            finish();
            overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
        }
    }

    @Override
    public void onBackPressed() {
        if(cancel_enabled.equals("false"))
        {
            finish();
            overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
        }
    }
}
