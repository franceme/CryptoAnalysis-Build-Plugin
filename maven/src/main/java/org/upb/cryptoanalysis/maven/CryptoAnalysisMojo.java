package org.upb.cryptoanalysis.maven;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import org.upb.cryptoanalysis.core.Settings;

import java.io.File;
import java.net.URL;

/**
 * Runs the CogniCrypt static analysis to
 * check used security for potential security problems.
 *
 * @goal cyptoanalysis
 * @phase verify
 */
@Mojo( name = "cryptoanalysis")
public class CryptoAnalysisMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(defaultValue = "${session}", readonly = true)
    private MavenSession session;

    @Parameter(property = "check.rulesDirectory")
    private String rulesDirectory;

    @Parameter(property = "check.callGraph", defaultValue = "CHA")
    private String callGraph;

    @Parameter(property = "check.reportsDirectory", defaultValue = "cognicrypt-reports")
    private File reportsFolder;

    @Parameter(property = "check.postIssuesToGithub", defaultValue = "false")
    private boolean postIssuesToGithub;

    @Parameter(property = "check.githubRepoUrl")
    private URL githubRepoUrl;

    private Log log = getLog();

    public void execute() throws  MojoExecutionException{
        log.info("CryptoAnalysis plugin started");
        Settings settings = new Settings();
        if (postIssuesToGithub){
            settings.setPostIssuesToGithub(postIssuesToGithub);
            if (!settings.setGithubUrl(githubRepoUrl)){
                throw new MojoExecutionException("Analysis should post issues to github, but github url is invalid.");
            }
        }
        if (settings.setIssueOutputDirectory(reportsFolder)){
            throw new MojoExecutionException("The plugin cannot write into the report folder.");
        }


        //TODO Pass information to analysis
    }
}

