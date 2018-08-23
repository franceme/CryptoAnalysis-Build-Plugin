# CryptoAnalysis Build Plugin 

Build Plugins for the [CROSSINGTUD CryptoAnalysis](https://github.com/CROSSINGTUD/CryptoAnalysis).

This project provides a structure for integrating the same core analysis into CI toolchains 
using different build plugins or ways to start it (maven, gradle, ant and from command line).
It should include the option to create Github issues from its findings.

This project should make sure all dependencies are collected from the 
used mechanism and pass them to their location to the static analysis.

## Current State

 - CryptoAnalysis contains default rules, which are detected and used by this maven plugin
 - Maven plugin is started, but not finished. It can be used and settings are imported, like this:
 

    <plugin>
        <groupId>org.upb.cryptoanalysis</groupId>
        <artifactId>crypto-analysis-maven-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <executions>
            <execution>
                <id>check</id>
                <goals>
                    <goal>check</goal>
                </goals>
            </execution>
        </executions>
        <configuration>
            <rulesDirectory>customRules</rulesDirectory>
        </configuration>
    </plugin>

 - For maven plugin, there are still exceptions in the HeadlessCryptoScanner.
 - Ant task needs to be added. It needs to fill in the settings and start the analysis, similar to maven plugin.
 - Same thing holds for command line interface (if desired)
 - There is currently nothing read from or posted to github. The methods to do this are linked in TODOs in the
  correct code locations. We will need OAuth Tokens to do that.
  
