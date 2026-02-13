package com.vivern.arpg.arpgfix;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ItemDataSync_Client {
    Map<String, Object> getMap();

    default <T> T getData(String dataName, Class<T> type) {
        return this.getData(dataName, type, null);
    }

    <T> T getData(String dataName, Class<T> type, T defaultValue);

    <T> void setData(String dataName, Class<T> type, @NotNull Object value);
}