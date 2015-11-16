package com.tripalocal.bentuke.helpers;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.tripalocal.bentuke.R;
import com.tripalocal.bentuke.Views.ExperiencesListFragment;

/**
 * Created by naveen on 4/25/2015.
 */
public class FragHelper {

    private static final int frag_continer = R.id.fragment_container;

    public static void addReplace(FragmentManager managea, Fragment replFrag){
       FragmentTransaction transaction = managea.beginTransaction();
       transaction.replace(frag_continer, replFrag);
       transaction.addToBackStack(null);
       transaction.commit();
    }

    public static void replace(FragmentManager managea, Fragment replFrag){
        FragmentTransaction transaction = managea.beginTransaction();
        transaction.replace(frag_continer, replFrag);
        transaction.commit();
    }

    public static void replace(FragmentManager managea, ExperiencesListFragment experiencesListFragment, int container) {
        FragmentTransaction transaction = managea.beginTransaction();
        transaction.replace(container, experiencesListFragment);
        transaction.commit();
    }
}
