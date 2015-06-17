package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.widget.*;

import com.google.gson.Gson;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.mime.TypedByteArray;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ApiService;
import tripalocal.com.au.tripalocalbeta.helpers.Login_Result;
import tripalocal.com.au.tripalocalbeta.helpers.ToastHelper;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.Experience_Detail;
import tripalocal.com.au.tripalocalbeta.models.exp_detail.request;

import org.json.*;
import android.content.*;

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
        payByCard();

//        if(validateInput(card_no_s,card_month_s,card_year_s,card_cvv_s)){
//        }
    }

    public boolean validateInput(String no,String month,String year,String cvv){
        if(no.length()<12 || no.length()>16){
            ToastHelper.errorToast(getResources().getString(R.string.credit_card_no_error));
            return false;
        }else if(month.length()!= 2 || Integer.parseInt(month)>12
                || Integer.parseInt(month)<1){
            ToastHelper.errorToast(getResources().getString(R.string.credit_card_month_error));
            return false;
        }else if(year.length()!=4 || Integer.parseInt(year)<2015){
            ToastHelper.errorToast(getResources().getString(R.string.credit_card_year_error));
            return false;
        }else if(cvv.length()!=3){
            ToastHelper.errorToast(getResources().getString(R.string.credit_card_cvv_error));
            return false;
        }
        return true;
    }

    public void payByCard(){
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
        System.out.println("token"+getUserToken()+"");
        ApiService apiService = restAdapter.create(ApiService.class);
//        System.out.println(ep.getE);

        Gson json=new Gson();
        json.toJson("{test:asd}");
        System.out.println("Json string : "+ json.toString() );
        System.out.println(createJson("","","",""));
        apiService.bookExperience(createJson("","","",""), new Callback<String>() {
            @Override
            public void success(String message, Response response) {
                ToastHelper.errorToast("Success");
                System.out.println("process success");

            }

            @Override
            public void failure(RetrofitError error) {
//                String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());

//                ToastHelper.errorToast("Echo errors"+json.toString());
                ToastHelper.errorToast("Echo errors");
                Intent intent = new Intent(getApplicationContext(), PaymentSuccessActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
//
            }
        });
    }

    public String createJson(String no,String month,String year,String cvv){
        String s="";
        String id=CheckoutActivity.position+"";
        String date=CheckoutActivity.date;
        String time=CheckoutActivity.time;
        String guest_num=CheckoutActivity.guest;

        try {
            JSONObject globalObj=new JSONObject();

//            complie itinerary string
            JSONArray itinerary_list=new JSONArray();
            JSONObject itinerary_string=new JSONObject();
            itinerary_string.put("id","20");
            itinerary_string.put("date","2016/04/17");
            itinerary_string.put("time","4:00-6:00");
            itinerary_string.put("guest_number",2);
            itinerary_list.put(itinerary_string);
            globalObj.put("itinerary_string",itinerary_list);

            //add crad info
            globalObj.put("card_number","4242424242424242");
            globalObj.put("expiration_month","10");
            globalObj.put("expiration_year","2017");
            globalObj.put("cvv",664);
            s=globalObj.toString();
            s=s.replace("\\","");

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


