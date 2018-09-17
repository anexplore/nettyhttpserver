package com.fd.asynchttpserver;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fd.asynchttpserver.HttpRequestHandler;
import com.fd.asynchttpserver.HttpRequestWrapper;
import com.fd.asynchttpserver.HttpResponseWrapper;
import com.fd.asynchttpserver.nettyimpl.NettyHttpServer;

public class NettyServerTest {
  
  private static final Logger LOG = LoggerFactory.getLogger(NettyServerTest.class);
  
  public static void main(String args[]) throws Exception {
    DefaultHttpServerConfig.Builder config = new DefaultHttpServerConfig.Builder();
    config.setBindAddress("127.0.0.1").setBindPort(8089).setReadTimeout(5000).setBossPoolSize(1).setHttps(false);
    NettyHttpServer server = new NettyHttpServer(config.build());
    HttpRequestHandler defaultHandler = new HttpRequestHandler() {
      @Override
      public void handle(HttpRequestWrapper request, HttpResponseWrapper response)
          throws Exception {
        Map<String, List<String>> params = request.getParamters();
        if (params != null) {
          for (Entry<String, List<String>> entry : params.entrySet()) {
            for (String v : entry.getValue()) {
              LOG.info(entry.getKey() + "," + v);
            }
          }
        }
        LOG.info(request.toString());
      }
    };
    server.registerHandler("*", defaultHandler);
    server.startup();
  }
}
