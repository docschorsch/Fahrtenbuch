package com.docschorsch.fahrtenbuch;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyLocalDateTime {
    private LocalDateTime localDateTime;
    private DateTimeFormatter formatter;


    public MyLocalDateTime(LocalDateTime localDateTime) {
        formatter = DateTimeFormatter.ofPattern("d MMM uuuu : HH mm");
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return localDateTime.format(formatter);
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}
