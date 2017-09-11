package co.insecurity.policy.directive;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.IntStream;

public class CharTypeDirectiveTest {

    private CharTypeDirective directive;

    @Before
    public void setUp() {
        directive = new CharTypeDirective().matchCharTypes(
                Character.LOWERCASE_LETTER,
                Character.OTHER_NUMBER);
    }

    @After
    public void tearDown() {
        directive = null;
    }

    @Test
    public void thatSpaceFails() {
        Assert.assertFalse("Failure - empty string should fail",
                directive.isMet(" ".codePointAt(0)));
    }

    @Test
    public void thatLowercasePasses() {
        Assert.assertTrue("Failure - lowercase letter should pass",
                directive.isMet("a".codePointAt(0)));
    }

    @Test
    public void thatUppercaseFails() {
        Assert.assertFalse("Failure - uppercase letter should fail",
                directive.isMet("A".codePointAt(0)));
    }

    @Test
    public void thatNumberPasses() {
        IntStream.range(0, 11).parallel().forEach(n -> {
            Assert.assertFalse("Failure - number should pass",
                    directive.isMet(String.valueOf(n).codePointAt(0)));
        });
    }

    @Test
    public void thatCaseDistinctionFunctions() {
        directive = new CharTypeDirective().matchCharTypes(
                Character.LOWERCASE_LETTER);
        Assert.assertTrue("Failure - lowercase letter should pass",
                directive.isMet("a".codePointAt(0)));
        Assert.assertFalse("Failure - uppercase letter should fail",
                directive.isMet("A".codePointAt(0)));

        directive = new CharTypeDirective().matchCharTypes(
                Character.UPPERCASE_LETTER);
        Assert.assertFalse("Failure - lowercase letter should fail",
                directive.isMet("a".codePointAt(0)));
        Assert.assertTrue("Failure - uppercase letter should pass",
                directive.isMet("A".codePointAt(0)));
    }
}