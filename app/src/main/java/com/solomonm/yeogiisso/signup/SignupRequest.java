package com.solomonm.yeogiisso.signup;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;

import java.util.HashMap;
import java.util.Map;

public class SignupRequest extends StringRequest {

    final static private String URL = Ui_Main_Activity.Context_UiMainActivity.getString(R.string.server_api_signup);

    private Map<String, String> parameters;

    public SignupRequest(String USER_ID, String USER_TYPE, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ACCOUNT_ID", USER_ID);
        parameters.put("ACCOUNT_TYPE", USER_TYPE);
    }

    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }

}
