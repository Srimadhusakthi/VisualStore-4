package com.visualstore.Retrfofit;

import com.visualstore.Model.DiameterModel;
import com.visualstore.Model.EmployeeModel;
import com.visualstore.Model.GetOrderModel;
import com.visualstore.Model.LogOutModel;
import com.visualstore.Model.MyProfileModel;
import com.visualstore.Model.Result;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetroTokenService {

    /*User Logout*/
    @POST("logout_app")
    Observable<Result>   onUserLogout(@Body LogOutModel logOutModel);

    @GET("my_profile")
    Observable<MyProfileModel> onGetProfile();



}
