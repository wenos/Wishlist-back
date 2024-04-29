package gr.project.wishlist.domain.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum BookingStatus {
    BOOKED("booked"),
    NOT_BOOKED("not_booked"),
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
