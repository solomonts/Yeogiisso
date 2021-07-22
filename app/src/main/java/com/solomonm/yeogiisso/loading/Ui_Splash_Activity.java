package com.solomonm.yeogiisso.loading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.static_init.Static_Function;
import com.solomonm.yeogiisso.static_init.Static_Method;
import com.solomonm.yeogiisso.static_init.Static_initView;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.utils.Utils;

public class Ui_Splash_Activity extends AppCompatActivity {
    public static Context Context_UiSplashActivity;

    Static_initView initView;
    Static_Method initMethod;
    Static_Function initFunction;
    SharedPreferences pref;
    Static_Method Static_Device_Info;

    private TextView tv_loading_app_name, tv_loading_app_loading;
    private ImageView iv_loading_portrait, iv_loading_landscape;

    public void initView()
    {
        Context_UiSplashActivity = this;

        initView = new Static_initView();
        initMethod = new Static_Method();
        initFunction = new Static_Function();
        pref = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getSharedPreferences("pref", Context.MODE_PRIVATE);
        Static_Device_Info = (Static_Method)Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();

        this.tv_loading_app_name = (TextView)findViewById(R.id.tv_loading_app_name);
        this.tv_loading_app_loading = (TextView)findViewById(R.id.tv_loading_app_loading);
        this.iv_loading_portrait = (ImageView)findViewById(R.id.iv_loading_portrait);
        this.iv_loading_landscape = (ImageView)findViewById(R.id.iv_loading_landscape);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Resources r = Resources.getSystem();
        Configuration config = r.getConfiguration();
        if(config.orientation == Configuration.ORIENTATION_LANDSCAPE )
        {
            setContentView(R.layout.ui_splash_activity_landscape);
            initView();
            Glide.with(this).load(R.drawable.ic_loading_splash).into(iv_loading_landscape);
        }
        else
        {
            setContentView(R.layout.ui_splash_activity_portrait);
            initView();
            Glide.with(this).load(R.drawable.ic_loading_splash).into(iv_loading_portrait);
        }

        set_StatusBar();

        tv_loading_app_name.setText(initFunction.getStringByLocal(this, R.string.app_name, pref.getString("AppDefaultSetting_Language", "en")));
        tv_loading_app_loading.setText(initFunction.getStringByLocal(this, R.string.text_loading, pref.getString("AppDefaultSetting_Language", "en")));

        reservation_Finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void reservation_Finish()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initView.layout_main_mainwait.setVisibility(View.GONE);
                initFunction.onCheck_Message(); //알림 메시지확인 (변경사항 발생시)
                //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).Call_BackgroundTask_NearbyMarketMarker();
                finish();
                overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
            }
        }, 3000);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            initFunction.onRestartLanguage(); //화면전환시 언어설정 유지
            setContentView(R.layout.ui_splash_activity_landscape);
            initView();
            Glide.with(this).load(R.drawable.ic_loading_splash).into(iv_loading_landscape);
        }
        else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            initFunction.onRestartLanguage(); //화면전환시 언어설정 유지
            setContentView(R.layout.ui_splash_activity_portrait);
            initView();
            Glide.with(this).load(R.drawable.ic_loading_splash).into(iv_loading_portrait);
        }
    }

    public void set_StatusBar()
    {
        int currentMode = Ui_Main_Activity.Context_UiMainActivity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (pref.getInt("AppDefaultSetting_Theme", currentMode))
        {
            case Configuration.UI_MODE_NIGHT_NO: //daymode
                Utils.setStatusBarColor(this, Utils.StatusBarColorType.WHITE_STATUS_BAR);
                break;
            case Configuration.UI_MODE_NIGHT_YES: //darkmode
                Utils.setStatusBarColor(this, Utils.StatusBarColorType.DARKGRAY_STATUS_BAR);
                break;
            default: //unknown
                Utils.setStatusBarColor(this, Utils.StatusBarColorType.WHITE_STATUS_BAR);
                break;
        }
    }
}
