package org.upb.cryptoanalysis.core;

import crypto.HeadlessCryptoScanner;
import crypto.HeadlessCryptoScanner.CG;

import java.util.logging.Logger;

/**
 * Core analysis class that starts the crypto analysis independent of the build tool or cli.
 */
public class Analysis {
    /* The settings for the analysis */
    private Settings settings;

    private final static Logger LOGGER = Logger.getLogger(Analysis.class.getName());


    public Analysis(Settings settings){
        this.settings = settings;
        createReportFolder();
    }

    public void start(){

        //TODO one scanner per jar?
        LOGGER.info("Starting analysis.");

        HeadlessCryptoScanner sourceCryptoScanner = new HeadlessCryptoScanner() {

            @Override
            protected String sootClassPath() { return applicationClassPath(); }

            @Override
            protected String applicationClassPath() {
                return settings.getApplicationClassPath().getAbsolutePath();
            }

            @Override
            protected String softwareIdentifier() { return settings.getSoftwareIdentifier(); }

            //TODO modify Scanner to provide json output options
            @Override
            protected String getOutputFile() { return null; }

            @Override
            protected CG callGraphAlogrithm() { return getCgFromString(settings.getCallGraph()); }

            @Override
            protected String getCSVOutputFile() { return null; }

            //TODO rulesDir: Include default rules in jar and allow configuring own rules in rulesDir parameter
            @Override
            protected String getRulesDirectory() {
                return settings.getRulesDirectory().getAbsolutePath();
            }
        };
        LOGGER.info("Initialized scanner");
        try{
            sourceCryptoScanner.exec();
        }
        catch (Exception e){
            LOGGER.info("Exception occurred while executing scanner: "+e.getMessage());
        }

    }

    //TODO scratch this?
    private CG getCgFromString(String callGraph){
        if (callGraph.equalsIgnoreCase("cha")) {
            return CG.CHA;
        } else if (callGraph.equalsIgnoreCase("spark")) {
            return CG.SPARK;
        } else if (callGraph.equalsIgnoreCase("spark-library")) {
            return CG.SPARK_LIBRARY;
        } else if (callGraph.equalsIgnoreCase("library")) {
            return CG.SPARK_LIBRARY;
        }
        return CG.CHA;
    }

    private void createReportFolder() {
        if (!settings.getIssueOutputDirectory().exists()) {
            boolean couldCreateReportDir = settings.getIssueOutputDirectory().mkdirs();
            if (!couldCreateReportDir) {
                LOGGER.warning("Could not create directory to output issue.");
            }
        }
    }
}
