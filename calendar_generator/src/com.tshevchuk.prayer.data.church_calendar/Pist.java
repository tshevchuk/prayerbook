package com.tshevchuk.prayer.data.church_calendar;

/**
 * Created by taras on 26.05.16.
 */
class Pist {
    private String type;
    private String name;
    private Integer fromMovable;
    private Integer toMovable;
    private int fromNonMovableMonth;
    private int fromNonMovableDayOfMonth;
    private int toNonMovableMonth;
    private int toNonMovableDayOfMonth;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Integer getFromMovable() {
        return fromMovable;
    }

    public void setFromMovable(Integer fromMovable) {
        this.fromMovable = fromMovable;
    }

    public Integer getToMovable() {
        return toMovable;
    }

    public void setToMovable(Integer toMovable) {
        this.toMovable = toMovable;
    }

    public int getFromNonMovableMonth() {
        return fromNonMovableMonth;
    }

    public void setFromNonMovableDate(int fromMonth, int fromDayOfMonth) {
        this.fromNonMovableMonth = fromMonth;
        this.fromNonMovableDayOfMonth = fromDayOfMonth;
    }

    public int getFromNonMovableDayOfMonth() {
        return fromNonMovableDayOfMonth;
    }

    public int getToNonMovableMonth() {
        return toNonMovableMonth;
    }

    public void setToNonMovableDate(int toMonth, int toDayOfMonth) {
        this.toNonMovableMonth = toMonth;
        this.toNonMovableDayOfMonth = toDayOfMonth;
    }

    public int getToNonMovableDayOfMonth() {
        return toNonMovableDayOfMonth;
    }

    @Override
    public String toString() {
        return "Pist{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", fromMovable=" + fromMovable +
                ", toMovable=" + toMovable +
                ", fromNonMovableMonth=" + fromNonMovableMonth +
                ", fromNonMovableDayOfMonth=" + fromNonMovableDayOfMonth +
                ", toNonMovableMonth=" + toNonMovableMonth +
                ", toNonMovableDayOfMonth=" + toNonMovableDayOfMonth +
                '}';
    }
}
