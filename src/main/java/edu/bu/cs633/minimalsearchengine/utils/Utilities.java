package edu.bu.cs633.minimalsearchengine.utils;

import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.ConstraintViolationException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Utilities {

    public static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList("i", "me", "my", "myself", "we",
            "our", "you", "you're", "you've", "you'll", "you'd", "he", "him", "his", "she", "she's", "her",
            "hers", "it", "it's", "its", "their", "theirs", "am", "is", "are", "was", "were", "be", "been",
            "being", "have", "has", "had", "do", "does", "did", "doing", "a", "an", "the", "and",
            "but", "if", "or", "as", "until", "while", "of", "at", "by", "for", "with", "to", "from", "up",
            "down", "in", "out", "on", "off", "here", "there", "when", "where", "why", "how", "all", "any",
            "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only",
            "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "don't",
            "should", "should've", "now", "ll", "aren", "aren't", "couldn", "couldn't", "didn", "didn't",
            "doesn", "doesn't", "hadn", "hadn't", "hasn", "hasn't", "haven", "haven't", "isn", "isn't",
            "mightn", "mightn't", "mustn", "mustn't", "needn", "needn't", "shan", "shan't", "shouldn",
            "shouldn't", "wasn", "wasn't", "weren", "weren't", "won", "won't", "wouldn", "wouldn't"));


    public static Set<String> cleanWordsFromQuery(final String query) {

        if (query == null || query.trim().length() == 0) {
            throw new ConstraintViolationException("invalid query");
        }

        Set<String> cleanedWords = new HashSet <>(Arrays.asList(query.trim().toLowerCase().split(" ")));
        cleanedWords.remove("");
        cleanedWords.removeAll(STOP_WORDS);

        return cleanedWords;
    }


}
