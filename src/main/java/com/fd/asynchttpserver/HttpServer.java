package com.fd.asynchttpserver;

import java.util.Map;

/**
 * 异步HttpServer 不支持100-continue 不支持trunk body请求
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
  void shutdown() throws Exception;

  /**
   * 注册处理句柄
   * 
   * @param path
   * @param handler
   */
  void registerHandler(String path, HttpRequestHandler handler);

  /**
   * @return 监听端口
   */
  int getBindPort();

  /**
   * @return 绑定地址
   */
  String getBindAddress();

  /**
   * @return 是否采用https协议
   */
  boolean isHttps();

  /**
   * @return 是否支持IO压缩
   */
  boolean isIoCompressionEnabled();

  /**
   * @return 返回所有注册的handler
   */
  Map<String, HttpRequestHandler> getHandlers();
}
