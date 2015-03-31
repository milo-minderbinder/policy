package co.insecurity.policy;

import java.util.Collection;

public interface Policy<T, D extends Directive<T>> {

	void addDirective(D directive);
	Collection<D> getDirectives();
	default boolean isCompliant(T e) {
		return getDirectives()
			.stream()
			.anyMatch(d -> d.isMet(e));
	}	
}