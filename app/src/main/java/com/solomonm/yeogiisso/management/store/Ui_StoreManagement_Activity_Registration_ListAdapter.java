package com.solomonm.yeogiisso.management.store;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;
import com.solomonm.yeogiisso.map.nearbylist.NearbyMarket_List;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class Ui_StoreManagement_Activity_Registration_ListAdapter extends RecyclerView.Adapter<Ui_StoreManagement_Activity_Registration_ListAdapter.ViewHolder> {

    private Context context;
    private List<Ui_StoreManagement_Activity_Registration_List> registrationstore_userList;
    private Activity parentActivity;
    private List<Ui_StoreManagement_Activity_Registration_List> registrationstore_saveList;

    int i=1;

    public Ui_StoreManagement_Activity_Registration_ListAdapter(Context context, List<Ui_StoreManagement_Activity_Registration_List> registrationstore_userList, Activity parentActivity, List<Ui_StoreManagement_Activity_Registration_List> registrationstore_saveList)
    {
        this.context = context;
        this.registrationstore_userList = registrationstore_userList;
        this.parentActivity = parentActivity;
        this.registrationstore_saveList = registrationstore_saveList;
    }

    @NonNull
    @Override
    public Ui_StoreManagement_Activity_Registration_ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_storemanagement_registration_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final Ui_StoreManagement_Activity_Registration_ListAdapter.ViewHolder viewHolder, final int position) {

        registrationstore_userList.get(position);

        //viewHolder.tv_nearby_sls_dw.setText("영업일 : "+registrationstore_userList.get(position).get_sls_dw()+", "+registrationstore_userList.get(position).get_sls_st_tm()+" ~ "+registrationstore_userList.get(position).get_sls_ed_tm());

        viewHolder.tv_str_nm.setText(registrationstore_userList.get(position).get_STORE_NM());
        viewHolder.tv_str_catemain.setText(registrationstore_userList.get(position).get_STORE_CATE_MAIN());
        viewHolder.tv_str_catesub.setText(registrationstore_userList.get(position).get_STORE_CATE_SUB());

        //if(이미지불러오기성공시)
        viewHolder.tv_profile_wait.setVisibility(View.GONE);

        String dd1 = "http://dolsup.synology.me:8080"+registrationstore_userList.get(position).get_ATTACH_SV_PATH()+"/"+registrationstore_userList.get(position).get_ATTACH_SV_FNM();
        String dd2 = "http://mhserver.duckdns.org:8880/android/yeogiisso/File/Pic/"+registrationstore_userList.get(position).get_ATTACH_SV_FNM();
        String dd3 = dd1;
        String dd4 = dd2;

        Glide.with(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity).load(dd2).into(viewHolder.iv_profile);

        /*
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    URL url = new URL(dd2);
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);

                    Glide.with(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity).load(bm).into(viewHolder.iv_profile);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        t.start();
         */

        /*
        new Thread(new Runnable() {
            public void run() {
                try
                {
                    URL url = new URL(dd2);
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);

                    Glide.with(Ui_StoreManagement_Activity.Context_Ui_StoreManagement_Activity).load(bm).into(viewHolder.iv_profile);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
         */




    }

    @Override
    public int getItemCount() {
        return registrationstore_userList.size();
    }

    public void setItem(List<Ui_StoreManagement_Activity_Registration_List> registrationstore_userList)
    {
        this.registrationstore_userList = registrationstore_userList;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nearby_distance, tv_nearby_sls_dw, tv_nearby_menu_des, tv_str_nm, tv_str_catemain, tv_str_catesub, tv_profile_wait;
        LinearLayout ll_bottom_list, ll_not_open;
        ImageView iv_profile;

        ViewHolder(View itemView) {
            super(itemView);

            /*
            tv_nearby_distance = itemView.findViewById(R.id.tv_nearby_distance);
            tv_nearby_sls_dw = itemView.findViewById(R.id.tv_nearby_sls_dw);
            tv_nearby_menu_des = itemView.findViewById(R.id.tv_nearby_menu_des);
            tv_str_nm = itemView.findViewById(R.id.tv_str_nm);
            ll_bottom_list = itemView.findViewById(R.id.ll_bottom_list);
            ll_not_open = itemView.findViewById(R.id.ll_not_open);
            iv_profile = itemView.findViewById(R.id.iv_profile);
             */

            tv_str_nm = itemView.findViewById(R.id.tv_str_nm);
            tv_str_catemain = itemView.findViewById(R.id.tv_str_catemain);
            tv_str_catesub = itemView.findViewById(R.id.tv_str_catesub);
            tv_profile_wait = itemView.findViewById(R.id.tv_profile_wait);

            iv_profile = itemView.findViewById(R.id.iv_profile);

        }
    }

}
