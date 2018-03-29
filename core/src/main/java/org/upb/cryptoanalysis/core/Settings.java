package org.upb.cryptoanalysis.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;

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
    private URL githubUrl;

    /**
     * Should issues be posted to Github. If yes, {@link #githubUrl} should be set, otherwise nothing will happen.
     */
    private boolean postIssuesToGithub;

    /**
     * The local directory to which issues will be output.
     */
    private File issueOutputDirectory;

    /**
     * The call graph algorithm to use
     */
    private String callGraph;

    /**
     * Directory in which the compiled classes for the application are
     */
    private File applicationClassPath;

    /**
     * Directory in which CrySL rules are located
     */
    private File rulesDirectory;

    /**
     * String which can be used to identify the analyzed software
     */
    private String softwareIdentifier;


    public boolean setGithubUrl(URL url){
        //TODO make regex pattern to check url
        this.githubUrl = url;
        return true;
    }

    public URL getGithubUrl() {
        return githubUrl;
    }

    public void setPostIssuesToGithub(boolean shouldPost){
        this.postIssuesToGithub = shouldPost;
    }

    public boolean postIssuesToGithub() {
        return postIssuesToGithub;
    }

    public void setIssueOutputDirectory(File outputDirectory){
        this.issueOutputDirectory = outputDirectory;
    }

    public File getIssueOutputDirectory() {
        return issueOutputDirectory;
    }

    public String getCallGraph() {
        return callGraph;
    }

    public void setCallGraph(String callGraph) {
        this.callGraph = callGraph;
    }

    public File getApplicationClassPath() {
        return applicationClassPath;
    }

    public void setApplicationClassPath(File applicationClassPath) {
        this.applicationClassPath = applicationClassPath;
    }

    public File getRulesDirectory() {
        return rulesDirectory;
    }

    public void setRulesDirectory(File rulesDirectory) {
        this.rulesDirectory = rulesDirectory;
    }

    public String getSoftwareIdentifier() {
        return softwareIdentifier;
    }

    public void setSoftwareIdentifier(String softwareIdentifier) {
        this.softwareIdentifier = softwareIdentifier;
    }
}
