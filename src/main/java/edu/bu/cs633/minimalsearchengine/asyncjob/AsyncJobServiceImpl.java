package edu.bu.cs633.minimalsearchengine.asyncjob;

import edu.bu.cs633.minimalsearchengine.crawler.MSEWebCrawler;
import edu.bu.cs633.minimalsearchengine.models.dao.Admin;
import edu.bu.cs633.minimalsearchengine.models.dao.Page;
import edu.bu.cs633.minimalsearchengine.services.AdminService;
import edu.bu.cs633.minimalsearchengine.services.PageService;
import edu.bu.cs633.minimalsearchengine.services.WordService;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

@Service
public class AsyncJobServiceImpl implements AsyncJobService {

    private final ApplicationContext applicationContext;
    private final AdminService adminService;
    private final PageService pageService;
    private final WordService wordService;
    private final Logger logger;

    @Autowired
    public AsyncJobServiceImpl(ApplicationContext applicationContext, AdminService adminService,
                               PageService pageService, WordService wordService, Logger logger) {

        this.applicationContext = applicationContext;
        this.adminService = adminService;
        this.pageService = pageService;
        this.wordService = wordService;
        this.logger = logger;
    }

    @Override
    @Async(value = "threadPoolTaskExecutor")
    public void crawl(final Admin admin, final String url) {

        logger.info("Job started to crawl URL= " + url + " by " + admin.getUsername());

        MSEWebCrawler MSEWebCrawler = applicationContext.getBean(MSEWebCrawler.class);
        MSEWebCrawler.setUrl(url);

        try {
            Set<Page> pages = MSEWebCrawler.crawl();

            AtomicInteger addCount = new AtomicInteger();

            pages.forEach(page -> {
                Set<Page> existingPages = pageService.getMatchingURLsIfAny(page.getUrl());

                if (existingPages.size() == 0) {
                    Page updatedPage = pageService.save(page);
                    wordService.saveAllWords(page.getWords());
                    adminService.updateIndexingHistory(admin.getAdminId(), updatedPage.getPageId());
                    addCount.getAndIncrement();
                }
                else {
                    logger.info("skipping URL=" + page.getUrl() + " as it already exists.");
                }
            });

            logger.info("Indexing have been updated by " + admin.getUsername() + " with " + addCount.get()  + " urls " +
                    "out of expected " + pages.size() + " urls.");
        }
        catch(Exception ex) {
            logger.severe(ex.getMessage());
        }

        logger.info("Job finished.");
    }

}