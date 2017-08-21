package knd.com.lib_core.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Copy Right 2012-2016, Afinos, Inc., or its affiliates
 * @Author: Afinos Team
 * @Created by phearom on 10/26/16 2:59 PM
 **/
public class AssetUtils {
    private static AssetUtils instance;
    private Context mContext;
    private AssetManager manager;

    private AssetUtils(Context context) {
        this.mContext = context;
        manager = context.getAssets();
    }

    public static AssetUtils init(Context context) {
        if (instance == null)
            instance = new AssetUtils(context);
        return instance;
    }

    public String[] listWithPath(String parent, String path) {
        try {
            return manager.list(parent + "/" + path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String[] withPath(String parent) {
        try {
            return manager.list(parent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Drawable getDrawable(String parent, String fileName) {
        Drawable d = null;
        try {
            InputStream is = manager.open(parent + "/" + fileName);
            d = Drawable.createFromStream(is, null);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return d;
    }

    public Drawable getDrawable(String fileName) {
        Drawable d = null;
        try {
            InputStream is = manager.open(fileName);
            d = Drawable.createFromStream(is, null);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return d;
    }

    public InputStream getInputStream(String parent, String fileName) {
        try {
            return manager.open(parent + "/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
