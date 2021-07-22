package com.solomonm.yeogiisso.management.store;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
import com.solomonm.yeogiisso.dialog.alertdialog.Ui_AlertDialog_Activity;
import com.solomonm.yeogiisso.drawer.option.search.Ui_DrawerOption_Search_Dialog;
import com.solomonm.yeogiisso.drawer.option.search.Ui_DrawerOption_Search_Dialog_LocalSearch_List;
import com.solomonm.yeogiisso.map.nearbylist.NearbyMarket_List;
import com.solomonm.yeogiisso.map.nearbylist.NearbyMarket_ListAdapter;
import com.solomonm.yeogiisso.static_init.Static_Function_Management;
import com.solomonm.yeogiisso.static_init.Static_Method;
import com.solomonm.yeogiisso.static_init.Static_initView_Management;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ui_StoreManagement_Activity extends AppCompatActivity {
    public static Context Context_Ui_StoreManagement_Activity;
    Static_initView_Management static_initview_management;
    Static_Function_Management static_function_management;
    Static_Method static_method;
    Ui_Main_Activity ui_main_activity;

    private Ui_StoreManagement_Activity_Registration_ListAdapter registrationstorelist_listAdapter;
    private List<Ui_StoreManagement_Activity_Registration_List> registrationstore_userList;
    private List<Ui_StoreManagement_Activity_Registration_List> registrationstore_saveList;
    String result_registrationstorelist = null;

    public void init()
    {
        static_initview_management = new Static_initView_Management();
        static_function_management = new Static_Function_Management();
        static_method = (Static_Method) Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity.getApplicationContext();
        ui_main_activity = new Ui_Main_Activity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_management_storemanagement_activity);
        Context_Ui_StoreManagement_Activity = this;

        init();

        //Call_BackgroundTask_RegistrationStoreList();

        static_initview_management.layout_storemanagement_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        static_initview_management.layout_storemanagement_store_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //위도,경도로 주소변환
                //final Geocoder geocoder = new Geocoder(Context_MainActivity);
                //List list = null;
                Geocoder mGeoCoder = new Geocoder(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity);
                List<Address> mResultList = null;
                try
                {
                    //위도, 경도 입력시 주소 출력
                    /*
                    //Double d1 = Double.parseDouble(et1.getText().toString());
                    Double d_lat = static_method.getlat_gps();
                    Double d_lng = static_method.getlng_gps();

                    mResultList = mGeoCoder.getFromLocation(
                            d_lat, //위도
                            d_lng, //경도
                            10); //얻어올 값의 개수
                     */

                    //주소 입력시 위도, 경도 출력
                    /*
                    String str = "서울특별시 영등포구";
                    
                    mResultList = mGeoCoder.getFromLocationName(
                            str, //지역 이름
                            10); //읽을 개수
                     */

                    Double d_lat = static_method.getlat_gps();
                    Double d_lng = static_method.getlng_gps();

                    mResultList = mGeoCoder.getFromLocation(
                            d_lat, //위도
                            d_lng, //경도
                            1); //얻어올 값의 개수

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    Log.e("ERROR", "입출력 오류 - 서버에서 주소변환시 에러발생");
                }
                if(mResultList != null)
                {
                    if(mResultList.size() == 0)
                    {
                        Toast.makeText(Ui_StoreManagement_Activity.this, "해당되는 주소 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //static_function_management.Ui_StoreManagement_Store_Registration_Activity_Start();
                        Intent intent = new Intent(Ui_StoreManagement_Activity.this, Ui_StoreManagement_Store_Registration_Activity.class);
                        //intent.putExtra("str_dist", ""+mResultList.get(0).getAddressLine(0)); //위도, 경도 입력시 주소 출력
                        //intent.putExtra("str_dist", ""+mResultList.get(0).getLatitude()+", "+mResultList.get(0).getLongitude()); //주소 입력시 위도, 경도 출력
                        intent.putExtra("str_dist", ""+mResultList.get(0).getAddressLine(0));
                        startActivity(intent);
                        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
                    }
                }

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        Call_BackgroundTask_RegistrationStoreList();
    }

    public void Call_BackgroundTask_RegistrationStoreList()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onRegistrationStoreList();
            }
        });
    }

    /*
    class BackgroundTask_RegistrationStoreList extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute()
        {
            target = getString(R.string.server_api_list_store_registration)+"?USER_ID="+static_method.getUserInfo_user_id();
        }
        @Override
        protected String doInBackground(Void... voids)
        {
            try
            {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        public void onPostExecute(String result)
        {
            result_registrationstorelist = result;
            if(result != null)
            {
                //새로고침
                //onResume();
                onRegistrationStoreList();
            }
        }
    }
     */

    public void onRegistrationStoreList()
    {
        /*
        LinearLayoutManager verticalLayoutManager = new LinearLayoutManager(Ui_StoreManagement_Activity.this);
        verticalLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        static_initview_management.rv_registered_store_list.setLayoutManager(new LinearLayoutManager(Ui_StoreManagement_Activity.this, LinearLayoutManager.VERTICAL, false));
        registrationstore_userList = new ArrayList<Ui_StoreManagement_Activity_Registration_List>();
        registrationstore_saveList = new ArrayList<Ui_StoreManagement_Activity_Registration_List>();
        registrationstorelist_listAdapter = new Ui_StoreManagement_Activity_Registration_ListAdapter(Ui_StoreManagement_Activity.this, registrationstore_userList, Ui_StoreManagement_Activity.this, registrationstore_saveList);
        static_initview_management.rv_registered_store_list.setAdapter(registrationstorelist_listAdapter);
         */

        GridLayoutManager mGridLayoutManager = new GridLayoutManager(Ui_StoreManagement_Activity.this, 2);
        static_initview_management.rv_registered_store_list.setLayoutManager(mGridLayoutManager);
        registrationstore_userList = new ArrayList<Ui_StoreManagement_Activity_Registration_List>();
        registrationstore_saveList = new ArrayList<Ui_StoreManagement_Activity_Registration_List>();
        registrationstorelist_listAdapter = new Ui_StoreManagement_Activity_Registration_ListAdapter(Ui_StoreManagement_Activity.this, registrationstore_userList, Ui_StoreManagement_Activity.this, registrationstore_saveList);
        static_initview_management.rv_registered_store_list.setAdapter(registrationstorelist_listAdapter);

        String serverUrl = ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.server_api_list_store_registration);

        StringRequest stringRequest= new StringRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    int Store_RegistrationList_List_Count = 0;
                    int Store_RegistrationList_List_Null_Count = 0;

                    String STORE_USERID = "", STORE_NM = "", STORE_PICKEY = "", STORE_DW = "", STORE_TC_YN = "", STORE_TC_DT = "",
                            STORE_ST_TM = "", STORE_ED_TM = "", STORE_CATE_MAIN = "", STORE_CATE_SUB = "",
                            STORE_NB = "", STORE_CNT = "",
                            STORE_DIST = "", STORE_DIST_XPOS = "", STORE_DIST_YPOS = "",
                            STORE_REG_DT = "", STORE_UDT_DT = "", STORE_DEL_YN = "", STORE_DEL_DT = "",
                            ATTACH_SV_PATH = "", ATTACH_SV_FNM = "", ATTACH_DEL_YN = "";
                    Ui_StoreManagement_Activity_Registration_List ui_storemanagement_activity_registration_list;

                    while (Store_RegistrationList_List_Count < jsonArray.length()) {
                        JSONObject object = jsonArray.getJSONObject(Store_RegistrationList_List_Count);

                        STORE_USERID = object.getString("store_userid");
                        if(!STORE_USERID.equals(static_method.getUserInfo_user_id()))
                        {
                            ///////////////////////////////
                            ///////////////////////////////
                            ///////////////////////////////
                            //잘못된 접근
                            ///////////////////////////////
                            ///////////////////////////////
                            ///////////////////////////////
                            return;
                        }
                        STORE_NM = object.getString("store_nm");
                        if(object.getString("store_pickey") != null && !object.getString("store_pickey").equals(""))
                        {
                            STORE_PICKEY = object.getString("store_pickey");
                        }
                        else
                        {
                            STORE_PICKEY = "";
                        }
                        STORE_DW = object.getString("store_dw");
                        STORE_TC_YN = object.getString("store_tc_yn");
                        if(object.getString("store_tc_yn").equals("Y"))
                        {
                            STORE_TC_DT = object.getString("store_tc_dt");
                        }
                        else
                        {
                            STORE_TC_DT = "";
                        }
                        STORE_ST_TM = object.getString("store_st_tm");
                        STORE_ED_TM = object.getString("store_ed_tm");
                        STORE_CATE_MAIN = object.getString("store_cate_main");
                        STORE_CATE_SUB = object.getString("store_cate_sub");
                        if(object.getString("store_nb").equals("0"))
                        {
                            STORE_NB = "";
                        }
                        else
                        {
                            STORE_NB = object.getString("store_nb");
                        }
                        STORE_CNT = object.getString("store_cnt");
                        STORE_DIST = object.getString("store_dist");
                        STORE_DIST_XPOS = object.getString("store_dist_xpos");
                        STORE_DIST_YPOS = object.getString("store_dist_ypos");
                        STORE_REG_DT = object.getString("store_reg_dt");
                        STORE_UDT_DT = object.getString("store_udt_dt");
                        STORE_DEL_YN = object.getString("store_del_yn");
                        if(object.getString("store_del_yn").equals("Y"))
                        {
                            STORE_DEL_DT = object.getString("store_del_dt");
                        }
                        else
                        {
                            STORE_DEL_DT = "";
                        }
                        ATTACH_SV_PATH = object.getString("attach_sv_path");
                        ATTACH_SV_FNM = object.getString("attach_sv_fnm");
                        ATTACH_DEL_YN = object.getString("attach_del_yn");

                        ui_storemanagement_activity_registration_list = new Ui_StoreManagement_Activity_Registration_List(
                                STORE_USERID,
                                STORE_NM,
                                STORE_PICKEY,
                                STORE_DW,
                                STORE_TC_YN,
                                STORE_TC_DT,
                                STORE_ST_TM,
                                STORE_ED_TM,
                                STORE_CATE_MAIN,
                                STORE_CATE_SUB,
                                STORE_NB,
                                STORE_CNT,
                                STORE_DIST,
                                STORE_DIST_XPOS,
                                STORE_DIST_YPOS,
                                STORE_REG_DT,
                                STORE_UDT_DT,
                                STORE_DEL_YN,
                                STORE_DEL_DT,
                                ATTACH_SV_PATH,
                                ATTACH_SV_FNM,
                                ATTACH_DEL_YN
                        );
                        registrationstore_userList.add(ui_storemanagement_activity_registration_list);
                        registrationstore_saveList.add(ui_storemanagement_activity_registration_list);

                        Store_RegistrationList_List_Count++;
                        Store_RegistrationList_List_Null_Count++;
                    }

                    if (Store_RegistrationList_List_Null_Count == 0) //서버에 자료가 존재하지 않으면
                    {
                        registrationstorelist_listAdapter.notifyDataSetChanged();
                        static_initview_management.layout_storemanagement_list_no_data.setVisibility(View.VISIBLE);
                        //Toast.makeText(Ui_DrawerOption_Search_Dialog.Context_Ui_DrawerOption_Search_Dialog, "데이터없음", Toast.LENGTH_SHORT).show();
                    }
                    else //출력완료
                    {
                        registrationstorelist_listAdapter.notifyDataSetChanged();
                        static_initview_management.layout_storemanagement_list_no_data.setVisibility(View.GONE);
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
                datas.put("ACCOUNT_ID", static_method.getUserInfo_user_id());
                return datas;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity);
        requestQueue.add(stringRequest);











        /*
        if(result_registrationstorelist != null)
        {
            try
            {
                JSONArray jsonArray = new JSONArray(result_registrationstorelist);
                int RegistrationStore_List_Count = 0;
                int RegistrationStore_List_Null_Count = 0;
                String str_nm=null, str_cnt=null, sls_dw=null, sls_st_tm=null, sls_ed_tm=null, srch_tag=null, atta_mkey=null, ctg_id=null, reg_dt=null, udt_dt=null;
                while(RegistrationStore_List_Count < jsonArray.length())
                {
                    JSONObject object = jsonArray.getJSONObject(RegistrationStore_List_Count);
                    str_nm = object.getString("str_nm");
                    str_cnt = object.getString("str_cnt");
                    sls_dw = object.getString("sls_dw");
                    sls_st_tm = object.getString("sls_st_tm");
                    sls_ed_tm = object.getString("sls_ed_tm");
                    srch_tag = object.getString("srch_tag");

                    Ui_StoreManagement_Activity_Registration_List ui_storemanagement_activity_registration_list = new Ui_StoreManagement_Activity_Registration_List(
                            str_nm,
                            str_cnt,
                            sls_dw,
                            sls_st_tm,
                            sls_ed_tm,
                            srch_tag
                    );
                    registrationstore_userList.add(ui_storemanagement_activity_registration_list);
                    registrationstore_saveList.add(ui_storemanagement_activity_registration_list);

                    RegistrationStore_List_Count++;
                    RegistrationStore_List_Null_Count++;

                }
                if(RegistrationStore_List_Count == 0) //서버에 자료가 존재하지 않으면
                {
                    static_initview_management.layout_storemanagement_list_no_data.setVisibility(View.VISIBLE);
                }
                else //출력완료
                {
                    //Toast.makeText(Ui_Main_Activity.this, "출력 완료", Toast.LENGTH_SHORT).show();
                    static_initview_management.layout_storemanagement_list_no_data.setVisibility(View.GONE);
                }
            }
            catch (Exception e) //서버 에러
            {
                e.printStackTrace();
                //Toast.makeText(Ui_Main_Activity.this, "서버 에러", Toast.LENGTH_SHORT).show();
            }
        }
         */
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
        Context_Ui_StoreManagement_Activity = null;
    }

}