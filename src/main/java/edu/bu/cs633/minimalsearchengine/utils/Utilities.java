package edu.bu.cs633.minimalsearchengine.utils;

import edu.bu.cs633.minimalsearchengine.exceptions.customExceptions.ConstraintViolationException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public final class Utilities {

    public static final Set<String> STOP_WORDS = new HashSet<>(Arrays.asList("i", "me", "my", "myself", "we",
            "our", "you", "youre", "youve", "youll", "youd", "he", "him", "his", "she", "shes", "her",
            "hers", "it", "its", "its", "their", "theirs", "am", "is", "are", "was", "were", "be", "been",
            "being", "have", "has", "had", "do", "does", "did", "doing", "a", "an", "the", "and",
            "but", "if", "or", "as", "until", "while", "of", "at", "by", "for", "with", "to", "from", "up",
            "down", "in", "out", "on", "off", "here", "there", "when", "where", "why", "how", "all", "any",
            "both", "each", "few", "more", "most", "other", "some", "such", "no", "nor", "not", "only",
            "own", "same", "so", "than", "too", "very", "s", "t", "can", "will", "just", "don", "dont",
            "should", "shouldve", "now", "ll", "aren", "arent", "couldn", "couldnt", "didn", "didnt",
            "doesn", "doesnt", "hadn", "hadnt", "hasn", "hasnt", "haven", "havent", "isn", "isnt",
            "mightn", "mightnt", "mustn", "mustnt", "needn", "neednt", "shan", "shant", "shouldn",
            "shouldnt", "wasn", "wasnt", "weren", "werent", "won", "wont", "wouldn", "wouldnt"));


    /**
     * Takes a string and replaces all punctuations with empty spaces and split in white spaces. After split all the
     * stop words are removed and rest of the words are returned as a set.
     * @param query string to process
     * @return words that are not punctuations and stop words
     */
    public static Set<String> cleanWordsFromQuery(final String query) {

        if (query == null || query.trim().length() == 0) {
            throw new ConstraintViolationException("Invalid query or empty query");
        }

        Set<String> cleanedWords = new HashSet <>(Arrays.asList(query.replaceAll("\\p{IsPunctuation}", "").trim().toLowerCase().split(" ")));
        cleanedWords.remove("");
        cleanedWords.removeAll(STOP_WORDS);

        return cleanedWords;
    }


    /**
     * Takes a URL and tries to connect to the website. Returns true if connected, otherwise throws exceptions
     * @param url url to connect to
     * @return true if connection is successful
     * @throws IOException if not able to connect
     */
    public static boolean isValidURL(final String url) throws IOException {

        boolean isValid = false;

        try {
            new URL(url);
            Connection connection = Jsoup.connect(url);
            connection.get();
            isValid = true;
        }
        catch (MalformedURLException ex) {
            if(ex.getMessage().contains("unknown protocol")) {
                throw new ProtocolException(url + " - contains unknown protocol which is - " +
                        ex.getMessage().split(":")[1]);
            }
            if(ex.getMessage().contains("no protocol")) {
                throw new ProtocolException(url + " - does not contain a valid protocol or format");
            }
        }
        catch (UnknownHostException ex) {
            throw new UnknownHostException("Invalid host name provided, host: " + ex.getMessage());
        }
        catch (IllegalArgumentException ex) {
            if(!url.matches("^http(s)?://.+"))
                throw new UnknownHostException("Missing // before hostname or invalid host name provided, url: " + url);
        }
        catch (ConnectException ex) {
            if(ex.getMessage().contains("refused")) {
                throw new ConnectException("Invalid host name provided. Connection cannot be established to this host: "
                        + url.split("^http(s)?:(//)?")[1]);
            }
        }

        return isValid;
    }
}
