package knd.com.lib_api.base;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Map;

/**
 * Created by Dell on 2/7/2017.
 */

public final class MyJsonObject extends JSONObject {
    public MyJsonObject() {
    }

    public MyJsonObject(Map copyFrom) {
        super(copyFrom);
    }

    public MyJsonObject(JSONTokener readFrom) throws JSONException {
        super(readFrom);
    }

    public MyJsonObject(String json) throws JSONException {
        super(json);
    }

    public MyJsonObject(JSONObject copyFrom, String[] names) throws JSONException {
        super(copyFrom, names);
    }

    @Override
    public int getInt(String name) {
        try {
            return super.getInt(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String getString(String name) {
        try {
            return super.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean getBoolean(String name) {
        try {
            return super.getBoolean(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public double getDouble(String name) {
        try {
            return super.getDouble(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
