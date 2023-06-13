package com.jmd;

import java.io.File;
import java.util.ArrayList;

import com.jmd.common.Setting;

import com.jmd.util.FileUtils;
import lombok.Getter;

public class ApplicationSetting {

    private static final File path = new File(System.getProperty("user.dir") + "/setting");
    private static final File file = new File(System.getProperty("user.dir") + "/setting/ApplicationSetting");

    @Getter
    private static Setting setting;

    static {
        if (!path.exists() && !path.isFile()) {
            path.mkdir();
        }
        if (!file.exists() && !file.isFile()) {
            setting = createDefault();
        } else {
            setting = loadSettingFile();
        }
    }

    public static void save() {
        try {
            FileUtils.saveObj2File(setting, file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save(Setting s) {
        setting = s;
        try {
            FileUtils.saveObj2File(setting, file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Setting createDefault() {
        var s = new Setting("default");
        try {
            FileUtils.saveObj2File(s, file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    private static Setting loadSettingFile() {
        var s = new Setting();
        try {
            s = (Setting) FileUtils.readFile2Obj(file);
        } catch (Exception e) {
            s = createDefault();
            e.printStackTrace();
        }
        return s;
    }

}
