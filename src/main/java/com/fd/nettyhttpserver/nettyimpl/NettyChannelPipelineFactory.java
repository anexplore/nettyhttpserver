package com.fd.nettyhttpserver.nettyimpl;

import java.util.concurrent.TimeUnit;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpContentCompressor;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.execution.ExecutionHandler;
import org.jboss.netty.handler.execution.OrderedMemoryAwareThreadPoolExecutor;
import org.jboss.netty.handler.ssl.SslContext;
import org.jboss.netty.handler.timeout.ReadTimeoutHandler;
import org.jboss.netty.util.Timer;

import com.fd.nettyhttpserver.UriHttpRequestHandlerMapper;


public class NettyChannelPipelineFactory implements ChannelPipelineFactory {
    private final SslContext sslCtx;
    private final NettyServerContext serverContext;
    private final UriHttpRequestHandlerMapper handlerMapper;
    private final int readTimeout;
    private final Timer timer;
    private final boolean compressionEnabled;
    private final ExecutionHandler execHandler;

    public NettyChannelPipelineFactory(SslContext context, NettyServerContext serverContext) {
        this.sslCtx = context;
        this.serverContext = serverContext;
        this.handlerMapper = this.serverContext.getHandlerMapper();
        this.readTimeout = this.serverContext.getHttpServerConfig().getReadTimeout();
        this.compressionEnabled = this.serverContext.getHttpServerConfig().isEnableCompression();
        this.timer = this.serverContext.getTimer();
        execHandler = new ExecutionHandler(new OrderedMemoryAwareThreadPoolExecutor(
                this.serverContext.getHttpServerConfig().getMaxExecutionThreadSize(),
                this.serverContext.getHttpServerConfig().getMaxChannelMemorySize(),
                this.serverContext.getHttpServerConfig().getMaxTotalMemorySize(), 60L,
                TimeUnit.SECONDS));
    }

    @Override
    public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        if (sslCtx != null) {
            pipeline.addLast("ssl", sslCtx.newHandler());
        }
        pipeline.addLast("readtimeouthandler",
                new ReadTimeoutHandler(timer, readTimeout, TimeUnit.MILLISECONDS));
        pipeline.addLast("decoder", new HttpRequestDecoder());
        // Uncomment the following line if you don't want to handle HttpChunks.
        pipeline.addLast("aggregator", new HttpChunkAggregator(
                serverContext.getHttpServerConfig().getMaxHttpTrunkAggregatorSize()));
        pipeline.addLast("encoder", new HttpResponseEncoder());
        // Remove the following line if you don't want automatic content compression.
        if (compressionEnabled) {
            pipeline.addLast("deflater", new HttpContentCompressor());
        }
        pipeline.addLast("execution", execHandler);
        pipeline.addLast("handler", new InnerServerHandler(handlerMapper));
        return pipeline;
    }

}
