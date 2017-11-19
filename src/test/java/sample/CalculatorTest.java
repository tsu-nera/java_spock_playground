package sample;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.*;
import org.junit.rules.TestName;

import java.io.IOException;

public class CalculatorTest {
    Calculator calc;

    @Rule
    public TestName testName = new TestName();

    @BeforeClass
    public static void before() throws IOException {
        // TestListWriter.create();
    }

    @AfterClass
    public static void after() throws IOException {
        // TestListWriter.destroy();
    }

    @Before
    public void setup(){
        calc = new Calculator();
    }

    @After
    public void tearDown() throws IOException {
        // TestListWriter.write(testName.getMethodName()+"\n");
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