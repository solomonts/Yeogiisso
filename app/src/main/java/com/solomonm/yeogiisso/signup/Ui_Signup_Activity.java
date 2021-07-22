package com.solomonm.yeogiisso.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.kakao.sdk.user.UserApiClient;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.login.Ui_Login_Activity;
import com.solomonm.yeogiisso.service.Class_NetworkStatus;
import com.solomonm.yeogiisso.static_init.Static_Method;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ui_Signup_Activity extends AppCompatActivity {
    public static Context Context_Ui_Signup_Activity;
    Static_Method Static_User_Info;

    private LinearLayout layout_btn_signup_kakao;
    private TextView tv_btn_changepage_login;

    int networkStatus;
    String ClickSignupType = "";

    public void initView()
    {
        this.layout_btn_signup_kakao = (LinearLayout)findViewById(R.id.layout_btn_signup_kakao);
        this.tv_btn_changepage_login = (TextView)findViewById(R.id.tv_btn_changepage_login);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_signup_activity);

        Context_Ui_Signup_Activity = this;
        Static_User_Info = (Static_Method) Ui_Signup_Activity.Context_Ui_Signup_Activity.getApplicationContext();
        initView();

        layout_btn_signup_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Signup_Activity.Context_Ui_Signup_Activity);

                Static_User_Info.setClickSignupType("Kakao");
                networkStatus = Class_NetworkStatus.getConnectivityStatus(Ui_Signup_Activity.Context_Ui_Signup_Activity);

                if (networkStatus != Class_NetworkStatus.TYPE_MOBILE && networkStatus != Class_NetworkStatus.TYPE_WIFI)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Signup_Activity",
                            "error_network_off",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0101_server_connect_network_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0102_server_connect_network_errorcode),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "true"
                    );
                }
                else
                {
                    //카카오톡 앱이 설치되어있을 경우
                    if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(Ui_Signup_Activity.Context_Ui_Signup_Activity))
                    {
                        // 로그인 공통 callback 구성
                        UserApiClient.getInstance().loginWithKakaoTalk(Ui_Signup_Activity.Context_Ui_Signup_Activity,(oAuthToken, error) -> {
                            if (error != null)
                            {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                Log.e("LOG : ", "로그인 실패", error);
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                        "Ui_Signup_Activity",
                                        "error_login_for_kakao_loginfail",
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_login_for_kakao_loginfail),
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_cancel),
                                        "empty",
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                        "false"
                                );
                            }
                            else if (oAuthToken != null)
                            {
                                Log.e("LOG : ", "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                                UserApiClient.getInstance().me((user, meError) -> {
                                    if(meError != null)
                                    {
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                        Log.e("LOG : ", "사용자 정보 요청 실패", meError);
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                "Ui_Signup_Activity",
                                                "error_login_for_kakao_loadingfail",
                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_login_for_kakao_loadingfail),
                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_cancel),
                                                "empty",
                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                                "false"
                                        );
                                    }
                                    else
                                    {
                                        Log.e("LOG : ", "사용자 정보 요청 성공"
                                                        +"\n회원번호 : "+user.getId()
                                                        +"\n닉네임 : "+user.getKakaoAccount().getProfile().getNickname()
                                                        +"\n프로필사진 : "+user.getKakaoAccount().getProfile().getProfileImageUrl()
                                                //+"\n카카오계정이메일 : "+user.getKakaoAccount().getEmail()
                                        );

                                        //회원가입 진행
                                        CallSignupFunction(String.valueOf(user.getId()), ClickSignupType);

                                        /*
                                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                String success = "false";
                                                String exists = "";
                                                try
                                                {
                                                    JSONObject jsonObject = new JSONObject(response);

                                                    success = jsonObject.getString("success");
                                                    exists = jsonObject.getString("exists");

                                                    if(success.equals("true"))
                                                    {
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                                "Ui_Signup_Activity",
                                                                "signup_finish",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_success_signup),
                                                                "empty",
                                                                "empty",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                                                "false"
                                                        );

                                                        finish();
                                                        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                                                        Context_Ui_Signup_Activity = null;
                                                    }
                                                    else
                                                    {
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                                                        if(exists.equals("ID"))
                                                        {
                                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                                    "Ui_Signup_Activity",
                                                                    "overlap_id",
                                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_overlap_id),
                                                                    "empty",
                                                                    "empty",
                                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                                                    "false"
                                                            );
                                                        }
                                                        else
                                                        {
                                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                                    "Ui_Signup_Activity",
                                                                    "unusual_approach",
                                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                                                                    "empty",
                                                                    "empty",
                                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                                                                    "true"
                                                            );
                                                        }
                                                    }
                                                }
                                                catch (Exception e)
                                                {
                                                    e.printStackTrace();

                                                    //인터넷 꺼져있을 때
                                                    if (networkStatus != Class_NetworkStatus.TYPE_MOBILE && networkStatus != Class_NetworkStatus.TYPE_WIFI)
                                                    {
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                                "Ui_Signup_Activity",
                                                                "error_network_off",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0101_server_connect_network_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0102_server_connect_network_errorcode),
                                                                "empty",
                                                                "empty",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                                                "true"
                                                        );
                                                    }
                                                    //API서버 오류
                                                    else
                                                    {
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                                "Ui_Signup_Activity",
                                                                "error_server_off",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0201_server_connect_server_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0202_server_connect_server_errorcode),
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                                                                "empty",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                                                "true"
                                                        );
                                                    }
                                                }
                                            }
                                        };
                                        SignupRequest signupRequest = new SignupRequest(String.valueOf(user.getId()), responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(Ui_Signup_Activity.Context_Ui_Signup_Activity);
                                        queue.add(signupRequest);
                                         */

                                    }
                                    return null;
                                });
                            }
                            return null;
                        });
                    }
                    else
                    {
                        // 로그인 공통 callback 구성
                        UserApiClient.getInstance().loginWithKakaoAccount(Ui_Signup_Activity.Context_Ui_Signup_Activity,(oAuthToken, error) -> {
                            if (error != null)
                            {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                Log.e("LOG : ", "로그인 실패", error);
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                        "Ui_Signup_Activity",
                                        "error_login_for_kakao_loginfail",
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_login_for_kakao_loginfail),
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_cancel),
                                        "empty",
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                        "false"
                                );
                            }
                            else if (oAuthToken != null)
                            {
                                Log.e("LOG : ", "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                                UserApiClient.getInstance().me((user, meError) -> {
                                    if(meError != null)
                                    {
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                        Log.e("LOG : ", "사용자 정보 요청 실패", meError);
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                "Ui_Signup_Activity",
                                                "error_login_for_kakao_loadingfail",
                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_login_for_kakao_loadingfail),
                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_cancel),
                                                "empty",
                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                                "false"
                                        );
                                    }
                                    else
                                    {
                                        Log.e("LOG : ", "사용자 정보 요청 성공"
                                                        +"\n회원번호 : "+user.getId()
                                                        +"\n닉네임 : "+user.getKakaoAccount().getProfile().getNickname()
                                                        +"\n프로필사진 : "+user.getKakaoAccount().getProfile().getProfileImageUrl()
                                                //+"\n카카오계정이메일 : "+user.getKakaoAccount().getEmail()
                                        );

                                        //회원가입 진행
                                        CallSignupFunction(String.valueOf(user.getId()), ClickSignupType);

                                        /*
                                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                String success = "false";
                                                String exists = "";
                                                try
                                                {
                                                    JSONObject jsonObject = new JSONObject(response);

                                                    success = jsonObject.getString("success");
                                                    exists = jsonObject.getString("exists");

                                                    if(success.equals("true"))
                                                    {
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                                "Ui_Signup_Activity",
                                                                "signup_finish",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_success_signup),
                                                                "empty",
                                                                "empty",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                                                "false"
                                                        );

                                                        finish();
                                                        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                                                        Context_Ui_Signup_Activity = null;
                                                    }
                                                    else
                                                    {
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                                                        if(exists.equals("ID"))
                                                        {
                                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                                    "Ui_Signup_Activity",
                                                                    "overlap_id",
                                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_overlap_id),
                                                                    "empty",
                                                                    "empty",
                                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                                                    "false"
                                                            );
                                                        }
                                                        else
                                                        {
                                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                                    "Ui_Signup_Activity",
                                                                    "unusual_approach",
                                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                                                                    "empty",
                                                                    "empty",
                                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                                                                    "true"
                                                            );
                                                        }
                                                    }
                                                }
                                                catch (Exception e)
                                                {
                                                    e.printStackTrace();

                                                    //인터넷 꺼져있을 때
                                                    if (networkStatus != Class_NetworkStatus.TYPE_MOBILE && networkStatus != Class_NetworkStatus.TYPE_WIFI)
                                                    {
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                                "Ui_Signup_Activity",
                                                                "error_network_off",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0101_server_connect_network_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0102_server_connect_network_errorcode),
                                                                "empty",
                                                                "empty",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                                                "true"
                                                        );
                                                    }
                                                    //API서버 오류
                                                    else
                                                    {
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                                "Ui_Signup_Activity",
                                                                "error_server_off",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0201_server_connect_server_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0202_server_connect_server_errorcode),
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                                                                "empty",
                                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                                                "true"
                                                        );
                                                    }
                                                }
                                            }
                                        };
                                        SignupRequest signupRequest = new SignupRequest(String.valueOf(user.getId()), responseListener);
                                        RequestQueue queue = Volley.newRequestQueue(Ui_Signup_Activity.Context_Ui_Signup_Activity);
                                        queue.add(signupRequest);
                                         */
                                    }
                                    return null;
                                });
                            }
                            return null;
                        });
                    }

                }
            }
        });

        tv_btn_changepage_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startUiLoginActivity = new Intent(Ui_Signup_Activity.this, Ui_Login_Activity.class);
                finish();
                overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                startActivity(startUiLoginActivity);
            }
        });

    }

    public void setArguments(Bundle args) {
        String result = args.getString("result");
        String parameter_data = args.getString("parameter_data");
        switch (result)
        {
            case "cancel" :
                switch (parameter_data)
                {
                    case "error_login_for_kakao_loginfail":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "error_login_for_kakao_loadingfail":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "error_server_off":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.onAppFinish();
                        break;
                }
                break;
            case "no" :
                break;
            case "ok" :
                switch (parameter_data)
                {
                    case "error_network_off":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "error_login_for_kakao_loginfail":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Signup_Activity.Context_Ui_Signup_Activity);
                        Handler handler_login_for_kakao_loginfail = new Handler();
                        handler_login_for_kakao_loginfail.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                ((Ui_Signup_Activity)Ui_Signup_Activity.Context_Ui_Signup_Activity).layout_btn_signup_kakao.callOnClick();
                            }
                        }, 2000);
                        break;
                    case "error_login_for_kakao_loadingfail":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Signup_Activity.Context_Ui_Signup_Activity);
                        Handler handler_login_for_kakao_loadingfail = new Handler();
                        handler_login_for_kakao_loadingfail.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                ((Ui_Signup_Activity)Ui_Signup_Activity.Context_Ui_Signup_Activity).layout_btn_signup_kakao.callOnClick();
                            }
                        }, 2000);
                        break;
                    case "error_server_off":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Signup_Activity.Context_Ui_Signup_Activity);
                        Handler handler_server_off = new Handler();
                        handler_server_off.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                if(((Ui_Signup_Activity)Ui_Signup_Activity.Context_Ui_Signup_Activity).Static_User_Info.getClickSignupType().equals("Kakao"))
                                {
                                    ((Ui_Signup_Activity)Ui_Signup_Activity.Context_Ui_Signup_Activity).layout_btn_signup_kakao.callOnClick();
                                }
                            }
                        }, 2000);
                        break;
                    case "signup_finish":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "overlap_id":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "unusual_approach":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.onAppFinish();
                        break;
                }
                break;
        }

    }

    public void CallSignupFunction(String user_id, String user_type)
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String success = "false";
                String exists = "";
                try
                {
                    JSONObject jsonObject = new JSONObject(response);

                    success = jsonObject.getString("success");
                    exists = jsonObject.getString("exists");

                    if(success.equals("true"))
                    {
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                "Ui_Signup_Activity",
                                "signup_finish",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_success_signup),
                                "empty",
                                "empty",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                "false"
                        );

                        finish();
                        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                    }
                    else
                    {
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                        if(exists.equals("ID"))
                        {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                    "Ui_Signup_Activity",
                                    "overlap_id",
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_overlap_id),
                                    "empty",
                                    "empty",
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                    "false"
                            );
                        }
                        else
                        {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                    "Ui_Signup_Activity",
                                    "unusual_approach",
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                                    "empty",
                                    "empty",
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                                    "true"
                            );
                        }
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();

                    //인터넷 꺼져있을 때
                    if (networkStatus != Class_NetworkStatus.TYPE_MOBILE && networkStatus != Class_NetworkStatus.TYPE_WIFI)
                    {
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                "Ui_Signup_Activity",
                                "error_network_off",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0101_server_connect_network_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0102_server_connect_network_errorcode),
                                "empty",
                                "empty",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                "false"
                        );
                    }
                    //API서버 오류
                    else
                    {
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                "Ui_Signup_Activity",
                                "error_server_off",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0201_server_connect_server_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0202_server_connect_server_errorcode),
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                                "empty",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                "true"
                        );
                    }
                }
            }
        };
        SignupRequest signupRequest = new SignupRequest(user_id, Static_User_Info.getClickSignupType(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(Ui_Signup_Activity.Context_Ui_Signup_Activity);
        queue.add(signupRequest);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
    }
}