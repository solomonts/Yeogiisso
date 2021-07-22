package com.solomonm.yeogiisso.static_init;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.drawer.option.search.Ui_DrawerOption_Search_Dialog;
import com.solomonm.yeogiisso.drawer.setting.language.Ui_DrawerSetting_Language_Dialog;
import com.solomonm.yeogiisso.drawer.setting.theme.Ui_DrawerSetting_Theme_Dialog;
import com.solomonm.yeogiisso.loading.Ui_FullScreenTutorial_Activity;
import com.solomonm.yeogiisso.loading.Ui_FullScreenWait_Dialog;
import com.solomonm.yeogiisso.login.Ui_Login_Dialog;
import com.solomonm.yeogiisso.map.marker.Ui_MarkerDetail_Dialog;
import com.solomonm.yeogiisso.signup.Ui_Signup_Dialog;

import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Static_Function {
    Static_Function static_function;
    public static Context Context_Static_Function;

    Ui_DrawerSetting_Language_Dialog ui_drawersetting_language_dialog;
    Ui_DrawerSetting_Theme_Dialog ui_drawersetting_theme_dialog;
    Ui_FullScreenWait_Dialog ui_fullscreenwait_dialog;
    Ui_AlertDialog_Activity ui_alertDialog_activity;
    Ui_MarkerDetail_Dialog ui_markerDetail_dialog;
    Ui_Login_Dialog ui_login_dialog;
    Ui_Signup_Dialog ui_signup_dialog;
    Ui_DrawerOption_Search_Dialog ui_drawerOption_search_dialog;
    public Static_Method initMethod;
    public Static_initView initView;
    public SharedPreferences pref;
    public Static_Method Static_Device_Info;

    Timer timer = new Timer();
    TimerTask tt;

    public void init()
    {
        static_function = this;
        Context_Static_Function = Ui_Main_Activity.Context_UiMainActivity;
        initMethod = new Static_Method();
        initView = new Static_initView();
        ui_alertDialog_activity = new Ui_AlertDialog_Activity();
        ui_markerDetail_dialog = new Ui_MarkerDetail_Dialog(Ui_Main_Activity.Context_UiMainActivity);
        ui_login_dialog = new Ui_Login_Dialog(Ui_Main_Activity.Context_UiMainActivity);
        pref = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getSharedPreferences("pref", Context.MODE_PRIVATE);
        Static_Device_Info = (Static_Method)Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
    }

    //Drawer_설정_언어변경_다이얼로그_시작
    public void Ui_DrawerSetting_Language_Dialog_Start()
    {
        if(ui_drawersetting_language_dialog != null && ui_drawersetting_language_dialog.isShowing())
        {
        }
        else
        {
            ui_drawersetting_language_dialog = new Ui_DrawerSetting_Language_Dialog(Ui_Main_Activity.Context_UiMainActivity);
            ui_drawersetting_language_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ui_drawersetting_language_dialog.show();
        }
    }
    //Drawer_설정_언어변경_다이얼로그_종료
    public void Ui_DrawerSetting_Language_Dialog_End()
    {
        if(ui_drawersetting_language_dialog != null && ui_drawersetting_language_dialog.isShowing())
        {
            ui_drawersetting_language_dialog.dismiss();
        }
    }

    //Drawer_설정_테마변경_다이얼로그_시작
    public void Ui_DrawerSetting_Theme_Dialog_Start()
    {
        if(ui_drawersetting_theme_dialog != null && ui_drawersetting_theme_dialog.isShowing())
        {
        }
        else
        {
            ui_drawersetting_theme_dialog = new Ui_DrawerSetting_Theme_Dialog(Ui_Main_Activity.Context_UiMainActivity);
            ui_drawersetting_theme_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ui_drawersetting_theme_dialog.show();
        }
    }
    //Drawer_설정_테마변경_다이얼로그_종료
    public void Ui_DrawerSetting_Theme_Dialog_End()
    {
        if(ui_drawersetting_theme_dialog != null && ui_drawersetting_theme_dialog.isShowing())
        {
            ui_drawersetting_theme_dialog.dismiss();
        }
    }

    //Loading_FullScreen_다이얼로그_시작
    public void Ui_FullScreenWait_Dialog_Start(Context context)
    {
        if(ui_fullscreenwait_dialog != null && ui_fullscreenwait_dialog.isShowing())
        {
        }
        else
        {
            ui_fullscreenwait_dialog = new Ui_FullScreenWait_Dialog(context);
            ui_fullscreenwait_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            ui_fullscreenwait_dialog.setCancelable(false);
            ui_fullscreenwait_dialog.show();
        }
    }
    //Loading_FullScreen_다이얼로그_종료
    public void Ui_FullScreenWait_Dialog_End()
    {
        if(ui_fullscreenwait_dialog != null && ui_fullscreenwait_dialog.isShowing())
        {
            ui_fullscreenwait_dialog.dismiss();
        }
    }

    //Marker_MarkerDetail_다이얼로그_시작
    public void Ui_MarkerDetail_Dialog_Start(String storeName, String storeSnippet, Double storeYpos, Double storeXpos)
    {
        if(ui_markerDetail_dialog != null && ui_markerDetail_dialog.isShowing())
        {
        }
        else
        {
            ui_markerDetail_dialog = new Ui_MarkerDetail_Dialog(Ui_Main_Activity.Context_UiMainActivity);
            ui_markerDetail_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //ui_markerDetail_dialog.setCancelable(false);
            Bundle args = new Bundle();
            args.putString("storeName", storeName);
            args.putString("storeSnippet", storeSnippet);
            args.putDouble("storeYpos", storeYpos);
            args.putDouble("storeXpos", storeXpos);
            ui_markerDetail_dialog.setArguments(args);
            ui_markerDetail_dialog.show();
        }
    }
    //Marker_MarkerDetail_다이얼로그_종료
    public void Ui_MarkerDetail_Dialog_End()
    {
        if(ui_markerDetail_dialog != null && ui_markerDetail_dialog.isShowing())
        {
            ui_markerDetail_dialog.dismiss();
        }
    }

    //Drawer_Login_다이얼로그_시작
    public void Ui_Login_Dialog_Start()
    {
        if(ui_login_dialog != null && ui_login_dialog.isShowing())
        {
        }
        else
        {
            ui_login_dialog = new Ui_Login_Dialog(Ui_Main_Activity.Context_UiMainActivity);
            ui_login_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //ui_markerDetail_dialog.setCancelable(false);
            ui_login_dialog.show();
        }
    }
    //Drawer_Login_다이얼로그_종료
    public void Ui_Login_Dialog_End()
    {
        if(ui_login_dialog != null && ui_login_dialog.isShowing())
        {
            ui_login_dialog.dismiss();
        }
    }

    //Drawer_Signup_다이얼로그_시작
    public void Ui_Signup_Dialog_Start()
    {
        if(ui_signup_dialog != null && ui_signup_dialog.isShowing())
        {
        }
        else
        {
            ui_signup_dialog = new Ui_Signup_Dialog(Ui_Main_Activity.Context_UiMainActivity);
            ui_signup_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //ui_markerDetail_dialog.setCancelable(false);
            ui_signup_dialog.show();
        }
    }
    //Drawer_Signup_다이얼로그_종료
    public void Ui_Signup_Dialog_End()
    {
        if(ui_signup_dialog != null && ui_signup_dialog.isShowing())
        {
            ui_signup_dialog.dismiss();
        }
    }

    //Drawer_옵션_지역검색_다이얼로그_시작
    public void Ui_DrawerOption_Search_Dialog_Start(String Language, String Class, String Parameter, String Parameter_Code)
    {
        if(ui_drawerOption_search_dialog != null && ui_drawerOption_search_dialog.isShowing())
        {
        }
        else
        {
            ui_drawerOption_search_dialog = new Ui_DrawerOption_Search_Dialog(Ui_Main_Activity.Context_UiMainActivity);
            ui_drawerOption_search_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //ui_markerDetail_dialog.setCancelable(false);

            Bundle args = new Bundle();
            args.putString("dialog_setting_language", Language);
            args.putString("dialog_setting_class", Class);
            args.putString("dialog_setting_parameter", Parameter);
            args.putString("dialog_setting_parameter_code", Parameter_Code);
            ui_drawerOption_search_dialog.setArguments(args);

            ui_drawerOption_search_dialog.show();
        }
    }
    //Drawer_옵션_지역검색_다이얼로그_종료
    public void Ui_DrawerOption_Search_Dialog_End()
    {
        if(ui_drawerOption_search_dialog != null && ui_drawerOption_search_dialog.isShowing())
        {
            ui_drawerOption_search_dialog.dismiss();
        }
    }

    //AlertDialog_알림_다이얼로그_시작
    public void Ui_AlertDialog_Dialog_Start(String where, String parameter_data, String alert_title, String cancelbtn_title, String nobtn_title, String okbtn_title, String cancel_enabled)
    {
        Intent start_Ui_AlertDialog_Activity = new Intent(Static_Function.Context_Static_Function, Ui_AlertDialog_Activity.class);

        start_Ui_AlertDialog_Activity.putExtra("where", where);
        start_Ui_AlertDialog_Activity.putExtra("parameter_data", parameter_data);
        start_Ui_AlertDialog_Activity.putExtra("alert_title", alert_title);
        start_Ui_AlertDialog_Activity.putExtra("cancelbtn_title", cancelbtn_title);
        start_Ui_AlertDialog_Activity.putExtra("nobtn_title", nobtn_title);
        start_Ui_AlertDialog_Activity.putExtra("okbtn_title", okbtn_title);
        start_Ui_AlertDialog_Activity.putExtra("cancel_enabled", cancel_enabled);

        Static_Function.Context_Static_Function.startActivity(start_Ui_AlertDialog_Activity);
    }
    //AlertDialog_알림_다이얼로그_종료
    public void Ui_AlertDialog_Dialog_End()
    {
        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
    }

    //초기실행언어 설정
    public void AppInitialRun_Language()
    {
        init();

        String InitialRun_Language = pref.getString("AppDefaultSetting_Language", "none");
        if(InitialRun_Language.equals("none"))
        {
            if(Locale.getDefault().getLanguage().equals("ko") || Locale.getDefault().getLanguage().equals("en") || Locale.getDefault().getLanguage().equals("ja") || Locale.getDefault().getLanguage().equals("zh"))
            {
                InitialRun_Language = Locale.getDefault().getLanguage();
            }
            else
            {
                InitialRun_Language = "en";
            }
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("AppDefaultSetting_Language", InitialRun_Language);
            editor.commit();
        }

        Locale mLocale = new Locale(pref.getString("AppDefaultSetting_Language", "en"));
        Locale.setDefault(mLocale);
        Configuration config = new Configuration();
        config.locale = mLocale;
        Ui_Main_Activity.Context_UiMainActivity.getResources().updateConfiguration(config, Ui_Main_Activity.Context_UiMainActivity.getResources().getDisplayMetrics());
    }

    //초기실행테마 설정
    public void AppInitialRun_Theme()
    {
        init();
        SharedPreferences.Editor editor = pref.edit();

        int currentMode = Ui_Main_Activity.Context_UiMainActivity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (pref.getInt("AppDefaultSetting_Theme", currentMode))
        {
            case Configuration.UI_MODE_NIGHT_NO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putInt("AppDefaultSetting_Theme", Configuration.UI_MODE_NIGHT_NO);
                editor.commit();
                return;
            case Configuration.UI_MODE_NIGHT_YES:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putInt("AppDefaultSetting_Theme", Configuration.UI_MODE_NIGHT_YES);
                editor.commit();
                return;
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putInt("AppDefaultSetting_Theme", Configuration.UI_MODE_NIGHT_UNDEFINED);
                editor.commit();
                return;
            default:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                    editor.putInt("AppDefaultSetting_Theme", Configuration.UI_MODE_NIGHT_NO);
                    editor.commit();
                    return;
                }
                else
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                    editor.putInt("AppDefaultSetting_Theme", Configuration.UI_MODE_NIGHT_YES);
                    editor.commit();
                    return;
                }
        }
    }

    public void AppInitialRun_DeviceInfo()
    {
        init();

        int resId = Ui_Main_Activity.Context_UiMainActivity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if(resId > 0)
        {
            initMethod.statusBarHeight = Ui_Main_Activity.Context_UiMainActivity.getResources().getDimensionPixelSize(resId);
        }
        DisplayMetrics device_display = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getApplicationContext().getResources().getDisplayMetrics();
        initMethod.deviceWidth = device_display.widthPixels;
        initMethod.deviceHeight = device_display.heightPixels-initMethod.statusBarHeight;

        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("AppDefaultSetting_DeviceSize_Width", initMethod.deviceWidth);
        editor.putInt("AppDefaultSetting_DeviceSize_Height", initMethod.deviceHeight);
        editor.putString("AppDefaultSetting_Permission_GPS", "false"); //GPS권한상태 저장
        editor.commit();
    }

    public void AppInitialRun_MapSetting()
    {
        init();
        SharedPreferences.Editor editor = pref.edit();

        int checkMapDistLevel = pref.getInt("AppDefaultSetting_Map_Dist_Level", 0);
        String checkMapMode = pref.getString("AppDefaultSetting_Map_Mode", "none");
        int checkMapThickness = pref.getInt("AppDefaultSetting_Map_Thickness", 0);
        float checkMapZoomLevel = pref.getFloat("AppDefaultSetting_Map_Zoom_Level", 0);
        if(checkMapDistLevel == 0)
        {
            editor.putInt("AppDefaultSetting_Map_Dist_Level", 1000); //단위 : 미터
            editor.commit();
            Static_Device_Info.setmap_circle(pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000));
        }
        if(checkMapMode.equals("none"))
        {
            editor.putString("AppDefaultSetting_Map_Mode", "MAP_TYPE_NORMAL");
            editor.commit();
            Static_Device_Info.setmap_mode(pref.getString("AppDefaultSetting_Map_Mode", "MAP_TYPE_NORMAL"));
        }
        if(checkMapThickness == 0)
        {
            editor.putInt("AppDefaultSetting_Map_Thickness", 2);
            editor.commit();
            Static_Device_Info.setmap_thickness(pref.getInt("AppDefaultSetting_Map_Thickness", 2));
        }
        if(checkMapZoomLevel == 0)
        {
            editor.putFloat("AppDefaultSetting_Map_Zoom_Level", 14.f);
            editor.commit();
            Static_Device_Info.setmap_zoom(pref.getFloat("AppDefaultSetting_Map_Zoom_Level", 14.f));
        }
    }

    public void AppInitialRun_DrawerOptionSetting()
    {
        //드로어옵션메뉴 - 검색조건 (시작시 메뉴펼치기)
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.layout_draweroption_searchmenu_call_mainframe.callOnClick();

        //드로어옵션메뉴 - 지도설정 - 검색반경 (텍스트값 설정)
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.seekbar_draweroption_mapsetting_dist.setEnabled(false); //스키바 비활성화
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_mapsetting_dist_applydist.setText("("+pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000)+"M)");
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_mapsetting_dist_hopedist.setText(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.drawermenu_option_mapsetting_radius)+" : "+pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000)+"M");
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.seekbar_draweroption_mapsetting_dist.setMax(5000); //최대 5KM
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.seekbar_draweroption_mapsetting_dist.setProgress(pref.getInt("AppDefaultSetting_Map_Dist_Level", 1000));

        //드로어옵션메뉴 - 지도설정 - 지도모드
        String map_mode = pref.getString("AppDefaultSetting_Map_Mode", "MAP_TYPE_NORMAL");
        switch (map_mode)
        {
            case "MAP_TYPE_NORMAL":
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.radiobutton_draweroption_mapsetting_mapmode_rb1.setChecked(true);
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.radiobutton_draweroption_mapsetting_mapmode_rb1.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.main_concept_color));
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_mapsetting_mapmode_applymapmode.setText("("+Ui_Main_Activity.Context_UiMainActivity.getString(R.string.drawermenu_option_mapsetting_mapmode_normal)+")");
                break;
            case "MAP_TYPE_TERRAIN":
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.radiobutton_draweroption_mapsetting_mapmode_rb2.setChecked(true);
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.radiobutton_draweroption_mapsetting_mapmode_rb2.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.main_concept_color));
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_mapsetting_mapmode_applymapmode.setText("("+Ui_Main_Activity.Context_UiMainActivity.getString(R.string.drawermenu_option_mapsetting_mapmode_terrain)+")");
                break;
            case "MAP_TYPE_HYBRID":
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.radiobutton_draweroption_mapsetting_mapmode_rb3.setChecked(true);
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.radiobutton_draweroption_mapsetting_mapmode_rb3.setTextColor(ContextCompat.getColor(Ui_Main_Activity.Context_UiMainActivity, R.color.main_concept_color));
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_mapsetting_mapmode_applymapmode.setText("("+Ui_Main_Activity.Context_UiMainActivity.getString(R.string.drawermenu_option_mapsetting_mapmode_hybrid)+")");
                break;
        }

        //드로어옵션메뉴 - 지도설정 - 기타설정(원 두께)
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_mapsetting_etc_thickness.setText("");
        int thickness_level = pref.getInt("AppDefaultSetting_Map_Thickness", 2);
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_mapsetting_etc_thickness.setText(thickness_level+"");

        //드로어옵션메뉴 - 지도설정 - 기타설정(줌 레벨)
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_mapsetting_etc_zoom.setText("");
        String zoom_level = String.valueOf(pref.getFloat("AppDefaultSetting_Map_Zoom_Level", 14.f));
        zoom_level = zoom_level.substring(0, zoom_level.length()-2);
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_mapsetting_etc_zoom.setText(zoom_level+".f");

        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initView.tv_draweroption_mapsetting_etc_applyetc.setText("("+Ui_Main_Activity.Context_UiMainActivity.getString(R.string.drawermenu_option_mapsetting_etc_thickness)+"-"+thickness_level+", "+Ui_Main_Activity.Context_UiMainActivity.getString(R.string.drawermenu_option_mapsetting_etc_zoom_level)+"-"+zoom_level+".f)");
    }

    public void onChangeLanguage(String choice_language)
    {
        init();

        String checkLanguage = pref.getString("AppDefaultSetting_Language", "en");
        if(choice_language.equals(checkLanguage)) //기존언어랑 같으면
        {
            Ui_FullScreenWait_Dialog_End();
            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Language_Dialog_End();

            //에러메세지(비정상적인 접근)
            Ui_AlertDialog_Dialog_Start(
                    "Static_Function",
                    "finish",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                    "empty",
                    "empty",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_finish_1MSG0102_finish_finish),
                    "true"
            );
        }
        else
        {
            Ui_FullScreenWait_Dialog_End();
            if(choice_language.equals("ko") || choice_language.equals("en") || choice_language.equals("ja") || choice_language.equals("zh"))
            {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("AppDefaultSetting_Language", choice_language);
                editor.putString("Action_Type", "ChangeLanguage"); //알림 메시지확인 (변경사항 발생시)
                editor.putString("Result", "OK"); //알림 메시지확인 (변경사항 발생시)
                editor.commit();

                Locale mLocale = new Locale(choice_language);
                Locale.setDefault(mLocale);
                Configuration config = new Configuration();
                config.locale = mLocale;
                Ui_Main_Activity.Context_UiMainActivity.getResources().updateConfiguration(config, Ui_Main_Activity.Context_UiMainActivity.getResources().getDisplayMetrics());

                //Ui_FullScreenWait_Dialog_End();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Language_Dialog_End();

                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).onAppRestart("change_language");
            }
            else
            {
                //Ui_FullScreenWait_Dialog_End();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Language_Dialog_End();

                //에러메세지(지원하지않는 언어)
                Ui_AlertDialog_Dialog_Start(
                        "Static_Function",
                        "finish",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0201_language_change_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0203_language_change_errorcode),
                        "empty",
                        "empty",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0202_language_change_ok),
                        "true"
                );
            }
        }
    }

    public void onChangeTheme(String choice_theme)
    {
        init();

        int currentMode = Ui_Main_Activity.Context_UiMainActivity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        int checkTheme = pref.getInt("AppDefaultSetting_Theme", currentMode);
        if(choice_theme.equals(checkTheme+"")) //기존테마랑 같으면
        {
            Ui_FullScreenWait_Dialog_End();
            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Theme_Dialog_End();

            //에러메세지(비정상적인 접근)
            Ui_AlertDialog_Dialog_Start(
                    "Static_Function",
                    "finish",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                    "empty",
                    "empty",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_finish_1MSG0102_finish_finish),
                    "true"
            );
        }
        else
        {
            Ui_FullScreenWait_Dialog_End();

            if(choice_theme.equals(Configuration.UI_MODE_NIGHT_NO+""))
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else if(choice_theme.equals(Configuration.UI_MODE_NIGHT_YES+""))
            {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            else
            {
                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY)
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else if(choice_theme.equals(Configuration.UI_MODE_NIGHT_UNDEFINED+""))
                {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                else
                {
                    //Ui_FullScreenWait_Dialog_End();
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Theme_Dialog_End();

                    //에러메세지(비정상적인 접근)
                    Ui_AlertDialog_Dialog_Start(
                            "Static_Function",
                            "finish",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_finish_1MSG0102_finish_finish),
                            "true"
                    );
                }
            }

            if(choice_theme.equals(Configuration.UI_MODE_NIGHT_NO+"") || choice_theme.equals(Configuration.UI_MODE_NIGHT_YES+"") || choice_theme.equals(Configuration.UI_MODE_NIGHT_UNDEFINED+""))
            {
                int insert_theme_val = 0;
                switch (choice_theme)
                {
                    case "16" :
                        insert_theme_val = Configuration.UI_MODE_NIGHT_NO;
                        break;
                    case "32" :
                        insert_theme_val = Configuration.UI_MODE_NIGHT_YES;
                        break;
                    default :
                        insert_theme_val = Configuration.UI_MODE_NIGHT_UNDEFINED;
                        break;
                }

                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("AppDefaultSetting_Theme", insert_theme_val);
                editor.putString("Action_Type", "ChangeTheme"); //알림 메시지확인 (변경사항 발생시)
                editor.putString("Result", "OK"); //알림 메시지확인 (변경사항 발생시)
                editor.commit();

                //Ui_FullScreenWait_Dialog_End();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Theme_Dialog_End();

                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).onAppRestart("change_language");
            }
            else
            {
                //Ui_FullScreenWait_Dialog_End();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Theme_Dialog_End();

                //에러메세지(비정상적인 접근)
                Ui_AlertDialog_Dialog_Start(
                        "Static_Function",
                        "finish",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                        "empty",
                        "empty",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_finish_1MSG0102_finish_finish),
                        "true"
                );
            }
        }
    }

    public void onCheck_Message()
    {
        init();

        String checkMessage_Action_Type = pref.getString("Action_Type", "empty");
        String checkMessage_Result = pref.getString("Result", "empty");
        if(!checkMessage_Action_Type.equals("empty"))
        {
            switch (checkMessage_Action_Type)
            {
                case "ChangeLanguage" :
                    Ui_AlertDialog_Dialog_Start(
                            "Ui_Main_Activity",
                            "ChangeLanguage",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_settings_language_change_finish)+" ("+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_settings_language_change)+")",
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "false"
                    );
                    break;
                case "ChangeTheme" :
                    Ui_AlertDialog_Dialog_Start(
                            "Ui_Main_Activity",
                            "ChangeTheme",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_settings_mode_change_finish)+" ("+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_settings_mode_change)+")",
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "false"
                    );
                    break;
            }
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("Action_Type", "empty"); //알림 메시지확인 (변경사항 발생시)
            editor.putString("Result", "empty"); //알림 메시지확인 (변경사항 발생시)
            editor.commit();
        }
    }

    public void onRestartLanguage()
    {
        init();

        Locale mLocale = new Locale(pref.getString("AppDefaultSetting_Language", "en"));
        Locale.setDefault(mLocale);
        Configuration config = new Configuration();
        config.locale = mLocale;
        Ui_Main_Activity.Context_UiMainActivity.getResources().updateConfiguration(config, Ui_Main_Activity.Context_UiMainActivity.getResources().getDisplayMetrics());
    }

    public void onChangeConfiguration(Configuration newConfig)
    {
        init();

        if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            //다이얼로그 실행중일때_설정_언어변경 다이얼로그
            if(ui_drawersetting_language_dialog != null && ui_drawersetting_language_dialog.isShowing())
            {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Language_Dialog_End();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Language_Dialog_Start();
            }
            //다이얼로그 실행중일때_설정_테마변경 다이얼로그
            if(ui_drawersetting_theme_dialog != null && ui_drawersetting_theme_dialog.isShowing())
            {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Theme_Dialog_End();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Theme_Dialog_Start();
            }
            //다이얼로그 실행중일때_옵션_지역검색 다이얼로그
            if(ui_drawerOption_search_dialog != null && ui_drawerOption_search_dialog.isShowing())
            {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerOption_Search_Dialog_End();
            }
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //다이얼로그 실행중일때_설정_언어변경 다이얼로그
            if(ui_drawersetting_language_dialog != null && ui_drawersetting_language_dialog.isShowing())
            {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Language_Dialog_End();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Language_Dialog_Start();
            }
            //다이얼로그 실행중일때_설정_테마변경 다이얼로그
            if(ui_drawersetting_theme_dialog != null && ui_drawersetting_theme_dialog.isShowing())
            {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Theme_Dialog_End();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Theme_Dialog_Start();
            }
            //다이얼로그 실행중일때_옵션_지역검색 다이얼로그
            if(ui_drawerOption_search_dialog != null && ui_drawerOption_search_dialog.isShowing())
            {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerOption_Search_Dialog_End();
            }
        }
        else
        {
            //에러메세지(비정상적인 접근)
            Ui_AlertDialog_Dialog_Start(
                    "Static_Function",
                    "finish",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                    "empty",
                    "empty",
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_finish_1MSG0102_finish_finish),
                    "true"
            );
        }

        AppInitialRun_DeviceInfo();
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).onResizeBottomLayout();
    }

    public void onFreeze(LinearLayout linearLayout_name, Button button_name, TextView textview_name, ImageView imageview_name, CardView cardview_name, int sec)
    {
        init();

        if(linearLayout_name != null) { linearLayout_name.setEnabled(false); }
        if(button_name != null) { button_name.setEnabled(false); }
        if(textview_name != null) { textview_name.setEnabled(false); }
        if(imageview_name != null) { imageview_name.setEnabled(false); }
        if(cardview_name != null) { cardview_name.setEnabled(false); }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(linearLayout_name != null) { linearLayout_name.setEnabled(true); }
                if(button_name != null) { button_name.setEnabled(true); }
                if(textview_name != null) { textview_name.setEnabled(true); }
                if(imageview_name != null) { imageview_name.setEnabled(true); }
                if(cardview_name != null) { cardview_name.setEnabled(true); }
            }
        }, sec);
    }

    public void onChangeBottomMenuSize(String action, CardView cardView1, CardView cardView2, CardView cardView3, CardView TargetCardView)
    {
        init();
        Timer_start(action, cardView1, cardView2, cardView3, TargetCardView);
    }
    public void Timer_start(String action, CardView cardView1, CardView cardView2, CardView cardView3, CardView TargetCardView)
    {
        Timer_stop();
        tt = timerTaskMaker(action, cardView1, cardView2, cardView3, TargetCardView);
        timer.schedule(tt, 0, 10);
    }
    public TimerTask timerTaskMaker(String action, CardView cardView1, CardView cardView2, CardView cardView3, CardView TargetCardView)
    {
        Static_Device_Info.setbottommenu1_width(initView.cardview_btn_call_bottommenu.getLayoutParams().width);
        Static_Device_Info.setbottommenu2_width(initView.cardview_btn_call_bottommenu.getLayoutParams().width);
        Static_Device_Info.setbottommenu3_width(initView.cardview_btn_call_bottommenu.getLayoutParams().width);
        //bottommenu1_width = cardView2.getLayoutParams().width;
        //bottommenu2_width = cardView2.getLayoutParams().width;
        //bottommenu3_width = cardView3.getLayoutParams().width;

        cardView1.setEnabled(false);
        cardView2.setEnabled(false);
        cardView3.setEnabled(false);
        initView.cardview_btn_call_bottommenu.setEnabled(false);

        TimerTask tempTask = new TimerTask() {
            @Override
            public void run() {

                switch (action)
                {
                    case "scaleUp":
                        if(TargetCardView.equals(cardView1))
                        {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(cardView1.getLayoutParams().width >= pref.getInt("AppDefaultSetting_DeviceSize_Width", 0)/5)
                                    {
                                        tt.cancel();
                                        cardView1.setEnabled(true);
                                        cardView2.setEnabled(true);
                                        cardView3.setEnabled(true);
                                        initView.cardview_btn_call_bottommenu.setEnabled(true);
                                        initView.tv_btn_bottommenu1_setmylocation.setVisibility(View.VISIBLE);
                                        initView.iv_btn_bottommenu1_setmylocation.setVisibility(View.GONE);
                                        initView.tv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.VISIBLE);
                                        initView.tv_btn_bottommenu3_realtime_gps.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu3_realtime_gps.setVisibility(View.VISIBLE);
                                        cardView1.setLayoutParams(new LinearLayout.LayoutParams(pref.getInt("AppDefaultSetting_DeviceSize_Width", 0)/5, cardView1.getHeight()));
                                    }
                                    else if(cardView1.getLayoutParams().width < pref.getInt("AppDefaultSetting_DeviceSize_Width", 0)/5)
                                    {
                                        cardView1.setLayoutParams(new LinearLayout.LayoutParams(cardView1.getWidth()+20, cardView1.getHeight()));
                                    }

                                    if(cardView2.getLayoutParams().width <= Static_Device_Info.getbottommenu1_width())
                                    {
                                        cardView2.setLayoutParams(new LinearLayout.LayoutParams(Static_Device_Info.getbottommenu1_width(), cardView2.getHeight()));
                                    }
                                    else if(cardView2.getLayoutParams().width > Static_Device_Info.getbottommenu1_width())
                                    {
                                        cardView2.setLayoutParams(new LinearLayout.LayoutParams(cardView2.getWidth()-20, cardView2.getHeight()));
                                    }

                                    if(cardView3.getLayoutParams().width <= Static_Device_Info.getbottommenu1_width())
                                    {
                                        cardView3.setLayoutParams(new LinearLayout.LayoutParams(Static_Device_Info.getbottommenu1_width(), cardView3.getHeight()));
                                    }
                                    else if(cardView3.getLayoutParams().width > Static_Device_Info.getbottommenu1_width())
                                    {
                                        cardView3.setLayoutParams(new LinearLayout.LayoutParams(cardView3.getWidth()-20, cardView3.getHeight()));
                                    }
                                }
                            });
                        }
                        else if(TargetCardView.equals(cardView2))
                        {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(cardView2.getLayoutParams().width >= pref.getInt("AppDefaultSetting_DeviceSize_Width", 0)/5)
                                    {
                                        tt.cancel();
                                        cardView1.setEnabled(true);
                                        cardView2.setEnabled(true);
                                        cardView3.setEnabled(true);
                                        initView.cardview_btn_call_bottommenu.setEnabled(true);
                                        initView.tv_btn_bottommenu1_setmylocation.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu1_setmylocation.setVisibility(View.VISIBLE);
                                        initView.tv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.VISIBLE);
                                        initView.iv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.GONE);
                                        initView.tv_btn_bottommenu3_realtime_gps.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu3_realtime_gps.setVisibility(View.VISIBLE);
                                        cardView2.setLayoutParams(new LinearLayout.LayoutParams(pref.getInt("AppDefaultSetting_DeviceSize_Width", 0)/5, cardView2.getHeight()));
                                    }
                                    else if(cardView2.getLayoutParams().width < pref.getInt("AppDefaultSetting_DeviceSize_Width", 0)/5)
                                    {
                                        cardView2.setLayoutParams(new LinearLayout.LayoutParams(cardView2.getWidth()+20, cardView2.getHeight()));
                                    }

                                    if(cardView1.getLayoutParams().width <= Static_Device_Info.getbottommenu2_width())
                                    {
                                        cardView1.setLayoutParams(new LinearLayout.LayoutParams(Static_Device_Info.getbottommenu2_width(), cardView1.getHeight()));
                                    }
                                    else if(cardView1.getLayoutParams().width > Static_Device_Info.getbottommenu2_width())
                                    {
                                        cardView1.setLayoutParams(new LinearLayout.LayoutParams(cardView1.getWidth()-20, cardView1.getHeight()));
                                    }

                                    if(cardView3.getLayoutParams().width <= Static_Device_Info.getbottommenu2_width())
                                    {
                                        cardView3.setLayoutParams(new LinearLayout.LayoutParams(Static_Device_Info.getbottommenu2_width(), cardView3.getHeight()));
                                    }
                                    else if(cardView3.getLayoutParams().width > Static_Device_Info.getbottommenu2_width())
                                    {
                                        cardView3.setLayoutParams(new LinearLayout.LayoutParams(cardView3.getWidth()-20, cardView3.getHeight()));
                                    }
                                }
                            });
                        }
                        else if(TargetCardView.equals(cardView3))
                        {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(cardView3.getLayoutParams().width >= pref.getInt("AppDefaultSetting_DeviceSize_Width", 0)/5)
                                    {
                                        tt.cancel();
                                        cardView1.setEnabled(true);
                                        cardView2.setEnabled(true);
                                        cardView3.setEnabled(true);
                                        initView.cardview_btn_call_bottommenu.setEnabled(true);
                                        initView.tv_btn_bottommenu1_setmylocation.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu1_setmylocation.setVisibility(View.VISIBLE);
                                        initView.tv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.VISIBLE);
                                        initView.tv_btn_bottommenu3_realtime_gps.setVisibility(View.VISIBLE);
                                        initView.iv_btn_bottommenu3_realtime_gps.setVisibility(View.GONE);
                                        cardView3.setLayoutParams(new LinearLayout.LayoutParams(pref.getInt("AppDefaultSetting_DeviceSize_Width", 0)/5, cardView3.getHeight()));
                                    }
                                    else if(cardView3.getLayoutParams().width < pref.getInt("AppDefaultSetting_DeviceSize_Width", 0)/5)
                                    {
                                        cardView3.setLayoutParams(new LinearLayout.LayoutParams(cardView3.getWidth()+20, cardView3.getHeight()));
                                    }

                                    if(cardView1.getLayoutParams().width <= Static_Device_Info.getbottommenu3_width())
                                    {
                                        cardView1.setLayoutParams(new LinearLayout.LayoutParams(Static_Device_Info.getbottommenu3_width(), cardView1.getHeight()));
                                    }
                                    else if(cardView1.getLayoutParams().width > Static_Device_Info.getbottommenu3_width())
                                    {
                                        cardView1.setLayoutParams(new LinearLayout.LayoutParams(cardView1.getWidth()-20, cardView1.getHeight()));
                                    }

                                    if(cardView2.getLayoutParams().width <= Static_Device_Info.getbottommenu3_width())
                                    {
                                        cardView2.setLayoutParams(new LinearLayout.LayoutParams(Static_Device_Info.getbottommenu3_width(), cardView2.getHeight()));
                                    }
                                    else if(cardView2.getLayoutParams().width > Static_Device_Info.getbottommenu3_width())
                                    {
                                        cardView2.setLayoutParams(new LinearLayout.LayoutParams(cardView2.getWidth()-20, cardView2.getHeight()));
                                    }
                                }
                            });
                        }
                        break;
                    case "scaleDown":
                        if(TargetCardView.equals(cardView1))
                        {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(cardView1.getLayoutParams().width <= Static_Device_Info.getbottommenu2_width())
                                    {
                                        tt.cancel();
                                        cardView1.setEnabled(true);
                                        cardView2.setEnabled(true);
                                        cardView3.setEnabled(true);
                                        initView.cardview_btn_call_bottommenu.setEnabled(true);
                                        initView.tv_btn_bottommenu1_setmylocation.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu1_setmylocation.setVisibility(View.VISIBLE);
                                        initView.tv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.VISIBLE);
                                        initView.tv_btn_bottommenu3_realtime_gps.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu3_realtime_gps.setVisibility(View.VISIBLE);
                                        cardView1.setLayoutParams(new LinearLayout.LayoutParams(Static_Device_Info.getbottommenu2_width(), cardView1.getHeight()));
                                    }
                                    else if(cardView1.getLayoutParams().width > Static_Device_Info.getbottommenu2_width())
                                    {
                                        cardView1.setLayoutParams(new LinearLayout.LayoutParams(cardView1.getWidth()-20, cardView1.getHeight()));
                                    }
                                }
                            });
                        }
                        else if(TargetCardView.equals(cardView2))
                        {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(cardView2.getLayoutParams().width <= Static_Device_Info.getbottommenu3_width())
                                    {
                                        tt.cancel();
                                        cardView1.setEnabled(true);
                                        cardView2.setEnabled(true);
                                        cardView3.setEnabled(true);
                                        initView.cardview_btn_call_bottommenu.setEnabled(true);
                                        initView.tv_btn_bottommenu1_setmylocation.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu1_setmylocation.setVisibility(View.VISIBLE);
                                        initView.tv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.VISIBLE);
                                        initView.tv_btn_bottommenu3_realtime_gps.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu3_realtime_gps.setVisibility(View.VISIBLE);
                                        cardView2.setLayoutParams(new LinearLayout.LayoutParams(Static_Device_Info.getbottommenu3_width(), cardView2.getHeight()));
                                    }
                                    else if(cardView2.getLayoutParams().width > Static_Device_Info.getbottommenu3_width())
                                    {
                                        cardView2.setLayoutParams(new LinearLayout.LayoutParams(cardView2.getWidth()-20, cardView2.getHeight()));
                                    }
                                }
                            });
                        }
                        else if(TargetCardView.equals(cardView3))
                        {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(cardView3.getLayoutParams().width <= Static_Device_Info.getbottommenu1_width())
                                    {
                                        tt.cancel();
                                        cardView1.setEnabled(true);
                                        cardView2.setEnabled(true);
                                        cardView3.setEnabled(true);
                                        initView.cardview_btn_call_bottommenu.setEnabled(true);
                                        initView.tv_btn_bottommenu1_setmylocation.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu1_setmylocation.setVisibility(View.VISIBLE);
                                        initView.tv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu2_searchmap_mapposition.setVisibility(View.VISIBLE);
                                        initView.tv_btn_bottommenu3_realtime_gps.setVisibility(View.GONE);
                                        initView.iv_btn_bottommenu3_realtime_gps.setVisibility(View.VISIBLE);
                                        cardView3.setLayoutParams(new LinearLayout.LayoutParams(Static_Device_Info.getbottommenu1_width(), cardView3.getHeight()));
                                    }
                                    else if(cardView3.getLayoutParams().width > Static_Device_Info.getbottommenu1_width())
                                    {
                                        cardView3.setLayoutParams(new LinearLayout.LayoutParams(cardView3.getWidth()-20, cardView3.getHeight()));
                                    }
                                }
                            });
                        }
                        break;
                }
            }
        };
        return tempTask;
    }
    public void Timer_stop()
    {
        if(tt != null)
        {
            tt.cancel();
        }
    }

    //선택한 언어에 맞는 String값을 반환합니다.
    @NonNull
    public static String getStringByLocal(AppCompatActivity context, int resId, String locale)
    {
        //버전에 따라서 언어를 설정해주는 방식이 다르기 때문에 분류해서 사용합니다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return getStringByLocalPlus17(context, resId, locale);
        else return getStringByLocalBefore17(context, resId, locale);
    }
    //젤리빈 버전 이상일 경우
    @NonNull
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private static String getStringByLocalPlus17(AppCompatActivity context, int resId, String locale)
    {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(new Locale(locale));
        return context.createConfigurationContext(configuration).getResources().getString(resId);
    }
    //젤리빈 버전 이하일 경우
    private static String getStringByLocalBefore17(Context context, int resId, String language)
    {
        //++추가 ->지원하지않는 버전 ->앱 종료
        return null;
    }

    public void AlertDialog_AppFinish()
    {
        init();

        AlertDialog.Builder dlg = new AlertDialog.Builder(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity));
        dlg.setTitle(getStringByLocal(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity), R.string.app_name, pref.getString("AppDefaultSetting_Language", "en")));
        dlg.setMessage(getStringByLocal(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity), R.string.common_finish_1MSG0101_finish_title, pref.getString("AppDefaultSetting_Language", "en")));
        dlg.setIcon(R.drawable.ic_icon_appicon);
        dlg.setPositiveButton(getStringByLocal(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity), R.string.common_finish_1MSG0102_finish_finish, pref.getString("AppDefaultSetting_Language", "en")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.finishAffinity(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity));
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).moveTaskToBack(true);
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).finish();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
        dlg.setNeutralButton(getStringByLocal(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity), R.string.common_finish_1MSG0103_finish_cancel, pref.getString("AppDefaultSetting_Language", "en")), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dlg.setCancelable(false);
        dlg.show();
    }

    public void setArguments(Bundle args) {
        String result = args.getString("result");
        String parameter_data = args.getString("parameter_data");
        switch (result)
        {
            case "cancel" :
                break;
            case "no" :
                break;
            case "ok" :
                if(parameter_data.equals("finish"))
                {
                    ActivityCompat.finishAffinity(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity));
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).moveTaskToBack(true);
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
                break;
        }
    }

    public boolean isServiceRunningCheck(String serviceName) {
        ActivityManager manager = (ActivityManager) Ui_Main_Activity.Context_UiMainActivity.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void onAppFinish()
    {
        ActivityCompat.finishAffinity(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity));
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).moveTaskToBack(true);
        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

}
