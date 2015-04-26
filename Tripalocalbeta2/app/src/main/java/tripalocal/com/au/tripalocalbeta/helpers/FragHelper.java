package tripalocal.com.au.tripalocalbeta.helpers;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import tripalocal.com.au.tripalocalbeta.R;

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

}
