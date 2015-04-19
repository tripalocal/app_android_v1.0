package tripalocal.com.au.tripalocalbeta.adapters;
import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import tripalocal.com.au.tripalocalbeta.models.SearchRequest;

/**
 * Created by naveen on 4/6/2015.
 */
public interface ApiService {
    @POST("/service_search/")
    void getSearchResults(@Body SearchRequest data_json, Callback<String> cb);

    @FormUrlEncoded
    @POST("/service_login")
    void loginUser(@Field("data")String credentials, Callback<String> response);

    @POST("/service_login")
    void login_user(@Body String creds, Callback<String> response);


    //void loginUser(@Field("email") String email, @Field("password") String password, Callback<String> response);

    //void getSearchResults(SearchRequest data_json, Callback<List<String>> cb);
}
