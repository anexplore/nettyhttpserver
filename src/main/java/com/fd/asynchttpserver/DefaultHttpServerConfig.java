package com.fd.asynchttpserver;

import java.io.File;
import com.fd.asynchttpserver.utils.TextUtils;
import static com.fd.asynchttpserver.DefaultConfigHelper.*;

/**
 * HttpServer配置
 * 
 */
public class DefaultHttpServerConfig implements HttpServerConfig {
  
  
  public static class Builder {
    private boolean enableCompression = defaultEnableCompression();
    private boolean https = defaultHttps();
    private int bindPort = defaultBindPort();
    private String bindAddress = defaultBindAddress();
    private int bossPoolSize = defaultBossPoolSize();
    private int workPoolSize = defaultWorkPoolSize();
    private int readTimeout = defaultReadTimeout();
    private boolean keepAlive = defaultKeepAlive();
    private String certificateFile = defaultCertificateFile();
    private String privateKeyFile = defaultPrivateKeyFile();
    private boolean reuseAddress = defaultReuseAddress();
    private int soLinger = defaultSoLinger();
    private boolean tcpNoDelay = defaultTcpNoDelay();
    private int recvBufferSize = defaultRecvBufferSize();
    private int sendBufferSize = defaultSendBufferSize();
    private int backlog = defaultBacklog();
    private boolean validateHeaders = defaltValidateHeaders();
    private int maxInitialLineLength = defaultMaxInitialLineLength();
    private int maxHeaderSize = defaultMaxHeaderSize();
    private int maxChunkSize = defaultMaxChunkSize();
    private int maxBodyLength = defaultMaxBodyLength();
    
    public Builder() {}
    
    public Builder setEnableCompression(boolean enableCompression) {
      this.enableCompression = enableCompression;
      return this;
    }

    public Builder setHttps(boolean https) {
      this.https = https;
      return this;
    }

    public Builder setBindPort(int bindPort) {
      this.bindPort = bindPort;
      return this;
    }

    public Builder setBindAddress(String bindAddress) {
      this.bindAddress = bindAddress;
      return this;
    }

    public Builder setBossPoolSize(int bossPoolSize) {
      this.bossPoolSize = bossPoolSize;
      return this;
    }

    public Builder setWorkPoolSize(int workPoolSize) {
      this.workPoolSize = workPoolSize;
      return this;
    }

    public Builder setReadTimeout(int readTimeout) {
      this.readTimeout = readTimeout;
      return this;
    }

    public Builder setKeepAlive(boolean keepAlive) {
      this.keepAlive = keepAlive;
      return this;
    }

    public Builder setCertificateFile(String certificateFile) {
      this.certificateFile = certificateFile;
      return this;
    }

    public Builder setPrivateKeyFile(String privateKeyFile) {
      this.privateKeyFile = privateKeyFile;
      return this;
    }

    public Builder setReuseAddress(boolean reuseAddress) {
      this.reuseAddress = reuseAddress;
      return this;
    }

    public Builder setSoLinger(int soLinger) {
      this.soLinger = soLinger;
      return this;
    }

    public Builder setTcpNoDelay(boolean tcpNoDelay) {
      this.tcpNoDelay = tcpNoDelay;
      return this;
    }

    public Builder setRecvBufferSize(int recvBufferSize) {
      this.recvBufferSize = recvBufferSize;
      return this;
    }

    public Builder setSendBufferSize(int sendBufferSize) {
      this.sendBufferSize = sendBufferSize;
      return this;
    }

    public Builder setBacklog(int backlog) {
      this.backlog = backlog;
      return this;
    }

    public Builder setValidateHeaders(boolean validateHeaders) {
      this.validateHeaders = validateHeaders;
      return this;
    }

    public Builder setMaxInitialLineLength(int maxInitialLineLength) {
      this.maxInitialLineLength = maxInitialLineLength;
      return this;
    }

    public Builder setMaxHeaderSize(int maxHeaderSize) {
      this.maxHeaderSize = maxHeaderSize;
      return this;
    }

    public Builder setMaxChunkSize(int maxChunkSize) {
      this.maxChunkSize = maxChunkSize;
      return this;
    }

    public Builder setMaxBodyLength(int maxBodyLength) {
      this.maxBodyLength = maxBodyLength;
      return this;
    }

    public DefaultHttpServerConfig build() {
      File certificateFile = TextUtils.isBlank(this.certificateFile) ? null : new File(this.certificateFile);
      File privateKeyFile = TextUtils.isBlank(this.privateKeyFile) ? null : new File(this.privateKeyFile);
      return new DefaultHttpServerConfig(
          enableCompression,
          https,
          bindAddress,
          bindPort,
          bossPoolSize,
          workPoolSize,
          readTimeout,
          keepAlive,
          certificateFile,
          privateKeyFile,
          reuseAddress,
          soLinger,
          tcpNoDelay,
          recvBufferSize,
          sendBufferSize,
          backlog,
          validateHeaders,
          maxInitialLineLength,
          maxHeaderSize,
          maxChunkSize,
          maxBodyLength
          );
    }
  }
  
  private final boolean enableCompression;
  private final boolean https;
  private final int bindPort;
  private final String bindAddress;
  private final int bossPoolSize;
  private final int workPoolSize;
  private final int readTimeout;
  private final boolean keepAlive;
  private final File certificateFile;
  private final File privateKeyFile;
  private final boolean reuseAddress;
  private final int soLinger;
  private final boolean tcpNoDelay;
  private final int recvBufferSize;
  private final int sendBufferSize;
  private final int backlog;
  private final int maxInitialLineLength;
  private final int maxChunkSize;
  private final int maxBodyLength;
  private final int maxHeaderSize;
  private final boolean validateHeaders;
  
  public DefaultHttpServerConfig(boolean enableCompression, boolean https, String bindAddress,
      int bindPort, int bossPoolSize, int workPoolSize, int readTimeout,
      boolean keepAlive, File certificateFile, File privateKeyFile, boolean reuseAddress,
      int soLinger, boolean tcpNoDelay, int recvBufferSize, int sendBufferSize, int backlog,
      boolean validateHeaders, int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, int maxBodyLength) {
    this.enableCompression = enableCompression;
    this.https = https;
    this.bindPort = bindPort;
    this.bindAddress = bindAddress;
    this.bossPoolSize = bossPoolSize;
    this.workPoolSize = workPoolSize;
    this.readTimeout = readTimeout;
    this.keepAlive = keepAlive;
    this.certificateFile = certificateFile;
    this.privateKeyFile = privateKeyFile;
    this.reuseAddress = reuseAddress;
    this.soLinger = soLinger;
    this.tcpNoDelay = tcpNoDelay;
    this.recvBufferSize = recvBufferSize;
    this.sendBufferSize = sendBufferSize;
    this.backlog = backlog;
    this.validateHeaders = validateHeaders;
    this.maxInitialLineLength = maxInitialLineLength;
    this.maxChunkSize = maxChunkSize;
    this.maxBodyLength = maxBodyLength;
    this.maxHeaderSize = maxHeaderSize;
  }

  @Override
  public boolean isCompressionEnabled() {
    return enableCompression;
  }

  @Override
  public boolean isSupportHttpAlive() {
    return keepAlive;
  }

  @Override
  public File getCertificate() {
    return certificateFile;
  }

  @Override
  public File getPrivateKey() {
    return privateKeyFile;
  }

  @Override
  public boolean isReuseAddress() {
    return reuseAddress;
  }

  @Override
  public int getSoLinger() {
    return soLinger;
  }

  @Override
  public boolean isTcpNoDelay() {
    return tcpNoDelay;
  }

  @Override
  public int getRecvBufferSize() {
    return recvBufferSize;
  }

  @Override
  public int getSendBufferSize() {
    return sendBufferSize;
  }

  @Override
  public int getBacklog() {
    return backlog;
  }

  @Override
  public boolean isHttps() {
    return https;
  }

  @Override
  public int getBindPort() {
    return bindPort;
  }

  @Override
  public String getBindAddress() {
    return bindAddress;
  }

  @Override
  public int getBossPoolSize() {
    return bossPoolSize;
  }

  @Override
  public int getWorkPoolSize() {
    return workPoolSize;
  }

  @Override
  public int getReadTimeout() {
    return readTimeout;
  }

  @Override
  public boolean isValidateHeaders() {
    return validateHeaders;
  }

  @Override
  public int getMaxInitialLineLength() {
    return maxInitialLineLength;
  }

  @Override
  public int getMaxHeaderSize() {
    return maxHeaderSize;
  }

  @Override
  public int getMaxChunkSize() {
    return maxChunkSize;
  }

  @Override
  public int getMaxBodyLength() {
    return maxBodyLength;
  }

}
