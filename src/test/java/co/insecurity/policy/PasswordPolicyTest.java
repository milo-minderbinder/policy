package co.insecurity.policy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import co.insecurity.policy.directive.CharTypesDirective;
import co.insecurity.policy.directive.Directive;
import co.insecurity.policy.directive.LengthDirective;

public class PasswordPolicyTest {
	
	private static PasswordPolicy policy;

	@BeforeClass
	public static void setUpClass() {
		policy = new PasswordPolicy();
		policy.addDirective(new LengthDirective(8, 12));
		policy.addDirective(new CharTypesDirective().addRequiredTypes(
				Character.LOWERCASE_LETTER,
				Character.UPPERCASE_LETTER,
				Character.DECIMAL_DIGIT_NUMBER));
	}
	
	@AfterClass
	public static void tearDownClass() {
		policy = null;
	}
	
	@Test
	public void that() {
		String password = null;
		List<Class<? extends Directive<String>>> expected = null;
		
		password = "admin";
		expected = Arrays.asList(
				LengthDirective.class, CharTypesDirective.class);
		Assert.assertFalse(
				String.format(
						"Failure - password should comply with this policy: %s",
						password),
				policy.isCompliant(password));
		Assert.assertTrue(
				String.format(
						"Failure2 - expected violations for %s: %s", 
						password, expected), 
				violationsForPasswordAre(policy, password, expected));
		
		password = "password";
		expected = Arrays.asList(
				CharTypesDirective.class);
		Assert.assertFalse(
				String.format(
						"Failure - password should comply with this policy: %s",
						password),
				policy.isCompliant(password));
		Assert.assertTrue(
				String.format(
						"Failure2 - expected violations for %s: %s", 
						password, expected), 
				violationsForPasswordAre(policy, password, expected));
		

		password = "Password";
		expected = Arrays.asList(
				CharTypesDirective.class);
		Assert.assertFalse(
				String.format(
						"Failure - password should comply with this policy: %s",
						password),
				policy.isCompliant(password));
		Assert.assertTrue(
				String.format(
						"Failure2 - expected violations for %s: %s", 
						password, expected), 
				violationsForPasswordAre(policy, password, expected));
		

		password = "Password1";
		expected = Arrays.asList();
		Assert.assertTrue(
				String.format(
						"Failure - password should comply with this policy: %s",
						password),
				policy.isCompliant(password));
		Assert.assertTrue(
				String.format(
						"Failure2 - expected violations for %s: %s", 
						password, expected), 
				violationsForPasswordAre(policy, password, expected));
	}
	
	private boolean violationsForPasswordAre(Policy<String> policy, 
			String password, 
			List<Class<? extends Directive<String>>> expected) {
		 return policy.getViolations(password)
				 .stream()
				 .map(v -> v.getClass())
				 .collect(Collectors.toCollection(ArrayList::new))
				 .containsAll(expected);
	}
}
