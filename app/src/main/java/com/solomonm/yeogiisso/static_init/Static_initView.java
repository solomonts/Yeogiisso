package com.solomonm.yeogiisso.static_init;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;

public class Static_initView{

    //layoutID
    public androidx.appcompat.widget.Toolbar toolbar;

    public FrameLayout layout_main, layout_main_mainwait;
    public LinearLayout layout_btn_bottomlayout_move, layout_bottomlayout_mainframe, layout_bottomlayout_innerframe, layout_bottomlayout_bottommenuframe, layout_draweroption_addonbutton,
            layout_draweroption_searchmenu_area, layout_draweroption_mapsetting_dist, layout_draweroption_searchmenu_price, layout_draweroption_searchmenu_price_mainframe, layout_draweroption_searchmenu_cate, layout_draweroption_searchmenu_cate_mainframe, layout_draweroption_mapsetting_dist_mainframe,
            layout_draweroption_searchmenu_call_mainframe, layout_draweroption_searchmenu_mainframe, layout_draweroption_mapsetting_call_mainframe, layout_draweroption_mapsetting_mainframe,
            layout_draweroption_mapsetting_mapmode, layout_draweroption_mapsetting_mapmode_mainframe, layout_draweroption_searchmenu_area_mainframe,
            layout_draweroption_mapsetting_etc, layout_draweroption_mapsetting_etc_mainframe,
            layout_draweroption_mapsetting_etc_thickness_scaleup, layout_draweroption_mapsetting_etc_thickness_scaledown, layout_draweroption_mapsetting_etc_zoom_scaleup, layout_draweroption_mapsetting_etc_zoom_scaledown, layout_btn_start_search,
            layout_draweroption_searchmenu_area_ctp, layout_draweroption_searchmenu_area_sig, layout_draweroption_searchmenu_area_emd;

    public DrawerLayout layout_drawer_main;
    public View layout_main_mapfrag, drawer_option, drawer_setting, view_btn_bottomlayout_move;

    public CardView cardview_btn_drawer_option, cardview_btn_call_searchbox, cardview_btn_toolbar_optionmenu,
            cardview_btn_call_bottommenu, cardview_btn_bottommenu1_setmylocation, cardview_btn_bottommenu2_searchmap_mapposition, cardview_btn_bottommenu3_realtime_gps,
            cardview_layout_searchbox;
    public ImageView iv_btn_drawer_setting, iv_btn_drawer_option, iv_btn_drawer_close,
            iv_btn_call_bottommenu, iv_btn_bottommenu1_setmylocation, iv_btn_bottommenu2_searchmap_mapposition, iv_btn_bottommenu3_realtime_gps,
            iv_btn_search, iv_draweroption_mapsetting_dist,
            iv_draweroption_searchmenu_call_mainframe, iv_draweroption_mapsetting_call_mainframe,
            iv_draweroption_mapsetting_mapmode, iv_draweroption_searchmenu_area, iv_draweroption_searchmenu_price, iv_draweroption_searchmenu_cate, iv_draweroption_mapsetting_etc, iv_draweroption_profilepic;

    public TextView tv_drawersetting_changelanguage, tv_drawersetting_changetheme,
            tv_btn_bottommenu1_setmylocation, tv_btn_bottommenu2_searchmap_mapposition, tv_btn_bottommenu3_realtime_gps,
            tv_draweroption_login, tv_draweroption_logout, tv_draweroption_mapsetting_dist_hopedist, tv_draweroption_mapsetting_dist_applydist,
            tv_draweroption_mapsetting_mapmode_applymapmode, tv_draweroption_mapsetting_etc_thickness, tv_draweroption_mapsetting_etc_zoom, tv_draweroption_mapsetting_etc_applyetc,
            tv_drawersetting_changeprofile, tv_drawersetting_qna, tv_drawersetting_withdrawal, tv_btn_start_search_alert,
            tv_draweroption_searchmenu_area_applylocal, tv_draweroption_searchmenu_area_ctp, tv_draweroption_searchmenu_area_sig, tv_draweroption_searchmenu_area_emd,
            tv_draweroption_call_store_management, tv_drawersetting_certification;

    public RecyclerView rv_nearbymarket_list;

    public SeekBar seekbar_draweroption_mapsetting_dist;

    public Button btn_draweroption_search_ok, btn_draweroption_change_ok;

    public RadioButton radiobutton_draweroption_mapsetting_mapmode_rb1, radiobutton_draweroption_mapsetting_mapmode_rb2, radiobutton_draweroption_mapsetting_mapmode_rb3;

    public EditText et_draweroption_searchmenu_price_set_stprice, et_draweroption_searchmenu_price_set_edprice, et_search_contents;

    public Spinner sp_draweroption_searchmenu_cate_main, sp_draweroption_searchmenu_cate_middle;

    //methodID
    public Drawable drawable;

    public PointF mCenter;
    public float mRadius;
    public float mMaxDist;

    public Static_initView()
    {
        toolbar = (Toolbar) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.toolbar);

        layout_main = (FrameLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_main);
        layout_main_mainwait = (FrameLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_main_mainwait);
        layout_btn_bottomlayout_move = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_btn_bottomlayout_move);
        layout_bottomlayout_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_bottomlayout_mainframe);
        layout_bottomlayout_innerframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_bottomlayout_innerframe);
        layout_bottomlayout_bottommenuframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_bottomlayout_bottommenuframe);
        layout_draweroption_addonbutton = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_addonbutton);
        layout_draweroption_searchmenu_area = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_area);
        layout_draweroption_mapsetting_dist = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_dist);
        layout_draweroption_searchmenu_price = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_price);
        layout_draweroption_searchmenu_price_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_price_mainframe);
        layout_draweroption_searchmenu_cate = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_cate);
        layout_draweroption_searchmenu_cate_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_cate_mainframe);
        layout_draweroption_mapsetting_dist_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_dist_mainframe);
        layout_draweroption_searchmenu_call_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_call_mainframe);
        layout_draweroption_searchmenu_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_mainframe);
        layout_draweroption_mapsetting_call_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_call_mainframe);
        layout_draweroption_mapsetting_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_mainframe);
        layout_draweroption_mapsetting_mapmode = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_mapmode);
        layout_draweroption_mapsetting_mapmode_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_mapmode_mainframe);
        layout_draweroption_searchmenu_area_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_area_mainframe);
        layout_draweroption_mapsetting_etc = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_etc);
        layout_draweroption_mapsetting_etc_mainframe = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_etc_mainframe);
        layout_draweroption_mapsetting_etc_thickness_scaleup = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_etc_thickness_scaleup);
        layout_draweroption_mapsetting_etc_thickness_scaledown = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_etc_thickness_scaledown);
        layout_draweroption_mapsetting_etc_zoom_scaleup = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_etc_zoom_scaleup);
        layout_draweroption_mapsetting_etc_zoom_scaledown = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_mapsetting_etc_zoom_scaledown);
        layout_btn_start_search = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_btn_start_search);
        layout_draweroption_searchmenu_area_ctp = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_area_ctp);
        layout_draweroption_searchmenu_area_sig = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_area_sig);
        layout_draweroption_searchmenu_area_emd = (LinearLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_draweroption_searchmenu_area_emd);

        layout_drawer_main = (DrawerLayout)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_drawer_main);
        drawer_option = (View)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.drawer_option);
        drawer_setting = (View)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.drawer_setting);
        layout_main_mapfrag = (View)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.layout_main_mapfrag);
        view_btn_bottomlayout_move = (View)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.view_btn_bottomlayout_move);

        cardview_btn_drawer_option = (CardView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.cardview_btn_drawer_option);
        cardview_btn_call_searchbox = (CardView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.cardview_btn_call_searchbox);
        cardview_btn_toolbar_optionmenu = (CardView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.cardview_btn_toolbar_optionmenu);
        cardview_btn_call_bottommenu = (CardView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.cardview_btn_call_bottommenu);
        cardview_btn_bottommenu1_setmylocation = (CardView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.cardview_btn_bottommenu1_setmylocation);
        cardview_btn_bottommenu2_searchmap_mapposition = (CardView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.cardview_btn_bottommenu2_searchmap_mapposition);
        cardview_btn_bottommenu3_realtime_gps = (CardView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.cardview_btn_bottommenu3_realtime_gps);
        cardview_layout_searchbox = (CardView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.cardview_layout_searchbox);
        iv_btn_drawer_setting = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_btn_drawer_setting);
        iv_btn_drawer_option = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_btn_drawer_option);
        iv_btn_drawer_close = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_btn_drawer_close);
        iv_btn_call_bottommenu = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_btn_call_bottommenu);
        iv_btn_bottommenu1_setmylocation = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_btn_bottommenu1_setmylocation);
        iv_btn_bottommenu2_searchmap_mapposition = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_btn_bottommenu2_searchmap_mapposition);
        iv_btn_bottommenu3_realtime_gps = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_btn_bottommenu3_realtime_gps);
        iv_btn_search = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_btn_search);
        iv_draweroption_mapsetting_dist = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_draweroption_mapsetting_dist);
        iv_draweroption_searchmenu_call_mainframe = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_draweroption_searchmenu_call_mainframe);
        iv_draweroption_mapsetting_call_mainframe = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_draweroption_mapsetting_call_mainframe);
        iv_draweroption_mapsetting_mapmode = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_draweroption_mapsetting_mapmode);
        iv_draweroption_searchmenu_area = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_draweroption_searchmenu_area);
        iv_draweroption_searchmenu_price = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_draweroption_searchmenu_price);
        iv_draweroption_searchmenu_cate = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_draweroption_searchmenu_cate);
        iv_draweroption_mapsetting_etc = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_draweroption_mapsetting_etc);
        iv_draweroption_profilepic = (ImageView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.iv_draweroption_profilepic);

        tv_drawersetting_changelanguage = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_drawersetting_changelanguage);
        tv_drawersetting_changetheme = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_drawersetting_changetheme);
        tv_btn_bottommenu1_setmylocation = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_btn_bottommenu1_setmylocation);
        tv_btn_bottommenu2_searchmap_mapposition = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_btn_bottommenu2_searchmap_mapposition);
        tv_btn_bottommenu3_realtime_gps = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_btn_bottommenu3_realtime_gps);
        tv_draweroption_login = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_login);
        tv_draweroption_logout = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_logout);
        tv_draweroption_mapsetting_dist_hopedist = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_mapsetting_dist_hopedist);
        tv_draweroption_mapsetting_dist_applydist = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_mapsetting_dist_applydist);
        tv_draweroption_mapsetting_mapmode_applymapmode = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_mapsetting_mapmode_applymapmode);
        tv_draweroption_mapsetting_etc_thickness = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_mapsetting_etc_thickness);
        tv_draweroption_mapsetting_etc_zoom = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_mapsetting_etc_zoom);
        tv_draweroption_mapsetting_etc_applyetc = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_mapsetting_etc_applyetc);
        //tv_drawersetting_changeprofile = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_drawersetting_changeprofile);
        tv_drawersetting_qna = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_drawersetting_qna);
        tv_drawersetting_withdrawal = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_drawersetting_withdrawal);
        tv_btn_start_search_alert = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_btn_start_search_alert);
        tv_draweroption_searchmenu_area_applylocal = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_searchmenu_area_applylocal);
        tv_draweroption_searchmenu_area_ctp = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_searchmenu_area_ctp);
        tv_draweroption_searchmenu_area_sig = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_searchmenu_area_sig);
        tv_draweroption_searchmenu_area_emd = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_searchmenu_area_emd);
        tv_draweroption_call_store_management = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_draweroption_call_store_management);
        tv_drawersetting_certification = (TextView)((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.tv_drawersetting_certification);

        rv_nearbymarket_list = (RecyclerView) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.rv_nearbymarket_list);

        seekbar_draweroption_mapsetting_dist = (SeekBar) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.seekbar_draweroption_mapsetting_dist);

        btn_draweroption_search_ok = (Button) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.btn_draweroption_search_ok);
        btn_draweroption_change_ok = (Button) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.btn_draweroption_change_ok);

        radiobutton_draweroption_mapsetting_mapmode_rb1 = (RadioButton) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.radiobutton_draweroption_mapsetting_mapmode_rb1);
        radiobutton_draweroption_mapsetting_mapmode_rb2 = (RadioButton) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.radiobutton_draweroption_mapsetting_mapmode_rb2);
        radiobutton_draweroption_mapsetting_mapmode_rb3 = (RadioButton) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.radiobutton_draweroption_mapsetting_mapmode_rb3);

        et_draweroption_searchmenu_price_set_stprice = (EditText) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.et_draweroption_searchmenu_price_set_stprice);
        et_draweroption_searchmenu_price_set_edprice = (EditText) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.et_draweroption_searchmenu_price_set_edprice);
        et_search_contents = (EditText) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.et_search_contents);

        sp_draweroption_searchmenu_cate_main = (Spinner) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.sp_draweroption_searchmenu_cate_main);
        sp_draweroption_searchmenu_cate_middle = (Spinner) ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).findViewById(R.id.sp_draweroption_searchmenu_cate_middle);
    }

}
