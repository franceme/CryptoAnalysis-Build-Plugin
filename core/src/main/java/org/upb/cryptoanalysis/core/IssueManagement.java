package org.upb.cryptoanalysis.core;

import java.util.ArrayList;
import java.util.List;

public class IssueManagement {
    private List<Issue> knownIssues = new ArrayList<Issue>();
    private List<Issue> detectedIssues = new ArrayList<Issue>();

    public void getKnownIssues(){
        //TODO Get known issues like this https://developer.github.com/v3/issues/#list-issues-for-a-repository
    }

    public void sendUnknownIssuesToGithub(){
        //TODO get oauth token so that we can send github issues like https://api.github.com/?access_token=OAUTH-TOKEN
        // Could be set as environment variable in build environment, COGNICRYPT_OAUTH-TOKEN
        //TODO Send all issues which were not known to https://developer.github.com/v3/issues/#create-an-issue
    }

}
