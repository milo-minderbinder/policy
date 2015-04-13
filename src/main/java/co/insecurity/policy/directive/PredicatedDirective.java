package co.insecurity.policy.directive;

import java.util.function.Predicate;

public class PredicatedDirective<T> implements Directive<T> {

	private Predicate<T> predicate;
	
	public PredicatedDirective() {
		this.predicate = e -> true;
	}
	
	public PredicatedDirective(Predicate<T> predicate) {
		this.predicate = predicate;
	}
	
	@Override
	public boolean isMet(T e) {
		return predicate.test(e);
	}
}