package gr.project.wishlist.domain.utils;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AccessMode {
    BOOKING_MODE("booking"),
    EDIT_MODE("edit"),
    SUBSCRIBE_MODE("subscribe");

    private final String mode;

    AccessMode(String mode) {
        this.mode = mode;
    }

    @JsonValue
    public String getMode() {
        return mode;
    }
}