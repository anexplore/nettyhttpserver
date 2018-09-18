package com.fd.asynchttpserver.nettyimpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fd.asynchttpserver.HttpRequestHandler;
import com.fd.asynchttpserver.HttpServerConfig;
import com.fd.asynchttpserver.UriHttpRequestHandlerMapper;
import com.fd.asynchttpserver.http.HttpHeaders;
import com.fd.asynchttpserver.http.HttpHelper;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;

public class InnerServerHandler extends SimpleChannelInboundHandler<HttpObject> {
  private static final Logger LOG = LoggerFactory.getLogger(InnerServerHandler.class);
  private final UriHttpRequestHandlerMapper handlerMapper;
  private final HttpServerConfig config;

  public InnerServerHandler(UriHttpRequestHandlerMapper mapper, HttpServerConfig config) {
    this.handlerMapper = mapper;
    this.config = config;
  }

  @Override
  public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
    // assert msg must be http request
    HttpRequest request = (HttpRequest)msg;
    if (HttpUtil.is100ContinueExpected(request)) {
      send100Continue(request, ctx);
      return;
    }
    NettyHttpRequest requestWrapper = NettyConverter.convertRequest(request);
    requestWrapper.setRemoteAddress(ctx.channel().remoteAddress());
    HttpRequestHandler handler = handlerMapper.lookup(requestWrapper);
    if (handler == null) {
      sendAccessDenied(request, ctx);
      return;
    }
    NettyHttpResponse responseWrapper = new NettyHttpResponse();
    try {
      handler.handle(requestWrapper, responseWrapper);
    } catch (Exception excep) {
      LOG.error("handle error:" + request.uri(), excep);
    }
    HttpResponse response = NettyConverter.convertResponse(responseWrapper);
    writeResponse(request, response, ctx);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    LOG.error("request error:" + cause + ", for " + ctx.channel().remoteAddress());
    ctx.close();
  }

  /**
   * 拒绝访问
   * 
   */
  private void sendAccessDenied(HttpRequest request, ChannelHandlerContext ctx) {
    LOG.warn("deny access " + request.uri() + " for connection:" + ctx.channel().remoteAddress());
    HttpResponse response =
        new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
    // 关闭链接
    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
  }

  /**
   * 不支持100 continue
   * 
   */
  private void send100Continue(HttpRequest request, ChannelHandlerContext ctx) {
    LOG.warn(
        "100 continue refused " + request.uri() + " for connection:" + ctx.channel().remoteAddress());
    HttpResponse response =
        new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
    // 关闭链接;
    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
  }

  /**
   * 写出Http 响应
   * @param request 请求
   * @param response 响应
   * @param ctx context
   */
  private void writeResponse(HttpRequest request, HttpResponse response, ChannelHandlerContext ctx) {
    // 屏蔽掉HTTP/1.1默认支持Keep-Alive
    boolean isKeepAlive = config.isSupportHttpAlive() && HttpHelper.isKeepAlive(request, false);
    if (response instanceof FullHttpResponse) {
      response.headers().set(HttpHeaders.CONTENT_LENGTH,
        ((FullHttpResponse)response).content().readableBytes());
    } else {
      response.headers().set(HttpHeaders.CONTENT_LENGTH, 0);
    }
    if (isKeepAlive) {
      response.headers().set(HttpHeaders.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
    }
    ChannelFuture future = ctx.writeAndFlush(response);
    if (!isKeepAlive) {
      future.addListener(ChannelFutureListener.CLOSE);
    }
  }
}
