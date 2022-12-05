package com.exercise.simplesearchengine.core.scoring;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreCalculator {

    public double score(List<String> doc, List<List<String>> docs, String term) {
        return countTf(doc, term) * countIdf(docs, term);

    }

    private double countTf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / doc.size();
    }

    private double countIdf(List<List<String>> docs, String term) {
        double n = 0;
        for (List<String> doc : docs) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        if (n == 0) {
            throw new RuntimeException("Calculation problem!");
        }
        return Math.log(docs.size() / n);
    }
}
