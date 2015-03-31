package co.insecurity.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PolicyImpl implements Policy<String, DirectiveImpl> {

	private List<DirectiveImpl> directives;
	
	public PolicyImpl() {
		directives = new ArrayList<DirectiveImpl>();
	}

	@Override
	public void addDirective(DirectiveImpl directive) {
		directives.add(directive);
	}

	@Override
	public Collection<DirectiveImpl> getDirectives() {
		return directives;
	}

	public static void main(String[] args) {
		PolicyImpl pwPolicy = new PolicyImpl();
		pwPolicy.addDirective(new DirectiveImpl());
		System.out.println(pwPolicy.isCompliant("admin"));
		System.out.println(pwPolicy.isCompliant("passwords"));
	}
}
