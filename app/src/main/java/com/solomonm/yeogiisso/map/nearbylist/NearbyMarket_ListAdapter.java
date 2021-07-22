package com.solomonm.yeogiisso.map.nearbylist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.solomonm.yeogiisso.R;
import com.solomonm.yeogiisso.Ui_Main_Activity;

import java.util.List;

public class NearbyMarket_ListAdapter extends RecyclerView.Adapter<NearbyMarket_ListAdapter.ViewHolder> {

    private Context context;
    private List<NearbyMarket_List> nearbymarket_userList;
    private Activity parentActivity;
    private List<NearbyMarket_List> nearbymarket_saveList;

    int i=1;

    public NearbyMarket_ListAdapter(Context context, List<NearbyMarket_List> nearbymarket_userList, Activity parentActivity, List<NearbyMarket_List> nearbymarket_saveList)
    {
        this.context = context;
        this.nearbymarket_userList = nearbymarket_userList;
        this.parentActivity = parentActivity;
        this.nearbymarket_saveList = nearbymarket_saveList;
    }

    @NonNull
    @Override
    public NearbyMarket_ListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_map_nearbymarket_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull final NearbyMarket_ListAdapter.ViewHolder viewHolder, final int position) {

        nearbymarket_userList.get(position);

        int meter = (int) Math.round(nearbymarket_userList.get(position).get_distance());
        viewHolder.tv_nearby_distance.setText(meter+"M");

        viewHolder.tv_nearby_sls_dw.setText("영업일 : "+nearbymarket_userList.get(position).get_sls_dw()+", "+nearbymarket_userList.get(position).get_sls_st_tm()+" ~ "+nearbymarket_userList.get(position).get_sls_ed_tm());

        viewHolder.tv_str_nm.setText(nearbymarket_userList.get(position).get_str_nm());

        //viewHolder.tv_nearby_menu_des.setText("판매음식\n["+nearbymarket_userList.get(position).get_menu_des()+"]");

        viewHolder.ll_bottom_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(new LatLng(nearbymarket_userList.get(position).get_ypos(), nearbymarket_userList.get(position).get_xpos()));
                ((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).gMap.animateCamera(cameraUpdate);
                //((Ui_Main_Activity)Ui_Main_Activity.Context_UiMainActivity).MainActivityEvent("MapCamera_Move", nearbymarket_userList.get(position).get_ypos(), nearbymarket_userList.get(position).get_xpos());
            }
        });

        if(meter > 450)
        {
            viewHolder.ll_not_open.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return nearbymarket_userList.size();
    }

    public void setItem(List<NearbyMarket_List> nearbymarket_userList)
    {
        this.nearbymarket_userList = nearbymarket_userList;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nearby_distance, tv_nearby_sls_dw, tv_nearby_menu_des, tv_str_nm;
        LinearLayout ll_bottom_list, ll_not_open;
        ImageView iv_profile;

        ViewHolder(View itemView) {
            super(itemView);

            tv_nearby_distance = itemView.findViewById(R.id.tv_nearby_distance);
            tv_nearby_sls_dw = itemView.findViewById(R.id.tv_nearby_sls_dw);
            tv_nearby_menu_des = itemView.findViewById(R.id.tv_nearby_menu_des);
            tv_str_nm = itemView.findViewById(R.id.tv_str_nm);
            ll_bottom_list = itemView.findViewById(R.id.ll_bottom_list);
            ll_not_open = itemView.findViewById(R.id.ll_not_open);
            iv_profile = itemView.findViewById(R.id.iv_profile);

        }
    }

}
