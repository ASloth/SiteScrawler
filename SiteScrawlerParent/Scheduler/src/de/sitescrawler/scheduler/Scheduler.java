package de.sitescrawler.scheduler;

import java.util.logging.Logger;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

import de.sitescrawler.crawler.interfaces.ICrawlerLaufService;

@Singleton
public class Scheduler
{
    private static final Logger LOGGER = Logger.getLogger("de.sitescrawler.logger");

    @Inject
    private ICrawlerLaufService crawlerLaufService;

    @Schedule(second = "0", minute = "*/2", hour = "*", persistent = false)
    public void starteCrawl()
    {
        Scheduler.LOGGER.info("Scheduler starte Crawlvorgang...");
        this.crawlerLaufService.crawl();
    }
}
