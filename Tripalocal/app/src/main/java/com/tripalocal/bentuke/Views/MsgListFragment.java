package com.tripalocal.bentuke.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.ApiService;
import com.tripalocal.bentuke.adapters.MessageListAdapter;
import com.tripalocal.bentuke.helpers.GeneralHelper;
import com.tripalocal.bentuke.helpers.NotificationHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.models.Experience;
import com.tripalocal.bentuke.models.database.ChatList_model;
import com.tripalocal.bentuke.models.network.MsgListModel;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MsgListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatList_model> messages;
    private ChatListDataSource chatList_db_source;
    private static MessageListAdapter adapter;
    public static boolean isInitialise=false;
    public static final int customer_support_id=5001;
    public  static final String customer_supprt_img="img/tripalocal_custom_service_profile.png";
    public MsgListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isInitialise=true;
    }

    public static boolean getInit(){
        return  isInitialise;
    }
    public static MessageListAdapter getAdapter(){
        return adapter;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_msg_list, container, false);
        chatList_db_source=new ChatListDataSource(HomeActivity.getHome_context());
        mRecyclerView = (RecyclerView)view.findViewById(R.id.rv);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        initializeData();
        getActivity().setTitle(getResources().getString(R.string.msg_list_title));
         adapter = new MessageListAdapter(messages);
        mRecyclerView.setAdapter(adapter);
            getActivity().invalidateOptionsMenu();
        checkValidate();

        return view;
    }

    private void initializeData(){
        //get All message list from database or web server.
        //update data here .
        messages = new ArrayList<>();
        ArrayList<ChatList_model> lists=new ArrayList<ChatList_model>();

        try {
            chatList_db_source.open();
            lists =(ArrayList<ChatList_model>)chatList_db_source.getChatList();
            boolean check_customer_support_exists=true;
            for(ChatList_model model : lists){
                if(model.getSender_id().equals(""+customer_support_id)){
//                    String text,int person,String image,String time
                    check_customer_support_exists=false;
                }
            }
            if(check_customer_support_exists){
                System.out.println("comming here ");
                ChatActivity.addTextToListRecorded("WelCome!", getResources().getString(R.string.customer_support_name), customer_support_id, customer_supprt_img, "2015/11/11/12/12/12/3333");
                lists.clear();
                lists =(ArrayList<ChatList_model>)chatList_db_source.getChatList();
                System.out.println("last olne + "+lists.get(0).getSender_img());

            }
            chatList_db_source.close();
        }catch (Exception e){
            System.out.println("exception123"+e.getMessage().toString());
        }
        for(ChatList_model model :lists){
            messages.add(model);
        }
        NotificationHelper.clearBadge();
    }



    public void onResume() {
        super.onResume();
        initializeData();
//        checkValidate();
        adapter.notifyDataSetChanged();
//        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_login)); //统计页面
    }
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_login));
    }

    public void checkValidate(){
        if(messages==null || messages.size()==0) {
            Fragment no_msg_fragment = new NoMsgFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, no_msg_fragment).addToBackStack("no msg").commit();
        }
    }

    public static void notfiChangeOfAdapter(){
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
}
