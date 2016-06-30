package com.example.dllo.mirror_20.networktools;


import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.example.dllo.mirror_20.R;

import java.util.Map;

/**
 * Created by dllo on 16/6/20.
 */
public class NetworkTools {
    private RequestQueue requestQueue;
    private ImageLoader loader;



    public NetworkTools() {
        requestQueue = VolleySingleton.getInstance().getRequestQueue();
        loader = VolleySingleton.getInstance().getLoader();
    }

    public void getNetworkData(String url, final NetworkListener listener) {
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

    public void getNetworkPostData(String url, final Map<String, String> map, final NetworkListener listener) {
        StringRequest requestPost = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listener.onSuccessed(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onFailed(error);
            }
        }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return map;
            }
        };
        requestQueue.add(requestPost);
    }


    public void getNetworkImage(String url, ImageView imageView) {
        loader.get(url, new ImageListenerWithAlpha(R.mipmap.grey_background, R.mipmap.grey_background, imageView, null));
    }


    public void getNetworkImage(String url, ImageView imageView, ProgressBar progressBar) {
        loader.get(url, new ImageListenerWithAlpha(R.mipmap.bg, R.mipmap.bg, imageView, progressBar));

    }


    class ImageListenerWithAlpha implements ImageLoader.ImageListener {
        int defaultIma, errorIma;
        ImageView imageView;
        ProgressBar bar;


        public ImageListenerWithAlpha(int defaultIma, int errorIma, ImageView imageView, ProgressBar progressBar) {
            this.defaultIma = defaultIma;
            this.errorIma = errorIma;
            this.imageView = imageView;
            this.bar = progressBar;
        }

        @Override
        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
            if (response.getBitmap() != null) {
                imageView.setImageBitmap(response.getBitmap());
                if (bar != null) {
                    bar.setVisibility(View.GONE);
                }

            } else if (defaultIma != 0) {
                imageView.setImageResource(defaultIma);
            }

        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }

    //李爽写的imgloader方法
    public void getImg(final ImageView imageView, String url) {

        loader.get(url, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {

                if (response.getBitmap() != null) {
                    imageView.setImageBitmap(response.getBitmap());

                }

            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


}
