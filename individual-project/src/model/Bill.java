package model;

import java.util.List;

public class Bill {
    List<BillItems> items;
    double card;

    public void setItems(List<BillItems> items) {
        this.items = items;
    }

    public void setCard(double card) {
        this.card = card;
    }

    public List<BillItems> getItems() {
        return items;
    }

    public double getCard() {
        return card;
    }
}
