package knd.com.lib_api.utils;

/*
*@Copy Right 2012-2016, Afinos, Inc., or its affiliates
*@Author: Afinos Team
*/

import com.afinos.api.application.MyApp;

public class Strings {
    public static String getString(int id){
        return MyApp.init().getResources().getString(id);
    }
}
