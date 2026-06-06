package com.springinpratice.ch08.service.impl;

public final class PlainTextFilter implements TextFilter {
   
    public String filter(String text) {
        if (text == null) {
            return null;
        }
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\n", "<br />");
    }
}