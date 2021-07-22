package com.solomonm.yeogiisso.static_init;

import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
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
import com.solomonm.yeogiisso.management.store.Ui_StoreManagement_Activity;
import com.solomonm.yeogiisso.management.store.Ui_StoreManagement_Store_Registration_Activity;

public class Static_initView_Management {

    //Ui_StoreManagement_Activity
    public LinearLayout layout_storemanagement_previous, layout_storemanagement_store_registration, layout_storemanagement_list_no_data;
    public RecyclerView rv_registered_store_list;

    //Ui_StoreManagement_Store_Registration_Activity
    public EditText et_store_registration_store_name, et_store_registration_store_menu, et_store_registration_store_explanation, et_store_registration_store_menu_price, et_store_registration_number;
    public TextView tv_store_registration_store_address_notice, tv_store_registration_store_address, tv_store_registration_store_address_location, tv_store_registration_store_address_choice_map, tv_store_registration_store_tag, tv_store_registration_change_openweek, tv_store_registration_store_openweek,
            tv_store_registration_store_uploadpic;
    public View layout_store_registration_map;
    public LinearLayout layout_store_registration_map_mainframe, layout_store_registration_map_button_mainframe;
    public Button button_store_registration_map_button_cancel, button_store_registration_map_button_ok, button_store_registration_enrollment;
    public NumberPicker np_store_registration_opentime_h, np_store_registration_opentime_m, np_store_registration_endtime_h, np_store_registration_endtime_m;
    public Spinner sp_store_registration_storecate_main, sp_store_registration_storecate_sub;
    public ImageView iv_store_registration_store_pic;

    public Static_initView_Management()
    {
        //Ui_StoreManagement_Activity
        if(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity != null)
        {
            layout_storemanagement_previous = (LinearLayout) ((Ui_StoreManagement_Activity)Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity).findViewById(R.id.layout_storemanagement_previous);
            layout_storemanagement_store_registration = (LinearLayout) ((Ui_StoreManagement_Activity)Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity).findViewById(R.id.layout_storemanagement_store_registration);
            rv_registered_store_list = (RecyclerView) ((Ui_StoreManagement_Activity)Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity).findViewById(R.id.rv_registered_store_list);
            layout_storemanagement_list_no_data = (LinearLayout) ((Ui_StoreManagement_Activity)Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity).findViewById(R.id.layout_storemanagement_list_no_data);
        }

        //Ui_StoreManagement_Store_Registration_Activity
        if(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity != null)
        {
            et_store_registration_store_name = (EditText) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.et_store_registration_store_name);
            //et_store_registration_store_menu = (EditText) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.et_store_registration_store_menu);
            et_store_registration_store_explanation = (EditText) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.et_store_registration_store_explanation);
            //et_store_registration_store_menu_price = (EditText) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.et_store_registration_store_menu_price);
            et_store_registration_number = (EditText) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.et_store_registration_number);

            tv_store_registration_store_address_notice = (TextView) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.tv_store_registration_store_address_notice);
            tv_store_registration_store_address = (TextView) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.tv_store_registration_store_address);
            tv_store_registration_store_address_location = (TextView) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.tv_store_registration_store_address_location);
            tv_store_registration_store_address_choice_map = (TextView) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.tv_store_registration_store_address_choice_map);
            //tv_store_registration_store_tag = (TextView) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.tv_store_registration_store_tag);
            tv_store_registration_change_openweek = (TextView) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.tv_store_registration_change_openweek);
            tv_store_registration_store_openweek = (TextView) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.tv_store_registration_store_openweek);
            tv_store_registration_store_uploadpic = (TextView) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.tv_store_registration_store_uploadpic);

            layout_store_registration_map = (View) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.layout_store_registration_map);

            layout_store_registration_map_mainframe = (LinearLayout) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.layout_store_registration_map_mainframe);
            layout_store_registration_map_button_mainframe = (LinearLayout) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.layout_store_registration_map_button_mainframe);

            button_store_registration_map_button_cancel = (Button) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.button_store_registration_map_button_cancel);
            button_store_registration_map_button_ok = (Button) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.button_store_registration_map_button_ok);
            button_store_registration_enrollment = (Button) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.button_store_registration_enrollment);

            np_store_registration_opentime_h = (NumberPicker) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.np_store_registration_opentime_h);
            np_store_registration_opentime_m = (NumberPicker) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.np_store_registration_opentime_m);
            np_store_registration_endtime_h = (NumberPicker) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.np_store_registration_endtime_h);
            np_store_registration_endtime_m = (NumberPicker) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.np_store_registration_endtime_m);

            sp_store_registration_storecate_main = (Spinner) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.sp_store_registration_storecate_main);
            sp_store_registration_storecate_sub = (Spinner) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.sp_store_registration_storecate_sub);

            iv_store_registration_store_pic = (ImageView) ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).findViewById(R.id.iv_store_registration_store_pic);
        }
    }

}
