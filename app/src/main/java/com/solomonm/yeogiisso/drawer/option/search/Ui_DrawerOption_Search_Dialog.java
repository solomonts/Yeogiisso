package com.solomonm.yeogiisso.drawer.option.search;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.widget.Adapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.management.store.Ui_StoreManagement_Activity_Registration_List;
import com.solomonm.yeogiisso.management.store.Ui_StoreManagement_Activity_Registration_ListAdapter;
import com.solomonm.yeogiisso.map.nearbylist.NearbyMarket_List;
import com.solomonm.yeogiisso.service.Class_NetworkStatus;
import com.solomonm.yeogiisso.signup.SignupRequest;
import com.solomonm.yeogiisso.signup.Ui_Signup_Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ui_DrawerOption_Search_Dialog extends Dialog {
    public static Context Context_Ui_DrawerOption_Search_Dialog;

    private LinearLayout layout_draweroption_search_dialog;
    private RecyclerView rv_ctp_sig_emd_list;

    Ui_DrawerOption_Search_Dialog_LocalSearch_ListAdapter localsearchlist_listAdapter;
    List<Ui_DrawerOption_Search_Dialog_LocalSearch_List> localsearch_userList;
    List<Ui_DrawerOption_Search_Dialog_LocalSearch_List> localsearch_saveList;

    String serverUrl = "";
    String Language, Class, Parameter, Parameter_Code;

    public void init()
    {
        this.layout_draweroption_search_dialog = (LinearLayout)findViewById(R.id.layout_draweroption_search_dialog);
        this.rv_ctp_sig_emd_list = (RecyclerView)findViewById(R.id.rv_ctp_sig_emd_list);
    }

    public Ui_DrawerOption_Search_Dialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_draweroption_search_dialog);

        Context_Ui_DrawerOption_Search_Dialog = context;
        init();
        set_dialogSize();

        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog);
        verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_ctp_sig_emd_list.setLayoutManager(new LinearLayoutManager(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, LinearLayoutManager.VERTICAL, false));
        localsearch_userList = new ArrayList<Ui_DrawerOption_Search_Dialog_LocalSearch_List>();
        localsearch_saveList = new ArrayList<Ui_DrawerOption_Search_Dialog_LocalSearch_List>();
        localsearchlist_listAdapter = new Ui_DrawerOption_Search_Dialog_LocalSearch_ListAdapter(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, localsearch_userList, this, localsearch_saveList);
        rv_ctp_sig_emd_list.setAdapter(localsearchlist_listAdapter);

        //attr 색상 관리
        /*
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = Ui_Main_Activity.Context_UiMainActivity.getTheme();
        theme.resolveAttribute(R.attr.color_common_black_softgray, typedValue, true);
        TypedArray arr =
                Ui_Main_Activity.Context_UiMainActivity.obtainStyledAttributes(typedValue.data, new int[]{
                        R.attr.color_common_black_softgray
                });
        text_attrColor_default = arr.getColor(0, -1);
         */

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(Context_Ui_DrawerOption_Search_Dialog != null && isShowing())
        {
            dismiss();
        }
    }

    public void set_dialogSize()
    {
        DisplayMetrics device_display = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getApplicationContext().getResources().getDisplayMetrics();
        int deviceWidth = device_display.widthPixels;
        int deviceHeight = device_display.heightPixels;
        layout_draweroption_search_dialog.setLayoutParams(new FrameLayout.LayoutParams((int)(deviceWidth/1.5), (int)(deviceHeight/1.5)));
    }

    public void setArguments(Bundle args) {
        Language = args.getString("dialog_setting_language");
        Class = args.getString("dialog_setting_class");
        Parameter = args.getString("dialog_setting_parameter");
        Parameter_Code = args.getString("dialog_setting_parameter_code");

        CallList();
    }

    public void CallList()
    {
        if(Class != null)
        {
            switch (Class)
            {
                case "ctp":
                    serverUrl = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.server_api_localsearch_ctp);
                    break;
                case "sig":
                    serverUrl = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.server_api_localsearch_sig);
                    break;
                case "emd":
                    serverUrl = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.server_api_localsearch_emd);
                    break;
            }

            StringRequest stringRequest= new StringRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        int LocalSearch_List_Count = 0;
                        int LocalSearch_List_Null_Count = 0;
                        String ctp = "", sig = "", emd = "", ctp_code = "", sig_code = "";
                        Ui_DrawerOption_Search_Dialog_LocalSearch_List ui_draweroption_search_dialog_localsearch_list;

                        while (LocalSearch_List_Count < jsonArray.length()) {
                            JSONObject object = jsonArray.getJSONObject(LocalSearch_List_Count);
                            switch (Class)
                            {
                                case "ctp":
                                    switch (Language)
                                    {
                                        case "ko":
                                            ctp = object.getString("ctp_kor_nm");
                                            break;
                                        case "en":
                                            ctp = object.getString("ctp_eng_nm");
                                            break;
                                        case "ja":
                                            ctp = object.getString("ctp_eng_nm");
                                            break;
                                        case "zh":
                                            ctp = object.getString("ctp_eng_nm");
                                            break;
                                    }
                                    ctp_code = object.getString("ctprvn_cd");
                                    ui_draweroption_search_dialog_localsearch_list = new Ui_DrawerOption_Search_Dialog_LocalSearch_List(
                                            "ctp",
                                            ctp,
                                            "",
                                            "",
                                            ctp_code,
                                            ""
                                    );
                                    break;
                                case "sig":
                                    switch (Language)
                                    {
                                        case "ko":
                                            sig = object.getString("sig_kor_nm");
                                            break;
                                        case "en":
                                            sig = object.getString("sig_eng_nm");
                                            break;
                                        case "ja":
                                            sig = object.getString("sig_eng_nm");
                                            break;
                                        case "zh":
                                            sig = object.getString("sig_eng_nm");
                                            break;
                                    }
                                    sig_code = object.getString("sig_cd");
                                    ui_draweroption_search_dialog_localsearch_list = new Ui_DrawerOption_Search_Dialog_LocalSearch_List(
                                            "sig",
                                            "",
                                            sig,
                                            "",
                                            "",
                                            sig_code
                                    );
                                    break;
                                case "emd":
                                    switch (Language)
                                    {
                                        case "ko":
                                            emd = object.getString("emd_kor_nm");
                                            break;
                                        case "en":
                                            emd = object.getString("emd_eng_nm");
                                            break;
                                        case "ja":
                                            emd = object.getString("emd_eng_nm");
                                            break;
                                        case "zh":
                                            emd = object.getString("emd_eng_nm");
                                            break;
                                    }
                                    ui_draweroption_search_dialog_localsearch_list = new Ui_DrawerOption_Search_Dialog_LocalSearch_List(
                                            "emd",
                                            "",
                                            "",
                                            emd,
                                            "",
                                            ""
                                    );
                                    break;
                                default:
                                    throw new IllegalStateException("Unexpected value : " + Class);
                            }

                            localsearch_userList.add(ui_draweroption_search_dialog_localsearch_list);
                            localsearch_saveList.add(ui_draweroption_search_dialog_localsearch_list);

                            LocalSearch_List_Count++;
                            LocalSearch_List_Null_Count++;
                        }

                        if (LocalSearch_List_Null_Count == 0) //서버에 자료가 존재하지 않으면
                        {
                            localsearchlist_listAdapter.notifyDataSetChanged();
                            //Toast.makeText(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, "데이터없음", Toast.LENGTH_SHORT).show();
                        }
                        else //출력완료
                        {
                            localsearchlist_listAdapter.notifyDataSetChanged();
                            //Toast.makeText(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, "성공", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) //서버 에러
                    {
                        e.printStackTrace();
                        //Toast.makeText(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, "에러", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }){
                @Override //response를 UTF8로 변경해주는 소스코드
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    try {
                        String utf8String = new String(response.data, "UTF-8");
                        return Response.success(utf8String, HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
                        // log error
                        return Response.error(new ParseError(e));
                    } catch (Exception e) {
                        // log error
                        return Response.error(new ParseError(e));
                    }
                }

                //POST 방식으로 보낼 데이터를
                //리턴해주는 콜백 메소드
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    HashMap<String, String> datas= new HashMap<>();
                    switch (Class)
                    {
                        case "sig":
                            datas.put("ctpCd", Parameter_Code);
                            break;
                        case "emd":
                            datas.put("sigCd", Parameter_Code);
                            break;
                    }
                    return datas;

                }
            };
            RequestQueue requestQueue= Volley.newRequestQueue(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog);
            requestQueue.add(stringRequest);

            /*
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        int LocalSearch_Ctp_List_Count = 0;
                        int LocalSearch_Ctp_List_Null_Count = 0;
                        String ctp_ko = "", ctp_en = "", ctp_ja = "", ctp_zh = "", ctp_code = "";

                        while (LocalSearch_Ctp_List_Count < jsonArray.length()) {
                            JSONObject object = jsonArray.getJSONObject(LocalSearch_Ctp_List_Count);
                            ctp_ko = object.getString("ctp_kor_nm");
                            ctp_en = object.getString("ctp_eng_nm");
                            ctp_ja = object.getString("ctp_eng_nm");
                            ctp_zh = object.getString("ctp_eng_nm");
                            ctp_code = object.getString("ctprvn_cd");

                            LocalSearch_Ctp_List_Count++;
                            LocalSearch_Ctp_List_Null_Count++;

                        }

                        if (LocalSearch_Ctp_List_Null_Count == 0) //서버에 자료가 존재하지 않으면
                        {
                            Toast.makeText(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, "데이터없음", Toast.LENGTH_SHORT).show();
                        }
                        else //출력완료
                        {
                            Toast.makeText(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, "성공", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) //서버 에러
                    {
                        e.printStackTrace();
                        Toast.makeText(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, "에러", Toast.LENGTH_SHORT).show();
                    }
                }
            };

            switch (Class)
            {
                case "ctp":
                    LocalSearchCtpRequest localSearchCtpRequest = new LocalSearchCtpRequest(Language, Class, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog);
                    queue.add(localSearchCtpRequest);
                    break;
                case "sig":
                    break;
                case "emd":
                    break;
            }
             */
        }
    }
}
