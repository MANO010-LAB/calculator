package com.devops;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorEngineTest {

    @Test
    void addsTwoNumbers() {
        assertEquals(7.0, CalculatorEngine.calculate(3, 4, "+"));
    }

    @Test
    void subtractsTwoNumbers() {
        assertEquals(-1.0, CalculatorEngine.calculate(3, 4, "-"));
    }

    @Test
    void multipliesTwoNumbers() {
        assertEquals(20.0, CalculatorEngine.calculate(4, 5, "*"));
    }

    @Test
    void dividesTwoNumbers() {
        assertEquals(2.5, CalculatorEngine.calculate(5, 2, "/"));
    }

    @Test
    void throwsOnDivideByZero() {
        assertThrows(ArithmeticException.class, () -> CalculatorEngine.calculate(5, 0, "/"));
    }

    @Test
    void formatsWholeNumbersWithoutDecimalPoint() {
        assertEquals("7", CalculatorEngine.format(7.0));
    }
}
