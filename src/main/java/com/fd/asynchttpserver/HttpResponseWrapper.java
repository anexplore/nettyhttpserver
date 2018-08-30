package com.fd.asynchttpserver;

import java.util.List;

public interface HttpResponseWrapper {

    /**
     * 获得http协议版本
     * 
     * @return
     */
    String getHttpVersion();

    /**
     * 获得返回头
     * 
     * @return
     */
    List<HttpHeaderWrapper> getHeaders();

    /**
     * 获得第一个key的http头
     * 
     * @param key
     * @return
     */
    HttpHeaderWrapper getFirstHeader(String key);

    /**
     * 获得返回的body体
     * 
     * @return
     */
    byte[] getBody() throws Exception;

    /**
     * 返回body体
     * 
     * @param charset
     * @return
     */
    String getBodyAsString() throws Exception;

    /**
     * 响应状态
     * 
     * @return
     */
    int getStatusCode();

    /**
     * 设置协议版本
     * 
     * @param httpVersion
     */
    void setHttpVersion(String httpVersion);

    /**
     * 设置响应头
     * 
     * @param headers
     */
    void setHeaders(List<HttpHeaderWrapper> headers);

    /**
     * 添加响应头
     * 
     * @param header
     */
    void addHeader(HttpHeaderWrapper header);

    /**
     * 设置响应体 UTF-8
     * 
     * @param body
     */
    void setBody(byte[] body);

    /**
     * 设置响应体 UTF-8
     * 
     * @param body
     */
    void setStringBody(String body);

    /**
     * 设置响应状态码
     */
    void setStatusCode(int code);
}
