package co.insecurity.policy.directive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class CharTypeDirective implements Directive<Integer> {

    private static final Logger LOG = LoggerFactory.getLogger(CharTypeDirective.class);

    private Set<Integer> matchedTypes;

    public CharTypeDirective() {
        matchedTypes = new HashSet<Integer>();
    }

    public CharTypeDirective matchCharType(byte charType) {
        LOG.debug("Adding character type matcher: {}", charType);
        matchedTypes.add(Integer.valueOf(charType));
        return this;
    }

    public CharTypeDirective matchCharTypes(byte... charTypes) {
        for (byte charType : charTypes)
            matchCharType(charType);
        return this;
    }

    @Override
    public boolean isMet(Integer codePoint) {
        LOG.debug("Checking if directive is met for code point: {}", codePoint);
        if (matchedTypes.contains(Character.getType(codePoint))) {
            LOG.debug("CharTypeDirective met for character code point: {}", codePoint);
            return true;
        }
        LOG.debug("CharTypeDirective NOT met for character code point: {}", codePoint);
        return false;
    }

    @Override
    public String toString() {
        return String.format("[%s%s]",
                this.getClass().getSimpleName(),
                matchedTypes.toString());
    }
}