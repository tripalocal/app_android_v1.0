package tripalocal.com.au.tripalocalbeta.adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.Views.HomeActivity;
import tripalocal.com.au.tripalocalbeta.Views.HomeActivityFragment;

/**
 * Created by naveen on 4/17/2015.
 */
public class SimpleRecycleAdapter extends RecyclerView.Adapter<SimpleRecycleAdapter.SimpleViewHolder>{

    private static final String[] places = {"Melbourne", "Sydney"};
    private static HomeActivityFragment HomeFrag;

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_card_layout, viewGroup, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder simpleViewHolder, int i) {
        simpleViewHolder.text.setText(places[i]);
        simpleViewHolder.itemView.setTag(places[i]);
    }


    public SimpleRecycleAdapter(HomeActivityFragment frag) {
        super();
        HomeFrag = frag;
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView text;

        public SimpleViewHolder(View item_view){
            super(item_view);
            text = (TextView) item_view.findViewById(R.id.card_text);
            item_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          if(v instanceof CardView){
              Toast.makeText(v.getContext(), v.getTag().toString(),Toast.LENGTH_SHORT).show();
              HomeFrag.displayListFrag(v.getTag().toString());
          }else{
              Toast.makeText(v.getContext(), "vada poche",Toast.LENGTH_SHORT).show();
          }
        }
    }
}
