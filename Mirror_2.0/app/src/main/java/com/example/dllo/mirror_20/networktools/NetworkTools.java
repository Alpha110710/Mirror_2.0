package com.example.dllo.mirror_20.networktools;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by dllo on 16/6/20.
 */
public class NetworkTools {
    private RequestQueue requestQueue;

    public NetworkTools() {
        requestQueue = VolleySingleton.getInstance().getRequestQueue();
    }
    public void getNetworkData(String url, final NetworkListener listener){
        StringRequest request = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccessed(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailed(error);

            }
        });
        requestQueue.add(request);
    }
}
