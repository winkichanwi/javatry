/*
 * Copyright 2019-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.docksidestage.bizfw.basic.buyticket;

/**
 * @author winkichanwi
 */
public class MultipleDayTicket implements Ticket {

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private final int displayPrice;
    private final String ticketType;
    private int remainCheckIn;
    private boolean alreadyIn;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public MultipleDayTicket(int displayPrice, int maxCheckIn) {
        this.remainCheckIn = maxCheckIn;
        this.displayPrice = displayPrice;
        this.ticketType = initTicketType();
    }

    private String initTicketType() {
        return remainCheckIn + "Day";
    }

    // ===================================================================================
    //                                                                             In Park
    //                                                                             =======
    public void doInPark() {
        if (remainCheckIn == 0) {
            throw new IllegalStateException("Check-in time has been used up already: displayedPrice=" + displayPrice);
        }
        if (alreadyIn) {
            throw new IllegalStateException("Already in park by this ticket: displayedPrice=" + displayPrice);
        }
        remainCheckIn--;
        alreadyIn = true;
    }

    // ===================================================================================
    //                                                                          Leave Park
    //                                                                          ==========

    public void leavePark() {
        if (!alreadyIn) {
            throw new IllegalStateException("Already left park by this ticket: displayedPrice=" + displayPrice);
        }
        alreadyIn = false;
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getDisplayPrice() {
        return displayPrice;
    }

    public boolean isAlreadyIn() { return alreadyIn; }

    public int getRemainCheckIn() { return remainCheckIn; }

    public String getTicketType() { return ticketType; }
}
