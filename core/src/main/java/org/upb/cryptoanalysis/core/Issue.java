package org.upb.cryptoanalysis.core;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class for a single issue, includes converting the issue to json
 *
 * Github API specification what issues should look like: https://developer.github.com/v3/issues/#create-an-issue
 * //TODO figure out where to check for existing github ids
 *
 */
public class Issue {

    private static ObjectMapper mapper = new ObjectMapper();

    private String title;
    /**
     * Explanation of the problem. Should include the line, so that
     * multiple occurrences of the same problem can be distinguished.
     */
    private String body;
    private List<String> labels;

    public Issue(String title, String body, List<String> labels) {
        this.title = title;
        this.body = body;
        this.labels = labels;
    }

    public String getJsonString(){
        String jsonInString = null;
        try {
            jsonInString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonInString;
    }
}
