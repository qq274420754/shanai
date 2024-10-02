package com.shanai.shanai.common.view.tablayout;

/**
 * Created by Administrator on 2019/2/13.
 */

public class TabEntity {

    public String key;
    public String title;
    public String selectedTextColor;
    public String unSelectedTextColor;
    private String selectedUrl;
    private String unSelectedUrl;
    private int selectedIcon;
    private int unSelectedIcon;

    public TabEntity(String key, String title, String selectedUrl, String unSelectedUrl, Integer selectedIcon, Integer unSelectedIcon, String selectedTextColor, String unSelectedTextColor) {
        this.key = key;
        this.title = title;
        this.selectedUrl = selectedUrl;
        this.unSelectedUrl = unSelectedUrl;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
        this.selectedTextColor = selectedTextColor;
        this.unSelectedTextColor = unSelectedTextColor;
    }

    public TabEntity(String key, String title, String selectedUrl, String unSelectedUrl, Integer selectedIcon, Integer unSelectedIcon) {
        this.key = key;
        this.title = title;
        this.selectedUrl = selectedUrl;
        this.unSelectedUrl = unSelectedUrl;
        this.selectedIcon = selectedIcon;
        this.unSelectedIcon = unSelectedIcon;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSelectedUrl() {
        return selectedUrl;
    }

    public void setSelectedUrl(String selectedUrl) {
        this.selectedUrl = selectedUrl;
    }

    public String getUnSelectedUrl() {
        return unSelectedUrl;
    }

    public void setUnSelectedUrl(String unSelectedUrl) {
        this.unSelectedUrl = unSelectedUrl;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSelectedTextColor() {
        return selectedTextColor;
    }

    public void setSelectedTextColor(String selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public String getUnSelectedTextColor() {
        return unSelectedTextColor;
    }

    public void setUnSelectedTextColor(String unSelectedTextColor) {
        this.unSelectedTextColor = unSelectedTextColor;
    }

    public int getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(int selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public int getUnSelectedIcon() {
        return unSelectedIcon;
    }

    public void setUnSelectedIcon(int unSelectedIcon) {
        this.unSelectedIcon = unSelectedIcon;
    }
}
