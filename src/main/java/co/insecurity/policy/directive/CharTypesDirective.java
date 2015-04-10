package co.insecurity.policy.directive;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CharTypesDirective implements Directive<String> {

	private Set<Integer> requiredCharTypes;
	
	public CharTypesDirective() {
		requiredCharTypes = new HashSet<Integer>();
	}
	
	public CharTypesDirective addRequiredType(byte charType) {
		requiredCharTypes.add(Integer.valueOf(charType));
		return this;
	}
	
	public CharTypesDirective addRequiredTypes(byte... charTypes) {
		for (byte charType : charTypes)
			requiredCharTypes.add(Integer.valueOf(charType));
		return this;
	}
	
	@Override
	public boolean isMet(String e) {
		return e
				.codePoints()
				.map(Character::getType)
				.boxed()
				.collect(Collectors.toCollection(HashSet<Integer>::new))
				.containsAll(requiredCharTypes);
	}
}