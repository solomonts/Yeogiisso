package com.solomonm.yeogiisso.drawer.setting.certification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.solomonm.yeogiisso.R;

public class Ui_DrawerSetting_Certification_Activity extends AppCompatActivity {
    public static Context Context_Ui_DrawerSetting_Certification_Activity;

    public void init()
    {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_drawer_setting_certification_activity);
        Context_Ui_DrawerSetting_Certification_Activity = this;
        init();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
        Context_Ui_DrawerSetting_Certification_Activity = null;
    }
}
