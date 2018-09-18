package com.fd.asynchttpserver.nettyimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.fd.asynchttpserver.HttpHeaderWrapper;
import com.fd.asynchttpserver.HttpResponseWrapper;
import com.fd.asynchttpserver.http.HttpStatus;

/**
 * @author anexplore 
 */
public class NettyHttpResponse implements HttpResponseWrapper {

  private byte[] body;
  private String sBody;
  private String httpVersion = "HTTP/1.1";
  private int statusCode = HttpStatus.SC_OK;
  private List<HttpHeaderWrapper> headers = new ArrayList<>(8);

  @Override
  public byte[] getBody() throws Exception {
    if (body == null && sBody != null) {
      return sBody.getBytes("UTF-8");
    }
    return body;
  }

  @Override
  public String getBodyAsString() throws Exception {
    if (sBody != null) {
      return sBody;
    }
    return body == null ? "" : new String(body, "UTF-8");
  }

  @Override
  public List<HttpHeaderWrapper> getHeaders() {
    return headers;
  }

  @Override
  public HttpHeaderWrapper getFirstHeader(String key) {
    if (key == null || headers == null) {
      return null;
    }
    for (HttpHeaderWrapper header : headers) {
      if (header.getKey().equalsIgnoreCase(key)) {
        return header;
      }
    }
    return null;
  }


  @Override
  public String getHttpVersion() {
    return httpVersion;
  }
  
  /**
   * 设置body体
   * @param body body体数据
   */
  public void setBody(byte[] body) {
    this.body = body;
  }

  /**
   * 设置body体
   * @param body 字符串格式的body
   */
  public void setStringBody(String body) {
    this.sBody = body;
  }

  /**
   * 设置HTTP头 
   * <p>所有已经设置的HTTP头都将情空 用headers替换</p>
   * @param headers HTTP头
   */
  public void setHeaders(List<HttpHeaderWrapper> headers) {
    this.headers = headers;
  }

  /**
   * 添加HTTP头
   * @param header HTTP头
   */
  public void addHeader(HttpHeaderWrapper header) {
    if (header == null) {
      return;
    }
    if (headers == null) {
      headers = new ArrayList<>(8);
    }
    headers.add(header);
  }
  
  /**
   * 添加HTTP头
   * @param key 名称  不可为空
   * @param value 值 可为空
   */
  public void addHeader(String key, String value) {
    if (key == null) {
      return;
    }
    if (headers == null) {
      headers = new ArrayList<>(8);
    }
    headers.add(new HttpHeaderWrapper(key, value));
  }

  /**
   * 设置HTTP版本 不设置默认HTTP/1.1
   * @param httpVersion
   */
  public void setHttpVersion(String httpVersion) {
    this.httpVersion = httpVersion;
  }

  @Override
  public int getStatusCode() {
    return statusCode;
  }

  /**
   * 设置状态码 不设置默认200
   * @param code 状态码
   */
  public void setStatusCode(int code) {
    this.statusCode = code;
  }

  @Override
  public String toString() {
    return "NettyHttpResponse [body=" + Arrays.toString(body) + ", sBody=" + sBody
        + ", httpVersion=" + httpVersion + ", statusCode=" + statusCode + ", headers=" + headers
        + "]";
  }
}
