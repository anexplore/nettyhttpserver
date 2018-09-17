package com.fd.asynchttpserver;

import java.net.SocketAddress;
import java.util.List;
import java.util.Map;
/**
 * Http Request 
 */
public interface HttpRequestWrapper {

  /**
   * 获取远程SocketAddress
   * 
   * @return SocketAddress
   */
  SocketAddress getRemoteAddress();

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
   * @return URL请求参数
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
   * @return 请求头列表
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
   * @return 请求URI
   */
  String getUri();

  /**
   * 返回请方法
   * 
   * @return HTTP METHOD
   */
  String getMethod();

  /**
   * 获得http版本
   * 
   * @return 默认返回HTTP/1.1
   */
  String getHttpVersion();

}
