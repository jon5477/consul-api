package com.ecwid.consul.v1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import com.ecwid.consul.QueryParameters;

/**
 * Implements the Consul query filter parameter.
 * 
 * @see <a href=
 *      "https://developer.hashicorp.com/consul/api-docs/features/filtering">Consul
 *      Filtering</a>
 * 
 * @author ovesh
 *
 */
public class Filter implements QueryParameters {
	public enum MatchingOperator {
		EQUAL("=", false), NOT_EQUAL("!=", false), IS_EMPTY("is empty", true), IS_NOT_EMPTY("is not empty", true),
		IN("in", false), NOT_IN("not in", false), CONTAINS("contains", false), NOT_CONTAINS("not contains", false),
		MATCHES("matches", false), NOT_MATCHES("not matches", false);

		private final String representation;
		private final boolean unary;

		private MatchingOperator(String representation, boolean unary) {
			this.representation = representation;
			this.unary = unary;
		}

		@Override
		public final String toString() {
			return representation;
		}
	}

	public static final class Selector {
		private final String s;

		private Selector(@NonNull String s) {
			this.s = Objects.requireNonNull(s, "selector cannot be null");
		}

		public static Selector of(@NonNull String s) {
			return new Selector(s);
		}

		@Override
		public final int hashCode() {
			return Objects.hash(s);
		}

		@Override
		public final boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Selector)) {
				return false;
			}
			Selector other = (Selector) obj;
			return Objects.equals(s, other.s);
		}
	}

	private static String operatorValueRequired(@NonNull MatchingOperator op) {
		return "operator " + op.name() + " requires a value";
	}

	private static String operatorNoValueRequired(@NonNull MatchingOperator op) {
		return "operator " + op.name() + " does not accept a value";
	}

	/**
	 * Creates a filter with a unary operator (e.g {@link MatchingOperator#IN} or
	 * {@link MatchingOperator#NOT_IN})
	 * 
	 * @param matchingOperator The {@link MatchingOperator} to use.
	 * @param selector         The Consul filter selector (i.e. "Service.Tags" or
	 *                         "Node.Meta").
	 * @return The created {@link Filter} instance.
	 * @throws IllegalArgumentException if used with a binary matching operator
	 */
	public static Filter of(@NonNull MatchingOperator matchingOperator, @NonNull Selector selector) {
		if (!matchingOperator.unary) {
			throw new IllegalArgumentException(operatorValueRequired(matchingOperator));
		}
		return new Filter(Collections.emptyList(), BoolOp.AND, new Leaf(matchingOperator, selector.s, null), true);
	}

	/**
	 * Creates a filter with a binary operator
	 * 
	 * @throws IllegalArgumentException if used with a unary matching operator
	 *                                  ({@link MatchingOperator#IN} or
	 *                                  {@link MatchingOperator#NOT_IN}) specifying
	 *                                  a value, or if used with a binary matching
	 *                                  operator w/o specifying a value.
	 */
	public static Filter of(@NonNull MatchingOperator matchingOperator, @NonNull Selector selector,
			@Nullable String value) {
		if (matchingOperator.unary && (value != null)) {
			throw new IllegalArgumentException(operatorNoValueRequired(matchingOperator));
		}
		if (!matchingOperator.unary && (value == null)) {
			throw new IllegalArgumentException(operatorValueRequired(matchingOperator));
		}
		if (matchingOperator == MatchingOperator.IN) {
			return in(value, selector);
		}
		if (matchingOperator == MatchingOperator.NOT_IN) {
			return notIn(value, selector);
		}
		return new Filter(Collections.emptyList(), BoolOp.AND, new Leaf(matchingOperator, selector.s, value), true);
	}

	/**
	 * Creates a {@code "<Value>" in <Selector>} query
	 */
	public static Filter in(@Nullable String value, @NonNull Selector selector) {
		return new Filter(Collections.emptyList(), BoolOp.AND, new Leaf(MatchingOperator.IN, selector.s, value), true);
	}

	/**
	 * Creates a {@code "<Value>" not in <Selector>} query
	 */
	public static Filter notIn(@Nullable String value, @NonNull Selector selector) {
		return new Filter(Collections.emptyList(), BoolOp.AND, new Leaf(MatchingOperator.NOT_IN, selector.s, value),
				true);
	}

	/**
	 * Returns a new filter, with the specified filters added, all joined with
	 * 'and'.
	 */
	public Filter and(@NonNull Filter... filters) {
		return add(BoolOp.AND, filters);
	}

	/**
	 * Creates a new filter with the specified filters, all joined with 'and'.
	 */
	public static Filter andAll(@NonNull List<Filter> filters) {
		return new Filter(filters, BoolOp.AND, true);
	}

	/**
	 * Returns a new filter, with the specified filters added, all joined with 'or'.
	 */
	public Filter or(@NonNull Filter... filters) {
		return add(BoolOp.OR, filters);
	}

	/**
	 * Creates a new filter with the specified filters, all joined with 'and'.
	 */
	public static Filter orAll(@NonNull List<Filter> filters) {
		return new Filter(filters, BoolOp.OR, true);
	}

	/**
	 * Returns a negated copy of this filter. Calling this method on a negative
	 * filter will turn it into a positive filter, i.e
	 * {@code filter.not().not().equals(filter)} is true
	 */
	public final Filter not() {
		return new Filter(children, boolOp, leaf, !positive);
	}

	@Override
	public final Map<String, String> getQueryParameters() {
		return Collections.singletonMap("filter", toString());
	}

	@Override
	public final int hashCode() {
		return Objects.hash(boolOp, children, leaf, positive);
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Filter)) {
			return false;
		}
		Filter other = (Filter) obj;
		return boolOp == other.boolOp && Objects.equals(children, other.children) && Objects.equals(leaf, other.leaf)
				&& positive == other.positive;
	}

	@Override
	public final String toString() {
		String prefix = positive ? "" : "not ";
		if (leaf != null) {
			if (leaf.value != null) {
				if ((leaf.matchingOperator == MatchingOperator.IN)
						|| (leaf.matchingOperator == MatchingOperator.NOT_IN)) {
					return prefix + "\"" + leaf.value + "\" " + leaf.matchingOperator + " " + leaf.selector;
				}
				return prefix + leaf.selector + " " + leaf.matchingOperator + " \"" + leaf.value + "\"";
			}
			return prefix + leaf.selector + " " + leaf.matchingOperator;
		}
		String result = children.stream().map(Filter::toString).collect(Collectors.joining(" " + boolOp + " "));
		if ((parent == null) && positive) {
			return result;
		}
		return prefix + "(" + result + ")";
	}

	private enum BoolOp {
		OR("or"), AND("and");

		private final String representation;

		private BoolOp(String representation) {
			this.representation = representation;
		}

		@Override
		public final String toString() {
			return representation;
		}
	}

	private static final class Leaf {
		private final Filter.MatchingOperator matchingOperator;
		private final String selector;
		private final String value;

		private Leaf(MatchingOperator matchingOperator, String selector, String value) {
			this.matchingOperator = matchingOperator;
			this.selector = selector;
			this.value = value;
		}

		@Override
		public final int hashCode() {
			return Objects.hash(matchingOperator, selector, value);
		}

		@Override
		public final boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Leaf)) {
				return false;
			}
			Leaf other = (Leaf) obj;
			return matchingOperator == other.matchingOperator && Objects.equals(selector, other.selector)
					&& Objects.equals(value, other.value);
		}
	}

	private final List<Filter> children;

	private final BoolOp boolOp;
	private final Leaf leaf;
	private Filter parent;
	private final boolean positive;

	private Filter(List<Filter> children, BoolOp boolOp, boolean positive) {
		this.children = children;
		this.boolOp = boolOp;
		this.leaf = null;
		this.positive = positive;
	}

	private Filter(List<Filter> children, BoolOp boolOp, Leaf leaf, boolean positive) {
		this.children = children;
		this.boolOp = boolOp;
		this.leaf = leaf;
		this.positive = positive;
	}

	private Filter add(BoolOp op, Filter... filters) {
		List<Filter> newChildren = new ArrayList<>();
		newChildren.add(this);
		newChildren.addAll(Arrays.asList(filters));
		Filter result = new Filter(newChildren, op, null, true);
		for (Filter child : newChildren) {
			child.parent = result;
		}
		return result;
	}
}
