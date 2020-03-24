package org.docksidestage.javatry.basic.st6.os;

public class St6OldWindows extends St6OperationSystem {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final String loginId;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public St6OldWindows(String loginId) {
        this.loginId = loginId;
    }

    // ===================================================================================
    //                                                                      User Directory
    //                                                                      ==============
    @Override
    protected String getFileSeparator() { return "\\"; }

    @Override
    protected String getUserDirectory() { return "/Documents and Settigs/" + loginId; }
}
