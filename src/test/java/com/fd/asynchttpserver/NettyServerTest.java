package com.fd.asynchttpserver;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fd.asynchttpserver.HttpRequestHandler;
import com.fd.asynchttpserver.HttpRequestWrapper;
import com.fd.asynchttpserver.HttpResponseWrapper;
import com.fd.asynchttpserver.http.HttpHeaders;
import com.fd.asynchttpserver.http.HttpStatus;
import com.fd.asynchttpserver.nettyimpl.NettyHttpResponse;
import com.fd.asynchttpserver.nettyimpl.NettyHttpServer;

public class NettyServerTest {

  private static final Logger LOG = LoggerFactory.getLogger(NettyServerTest.class);

  public static void main(String args[]) throws Exception {
    DefaultHttpServerConfig.Builder config = new DefaultHttpServerConfig.Builder();
    config.setBindAddress("127.0.0.1")
      .setBindPort(8089)
      .setReadTimeout(5000)
      .setHttps(false);
    NettyHttpServer server = new NettyHttpServer(config.build());
    HttpRequestHandler defaultHandler = new HttpRequestHandler() {
      @Override
      public void handle(HttpRequestWrapper request, HttpResponseWrapper response)
          throws Exception {
        LOG.info("Request: {}", request);
        Map<String, List<String>> params = request.getParamters();
        if (params != null) {
          for (Entry<String, List<String>> entry : params.entrySet()) {
            for (String value : entry.getValue()) {
              LOG.info("Param:{}, {}", entry.getKey(), value);
            }
          }
        }
        NettyHttpResponse nettyResponse = (NettyHttpResponse)response;
        nettyResponse.setStringBody("Hello Guest");
        nettyResponse.setStatusCode(HttpStatus.SC_OK);
        nettyResponse.addHeader(HttpHeaders.SET_COOKIE, "CT=27315; max-age=86400; domain=.com; path=/");
        nettyResponse.addHeader("myheader", "netty");
      }
    };
    server.registerHandler("*", defaultHandler);
    server.startup();
  }
}
