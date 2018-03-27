package org.upb.cryptoanalysis.core;

import crypto.HeadlessCryptoScanner;
import crypto.HeadlessCryptoScanner.CG;

import java.util.logging.Logger;

/**
 * Core analysis class that starts the crypto analysis independent of the build tool or cli.
 *
 * TODO rulesDir: Do we download standard rules somewhere (or include them) and allow configuring own rules in some directory?
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

        //TODO should we be using the headless version?
        //TODO one scanner per jar?

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

            @Override
            protected String getRulesDirectory() {
                return settings.getRulesDirectory().getAbsolutePath();
            }
        };
        sourceCryptoScanner.exec();
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
            if (!couldCreateReportDir){
                LOGGER.warning("Could not create directory to output issue.");
            }
        }
    }
}
