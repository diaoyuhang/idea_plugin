package com._4scaffolding.infrastructure;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;

public class ICONS {

    private static Icon load(String path) {
        return IconLoader.getIcon(path, ICONS.class);
    }

    public static final Icon DDD = load("/icon/DDD.png");

    public static final Icon SPRING_BOOT = load("/icon/spring-boot.png");

}
