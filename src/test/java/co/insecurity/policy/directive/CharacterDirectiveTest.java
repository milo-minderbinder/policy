package co.insecurity.policy.directive;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterDirectiveTest {

    private static final Logger LOG = LoggerFactory.getLogger(CharacterDirectiveTest.class);

    private CharacterDirective directive;

    @Before
    public void setUp() {
        directive = new CharacterDirective();
    }

    @After
    public void tearDown() {
        directive = null;
    }

    @Test
    public void thatAllowWorks() {
        LOG.info("BEGIN TEST: {}", "thatAllowWorks");
        CharTypeDirective charTypeDirective =
                new CharTypeDirective().matchCharTypes(
                        Character.LOWERCASE_LETTER,
                        Character.DECIMAL_DIGIT_NUMBER);
        directive.allow(charTypeDirective);
        Assert.assertTrue("Failure - lowercase string should pass",
                directive.isMet("password"));
        Assert.assertFalse("Failure - uppercase string should fail",
                directive.isMet("PASSWORD"));
        Assert.assertTrue("Failure - string with numbers should pass",
                directive.isMet("password1"));
    }

    @Test
    public void thatRestrictWorks() {
        LOG.info("BEGIN TEST: {}", "thatRestrictWorks");
        CharTypeDirective restricted = new CharTypeDirective().matchCharTypes(
                Character.LOWERCASE_LETTER,
                Character.DECIMAL_DIGIT_NUMBER);
        directive.restrict(restricted);
        Assert.assertFalse("Failure - lowercase string should fail",
                directive.isMet("password"));
        Assert.assertTrue("Failure - uppercase string should pass",
                directive.isMet("PASSWORD"));
        Assert.assertFalse("Failure - string with numbers should fail",
                directive.isMet("password1"));
    }

    @Test
    public void thatRequireWorks() {
        LOG.info("BEGIN TEST: {}", "thatRequireWorks");
        String password = null;
        directive.require(new CharTypeDirective().matchCharTypes(Character.LOWERCASE_LETTER));
        directive.require(new CharTypeDirective().matchCharTypes(Character.UPPERCASE_LETTER));
        directive.require(new CharTypeDirective().matchCharTypes(Character.DECIMAL_DIGIT_NUMBER));
        directive.require(new CharTypeDirective().matchCharTypes(Character.DECIMAL_DIGIT_NUMBER));
        password = "password";
        Assert.assertFalse(
                String.format("Failure - '%s' should fail", password),
                directive.isMet(password));
        password = "PASSWORD";
        Assert.assertFalse(
                String.format("Failure - '%s' should fail", password),
                directive.isMet(password));
        password = "password1";
        Assert.assertFalse(
                String.format("Failure - '%s' should fail", password),
                directive.isMet(password));
        password = "PASSWORD1";
        Assert.assertFalse(
                String.format("Failure - '%s' should fail", password),
                directive.isMet(password));
        password = "Password1";
        Assert.assertTrue(
                String.format("Failure - '%s' should pass", password),
                directive.isMet(password));
    }
}