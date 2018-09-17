package com.fd.asynchttpserver.utils;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 命名的线程Factory
 * @author anexplore
 *
 */
public class NamedThreadFactory implements ThreadFactory {
  private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
  private final ThreadGroup group;
  private final AtomicInteger threadNumber = new AtomicInteger(1);
  private final String namePrefix;

  public NamedThreadFactory(String prefix) {
    SecurityManager s = System.getSecurityManager();
    group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
    namePrefix = prefix + "-pool-" + POOL_NUMBER.getAndIncrement() + "-thread-";
  }

  public Thread newThread(Runnable r) {
    Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
    if (t.isDaemon())
      t.setDaemon(false);
    if (t.getPriority() != Thread.NORM_PRIORITY)
      t.setPriority(Thread.NORM_PRIORITY);
    return t;
  }
}
