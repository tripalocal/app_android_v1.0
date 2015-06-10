package tripalocal.com.au.tripalocalbeta.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import tripalocal.com.au.tripalocalbeta.R;
import tripalocal.com.au.tripalocalbeta.adapters.ExperienceListAdapter;


public class ExperiencesListFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    RecyclerView rv;
    public ExperiencesListFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experiences_list, container, false);
        rv = (RecyclerView) view.findViewById(R.id.recycle_view_exp_list);
        LinearLayoutManager LLM = new LinearLayoutManager(getActivity().getApplicationContext());
        rv.setLayoutManager(LLM);
        rv.setAdapter(new ExperienceListAdapter(getActivity().getApplicationContext()));
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != ExperienceListAdapter.current_city ) {
            //displayListFrag(city[position]);
            ((RecyclerView) getActivity().findViewById(R.id.recycle_view_exp_list)).getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}