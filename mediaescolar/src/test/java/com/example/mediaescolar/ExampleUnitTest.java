package com.example.mediaescolar;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void calcularMediaDosBimestres() {
        MainActivity mainActivity = new MainActivity();
        assertEquals((Double) 9.0, mainActivity.calcularMedia(10.0, 8.5, 10.0, 7.5));
    }
}