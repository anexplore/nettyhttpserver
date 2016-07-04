package com.fd.nettyhttpserver;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fd.nettyhttpserver.utils.Args;


/**
 * Maintains a map of objects keyed by a request URI pattern. <br>
 * Patterns may have three formats:
 * <ul>
 * <li><code>*</code></li>
 * <li><code>*&lt;uri&gt;</code></li>
 * <li><code>&lt;uri&gt;*</code></li>
 * </ul>
 * <br>
 * This class can be used to resolve an object matching a particular request URI.
 *
 * @see Copy and modified from Apache HttpCore 4.4
 */
public class UriPatternMatcher<T> {

    private final Map<String, T> map;

    public UriPatternMatcher() {
        super();
        this.map = new HashMap<String, T>();
    }

    /**
     * Registers the given object for URIs matching the given pattern.
     *
     * @param pattern the pattern to register the handler for.
     * @param obj the object.
     */
    public synchronized void register(final String pattern, final T obj) {
        Args.notNull(pattern, "URI request pattern");
        this.map.put(pattern, obj);
    }

    /**
     * Removes registered object, if exists, for the given pattern.
     *
     * @param pattern the pattern to unregister.
     */
    public synchronized void unregister(final String pattern) {
        if (pattern == null) {
            return;
        }
        this.map.remove(pattern);
    }

    /**
     * @return Map镜像
     */
    public synchronized Map<String, T> getObjects() {
        Map<String, T> r = new HashMap<String, T>();
        for (Entry<String, T> entry : this.map.entrySet()) {
            r.put(entry.getKey(), entry.getValue());
        }
        return r;
    }

    /**
     * Looks up an object matching the given request path.
     *
     * @param path the request path
     * @return object or <code>null</code> if no match is found.
     */
    public synchronized T lookup(final String path) {
        Args.notNull(path, "Request path");
        // direct match?
        T obj = this.map.get(path);
        if (obj == null) {
            // pattern match?
            String bestMatch = null;
            for (final String pattern : this.map.keySet()) {
                if (matchUriRequestPattern(pattern, path)) {
                    // we have a match. is it any better?
                    if (bestMatch == null || (bestMatch.length() < pattern.length())
                            || (bestMatch.length() == pattern.length() && pattern.endsWith("*"))) {
                        obj = this.map.get(pattern);
                        bestMatch = pattern;
                    }
                }
            }
        }
        return obj;
    }

    /**
     * Tests if the given request path matches the given pattern.
     *
     * @param pattern the pattern
     * @param path the request path
     * @return <code>true</code> if the request URI matches the pattern, <code>false</code>
     *         otherwise.
     */
    protected boolean matchUriRequestPattern(final String pattern, final String path) {
        if (pattern.equals("*")) {
            return true;
        } else {
            return (pattern.endsWith("*")
                    && path.startsWith(pattern.substring(0, pattern.length() - 1)))
                    || (pattern.startsWith("*")
                            && path.endsWith(pattern.substring(1, pattern.length())));
        }
    }

    @Override
    public String toString() {
        return this.map.toString();
    }

}
