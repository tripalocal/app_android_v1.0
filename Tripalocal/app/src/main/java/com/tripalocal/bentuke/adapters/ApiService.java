package com.tripalocal.bentuke.adapters;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

import com.tripalocal.bentuke.helpers.Login_Result;
import com.tripalocal.bentuke.helpers.SearchRequest;
import com.tripalocal.bentuke.models.Experience;
import com.tripalocal.bentuke.models.MyTrip;
import com.tripalocal.bentuke.models.exp_detail.Experience_Detail;
import com.tripalocal.bentuke.models.exp_detail.WishList_Retrieve_Result;
import com.tripalocal.bentuke.models.exp_detail.request;
import com.tripalocal.bentuke.models.network.Booking_Result;
import com.tripalocal.bentuke.models.network.Conversation_Result;
import com.tripalocal.bentuke.models.network.Coupon_Result;
import com.tripalocal.bentuke.models.network.Credit_Request;
import com.tripalocal.bentuke.models.network.LoginFBRequest;
import com.tripalocal.bentuke.models.network.MsgListModel;
import com.tripalocal.bentuke.models.network.MyProfile_result;
import com.tripalocal.bentuke.models.network.Profile_result;
import com.tripalocal.bentuke.models.network.Search_Result;
import com.tripalocal.bentuke.models.network.SignupRequest;
import com.tripalocal.bentuke.models.network.Update_Conversation_Request;
import com.tripalocal.bentuke.models.network.Update_Conversation_Result;
import com.tripalocal.bentuke.models.network.WishList_update_Request;
import com.tripalocal.bentuke.models.network.Wishlist_Update_Result;
import com.tripalocal.bentuke.models.network.profileUpdateRequest;

/**
 * Created by naveen on 4/6/2015.
 */
public interface ApiService {


    @GET("/service_message_list/")
    void getAllMessageList(Callback<ArrayList<MsgListModel>> response);

    @GET("/service_message/")
    void getConversationById(@Query("sender_id") int sender_id,
                             @Query("last_update_id") int user_id,Callback<ArrayList<Conversation_Result>> response);

    @POST("/service_message/")
    void updateConversation(@Body Update_Conversation_Request request,Callback<Update_Conversation_Result> response);

    @POST("/service_wishlist/")
    void UpdateWishList(@Body WishList_update_Request request,Callback<Wishlist_Update_Result> response);

    @GET("/service_wishlist/")
    void RetrieveWishList(Callback<ArrayList<Experience>> response);

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

    @GET("/service_publicprofile/")
    void getPublicProfile(@Query("user_id") String user_id,Callback<Profile_result> response);


    @POST("/service_myprofile/")
    void saveMyProfileDetails(@Body profileUpdateRequest phone , Callback<MyProfile_result> response);

    @POST("/service_booking/")
    void bookExperience(@Body Credit_Request data, Callback<Booking_Result> response);

    @POST("/service_booking/")
    void bookAliPayExperience(@Body String data_json, Callback<String> response);

    @POST("/service_couponverification/")
    void verifyCouponCode(@Body String data_json, Callback<Coupon_Result> response);


}
