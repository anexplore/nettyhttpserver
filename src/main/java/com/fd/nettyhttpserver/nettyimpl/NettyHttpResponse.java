package com.fd.nettyhttpserver.nettyimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fd.nettyhttpserver.HttpHeaderWrapper;
import com.fd.nettyhttpserver.HttpResponseWrapper;
import com.fd.nettyhttpserver.http.HttpStatus;

/**
 * 非线程安全
 * 
 */
public class NettyHttpResponse implements HttpResponseWrapper {

    private byte[] body;
    private String sBody;
    private String httpVersion = "HTTP/1.1";
    private int statusCode = HttpStatus.SC_OK;
    private List<HttpHeaderWrapper> headers = new ArrayList<>(8);


    @Override
    public byte[] getBody() throws Exception {
        if (body == null && sBody != null) {
            return sBody.getBytes("UTF-8");
        }
        return body;
    }

    @Override
    public String getBodyAsString() throws Exception {
        if (sBody != null) {
            return sBody;
        }
        return body == null ? "" : new String(body, "UTF-8");
    }

    @Override
    public List<HttpHeaderWrapper> getHeaders() {
        return headers;
    }

    @Override
    public HttpHeaderWrapper getFirstHeader(String key) {
        if (key == null || headers == null) {
            return null;
        }
        for (HttpHeaderWrapper header : headers) {
            if (header.getKey().equalsIgnoreCase(key)) {
                return header;
            }
        }
        return null;
    }


    @Override
    public String getHttpVersion() {
        return httpVersion;
    }

    @Override
    public void setBody(byte[] body) {
        this.body = body;
    }

    @Override
    public void setStringBody(String body) {
        this.sBody = body;
    }

    @Override
    public void setHeaders(List<HttpHeaderWrapper> headers) {
        this.headers = headers;
    }

    @Override
    public void addHeader(HttpHeaderWrapper header) {
        if (header == null) {
            return;
        }
        if (headers == null) {
            headers = new ArrayList<>(8);
        }
        headers.add(header);
    }

    @Override
    public void setHttpVersion(String httpVersion) {
        this.httpVersion = httpVersion;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    @Override
    public String toString() {
        return "NettyHttpResponse [body=" + Arrays.toString(body) + ", sBody=" + sBody
                + ", httpVersion=" + httpVersion + ", statusCode=" + statusCode + ", headers="
                + headers + "]";
    }
}
