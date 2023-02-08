package edu.bu.cs633.minimalsearchengine.crawler;

import edu.bu.cs633.minimalsearchengine.models.dao.Page;
import edu.bu.cs633.minimalsearchengine.models.dao.Word;
import edu.bu.cs633.minimalsearchengine.utils.Utilities;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class MSEWebCrawler {

    private String url;

    public static final int MAX_LINKS = 3;
    private static final String DELIMITERS = "[\\s\\W]+";

    private static final Logger logger = Logger.getLogger(MSEWebCrawler.class.toString());

    public MSEWebCrawler() {
    }

    public MSEWebCrawler(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Page> crawl() throws IOException {

        Set<Page> pages = findLinks();

        if (pages == null || pages.size() == 0) {
            throw new IOException("No pages or invalid URL detected.");
        }

        return extractPagesAndWords(pages);
    }

    /**
     * Finds 2 additional URLs from the requested URL which needs to be crawled.
     * @return A set of Pages with URLs which needs to be crawled.
     * @throws IOException occurs when tries to read the URL
     */
    private Set<Page> findLinks() throws IOException {

        boolean isValidURL;

        Set<Page> pages = new HashSet<>();

        try {
            isValidURL = Utilities.isValidURL(getUrl());
        }
        catch(IOException ioException) {
            logger.severe("Exception occurred during URL validation. " + ioException.getMessage());
            throw ioException;
        }

        if (isValidURL) {

            pages.add(new Page(getUrl()));

            Document document = Jsoup.connect(getUrl()).get();
            Elements urls = document.select("a[href]");

            int urlCount = 0;

            int breakPointCountForAdditionalPages = 10;

            while (pages.size() != MAX_LINKS && urlCount < urls.size()) {

                Element page = urls.get(urlCount++);
                String url = page.attr( "abs:href");

                Page additionalPage = new Page(url);

                try {
                    if(Utilities.isValidURL(additionalPage.getUrl()) && !pages.contains(additionalPage)) {

                        pages.add(additionalPage);

                        urlCount++;
                    }
                }
                catch(IOException ex) {
                    urlCount++;
                    continue;
                }

                if (urlCount == breakPointCountForAdditionalPages) {
                    break;
                }
            }
        }

        return pages;
    }

    /**
     * Given a set of pages (URLs) to crawl and extract words from each page (url).
     * @param pages pages to be crawled
     * @return set of pages which are crawled and populated with words
     * @throws IOException occurs when connecting and extracting data from a page
     */
    private Set<Page> extractPagesAndWords(Set<Page> pages) throws IOException {

        for (Page page : pages) {

            Document document = Jsoup.connect(page.getUrl())
                    .followRedirects(true)
                    .ignoreHttpErrors(true)
                    .maxBodySize(0)
                    .timeout(0)
                    .get();

            page.setTitle(document.title());
            page.setUrl(document.baseUri());

            extractWords(page, document.body().text());
        }

        return pages;
    }

    /**
     * Given a page (url) and body (html) extract words from the body and assign them to the page itself
     * @param page page to which extracted words will be linked  to
     * @param body a html body to be extracted and words to be retrieved
     */
    private void extractWords(Page page, String body) {

        String[] words = body.replaceAll("\\p{IsPunctuation}", "").trim().toLowerCase().split(DELIMITERS);

        Map<String, Word> wordsMap = new HashMap<>();

        for(String word : words) {

            if (Utilities.STOP_WORDS.contains(word)) {
                continue;
            }

            if (word.trim().length() == 0) {
                continue;
            }

            Word wordObj = new Word();

            wordObj.setWord(word);
            wordObj.setFrequency(0);
            wordObj.setPage(page);

            Word mappedWord = wordsMap.getOrDefault(word, wordObj);
            mappedWord.setFrequency(mappedWord.getFrequency() + 1);

            wordsMap.put(word, mappedWord);
        }

        page.setWords(new HashSet<>(wordsMap.values()));
    }
}

