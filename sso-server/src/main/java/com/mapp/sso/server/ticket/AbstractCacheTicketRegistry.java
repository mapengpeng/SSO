package com.mapp.sso.server.ticket;

import com.mapp.sso.server.cache.Cache;
import com.mapp.sso.server.cache.CacheManager;

import java.util.Collection;

/**
 * 带缓存的TicketRegistry
 *
 * @author mapp
 */
public abstract class AbstractCacheTicketRegistry implements TicketRegistry {

    private CacheManager<String, Ticket> cacheManager;
    private Cache<String, Ticket> cache;
    private final String ticketCacheName = "ticktCache";

    public AbstractCacheTicketRegistry() {

    }

    public AbstractCacheTicketRegistry(CacheManager<String, Ticket> cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void addTicket(Ticket ticket) {
        Cache<String, Ticket> cache = getCache();
        cache.put(ticket.getId(), ticket);
    }

    @Override
    public Ticket getTicket(String id) {
        Cache<String, Ticket> cache = getCache();
        return cache.get(id);
    }

    @Override
    public <T extends Ticket> T getTicket(String id, Class<T> tClass) {
        return (T) getTicket(id);
    }

    @Override
    public boolean deleteTicket(String id) {
        Cache<String, Ticket> cache = getCache();
        return cache.remove(id) != null;
    }

    @Override
    public Collection<Ticket> getTickets() {
        return getCache().values();
    }

    public CacheManager<String, Ticket> getCacheManager() {
        return cacheManager;
    }

    public void setCacheManager(CacheManager<String, Ticket> cacheManager) {
        this.cacheManager = cacheManager;
    }

    public Cache<String, Ticket> getCache() {
        if (cache == null) {
            cache = getCacheManager().getCache(ticketCacheName);
        }
        return cache;
    }

    public void setCache(Cache<String, Ticket> cache) {
        this.cache = cache;
    }

    public String getTicketCacheName() {
        return ticketCacheName;
    }
}
