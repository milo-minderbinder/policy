package co.insecurity.policy;

import co.insecurity.policy.directive.CharTypeDirective;
import co.insecurity.policy.directive.CharacterDirective;
import co.insecurity.policy.directive.Directive;
import co.insecurity.policy.directive.LengthDirective;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PasswordPolicyTest {

    private static PasswordPolicy policy;

    @BeforeClass
    public static void setUpClass() {
        policy = new PasswordPolicy();
        policy.addDirective(new LengthDirective(9, 128));
        policy.addDirective(new CharacterDirective()
                .allow(new CharTypeDirective().matchCharTypes(
                        Character.LOWERCASE_LETTER,
                        Character.UPPERCASE_LETTER,
                        Character.DECIMAL_DIGIT_NUMBER))
                .require(new CharTypeDirective().matchCharType(
                        Character.DECIMAL_DIGIT_NUMBER)));
        policy.addDirective(s -> s.matches(".*\\d$"));
    }

    @AfterClass
    public static void tearDownClass() {
        policy = null;
    }

    @Test
    public void thatConformingPasswordIsCompliant() {
        String password = "Password1";
        Assert.assertTrue(String.format("Failure - %s should meet policy", password), policy.isCompliant(password));
        Assert.assertTrue("Failure - violations should be empty", policy.getViolations(password).isEmpty());
    }

    @Test
    public void thatPredicate() {
        Directive<String> d = v -> v.equals("dog");
        Assert.assertTrue(d.isMet("dog"));
        Assert.assertFalse(d.isMet("dogs"));
    }
}