package core;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    void testIsValidName() {
        assertTrue(Validator.isValidName("Exercise"));
        assertFalse(Validator.isValidName(""));
        assertFalse(Validator.isValidName("   "));
        assertFalse(Validator.isValidName(null));
    }
}
