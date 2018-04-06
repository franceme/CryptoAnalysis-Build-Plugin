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

    private static final Logger LOGGER = Logger.getLogger(Analysis.class.getName());


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

            @Override
            protected String getOutputFile() { return null; }

            @Override
            protected CG callGraphAlogrithm() { return getCgFromString(settings.getCallGraph()); }

            @Override
            protected String getCSVOutputFile() { return null; }

            //TODO test json output dir
            @Override
            protected String getJSONOutputDir() {
                return settings.getIssueOutputDirectory().getAbsolutePath();
            }

            //TODO check if custom directory works
            //TODO check if defaultRules from Jar work
            @Override
            protected String getRulesDirectory() {
                if (settings.getRulesDirectory() != null && settings.getRulesDirectory().exists()){
                    return settings.getRulesDirectory().getAbsolutePath();
                } else {
                    return this.getClass().getResource("defaultRules").getFile();
                }
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
