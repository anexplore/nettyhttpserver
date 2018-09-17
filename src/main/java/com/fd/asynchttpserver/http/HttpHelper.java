package com.fd.asynchttpserver.http;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpMessage;

public class HttpHelper {
  
  /**
   * message对应的链接是否启用Keep-Alive
   * @param message Http Message, request
   * @param openVersionFeature 是否启用HTTP/1.1默认支持Keep-Alive特性
   * @return 是否启用Keep-Alive
   */
  public static boolean isKeepAlive(HttpMessage message, boolean openVersionFeature) {
    CharSequence connection = message.headers().get(HttpHeaderNames.CONNECTION);
    if (connection != null && HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(connection)) {
        return false;
    }

    if (openVersionFeature && message.protocolVersion().isKeepAliveDefault()) {
        return !HttpHeaderValues.CLOSE.contentEqualsIgnoreCase(connection);
    } else {
        return HttpHeaderValues.KEEP_ALIVE.contentEqualsIgnoreCase(connection);
    }
  }

}
