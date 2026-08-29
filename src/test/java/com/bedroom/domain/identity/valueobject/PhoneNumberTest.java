package com.bedroom.domain.identity.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void shouldCreateValidPhoneNumber() {
        PhoneNumber phoneNumber =
                new PhoneNumber("+628123456789");

        assertEquals("+628123456789", phoneNumber.value());
    }

    @Test
    void shouldTrimPhoneNumber() {
        PhoneNumber phoneNumber =
                new PhoneNumber("  +628123456789  ");

        assertEquals("+628123456789", phoneNumber.value());
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
    void shouldRejectPhoneNumberWithoutPlus() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumber("628123456789")
        );
    }

    @Test
    void shouldRejectPhoneNumberWithInvalidCountryCode() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumber("+0123456789")
        );
    }

    @Test
    void shouldRejectPhoneNumberExceedingE164Length() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumber("+62812345678901234")
        );
    }

}
