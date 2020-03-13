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

import java.util.HashMap;
import java.util.Map;

/**
 * @author jflute
 * @author winkichanwi
 */
public class TicketBooth {

    // ===================================================================================
    //                                                                          Definition
    //                                                                          ==========
    private static final int ONE_DAY_MAX_QUANTITY = 10;
    private static final int TWO_DAY_MAX_QUANTITY = 10;
    private static final int FOUR_DAY_MAX_QUANTITY = 10;
    private static Map<TicketType, Integer> ticketStock = new HashMap<>();

    // ===================================================================================
    //                                                                           Attribute
    //                                                                           =========
    private Integer salesProceeds;

    // ===================================================================================
    //                                                                         Constructor
    //                                                                         ===========
    public TicketBooth() {
        initStock();
    }

    private void initStock() {
        ticketStock.put(TicketType.ONE_DAY_TICKET, ONE_DAY_MAX_QUANTITY);
        ticketStock.put(TicketType.TWO_DAY_TICKET, TWO_DAY_MAX_QUANTITY);
        ticketStock.put(TicketType.FOUR_DAY_TICKET, FOUR_DAY_MAX_QUANTITY);
    }

    // ===================================================================================
    //                                                                          Buy Ticket
    //                                                                          ==========
    public TicketBuyResult buyOneDayPassport(int handedMoney) {
        TicketBuyResult result = buyPassport(handedMoney, TicketType.ONE_DAY_TICKET);
        return result;
    }

    public TicketBuyResult buyTwoDayPassport(int handedMoney) {
        TicketBuyResult result = buyPassport(handedMoney, TicketType.TWO_DAY_TICKET);
        return result;
    }

    public TicketBuyResult buyFourDayPassport(int handedMoney) {
        TicketBuyResult result = buyPassport(handedMoney, TicketType.FOUR_DAY_TICKET);
        return result;
    }

    private TicketBuyResult buyPassport(int handedMoney, TicketType ticketType) {
        checkQuantity(ticketType);
        checkHandedMoney(handedMoney, ticketType);
        addSalesProceeds(ticketType);
        reduceQuantity(ticketType);

        Ticket ticket;
        if (ticketType == TicketType.ONE_DAY_TICKET) {
            ticket = new OneDayTicket();
        } else {
            ticket = new MultipleDayTicket(ticketType);
        }
        int change = handedMoney - ticketType.getPrice();
        return new TicketBuyResult(ticket, change);
    }

    private void checkQuantity(TicketType ticketType) {
        int stock = ticketStock.get(ticketType);
        if (stock <= 0) {
            throw new TicketSoldOutException(ticketType.name() + "is sold out");
        }
    }

    private void checkHandedMoney(int handedMoney, TicketType ticketType) {
        if (handedMoney < ticketType.getPrice()) {
            throw new TicketShortMoneyException("Short money: " + handedMoney);
        }
    }

    private void addSalesProceeds(TicketType ticketType) {
        if (salesProceeds != null) {
            salesProceeds = salesProceeds + ticketType.getPrice();
        } else {
            salesProceeds = ticketType.getPrice();
        }
    }

    private void reduceQuantity(TicketType ticketType) {
        int stock = ticketStock.get(ticketType);
        ticketStock.replace(ticketType, stock - 1);
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
    public int getStock(TicketType ticketType) {
        return ticketStock.get(ticketType);
    }
    public Integer getSalesProceeds() {
        return salesProceeds;
    }
}
