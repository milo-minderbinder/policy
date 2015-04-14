package co.insecurity.policy.directive;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ConcurrentModificationException;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BFDictionaryDirective implements Directive<String> {

	private static final Logger LOG = 
			LoggerFactory.getLogger(BFDictionaryDirective.class);
	private static final String DEFAULT_DICTIONARY = "passwords.dat";

	private final BloomFilter<String> filter;
	
	public BFDictionaryDirective() throws IOException {
		this(Dictionary.fromResource(DEFAULT_DICTIONARY));
	}
	
	public BFDictionaryDirective(Dictionary dictionary) throws IOException {
		filter = new FilterBuilder(dictionary.getNumWords(), 0.001)
				.buildBloomFilter();
		loadDictionary(dictionary);
		LOG.debug("Instantiated new directive: {}", this.toString());
	}
	
	public BFDictionaryDirective(BloomFilter<String> filter) {
		this.filter = filter;
		LOG.debug("Instantiated new directive: {}", this.toString());
	}
	
	public void loadDictionary(Dictionary dictionary) throws IOException {
		LOG.debug("Loading {} into filter", dictionary);
		int numExpected = filter.getExpectedElements();
		int numAdded = 0;
		try (BufferedReader reader = dictionary.getReader()) {
			String word = null;
			while ((word = reader.readLine()) != null) {
				if (filter.add(word))
					numAdded++;
				else
					LOG.debug("Not loaded into filter (may be duplicate): {}",
							word);
				if (numAdded > numExpected) {
					String msg = String.format(
							"Added %d words but expected %d." + 
							"Did the data file change?", 
							numAdded, numExpected);
					LOG.error(msg);
					throw new ConcurrentModificationException(msg);
				}
			}
		}
		LOG.debug("Successfully loaded {} words into filter", numAdded);
	}
	
	@Override
	public boolean isMet(String s) {
		return !filter.contains(s);
	}
	
	@Override
	public String toString() {
		return String.format("[BFDictionaryDirective[%s]", filter.asString());
	}
}