package org.upb.cryptoanalysis.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

public class CryptoAnalysisMojo extends AbstractMojo {

    private Log log = getLog();

    /**
     * Runs the CogniCrypt static analysis to
     * check used security for potential security problems.
     *
     */
    @Mojo( name = "cryptoanalysis")
    public void execute() throws MojoExecutionException
    {
        log.info("CryptoAnalysis plugin started");
    }
}

