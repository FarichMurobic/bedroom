package com.bedroom.domain.identity.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberTest {

    @Test
    void shouldCreateValidPhoneNumber() {
        PhoneNumber phoneNumber = new PhoneNumber("+6285795488106");

        assertEquals("+6285795488106", phoneNumber.value());
    }

    @Test
    void shouldTrimPhoneNumber() {
        PhoneNumber phoneNumber = new PhoneNumber("  +6285795488106  ");

        assertEquals("+6285795488106", phoneNumber.value());
    }

    @Test
    void shouldRejectNullPhoneNumber() {
        assertThrows(
                NullPointerException.class,
                () -> new PhoneNumber(null)
        );
    }

    @Test
    void shouldRejectBlankPhoneNumber() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumber("   ")
        );
    }

    @Test
    void shouldRejectInvalidPhoneNumber() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumber("085795488106")
        );
    }

}