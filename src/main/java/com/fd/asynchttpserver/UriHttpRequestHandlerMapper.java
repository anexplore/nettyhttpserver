package com.fd.asynchttpserver;

import java.util.Map;
import com.fd.asynchttpserver.utils.Args;


/**
 * Maintains a map of HTTP request handlers keyed by a request URI pattern. <br>
 * Patterns may have three formats:
 * <ul>
 * <li><code>*</code></li>
 * <li><code>*&lt;uri&gt;</code></li>
 * <li><code>&lt;uri&gt;*</code></li>
 * </ul>
 * <br>
 * This class can be used to map an instance of {@link HttpRequestHandler} matching a particular
 * request URI. Usually the mapped request handler will be used to process the request with the
 * specified request URI.
 *
 * @see Copy and Modified from Apache HttpCore 4.4
 */

public class UriHttpRequestHandlerMapper {

  private final UriPatternMatcher<HttpRequestHandler> matcher;

  protected UriHttpRequestHandlerMapper(final UriPatternMatcher<HttpRequestHandler> matcher) {
    super();
    this.matcher = Args.notNull(matcher, "Pattern matcher");
  }

  public UriHttpRequestHandlerMapper() {
    this(new UriPatternMatcher<HttpRequestHandler>());
  }

  /**
   * Registers the given {@link HttpRequestHandler} as a handler for URIs matching the given
   * pattern.
   *
   * @param pattern the pattern to register the handler for.
   * @param handler the handler.
   */
  public void register(final String pattern, final HttpRequestHandler handler) {
    Args.notNull(pattern, "Pattern");
    Args.notNull(handler, "Handler");
    matcher.register(pattern, handler);
  }

  /**
   * Removes registered handler, if exists, for the given pattern.
   *
   * @param pattern the pattern to unregister the handler for.
   */
  public void unregister(final String pattern) {
    matcher.unregister(pattern);
  }

  /**
   * Extracts request path from the given {@link HttpRequest}
   */
  protected String getRequestPath(final HttpRequestWrapper request) {
    String uriPath = request.getUri();
    int index = uriPath.indexOf("?");
    if (index != -1) {
      uriPath = uriPath.substring(0, index);
    } else {
      index = uriPath.indexOf("#");
      if (index != -1) {
        uriPath = uriPath.substring(0, index);
      }
    }
    return uriPath;
  }

  /**
   * Looks up a handler matching the given request URI.
   *
   * @param request the request
   * @return handler or <code>null</code> if no match is found.
   */
  public HttpRequestHandler lookup(final HttpRequestWrapper request) {
    Args.notNull(request, "HTTP request");
    return matcher.lookup(getRequestPath(request));
  }

  /**
   * 返回所有注册的handler(镜像)
   * 
   * @return map<String, HttpRequestHandler>
   */
  public Map<String, HttpRequestHandler> getHandlers() {
    return this.matcher.getObjects();
  }
}
