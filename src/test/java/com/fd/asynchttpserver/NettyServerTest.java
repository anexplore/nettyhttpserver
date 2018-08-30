package com.fd.asynchttpserver;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fd.asynchttpserver.HttpHeaderWrapper;
import com.fd.asynchttpserver.HttpRequestHandler;
import com.fd.asynchttpserver.HttpRequestWrapper;
import com.fd.asynchttpserver.HttpResponseWrapper;
import com.fd.asynchttpserver.http.HttpStatus;
import com.fd.asynchttpserver.nettyimpl.NettyHttpServer;
import com.fd.asynchttpserver.nettyimpl.NettyHttpServerConfig;

public class NettyServerTest {
	
    public static void main(String args[]) throws Exception {
		NettyHttpServerConfig config = new NettyHttpServerConfig();
		config.setLocalAddress("127.0.0.1");
		config.setListenPort(8089);
		config.setReadTimeout(5000);
		config.setBossPoolSize(1);
		config.setHttps(false);
		config.setMaxExecutionThreadSize(2000);
		NettyHttpServer server = new NettyHttpServer(config);
		HttpRequestHandler defaultHandler = new HttpRequestHandler() {
			@Override
			public void handle(HttpRequestWrapper request,
					HttpResponseWrapper response) throws Exception {
				Map<String, List<String>> params = request.getParamters();
				if (params != null) {
    				for (Entry<String, List<String>> entry: params.entrySet()) {
    					for (String v : entry.getValue()) {
    						System.out.println(entry.getKey() +"," + v);
    					}
    				}
				}
				response.setStringBody("Hello world");
				response.addHeader(new HttpHeaderWrapper("Content-Type",
						"text/plain; charset=utf-8"));
				response.setStatusCode(HttpStatus.SC_OK);
			}
		};
		server.registerHandler("*", defaultHandler);
		server.startup();
	}
}
