package tripalocal.com.au.tripalocalbeta.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import tripalocal.com.au.tripalocalbeta.R;

public class BlankFragment extends Fragment {

    private static String msg_to_show;
    public static final String MSG_EXTRA = "message";

    public BlankFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null)
            msg_to_show = getArguments().getString(MSG_EXTRA);
        else msg_to_show = getResources().getString(R.string.empty_list_err);
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView msg_txt = (TextView) view.findViewById(R.id.blank_msg);
        msg_txt.setText(msg_to_show);
        return view;
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_blank)); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_blank));
    }
}
