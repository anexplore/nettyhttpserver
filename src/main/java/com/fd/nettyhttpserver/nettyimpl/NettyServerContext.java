package com.fd.nettyhttpserver.nettyimpl;

import org.jboss.netty.util.Timer;

import com.fd.nettyhttpserver.UriHttpRequestHandlerMapper;

/**
 * 存放全局变量等 线程安全
 * 
 */
public class NettyServerContext {
    private NettyHttpServerConfig config;
    private UriHttpRequestHandlerMapper handlerMapper;
    private Timer timer;

    public synchronized Timer getTimer() {
        return timer;
    }

    public synchronized void setTimer(Timer timer) {
        this.timer = timer;
    }

    public synchronized NettyHttpServerConfig getHttpServerConfig() {
        return config;
    }

    public synchronized void setHttpServerConfig(NettyHttpServerConfig config) {
        this.config = config;
    }

    public synchronized UriHttpRequestHandlerMapper getHandlerMapper() {
        return handlerMapper;
    }

    public synchronized void setHandlerMapper(UriHttpRequestHandlerMapper mapper) {
        this.handlerMapper = mapper;
    }
}
