package org.upb.cryptoanalysis.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to store settings for the analysis, e.g.,
 * whether to post the issues to Github or to which URL.
 */
public class Settings {

    private static final Logger LOGGER = LoggerFactory.getLogger(Settings.class);

    /**
     * The tag on github with which we annotate all issues, so we and users can easily
     * filter them.
     */
    public static final String GITHUB_TAG ="CryptoAnalysis";

    /**
     * Used to get existent issues and to post new issues.
     */
    private String githubUrl;

    private boolean postIssuesToGithub;

    public void setGithubUrl(String url){
        //TODO make regex pattern to check url
        githubUrl = url;
    }

    public void setPostIssuesToGithub(boolean shouldPost){
        postIssuesToGithub = shouldPost;
    }
}
