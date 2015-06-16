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
        String card_no_s=card_no.getText().toString();
        String card_month_s=card_month.getText().toString();
        String card_year_s=card_year.getText().toString();
        String card_ccv_s=card_ccv.getText().toString();

        if(validateInput(card_no_s,card_month_s,card_year_s,card_ccv_s)){

        }
    }

    public boolean validateInput(String no,String month,String year,String ccv){
        if(no.length()<12 || no.length()>16){
            Toast.makeText(this,"Card number must be between 12 to 16 digits",Toast.LENGTH_LONG).show();
            return false;
        }else if(month.length()!= 2 || Integer.parseInt(month)>12
                || Integer.parseInt(month)<1){
            Toast.makeText(this,"Incorrect Month",Toast.LENGTH_LONG).show();
            return false;
        }else if(year.length()!=4 || Integer.parseInt(year)<2015){
            Toast.makeText(this,"Incorrect Year",Toast.LENGTH_LONG).show();
            return false;
        }else if(ccv.length()!=3){
            Toast.makeText(this,"CCV must be 3 digit number",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
