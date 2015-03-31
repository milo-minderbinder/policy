package co.insecurity.policy;

public class DirectiveImpl implements Directive<String> {

	@Override
	public boolean isMet(String e) {
		if(e.length() > 8)
			return true;
		return false;
	}

}
