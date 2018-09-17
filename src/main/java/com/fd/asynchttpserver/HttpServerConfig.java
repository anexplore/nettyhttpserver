package com.fd.asynchttpserver;

import java.io.File;

/**
 * Http Server Configs
 */
public interface HttpServerConfig {
  
  /**
   * @return 是否Http Response压缩body
   */
  boolean isCompressionEnabled();
  
  /**
   * @return 是否开启Https协议
   */
  boolean isHttps();
  
  /**
   * @return 是否支持Http Keep-Alive
   */
  boolean isSupportHttpAlive();
  
  /**
   * @return 绑定的本地端口
   */
  int getBindPort();
  
  /**
   * @return 绑定的本地网卡地址
   */
  String getBindAddress();
  
  /**
   * @return 接收连接线程池大小
   */
  int getBossPoolSize();
  
  /**
   * @return IO工作线程池大小
   */
  int getWorkPoolSize();
  
  /**
   * @return 读超时时间 milliseconds
   */
  int getReadTimeout();
  
  /**
   * @return PEM格式证书的crt文件
   */
  File getCertificate();
  
  /**
   * @return PEM格式证书的key文件
   */
  File getPrivateKey();

  /* channel options */
  boolean isReuseAddress();
  
  int getSoLinger();
  
  boolean isTcpNoDelay();
  
  int getRecvBufferSize();
  
  int getSendBufferSize();
  
  int getBacklog();

  /* http options */
  /**
   * @return 是否验证HTTP头是否符合标准
   */
  boolean isValidateHeaders();
  
  /**
   * @return Http头请求或者响应第一行最大长度
   */
  int getMaxInitialLineLength();
  
  /**
   * @return HTTP协议HTTP头部分最大长度
   */
  int getMaxHeaderSize();
  
  /**
   * @return 每个chunk最大大小，大chunk将被切分
   */
  int getMaxChunkSize();
  
  /**
   * @return 最大请求体大小
   */
  int getMaxBodyLength();
}
