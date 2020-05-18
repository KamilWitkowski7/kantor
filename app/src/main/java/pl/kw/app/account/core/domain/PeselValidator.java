package pl.kw.app.account.core.domain;

import java.time.LocalDate;

class PeselValidator {
    private static final byte[] PESEL = new byte[11];

    static boolean hasCorrectAge(String pesel, int minimalAge) {
        for (int i = 0; i < 11; i++) {
            PESEL[i] = Byte.parseByte(pesel.substring(i, i + 1));
        }
        LocalDate dateOfBirth = LocalDate.of(getBirthYear(), getBirthMonth(), getBirthDay());
        LocalDate dateOfBirthMinusMinimalAge = LocalDate.now().minusYears(minimalAge);
        if (dateOfBirthMinusMinimalAge.isAfter(dateOfBirth) || dateOfBirthMinusMinimalAge.isEqual(dateOfBirth)) {
            return true;
        } else {
            return false;
        }
    }

    private static int getBirthYear() {
        int year;
        int month;
        year = 10 * PESEL[0];
        year += PESEL[1];
        month = 10 * PESEL[2];
        month += PESEL[3];
        if (month > 80 && month < 93) {
            year += 1800;
        } else if (month > 0 && month < 13) {
            year += 1900;
        } else if (month > 20 && month < 33) {
            year += 2000;
        } else if (month > 40 && month < 53) {
            year += 2100;
        } else if (month > 60 && month < 73) {
            year += 2200;
        }
        return year;
    }

    private static int getBirthMonth() {
        int month;
        month = 10 * PESEL[2];
        month += PESEL[3];
        if (month > 80 && month < 93) {
            month -= 80;
        } else if (month > 20 && month < 33) {
            month -= 20;
        } else if (month > 40 && month < 53) {
            month -= 40;
        } else if (month > 60 && month < 73) {
            month -= 60;
        }
        return month;
    }

    private static int getBirthDay() {
        int day;
        day = 10 * PESEL[4];
        day += PESEL[5];
        return day;
    }
}
