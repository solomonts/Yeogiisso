package com.solomonm.yeogiisso.loading;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.static_init.Static_Function;
import com.solomonm.yeogiisso.utils.Utils;

public class Ui_FullScreenTutorial_Activity extends AppCompatActivity {
    public static Context Context_Ui_FullScreenTutorial_Activity;
    SharedPreferences pref;
    Static_Function static_function;

    private FrameLayout layout_tutorial_mainframe;
    private TextView tv_btn_veto_launching;
    private CheckBox checkbox_veto_launching;
    private Button btn_ok;

    private void initView()
    {
        pref = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getSharedPreferences("pref", Context.MODE_PRIVATE);
        static_function = new Static_Function();

        this.layout_tutorial_mainframe = (FrameLayout)findViewById(R.id.layout_tutorial_mainframe);
        this.tv_btn_veto_launching = (TextView)findViewById(R.id.tv_btn_veto_launching);
        this.checkbox_veto_launching = (CheckBox)findViewById(R.id.checkbox_veto_launching);
        this.btn_ok = (Button)findViewById(R.id.btn_ok);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_fullscreentutorial_activity);
        Context_Ui_FullScreenTutorial_Activity = this;

        initView();
        Utils.setStatusBarColor(this, Utils.StatusBarColorType.TRANSPARENT_STATUS_BAR);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox_veto_launching.isChecked() == true)
                {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("AppDefaultSetting_AppLaunching", "none");
                    editor.commit();
                }
                finish();
                overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
            }
        });

        tv_btn_veto_launching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkbox_veto_launching.isChecked() == false)
                {
                    checkbox_veto_launching.setChecked(true);
                }
                else
                {
                    checkbox_veto_launching.setChecked(false);
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        //
    }
}
