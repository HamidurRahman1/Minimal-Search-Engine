package edu.bu.cs633.minimalsearchengine.services;

import edu.bu.cs633.minimalsearchengine.models.dao.Word;
import edu.bu.cs633.minimalsearchengine.repositories.WordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class WordService {

    private final WordRepository wordRepository;

    @Autowired
    public WordService(final WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    public void saveAllWords(final Set<Word> words) {
        wordRepository.saveAll(words);
    }
}
