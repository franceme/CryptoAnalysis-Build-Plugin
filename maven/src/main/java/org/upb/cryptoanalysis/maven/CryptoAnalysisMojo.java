package org.upb.cryptoanalysis.maven;

import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import org.upb.cryptoanalysis.core.Analysis;
import org.upb.cryptoanalysis.core.Settings;

import java.io.File;
import java.net.URL;

/**
 * Runs the CogniCrypt static analysis to
 * check used security for potential security problems.
 */
@Mojo(
        name = "check",
        defaultPhase = LifecyclePhase.VERIFY
)
public class CryptoAnalysisMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true)
    private MavenProject project;

    @Parameter(property = "check.rulesDirectory")
    private File rulesDirectory;

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
        try{
            settings = fillSettings(settings);
        }
        catch (MojoExecutionException m){
            throw new MojoExecutionException("Error filling settings: " + m.getMessage());
        }
        log.debug("Settings successfully filled.");

        try{
            log.debug("Trying to initialize analysis with settings.");
            Analysis analysis = new Analysis(settings);
            log.debug("Analysis initialized");
            analysis.start();
        } catch (Exception e){
            log.debug("Exception occurred in analysis: " + e.getMessage());
        }
    }

    private Settings fillSettings(Settings settings) throws MojoExecutionException {
        if (postIssuesToGithub){
            settings.setPostIssuesToGithub(postIssuesToGithub);
            if (!settings.setGithubUrl(githubRepoUrl)){
                throw new MojoExecutionException("Analysis should post issues to github, but github url is invalid.");
            }
        }
        settings.setIssueOutputDirectory(reportsFolder);
        settings.setCallGraph(callGraph);
        settings.setApplicationClassPath(getClassFolderFromModel());
        settings.setRulesDirectory(rulesDirectory);
        settings.setSoftwareIdentifier(project.getArtifactId());
        return settings;
    }

    private File getClassFolderFromModel(){
        Model model = project.getModel();
        Build build = model.getBuild();
        File targetDir = new File(build.getDirectory());
        return new File(targetDir.getAbsolutePath() + File.separator + "classes");
    }
}

