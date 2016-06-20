package com.example.dllo.mirror_20.networktools;

import com.android.volley.VolleyError;

/**
 * Created by dllo on 16/6/20.
 */
public interface NetworkListener {
    void onSuccessed(String result);
    void onFailed(VolleyError error);
}
