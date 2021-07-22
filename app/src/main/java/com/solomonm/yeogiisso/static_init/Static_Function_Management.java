package com.solomonm.yeogiisso.static_init;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.loading.Ui_FullScreenWait_Dialog;
import com.solomonm.yeogiisso.management.store.Ui_StoreManagement_Activity;
import com.solomonm.yeogiisso.management.store.Ui_StoreManagement_Store_Registration_Activity;
import com.solomonm.yeogiisso.management.store.Ui_StoreManagement_Store_Registration_OpenWeek_Dialog;

public class Static_Function_Management {
    Static_Function_Management static_function_management;
    public static Context Context_Static_Function_Management;

    Ui_StoreManagement_Store_Registration_Activity ui_storeManagement_store_registration_activity;
    Ui_StoreManagement_Store_Registration_OpenWeek_Dialog ui_storeManagement_store_registration_openWeek_dialog;


    public void init()
    {
        static_function_management = this;
        Context_Static_Function_Management = Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity;

        ui_storeManagement_store_registration_activity = new Ui_StoreManagement_Store_Registration_Activity();
        ui_storeManagement_store_registration_openWeek_dialog = new Ui_StoreManagement_Store_Registration_OpenWeek_Dialog(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity);
    }

    //점포등록_액티비티_시작
   public void Ui_StoreManagement_Store_Registration_Activity_Start()
    {
        if(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity == null)
        {
            Intent start_StoreRegistrationActivity = new Intent(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity.getApplicationContext(), Ui_StoreManagement_Store_Registration_Activity.class);
            Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity.startActivity(start_StoreRegistrationActivity);
            ((Ui_StoreManagement_Activity)Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity).overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
        }
    }
    //점포등록_액티비티_종료
    public void Ui_StoreManagement_Store_Registration_Activity_End()
    {
        if(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity != null)
        {
            ui_storeManagement_store_registration_activity.finish();
            ((Ui_StoreManagement_Activity)Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity).overridePendingTransition(R.anim.effect_activity_start, R.anim.effect_activity_end);
        }
    }

    //점포등록_영업요일변경_다이얼로그_시작
    public void Ui_StoreManagement_Store_Registration_OpenWeek_Dialog_Start(String A, String B, String C, String D, String E, String F, String G)
    {
        if(ui_storeManagement_store_registration_openWeek_dialog != null && ui_storeManagement_store_registration_openWeek_dialog.isShowing())
        {
        }
        else
        {
            ui_storeManagement_store_registration_openWeek_dialog = new Ui_StoreManagement_Store_Registration_OpenWeek_Dialog(Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity);
            ui_storeManagement_store_registration_openWeek_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //customLoginDialog.setCancelable(false);

            Bundle args = new Bundle();
            args.putString("monday", A);
            args.putString("tuesday", B);
            args.putString("wednesday", C);
            args.putString("thursday", D);
            args.putString("friday", E);
            args.putString("saturday", F);
            args.putString("sunday", G);
            ui_storeManagement_store_registration_openWeek_dialog.setArguments(args);

            ui_storeManagement_store_registration_openWeek_dialog.show();
        }
    }
    //점포등록_영업요일변경_다이얼로그_종료
    public void Ui_StoreManagement_Store_Registration_OpenWeek_Dialog_End()
    {
        if(ui_storeManagement_store_registration_openWeek_dialog != null && ui_storeManagement_store_registration_openWeek_dialog.isShowing())
        {
            ui_storeManagement_store_registration_openWeek_dialog.dismiss();
        }
    }
}
