package ru.hofftech.liga.lessons.packageloader.model;

import java.util.Date;

public record Order(String userId, Date date, String operation, int trucksCount, int packagesCount, int totalPrice) {
    private static final String DELIMITER = "; ";

    @Override
    public String toString() {
        return new StringBuilder()
                .append(date)
                .append(DELIMITER)
                .append(operation)
                .append(DELIMITER)
                .append(trucksCount)
                .append(" машин")
                .append(DELIMITER)
                .append(packagesCount)
                .append(" посылок")
                .append(DELIMITER)
                .append(totalPrice)
                .append(" рублей")
                .append(DELIMITER)
                .toString();
    }

}
