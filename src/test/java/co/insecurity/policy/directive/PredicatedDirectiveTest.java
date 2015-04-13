package co.insecurity.policy.directive;

import org.junit.Assert;
import org.junit.Test;

public class PredicatedDirectiveTest {

	@Test
	public void thatSimplePredicateEnforced() {
		PredicatedDirective<String> directive =
				new PredicatedDirective<String>(s -> s.equals("password"));
		Assert.assertTrue(directive.isMet("password"));
		Assert.assertFalse(directive.isMet("passwords"));
	}
}
