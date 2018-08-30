package com.fd.asynchttpserver.nettyimpl;

import com.fd.asynchttpserver.HttpServerConfig;

public class NettyHttpServerConfig extends HttpServerConfig {

    private int maxHttpTrunkAggregatorSize = 1024 * 1024 * 1024;
    // executor thread pool size
    private int maxExecutionThreadSize = 10;
    // 每个channel最大可占用内存
    private long maxChannelMemorySize = 30 * 1024 * 1024L;
    // 所有channel所占最大内存数
    private long maxTotalMemorySize = maxExecutionThreadSize * maxChannelMemorySize;

    public int getMaxExecutionThreadSize() {
        return maxExecutionThreadSize;
    }

    public NettyHttpServerConfig setMaxExecutionThreadSize(int maxExecutionThreadSize) {
        this.maxExecutionThreadSize = maxExecutionThreadSize;
        return this;
    }

    public long getMaxChannelMemorySize() {
        return maxChannelMemorySize;
    }

    public NettyHttpServerConfig setMaxChannelMemorySize(long maxChannelMemorySize) {
        this.maxChannelMemorySize = maxChannelMemorySize;
        return this;
    }

    public long getMaxTotalMemorySize() {
        return maxTotalMemorySize;
    }

    public NettyHttpServerConfig setMaxTotalMemorySize(long maxTotalMemorySize) {
        this.maxTotalMemorySize = maxTotalMemorySize;
        return this;
    }

    public int getMaxHttpTrunkAggregatorSize() {
        return maxHttpTrunkAggregatorSize;
    }

    public NettyHttpServerConfig setMaxHttpTrunkAggregatorSize(int maxHttpTrunkAggregatorSize) {
        this.maxHttpTrunkAggregatorSize = maxHttpTrunkAggregatorSize;
        return this;
    }

}
