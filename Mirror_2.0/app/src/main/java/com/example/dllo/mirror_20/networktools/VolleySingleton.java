package com.example.dllo.mirror_20.networktools;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.dllo.mirror_20.base.App;

/**
 * Created by dllo on 16/6/20.
 */
public class VolleySingleton {
    private RequestQueue requestQueue;
    private static VolleySingleton volleySingleton;

    private  VolleySingleton() {
        requestQueue = Volley.newRequestQueue(App.context);
    }

    public RequestQueue getRequestQueue(){
        return requestQueue;
    }

    public static VolleySingleton getInstance(){
        if (volleySingleton == null) {
            synchronized (VolleySingleton.class) {
                if (volleySingleton == null) {
                    volleySingleton = new VolleySingleton();
                }
            }
        }
        return volleySingleton;
    }
}
