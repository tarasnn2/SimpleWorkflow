package test.app.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.app.simpleworkflow.impl.restriction.RestrictionException;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScriptSixTest
        extends ScriptTwoTest {

    @DisplayName("script #6")
    @Test
    public void run() {
        super.run();
        assertThrows(
                RestrictionException.class,
                () -> super.run()
        );
    }

    @Override
    protected Properties getProperty2() {
        return new Properties() {
            {
                setProperty("allowCountWorkflow", "1");
            }
        };
    }

}