package co.insecurity.policy.directive;

public class LengthDirective implements Directive<String> {

	private final int minLength;
	private final int maxLength;
	
	public LengthDirective() {
		this(9, 128);
	}
	
	public LengthDirective(int length) {
		this(length, length);
	}
	
	public LengthDirective(int minLength, int maxLength) {
		this.minLength = minLength;
		this.maxLength = maxLength;
	}
	
	@Override
	public boolean isMet(String e) {
		if(e.length() < minLength || e.length() > maxLength)
			return false;
		return true;
	}
}