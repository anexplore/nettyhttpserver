package com.fd.asynchttpserver;

import java.util.List;
/**
 * Http Response
 */
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
   * @return Http Response Headers
   */
  List<HttpHeaderWrapper> getHeaders();

  /**
   * 获得第一个key的http头
   * 
   * @param key
   * @return key的第一个value
   */
  HttpHeaderWrapper getFirstHeader(String key);

  /**
   * 获得返回的body体
   * 
   * @return http 响应体
   */
  byte[] getBody() throws Exception;

  /**
   * 返回body体
   * 
   * @param charset
   * @return 按照charset编码的string
   */
  String getBodyAsString() throws Exception;

  /**
   * 响应状态
   * 
   * @return status code
   */
  int getStatusCode();

}
