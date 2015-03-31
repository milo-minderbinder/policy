package co.insecurity.policy;

public interface Directive<T> {

	boolean isMet(T e);
}
