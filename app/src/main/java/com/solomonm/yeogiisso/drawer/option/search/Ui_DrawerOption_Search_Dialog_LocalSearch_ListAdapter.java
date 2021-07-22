package com.solomonm.yeogiisso.drawer.option.search;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.static_init.Static_Method;
import com.solomonm.yeogiisso.static_init.Static_initView;

import java.util.List;

public class Ui_DrawerOption_Search_Dialog_LocalSearch_ListAdapter extends RecyclerView.Adapter<Ui_DrawerOption_Search_Dialog_LocalSearch_ListAdapter.ViewHolder> {

    private Context context;
    private List<Ui_DrawerOption_Search_Dialog_LocalSearch_List> localsearch_userList;
    private Dialog parentActivity;
    private List<Ui_DrawerOption_Search_Dialog_LocalSearch_List> localsearch_saveList;

    public Ui_DrawerOption_Search_Dialog_LocalSearch_ListAdapter(Context context, List<Ui_DrawerOption_Search_Dialog_LocalSearch_List> localsearch_userList, Dialog parentActivity, List<Ui_DrawerOption_Search_Dialog_LocalSearch_List> localsearch_saveList)
    {
        this.context = context;
        this.localsearch_userList = localsearch_userList;
        this.parentActivity = parentActivity;
        this.localsearch_saveList = localsearch_saveList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_draweroption_search_localsearch_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        Static_Method Static_Search_Detail;
        Static_initView static_initView;

        Static_Search_Detail = (Static_Method) Ui_Main_Activity.Context_UiMainActivity.getApplicationContext();
        static_initView = new Static_initView();

        localsearch_userList.get(position);

        switch (localsearch_userList.get(position).get_Class())
        {
            case "ctp":
                viewHolder.tv_ctp_sig_emd_name.setText(localsearch_userList.get(position).get_ctp());
                break;
            case "sig":
                viewHolder.tv_ctp_sig_emd_name.setText(localsearch_userList.get(position).get_sig());
                break;
            case "emd":
                viewHolder.tv_ctp_sig_emd_name.setText(localsearch_userList.get(position).get_emd());
                break;
        }

        viewHolder.tv_ctp_sig_emd_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (localsearch_userList.get(position).get_Class())
                {
                    case "ctp":
                        String ctp = localsearch_userList.get(position).get_ctp();
                        String ctp_code = localsearch_userList.get(position).get_ctp_code();

                        if(!Static_Search_Detail.getlocalsearch_ctp().equals(ctp))
                        {
                            static_initView.tv_draweroption_searchmenu_area_sig.setText(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.text_option_localsearch_none));
                            static_initView.tv_draweroption_searchmenu_area_emd.setText(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.text_option_localsearch_none));

                            Static_Search_Detail.setlocalsearch_sig("");
                            Static_Search_Detail.setlocalsearch_sig_code("");
                            Static_Search_Detail.setlocalsearch_emd("");
                        }

                        Static_Search_Detail.setlocalsearch_ctp(ctp);
                        Static_Search_Detail.setlocalsearch_ctp_code(ctp_code);

                        static_initView.tv_draweroption_searchmenu_area_ctp.setText(ctp);

                        parentActivity.dismiss();

                        break;
                    case "sig":
                        String sig = localsearch_userList.get(position).get_sig();
                        String sig_code = localsearch_userList.get(position).get_sig_code();

                        if(!Static_Search_Detail.getlocalsearch_sig().equals(sig))
                        {
                            static_initView.tv_draweroption_searchmenu_area_emd.setText(((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).getString(R.string.text_option_localsearch_none));

                            Static_Search_Detail.setlocalsearch_emd("");
                        }

                        Static_Search_Detail.setlocalsearch_sig(sig);
                        Static_Search_Detail.setlocalsearch_sig_code(sig_code);

                        static_initView.tv_draweroption_searchmenu_area_sig.setText(sig);

                        parentActivity.dismiss();

                        break;
                    case "emd":
                        String emd = localsearch_userList.get(position).get_emd();
                        Static_Search_Detail.setlocalsearch_emd(emd);

                        static_initView.tv_draweroption_searchmenu_area_emd.setText(emd);


                        parentActivity.dismiss();
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return localsearch_userList.size();
    }

    public void setItem(List<Ui_DrawerOption_Search_Dialog_LocalSearch_List> localsearch_userList)
    {
        this.localsearch_userList = localsearch_userList;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ctp_sig_emd_name;

        ViewHolder(View itemView) {
            super(itemView);

            tv_ctp_sig_emd_name = itemView.findViewById(R.id.tv_ctp_sig_emd_name);

        }
    }

}
