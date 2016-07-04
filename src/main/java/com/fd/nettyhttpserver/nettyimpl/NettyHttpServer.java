package com.fd.nettyhttpserver.nettyimpl;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.ssl.SslContext;
import org.jboss.netty.handler.ssl.util.SelfSignedCertificate;
import org.jboss.netty.util.HashedWheelTimer;
import org.jboss.netty.util.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fd.nettyhttpserver.HttpRequestHandler;
import com.fd.nettyhttpserver.HttpServer;
import com.fd.nettyhttpserver.UriHttpRequestHandlerMapper;
import com.fd.nettyhttpserver.utils.Args;
import com.fd.nettyhttpserver.utils.NamedThreadFactory;

public class NettyHttpServer implements HttpServer {
    private static final Logger LOG = LoggerFactory.getLogger(NettyHttpServer.class);
    private NettyHttpServerConfig config;
    private String localAddress;
    private int port;
    private boolean https;
    private boolean compressionEnabled = true;
    private final UriHttpRequestHandlerMapper handlerMap;
    private final NettyServerContext context;
    private final Timer timer;
    private ServerBootstrap bootstrap;

    public NettyHttpServer(NettyHttpServerConfig config) {
        Args.notNull(config, "http server config");
        Args.notNull(config.getLocalAddress(), "localAddress");
        this.config = config;
        this.localAddress = config.getLocalAddress();
        this.port = config.getListenPort();
        this.https = config.isHttps();
        this.compressionEnabled = config.isEnableCompression();
        handlerMap = new UriHttpRequestHandlerMapper();
        // tick is 100ms
        timer = new HashedWheelTimer();
        context = new NettyServerContext();
        context.setHandlerMapper(handlerMap);
        context.setHttpServerConfig(config);
        context.setTimer(timer);
    }

    @Override
    public boolean startup() throws Exception {
        final SslContext sslCtx;
        if (https) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslCtx = null;
        }
        bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
                new ThreadPoolExecutor(1, config.getBossPoolSize(), 60L, TimeUnit.SECONDS,
                        new SynchronousQueue<Runnable>(), new NamedThreadFactory("Server-Boss")),
                config.getBossPoolCoreSize(),
                new ThreadPoolExecutor(1, config.getWorkPoolSize(), 60L, TimeUnit.SECONDS,
                        new SynchronousQueue<Runnable>(), new NamedThreadFactory("Server-Worker")),
                config.getWorkPoolCoreSize()));
        bootstrap.setOption("child.tcpNoDelay", true);
        bootstrap.setPipelineFactory(new NettyChannelPipelineFactory(sslCtx, context));
        bootstrap.bind(new InetSocketAddress(localAddress, port));
        LOG.info("server started on:" + localAddress + ":" + port);
        return true;
    }

    @Override
    public void shutdown() {
        bootstrap.shutdown();
        bootstrap.releaseExternalResources();
        if (timer != null) {
            timer.stop();
        }
    }

    @Override
    public void registerHandler(String path, HttpRequestHandler handler) {
        Args.notNull(path, "path");
        Args.notNull(handler, "handler");
        handlerMap.register(path, handler);
    }

    @Override
    public void setHttps(boolean https) {
        this.https = https;
    }

    @Override
    public void enableIoCompression(boolean compression) {
        this.compressionEnabled = compression;
    }

    @Override
    public void setBindAddress(String address) {
        this.localAddress = address;
    }

    @Override
    public void setListenPort(int port) {
        this.port = port;
    }

    @Override
    public int getListenPort() {
        return port;
    }

    @Override
    public String getLocalAddress() {
        return localAddress;
    }

    @Override
    public boolean isHttps() {
        return https;
    }

    @Override
    public boolean isIoCompressionEabled() {
        return compressionEnabled;
    }

    @Override
    public Map<String, HttpRequestHandler> getHandlers() {
        return handlerMap.getHandlers();
    }

}