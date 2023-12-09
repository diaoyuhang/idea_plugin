package com._3stock.infrastructure;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "DataSetting",storages = @Storage("plugin.xml"))
public class DataSetting implements PersistentStateComponent<DataState> {

    private DataState dataState = new DataState();

    private DataSetting() {
    }

    public static DataSetting getInstance(){
        return  ServiceManager.getService(DataSetting.class);
    }

    @Override
    public @Nullable DataState getState() {
        return dataState;
    }

    @Override
    public void loadState(@NotNull DataState state) {
        this.dataState = state;
    }
}
