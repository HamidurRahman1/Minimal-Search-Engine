package edu.bu.cs633.minimalsearchengine.models.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "words")
public class Word implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id", nullable = false, updatable = false, unique = true)
    private Integer  wordId;

    @Column(name = "word", nullable = false, length = 1000)
    private String word;

    @Column(name = "frequency", nullable = false)
    private Integer frequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    private Page page;

    public Word() {
    }

    public Word(Integer wordId, String word, Integer frequency, Page page) {
        this.wordId = wordId;
        this.word = word;
        this.frequency = frequency;
        this.page = page;
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        Word thatWord = (Word) o;
        return Objects.equals(word, thatWord.word)
                && Objects.equals(page, thatWord.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word, page);
    }

    @Override
    public String toString() {
        return "Word{" +
                "wordId=" + wordId +
                ", word='" + word + '\'' +
                ", frequency=" + frequency +
                ", page=" + page.getUrl() +
                '}';
    }
}

