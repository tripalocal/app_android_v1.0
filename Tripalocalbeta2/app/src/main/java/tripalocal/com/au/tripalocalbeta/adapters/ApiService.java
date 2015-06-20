package tripalocal.com.au.tripalocalbeta.adapters;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import tripalocal.com.au.tripalocalbeta.helpers.Login_Result;
import tripalocal.com.au.tripalocalbeta.helpers.SearchRequest;
import tripalocal.com.au.tripalocalbeta.models.network.Coupon_Result;
import tripalocal.com.au.tripalocalbeta.models.network.Credit_Request;
import tripalocal.com.au.tripalocalbeta.models.network.MyProfile_result;
import tripalocal.com.au.tripalocalbeta.models.MyTrip;
import tripalocal.com.au.tripalocalbeta.models.network.Search_Result;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.Experience_Detail;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.request;

/**
 * Created by naveen on 4/6/2015.
 */
public interface ApiService {

    @POST("/service_search/")
    void getSearchResults(@Body SearchRequest data_json, Callback<List<Search_Result>> cb);

    @POST("/service_experience/")
    void getExpDetails(@Body request data_json, Callback<Experience_Detail> cb);

    @FormUrlEncoded
    @POST("/service_login/")
    void loginUser(@Field("email")String email,@Field("password")String pwd, Callback<Login_Result> response);

    @FormUrlEncoded
    @POST("/service_signup/")
    void signup_user(@Field("email")String email, @Field("password")String pwd, @Field("first_name")
    String firstName, @Field("last_name")String lastName,Callback<Login_Result> response);

    @GET("/service_mytrip/")
    void getMyTrip(Callback<ArrayList<MyTrip>> response);

    @GET("/service_myprofile/")
    void getMyProfileDetails(Callback<MyProfile_result> response);

    @POST("/service_myprofile/")
    void saveMyProfileDetails(String phone_number , Callback<MyProfile_result> response);

    @POST("/service_booking/")
    void bookExperience(@Body Credit_Request data, Callback<String> response);

    @POST("/service_booking/")
    void bookAliPayExperience(@Body String data_json, Callback<String> response);

    @GET("/service_couponverification/")
    void verifyCouponCode(@Body String data_json, Callback<Coupon_Result> response);

}
