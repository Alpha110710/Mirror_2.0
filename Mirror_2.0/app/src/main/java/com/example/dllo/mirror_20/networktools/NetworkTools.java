package com.example.dllo.mirror_20.networktools;

import android.animation.ObjectAnimator;
import android.widget.ImageView;

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

    public void getNetworkPostData(String url, final Map<String, String> map, final String body, final NetworkListener listener) {
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
            public byte[] getBody() throws AuthFailureError {
                return body.getBytes();
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return map;
            }
        };
        requestQueue.add(requestPost);
    }


    public void getNetworkImage(String url, ImageView imageView) {
        loader.get(url, new ImageListenerWithAlpha(R.mipmap.ic_launcher, R.mipmap.ic_launcher, imageView));
    }


    class ImageListenerWithAlpha implements ImageLoader.ImageListener {
        int defaultIma, errorIma;
        ImageView imageView;


        public ImageListenerWithAlpha(int defaultIma, int errorIma, ImageView imageView) {
            this.defaultIma = defaultIma;
            this.errorIma = errorIma;
            this.imageView = imageView;
        }

        @Override
        public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
            if (response.getBitmap() != null) {
                imageView.setImageBitmap(response.getBitmap());
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imageView, "alpha", 0, 1);
                objectAnimator.setDuration(1000);
                objectAnimator.start();
            } else if (defaultIma != 0) {
                imageView.setImageResource(defaultIma);
            }
        }

        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
}
