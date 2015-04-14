package co.insecurity.policy.directive;

import java.io.IOException;

import orestes.bloomfilter.BloomFilter;
import orestes.bloomfilter.FilterBuilder;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BFDictionaryDirectiveTest {

	private static final Logger LOG = 
			LoggerFactory.getLogger(BFDictionaryDirectiveTest.class);

	private String password;
	private BFDictionaryDirective directive;
	
	@After
	public void tearDown() {
		password = null;
		directive = null;
	}
	
	@Test
	public void thatDefaultFunctions() throws IOException {
		LOG.info("BEGIN TEST: {}", "thatDefaultFunctions");
		directive = new BFDictionaryDirective();
		password = "password";
		Assert.assertFalse(String.format(
				"Failure - directive should not be met for '%s'", password),
				directive.isMet(password));
	}

	@Test
	public void thatConstructWithDictionaryFunctions() throws IOException {
		LOG.info("BEGIN TEST: {}", "thatConstructWithDictionaryFunctions");
		directive = new BFDictionaryDirective(Dictionary.fromResource("test-passwords.dat"));
		password = "password";
		Assert.assertFalse(String.format(
				"Failure - directive should not be met for '%s'", password),
				directive.isMet(password));
	}
	
	@Test
	public void thatConstructWithFilterFunctions() throws IOException {
		LOG.info("BEGIN TEST: {}", "thatConstructWithFilterFunctions");
		Dictionary dictionary = Dictionary.fromResource("test-passwords.dat");
		BloomFilter<String> filter = 
				new FilterBuilder(dictionary.getNumWords(), 0.001)
				.buildBloomFilter();
		directive = new BFDictionaryDirective(filter);
		directive.loadDictionary(dictionary);
		password = "password";
		Assert.assertFalse(String.format(
				"Failure - directive should not be met for '%s'", password),
				directive.isMet(password));
		password = "bar";
		Assert.assertTrue(String.format(
				"Failure - directive should be met for '%s'", password),
				directive.isMet(password));
	}
}