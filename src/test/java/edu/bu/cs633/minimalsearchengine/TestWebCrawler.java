package edu.bu.cs633.minimalsearchengine;

import edu.bu.cs633.minimalsearchengine.crawler.MSEWebCrawler;
import edu.bu.cs633.minimalsearchengine.models.dao.Page;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

public class TestWebCrawler {

    @Test
    public void extractPagesAndWordsTest() {

        MSEWebCrawler mseWebCrawler = new MSEWebCrawler();
        mseWebCrawler.setUrl("https://www.w3schools.com/html/");

        try {
            Set<Page> pages = mseWebCrawler.crawl();

            Assert.assertNotNull(pages);
            Assert.assertTrue(pages.size() != 0);

            pages.forEach(page -> {
                Assert.assertNotNull(page.getWords());
                Assert.assertTrue(page.getWords().size() != 0);
            });
        }
        catch (Exception ex) {
            Assert.assertEquals(IOException.class.getName(), ex.getClass().getName());
        }
    }

    @Test(expectedExceptions = {IOException.class})
    public void extractPagesAndWordsForInvalidURLTest() throws IOException {

        MSEWebCrawler mseWebCrawler = new MSEWebCrawler();
        mseWebCrawler.setUrl("httw.w3schools.com/html/");

        mseWebCrawler.crawl();
    }

}
