package com.as.security.shiro;

import com.google.common.cache.CacheBuilder;
import com.google.common.primitives.Ints;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;

/**
 * @author who
 */
public class GuavaCacheManager extends AbstractCacheManager {
    private static final Logger LOG = LoggerFactory.getLogger(GuavaCacheManager.class);

    /**
     * Specification to use when creating cacheBuilder.
     * <p>
     * It may be null if user didn't gave any configuration.
     */
    protected String cacheBuilderSpecification;

    /**
     * CacheBuilder to create new caches according to {@link #cacheBuilderSpecification} if provided.
     */
    protected CacheBuilder cacheBuilder;

    public void setCacheBuilderSpecification(String cacheBuilderSpecification) {
        this.cacheBuilderSpecification = cacheBuilderSpecification;
    }

    protected CacheBuilder getCacheBuilder() {
        if (cacheBuilder == null) {
            if (cacheBuilderSpecification == null) {
                LOG.info("creating default cache builder");
                cacheBuilder = CacheBuilder.newBuilder();
            } else {
                LOG.info("creating cache builder using spec " + cacheBuilderSpecification);
                cacheBuilder = CacheBuilder.from(cacheBuilderSpecification);
            }
        }
        return cacheBuilder;
    }

    @Override
    protected Cache createCache(String name) {
        com.google.common.cache.Cache guavaCache = getCacheBuilder().build();
        Cache cache = new ShiroCacheToGuavaCacheAdapter(name, guavaCache);
        return cache;
    }

    /**
     * Adapts a {@link com.google.common.cache.Cache} to a {@link Cache} implementation.
     *
     * @author Brendan Le Ny <bleny@codelutin.com>
     * @since 1.3
     */
    protected static class ShiroCacheToGuavaCacheAdapter<K, V> implements Cache<K, V> {

        protected String name;

        protected com.google.common.cache.Cache<K, V> adapted;

        public ShiroCacheToGuavaCacheAdapter(String name, com.google.common.cache.Cache<K, V> adapted) {
            this.name = name;
            this.adapted = adapted;
        }

        public V get(K key) {
            return adapted.getIfPresent(key);
        }

        public V put(K key, V value) {
            V lastValue = get(key);
            adapted.put(key, value);
            return lastValue;
        }

        public V remove(K key) {
            V lastValue = get(key);
            adapted.invalidate(key);
            return lastValue;
        }

        public void clear() {
            adapted.invalidateAll();
        }

        public int size() {
            return Ints.checkedCast(adapted.size());
        }

        public Set<K> keys() {
            return adapted.asMap().keySet();
        }

        public Collection<V> values() {
            return adapted.asMap().values();
        }

        /**
         * Get the name of this cache.
         * +         *
         * +         * This method will not be used by Shiro but is provided for developer
         * +         * convenience (testing, debugging...).
         * +         *
         * +         * @return the name given when {@link #createCache(String)} was called.
         * +
         */
        public String getName() {
            return name;
        }

        /**
         * Get the adapted Guava cache.
         * +         *
         * +         * This method will not be used by Shiro but is provided for developer
         * +         * convenience (testing, debugging...).
         * +         *
         * +         * @return the underlying, adapted Guava cache.
         * +
         */
        public com.google.common.cache.Cache<K, V> getAdapted() {
            return adapted;
        }

        @Override
        public String toString() {
            return "GuavaCache [" + adapted + "]";
        }
    }
}
