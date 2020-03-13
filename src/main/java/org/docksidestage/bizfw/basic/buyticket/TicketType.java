package org.docksidestage.bizfw.basic.buyticket;

public enum TicketType {
    ONE_DAY_TICKET(1, 7400), TWO_DAY_TICKET(1, 13200), FOUR_DAY_TICKET(4, 22400);

    private final int checkInLimit;
    private final int price;
    TicketType(int checkInLimit, int price) {
        this.checkInLimit = checkInLimit;
        this.price = price;
    }

    public int getCheckInLimit() {
        return checkInLimit;
    }
    public int getPrice() {
        return price;
    }
}
