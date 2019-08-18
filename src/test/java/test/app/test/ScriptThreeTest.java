package test.app.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.app.simpleworkflow.impl.restriction.RestrictionException;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScriptThreeTest
        extends ScriptOneTest {

    @DisplayName("script #3")
    @Test
    public void run() {
        assertThrows(
                RestrictionException.class,
                () -> super.run()
        );
    }


    @Override
    protected Properties getProperty1() {
        return new Properties() {
            {
                setProperty("durationAllowStart", "00:00");
                setProperty("durationAllowStop", "00:01");
            }
        };
    }
}
