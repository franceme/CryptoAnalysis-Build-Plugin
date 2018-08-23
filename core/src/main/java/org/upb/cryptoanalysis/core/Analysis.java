package org.upb.cryptoanalysis.core;

import crypto.HeadlessCryptoScanner;
import crypto.HeadlessCryptoScanner.CG;

import java.io.File;
import java.net.URL;
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
            String rulesDir;

            @Override
            protected String sootClassPath() { return applicationClassPath(); }

            @Override
            protected String applicationClassPath() {
                return settings.getApplicationClassPath();
            }

            @Override
            protected String softwareIdentifier() { return settings.getSoftwareIdentifier(); }

            //TODO test json output dir
            @Override
            protected String getOutputFolder(){ return settings.getIssueOutputDirectory().getAbsolutePath(); };

            @Override
            protected CG callGraphAlogrithm() { return getCgFromString(settings.getCallGraph()); }

            @Override
            protected String getCSVOutputFile() { return null; }

            @Override
            protected String getRulesDirectory() {
                if (rulesDir == null){
                    System.out.println("Searching rules directory");
                    File settingsRulesDirectory = settings.getRulesDirectory();
                    System.out.println("Is the rules directory specified in the setting? " +
                            ((settingsRulesDirectory == null) ? "no" : "yes"));
                    if (settingsRulesDirectory != null && settingsRulesDirectory.exists()){
                        System.out.println("Found custom directory with rules: " + settingsRulesDirectory.getAbsolutePath());
                        rulesDir =  settingsRulesDirectory.getAbsolutePath();
                    } else {
                        System.out.println("Using default rules.");
                        URL defaultRules = this.getClass().getClassLoader().getResource("rules");
                        if (defaultRules == null || defaultRules.getFile() == null){
                            System.out.println("Default rules not included in jar.");
                        }
                        rulesDir = defaultRules.getFile();
                    }
                }
                return rulesDir;
            }
        };
        LOGGER.info("Initialized scanner");
        try{
            sourceCryptoScanner.exec();
        }
        catch (Exception e){
            LOGGER.info("Exception occurred while executing scanner: "+ e);
        }
    }

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
