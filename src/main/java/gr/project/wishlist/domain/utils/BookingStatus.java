package gr.project.wishlist.domain.utils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BookingStatus {
    NOT_BOOKED("not_booked"),
    BOOKED("booked"),
    DONATED("donated");

    private final String status;

    BookingStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }
}
