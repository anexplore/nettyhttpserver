package com.fd.asynchttpserver.nettyimpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import com.fd.asynchttpserver.HttpHeaderWrapper;
import com.fd.asynchttpserver.utils.Args;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;

/**
 * 将netty内部类型转换成本模块的封装类型
 */
public class NettyConverter {

  /**
   * <li>将Netty的HttpRequest转换成HttpRequestWrapper</li>
   * <li>这里没有直接设置body而是设置ByteBuf从而避免一次内存copy</li>
   * 
   * @param request NettyRequest
   * @return NettyHttpRequest
   */
  public static NettyHttpRequest convertRequest(HttpRequest request) {
    Args.notNull(request, "request");
    NettyHttpRequest wrapper = new NettyHttpRequest();
    wrapper.setMethod(request.method().name().toUpperCase(Locale.ENGLISH));
    wrapper.setUri(request.uri());
    if (request instanceof FullHttpRequest) {
      wrapper.setChannelBuffer(FullHttpRequest.class.cast(request).content());
    }
    for (Map.Entry<String, String> h : request.headers()) {
      wrapper.addHeader(new HttpHeaderWrapper(h.getKey(), h.getValue()));
    }
    QueryStringDecoder queryDecoder = new QueryStringDecoder(request.uri());
    for (Map.Entry<String, List<String>> query : queryDecoder.parameters().entrySet()) {
      wrapper.addParamter(query.getKey(), query.getValue());
    }
    return wrapper;
  }

  /**
   * 将HttpResponseWrapper转换成Netty的Http Response
   * 
   * @param wrapper NettyHttpResponse
   * @return 采用UTF-8编码 如果转码失败则body为空
   */
  public static HttpResponse convertResponse(NettyHttpResponse wrapper) {
    Args.notNull(wrapper, "response wrapper");
    DefaultFullHttpResponse response;
    try {
      response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
          HttpResponseStatus.valueOf(wrapper.getStatusCode()),
          Unpooled.wrappedBuffer(wrapper.getBody()));
    } catch (Exception e) {
      response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
          HttpResponseStatus.valueOf(wrapper.getStatusCode()));
    }
    if (wrapper.getHeaders() != null) {
      for (HttpHeaderWrapper header : wrapper.getHeaders()) {
        response.headers().add(header.getKey(), header.getValue());
      }
    }
    return response;
  }
}
