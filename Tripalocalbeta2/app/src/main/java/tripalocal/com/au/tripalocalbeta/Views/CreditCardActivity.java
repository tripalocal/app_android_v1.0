package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.widget.*;
import tripalocal.com.au.tripalocalbeta.R;

/**
 * Created by user on 16/06/2015.
 */
public class CreditCardActivity  extends AppCompatActivity {
    EditText card_no,card_month,card_year,card_ccv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_creditcard);
        card_no=(EditText)this.findViewById(R.id.card_no);
        card_month=(EditText)this.findViewById(R.id.card_month);
        card_year=(EditText)this.findViewById(R.id.card_year);
        card_ccv=(EditText)this.findViewById(R.id.card_ccv);
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
        if(validateInput()){

        }
    }

    public boolean validateInput(){
        if(card_no.toString().length()!=16){
            Toast.makeText(this,"Card number must be 16 digits",Toast.LENGTH_LONG);
            return false;
        }else if(card_month.toString().length()!= 2 || Integer.parseInt(card_month.toString())>12
                || Integer.parseInt(card_month.toString())<1){
            Toast.makeText(this,"Incorrect Month",Toast.LENGTH_LONG);
            return false;
        }else if(card_year.toString().length()!=4 || Integer.parseInt(card_year.toString())<2015){
            Toast.makeText(this,"Incorrect Year",Toast.LENGTH_LONG);
            return false;
        }else if(card_ccv.toString().length()!=3){
            Toast.makeText(this,"CCV must be 3 digit number",Toast.LENGTH_LONG);
            return false;
        }
        return true;
    }
}
