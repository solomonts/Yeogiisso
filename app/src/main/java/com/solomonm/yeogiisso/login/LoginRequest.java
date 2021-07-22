package com.solomonm.yeogiisso.login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    final static private String URL = Ui_Main_Activity.Context_UiMainActivity.getString(R.string.server_api_login);

    private Map<String, String> parameters;

    public LoginRequest(String USER_ID, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ACCOUNT_ID", USER_ID);
    }

    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }

}