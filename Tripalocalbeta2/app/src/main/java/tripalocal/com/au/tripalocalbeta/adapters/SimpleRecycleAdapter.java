package tripalocal.com.au.tripalocalbeta.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.Views.HomeActivity;
import tripalocal.com.au.tripalocalbeta.Views.HomeActivityFragment;

/**
 * Created by naveen on 4/17/2015.
 */
public class SimpleRecycleAdapter extends RecyclerView.Adapter<SimpleRecycleAdapter.SimpleViewHolder>{

    private static final String[] places = {"Melbourne", "Sydney", "Brisbane", "Cairns", "Goldcoast", "Hobart", "Adelaide"};
    private static HomeActivityFragment HomeFrag;

    private static final String[] bg_urls = {"https://www.tripalocal.com/images/mobile/home/Melbourne.jpg",
                                                "https://www.tripalocal.com/images/mobile/home/Sydney.jpg",
                                                "https://www.tripalocal.com/images/mobile/home/Brisbane.jpg",
                                                "https://www.tripalocal.com/images/mobile/home/Cairns.jpg",
                                                "https://www.tripalocal.com/images/mobile/home/Goldcoast.jpg",
                                                "https://www.tripalocal.com/images/mobile/home/Hobart.jpg",
                                                "https://www.tripalocal.com/images/mobile/home/Adelaide.jpg"};

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_card_layout, viewGroup, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder simpleViewHolder, int i) {
        simpleViewHolder.text.setText(places[i]);
        Glide.with(HomeActivity.getHome_context()).load(bg_urls[i]).centerCrop()
                .into(simpleViewHolder.image);
        simpleViewHolder.itemView.setTag(i);
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
        public ImageView image;

        public SimpleViewHolder(View item_view){
            super(item_view);
            text = (TextView) item_view.findViewById(R.id.card_text);
            image = (ImageView) item_view.findViewById(R.id.card_bg_image);
            item_view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          if(v instanceof CardView){
              Toast.makeText(v.getContext(), v.getTag().toString(),Toast.LENGTH_SHORT).show();
              HomeFrag.displayListFrag2((int)v.getTag());
          }else{
              Toast.makeText(v.getContext(), "vada poche",Toast.LENGTH_SHORT).show();
          }
        }
    }
}
