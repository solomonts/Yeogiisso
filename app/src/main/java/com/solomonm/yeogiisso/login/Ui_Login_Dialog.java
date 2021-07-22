package com.solomonm.yeogiisso.login;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.user.UserApiClient;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.service.BackgroundTask_SERVER_Connect;
import com.solomonm.yeogiisso.service.Class_NetworkStatus;
import com.solomonm.yeogiisso.static_init.Static_Method;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import kotlin.Unit;

public class Ui_Login_Dialog extends Dialog {
    public static Context Context_Ui_Login_Dialog;

    //////////////////////////////////////////////////////////////////////
    //기존 자체 로그인 시스템 (추후 사용가능하도록 코드 유지)
    //////////////////////////////////////////////////////////////////////
    /*
    private EditText et_id, et_pw;
    private TextView tv_id_error, tv_pw_error, tv_id_errormark, tv_pw_errormark, tv_signup;
    private Button button_login;

    String USER_ID;
    String USER_PW;
    String USER_NM;
    String USER_AUTH;
    String ID_STATE="false";
    String PW_STATE="false";
    int networkStatus;

    AlertDialog.Builder alertDialog;
     */

    int networkStatus;

    private  ImageView iv_btn_login_kakao;

    public void initView()
    {
        //////////////////////////////////////////////////////////////////////
        //기존 자체 로그인 시스템 (추후 사용가능하도록 코드 유지)
        //////////////////////////////////////////////////////////////////////
        /*
        this.et_id = (EditText)findViewById(R.id.et_id);
        this.et_pw = (EditText)findViewById(R.id.et_pw);
        this.tv_id_error = (TextView)findViewById(R.id.tv_id_error);
        this.tv_pw_error = (TextView)findViewById(R.id.tv_pw_error);
        this.tv_id_errormark = (TextView)findViewById(R.id.tv_id_errormark);
        this.tv_pw_errormark = (TextView)findViewById(R.id.tv_pw_errormark);
        this.tv_signup = (TextView)findViewById(R.id.tv_signup);
        this.button_login = (Button)findViewById(R.id.button_login);
         */

        this.iv_btn_login_kakao = (ImageView)findViewById(R.id.iv_btn_login_kakao);
    }

    public Ui_Login_Dialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_login_dialog);

        Context_Ui_Login_Dialog = context;
        initView();

        //////////////////////////////////////////////////////////////////////
        //기존 자체 로그인 시스템 (추후 사용가능하도록 코드 유지)
        //////////////////////////////////////////////////////////////////////
        /*
        et_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                ID_STATE = "false";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_id.getText().toString().length() < 0)
                {
                    ID_STATE = "false";
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Login_Dialog",
                            "unusual_approach",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                            "true"
                    );
                }
                else if(et_id.getText().toString().length() == 0)
                {
                    ID_STATE = "false";
                    tv_id_error.setVisibility(View.INVISIBLE);
                    tv_id_errormark.setVisibility(View.INVISIBLE);
                }
                else if(et_id.getText().toString().length() >= 1 && et_id.getText().toString().length() <8)
                {
                    ID_STATE = "false";
                    tv_id_error.setVisibility(View.VISIBLE);
                    tv_id_error.setText(et_id.getText().toString().length()+"/8~20");
                    tv_id_errormark.setVisibility(View.VISIBLE);
                }
                else if(et_id.getText().toString().length() >= 8 && et_id.getText().toString().length() <= 20)
                {
                    ID_STATE = "true";
                    tv_id_error.setVisibility(View.VISIBLE);
                    tv_id_error.setText(et_id.getText().toString().length()+"/8~20");
                    tv_id_errormark.setVisibility(View.INVISIBLE);
                }
                else if(et_id.getText().toString().length() > 20)
                {
                    ID_STATE = "false";
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Login_Dialog",
                            "unusual_approach",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                            "true"
                    );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        et_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                PW_STATE = "false";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_pw.getText().toString().length() < 0)
                {
                    PW_STATE = "false";
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Login_Dialog",
                            "unusual_approach",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                            "true"
                    );
                }
                else if(et_pw.getText().toString().length() == 0)
                {
                    PW_STATE = "false";
                    tv_pw_error.setVisibility(View.INVISIBLE);
                    tv_pw_errormark.setVisibility(View.INVISIBLE);
                }
                else if(et_pw.getText().toString().length() >= 1 && et_pw.getText().toString().length() <8)
                {
                    PW_STATE = "false";
                    tv_pw_error.setVisibility(View.VISIBLE);
                    tv_pw_error.setText(et_pw.getText().toString().length()+"/8~50");
                    tv_pw_errormark.setVisibility(View.VISIBLE);
                }
                else if(et_pw.getText().toString().length() >= 8 && et_pw.getText().toString().length() <= 50)
                {
                    PW_STATE = "true";
                    tv_pw_error.setVisibility(View.VISIBLE);
                    tv_pw_error.setText(et_pw.getText().toString().length()+"/8~50");
                    tv_pw_errormark.setVisibility(View.INVISIBLE);
                }
                else if(et_pw.getText().toString().length() > 50)
                {
                    PW_STATE = "false";
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Login_Dialog",
                            "unusual_approach",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                            "true"
                    );
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USER_ID = et_id.getText().toString();
                USER_PW = et_pw.getText().toString();
                if(ID_STATE.equals("true") && PW_STATE.equals("true") && USER_ID.length() >= 8 && USER_PW.length() >= 8 && USER_ID.length() <= 20 && USER_PW.length() <= 50)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String success = "fail";
                                    try
                                    {
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                                        JSONObject jsonObject = new JSONObject(response);
                                        JSONArray jsonArray = jsonObject.getJSONArray("result");

                                        success = jsonObject.getString("success");
                                        if(success.equals("success"))
                                        {
                                            USER_ID = jsonArray.getJSONObject(0).getString("user_id");
                                            USER_NM = jsonArray.getJSONObject(0).getString("user_nm");
                                            USER_AUTH = jsonArray.getJSONObject(0).getString("user_auth");

                                            Static_Method Static_User_Info = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
                                            Static_User_Info.setUserInfo_user_id(USER_ID);
                                            Static_User_Info.setUserInfo_user_nickname(USER_NM);
                                            Static_User_Info.setUserInfo_user_auth(USER_AUTH);
                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_Login_Dialog_End();
                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).onResume();
                                        }
                                        else
                                        {
                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                    "Ui_Login_Dialog",
                                                    "matching_memberinformation",
                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_matching_memberinformation),
                                                    "empty",
                                                    "empty",
                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                                    "false"
                                            );
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                                        //인터넷 꺼져있을 때
                                        if (networkStatus != Class_NetworkStatus.TYPE_MOBILE && networkStatus != Class_NetworkStatus.TYPE_WIFI)
                                        {
                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                    "Ui_Login_Dialog",
                                                    "error_network_off",
                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0101_server_connect_network_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0102_server_connect_network_errorcode),
                                                    "empty",
                                                    "empty",
                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                                                    "true"
                                            );
                                        }
                                        else
                                        {
                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                    "Ui_Login_Dialog",
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
                            LoginRequest loginRequest = new LoginRequest(USER_ID, USER_PW, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(Ui_Login_Dialog.Context_Ui_Login_Dialog);
                            queue.add(loginRequest);
                        }
                    }, 1000);
                }
                else if(USER_ID.length() == 0)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Login_Dialog",
                            "missing_iddata",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_missing_iddata),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "false"
                    );
                }
                else if(USER_PW.length() == 0)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Login_Dialog",
                            "missing_pwdata",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_missing_pwdata),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "false"
                    );
                }
                else if(ID_STATE.equals("false") || PW_STATE.equals("false") || USER_ID.length() < 8 || USER_PW.length() < 8 || USER_ID.length() > 20 || USER_PW.length() > 50)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Login_Dialog",
                            "check_idpw",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_check_idpw),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "false"
                    );
                }
                else
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Login_Dialog",
                            "unusual_approach",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                            "true"
                    );
                }
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_Login_Dialog_End();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_Signup_Dialog_Start();
            }
        });
         */

        iv_btn_login_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkStatus = Class_NetworkStatus.getConnectivityStatus(Ui_Login_Dialog.Context_Ui_Login_Dialog);
                if (networkStatus != Class_NetworkStatus.TYPE_MOBILE && networkStatus != Class_NetworkStatus.TYPE_WIFI)
                {
                    //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End(); //로딩화면 종료
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Login_Dialog",
                            "error_network_off",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0101_server_connect_network_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0CON0102_server_connect_network_errorcode),
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_cancel),
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_retry),
                            "true"
                    );
                }
                else
                {
                    //카카오톡 앱이 설치되어있을 경우
                    if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(Ui_Login_Dialog.Context_Ui_Login_Dialog))
                    {
                        // 로그인 공통 callback 구성
                        UserApiClient.getInstance().loginWithKakaoTalk(Ui_Login_Dialog.Context_Ui_Login_Dialog,(oAuthToken, error) -> {
                            if (error != null)
                            {
                                Log.e("LOG : ", "로그인 실패", error);
                            }
                            else if (oAuthToken != null)
                            {
                                Log.e("LOG : ", "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                                UserApiClient.getInstance().me((user, meError) -> {
                                    if(meError != null)
                                    {
                                        Log.e("LOG : ", "사용자 정보 요청 실패", meError);
                                    }
                                    else
                                    {
                                        Log.e("LOG : ", "사용자 정보 요청 성공"
                                                +"\n회원번호 : "+user.getId()
                                                +"\n닉네임 : "+user.getKakaoAccount().getProfile().getNickname()
                                                +"\n프로필사진 : "+user.getKakaoAccount().getProfile().getProfileImageUrl()
                                                +"\n카카오계정이메일 : "+user.getKakaoAccount().getEmail()
                                        );
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
                        UserApiClient.getInstance().loginWithKakaoAccount(Ui_Login_Dialog.Context_Ui_Login_Dialog,(oAuthToken, error) -> {
                            if (error != null)
                            {
                                Log.e("LOG : ", "로그인 실패", error);
                            }
                            else if (oAuthToken != null)
                            {
                                Log.e("LOG : ", "로그인 성공(토큰) : " + oAuthToken.getAccessToken());
                                UserApiClient.getInstance().me((user, meError) -> {
                                    if(meError != null)
                                    {
                                        Log.e("LOG : ", "사용자 정보 요청 실패", meError);
                                    }
                                    else
                                    {
                                        Log.e("LOG : ", "사용자 정보 요청 성공"
                                                +"\n회원번호 : "+user.getId()
                                                +"\n닉네임 : "+user.getKakaoAccount().getProfile().getNickname()
                                                +"\n프로필사진 : "+user.getKakaoAccount().getProfile().getProfileImageUrl()
                                                +"\n카카오계정이메일 : "+user.getKakaoAccount().getEmail()
                                        );
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

    }

    //////////////////////////////////////////////////////////////////////
    //기존 자체 로그인 시스템 (추후 사용가능하도록 코드 유지)
    //////////////////////////////////////////////////////////////////////
    /*
    public void setArguments(Bundle args) {
        USER_ID="";
        USER_PW="";
        USER_NM="";
        USER_AUTH="";
        ID_STATE="false";
        PW_STATE="false";
        String result = args.getString("result");
        String parameter_data = args.getString("parameter_data");
        switch (result)
        {
            case "cancel" :
                switch (parameter_data)
                {
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
                    case "unusual_approach":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.onAppFinish();
                        break;
                    case "matching_memberinformation":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "error_network_off":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start();
                        Handler handler_network_off = new Handler();
                        handler_network_off.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                button_login.callOnClick();
                            }
                        }, 2000);
                        break;
                    case "error_server_off":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start();
                        Handler handler_server_off = new Handler();
                        handler_server_off.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                button_login.callOnClick();
                            }
                        }, 2000);
                        break;
                    case "missing_iddata":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "missing_pwdata":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "check_idpw":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                }
                break;
        }
    }
     */

    public void setArguments(Bundle args) {
        String result = args.getString("result");
        String parameter_data = args.getString("parameter_data");
        switch (result)
        {
            case "cancel" :
                switch (parameter_data)
                {
                    case "error_network_off":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
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
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Login_Dialog.Context_Ui_Login_Dialog);
                        Handler handler_network_off = new Handler();
                        handler_network_off.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                iv_btn_login_kakao.callOnClick();
                            }
                        }, 2000);
                        break;
                }
                break;
        }
    }

}
