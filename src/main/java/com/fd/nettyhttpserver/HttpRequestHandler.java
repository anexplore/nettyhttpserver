package com.fd.nettyhttpserver;

public interface HttpRequestHandler {
    /**
     * 处理http请求
     * 
     * @param request 请求
     * @param response 响应
     * @throws Exception
     */
    void handle(HttpRequestWrapper request, HttpResponseWrapper response) throws Exception;
}
