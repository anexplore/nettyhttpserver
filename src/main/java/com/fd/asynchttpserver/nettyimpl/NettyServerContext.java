package com.fd.asynchttpserver.nettyimpl;

import com.fd.asynchttpserver.HttpServerConfig;
import com.fd.asynchttpserver.UriHttpRequestHandlerMapper;
import io.netty.handler.ssl.SslContext;

/**
 * 存放全局变量等 线程安全
 * 
 */
public class NettyServerContext {
  private HttpServerConfig config;
  private UriHttpRequestHandlerMapper handlerMapper;
  private SslContext sslContext;
  
  /**
   * @return 配置
   */
  public synchronized HttpServerConfig getHttpServerConfig() {
    return config;
  }

  public synchronized void setHttpServerConfig(HttpServerConfig config) {
    this.config = config;
  }

  /**
   * @return handler映射
   */
  public synchronized UriHttpRequestHandlerMapper getHandlerMapper() {
    return handlerMapper;
  }

  public synchronized void setHandlerMapper(UriHttpRequestHandlerMapper mapper) {
    this.handlerMapper = mapper;
  }
  
  /**
   * @return ssl context
   */
  public synchronized SslContext getSslContext() {
    return sslContext;
  }

  public synchronized void setSslContext(SslContext sslContext) {
    this.sslContext = sslContext;
  }
}
