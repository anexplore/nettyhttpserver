package com.fd.asynchttpserver;

/**
 * HttpServer配置
 * 
 */
public class HttpServerConfig {
    // 是否允许压缩
    private boolean enableCompression = true;
    // 是否采用https
    private boolean https = false;
    // 监听端口
    private int listenPort = 80;
    // reactor线程池基础大小
    private int bossPoolCoreSize = 1;
    // reactor线程池大小
    private int bossPoolSize = 1;
    // IO worker线程池大小
    private int workPoolSize = Runtime.getRuntime().availableProcessors() * 2;
    // io worker线程池基础大小
    private int workPoolCoreSize = workPoolSize;
    // 空闲连接超时时间 30s
    private int readTimeout = 30000;

    // 绑定本地地址
    private String localAddress = "localhost";

    public boolean isEnableCompression() {
        return enableCompression;
    }

    public HttpServerConfig setEnableCompression(boolean enableCompression) {
        this.enableCompression = enableCompression;
        return this;
    }

    public boolean isHttps() {
        return https;
    }

    public HttpServerConfig setHttps(boolean https) {
        this.https = https;
        return this;
    }

    public int getListenPort() {
        return listenPort;
    }

    public HttpServerConfig setListenPort(int listenPort) {
        this.listenPort = listenPort;
        return this;
    }

    public int getBossPoolSize() {
        return bossPoolSize;
    }

    public HttpServerConfig setBossPoolSize(int bossPoolSize) {
        this.bossPoolSize = bossPoolSize;
        return this;
    }

    public int getWorkPoolSize() {
        return workPoolSize;
    }

    public HttpServerConfig setWorkPoolSize(int workPoolSize) {
        this.workPoolSize = workPoolSize;
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public HttpServerConfig setReadTimeout(int idleChannelTimeout) {
        this.readTimeout = idleChannelTimeout;
        return this;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public HttpServerConfig setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
        return this;
    }

    public int getBossPoolCoreSize() {
        return bossPoolCoreSize;
    }

    public HttpServerConfig setBossPoolCoreSize(int bossPoolCoreSize) {
        this.bossPoolCoreSize = bossPoolCoreSize;
        return this;
    }

    public int getWorkPoolCoreSize() {
        return workPoolCoreSize;
    }

    public HttpServerConfig setWorkPoolCoreSize(int workPoolCoreSize) {
        this.workPoolCoreSize = workPoolCoreSize;
        return this;
    }

}
