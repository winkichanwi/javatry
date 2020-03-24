package org.docksidestage.javatry.basic.st6.os;

/**
 * @author winkichan
 */
public class St6Mac extends St6OperationSystem {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final String loginId;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public St6Mac(String loginId) {
        this.loginId = loginId;
    }

    // ===================================================================================
    //                                                                      User Directory
    //                                                                      ==============
    @Override
    protected String getFileSeparator() { return "/"; }

    @Override
    protected String getUserDirectory() { return "/Users/" + loginId; }
}
