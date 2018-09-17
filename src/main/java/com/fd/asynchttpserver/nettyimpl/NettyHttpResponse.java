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

  public void setBody(byte[] body) {
    this.body = body;
  }

  public void setStringBody(String body) {
    this.sBody = body;
  }

  public void setHeaders(List<HttpHeaderWrapper> headers) {
    this.headers = headers;
  }

  public void addHeader(HttpHeaderWrapper header) {
    if (header == null) {
      return;
    }
    if (headers == null) {
      headers = new ArrayList<>(8);
    }
    headers.add(header);
  }

  public void setHttpVersion(String httpVersion) {
    this.httpVersion = httpVersion;
  }

  @Override
  public int getStatusCode() {
    return statusCode;
  }

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
