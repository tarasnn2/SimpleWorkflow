package test.app.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.app.simpleworkflow.impl.restriction.RestrictionException;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScriptFourTest
        extends ScriptAbstractTest {

    @DisplayName("script #4")
    @Test
    public void run() {
        login(firstCompany);
        createFirstDocument();
        assertThrows(
                RestrictionException.class,
                () -> createFirstDocument()
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