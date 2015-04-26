package tripalocal.com.au.tripalocalbeta.adapters;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import tripalocal.com.au.tripalocalbeta.helpers.Login_Result;
import tripalocal.com.au.tripalocalbeta.helpers.SearchRequest;
import tripalocal.com.au.tripalocalbeta.helpers.Search_Result;

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

}
