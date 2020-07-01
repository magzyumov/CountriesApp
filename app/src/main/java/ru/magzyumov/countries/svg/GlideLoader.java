package ru.magzyumov.countries.svg;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.util.Log;

import android.widget.ImageView;


import com.bumptech.glide.Glide;

import com.bumptech.glide.RequestBuilder;


import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.util.Preconditions;


import java.io.File;

import ru.magzyumov.countries.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideLoader {
    private static final String TAG = "SVG";
    private RequestBuilder<PictureDrawable> requestBuilder;
    private Context context;

    public GlideLoader(Context context){
        this.context = context;
    }

    public void init (){
        requestBuilder =
                GlideApp.with(context)
                        .as(PictureDrawable.class)
                        //.placeholder(R.drawable.image_loading)
                        .error(R.drawable.image_error)
                        .transition(withCrossFade())
                        .listener(new SvgSoftwareLayerSetter());
    }

    public void clearCache(ImageView imageView) {
        Log.e(TAG, "clearing cache");
        GlideRequests glideRequests = GlideApp.with(context);
        glideRequests.clear(imageView);
        GlideApp.get(context).clearMemory();
        File cacheDir = Preconditions.checkNotNull(Glide.getPhotoCacheDir(context));
        if (cacheDir.isDirectory()) {
            for (File child : cacheDir.listFiles()) {
                if (!child.delete()) {
                    Log.w(TAG, "cannot delete: " + child);
                }
            }
        }
        reload(imageView);
    }

    private void reload(ImageView imageView) {
        loadRes(imageView);
        loadNet(imageView);
    }

    private void loadRes(ImageView imageView) {
        Uri uri =
                Uri.parse(
                        ContentResolver.SCHEME_ANDROID_RESOURCE
                                + "://"
                                + context.getPackageName()
                                + "/"
                                + R.raw.bgr);
        requestBuilder.load(uri).into(imageView);
    }

    public void loadNet(String url, ImageView imageView) {
        Uri uri = Uri.parse(url);
        requestBuilder
                .load(uri)
                .into(imageView);
    }

    private void loadNet(ImageView imageView) {
        Uri uri = Uri.parse("https://restcountries.eu/data/bgr.svg");
        requestBuilder
                .override(100, 71)
                .fitCenter()
                .load(uri)
                .into(imageView);
    }








}
