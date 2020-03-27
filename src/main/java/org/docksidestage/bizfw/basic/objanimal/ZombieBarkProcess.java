package org.docksidestage.bizfw.basic.objanimal;

/**
 * @author wingin.chan
 */
public class ZombieBarkProcess extends BarkProcess {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private Zombie zombie;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    ZombieBarkProcess(Zombie zombie) {
        super(zombie);
        this.zombie = zombie;
    }

    @Override
    protected void breatheIn() {
        super.breatheIn();
        zombie.zombieDiary.countBreatheIn();
    }
}
