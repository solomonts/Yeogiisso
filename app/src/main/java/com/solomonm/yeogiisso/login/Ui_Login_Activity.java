package com.solomonm.yeogiisso.login;

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
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.kakao.sdk.user.UserApiClient;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.service.Class_NetworkStatus;
import com.solomonm.yeogiisso.signup.SignupRequest;
import com.solomonm.yeogiisso.signup.Ui_Signup_Activity;
import com.solomonm.yeogiisso.static_init.Static_Method;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ui_Login_Activity extends AppCompatActivity {
    public static Context Context_Ui_Login_Activity;
    Static_Method Static_User_Info;

    private LinearLayout layout_btn_login_kakao;
    private TextView tv_btn_changepage_signup;

    int networkStatus;
    String ClickLoginType = "";

    //회원정보 저장
    String USER_ID="";
    String USER_NICKNAME="";
    String USER_PROFILE_PIC="";
    String USER_TYPE="";
    String USER_AUTH="";
    int USER_NUMBER=0;
    String USER_QUIT_YN="";
    String USER_QUIT_DT="";

    public void initView()
    {
        this.layout_btn_login_kakao = (LinearLayout)findViewById(R.id.layout_btn_login_kakao);
        this.tv_btn_changepage_signup = (TextView)findViewById(R.id.tv_btn_changepage_signup);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_login_activity);

        Context_Ui_Login_Activity = this;
        Static_User_Info = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
        initView();

        layout_btn_login_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Login_Activity.Context_Ui_Login_Activity);

                Static_User_Info.setClickLoginType("Kakao");
                networkStatus = Class_NetworkStatus.getConnectivityStatus(Ui_Login_Activity.Context_Ui_Login_Activity);

                if (networkStatus != Class_NetworkStatus.TYPE_MOBILE && networkStatus != Class_NetworkStatus.TYPE_WIFI)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Login_Activity",
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
                    if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(Ui_Login_Activity.Context_Ui_Login_Activity))
                    {
                        // 로그인 공통 callback 구성
                        UserApiClient.getInstance().loginWithKakaoTalk(Ui_Login_Activity.Context_Ui_Login_Activity,(oAuthToken, error) -> {
                            if (error != null)
                            {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                Log.e("LOG : ", "로그인 실패", error);
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                        "Ui_Login_Activity",
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
                                                "Ui_Login_Activity",
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

                                        //로그인 진행
                                        CallLoginFunction(String.valueOf(user.getId()), user.getKakaoAccount().getProfile().getNickname(), user.getKakaoAccount().getProfile().getProfileImageUrl());

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
                        UserApiClient.getInstance().loginWithKakaoAccount(Ui_Login_Activity.Context_Ui_Login_Activity,(oAuthToken, error) -> {
                            if (error != null)
                            {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                Log.e("LOG : ", "로그인 실패", error);
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                        "Ui_Login_Activity",
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
                                                "Ui_Login_Activity",
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

                                        //로그인 진행
                                        CallLoginFunction(String.valueOf(user.getId()), user.getKakaoAccount().getProfile().getNickname(), user.getKakaoAccount().getProfile().getProfileImageUrl());

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

        tv_btn_changepage_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startUiSignupActivity = new Intent(Ui_Login_Activity.this, Ui_Signup_Activity.class);
                finish();
                overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                startActivity(startUiSignupActivity);
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
                    case "error_not_member":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        Toast.makeText(Ui_Login_Activity.Context_Ui_Login_Activity, ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.text_checkfunction_alerttitle_signup_refusal), Toast.LENGTH_SHORT).show();
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
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Login_Activity.Context_Ui_Login_Activity);
                        Handler handler_login_for_kakao_loginfail = new Handler();
                        handler_login_for_kakao_loginfail.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                ((Ui_Login_Activity)Ui_Login_Activity.Context_Ui_Login_Activity).layout_btn_login_kakao.callOnClick();
                            }
                        }, 2000);
                        break;
                    case "error_login_for_kakao_loadingfail":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Login_Activity.Context_Ui_Login_Activity);
                        Handler handler_login_for_kakao_loadingfail = new Handler();
                        handler_login_for_kakao_loadingfail.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                ((Ui_Login_Activity)Ui_Login_Activity.Context_Ui_Login_Activity).layout_btn_login_kakao.callOnClick();
                            }
                        }, 2000);
                        break;
                    case "error_server_off":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Login_Activity.Context_Ui_Login_Activity);
                        Handler handler_server_off = new Handler();
                        handler_server_off.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                if(((Ui_Login_Activity)Ui_Login_Activity.Context_Ui_Login_Activity).Static_User_Info.getClickLoginType().equals("Kakao"))
                                {
                                    ((Ui_Login_Activity)Ui_Login_Activity.Context_Ui_Login_Activity).layout_btn_login_kakao.callOnClick();
                                }
                            }
                        }, 2000);
                        break;
                    case "error_not_member":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Login_Activity.Context_Ui_Login_Activity);
                        Handler handler_not_member = new Handler();
                        handler_not_member.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //회원가입 진행
                                UserApiClient.getInstance().me((user, meError) -> {
                                    if (meError != null) {
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                        Log.e("LOG : ", "사용자 정보 요청 실패", meError);
                                        ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                "Ui_Login_Activity",
                                                "error_login_for_kakao_loadingfail",
                                                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_login_for_kakao_loadingfail),
                                                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_cancel),
                                                "empty",
                                                ((Ui_Main_Activity) Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                                "false"
                                        );
                                    } else {
                                        Log.e("LOG : ", "사용자 정보 요청 성공"
                                                        + "\n회원번호 : " + user.getId()
                                                        + "\n닉네임 : " + user.getKakaoAccount().getProfile().getNickname()
                                                        + "\n프로필사진 : " + user.getKakaoAccount().getProfile().getProfileImageUrl()
                                                //+"\n카카오계정이메일 : "+user.getKakaoAccount().getEmail()
                                        );

                                        Static_User_Info = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
                                        NotMemberSignup(String.valueOf(user.getId()), Static_User_Info.getClickLoginType(), user.getKakaoAccount().getProfile().getNickname(), user.getKakaoAccount().getProfile().getProfileImageUrl());
                                    }
                                    return null;
                                });
                            }
                        }, 2000);
                        break;
                    case "login_finish":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "unusual_approach":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.onAppFinish();
                        break;
                    case "overlap_id":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
    }

    public void CallLoginFunction(String user_id, String user_nickname, String user_profile_pic)
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String success = "fail";
                try
                {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("result");

                    success = jsonObject.getString("success");
                    if(success.equals("true"))
                    {

                        USER_ID=jsonArray.getJSONObject(0).getString("account_id");
                        USER_NICKNAME=user_nickname;
                        USER_PROFILE_PIC=user_profile_pic;
                        USER_TYPE=jsonArray.getJSONObject(0).getString("account_type");
                        USER_AUTH=jsonArray.getJSONObject(0).getString("account_auth");
                        if(USER_AUTH.equals("O"))
                        {
                            USER_NUMBER=jsonArray.getJSONObject(0).getInt("account_number");
                        }
                        else
                        {
                            USER_NUMBER=0;
                        }
                        USER_QUIT_YN=jsonArray.getJSONObject(0).getString("account_id_quit_yn");

                        if(USER_QUIT_YN.equals("Y"))
                        {


                            USER_QUIT_DT = jsonArray.getJSONObject(0).getString("account_id_quit_dt");

                            //////
                            //서버에서 실시간 실제시간 받아오기 => 값 비교

                            ////////////////////////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////////////////////////

                            //탈퇴한 회원 => 다시 회원가입진행해야됨 => 탈퇴후1개월지났는지 체크
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                            ////////////////////////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////////////////////////
                            ////////////////////////////////////////////////////////////////////////////





                        }
                        else
                        {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                            Static_Method Static_User_Info = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
                            Static_User_Info.setUserInfo_user_id(USER_ID);
                            Static_User_Info.setUserInfo_user_nickname(USER_NICKNAME);
                            Static_User_Info.setUserInfo_user_profile_pic(USER_PROFILE_PIC);
                            Static_User_Info.setUserInfo_user_type(USER_TYPE);
                            Static_User_Info.setUserInfo_user_auth(USER_AUTH);
                            Static_User_Info.setUserInfo_user_number(USER_NUMBER);
                            Static_User_Info.setUserInfo_user_quit_yn(USER_QUIT_YN);

                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                    "Ui_Login_Activity",
                                    "login_finish",
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish_alerttitle_login_success),
                                    "empty",
                                    "empty",
                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                    "false"
                            );

                            ((Ui_Login_Activity)Ui_Login_Activity.Context_Ui_Login_Activity).finish();
                            ((Ui_Login_Activity)Ui_Login_Activity.Context_Ui_Login_Activity).overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);

                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).onResume();
                        }
                    }
                    else
                    {
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                "Ui_Login_Activity",
                                "error_not_member",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_not_registered_id),
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_no),
                                "empty",
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_yes),
                                "false"
                        );
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();

                    networkStatus = Class_NetworkStatus.getConnectivityStatus(Ui_Login_Activity.Context_Ui_Login_Activity);

                    //인터넷 꺼져있을 때
                    if (networkStatus != Class_NetworkStatus.TYPE_MOBILE && networkStatus != Class_NetworkStatus.TYPE_WIFI)
                    {
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                "Ui_Login_Activity",
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
                                "Ui_Login_Activity",
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
        LoginRequest loginRequest = new LoginRequest(user_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Ui_Login_Activity.Context_Ui_Login_Activity);
        queue.add(loginRequest);
    }

    public void NotMemberSignup(String user_id, String user_type, String user_nickname, String user_profile_pic)
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

                        //회원등록 성공
                        //로그인 진행
                        CallLoginFunction(user_id, user_nickname, user_profile_pic);

                    }
                    else
                    {
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                        if(exists.equals("ID"))
                        {
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                    "Ui_Login_Activity",
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
                                    "Ui_Login_Activity",
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
                                "Ui_Login_Activity",
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
                                "Ui_Login_Activity",
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
        SignupRequest signupRequest = new SignupRequest(user_id, user_type, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Ui_Login_Activity.Context_Ui_Login_Activity);
        queue.add(signupRequest);
    }
}