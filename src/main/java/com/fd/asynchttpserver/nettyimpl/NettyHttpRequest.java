package com.fd.asynchttpserver.nettyimpl;

import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.fd.asynchttpserver.HttpHeaderWrapper;
import com.fd.asynchttpserver.HttpRequestWrapper;
import com.fd.asynchttpserver.http.HttpMethod;
import com.fd.asynchttpserver.utils.Args;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;

/**
 * @author anexplore 
 */
public class NettyHttpRequest implements HttpRequestWrapper {

  private byte[] body;
  private String sBody;
  private SocketAddress remoteAddress;
  private List<HttpHeaderWrapper> headers;
  private String uri;
  private String method = HttpMethod.GET;
  private String httpVersion = "HTTP/1.1";
  private Map<String, List<String>> paramters;
  private ByteBuf cBuf;


  @Override
  public SocketAddress getRemoteAddress() {
    return remoteAddress;
  }

  public void setRemoteAddress(SocketAddress remoteAddress) {
    this.remoteAddress = remoteAddress;
  }

  @Override
  public byte[] getBody() throws Exception {
    if (body == null && sBody != null) {
      return sBody.getBytes("UTF-8");
    }
    if (body == null && sBody == null && cBuf != null) {
      body = ByteBufUtil.getBytes(cBuf);
    }
    return body;
  }

  @Override
  public String getBodyAsString(String charset) throws Exception {
    if (sBody != null) {
      return sBody;
    }
    if (body != null) {
      return new String(body, charset);
    }
    if (cBuf != null) {
      return cBuf.toString(Charset.forName(charset));
    }
    return "";
  }

  @Override
  public List<String> getParamter(String key) {
    if (paramters == null) {
      return null;
    }
    return paramters.get(key);
  }

  @Override
  public Map<String, List<String>> getParamters() {
    return paramters;
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
  public String getUri() {
    return uri;
  }

  @Override
  public String getMethod() {
    return method;
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

  public void addParamter(String key, String value) {
    if (paramters == null) {
      paramters = new HashMap<>(4);
    }
    List<String> params = paramters.get(key);
    if (params == null) {
      params = new ArrayList<>(1);
      paramters.put(key, params);
    }
    params.add(value);
  }

  public void addParamter(String key, List<String> values) {
    if (paramters == null) {
      paramters = new HashMap<>(4);
    }
    List<String> params = paramters.get(key);
    if (params == null) {
      params = new ArrayList<>(1);
      paramters.put(key, params);
    }
    params.addAll(values);
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

  public void setUri(String uri) {
    this.uri = uri;
  }

  public void setMethod(String method) {
    Args.notNull(method, "http method");
    this.method = method.toUpperCase(Locale.ENGLISH);
  }

  public void setHttpVersion(String httpVersion) {
    Args.notNull(httpVersion, "http version");
    this.httpVersion = httpVersion.toUpperCase(Locale.ENGLISH);
  }

  public void setChannelBuffer(ByteBuf cBuf) {
    this.cBuf = cBuf;
  }

  @Override
  public String toString() {
    return "NettyHttpRequest [body=" + Arrays.toString(body) + ", sBody=" + sBody + ", headers="
        + headers + ", uri=" + uri + ", method=" + method + ", httpVersion=" + httpVersion
        + ", paramters=" + paramters + ", cBuf=" + cBuf + "]";
  }
}
