package com.fd.asynchttpserver;
/**
 * 默认配置
 * @author anexplore
 *
 */
public class DefaultConfigHelper {
  
  private static final String PROPERTY_PREFIX = "com.fd.asynchttpserver.";
  private static final String ENABLE_COMPRESSION = "enableCompression";
  private static final String HTTPS = "https";
  private static final String BIND_PORT = "bindPort";
  private static final String BIND_ADDRESS = "bindAddress";
  private static final String BOSS_POOL_SIZE = "bossPoolSize";
  private static final String WORK_POOL_SIZE = "workPoolSize";
  private static final String READ_TIMEOUT = "readTimeout";
  private static final String KEEP_ALIVE = "keepAlive";
  private static final String REUSE_ADDRESS = "reuseAddress";
  private static final String SO_LINGER = "soLinger";
  private static final String TCP_NODELAY = "tcpNoDelay";
  private static final String RECV_BUFFER_SIZE = "recvBufferSize";
  private static final String SEND_BUFFER_SIZE = "sendBufferSize";
  private static final String BACKLOG = "backlog";
  private static final String MAX_INITIAL_LINE_LENGTH = "maxInitialLineLength";
  private static final String MAX_CHUNK_SIZE = "maxChunkSize";
  private static final String MAX_HEADER_SIZE = "maxHeaderSize";
  private static final String MAX_BODY_LENGTH = "maxBodyLength";
  private static final String VALIDATE_HEADERS = "validateHeaders";
  
  public static boolean convertBooleanValue(String value) {
    if (value.toLowerCase().equals("true")) {
      return true;
    }
    return false;
  }
  
  public static int convertIntegerValue(String value) {
    return Integer.parseInt(value);
  }
  
  public static boolean defaultEnableCompression() {
    return convertBooleanValue(System.getProperty(PROPERTY_PREFIX + ENABLE_COMPRESSION, "false"));
  }
  
  public static boolean defaultHttps() {
    return convertBooleanValue(System.getProperty(PROPERTY_PREFIX + HTTPS, "false"));
  }
  
  public static int defaultBindPort() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + BIND_PORT, "80"));
  }
  
  public static String defaultBindAddress() {
    return System.getProperty(PROPERTY_PREFIX + BIND_ADDRESS, "0.0.0.0");
  }
  
  public static int defaultBossPoolSize() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + BOSS_POOL_SIZE, "1"));
  }
  
  public static int defaultWorkPoolSize() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + WORK_POOL_SIZE, "" + Runtime.getRuntime().availableProcessors()));
  }
  
  public static int defaultReadTimeout() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + READ_TIMEOUT, "10000"));
  }
  
  public static boolean defaultKeepAlive() {
    return convertBooleanValue(System.getProperty(PROPERTY_PREFIX + KEEP_ALIVE, "false"));
  }
  
  public static String defaultCertificateFile() {
    return null;
  }
  
  public static String defaultPrivateKeyFile() {
    return null;
  }
  
  public static boolean defaultReuseAddress() {
    return convertBooleanValue(System.getProperty(PROPERTY_PREFIX + REUSE_ADDRESS, "true"));
  }
  
  public static int defaultSoLinger() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + SO_LINGER, "-1"));
  }
  
  public static boolean defaultTcpNoDelay() {
    return convertBooleanValue(System.getProperty(PROPERTY_PREFIX + TCP_NODELAY, "true"));
  }
  
  public static int defaultRecvBufferSize() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + RECV_BUFFER_SIZE, "" + 8 * 1024));
  }
  
  public static int defaultSendBufferSize() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + SEND_BUFFER_SIZE, "" + 8 * 1024));
  }
  
  public static int defaultBacklog() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + BACKLOG, "1024"));
  }
  
  public static boolean defaltValidateHeaders() {
    return convertBooleanValue(System.getProperty(PROPERTY_PREFIX + VALIDATE_HEADERS, "false"));
  }
 
  public static int defaultMaxBodyLength() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + MAX_BODY_LENGTH, "" + 5 * 1024 * 1024));
  }

  public static int defaultMaxChunkSize() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + MAX_CHUNK_SIZE, "" + 1 * 1024 * 1024));
  }

  public static int defaultMaxHeaderSize() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + MAX_HEADER_SIZE, "" + 8 * 1024));
  }

  public static int defaultMaxInitialLineLength() {
    return convertIntegerValue(System.getProperty(PROPERTY_PREFIX + MAX_INITIAL_LINE_LENGTH, "" + 4 * 1024));
  }

}
