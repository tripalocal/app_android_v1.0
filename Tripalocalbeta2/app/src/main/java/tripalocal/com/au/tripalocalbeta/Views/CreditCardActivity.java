package tripalocal.com.au.tripalocalbeta.Views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.Experience_Detail;
import tripalocal.com.au.tripalocalbeta.models.network.Booking_Result;
import tripalocal.com.au.tripalocalbeta.models.network.Credit_Request;

/**
 * Created by user on 16/06/2015.
 */
public class CreditCardActivity  extends AppCompatActivity {
    EditText card_no,card_month,card_year,card_cvv;
    private Experience_Detail exp;//position is experience id
    private int exp_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_creditcard);
        card_no=(EditText)this.findViewById(R.id.card_no);
        card_month=(EditText)this.findViewById(R.id.card_month);
        card_year=(EditText)this.findViewById(R.id.card_year);
        card_cvv=(EditText)this.findViewById(R.id.card_cvv);
        exp=CheckoutActivity.experience_to_book;
        exp_id=CheckoutActivity.position;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pay(View v){
        String card_no_s=card_no.getText().toString();
        String card_month_s=card_month.getText().toString();
        String card_year_s=card_year.getText().toString();
        String card_cvv_s=card_cvv.getText().toString();

        if(validateInput(card_no_s,card_month_s,card_year_s,card_cvv_s)){
            payByCard(card_no_s,card_month_s,card_year_s,card_cvv_s);
        }
    }

    public boolean validateInput(String no,String month,String year,String cvv){
        if(no.length()<12 || no.length()>16){
            ToastHelper.errorToast(getResources().getString(R.string.credit_card_no_error));
            return false;
        }else if(month.length()!= 2 || Integer.parseInt(month)>12
                || Integer.parseInt(month)<1){
            ToastHelper.errorToast(getResources().getString(R.string.credit_card_month_error));
            return false;
        }else if(year.length()!=2 || Integer.parseInt(year)<15){
            ToastHelper.errorToast(getResources().getString(R.string.credit_card_year_error));
            return false;
        }else if(cvv.length()!=3){
            ToastHelper.errorToast(getResources().getString(R.string.credit_card_cvv_error));
            return false;
        }
        return true;
    }

    public void payByCard(String no,String month,String year,String cvv){
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(getResources().getString(R.string.server_url))
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addHeader("Accept", "application/json");
                        request.addHeader("Authorization", "Token " + getUserToken());
                    }
                })
                .build();
//        System.out.println("token"+getUserToken()+"");
        ApiService apiService = restAdapter.create(ApiService.class);
        System.out.println("create json"+createJson(no,month,year,cvv));

       // apiService.bookExperience(createJson(no,month,year,cvv), new Callback<String>() {



        //apiService.bookExperience(getCreditRequest(no,month,year,cvv), new Callback<String>() {
        apiService.bookExperience(getCreditRequest(no,month,year,cvv), new Callback<Booking_Result>() {
            @Override
            public void success(Booking_Result message, Response response) {
//                ToastHelper.errorToast("Success");
                Intent intent = new Intent(getApplicationContext(), PaymentSuccessActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
//                String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                ToastHelper.errorToast(getResources().getString(R.string.payment_failure));

            }
        });
    }

    private Credit_Request getCreditRequest(String no, String month, String year, String cvv) {
        String id=CheckoutActivity.position+"";
        String datearr[]=(CheckoutActivity.date).split("/");
        String date=datearr[2]+"/"+datearr[1]+"/"+datearr[0];
        String time=CheckoutActivity.time;
        String guest_num=CheckoutActivity.guest;
        String coupon_code = CheckoutActivity.coupon;
        Credit_Request.ItineraryString itenerary = new Credit_Request.ItineraryString(id,date,time,Integer.parseInt(guest_num));
        List<Credit_Request.ItineraryString> itinerary_list = new ArrayList<>();
        itinerary_list.add(itenerary);
        Credit_Request cred_req = new Credit_Request(no,Integer.parseInt(month),Integer.parseInt("20"+year),Integer.parseInt(cvv),coupon_code,itinerary_list);
        return cred_req;
    }

    public String createJson(String no,String month,String year,String cvv){
        String s="";
        String id=CheckoutActivity.position+"";
        String datearr[]=(CheckoutActivity.date).split("/");
        String date=datearr[2]+"/"+datearr[1]+"/"+datearr[0];
        String time=CheckoutActivity.time;
        String guest_num=CheckoutActivity.guest;
        String coupon_text=CheckoutActivity.coupon;
        try {
            JSONObject globalObj=new JSONObject();

//            complie itinerary string
            JSONArray itinerary_list=new JSONArray();
            JSONObject itinerary_string=new JSONObject();
            itinerary_string.put("id",id);
            itinerary_string.put("date",date);
            itinerary_string.put("time",time);
            itinerary_string.put("guest_number",Integer.parseInt(guest_num));
            itinerary_list.put(itinerary_string);
            globalObj.put("itinerary_string",itinerary_list);

            //add crad info
            globalObj.put("card_number",no);//4242424242424242
            globalObj.put("expiration_month",Integer.parseInt(month));//10
            globalObj.put("expiration_year","20"+Integer.parseInt(year));//2017
            globalObj.put("cvv",Integer.parseInt(cvv));//664,integer
            globalObj.put("coupon",coupon_text);
            s=globalObj.toString();
            s=s.replace("\\","");
    System.out.println("Coupon"+coupon_text);
        }catch (Exception e){

        }

        return s;
    }

    private String getUserToken()
    {
        //TODO
        return HomeActivity.getCurrent_user().getLogin_token();//"73487d0eb131a6822e08cd74612168cf6e0755dc";//
    }
}


