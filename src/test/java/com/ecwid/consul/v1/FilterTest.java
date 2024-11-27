package com.ecwid.consul.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.ecwid.consul.v1.Filter.Selector;

import nl.jqno.equalsverifier.EqualsVerifier;

class FilterTest {
	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.forClass(Filter.class)
					.withPrefabValues(Filter.class,
							Filter.of(Filter.MatchingOperator.EQUAL, Filter.Selector.of("foo"), "bar"),
							Filter.in("baz", Filter.Selector.of("fang")))
					.withIgnoredFields("parent").verify();
		}
	}

	@Test
	void of() {
		Filter actual = Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo"), "bar");
		assertFilter("foo = \"bar\"", actual);
		assertThrows(IllegalArgumentException.class,
				() -> Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo")));

		actual = Filter.of(Filter.MatchingOperator.NOT_EQUAL, Selector.of("foo"), "bar");
		assertFilter("foo != \"bar\"", actual);
		assertThrows(IllegalArgumentException.class,
				() -> Filter.of(Filter.MatchingOperator.NOT_EQUAL, Selector.of("foo")));

		actual = Filter.of(Filter.MatchingOperator.IS_EMPTY, Selector.of("foo"), null);
		assertFilter("foo is empty", actual);
		actual = Filter.of(Filter.MatchingOperator.IS_EMPTY, Selector.of("foo"));
		assertFilter("foo is empty", actual);
		assertThrows(IllegalArgumentException.class,
				() -> Filter.of(Filter.MatchingOperator.IS_EMPTY, Selector.of("foo"), "bar"));

		actual = Filter.of(Filter.MatchingOperator.IS_NOT_EMPTY, Selector.of("foo"), null);
		assertFilter("foo is not empty", actual);
		actual = Filter.of(Filter.MatchingOperator.IS_NOT_EMPTY, Selector.of("foo"));
		assertFilter("foo is not empty", actual);
		assertThrows(IllegalArgumentException.class,
				() -> Filter.of(Filter.MatchingOperator.IS_NOT_EMPTY, Selector.of("foo"), "bar"));

		actual = Filter.of(Filter.MatchingOperator.IN, Selector.of("foo"), "bar");
		assertFilter("\"bar\" in foo", actual);
		assertThrows(IllegalArgumentException.class, () -> Filter.of(Filter.MatchingOperator.IN, Selector.of("foo")));

		actual = Filter.of(Filter.MatchingOperator.NOT_IN, Selector.of("foo"), "bar");
		assertFilter("\"bar\" not in foo", actual);
		assertThrows(IllegalArgumentException.class,
				() -> Filter.of(Filter.MatchingOperator.NOT_IN, Selector.of("foo")));

		actual = Filter.of(Filter.MatchingOperator.CONTAINS, Selector.of("foo"), "bar");
		assertFilter("foo contains \"bar\"", actual);
		assertThrows(IllegalArgumentException.class,
				() -> Filter.of(Filter.MatchingOperator.CONTAINS, Selector.of("foo")));

		actual = Filter.of(Filter.MatchingOperator.NOT_CONTAINS, Selector.of("foo"), "bar");
		assertFilter("foo not contains \"bar\"", actual);
		assertThrows(IllegalArgumentException.class,
				() -> Filter.of(Filter.MatchingOperator.NOT_CONTAINS, Selector.of("foo")));

		actual = Filter.of(Filter.MatchingOperator.MATCHES, Selector.of("foo"), "bar");
		assertFilter("foo matches \"bar\"", actual);
		assertThrows(IllegalArgumentException.class,
				() -> Filter.of(Filter.MatchingOperator.MATCHES, Selector.of("foo")));

		actual = Filter.of(Filter.MatchingOperator.NOT_MATCHES, Selector.of("foo"), "bar");
		assertFilter("foo not matches \"bar\"", actual);
		assertThrows(IllegalArgumentException.class,
				() -> Filter.of(Filter.MatchingOperator.NOT_MATCHES, Selector.of("foo")));
	}

	@Test
	void in() {
		final Filter actual = Filter.in("bar", Selector.of("foo"));
		assertFilter("\"bar\" in foo", actual);
	}

	@Test
	void notIn() {
		final Filter actual = Filter.notIn("bar", Selector.of("foo"));
		assertFilter("\"bar\" not in foo", actual);
	}

	@Test
	void and() {
		final Filter f = Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo"), "bar");
		final Filter actual = f.and(Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo1"), "bar1"),
				Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo2"), "bar2"));

		assertFilter("foo = \"bar\" and foo1 = \"bar1\" and foo2 = \"bar2\"", actual);

		final Filter actual2 = f.or(Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo1"), "bar1"))
				.and(Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo3"), "bar3"));
		assertFilter("(foo = \"bar\" or foo1 = \"bar1\") and foo3 = \"bar3\"", actual2);

		final Filter actual3 = f.and();
		assertFilter("foo = \"bar\"", actual3);
	}

	@Test
	void addAll() {
		final Filter actual = Filter.andAll(
				Arrays.asList(new Filter[] { Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo"), "bar"),
						Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo1"), "bar1") }));
		assertFilter("foo = \"bar\" and foo1 = \"bar1\"", actual);
	}

	@Test
	void or() {
		final Filter f = Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo"), "bar");
		final Filter actual = f.or(Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo1"), "bar1"),
				Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo2"), "bar2"));

		assertFilter("foo = \"bar\" or foo1 = \"bar1\" or foo2 = \"bar2\"", actual);

		final Filter actual2 = f.and(Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo1"), "bar1"))
				.or(Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo3"), "bar3"));
		assertFilter("(foo = \"bar\" and foo1 = \"bar1\") or foo3 = \"bar3\"", actual2);

		final Filter actual3 = f.or();
		assertFilter("foo = \"bar\"", actual3);
	}

	@Test
	void orAll() {
		final Filter actual = Filter
				.orAll(Arrays.asList(new Filter[] { Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo"), "bar"),
						Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo1"), "bar1") }));
		assertFilter("foo = \"bar\" or foo1 = \"bar1\"", actual);
	}

	@Test
	void not() {
		final Filter f = Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo"), "bar");
		assertFilter("foo = \"bar\"", f);
		assertFilter("not foo = \"bar\"", f.not());
		assertFilter("foo = \"bar\"", f.not().not());

		final Filter f2 = f.and(Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo1"), "bar2"));
		assertFilter("foo = \"bar\" and foo1 = \"bar2\"", f2);
		assertFilter("not (foo = \"bar\" and foo1 = \"bar2\")", f2.not());
	}

	@Test
	void getQueryParameters() {
		final Filter subject = Filter.of(Filter.MatchingOperator.EQUAL, Selector.of("foo"), "bar");
		final Map<String, String> actual = subject.getQueryParameters();
		assertEquals(Collections.singletonMap("filter", "foo = \"bar\""), actual);
	}

	private void assertFilter(String expected, Filter subject) {
		assertEquals(expected, subject.toString());
	}
}