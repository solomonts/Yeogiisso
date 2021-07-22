package com.solomonm.yeogiisso.drawer.setting.language;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import androidx.core.content.ContextCompat;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.static_init.Static_Function;
import com.solomonm.yeogiisso.static_init.Static_Method;
import com.solomonm.yeogiisso.Ui_Main_Activity;

public class Ui_DrawerSetting_Language_Dialog extends Dialog {

    public static Context Context_Ui_DrawerSetting_Language_Dialog;
    Static_Function initFunction;
    Static_Method initMethod;
    SharedPreferences pref;

    private LinearLayout layout_drawersetting_language_dialog;
    private ImageView iv_change_language_Korean, iv_change_language_English, iv_change_language_Japanese, iv_change_language_Chinese;
    private TextView tv_change_language_korean, tv_change_language_English, tv_change_language_Japanese, tv_change_language_Chinese,
            tv_now_language_korean, tv_now_language_English, tv_now_language_Japanese, tv_now_language_Chinese;
    private Button btn_language_change_cancel, btn_language_change_ok;

    String choice_language = "en";
    int text_attrColor_default;

    public void initView()
    {
        initFunction = new Static_Function();
        initMethod = new Static_Method();
        pref = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getSharedPreferences("pref", Context.MODE_PRIVATE);

        this.layout_drawersetting_language_dialog = (LinearLayout)findViewById(R.id.layout_drawersetting_language_dialog);
        this.iv_change_language_Korean = (ImageView)findViewById(R.id.iv_change_language_Korean);
        this.iv_change_language_English = (ImageView)findViewById(R.id.iv_change_language_English);
        this.iv_change_language_Japanese = (ImageView)findViewById(R.id.iv_change_language_Japanese);
        this.iv_change_language_Chinese = (ImageView)findViewById(R.id.iv_change_language_Chinese);
        this.tv_change_language_korean = (TextView)findViewById(R.id.tv_change_language_korean);
        this.tv_change_language_English = (TextView)findViewById(R.id.tv_change_language_English);
        this.tv_change_language_Japanese = (TextView)findViewById(R.id.tv_change_language_Japanese);
        this.tv_change_language_Chinese = (TextView)findViewById(R.id.tv_change_language_Chinese);
        this.tv_now_language_korean = (TextView)findViewById(R.id.tv_now_language_korean);
        this.tv_now_language_English = (TextView)findViewById(R.id.tv_now_language_English);
        this.tv_now_language_Japanese = (TextView)findViewById(R.id.tv_now_language_Japanese);
        this.tv_now_language_Chinese = (TextView)findViewById(R.id.tv_now_language_Chinese);
        this.btn_language_change_cancel = (Button)findViewById(R.id.btn_language_change_cancel);
        this.btn_language_change_ok = (Button)findViewById(R.id.btn_language_change_ok);
    }

    public Ui_DrawerSetting_Language_Dialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_drawersetting_language_dialog);

        Context_Ui_DrawerSetting_Language_Dialog = context;
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

        switch (pref.getString("AppDefaultSetting_Language", "en"))
        {
            case "ko":
                tv_now_language_korean.setVisibility(View.VISIBLE);
                tv_now_language_English.setVisibility(View.INVISIBLE);
                tv_now_language_Japanese.setVisibility(View.INVISIBLE);
                tv_now_language_Chinese.setVisibility(View.INVISIBLE);
                onClickActive_ko();
                break;
            case "en":
                tv_now_language_korean.setVisibility(View.INVISIBLE);
                tv_now_language_English.setVisibility(View.VISIBLE);
                tv_now_language_Japanese.setVisibility(View.INVISIBLE);
                tv_now_language_Chinese.setVisibility(View.INVISIBLE);
                onClickActive_en();
                break;
            case "ja":
                tv_now_language_korean.setVisibility(View.INVISIBLE);
                tv_now_language_English.setVisibility(View.INVISIBLE);
                tv_now_language_Japanese.setVisibility(View.VISIBLE);
                tv_now_language_Chinese.setVisibility(View.INVISIBLE);
                onClickActive_ja();
                break;
            case "zh":
                tv_now_language_korean.setVisibility(View.INVISIBLE);
                tv_now_language_English.setVisibility(View.INVISIBLE);
                tv_now_language_Japanese.setVisibility(View.INVISIBLE);
                tv_now_language_Chinese.setVisibility(View.VISIBLE);
                onClickActive_zh();
                break;
        }

        tv_change_language_korean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActive_ko();
                choice_language = "ko";
            }
        });

        tv_change_language_English.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActive_en();
                choice_language = "en";
            }
        });

        tv_change_language_Japanese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActive_ja();
                choice_language = "ja";
            }
        });

        tv_change_language_Chinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickActive_zh();
                choice_language = "zh";
            }
        });

        btn_language_change_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Context_Ui_DrawerSetting_Language_Dialog != null && isShowing())
                {
                    dismiss();
                }
            }
        });

        btn_language_change_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFunction.Ui_AlertDialog_Dialog_Start(
                        "Ui_DrawerSetting_Language_Dialog",
                        choice_language,
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.common_change_1MSG0101_change_title),
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_cancel),
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
                choice_language = parameter_data;

                ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_DrawerSetting_Language_Dialog_End();
                initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(choice_language.equals("ko") || choice_language.equals("en") || choice_language.equals("ja") || choice_language.equals("zh"))
                        {
                        }
                        else
                        {
                            choice_language = "en";
                        }
                        initFunction.onChangeLanguage(choice_language);
                    }
                }, 2000);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(Context_Ui_DrawerSetting_Language_Dialog != null && isShowing())
        {
            dismiss();
        }
    }

    public void onClickActive_ko()
    {
        if(iv_change_language_Korean.getVisibility() == View.INVISIBLE)
        {
            iv_change_language_Korean.setVisibility(View.VISIBLE);
            iv_change_language_English.setVisibility(View.INVISIBLE);
            iv_change_language_Japanese.setVisibility(View.INVISIBLE);
            iv_change_language_Chinese.setVisibility(View.INVISIBLE);
        }
        if(tv_change_language_korean.getCurrentTextColor() == text_attrColor_default)
        {
            tv_change_language_korean.setTextColor(Color.parseColor("#FF0000"));
            tv_change_language_English.setTextColor(text_attrColor_default);
            tv_change_language_Japanese.setTextColor(text_attrColor_default);
            tv_change_language_Chinese.setTextColor(text_attrColor_default);
        }
        if(tv_now_language_korean.getVisibility() == View.INVISIBLE)
        {
            btn_language_change_ok.setEnabled(true);
        }
        else
        {
            btn_language_change_ok.setEnabled(false);
        }
    }

    @SuppressLint("ResourceType")
    public void onClickActive_en()
    {
        if(iv_change_language_English.getVisibility() == View.INVISIBLE)
        {
            iv_change_language_Korean.setVisibility(View.INVISIBLE);
            iv_change_language_English.setVisibility(View.VISIBLE);
            iv_change_language_Japanese.setVisibility(View.INVISIBLE);
            iv_change_language_Chinese.setVisibility(View.INVISIBLE);
        }
        if(tv_change_language_English.getCurrentTextColor() == text_attrColor_default)
        {
            tv_change_language_korean.setTextColor(text_attrColor_default);
            tv_change_language_English.setTextColor(Color.parseColor("#FF0000"));
            tv_change_language_Japanese.setTextColor(text_attrColor_default);
            tv_change_language_Chinese.setTextColor(text_attrColor_default);
        }
        if(tv_now_language_English.getVisibility() == View.INVISIBLE)
        {
            btn_language_change_ok.setEnabled(true);
        }
        else
        {
            btn_language_change_ok.setEnabled(false);
        }
    }

    public void onClickActive_ja()
    {
        if(iv_change_language_Japanese.getVisibility() == View.INVISIBLE)
        {
            iv_change_language_Korean.setVisibility(View.INVISIBLE);
            iv_change_language_English.setVisibility(View.INVISIBLE);
            iv_change_language_Japanese.setVisibility(View.VISIBLE);
            iv_change_language_Chinese.setVisibility(View.INVISIBLE);
        }
        if(tv_change_language_Japanese.getCurrentTextColor() == text_attrColor_default)
        {
            tv_change_language_korean.setTextColor(text_attrColor_default);
            tv_change_language_English.setTextColor(text_attrColor_default);
            tv_change_language_Japanese.setTextColor(Color.parseColor("#FF0000"));
            tv_change_language_Chinese.setTextColor(text_attrColor_default);
        }
        if(tv_now_language_Japanese.getVisibility() == View.INVISIBLE)
        {
            btn_language_change_ok.setEnabled(true);
        }
        else
        {
            btn_language_change_ok.setEnabled(false);
        }
    }

    public void onClickActive_zh()
    {
        if(iv_change_language_Chinese.getVisibility() == View.INVISIBLE)
        {
            iv_change_language_Korean.setVisibility(View.INVISIBLE);
            iv_change_language_English.setVisibility(View.INVISIBLE);
            iv_change_language_Japanese.setVisibility(View.INVISIBLE);
            iv_change_language_Chinese.setVisibility(View.VISIBLE);
        }
        if(tv_change_language_Chinese.getCurrentTextColor() == text_attrColor_default)
        {
            tv_change_language_korean.setTextColor(text_attrColor_default);
            tv_change_language_English.setTextColor(text_attrColor_default);
            tv_change_language_Japanese.setTextColor(text_attrColor_default);
            tv_change_language_Chinese.setTextColor(Color.parseColor("#FF0000"));
        }
        if(tv_now_language_Chinese.getVisibility() == View.INVISIBLE)
        {
            btn_language_change_ok.setEnabled(true);
        }
        else
        {
            btn_language_change_ok.setEnabled(false);
        }
    }

    public void set_dialogSize()
    {
        DisplayMetrics device_display = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getApplicationContext().getResources().getDisplayMetrics();
        int deviceWidth = device_display.widthPixels;
        int deviceHeight = device_display.heightPixels;
        layout_drawersetting_language_dialog.setLayoutParams(new FrameLayout.LayoutParams((int)(deviceWidth/1.5), (int)(deviceHeight/1.5)));
    }
}
