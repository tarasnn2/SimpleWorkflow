package test.app.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import test.app.simpleworkflow.impl.restriction.RestrictionException;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ScriptFiveTest
        extends ScriptAbstractTest {

    @DisplayName("script #5")
    @Test
    public void run() throws InterruptedException {
        login(firstCompany);
        createFirstDocument();
        Thread.sleep(1000);
        assertThrows(
                RestrictionException.class,
                () -> createFirstDocument()
        );
    }

    @Override
    protected Properties getProperty3() {
        return new Properties() {
            {
                setProperty("allowCountWorkflow", "1");
                setProperty("durationAllow", "1"); //в секундах
            }
        };
    }
}