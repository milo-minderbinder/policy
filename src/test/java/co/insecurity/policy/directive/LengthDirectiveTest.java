package co.insecurity.policy.directive;

import org.junit.Assert;
import org.junit.Test;

public class LengthDirectiveTest {

	private LengthDirective directive;
	
	@Test
	public void thatMinLengthEnforced() {
		directive = new LengthDirective(9, 128);
		Assert.assertFalse("Failure - minLength must fail for len < minLength",
				directive.isMet("admin"));
		Assert.assertTrue("Failure - minLength must pass for len >= minLength",
				directive.isMet("123456789"));
		Assert.assertTrue("Failure - minLength must pass for len >= minLength",
				directive.isMet("1234567890"));
	}

	@Test
	public void thatMaxLengthEnforced() {
		directive = new LengthDirective(0, 5);
		Assert.assertTrue("Failure - maxLength must pass for len <= maxLength",
				directive.isMet("admin"));
		Assert.assertFalse("Failure - maxLength must fail for len > maxLength",
				directive.isMet("123456"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void thatNegativeMinLengthThrowsRTE() {
		directive = new LengthDirective(-1, 128);
	}

	@Test(expected = IllegalArgumentException.class)
	public void thatMinLengthGreaterThanMaxLengthThrowsRTE() {
		directive = new LengthDirective(8, 4);
	}
}