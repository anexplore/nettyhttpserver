package com.fd.nettyhttpserver;

import java.util.Map;

/**
 * 异步HttpServer 不支持100-continue 不支持Trunk请求
 * 
 */
public interface HttpServer {

    /**
     * 启动
     * 
     * @return true 如果成功
     * @throws Exception
     */
    boolean startup() throws Exception;

    /**
     * 关闭
     */
    void shutdown();

    /**
     * 注册处理句柄
     * 
     * @param path
     * @param handler
     */
    void registerHandler(String path, HttpRequestHandler handler);

    /**
     * 设置是否采用https
     * 
     * @param https
     */
    void setHttps(boolean https);

    /**
     * 是否允许io压缩
     * 
     * @param compression
     */
    void enableIoCompression(boolean compression);

    /**
     * 设置绑定的本地地址
     * 
     * @param address
     */
    void setBindAddress(String address);

    /**
     * 设置监听端口
     * 
     * @param port
     */
    void setListenPort(int port);

    /**
     * @return 监听端口
     */
    int getListenPort();

    /**
     * @return 绑定地址
     */
    String getLocalAddress();

    /**
     * @return 是否采用https协议
     */
    boolean isHttps();

    /**
     * @return 是否支持IO压缩
     */
    boolean isIoCompressionEabled();

    /**
     * @return 返回所有注册的handler
     */
    Map<String, HttpRequestHandler> getHandlers();
}
