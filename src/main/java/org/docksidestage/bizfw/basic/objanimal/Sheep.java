package org.docksidestage.bizfw.basic.objanimal;

/**
 * @author winkichanwi
 */
public class Sheep extends Animal {

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public Sheep() {
    }

    @Override
    protected int getInitialHitPoint() { return 5; }

    // ===================================================================================
    //                                                                               Bark
    //                                                                              ======
    protected String getBarkWord() { return "meh"; }
}
