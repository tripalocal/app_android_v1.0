package com.tripalocal.bentuke.Views;

import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.umeng.analytics.MobclickAgent;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.helpers.ToastHelper;
import com.tripalocal.bentuke.models.exp_detail.Experience_Detail;
import com.tripalocal.bentuke.models.network.Booking_Result;
import com.tripalocal.bentuke.models.network.Credit_Request;

/**
 * Created by user on 16/06/2015.
 */
public class CreditCardActivity  extends AppCompatActivity {
    EditText card_no,card_month,card_year,card_cvv;
    private Experience_Detail exp;//position is experience id
    private int exp_id;
    TableRow row1,row2,row3;
    View divider1,divider2,divider3;



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
row1=(TableRow)this.findViewById(R.id.row_num);
        row2=(TableRow)this.findViewById(R.id.row_expire);
        row3=(TableRow)this.findViewById(R.id.row_ccv);
        divider1=(View)this.findViewById(R.id.divider_line_3);
        divider2=(View)this.findViewById(R.id.divider_line_4);
        divider3=(View)this.findViewById(R.id.divider_line_5);

        setPaymentContentVisibility();
    }


    public void setPaymentContentVisibility(){
        if(CheckoutActivity.total_price.equals("0")){
            row1.setVisibility(View.GONE);
            row2.setVisibility(View.GONE);
            row3.setVisibility(View.GONE);
            divider1.setVisibility(View.GONE);
            divider2.setVisibility(View.GONE);
            divider3.setVisibility(View.GONE);
        }else{
            row1.setVisibility(View.VISIBLE);
            row2.setVisibility(View.VISIBLE);
            row3.setVisibility(View.VISIBLE);
            divider1.setVisibility(View.VISIBLE);
            divider2.setVisibility(View.VISIBLE);
            divider3.setVisibility(View.VISIBLE);

        }
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

        if(CheckoutActivity.total_price.equals("0")){
            payByCard("", "", "", "");
        }else {
            if (validateInput(card_no_s, card_month_s, card_year_s, card_cvv_s)) {
                payByCard(card_no_s, card_month_s, card_year_s, card_cvv_s);
            }
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
        GeneralHelper.showLoadingProgress(this);
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
        //System.out.println("create json"+createJson(no,month,year,cvv));

       // apiService.bookExperience(createJson(no,month,year,cvv), new Callback<String>() {
Credit_Request request;
        if(CheckoutActivity.total_price.equals("0")) {
            request=getCreditRequest_Zero();
        }else{
             request = getCreditRequest(no, month, year, cvv);

        }
        //apiService.bookExperience(getCreditRequest(no,month,year,cvv), new Callback<String>() {
        apiService.bookExperience(request, new Callback<Booking_Result>() {
            @Override
            public void success(Booking_Result message, Response response) {
//                ToastHelper.errorToast("Success");
                GeneralHelper.closeLoadingProgress();

                updateYoumeng();
                Intent intent = new Intent(getApplicationContext(), PaymentSuccessActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }

            @Override
            public void failure(RetrofitError error) {
                GeneralHelper.closeLoadingProgress();

                String json =  new String(((TypedByteArray)error.getResponse().getBody()).getBytes());
                ToastHelper.errorToast(getResources().getString(R.string.payment_failure));
                System.out.println(json);

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
//        System.out.println(cred_req);
        Credit_Request cred_req = new Credit_Request(no,Integer.parseInt(month),Integer.parseInt("20"+year),Integer.parseInt(cvv),coupon_code,itinerary_list);
        System.out.println("card no " + cred_req.getCard_number() + " month " + cred_req.getExpiration_month() +
                "year " + cred_req.getExpiration_year() + " coupon" + cred_req.getCoupon() + " cvv" + cred_req.getCvv() +
                "itenaray_no" + cred_req.getItinerary_string().size());

        return cred_req;
    }

    private Credit_Request getCreditRequest_Zero() {
        String no="4242424242424242";
        String month="12";
        String year="12";
        String cvv="646";
        String id=CheckoutActivity.position+"";
        String datearr[]=(CheckoutActivity.date).split("/");
        String date=datearr[2]+"/"+datearr[1]+"/"+datearr[0];
        String time=CheckoutActivity.time;
        String guest_num=CheckoutActivity.guest;
        String coupon_code = CheckoutActivity.coupon;
        Credit_Request.ItineraryString itenerary = new Credit_Request.ItineraryString(id,date,time,Integer.parseInt(guest_num));
        List<Credit_Request.ItineraryString> itinerary_list = new ArrayList<>();
        itinerary_list.add(itenerary);
//        System.out.println(cred_req);
        Credit_Request cred_req = new Credit_Request(no,Integer.parseInt(month),Integer.parseInt("20"+year),Integer.parseInt(cvv),coupon_code,itinerary_list);
        System.out.println("card no "+cred_req.getCard_number()+" month "+cred_req.getExpiration_month()+
                "year "+cred_req.getExpiration_year()+" coupon"+cred_req.getCoupon()+" cvv"+cred_req.getCvv()+
                "itenaray_no"+cred_req.getItinerary_string().size());

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
    //System.out.println("Coupon"+coupon_text);
        }catch (Exception e){
        }
        return s;
    }

    private String getUserToken()
    {
        //TODO
        return HomeActivity.getCurrent_user().getLogin_token();//"73487d0eb131a6822e08cd74612168cf6e0755dc";//
    }
    public void onResume() {
        super.onResume();
        setPaymentContentVisibility();

        MobclickAgent.onResume(this);       //统计时长
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public void updateYoumeng(){
        HashMap<String,String> map = new HashMap<String,String>();
        map.put(getResources().getString(R.string.youmeng_event_item_expId), CheckoutActivity.position + "");
        map.put(getResources().getString(R.string.youmeng_event_item_guestNum), CheckoutActivity.guest+"" + "");
        map.put(getResources().getString(R.string.youmeng_event_item_expDate), CheckoutActivity.date+"" + "");
        map.put(getResources().getString(R.string.youmeng_event_item_paymentMethod), getResources().getString(R.string.youmeng_event_item_creditCardPayment));
        MobclickAgent.onEvent(this.getApplicationContext(), getResources().getString(R.string.youmeng_event_title_payment), map);
    }


}


