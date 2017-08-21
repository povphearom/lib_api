package knd.com.lib_core.listener;

import org.json.JSONObject;

import java.util.List;

public interface ClickHandlerSchedule<T>
{
    void onClick(List<JSONObject> data, int i, boolean isCheck, int index);
}