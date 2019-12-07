package com.YTeam.cinema;

public class ticket {
    public int ID;
    public int shedule_id;
    public int  plase_number;
    public int row_number;
    public int price;
    public int state;

    public ticket(int ID, int shedule_id, int plase_number, int row_number, int price, int state) {
        this.ID = ID;
        this.shedule_id = shedule_id;
        this.plase_number = plase_number;
        this.row_number = row_number;
        this.price = price;
        this.state = state;
    }
}
