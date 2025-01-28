/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package ps0.rules;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ps0.rules.RulesOf6005;

/**
 * JUnit tests for RulesOf6005.
 */
public class RulesOf6005Test {
    
    /**
     * Tests the mayUseCodeInAssignment method.
     */
    @Test
    public void testMayUseCodeInAssignment() {
        assertFalse(
                RulesOf6005.mayUseCodeInAssignment(false, true, false, false, false),
                "Expected false: un-cited publicly-available code");
        assertTrue(
                RulesOf6005.mayUseCodeInAssignment(true, false, true, true, true),
                "Expected true: self-written required code");
    }
}
