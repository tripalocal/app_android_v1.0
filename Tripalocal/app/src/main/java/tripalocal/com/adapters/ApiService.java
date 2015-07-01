package tripalocal.com.adapters;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import tripalocal.com.helpers.Login_Result;
import tripalocal.com.helpers.SearchRequest;
import tripalocal.com.models.MyTrip;
import tripalocal.com.models.exp_detail.Experience_Detail;
import tripalocal.com.models.exp_detail.request;
import tripalocal.com.models.network.Booking_Result;
import tripalocal.com.models.network.Coupon_Result;
import tripalocal.com.models.network.Credit_Request;
import tripalocal.com.models.network.LoginFBRequest;
import tripalocal.com.models.network.MyProfile_result;
import tripalocal.com.models.network.Search_Result;
import tripalocal.com.models.network.SignupRequest;
import tripalocal.com.models.network.profileUpdateRequest;

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

    @POST("/service_signup/")
    void signupUser(@Body SignupRequest request, Callback<Login_Result> response);

    @POST("/service_facebook_login/")
    void loginFBUser(@Body LoginFBRequest request, Callback<Login_Result> response);

    @GET("/service_mytrip/")
    void getMyTrip(Callback<ArrayList<MyTrip>> response);

    @GET("/service_myprofile/")
    void getMyProfileDetails(Callback<MyProfile_result> response);

    @POST("/service_myprofile/")
    void saveMyProfileDetails(@Body profileUpdateRequest phone , Callback<MyProfile_result> response);

    @POST("/service_booking/")
    void bookExperience(@Body Credit_Request data, Callback<Booking_Result> response);

    @POST("/service_booking/")
    void bookAliPayExperience(@Body String data_json, Callback<String> response);

    @POST("/service_couponverification/")
    void verifyCouponCode(@Body String data_json, Callback<Coupon_Result> response);

}
