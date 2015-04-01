package co.insecurity.policy;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import co.insecurity.policy.directive.CharTypesDirective;
import co.insecurity.policy.directive.LengthDirective;

public class PasswordPolicyTest {
	
	private static PasswordPolicy policy;

	@BeforeClass
	public static void setUpClass() {
		policy = new PasswordPolicy();
		policy.addDirective(new LengthDirective(8, 12));
		CharTypesDirective charTypes = new CharTypesDirective();
		charTypes.addRequiredType(Character.LOWERCASE_LETTER);
		charTypes.addRequiredType(Character.UPPERCASE_LETTER);
		policy.addDirective(charTypes);
	}
	
	@AfterClass
	public static void tearDownClass() {
		policy = null;
	}
	
	@Test
	public void that() {
		System.out.println(policy.isCompliant("Password"));
		System.out.println(policy.isCompliant("password"));
	}
	
}
