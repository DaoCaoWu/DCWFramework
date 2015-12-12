package com.dcw.app.util;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * create by adao12.vip@gmail.com on 15/12/12
 *
 * @author JiaYing.Cheng
 * @version 1.0
 */
public class CalculatorTest {

    private Calculator mCalculator;

    @Before
    public void setUp() throws Exception {
        mCalculator = new Calculator();
    }

    @Test
    public void testSum() throws Exception {
        assertEquals(6, mCalculator.sum(1, 5), 0);
    }

    @Test
    public void testSubstract() throws Exception {
        assertEquals(-4, mCalculator.substract(1, 5), 0);
    }

    @Test
    public void testDivide() throws Exception {
        assertEquals(0.2, mCalculator.divide(1, 5), 0);
    }

    @Test
    public void testMultiply() throws Exception {
        assertEquals(5, mCalculator.multiply(1, 5), 0);
    }
}