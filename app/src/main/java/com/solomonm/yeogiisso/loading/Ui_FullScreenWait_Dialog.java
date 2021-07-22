package com.solomonm.yeogiisso.loading;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.solomonm.yeogiisso.R;

public class Ui_FullScreenWait_Dialog extends Dialog {

    public static Context Context_FullScreenWait_Dialog;

    private ImageView iv_loading;

    private void initView()
    {
        this.iv_loading = (ImageView)findViewById(R.id.iv_loading);
    }

    public Ui_FullScreenWait_Dialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ui_fullscreenwait_dialog);
        Context_FullScreenWait_Dialog = context;

        initView();

        Glide.with(Context_FullScreenWait_Dialog).load(R.drawable.ic_loading_wait).into(iv_loading);

    }

}
