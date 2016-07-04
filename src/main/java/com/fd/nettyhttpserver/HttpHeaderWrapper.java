package com.fd.nettyhttpserver;

import com.fd.nettyhttpserver.utils.Args;

/**
 * Http Header
 * 
 */
public class HttpHeaderWrapper {
    private String key;
    private String value;

    public HttpHeaderWrapper(String key, String value) {
        Args.notEmpty(key, "key");
        Args.notNull(value, "value");
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public void setKey(String key) {
        Args.notEmpty(key, "key");
        Args.notNull(value, "value");
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HttpHeaderWrapper [key=" + key + ", value=" + value + "]";
    }
}
