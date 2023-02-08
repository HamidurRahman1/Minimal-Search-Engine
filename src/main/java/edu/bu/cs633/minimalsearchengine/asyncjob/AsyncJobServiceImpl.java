package edu.bu.cs633.minimalsearchengine.asyncjob;

import edu.bu.cs633.minimalsearchengine.models.dao.Admin;
import edu.bu.cs633.minimalsearchengine.services.AdminService;
import edu.bu.cs633.minimalsearchengine.services.PageService;
import edu.bu.cs633.minimalsearchengine.services.WordService;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class AsyncJobServiceImpl implements AsyncJobService {

    private final ApplicationContext applicationContext;
    private final AdminService adminService;
    private final PageService pageService;

    private final WordService wordService;

    @Autowired
    public AsyncJobServiceImpl(ApplicationContext applicationContext, AdminService adminService,
                               PageService pageService, WordService wordService) {

        this.applicationContext = applicationContext;
        this.adminService = adminService;
        this.pageService = pageService;
        this.wordService = wordService;
    }

    private static final Logger logger = Logger.getLogger(AsyncJobServiceImpl.class.getName());

    @Override
    @Async(value = "threadPoolTaskExecutor")
    public void crawl(final Admin admin, final String url) {

        logger.info("Job started to crawl URL= " + url + " by " + admin.getUsername());

        // TBI

        logger.info("Job finished.");
    }

}