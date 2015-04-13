package co.insecurity.policy.directive;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterDirective implements Directive<String> {

	private static final Logger LOG = 
			LoggerFactory.getLogger(CharacterDirective.class);

	private Set<Directive<Integer>> required;
	private Set<Directive<Integer>> allowed;
	private Set<Directive<Integer>> restricted;
	
	public CharacterDirective() {
		required = new HashSet<Directive<Integer>>();
		allowed = new HashSet<Directive<Integer>>();
		restricted = new HashSet<Directive<Integer>>();
	}
	
	public CharacterDirective require(Directive<Integer> directive) {
		LOG.debug("Adding required character directive: {}", directive);
		required.add(directive);
		return this;
	}
	
	public CharacterDirective allow(Directive<Integer> directive) {
		LOG.debug("Adding allowed character directive: {}", directive);
		allowed.add(directive);
		return this;
	}
	
	public CharacterDirective restrict(Directive<Integer> directive) {
		LOG.debug("Adding restricted character directive: {}", directive);
		restricted.add(directive);
		return this;
	}
	
	@Override
	public boolean isMet(String s) {
		LOG.debug("Checking if '{}' meets directive: {}", s, this.toString());

		boolean a = allowed.isEmpty() ? true : s.codePoints().allMatch(
				i -> {
					return allowed.stream().anyMatch(d -> d.isMet(i)); 
					});
		LOG.debug("allowed: {}", a);
		
		boolean n = restricted.isEmpty() ? true : s.codePoints().noneMatch(
				i -> {
					return restricted.stream().anyMatch(d -> d.isMet(i));
					});
		LOG.debug("restricted: {}", n);
		
		boolean r = required.isEmpty() ? true : required.stream().allMatch(
				d -> {
					return s.codePoints().anyMatch(i -> d.isMet(i)); 
					});
		LOG.debug("required: {}", r);
		
		return (a && n && r);
	}
	
	@Override
	public String toString() {
		return String.format("[%s{allowed: %s, restricted: %s, required: %s}]", 
				this.getClass().getSimpleName(),
				this.allowed.toString(),
				this.restricted.toString(),
				this.required.toString());
	}
}