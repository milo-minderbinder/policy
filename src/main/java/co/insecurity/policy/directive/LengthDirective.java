package co.insecurity.policy.directive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LengthDirective implements Directive<String> {

	private static final Logger LOG = 
			LoggerFactory.getLogger(LengthDirective.class);
	
	private final int minLength;
	private final int maxLength;
	
	public LengthDirective() {
		this(9, 128);
	}
	
	public LengthDirective(int length) {
		this(length, length);
	}
	
	public LengthDirective(int minLength, int maxLength) {
		if (!(0 <= minLength && minLength <= maxLength)) {
			IllegalArgumentException e = new IllegalArgumentException(
					String.format("Invalid length directive (%d, %d)",
					minLength, maxLength));
			LOG.error("Must satisfy 0 <= minLength <= maxLength", e);
			throw e;
		}
		this.minLength = minLength;
		this.maxLength = maxLength;
	}
	
	public int getMinLength() {
		return minLength;
	}
	
	public int getMaxLength() {
		return maxLength;
	}
	
	@Override
	public boolean isMet(String s) {
		int len = s.length();
		if(len < minLength || len > maxLength)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return String.format("%s(%d, %d)", 
				this.getClass().getSimpleName(),
				this.minLength, this.maxLength);
	}
}