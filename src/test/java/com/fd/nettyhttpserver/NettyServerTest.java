package com.fd.nettyhttpserver;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.fd.nettyhttpserver.HttpHeaderWrapper;
import com.fd.nettyhttpserver.HttpRequestHandler;
import com.fd.nettyhttpserver.HttpRequestWrapper;
import com.fd.nettyhttpserver.HttpResponseWrapper;
import com.fd.nettyhttpserver.http.HttpStatus;
import com.fd.nettyhttpserver.nettyimpl.NettyHttpServer;
import com.fd.nettyhttpserver.nettyimpl.NettyHttpServerConfig;

public class NettyServerTest {
	
    public static void main(String args[]) throws Exception {
		NettyHttpServerConfig config = new NettyHttpServerConfig();
		config.setLocalAddress("127.0.0.1");
		config.setListenPort(8089);
		config.setReadTimeout(5000);
		config.setBossPoolSize(2);
		config.setHttps(false);
		config.setMaxExecutionThreadSize(2000);
		NettyHttpServer server = new NettyHttpServer(config);
		HttpRequestHandler defaultHandler = new HttpRequestHandler() {
			@Override
			public void handle(HttpRequestWrapper request,
					HttpResponseWrapper response) throws Exception {
				Map<String, List<String>> params = request.getParamters();
				for (Entry<String, List<String>> entry: params.entrySet()) {
					for (String v : entry.getValue()) {
						System.out.println(entry.getKey() +"," + v);
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
