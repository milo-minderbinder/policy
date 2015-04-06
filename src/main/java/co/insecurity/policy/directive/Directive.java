package co.insecurity.policy.directive;

@FunctionalInterface
public interface Directive<T> {

	boolean isMet(T e);
}
