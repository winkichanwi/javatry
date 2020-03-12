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
 * @author jflute
 * @author winkichanwi
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int MAX_QUANTITY = 10;
    private static final int ONE_DAY_PRICE = 7400; // when 2019/06/15
    private static final int TWO_DAY_PRICE = 13200;
    private static final int FOUR_DAY_PRICE = 22400;

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private int quantity = MAX_QUANTITY;
    private Integer salesProceeds;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    public TicketBuyResult buyOneDayPassport(int handedMoney) {
        TicketBuyResult result = buyPassport(handedMoney, ONE_DAY_PRICE, 1);
        return result;
    }

    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        TicketBuyResult result = buyPassport(handedMoney, TWO_DAY_PRICE, 2);
        return result;
    }

    public TicketBuyResult buyFourDayPassport(int handedMoney) {
        TicketBuyResult result = buyPassport(handedMoney, FOUR_DAY_PRICE, 4);
        return result;
    }

    private TicketBuyResult buyPassport(int handedMoney, int price, int salesQuantity) {
        checkQuantity();
        checkHandedMoney(handedMoney, price);
        addSalesProceeds(price);
        reduceQuantity(salesQuantity);

        Ticket ticket;
        if (salesQuantity == 1) {
            ticket = new OneDayTicket(price);
        } else {
            ticket =  new MultipleDayTicket(price, salesQuantity);
        }
        int change = handedMoney - price;
        return new TicketBuyResult(ticket, change);
    }

    private void checkQuantity() {
        if (quantity <= 0) {
            throw new TicketSoldOutException("Sold out");
        }
    }

    private void checkHandedMoney(int handedMoney, int price) {
        if (handedMoney < price) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
    }

    private void addSalesProceeds(int price) {
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + price;
        } else {
            salesProceeds = price;
        }
    }

    private void reduceQuantity(int salesQuantity) {
        quantity = quantity - salesQuantity;
    }

    public static class TicketSoldOutException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketSoldOutException(String msg) {
            super(msg);
        }
    }

    public static class TicketShortMoneyException extends RuntimeException {

        private static final long serialVersionUID = 1L;

        public TicketShortMoneyException(String msg) {
            super(msg);
        }
    }

    // ===================================================================================
    //                                                                            Accessor
    //                                                                            ========
    public int getQuantity() {
        return quantity;
    }

    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
