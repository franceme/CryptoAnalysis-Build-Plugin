package org.upb.cryptoanalysis.maven;

import org.apache.maven.execution.MavenSession;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

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
    private String reportsFolderParameter;
    private File reportsFolder;

    private Log log = getLog();

    public void execute() {
        log.info("CryptoAnalysis plugin started");
        //TODO Pass information to analysis
    }
}

