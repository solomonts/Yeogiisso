package com.solomonm.yeogiisso.map.nearbylist;

public class NearbyMarket_List {
    String str_dist;
    String str_nm;
    String sls_st_tm;
    String sls_ed_tm;
    //String menu_des;
    String sls_dw;
    Double ypos;
    Double xpos;
    Double distance;

    public String get_str_dist() {
        return str_dist;
    }
    public void set_str_dist(String str_dist) {
        this.str_dist = str_dist;
    }

    public String get_str_nm() {
        return str_nm;
    }
    public void set_str_nm(String str_nm) {
        this.str_nm = str_nm;
    }

    public String get_sls_st_tm() {
        return sls_st_tm;
    }
    public void set_sls_st_tm(String sls_st_tm) {
        this.sls_st_tm = sls_st_tm;
    }

    public String get_sls_ed_tm() {
        return sls_ed_tm;
    }
    public void set_sls_ed_tm(String sls_ed_tm) {
        this.sls_ed_tm = sls_ed_tm;
    }

    /*
    public String get_menu_des() {
        return menu_des;
    }
    public void set_menu_des(String menu_des) {
        this.menu_des = menu_des;
    }
     */

    public String get_sls_dw() {
        return sls_dw;
    }
    public void set_sls_dw(String sls_dw) {
        this.sls_dw = sls_dw;
    }

    public Double get_ypos() {
        return ypos;
    }
    public void set_ypos(Double ypos) {
        this.ypos = ypos;
    }

    public Double get_xpos() {
        return xpos;
    }
    public void set_xpos(Double xpos) {
        this.xpos = xpos;
    }

    public Double get_distance() {
        return distance;
    }
    public void set_distance(Double distance) {
        this.distance = distance;
    }

    public NearbyMarket_List
            (
                    String sls_st_tm,
                    Double ypos,
                    Double xpos,
                    Double distance,
                    String sls_ed_tm,
                    String str_dist,
                    String str_nm,
                    //String menu_des,
                    String sls_dw
            )

    {
        this.sls_st_tm = sls_st_tm;
        this.ypos = ypos;
        this.xpos = xpos;
        this.distance = distance;
        this.sls_ed_tm = sls_ed_tm;
        this.str_dist = str_dist;
        this.str_nm = str_nm;
        //this.menu_des = menu_des;
        this.sls_dw = sls_dw;
    }
}
