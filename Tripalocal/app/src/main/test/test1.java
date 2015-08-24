import java.lang.Exception;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vogella.android.temperature.ConverterUtil;
public class testJava {
    @Test
    public void testConvertFahrenheitToCelsius() {
        float actual = ConverterUtil.convertCelsiusToFahrenheit(100);
        // expected value is 212
        float expected = 212;
        // use this method because float is not precise
        assertEquals("Conversion from celsius to fahrenheit failed", expected,
                actual, 0.001);
    }
}