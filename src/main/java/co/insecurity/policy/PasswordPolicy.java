package co.insecurity.policy;

import co.insecurity.policy.directive.Directive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PasswordPolicy implements Policy<String> {

    private final List<Directive<String>> directives;

    public PasswordPolicy() {
        directives = new ArrayList<Directive<String>>();
    }

    @Override
    public Collection<Directive<String>> getDirectives() {
        return directives;
    }

    @Override
    public void addDirective(final Directive<String> directive) {
        directives.add(directive);
    }
}