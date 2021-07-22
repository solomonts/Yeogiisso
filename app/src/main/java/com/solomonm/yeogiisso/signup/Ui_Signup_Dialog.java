package com.solomonm.yeogiisso.signup;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.service.Class_NetworkStatus;

import org.json.JSONObject;

public class Ui_Signup_Dialog extends Dialog {
    public static Context Context_Ui_Signup_Dialog;

    /*
    private EditText et_id, et_pw, et_nm;
    private TextView tv_id_error, tv_id_errormark, tv_pw_error, tv_pw_errormark, tv_nickname_error, tv_nickname_errormark, tv_login;
    private Button button_signup;
    private RadioButton rb1, rb2;

    String USER_ID;
    String USER_PW;
    String USER_NM;
    String USER_AUTH;
    String ID_STATE="false";
    String PW_STATE="false";
    String NICKNAME_STATE="false";
    int networkStatus;
     */

    public void initView()
    {
        /*
        this.et_id = (EditText)findViewById(R.id.et_id);
        this.et_pw = (EditText)findViewById(R.id.et_pw);
        this.et_nm = (EditText)findViewById(R.id.et_nm);
        this.tv_id_error = (TextView)findViewById(R.id.tv_id_error);
        this.tv_pw_error = (TextView)findViewById(R.id.tv_pw_error);
        this.tv_nickname_error = (TextView)findViewById(R.id.tv_nickname_error);
        this.tv_id_errormark = (TextView)findViewById(R.id.tv_id_errormark);
        this.tv_pw_errormark = (TextView)findViewById(R.id.tv_pw_errormark);
        this.tv_nickname_errormark = (TextView)findViewById(R.id.tv_nickname_errormark);
        this.tv_login = (TextView)findViewById(R.id.tv_login);
        this.button_signup = (Button)findViewById(R.id.button_signup);
        this.rb1 = (RadioButton)findViewById(R.id.rb1);
        this.rb2 = (RadioButton)findViewById(R.id.rb2);
         */
    }

    public Ui_Signup_Dialog(Context context) {
        super(context);
        Context_Ui_Signup_Dialog = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_signup_dialog);

        initView();

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
                            "Ui_Signup_Dialog",
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
                            "Ui_Signup_Dialog",
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
         */

        /*
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
                            "Ui_Signup_Dialog",
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
                            "Ui_Signup_Dialog",
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
         */

        /*
        et_nm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                NICKNAME_STATE = "false";
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(et_nm.getText().toString().length() < 0)
                {
                    NICKNAME_STATE = "false";
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Signup_Dialog",
                            "unusual_approach",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                            "true"
                    );
                }
                else if(et_nm.getText().toString().length() == 0)
                {
                    NICKNAME_STATE = "false";
                    tv_nickname_error.setVisibility(View.INVISIBLE);
                    tv_nickname_errormark.setVisibility(View.INVISIBLE);
                }
                else if(et_nm.getText().toString().length() == 1)
                {
                    NICKNAME_STATE = "false";
                    tv_nickname_error.setVisibility(View.VISIBLE);
                    tv_nickname_error.setText(et_nm.getText().toString().length()+"/2~50");
                    tv_nickname_errormark.setVisibility(View.VISIBLE);
                }
                else if(et_nm.getText().toString().length() >= 2 && et_pw.getText().toString().length() <= 12)
                {
                    NICKNAME_STATE = "true";
                    tv_nickname_error.setVisibility(View.VISIBLE);
                    tv_nickname_error.setText(et_nm.getText().toString().length()+"/2~50");
                    tv_nickname_errormark.setVisibility(View.INVISIBLE);
                }
                else if(et_nm.getText().toString().length() > 12)
                {
                    NICKNAME_STATE = "false";
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Signup_Dialog",
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
         */

        /*
        button_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USER_ID = et_id.getText().toString();
                USER_PW = et_pw.getText().toString();
                USER_NM = et_nm.getText().toString();
                if(rb1.isChecked() == true)
                {
                    USER_AUTH = "U";
                }
                else
                {
                    USER_AUTH = "O";
                }

                if(USER_ID.length() == 0)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Signup_Dialog",
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
                            "Ui_Signup_Dialog",
                            "missing_pwdata",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_missing_pwdata),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "false"
                    );
                }
                else if(USER_NM.length() == 0)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Signup_Dialog",
                            "missing_nicknamedata",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_missing_nicknamedata),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "false"
                    );
                }
                else if(ID_STATE.equals("false") || PW_STATE.equals("false") || NICKNAME_STATE.equals("false") || USER_ID.length() < 8 || USER_PW.length() < 8 || USER_NM.length() < 2 || USER_ID.length() > 20 || USER_PW.length() > 50 || USER_NM.length() > 12)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Signup_Dialog",
                            "check_information",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_check_idpw),
                            "empty",
                            "empty",
                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                            "false"
                    );
                }
                else if(ID_STATE.equals("true") && PW_STATE.equals("true") && NICKNAME_STATE.equals("true") && USER_ID.length() >= 8 && USER_PW.length() >= 8 && USER_NM.length() >= 2 && USER_ID.length() <= 20 && USER_PW.length() <= 50 && USER_NM.length() <= 12)
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity);
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    String message = "";
                                    String success = "false";
                                    String exists = "";
                                    try
                                    {
                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();

                                        JSONObject jsonObject = new JSONObject(response);

                                        message = jsonObject.getString("message");
                                        success = jsonObject.getString("success");
                                        exists = jsonObject.getString("exists");

                                        if(success.equals("false"))
                                        {
                                            if(exists.equals("ID"))
                                            {
                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                        "Ui_Signup_Dialog",
                                                        "overlap_email",
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_overlap_email),
                                                        "empty",
                                                        "empty",
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                                        "false"
                                                );
                                            }
                                            else if(exists.equals("NICKNAME"))
                                            {
                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                        "Ui_Signup_Dialog",
                                                        "overlap_nickname",
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_overlap_nickname),
                                                        "empty",
                                                        "empty",
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_ok),
                                                        "false"
                                                );
                                            }
                                            else
                                            {
                                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                        "Ui_Signup_Dialog",
                                                        "unusual_approach",
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_0ERO0101_unusual_approach_title)+"\nERRORCODE : "+((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.error_unusualapproach_ERO01),
                                                        "empty",
                                                        "empty",
                                                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_finish),
                                                        "true"
                                                );
                                            }
                                        }
                                        else if (success.equals("true"))
                                        {
                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_Signup_Dialog_End();
                                            ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                                                    "Ui_Signup_Dialog",
                                                    "success_signup",
                                                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getResources().getString(R.string.text_checkfunction_alerttitle_success_signup),
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
                                                    "Ui_Signup_Dialog",
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
                                                    "Ui_Signup_Dialog",
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
                            //SignupRequest signupRequest = new SignupRequest(USER_ID, USER_PW, USER_NM, USER_AUTH, responseListener);
                            SignupRequest signupRequest = new SignupRequest(USER_ID, responseListener);
                            RequestQueue queue = Volley.newRequestQueue(Ui_Signup_Dialog.Context_Ui_Signup_Dialog);
                            queue.add(signupRequest);
                        }
                    }, 1000);
                }
                else
                {
                    ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_AlertDialog_Dialog_Start(
                            "Ui_Signup_Dialog",
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
         */

        /*
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_Signup_Dialog_End();
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_Login_Dialog_Start();
            }
        });
         */

    }

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
                    case "error_network_off":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity);
                        Handler handler_network_off = new Handler();
                        handler_network_off.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                button_signup.callOnClick();
                            }
                        }, 1000);
                        break;
                    case "error_server_off":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_Start(Ui_Main_Activity.Context_UiMainActivity);
                        Handler handler_server_off = new Handler();
                        handler_server_off.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).initFunction.Ui_FullScreenWait_Dialog_End();
                                button_signup.callOnClick();
                            }
                        }, 1000);
                        break;
                    case "missing_iddata":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "missing_pwdata":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "missing_nicknamedata":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "success_signup":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "check_information":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "overlap_email":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                    case "overlap_nickname":
                        ((Ui_AlertDialog_Activity)Ui_AlertDialog_Activity.Context_Ui_AlertDialog_Dialog).call_AlertDialog_finish();
                        break;
                }
                break;
        }
    }
     */
}
