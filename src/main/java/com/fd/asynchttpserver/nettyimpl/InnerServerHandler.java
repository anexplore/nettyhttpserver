package com.fd.asynchttpserver.nettyimpl;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fd.asynchttpserver.HttpRequestHandler;
import com.fd.asynchttpserver.HttpRequestWrapper;
import com.fd.asynchttpserver.UriHttpRequestHandlerMapper;


public class InnerServerHandler extends SimpleChannelUpstreamHandler {
    private static final Logger LOG = LoggerFactory.getLogger(InnerServerHandler.class);
    private UriHttpRequestHandlerMapper handlerMapper;

    public InnerServerHandler(UriHttpRequestHandlerMapper mapper) {
        this.handlerMapper = mapper;
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        HttpRequest request = (HttpRequest) e.getMessage();
        if (HttpHeaders.is100ContinueExpected(request)) {
            send100Continue(request, e);
            return;
        }
        HttpRequestWrapper requestWrapper = NettyConverter.convertRequest(request);
        requestWrapper.setRemoteAddress(e.getRemoteAddress());
        HttpRequestHandler handler = handlerMapper.lookup(requestWrapper);
        if (handler == null) {
            sendAccessDenied(request, e);
            return;
        }
        NettyHttpResponse responseWrapper = new NettyHttpResponse();
        try {
            handler.handle(requestWrapper, responseWrapper);
        } catch (Exception excep) {
            LOG.error("handle error:" + request.getUri(), excep);
        }
        HttpResponse response = NettyConverter.convertResponse(responseWrapper);
        writeResponse(request, response, e);
    }

    private void writeResponse(HttpRequest request, HttpResponse response, MessageEvent e) {
        boolean isKeepAlive = HttpHeaders.isKeepAlive(request);
        if (isKeepAlive) {
            response.headers().add(HttpHeaders.Names.CONTENT_LENGTH,
                    response.getContent().readableBytes());
            response.headers().add(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
        }
        ChannelFuture future = e.getChannel().write(response);
        if (!isKeepAlive) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        LOG.error("request error:" + e.getCause() + ", for " + e.getChannel().getRemoteAddress());
        e.getChannel().close();
    }

    /**
     * 拒绝访问
     * 
     * @param e
     */
    private void sendAccessDenied(HttpRequest request, MessageEvent e) {
        LOG.warn("deny access " + request.getUri() + " for connection:" + e.getRemoteAddress());
        HttpResponse response =
                new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
        e.getChannel().write(response);
        // 关闭链接
        e.getFuture().addListener(ChannelFutureListener.CLOSE);
    }

    /**
     * 不支持100 continue
     * 
     * @param e
     */
    private void send100Continue(HttpRequest request, MessageEvent e) {
        LOG.warn("100 continue refused " + request.getUri() + " for connection:"
                + e.getRemoteAddress());
        HttpResponse response =
                new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
        e.getChannel().write(response);
        // 关闭链接
        e.getFuture().addListener(ChannelFutureListener.CLOSE);
    }
}
