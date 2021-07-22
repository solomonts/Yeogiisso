package com.solomonm.yeogiisso.static_init;

import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDexApplication;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kakao.sdk.auth.AuthApiClient;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.model.KakaoSdkError;
import com.kakao.sdk.user.UserApiClient;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.login.LoginRequest;
import com.solomonm.yeogiisso.login.Ui_Login_Activity;
import com.solomonm.yeogiisso.service.Class_NetworkStatus;

import org.json.JSONArray;
import org.json.JSONObject;

public class Static_Method extends MultiDexApplication {
    private static Static_Method instance;
    Static_Method Static_User_Info;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Static_User_Info = (Static_Method) instance.getApplicationContext();

        // 카카오 네이티브 앱 키로 초기화
        int key = getString(R.string.kakao_app_key).length();
        KakaoSdk.init(this, getString(R.string.kakao_app_key).substring(5, key));
        
        if(AuthApiClient.getInstance().hasToken())
        {
            UserApiClient.getInstance().accessTokenInfo((tokenInfo, tokenError) -> {
                if(tokenError != null)
                {
                    Static_User_Info.setUserInfo_user_id(null);
                    Static_User_Info.setUserInfo_user_nickname(null);
                    Static_User_Info.setUserInfo_user_profile_pic(null);
                    Static_User_Info.setUserInfo_user_type(null);
                    Static_User_Info.setUserInfo_user_auth(null);
                    Static_User_Info.setUserInfo_user_number(0);
                    Static_User_Info.setUserInfo_user_quit_yn(null);
                    Static_User_Info.setUserInfo_user_quit_dt(null);
                }
                else
                {
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    UserApiClient.getInstance().me((user, meError) -> {
                        if(meError != null)
                        {
                        }
                        else
                        {
                            Log.e("LOG : ", "사용자 정보 요청 성공"
                                            +"\n회원번호 : "+user.getId()
                                            +"\n닉네임 : "+user.getKakaoAccount().getProfile().getNickname()
                                            +"\n프로필사진 : "+user.getKakaoAccount().getProfile().getProfileImageUrl()
                                    //+"\n카카오계정이메일 : "+user.getKakaoAccount().getEmail()
                            );
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
                                            Static_User_Info.setUserInfo_user_id(jsonArray.getJSONObject(0).getString("account_id"));
                                            Static_User_Info.setUserInfo_user_nickname(user.getKakaoAccount().getProfile().getNickname());
                                            Static_User_Info.setUserInfo_user_profile_pic(user.getKakaoAccount().getProfile().getProfileImageUrl());
                                            Static_User_Info.setUserInfo_user_type(jsonArray.getJSONObject(0).getString("account_type"));
                                            Static_User_Info.setUserInfo_user_auth(jsonArray.getJSONObject(0).getString("account_auth"));
                                            if(jsonArray.getJSONObject(0).getString("account_auth").equals("O"))
                                            {
                                                Static_User_Info.setUserInfo_user_number(jsonArray.getJSONObject(0).getInt("account_number"));
                                            }
                                            else
                                            {
                                                Static_User_Info.setUserInfo_user_number(0);
                                            }
                                            Static_User_Info.setUserInfo_user_quit_yn(jsonArray.getJSONObject(0).getString("account_id_quit_yn"));

                                            if(jsonArray.getJSONObject(0).getString("account_id_quit_yn").equals("Y"))
                                            {
                                                Static_User_Info.setUserInfo_user_quit_dt(jsonArray.getJSONObject(0).getString("account_id_quit_dt"));

                                                /////////////////////
                                                /////////////////////
                                                //탈퇴된 회원입니다. \n 탈퇴날짜 : account_id_quit_dt
                                                /////////////////////
                                                /////////////////////
                                                Static_User_Info.setUserInfo_user_id(null);
                                                Static_User_Info.setUserInfo_user_nickname(null);
                                                Static_User_Info.setUserInfo_user_profile_pic(null);
                                                Static_User_Info.setUserInfo_user_type(null);
                                                Static_User_Info.setUserInfo_user_auth(null);
                                                Static_User_Info.setUserInfo_user_number(0);
                                                Static_User_Info.setUserInfo_user_quit_yn(null);
                                                Static_User_Info.setUserInfo_user_quit_dt(null);
                                            }
                                            else
                                            {
                                                //로그인처리 성공
                                            }
                                        }
                                        else
                                        {
                                            /////////////////////////////
                                            /////////////////////////////
                                            //등록되지 않은 회원입니다\n무료로 회원등록하시겠습니까?
                                            /////////////////////////////
                                            /////////////////////////////
                                            Static_User_Info.setUserInfo_user_id(null);
                                            Static_User_Info.setUserInfo_user_nickname(null);
                                            Static_User_Info.setUserInfo_user_profile_pic(null);
                                            Static_User_Info.setUserInfo_user_type(null);
                                            Static_User_Info.setUserInfo_user_auth(null);
                                            Static_User_Info.setUserInfo_user_number(0);
                                            Static_User_Info.setUserInfo_user_quit_yn(null);
                                            Static_User_Info.setUserInfo_user_quit_dt(null);
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        e.printStackTrace();
                                        ////////////////////////////////
                                        ////////////////////////////////
                                        //인터넷이 꺼져있을 때
                                        //API서버 오류
                                        ////////////////////////////////
                                        ////////////////////////////////
                                        Static_User_Info.setUserInfo_user_id(null);
                                        Static_User_Info.setUserInfo_user_nickname(null);
                                        Static_User_Info.setUserInfo_user_profile_pic(null);
                                        Static_User_Info.setUserInfo_user_type(null);
                                        Static_User_Info.setUserInfo_user_auth(null);
                                        Static_User_Info.setUserInfo_user_number(0);
                                        Static_User_Info.setUserInfo_user_quit_yn(null);
                                        Static_User_Info.setUserInfo_user_quit_dt(null);
                                    }
                                }
                            };
                            LoginRequest loginRequest = new LoginRequest(""+user.getId(), responseListener);
                            RequestQueue queue = Volley.newRequestQueue(Ui_Main_Activity.Context_UiMainActivity);
                            queue.add(loginRequest);
                        }
                        return null;
                    });
                }
                return null;
            });
        }
        else
        {
            //로그인 필요
            Static_User_Info.setUserInfo_user_id(null);
            Static_User_Info.setUserInfo_user_nickname(null);
            Static_User_Info.setUserInfo_user_profile_pic(null);
            Static_User_Info.setUserInfo_user_type(null);
            Static_User_Info.setUserInfo_user_auth(null);
            Static_User_Info.setUserInfo_user_number(0);
            Static_User_Info.setUserInfo_user_quit_yn(null);
            Static_User_Info.setUserInfo_user_quit_dt(null);
        }
    }

    public int count = 0;
    public int look_tutorial = 0;
    public String refresh = "";

    //지도 관련
    public Double lat_mapcenter=0.0; //현재 보고있는 지도의 중앙값
    public Double lng_mapcenter=0.0;
    public Double lat_gps=0.0; //gps수신받은 값
    public Double lng_gps=0.0;
    public Double lat_onclick=0.0; //사용자가 클릭한 위치의 값
    public Double lng_onclick=0.0;
    public Double lat_searchstore_center=0.0; //점포검색시 중앙값
    public Double lng_searchstore_center=0.0;
    public int map_thickness=2; //지도 원 두께
    public Float map_zoom=14.f; //지도 줌 레벨
    public int map_circle=1000; //지도 반경 원 크기 (단위 : 미터)
    public String map_mode="MAP_TYPE_NORMAL"; //지도 모드
    //마커
    public String storeName;
    public String storeSnippet;
    public Double storeYpos;
    public Double storeXpos;
    //드로어옵션 상세검색 설정값
    public int draweroption_searchmenu_dist = 1000;
    public String localsearch_ctp = "";
    public String localsearch_sig = "";
    public String localsearch_emd = "";
    public String localsearch_ctp_code = "";
    public String localsearch_sig_code = "";

    //저장소접근권한 true/false
    public String permission_storage = "false";
    //GPS권한 true/false
    public String permission_gps = "false";
    //GPS on/off
    public String checkfunction_gps = "false";
    //Network 상태체크 실행중
    public String checkfunction_run = "false";
    //서버상태
    public String checkfunction_serverStatus = "false";

    public Static_Method Static_Method_UserInfo;
    //유저정보 get/set
    String UserInfo_user_id=null;
    String UserInfo_user_nickname=null;
    String UserInfo_user_profile_pic=null;
    String UserInfo_user_type=null;
    String UserInfo_user_auth=null;
    int UserInfo_user_number=0;
    String UserInfo_user_quit_yn=null;
    String UserInfo_user_quit_dt=null;

    //유저정보 변수
    String USER_ID="";

    //로그인, 회원가입 타입
    String ClickLoginType="";
    String ClickSignupType="";

    public Static_Method Static_Method_AppSetting;
    //앱세팅정보 변수
    public String App_Language="";
    public int App_Theme=0; //현재 테마

    public Static_Method Static_Method_DeviceSetting;
    public int deviceWidth=0;
    public int deviceHeight=0;
    public int statusBarHeight=0;
    public int bottommenu1_width = 0;
    public int bottommenu2_width = 0;
    public int bottommenu3_width = 0;

    public String test = "";

    public int getcount()
    {
        return  count;
    }
    public void setcount(int count)
    {
        this.count = count;
    }

    public int getlook_tutorial()
    {
        return  look_tutorial;
    }
    public void setlook_tutorial(int look_tutorial)
    {
        this.look_tutorial = look_tutorial;
    }

    public String getrefresh()
    {
        return  refresh;
    }
    public void setrefresh(String refresh)
    {
        this.refresh = refresh;
    }

    public Double getlat_mapcenter()
    {
        return  lat_mapcenter;
    }
    public void setlat_mapcenter(Double lat_mapcenter)
    {
        this.lat_mapcenter = lat_mapcenter;
    }

    public Double getlng_mapcenter()
    {
        return  lng_mapcenter;
    }
    public void setlng_mapcenter(Double lng_mapcenter)
    {
        this.lng_mapcenter = lng_mapcenter;
    }

    public Double getlat_gps()
    {
        return  lat_gps;
    }
    public void setlat_gps(Double lat_gps)
    {
        this.lat_gps = lat_gps;
    }

    public Double getlng_gps()
    {
        return  lng_gps;
    }
    public void setlng_gps(Double lng_gps)
    {
        this.lng_gps = lng_gps;
    }

    public Double getlat_onclick()
    {
        return  lat_onclick;
    }
    public void setlat_onclick(Double lat_onclick)
    {
        this.lat_onclick = lat_onclick;
    }

    public Double getlng_onclick()
    {
        return  lng_onclick;
    }
    public void setlng_onclick(Double lng_onclick)
    {
        this.lng_onclick = lng_onclick;
    }

    public Double getlat_searchstore_center()
    {
        return  lat_searchstore_center;
    }
    public void setlat_searchstore_center(Double lat_searchstore_center)
    {
        this.lat_searchstore_center = lat_searchstore_center;
    }

    public Double getlng_searchstore_center()
    {
        return  lng_searchstore_center;
    }
    public void setlng_searchstore_center(Double lng_searchstore_center)
    {
        this.lng_searchstore_center = lng_searchstore_center;
    }

    public int getmap_thickness()
    {
        return  map_thickness;
    }
    public void setmap_thickness(int map_thickness)
    {
        this.map_thickness = map_thickness;
    }

    public float getmap_zoom()
    {
        return  map_zoom;
    }
    public void setmap_zoom(float map_zoom)
    {
        this.map_zoom = map_zoom;
    }

    public int getmap_circle()
    {
        return  map_circle;
    }
    public void setmap_circle(int map_circle)
    {
        this.map_circle = map_circle;
    }

    public String getmap_mode()
    {
        return  map_mode;
    }
    public void setmap_mode(String map_mode)
    {
        this.map_mode = map_mode;
    }

    public String getstoreName()
    {
        return  storeName;
    }
    public void setstoreName(String storeName)
    {
        this.storeName = storeName;
    }

    public String getstoreSnippet()
    {
        return  storeSnippet;
    }
    public void setstoreSnippet(String storeSnippet)
    {
        this.storeSnippet = storeSnippet;
    }

    public double getstoreYpos()
    {
        return  storeYpos;
    }
    public void setstoreYpos(double storeYpos)
    {
        this.storeYpos = storeYpos;
    }

    public double getstoreXpos()
    {
        return  storeXpos;
    }
    public void setstoreXpos(double storeXpos)
    {
        this.storeXpos = storeXpos;
    }

    public int getdraweroption_searchmenu_dist()
    {
        return  draweroption_searchmenu_dist;
    }
    public void setdraweroption_searchmenu_dist(int draweroption_searchmenu_dist)
    {
        this.draweroption_searchmenu_dist = draweroption_searchmenu_dist;
    }

    public String getlocalsearch_ctp()
    {
        return  localsearch_ctp;
    }
    public void setlocalsearch_ctp(String localsearch_ctp)
    {
        this.localsearch_ctp = localsearch_ctp;
    }

    public String getlocalsearch_sig()
    {
        return  localsearch_sig;
    }
    public void setlocalsearch_sig(String localsearch_sig)
    {
        this.localsearch_sig = localsearch_sig;
    }

    public String getlocalsearch_emd()
    {
        return  localsearch_emd;
    }
    public void setlocalsearch_emd(String localsearch_emd)
    {
        this.localsearch_emd = localsearch_emd;
    }

    public String getlocalsearch_ctp_code()
    {
        return  localsearch_ctp_code;
    }
    public void setlocalsearch_ctp_code(String localsearch_ctp_code)
    {
        this.localsearch_ctp_code = localsearch_ctp_code;
    }

    public String getlocalsearch_sig_code()
    {
        return  localsearch_sig_code;
    }
    public void setlocalsearch_sig_code(String localsearch_sig_code)
    {
        this.localsearch_sig_code = localsearch_sig_code;
    }

    public String getpermission_storage()
    {
        return  permission_storage;
    }
    public void setpermission_storage(String permission_storage)
    {
        this.permission_storage = permission_storage;
    }

    public String getpermission_gps()
    {
        return  permission_gps;
    }
    public void setpermission_gps(String permission_gps)
    {
        this.permission_gps = permission_gps;
    }

    public String getcheckfunction_gps()
    {
        return  checkfunction_gps;
    }
    public void setcheckfunction_gps(String checkfunction_gps)
    {
        this.checkfunction_gps = checkfunction_gps;
    }

    public String getcheckfunction_run()
    {
        return  checkfunction_run;
    }
    public void setcheckfunction_run(String checkfunction_run)
    {
        this.checkfunction_run = checkfunction_run;
    }

    public String getcheckfunction_serverStatus()
    {
        return  checkfunction_serverStatus;
    }
    public void setcheckfunction_serverStatus(String checkfunction_serverStatus)
    {
        this.checkfunction_serverStatus = checkfunction_serverStatus;
    }

    public String getUserInfo_user_id()
    {
        return  UserInfo_user_id;
    }
    public void setUserInfo_user_id(String UserInfo_user_id)
    {
        this.UserInfo_user_id = UserInfo_user_id;
    }

    public String getUserInfo_user_nickname()
    {
        return  UserInfo_user_nickname;
    }
    public void setUserInfo_user_nickname(String UserInfo_user_nickname)
    {
        this.UserInfo_user_nickname = UserInfo_user_nickname;
    }

    public String getUserInfo_user_profile_pic()
    {
        return  UserInfo_user_profile_pic;
    }
    public void setUserInfo_user_profile_pic(String UserInfo_user_profile_pic)
    {
        this.UserInfo_user_profile_pic = UserInfo_user_profile_pic;
    }

    public String getUserInfo_user_type()
    {
        return  UserInfo_user_type;
    }
    public void setUserInfo_user_type(String UserInfo_user_type)
    {
        this.UserInfo_user_type = UserInfo_user_type;
    }

    public String getUserInfo_user_auth()
    {
        return  UserInfo_user_auth;
    }
    public void setUserInfo_user_auth(String UserInfo_user_auth)
    {
        this.UserInfo_user_auth = UserInfo_user_auth;
    }

    public int getUserInfo_user_number()
    {
        return  UserInfo_user_number;
    }
    public void setUserInfo_user_number(int UserInfo_user_number)
    {
        this.UserInfo_user_number = UserInfo_user_number;
    }

    public String getUserInfo_user_quit_yn()
    {
        return  UserInfo_user_quit_yn;
    }
    public void setUserInfo_user_quit_yn(String UserInfo_user_quit_yn)
    {
        this.UserInfo_user_quit_yn = UserInfo_user_quit_yn;
    }

    public String getUserInfo_user_quit_dt()
    {
        return  UserInfo_user_quit_dt;
    }
    public void setUserInfo_user_quit_dt(String UserInfo_user_quit_dt)
    {
        this.UserInfo_user_quit_dt = UserInfo_user_quit_dt;
    }

    public String getApp_Language()
    {
        return  App_Language;
    }
    public void setApp_Language(String App_Language)
    {
        this.App_Language = App_Language;
    }

    public int getApp_Theme()
    {
        return  App_Theme;
    }
    public void setApp_Theme(int App_Theme)
    {
        this.App_Theme = App_Theme;
    }

    public int getdeviceWidth()
    {
        return  deviceWidth;
    }
    public void setdeviceWidth(int deviceWidth)
    {
        this.deviceWidth = deviceWidth;
    }

    public int getdeviceHeight()
    {
        return  deviceHeight;
    }
    public void setdeviceHeight(int deviceHeight)
    {
        this.deviceHeight = deviceHeight;
    }

    public int getstatusBarHeight()
    {
        return  statusBarHeight;
    }
    public void setstatusBarHeight(int statusBarHeight)
    {
        this.statusBarHeight = statusBarHeight;
    }

    public int getbottommenu1_width()
    {
        return  bottommenu1_width;
    }
    public void setbottommenu1_width(int bottommenu1_width)
    {
        this.bottommenu1_width = bottommenu1_width;
    }

    public int getbottommenu2_width()
    {
        return  bottommenu2_width;
    }
    public void setbottommenu2_width(int bottommenu2_width)
    {
        this.bottommenu2_width = bottommenu2_width;
    }

    public int getbottommenu3_width()
    {
        return  bottommenu3_width;
    }
    public void setbottommenu3_width(int bottommenu3_width)
    {
        this.bottommenu3_width = bottommenu3_width;
    }

    public String getClickLoginType()
    {
        return  ClickLoginType;
    }
    public void setClickLoginType(String ClickLoginType)
    {
        this.ClickLoginType = ClickLoginType;
    }

    public String getClickSignupType()
    {
        return  ClickSignupType;
    }
    public void setClickSignupType(String ClickSignupType)
    {
        this.ClickSignupType = ClickSignupType;
    }

    public String gettest()
    {
        return  test;
    }
    public void settest(String test)
    {
        this.test = test;
    }

    public Static_Method(
    )
    {
        this.count = count;
        this.look_tutorial = look_tutorial;
        this.refresh = refresh;
        this.lat_mapcenter = lat_mapcenter;
        this.lng_mapcenter = lng_mapcenter;
        this.lat_gps = lat_gps;
        this.lng_gps = lng_gps;
        this.lat_onclick = lat_onclick;
        this.lng_onclick = lng_onclick;
        this.lat_searchstore_center = lat_searchstore_center;
        this.lng_searchstore_center = lng_searchstore_center;
        this.map_thickness = map_thickness;
        this.map_zoom = map_zoom;
        this.map_circle = map_circle;
        this.map_mode = map_mode;
        this.storeName = storeName;
        this.storeSnippet = storeSnippet;
        this.storeYpos = storeYpos;
        this.storeXpos = storeXpos;
        this.draweroption_searchmenu_dist = draweroption_searchmenu_dist;
        this.localsearch_ctp = localsearch_ctp;
        this.localsearch_sig = localsearch_sig;
        this.localsearch_emd = localsearch_emd;
        this.localsearch_ctp_code = localsearch_ctp_code;
        this.localsearch_sig_code = localsearch_sig_code;
        this.permission_storage = permission_storage;
        this.permission_gps = permission_gps;
        this.checkfunction_gps = checkfunction_gps;
        this.checkfunction_run = checkfunction_run;
        this.checkfunction_serverStatus = checkfunction_serverStatus;
        this.UserInfo_user_id = UserInfo_user_id;
        this.UserInfo_user_nickname = UserInfo_user_nickname;
        this.UserInfo_user_profile_pic = UserInfo_user_profile_pic;
        this.UserInfo_user_type = UserInfo_user_type;
        this.UserInfo_user_auth = UserInfo_user_auth;
        this.UserInfo_user_number = UserInfo_user_number;
        this.UserInfo_user_quit_yn = UserInfo_user_quit_yn;
        this.UserInfo_user_quit_dt = UserInfo_user_quit_dt;
        this.USER_ID = USER_ID;
        this.App_Language = App_Language;
        this.App_Theme = App_Theme;
        this.deviceWidth = deviceWidth;
        this.deviceHeight = deviceHeight;
        this.statusBarHeight = statusBarHeight;
        this.bottommenu1_width = bottommenu1_width;
        this.bottommenu2_width = bottommenu2_width;
        this.bottommenu3_width = bottommenu3_width;
        this.ClickLoginType = ClickLoginType;
        this.ClickSignupType = ClickSignupType;
        this.test = test;
    }
}
