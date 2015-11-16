package test;

import android.test.InstrumentationTestCase;

/**
 * Created by Frank on 24/08/2015.
 */
public class ExampleTest extends InstrumentationTestCase{
    public void test() throws Exception{
        final int expected=1;
        final int reality=5;
        System.out.println("testing success");
        assertEquals(expected,reality);
    }

    public void testeqw() throws Exception{
        assertEquals(12,32);
    }
}
