package edu.bu.cs633.minimalsearchengine.models.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linkedin.urls.Url;
import com.linkedin.urls.detection.UrlDetector;
import com.linkedin.urls.detection.UrlDetectorOptions;

import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.InvalidURLException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "pages")
public class Page implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "page_id", nullable = false, updatable = false, unique = true)
    private Integer pageId;

    @Column(name = "title", nullable = false, length = 5000)
    private String title;

    @Column(name = "url", nullable = false, updatable = false, unique = true, length = 1000)
    private String url;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "page")
    private Set<Word> words;

    @JsonIgnore
    @OneToMany(mappedBy = "page", cascade = CascadeType.ALL)
    private Set<IndexingHistory> indexingHistories;

    public Page() {
    }

    public Page(String url) {
        this.url = url;
    }

    public Page(Integer pageId, String title, String url, Set <Word> words, Set <IndexingHistory> indexingHistories) {
        this.pageId = pageId;
        this.title = title;
        this.url = url;
        this.words = words;
        this.indexingHistories = indexingHistories;
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Word> getWords() {
        return words;
    }

    public void setWords(Set<Word> words) {
        this.words = words;
    }

    public Set<IndexingHistory> getIndexingHistories() {
        return indexingHistories;
    }

    public void setIndexingHistories(Set<IndexingHistory> indexingHistories) {
        this.indexingHistories = indexingHistories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;

        Page page = (Page) o;
        return this.getHostAndPath().equals(page.getHostAndPath());
    }

    @JsonIgnore
    public String getHostAndPath() {
        UrlDetector parser = new UrlDetector(url, UrlDetectorOptions.Default);

        if (parser.detect().size() == 0) {
            throw new InvalidURLException("invalid URL detected.");
        }

        Url link = parser.detect().get(0);
        String host = link.getHost().replace("www.", "").trim();
        String path = link.getPath().replace(link.getFragment(), "").trim();
        if (path.endsWith("/")) path = path.substring(0, path.length()-1);
        return host + path;
    }

    @Override
    public int hashCode() {
        return 231 * Objects.hash(this.getHostAndPath());
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageId=" + pageId +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", words=" + words +
                '}';
    }
}
