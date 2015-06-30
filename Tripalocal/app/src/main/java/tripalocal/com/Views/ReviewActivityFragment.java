package tripalocal.com.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import tripalocal.com.R;
import tripalocal.com.adapters.ReviewAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReviewActivityFragment extends Fragment {

    RecyclerView rv;
    public ReviewActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recycle_view_exp_reviews);
        LinearLayoutManager LLM = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(LLM);
        rv.setAdapter(new ReviewAdapter());
        return view;
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_review)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_review));
    }

}
