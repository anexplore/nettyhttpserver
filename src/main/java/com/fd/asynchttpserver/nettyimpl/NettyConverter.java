package com.fd.asynchttpserver.nettyimpl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.codec.http.HttpResponseStatus;
import org.jboss.netty.handler.codec.http.HttpVersion;
import org.jboss.netty.handler.codec.http.QueryStringDecoder;
import org.jboss.netty.util.CharsetUtil;

import com.fd.asynchttpserver.HttpHeaderWrapper;
import com.fd.asynchttpserver.HttpRequestWrapper;
import com.fd.asynchttpserver.utils.Args;

/**
 * 将netty内部类型转换成本模块的封装类型
 */
public class NettyConverter {

    /**
     * <li>将Netty的HttpRequest转换成HttpRequestWrapper</li>
     * <li>这里没有直接设置body而是设置ChannelBuffer从而避免一次内存copy</li>
     * 
     * @param request
     * @return
     */
    public static HttpRequestWrapper convertRequest(HttpRequest request) {
        Args.notNull(request, "request");
        NettyHttpRequest wrapper = new NettyHttpRequest();
        wrapper.setMethod(request.getMethod().getName().toUpperCase(Locale.ENGLISH));
        wrapper.setUri(request.getUri());
        wrapper.setChannelBuffer(request.getContent());
        for (Map.Entry<String, String> h : request.headers()) {
            wrapper.addHeader(new HttpHeaderWrapper(h.getKey(), h.getValue()));
        }
        QueryStringDecoder queryDecoder = new QueryStringDecoder(request.getUri());
        for (Map.Entry<String, List<String>> query : queryDecoder.getParameters().entrySet()) {
            wrapper.addParamter(query.getKey(), query.getValue());
        }
        return wrapper;
    }

    /**
     * 将HttpResponseWrapper转换成Netty的Http Response
     * 
     * @param wrapper
     * @return 采用UTF-8编码 如果转码失败则body为空
     */
    public static HttpResponse convertResponse(NettyHttpResponse wrapper) {
        Args.notNull(wrapper, "response wrapper");
        HttpResponse response = new DefaultHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.valueOf(wrapper.getStatusCode()));
        response.setChunked(false);
        try {
            response.setContent(
                    ChannelBuffers.copiedBuffer(wrapper.getBodyAsString(), CharsetUtil.UTF_8));
        } catch (Exception e) {
            response.setContent(ChannelBuffers.copiedBuffer(new byte[0]));
        }
        if (wrapper.getHeaders() != null) {
            for (HttpHeaderWrapper header : wrapper.getHeaders()) {
                response.headers().add(header.getKey(), header.getValue());
            }
        }
        return response;
    }
}
