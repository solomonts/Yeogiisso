package com.solomonm.yeogiisso.drawer.setting.theme;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.static_init.Static_Function;
import com.solomonm.yeogiisso.Ui_Main_Activity;

public class Ui_DrawerSetting_Theme_Dialog extends Dialog {

    public static Context Context_Ui_DrawerSetting_Theme_Dialog;
    Static_Function initFunction;
    SharedPreferences pref;

    private LinearLayout layout_drawersetting_theme_dialog;
    private ImageView iv_change_theme_daymode, iv_change_theme_darkmode;
    private TextView tv_change_theme_daymode, tv_change_theme_darkmode, tv_now_theme_daymode, tv_now_theme_darkmode;
    private Button btn_theme_change_cancel, btn_theme_change_ok;

    String choice_theme = "16";
    int text_attrColor_default;

    public void initView()
    {
        initFunction = new Static_Function();
        pref = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getSharedPreferences("pref", Context.MODE_PRIVATE);

        this.layout_drawersetting_theme_dialog = (LinearLayout)findViewById(R.id.layout_drawersetting_theme_dialog);
        this.iv_change_theme_daymode = (ImageView)findViewById(R.id.iv_change_theme_daymode);
        this.iv_change_theme_darkmode = (ImageView)findViewById(R.id.iv_change_theme_darkmode);
        this.tv_change_theme_daymode = (TextView)findViewById(R.id.tv_change_theme_daymode);
        this.tv_change_theme_darkmode = (TextView)findViewById(R.id.tv_change_theme_darkmode);
        this.tv_now_theme_daymode = (TextView)findViewById(R.id.tv_now_theme_daymode);
        this.tv_now_theme_darkmode = (TextView)findViewById(R.id.tv_now_theme_darkmode);
        this.btn_theme_change_cancel = (Button)findViewById(R.id.btn_theme_change_cancel);
        this.btn_theme_change_ok = (Button)findViewById(R.id.btn_theme_change_ok);
    }

    public Ui_DrawerSetting_Theme_Dialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_drawersetting_theme_dialog);

        Context_Ui_DrawerSetting_Theme_Dialog = context;
        initView();
        set_dialogSize();

        //attr 색상 관리
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = Ui_Main_Activity.Context_UiMainActivity.getTheme();
        theme.resolveAttribute(R.attr.color_common_black_softgray, typedValue, true);
        TypedArray arr =
                Ui_Main_Activity.Context_UiMainActivity.obtainStyledAttributes(typedValue.data, new int[]{
                        R.attr.color_common_black_softgray
                });
        text_attrColor_default = arr.getColor(0, -1);

        int currentMode = Ui_Main_Activity.Context_UiMainActivity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (pref.getInt("AppDefaultSetting_Theme", currentMode))
        {
            case Configuration.UI_MODE_NIGHT_NO: //daymode
                tv_now_theme_daymode.setVisibility(View.VISIBLE);
                tv_now_theme_darkmode.setVisibility(View.INVISIBLE);
                onClickActive_daymode();
                break;
            case Configuration.UI_MODE_NIGHT_YES: //darkmode
                tv_now_theme_daymode.setVisibility(View.INVISIBLE);
                tv_now_theme_darkmode.setVisibility(View.VISIBLE);
                onClickActive_darkmode();
                break;
            default: //unknown
                tv_now_theme_daymode.setVisibility(View.VISIBLE);
                tv_now_theme_darkmode.setVisibility(View.INVISIBLE);
                onClickActive_daymode();
                break;
        }

        tv_change_theme_daymode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActive_daymode();
                choice_theme = Configuration.UI_MODE_NIGHT_NO+"";
            }
        });

        tv_change_theme_darkmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActive_darkmode();
                choice_theme = Configuration.UI_MODE_NIGHT_YES+"";
            }
        });

        btn_theme_change_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Context_Ui_DrawerSetting_Theme_Dialog != null && isShowing())
                {
                    dismiss();
                }
            }
        });

        btn_theme_change_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFunction.Ui_AlertDialog_Dialog_Start(
                        "Ui_DrawerSetting_Theme_Dialog",
                        choice_theme+"",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_change_1MSG0101_change_title),
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_change_1MSG0103_change_cancel),
                        "empty",
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_change_1MSG0102_change_ok),
                        "false"
                );
            }
        });
    }

    public void setArguments(Bundle args) {
        String result = args.getString("result");
        String parameter_data = args.getString("parameter_data");
        switch (result)
        {
            case "cancel" :
                ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                break;
            case "no" :
                ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                break;
            case "ok" :
                choice_theme = parameter_data;

                ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Theme_Dialog_End();
                initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initFunction.onChangeTheme(choice_theme);
                    }
                }, 2000);

                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(Context_Ui_DrawerSetting_Theme_Dialog != null && isShowing())
        {
            dismiss();
        }
    }

    public void onClickActive_daymode()
    {
        if(iv_change_theme_daymode.getVisibility() == View.INVISIBLE)
        {
            iv_change_theme_daymode.setVisibility(View.VISIBLE);
            iv_change_theme_darkmode.setVisibility(View.INVISIBLE);
        }
        if(tv_change_theme_daymode.getCurrentTextColor() == text_attrColor_default)
        {
            tv_change_theme_daymode.setTextColor(Color.parseColor("#FF0000"));
            tv_change_theme_darkmode.setTextColor(text_attrColor_default);
        }
        if(tv_now_theme_daymode.getVisibility() == View.INVISIBLE)
        {
            btn_theme_change_ok.setEnabled(true);
        }
        else
        {
            btn_theme_change_ok.setEnabled(false);
        }
    }

    public void onClickActive_darkmode()
    {
        if(iv_change_theme_darkmode.getVisibility() == View.INVISIBLE)
        {
            iv_change_theme_daymode.setVisibility(View.INVISIBLE);
            iv_change_theme_darkmode.setVisibility(View.VISIBLE);
        }
        if(tv_change_theme_darkmode.getCurrentTextColor() == text_attrColor_default)
        {
            tv_change_theme_daymode.setTextColor(text_attrColor_default);
            tv_change_theme_darkmode.setTextColor(Color.parseColor("#FF0000"));
        }
        if(tv_now_theme_darkmode.getVisibility() == View.INVISIBLE)
        {
            btn_theme_change_ok.setEnabled(true);
        }
        else
        {
            btn_theme_change_ok.setEnabled(false);
        }
    }

    public void set_dialogSize()
    {
        DisplayMetrics device_display = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getApplicationContext().getResources().getDisplayMetrics();
        int deviceWidth = device_display.widthPixels;
        int deviceHeight = device_display.heightPixels;
        layout_drawersetting_theme_dialog.setLayoutParams(new FrameLayout.LayoutParams((int)(deviceWidth/1.5), (int)(deviceHeight/2)));
    }
}
