package com.solomonm.yeogiisso.drawer.setting.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.management.store.Ui_StoreManagement_Activity;
import com.solomonm.yeogiisso.static_init.Static_Method;
import com.solomonm.yeogiisso.static_init.Static_initView;

public class Ui_ChangeProfile_Activity extends AppCompatActivity {
    public static Context Context_Ui_ChangeProfile_Activity;
    Static_Method Static_User_Info;

    private LinearLayout layout_changeprofile_previous;
    private TextView tv_drawersetting_profile_changeprofile, tv_drawersetting_profile_userinfo_change_consumer, tv_drawersetting_profile_userinfo_nickname_errormark;
    private EditText et_drawersetting_profile_userinfo_email, et_drawersetting_profile_userinfo_nickname;

    public void initView()
    {
        Static_User_Info = (Static_Method)Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();

        this.layout_changeprofile_previous = (LinearLayout)findViewById(R.id.layout_changeprofile_previous);
        this.tv_drawersetting_profile_changeprofile = (TextView)findViewById(R.id.tv_drawersetting_profile_changeprofile);
        this.tv_drawersetting_profile_userinfo_change_consumer = (TextView)findViewById(R.id.tv_drawersetting_profile_userinfo_change_consumer);
        this.tv_drawersetting_profile_userinfo_nickname_errormark = (TextView)findViewById(R.id.tv_drawersetting_profile_userinfo_nickname_errormark);
        this.et_drawersetting_profile_userinfo_email = (EditText)findViewById(R.id.et_drawersetting_profile_userinfo_email);
        this.et_drawersetting_profile_userinfo_nickname = (EditText)findViewById(R.id.et_drawersetting_profile_userinfo_nickname);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_draweroption_changeprofile_activity);
        Context_Ui_ChangeProfile_Activity = this;

        initView();
        et_drawersetting_profile_userinfo_email.setText(Static_User_Info.getUserInfo_user_id());
        et_drawersetting_profile_userinfo_nickname.setText(Static_User_Info.getUserInfo_user_nickname());
        if(Static_User_Info.getUserInfo_user_auth().equals("U") && tv_drawersetting_profile_userinfo_change_consumer.getVisibility() == View.GONE)
        {
            tv_drawersetting_profile_userinfo_change_consumer.setVisibility(View.VISIBLE);
        }

        layout_changeprofile_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Context_Ui_ChangeProfile_Activity = null;
                overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
            }
        });

        tv_drawersetting_profile_changeprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startChangeProfilePicActivity = new Intent(Ui_ChangeProfile_Activity.this, Ui_ChangeProfile_Pic_Activity.class);
                startActivity(startChangeProfilePicActivity);
                overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
            }
        });

        et_drawersetting_profile_userinfo_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_drawersetting_profile_userinfo_nickname.getText().toString().equals(Static_User_Info.getUserInfo_user_nickname()))
                {
                    tv_drawersetting_profile_userinfo_nickname_errormark.setVisibility(View.GONE);
                }
                else
                {
                    tv_drawersetting_profile_userinfo_nickname_errormark.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        Context_Ui_ChangeProfile_Activity = null;
        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
    }
}