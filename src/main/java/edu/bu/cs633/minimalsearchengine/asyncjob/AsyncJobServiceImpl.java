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
import java.util.logging.Logger;

@Service
public class AsyncJobServiceImpl implements AsyncJobService {

    private final ApplicationContext applicationContext;
    private final AdminService adminService;
    private final PageService pageService;

    private final WordService wordService;

    private static final Logger logger = Logger.getLogger(AsyncJobServiceImpl.class.getName());

    @Autowired
    public AsyncJobServiceImpl(ApplicationContext applicationContext, AdminService adminService,
                               PageService pageService, WordService wordService) {

        this.applicationContext = applicationContext;
        this.adminService = adminService;
        this.pageService = pageService;
        this.wordService = wordService;
    }

    @Override
    @Async(value = "threadPoolTaskExecutor")
    public void crawl(final Admin admin, final String url) {

        logger.info("Job started to crawl URL= " + url + " by " + admin.getUsername());

        MSEWebCrawler MSEWebCrawler = applicationContext.getBean(MSEWebCrawler.class);
        MSEWebCrawler.setUrl(url);

        try {
            Set<Page> pages = MSEWebCrawler.crawl();

            pages.forEach(page -> {
                Page updatedPage = pageService.save(page);
                wordService.saveAllWords(page.getWords());
                adminService.updateIndexingHistory(admin.getAdminId(), updatedPage.getPageId());
            });

            logger.info("Indexing have been updated by " + admin.getUsername() + " with " + pages.size()  + " urls.");
        }
        catch(Exception ex) {
            logger.severe(ex.getMessage());
        }

        logger.info("Job finished.");
    }

}