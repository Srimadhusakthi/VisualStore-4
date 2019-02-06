package com.visualstore.Sharedpreference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Sharedpreference {

    public static String mPreference = "preferences";
    public static SharedPreferences.Editor  editor;
    public static SharedPreferences sharedPreferences;

    /*Visual Store application sharedpreference key*/
    public static String mOrderType = "ORDERTYPE";
    public static String mOrderStatus = "orderstatus";
    public static String mOrderType_id = "mOrderType_id";

    /*User Data*/
    public static String admin_id="id";

    /*Employee Key*/
    public static String mEmployeeId = "mEmployeeid";
    public static String mEmployeeName = "mEmployeeName";
    public static String mOrder_id = "mOrder_id";
    public static String mOrderid_id = "mOrderid_id";
    public static String mOrderEdit = "mOrderEdit";

    /*Login Values*/
//    public static String id = "id";
    public static String auth_id = "auth_id";
    public static String name = "name";
    public static String email = "email";
    public static String phone = "phone";
    public static String username = "username";
    public static String logged_in = "logged_in";
    public static String log_inauth = "log_inauth";

    /*Conatct Information*/
    public static String userinfo = "userinfo";

    /*Customer Name*/
    public static String orderreference = "orderreference";
    public static String firstname = "firstname";
    public static String lastname = "lastname";
    public static String personaldata = "personaldata";
    public static String consignee_list = "consignee_list";
    public static String employee_list = "employee_list";
    public static String side = "side";



    /*Refraction Type*/
    public static String both_single = "bothsingle";
    public static String sphere_right = "refraction_type";
    public static String cyclind_right = "cyclind_right";
    public static String axis_right = "axis_right";
    public static String addition_right = "addition_right";
    public static String sphere_left = "refraction_left";
    public static String cyclind_left = "cyclind_left";
    public static String axis_left = "axis_left";
    public static String addition_left = "addition_left";


    /*LensOrdering*/
    public static String  lens_selected_portfolio_id = "lens_selected_portfolio_id";
    public static String  lens_selected_portfolio_name = "lens_selected_portfolio_name";
    public static String  lens_typename_right = "lens_typename_right";
    public static String  lens_typename_left = "lens_typename_left";
    public static String  lens_typecode_right = "lens_typecode_right";
    public static String  lens_typecode_left = "lens_typecode_left";
    public static String  lens_diameter_id_right = "lens_diameter_id_right";
    public static String  lens_diameter_id_left = "lens_diameter_id_left";
    public static String  lens_diameter_name_right = "lens_diameter_name_right";
    public static String  lens_diameter_name_left = "lens_diameter_name_left";
    public static String  lens_individual = "lens_individual";
    public static String  lens_code_right = "lens_code_right";
    public static String  lens_code_left = "lens_code_left";


    public static String  lens_type_act = "lenstypenameact";
    public static String  lens_type_id = "lenstypenameid";
    public static String  lens_portfolio_id = "lens_portfolio_id";
    public static String  lens_diamter_right_id = "lensdiamterright";
    public static String  lens_diamter_left_id = "lensdiamterleft";
    public static String  lens_portfolio_name = "order_type_Name";
    public static String  lens_diamter_right_name = "lensdiamterright";
    public static String  lens_diamter_left_name= "lensdiamterleft";
    public static String  lens_typename_id = "lens_typename_id";


    /*Treatment*/

    public static String  lens_coatingname = "lens_coatingname";
    public static String  lens_coatingcode = "lens_coatingcode";
    public static String  lens_coatingtype = "lens_coatingtype";
    public static String  lens_tintname = "lens_tintname";
    public static String  lens_tintcode = "lens_tintcode";
    public static String  lens_tintdisplay_name = "lens_tintdisplay_name";
    public static String order_type_id = "order_type_id";
    public static String order_type_name = "order_type_Name";
    public static String cutom_id = "cutom_id";
    public static String mlens_coating_id = "mlens_coating_id";

    /*ORDER_UPLOAD_ DETAILS*/
    public static String Shape_bevel_id  = "Shape_bevel_id";

    /*AdvanceOption*/
    public static String panto_angle     = "panto_angle";
    public static String framefit       = "framefit";
    public static String bow_angle  = "bow_angle";
    public static String distance_near  = "distance_near";
    public static String neartype  = "neartype";
    public static String mid  = "mid";
    public static String indiv_engrav  = "indiv_engrav";
    public static String frame_type  = "frametype";
    public static String frame_type_id  = "frametype_id";
    public static String frame_length_r = "frame_length_r";
    public static String frame_height_r  = "frame_height_r";
    public static String frame_length_l = "frame_length_l";
    public static String frame_height_l  = "frame_height_l";
    public static String DBL_right = "dbl_right";
    public static String DBL_left = "dbl_left";
    public static String PDZ_right ="PDZ_right";
    public static String PDZ_left ="PDZ_left";
    public static String YFH_right ="YFH_right";
    public static String YFH_left ="YFH_left";
    public static String bvd_right ="bvd_right";
    public static String bvd_left ="bvd_left";




    public static void  onStorePreferences(Activity activity,String key,String value){
         sharedPreferences = activity.getSharedPreferences(mPreference,Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
         editor = editor.putString(key,value);
         editor.commit();
    }

    public static String getSharedprefernce(Activity activity,String key,String values)
    {
        sharedPreferences = activity.getSharedPreferences(mPreference,Context.MODE_PRIVATE);
        String mValue = sharedPreferences.getString(key,values);
        return  mValue;
    }






    public static void onDeleteAllValues(Activity activity){
        editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
}
