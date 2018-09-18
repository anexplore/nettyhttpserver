package com.fd.asynchttpserver.nettyimpl;

import java.util.concurrent.TimeUnit;
import com.fd.asynchttpserver.UriHttpRequestHandlerMapper;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.ReadTimeoutHandler;

/**
 * 初始化Channel Pipeline
 * @author anexplore
 *
 */
public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {
  private final NettyServerContext serverContext;
  private final UriHttpRequestHandlerMapper handlerMapper;

  public NettyChannelInitializer(NettyServerContext serverContext) {
    this.serverContext = serverContext;
    this.handlerMapper = this.serverContext.getHandlerMapper();
  }

  @Override
  public void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();
    if (serverContext.getSslContext() != null) {
      pipeline.addLast("ssl", serverContext.getSslContext().newHandler(ch.alloc()));
    }
    pipeline.addLast("readtimeouthandler",
        new ReadTimeoutHandler(serverContext.getHttpServerConfig().getReadTimeout(), TimeUnit.MILLISECONDS));
    pipeline.addLast("decoder", new HttpServerCodec(serverContext.getHttpServerConfig().getMaxInitialLineLength(), 
        serverContext.getHttpServerConfig().getMaxHeaderSize(),
        serverContext.getHttpServerConfig().getMaxChunkSize()));
    pipeline.addLast("aggregator", new HttpObjectAggregator(serverContext.getHttpServerConfig().getMaxBodyLength()));
    if (serverContext.getHttpServerConfig().isCompressionEnabled()) {
      pipeline.addLast("deflater", new HttpContentCompressor());
    }
    pipeline.addLast("handler", new InnerServerHandler(handlerMapper, serverContext.getHttpServerConfig()));
  }

}
