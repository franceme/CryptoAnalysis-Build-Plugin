package org.upb.cryptoanalysis.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;

public class CryptoAnalysisMojo extends AbstractMojo {

    /**
     * Runs the CogniCrypt static analysis to
     * check used security for potential security problems.
     *
     */
    @Mojo( name = "CryptoAnalysis")
    public void execute() throws MojoExecutionException
    {

    }
}

