基于Netty4的HttpServer

Dependency:
netty4

Notice:
* 不支持Http Expect 100 Continue 请求
* 没有添加自动解压缩请求体


Example:
```java
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
```