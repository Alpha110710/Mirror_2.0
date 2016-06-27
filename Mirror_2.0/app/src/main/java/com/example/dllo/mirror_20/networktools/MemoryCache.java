package com.example.dllo.mirror_20.networktools;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by dllo on 16/6/20.
 */
public class MemoryCache implements ImageLoader.ImageCache {

    private LruCache<String,Bitmap> lruCache;

    public MemoryCache() {
        int maxSize = (int) (Runtime.getRuntime().maxMemory()/8/1024);

        lruCache = new LruCache<String,Bitmap>(maxSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight()/1024;
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return lruCache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
       lruCache.put(url,bitmap);
    }
}
