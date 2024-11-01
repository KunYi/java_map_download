package com.jmd.util;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Properties;
import java.util.Collections;
import java.util.Enumeration;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.PropertyResourceBundle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class I18nUtil {
    private static ResourceBundle bundle;
    private static Locale currentLocale;

    static {
        try {
            log.info("Current Locale: " + Locale.getDefault());
            setLocale(Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
            fallbackToDefault();
        }
    }

    public static void setLocale(Locale locale) {
        try {
            currentLocale = locale;
            log.info("Loading resource bundle for locale: " + locale);
            bundle = ResourceBundle.getBundle("messages", locale);
        } catch (Exception e) {
            e.printStackTrace();
            fallbackToDefault();
        }
    }

    private static void fallbackToDefault() {
        try {
            log.error("Falling back to default values");
            currentLocale = Locale.ENGLISH;
            final Properties props = new Properties();  // 改為 final
            props.setProperty("tab.map", "Map Control");
            props.setProperty("tab.download", "Download Tasks");
            props.setProperty("tab.preview", "Tile Preview");
            props.setProperty("tab.merge", "Tile Merge");
            props.setProperty("tab.log", "System Log");

            try (ByteArrayInputStream bais = new ByteArrayInputStream(
                    props.toString().getBytes())) {
                bundle = new PropertyResourceBundle(bais);
            } catch (IOException e) {
                e.printStackTrace();
                bundle = new ResourceBundle() {
                    @Override
                    protected Object handleGetObject(String key) {
                        return props.getProperty(key, "!" + key + "!");
                    }

                    @Override
                    public Enumeration<String> getKeys() {
                        return Collections.enumeration(props.stringPropertyNames());
                    }
                };
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getString(String key) {
        try {
            return bundle.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "!" + key + "!";
        }
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }
}
