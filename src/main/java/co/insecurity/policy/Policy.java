package co.insecurity.policy;

import co.insecurity.policy.directive.Directive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public interface Policy<T> {

    void addDirective(Directive<T> directive);

    Collection<Directive<T>> getDirectives();

    default boolean isCompliant(final T e) {
        return getDirectives()
                .stream()
                .allMatch(d -> d.isMet(e));
    }

    default Collection<Directive<T>> getViolations(final T e) {
        return getDirectives()
                .stream()
                .filter(d -> !d.isMet(e))
                .collect(Collectors.toCollection(
                        ArrayList<Directive<T>>::new)
                );
    }
}