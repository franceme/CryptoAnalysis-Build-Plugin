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

    /**
     * Should issues be posted to Github. If yes, {@link #githubUrl} should be set, otherwise nothing will happen.
     */
    private boolean postIssuesToGithub;

    /**
     * The local directory to which issues will be output.
     */
    private String issueOutputDirectory;

    public void setGithubUrl(String url){
        //TODO make regex pattern to check url
        this.githubUrl = url;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setPostIssuesToGithub(boolean shouldPost){
        this.postIssuesToGithub = shouldPost;
    }

    public boolean postIssuesToGithub() {
        return postIssuesToGithub;
    }

    public void setIssueOutputDirectory(String outputDirectory){
        this.issueOutputDirectory = outputDirectory;
    }

    public String getIssueOutputDirectory() {
        return issueOutputDirectory;
    }
}
