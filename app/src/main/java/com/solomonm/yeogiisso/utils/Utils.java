package com.solomonm.yeogiisso.utils;

import android.app.Activity;
import android.os.Build;

import androidx.core.content.ContextCompat;

import com.solomonm.yeogiisso.R;

public class Utils {
    public enum StatusBarColorType {
        BLACK_STATUS_BAR( R.color.black ),
        WHITE_STATUS_BAR( R.color.white ),
        GRAY_STATUS_BAR( R.color.gray ),
        SOFTGRAY_STATUS_BAR( R.color.soft_gray ),
        DARKGRAY_STATUS_BAR( R.color.dark_gray ),
        MAIN_CONCEPT_STATUS_BAR( R.color.main_concept_color ),
        TRANSPARENT_STATUS_BAR( R.color.transparent );

        private int backgroundColorId;

        StatusBarColorType(int backgroundColorId){
            this.backgroundColorId = backgroundColorId;
        }

        public int getBackgroundColorId() {
            return backgroundColorId;
        }
    }

    public static void setStatusBarColor(Activity activity, StatusBarColorType colorType) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity, colorType.getBackgroundColorId()));
        }
    }
}
