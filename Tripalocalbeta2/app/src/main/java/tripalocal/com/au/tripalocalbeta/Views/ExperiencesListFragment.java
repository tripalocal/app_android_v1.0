package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter;


public class ExperiencesListFragment extends Fragment{

    public ExperiencesListFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experiences_list, container, false);
        RecyclerView rv = (RecyclerView) view.findViewById(R.id.recycle_view_exp_list);
        LinearLayoutManager LLM = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(LLM);
        rv.setAdapter(new ExperienceListAdapter(getActivity().getApplicationContext()));
        return view;
    }
}