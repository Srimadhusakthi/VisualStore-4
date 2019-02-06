package com.visualstore.Retrfofit;

import com.visualstore.Model.CoatingCodeListModel;
import com.visualstore.Model.CoatingTintModel;
import com.visualstore.Model.DeletEmployeeModel;
import com.visualstore.Model.DeleteOrderModel;
import com.visualstore.Model.DiameterModel;
import com.visualstore.Model.EmployeeAddNameModel;
import com.visualstore.Model.EmployeeModel;
import com.visualstore.Model.EmployeeResultModel;
import com.visualstore.Model.EmployeeUpdateNameModel;
import com.visualstore.Model.GetOrderData;
import com.visualstore.Model.GetOrderModel;
import com.visualstore.Model.LensEditData;
import com.visualstore.Model.LensOrderModel;
import com.visualstore.Model.LoginCredential;
import com.visualstore.Model.LoginResultData;
import com.visualstore.Model.PlaceOrderModel;
import com.visualstore.Model.ProductlensModel;
import com.visualstore.Model.Result;
import com.visualstore.Model.SentOrderModel;
import com.visualstore.Model.TintModel;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetroServices {

    /*Login User API*/
    @POST("login_app")
    Observable<LoginResultData> onLogin(@Body LoginCredential loginCredential);

    /*user signup*/
//    @POST("app_signup")
//    Observable<LO>


    /*Get Order API*/
    @GET("get_orders")
    Observable<GetOrderModel>  getOrderValues(@Query("s") String s, @Query("admin_id") String admin_id);


    /*Get Order API*/
    @GET("get_orders")
    Observable<GetOrderModel>  getAllOrderValues(@Query("admin_id") String admin_id);


    /*Get Diamter Values API*/
    @GET("get_diameter")
    Observable<DiameterModel> getDiamaterValues(@Query("lenscode") String mLenscode);


    /*Sent Order*/
    @GET("get_success_orders")
    Observable<SentOrderModel> getSuccessOrder(@Query("admin_id") String admin_id);


    /*Employee Api*/
    @GET("get_employee")
    Observable<EmployeeModel> getEmployeeList();

    /*Add Employee Api*/
    @POST("add_employee")
    Observable<EmployeeResultModel> addEmployeeName(@Body EmployeeAddNameModel employeeAddNameModel);

    /*Tintlist API*/
    @GET("tintcode_list_lookup")
    Observable<TintModel>   getTintCodeList();

    /*Product lens API*/
    @GET("product_lens_lookup")
    Observable<ProductlensModel> getProductLens();

    @GET("coatingcode_list2")
    Observable<CoatingTintModel>  getCoatingCode(@Query("lens_id") String lens_id);

    @PUT("update_employee")
    Observable<EmployeeResultModel>  updateEmployeeModel(@Body EmployeeUpdateNameModel updateNameModel);

    @HTTP( method = "DELETE" , path = "delete_employee" , hasBody = true)
    Observable<Result> deleteEmployee(@Body DeletEmployeeModel  mEmployeeid);

    @HTTP(method = "DELETE" ,path = "delete_order" , hasBody = true)
    Observable<Result> onDeleteOrder(@Body DeleteOrderModel deleteOrderModel);

    /*Place Order*/
    @POST("place_order")
    Observable<Result>   onPLaceOrder(@Body PlaceOrderModel placeOrderModel);

    /*update edit*/
    @GET("get_orders")
    Observable<LensEditData>  getOrdeEditValues(@Query("s") String s, @Query("admin_id") String admin_id, @Query("id") String id);


}
