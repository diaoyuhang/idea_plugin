package com._4scaffolding.infrastructure;

import com._4scaffolding.factory.TemplateFactory;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "DataSetting2",storages = @Storage("plugin.xml"))
public class DataSetting2 implements PersistentStateComponent<DataState> {
    private DataState dataState =new DataState();

    public static DataSetting2 getInstance(){
        return TemplateFactory.project.getService(DataSetting2.class);
    }

    @Override
    public @Nullable DataState getState() {
        return dataState;
    }

    @Override
    public void loadState(@NotNull DataState state) {
        this.dataState = state;
    }

    @Override
    public void noStateLoaded() {
        PersistentStateComponent.super.noStateLoaded();
    }

    @Override
    public void initializeComponent() {
        PersistentStateComponent.super.initializeComponent();
    }
}
