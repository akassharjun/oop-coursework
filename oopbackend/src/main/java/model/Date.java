package model;

import java.util.Objects;

public class Date {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.setDay(day);
        this.setMonth(month);
        this.setYear(year);
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    private void setDay(int day) {
        // checking for leap year
        if (this.month == 2) {
            if (this.year % 4 == 0) {
                if (day <= 0 || day > 29) {
                    throw new IllegalArgumentException("Day should be in range of 1-29");
                }
                this.day = day;
            } else {
                if (day <= 0 || day > 28) {
                    throw new IllegalArgumentException("Day should be in range of 1-28");
                }
                this.day = day;
            }
        } else if (day > 31 || day < 1) {
            throw new IllegalArgumentException("Day should be in range of 1-31");
        }

        this.day = day;
    }

    private void setMonth(int month) {
        if (month > 12 || month < 1) {
            throw new IllegalArgumentException("Month should be in range of 1-12");
        }
        this.month = month;
    }

    private void setYear(int year) {
        if (year > 2019 || year < 1) {
            throw new IllegalArgumentException("Year should be in range of 1-2019");
        }
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Date)) return false;
        Date date = (Date) o;
        return day == date.day &&
                month == date.month &&
                year == date.year;
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, month, year);
    }

    @Override
    public String toString() {
        return year + "-" + month + "-" + day;
    }
}
