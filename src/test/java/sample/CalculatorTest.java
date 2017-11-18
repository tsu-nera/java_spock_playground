package sample;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest {
    Calculator calc;

    @Before
    public void setup(){
        calc = new Calculator();
    }

    @After
    public void tearDown(){
    }

    @Test
    public void add1() {
        assertThat(2, is(calc.add(1,1)));
    }

    @Test
    public void add2() {
        assertThat(3, is(calc.add(1,2)));
    }
}