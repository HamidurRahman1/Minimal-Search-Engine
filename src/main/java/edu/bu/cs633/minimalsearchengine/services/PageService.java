package edu.bu.cs633.minimalsearchengine.services;

import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.ConstraintViolationException;
import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.InvalidURLException;
import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.NotFoundException;
import edu.bu.cs633.minimalsearchengine.models.dao.Page;
import edu.bu.cs633.minimalsearchengine.repositories.PageRepository;
import edu.bu.cs633.minimalsearchengine.utils.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PageService {

    private final PageRepository pageRepository;

    @Autowired
    public PageService(final PageRepository pageRepository) {
        this.pageRepository = pageRepository;
    }

    public Set<Page> getPagesByQuery(final String query) {

        if (query == null || query.trim().length() == 0) {
            throw new ConstraintViolationException("invalid query or empty query");
        }

        Set<String> goodWords = Utilities.cleanWordsFromQuery(query);

        if (goodWords.size() == 0) {
            throw new ConstraintViolationException("query only contained stop words. Not enough words to query database.");
        }

        Set<Page> pages = pageRepository.getPagesByWords(goodWords);

        if (pages == null || pages.size() == 0) {
            throw new NotFoundException("No pages found matching the query.");
        }

        return pages;
    }

    public Page save(final Page page) {
        return pageRepository.save(page);
    }

    public Set<Page> getMatchingURLsIfAny(final String url) {

        if (url == null || url.trim().length() == 0) {
            throw new InvalidURLException("given url is invalid.");
        }

        final String hostPath = "%" + new Page(url.trim()).getHostAndPath() + "%";

        return pageRepository.findByUrlLikeIgnoreCase(hostPath);
    }
}
