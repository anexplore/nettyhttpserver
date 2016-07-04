package com.fd.nettyhttpserver;

import java.net.SocketAddress;
import java.util.List;
import java.util.Map;

public interface HttpRequestWrapper {

    /**
     * 获取远程SocketAddress
     * 
     * @return SocketAddress
     */
    SocketAddress getRemoteAddress();

    /**
     * 设置远程连接地址
     * 
     * @param remoteAddress
     */
    void setRemoteAddress(SocketAddress remoteAddress);

    /**
     * 获取body数据
     * 
     * @return byte[]
     * @throws Exception
     */
    byte[] getBody() throws Exception;

    /**
     * 将body转换成String
     * 
     * @param charset
     * @return 如果body为null则返回null
     * @throws Exception
     */
    String getBodyAsString(String charset) throws Exception;

    /**
     * 获得URL中的请求参数
     * 
     * @return
     */
    List<String> getParamter(String key);

    /**
     * 获得所有请求参数
     * 
     * @return
     */
    Map<String, List<String>> getParamters();

    /**
     * 获得所有请求头
     * 
     * @return
     */
    List<HttpHeaderWrapper> getHeaders();

    /**
     * 获得key的第一个请求头
     * 
     * @param key
     * @return 如果不存在返回null
     */
    HttpHeaderWrapper getFirstHeader(String key);

    /**
     * 获得请求路径
     * 
     * @return
     */
    String getUri();

    /**
     * 返回请方法
     * 
     * @return
     */
    String getMethod();

    /**
     * 获得http版本
     * 
     * @return 默认返回HTTP/1.1
     */
    String getHttpVersion();

    /**
     * 设置请求体
     * 
     * @param body
     */
    void setBody(byte[] body);

    /**
     * 设置请求体
     * 
     * @param body
     */
    void setStringBody(String body);

    /**
     * 设置请求头
     * 
     * @param headers
     */
    void setHeaders(List<HttpHeaderWrapper> headers);

    /**
     * 添加http头
     * 
     * @param header
     */
    void addHeader(HttpHeaderWrapper header);

    /**
     * 添加请求参数
     * 
     * @param key
     * @param value
     */
    void addParamter(String key, String value);


    /**
     * 添加请求参数
     * 
     * @param key
     * @param values
     */
    void addParamter(String key, List<String> values);

    /**
     * 设置请求路径
     * 
     * @param uri
     */
    void setUri(String uri);

    /**
     * 设置请求方法
     * 
     * @param method
     */
    void setMethod(String method);

    /**
     * 设置HTTP协议版本
     * 
     * @param httpVersion
     */
    void setHttpVersion(String httpVersion);

}
