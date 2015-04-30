package tripalocal.com.au.tripalocalbeta.adapters;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import tripalocal.com.au.tripalocalbeta.helpers.Login_Result;
import tripalocal.com.au.tripalocalbeta.helpers.SearchRequest;
import tripalocal.com.au.tripalocalbeta.helpers.Search_Result;
import tripalocal.com.au.tripalocalbeta.models.MyTrip;

/**
 * Created by naveen on 4/6/2015.
 */
public interface ApiService {

    @POST("/service_search/")
    void getSearchResults(@Body SearchRequest data_json, Callback<Search_Result[]> cb);

    @FormUrlEncoded
    @POST("/service_login/")
    void loginUser(@Field("email")String email,@Field("password")String pwd, Callback<Login_Result> response);

    @FormUrlEncoded
    @POST("/service_signup/")
    void signup_user(@Field("email")String email, @Field("password")String pwd, @Field("first_name")
    String firstName, @Field("last_name")String lastName,Callback<Login_Result> response);

    @GET("/service_mytrip/")
    void getMyTrip(Callback<ArrayList<MyTrip>> response);

}
