package co.insecurity.policy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public interface Policy<T, D extends Directive<T>> {

	void addDirective(D directive);

	Collection<D> getDirectives();

	default boolean isCompliant(T e) {
		return getDirectives()
			.stream()
			.allMatch(d -> d.isMet(e));
	}	
	
	default Collection<D> getViolations(T e) {
		return getDirectives()
				.stream()
				.filter(d -> !d.isMet(e))
				.collect(Collectors.toCollection(ArrayList<D>::new));
	}
}