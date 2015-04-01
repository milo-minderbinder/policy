package co.insecurity.policy.directive;

public interface Directive<T> {

	boolean isMet(T e);
}
