package com.tripalocal.bentuke.Views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.adapters.MessageListAdapter;
import com.tripalocal.bentuke.helpers.NotificationHelper;
import com.tripalocal.bentuke.helpers.dbHelper.ChatListDataSource;
import com.tripalocal.bentuke.models.database.ChatList_model;

import java.util.ArrayList;
import java.util.List;

public class MsgListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private static RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<ChatList_model> messages;
    private ChatListDataSource chatList_db_source;
    private static MessageListAdapter adapter;
    public MsgListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        return view;
    }

    private void initializeData(){
        //get All message list from database or web server.
        messages = new ArrayList<>();
        ArrayList<ChatList_model> lists=new ArrayList<ChatList_model>();

        try {
            chatList_db_source.open();
            lists =(ArrayList<ChatList_model>)chatList_db_source.getChatList();
            chatList_db_source.close();
        }catch (Exception e){
            System.out.println("exception"+e.getMessage().toString());
        }
        for(ChatList_model model :lists){
            messages.add(model);
        }
        NotificationHelper.clearBadge();
    }
    public void onResume() {
        super.onResume();
        initializeData();
        adapter.notifyDataSetChanged();
//        MobclickAgent.onPageStart(getActivity().getResources().getString(R.string.youmeng_fragment_login)); //统计页面
    }
    public void onPause() {
        super.onPause();
//        MobclickAgent.onPageEnd(getActivity().getResources().getString(R.string.youmeng_fragment_login));
    }

    public static void notfiChangeOfAdapter(){
        mAdapter.notifyDataSetChanged();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
}
