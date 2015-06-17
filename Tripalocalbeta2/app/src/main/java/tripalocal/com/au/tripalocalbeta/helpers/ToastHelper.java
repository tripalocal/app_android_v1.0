package tripalocal.com.au.tripalocalbeta.helpers;

import com.github.johnpersano.supertoasts.SuperToast;
import com.github.johnpersano.supertoasts.util.Style;

import tripalocal.com.au.tripalocalbeta.Views.HomeActivity;

/**
 * Created by naveen on 4/25/2015.
 */
public class ToastHelper {

    public static void longToast(String message){
        SuperToast.create(HomeActivity.getHome_context(), message , SuperToast.Duration.LONG,
                Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();
    }

    public static void shortToast(String message){
        SuperToast.create(HomeActivity.getHome_context(), message , SuperToast.Duration.SHORT,
                Style.getStyle(Style.GREEN, SuperToast.Animations.FLYIN)).show();
    }

    public static void warnToast(String message){
        SuperToast.create(HomeActivity.getHome_context(), message , SuperToast.Duration.SHORT,
                Style.getStyle(Style.ORANGE, SuperToast.Animations.FLYIN)).show();
    }

    public static void errorToast(String message){
        SuperToast.create(HomeActivity.getHome_context(), message , SuperToast.Duration.LONG,
                Style.getStyle(Style.RED, SuperToast.Animations.FLYIN)).show();
    }

}


