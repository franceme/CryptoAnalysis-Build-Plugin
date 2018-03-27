package org.upb.cryptoanalysis.core;

import crypto.HeadlessCryptoScanner;
import crypto.HeadlessCryptoScanner.CG;

import java.io.File;

/**
 * Core analysis class that starts the crypto analysis independent of the build tool or cli.
 *
 * TODO rulesDir: Do we download standard rules somewhere (or include them) and allow configuring own rules in some directory?
 */
public class Analysis {
    /* The settings for the analysis */
    private Settings settings;


    private String rulesDirectory;
    private String callGraph;
    private String reportsFolderParameter;
    private File reportsFolder;

    private File targetDir;
    private String classPath;
    private String artifactIdentifier;
    //TODO output directory

    public Analysis(Settings settings){
        final File classFolder = new File(targetDir.getAbsolutePath() + File.separator + "classes");

        //TODO should we be using the headless version?
        //TODO one scanner per jar?

        final String outputFile = settings.getIssueOutputDirectory() + File.separator + artifactIdentifier + ".txt";

        HeadlessCryptoScanner sourceCryptoScanner = new HeadlessCryptoScanner() {

            @Override
            protected String sootClassPath() {
                if (classPath == null) {
                    System.out.println("Potentially missing some dependencies");
                    return applicationClassPath();
                }
                return classPath;
            }

            @Override
            protected String applicationClassPath() {
                return classFolder.getAbsolutePath();
            }

            @Override
            protected String softwareIdentifier() {
                return artifactIdentifier;
            }

            @Override
            protected CG callGraphAlogrithm() {
                return getCgFromString();
            }

            @Override
            protected String getRulesDirectory() {
                return rulesDirectory;
            }

            @Override
            protected String getCSVOutputFile() {
                return null;
            }

            @Override
            protected String getOutputFile() {
                return outputFile;
            }
        };
        sourceCryptoScanner.exec();
    }

    //TODO scratch this?
    private CG getCgFromString(){
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


}
