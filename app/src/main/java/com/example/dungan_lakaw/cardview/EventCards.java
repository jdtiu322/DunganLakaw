package com.example.dungan_lakaw.cardview;

public class EventCards {
    String Monthname;
    int Month, Year, Day;

    public EventCards(String monthname, int month, int year, int day) {
        Monthname = monthname;
        Month = month;
        Year = year;
        Day = day;
    }

    public String getMonthname() {
        return Monthname;
    }

    public int getMonth() {
        return Month;
    }

    public int getYear() {
        return Year;
    }

    public int getDay() {
        return Day;
    }
}
