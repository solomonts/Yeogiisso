package com.solomonm.yeogiisso.management.store;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

import com.solomonm.yeogiisso.R;

public class Ui_StoreManagement_Store_Registration_OpenWeek_Dialog extends Dialog {
    public static Context Context_Ui_StoreManagement_Store_Registration_OpenWeek_Dialog;

    String monday="true";
    String tuesday="true";
    String wednesday="true";
    String thursday="true";
    String friday="true";
    String saturday="false";
    String sunday="false";

    private Button button_ok;
    private CheckBox chk_A, chk_B, chk_C, chk_D, chk_E, chk_F, chk_G;

    public void initView()
    {
        this.button_ok = (Button)findViewById(R.id.button_ok);
        this.chk_A = (CheckBox)findViewById(R.id.chk_A);
        this.chk_B = (CheckBox)findViewById(R.id.chk_B);
        this.chk_C = (CheckBox)findViewById(R.id.chk_C);
        this.chk_D = (CheckBox)findViewById(R.id.chk_D);
        this.chk_E = (CheckBox)findViewById(R.id.chk_E);
        this.chk_F = (CheckBox)findViewById(R.id.chk_F);
        this.chk_G = (CheckBox)findViewById(R.id.chk_G);
    }

    public Ui_StoreManagement_Store_Registration_OpenWeek_Dialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_management_store_registration_openweek_dialog);
        Context_Ui_StoreManagement_Store_Registration_OpenWeek_Dialog = context;

        initView();

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chk_A.isChecked()==true) { monday="true"; }
                else { monday="false"; }

                if(chk_B.isChecked()==true) { tuesday="true"; }
                else { tuesday="false"; }

                if(chk_C.isChecked()==true) { wednesday="true"; }
                else { wednesday="false"; }

                if(chk_D.isChecked()==true) { thursday="true"; }
                else { thursday="false"; }

                if(chk_E.isChecked()==true) { friday="true"; }
                else { friday="false"; }

                if(chk_F.isChecked()==true) { saturday="true"; }
                else { saturday="false"; }

                if(chk_G.isChecked()==true) { sunday="true"; }
                else { sunday="false"; }

                Bundle args = new Bundle();
                args.putString("monday", monday);
                args.putString("tuesday", tuesday);
                args.putString("wednesday", wednesday);
                args.putString("thursday", thursday);
                args.putString("friday", friday);
                args.putString("saturday", saturday);
                args.putString("sunday", sunday);
                Ui_StoreManagement_Store_Registration_Activity.setArguments(args);

                ((Ui_StoreManagement_Store_Registration_Activity)Ui_StoreManagement_Store_Registration_Activity.Context_Ui_StoreManagement_Store_Registration_Activity).Static_function_management.Ui_StoreManagement_Store_Registration_OpenWeek_Dialog_End();

            }
        });

    }

    public void setArguments(Bundle args) {
        monday = args.getString("monday");
        if(monday.equals("true")) { chk_A.setChecked(true); }
        else { chk_A.setChecked(false); }

        tuesday = args.getString("tuesday");
        if(tuesday.equals("true")) { chk_B.setChecked(true); }
        else { chk_B.setChecked(false); }

        wednesday = args.getString("wednesday");
        if(wednesday.equals("true")) { chk_C.setChecked(true); }
        else { chk_C.setChecked(false); }

        thursday = args.getString("thursday");
        if(thursday.equals("true")) { chk_D.setChecked(true); }
        else { chk_D.setChecked(false); }

        friday = args.getString("friday");
        if(friday.equals("true")) { chk_E.setChecked(true); }
        else { chk_E.setChecked(false); }

        saturday = args.getString("saturday");
        if(saturday.equals("true")) { chk_F.setChecked(true); }
        else { chk_F.setChecked(false); }

        sunday = args.getString("sunday");
        if(sunday.equals("true")) { chk_G.setChecked(true); }
        else { chk_G.setChecked(false); }
    }
}
