package co.insecurity.policy;

public class ComplexityDirective implements Directive<String> {

	@Override
	public boolean isMet(String e) {
		return e.chars()
				.filter(i -> Character.isDigit(i))
				.count() > 0;
	}
}