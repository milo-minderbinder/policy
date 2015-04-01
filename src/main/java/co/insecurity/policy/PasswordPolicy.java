package co.insecurity.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import co.insecurity.policy.directive.CharTypesDirective;
import co.insecurity.policy.directive.Directive;
import co.insecurity.policy.directive.LengthDirective;

public class PasswordPolicy implements Policy<String, Directive<String>> {

	private final List<Directive<String>> directives;
	
	public PasswordPolicy() {
		directives = new ArrayList<Directive<String>>();
	}

	@Override
	public Collection<Directive<String>> getDirectives() {
		return directives;
	}

	@Override
	public void addDirective(final Directive<String> directive) {
		directives.add(directive);
	}

	public static void main(String[] args) {
		PasswordPolicy pwPolicy = new PasswordPolicy();
		pwPolicy.addDirective(new LengthDirective());
		pwPolicy.addDirective(new CharTypesDirective());
		System.out.println(pwPolicy.isCompliant("admin"));
		System.out.println(pwPolicy.isCompliant("passwords"));
		System.out.println(pwPolicy.isCompliant("passwords1"));
		System.out.println(pwPolicy.getViolations("admin"));
		System.out.println(pwPolicy.getViolations("passwords"));
		System.out.println(pwPolicy.getViolations("passwords1"));
	}
}