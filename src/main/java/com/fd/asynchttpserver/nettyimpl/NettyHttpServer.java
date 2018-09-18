package com.fd.asynchttpserver.nettyimpl;

import java.net.InetSocketAddress;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fd.asynchttpserver.HttpRequestHandler;
import com.fd.asynchttpserver.HttpServer;
import com.fd.asynchttpserver.HttpServerConfig;
import com.fd.asynchttpserver.UriHttpRequestHandlerMapper;
import com.fd.asynchttpserver.utils.Args;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
/**
 * Netty Http Server
 * @author anexplore
 *
 */
public class NettyHttpServer implements HttpServer {
  private static final Logger LOG = LoggerFactory.getLogger(NettyHttpServer.class);
  private final HttpServerConfig config;
  private final UriHttpRequestHandlerMapper handlerMap;
  private final NettyServerContext context;
  private EventLoopGroup bossGroup;
  private EventLoopGroup workGroup;
  private ServerBootstrap bootstrap;

  public NettyHttpServer(HttpServerConfig config) {
    Args.notNull(config, "http server config");
    Args.notNull(config.getBindAddress(), "localAddress");
    this.config = config;
    handlerMap = new UriHttpRequestHandlerMapper();
    context = new NettyServerContext();
    context.setHandlerMapper(handlerMap);
    context.setHttpServerConfig(config);
  }

  @Override
  public boolean startup() throws Exception {
    final SslContext sslCtx;
    if (config.isHttps()) {
      if (config.getCertificate() != null && config.getPrivateKey() != null) {
        LOG.info("use {} and {} as ssl crt and key", config.getCertificate().getName(), config.getPrivateKey().getName());
        sslCtx = SslContextBuilder.forServer(config.getCertificate(), config.getPrivateKey()).build();
      } else {
        LOG.info("use auto generated self signed ssl crt and key");
        SelfSignedCertificate ssc = new SelfSignedCertificate();
        sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
      }
    } else {
      sslCtx = null;
    }
    context.setSslContext(sslCtx);
    bossGroup = new NioEventLoopGroup(config.getBossPoolSize());
    workGroup = new NioEventLoopGroup(config.getWorkPoolSize());
    bootstrap = new ServerBootstrap();
    bootstrap.channel(NioServerSocketChannel.class);
    bootstrap.group(bossGroup, workGroup)
      .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
      .option(ChannelOption.SO_BACKLOG, config.getBacklog())
      .option(ChannelOption.SO_RCVBUF, config.getRecvBufferSize())
      .option(ChannelOption.SO_REUSEADDR, config.isReuseAddress())
      .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
      .childOption(ChannelOption.SO_RCVBUF, config.getRecvBufferSize())
      .childOption(ChannelOption.TCP_NODELAY, config.isTcpNoDelay())
      .childOption(ChannelOption.SO_KEEPALIVE, false)
      .childOption(ChannelOption.SO_LINGER, config.getSoLinger())
      .childOption(ChannelOption.SO_SNDBUF, config.getSendBufferSize());
    bootstrap.childHandler(new NettyChannelInitializer(context));
    bootstrap.bind(new InetSocketAddress(config.getBindAddress(), config.getBindPort())).sync();
    LOG.info("server started on:" + config.getBindAddress() + ":" + config.getBindPort());
    return true;
  }

  @Override
  public void shutdown() {
    if (bossGroup != null) {
      bossGroup.shutdownGracefully();
    }
    if (workGroup != null) {
      workGroup.shutdownGracefully();
    }
  }

  @Override
  public void registerHandler(String path, HttpRequestHandler handler) {
    Args.notNull(path, "path");
    Args.notNull(handler, "handler");
    handlerMap.register(path, handler);
  }

  @Override
  public Map<String, HttpRequestHandler> getHandlers() {
    return handlerMap.getHandlers();
  }

  @Override
  public boolean isIoCompressionEnabled() {
    return config.isCompressionEnabled();
  }

  @Override
  public int getBindPort() {
    return config.getBindPort();
  }

  @Override
  public String getBindAddress() {
    return config.getBindAddress();
  }

  @Override
  public boolean isHttps() {
    return config.isHttps();
  }

}
