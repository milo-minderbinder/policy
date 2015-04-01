package co.insecurity.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import co.insecurity.policy.directive.Directive;

public interface Policy<T, D extends Directive<T>> {

	void addDirective(D directive);

	Collection<D> getDirectives();

	default boolean isCompliant(final T e) {
		return getDirectives()
			.stream()
			.allMatch(d -> d.isMet(e));
	}	
	
	default Collection<D> getViolations(final T e) {
		return getDirectives()
				.stream()
				.filter(d -> !d.isMet(e))
				.collect(Collectors.toCollection(ArrayList<D>::new));
	}
}