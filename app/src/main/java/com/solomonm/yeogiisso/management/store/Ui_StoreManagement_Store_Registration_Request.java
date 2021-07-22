package com.solomonm.yeogiisso.management.store;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;

import java.util.HashMap;
import java.util.Map;

public class Ui_StoreManagement_Store_Registration_Request extends StringRequest {

    final static private String URL = Ui_Main_Activity.Context_UiMainActivity.getString(R.string.server_api_store_registration);

    private Map<String, String> parameters;

    public Ui_StoreManagement_Store_Registration_Request(String user_id, String store_name, String store_picname, String store_openweek, String store_opentime, String store_endtime, String storecate_main, String storecate_sub, int store_number, String store_explanation, String store_dist, double store_lat, double store_lng, Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("ACCOUNT_ID", user_id);
        parameters.put("STORE_NAME", store_name);
        parameters.put("STORE_PICNAME", store_picname);
        parameters.put("STORE_OPENWEEK", store_openweek);
        parameters.put("STORE_OPENTIME", store_opentime);
        parameters.put("STORE_ENDTIME", store_endtime);
        parameters.put("STORE_CATEMAIN", storecate_main);
        parameters.put("STORE_CATESUB", storecate_sub);
        parameters.put("STORE_NUMBER", store_number+"");
        parameters.put("STORE_CONTENTS", store_explanation);
        parameters.put("STORE_DIST", store_dist);
        parameters.put("STORE_XPOS", store_lat+"");
        parameters.put("STORE_YPOS", store_lng+"");
    }

    @Override
    public Map<String, String> getParams()
    {
        return parameters;
    }

}
