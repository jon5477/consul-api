package com.ecwid.consul.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.ecwid.consul.Utils;
import com.ecwid.consul.v1.QueryParams.Builder;

import nl.jqno.equalsverifier.EqualsVerifier;

public class QueryParamsTest {
	@Test
	public void queryParamsBuilder_ShouldReturnAllDefaults_WhenNoValuesAdded() {
		// Given
		final ConsistencyMode EXPECTED_MODE = ConsistencyMode.DEFAULT;
		final long EXPECTED_INDEX = -1;
		final long EXPECTED_WAIT_TIME = -1;
		final String EXPECTED_NEAR = null;

		// When
		QueryParams actual = Builder.builder().build();

		// Then
		assertNull(actual.getDatacenter());
		assertEquals(actual.getConsistencyMode(), EXPECTED_MODE);
		assertEquals(actual.getWaitTime(), EXPECTED_WAIT_TIME);
		assertEquals(actual.getIndex(), EXPECTED_INDEX);
		assertEquals(actual.getNear(), EXPECTED_NEAR);
	}

	@Test
	public void queryParamsBuilder_ShouldReturnQueryParams_WithCorrectValuesApplied() {
		// Given
		final String EXPECTED_DATACENTER = "testDC";
		final ConsistencyMode EXPECTED_MODE = ConsistencyMode.CONSISTENT;
		final long EXPECTED_INDEX = 100;
		final long EXPECTED_WAIT_TIME = 10000;
		final String EXPECTED_NEAR = "_agent";

		// When
		QueryParams actual = Builder.builder().setDatacenter(EXPECTED_DATACENTER).setConsistencyMode(EXPECTED_MODE)
				.setWaitTime(EXPECTED_WAIT_TIME).setIndex(EXPECTED_INDEX).setNear(EXPECTED_NEAR).build();

		// Then
		assertEquals(actual.getDatacenter(), EXPECTED_DATACENTER);
		assertEquals(actual.getConsistencyMode(), EXPECTED_MODE);
		assertEquals(actual.getIndex(), EXPECTED_INDEX);
		assertEquals(actual.getWaitTime(), EXPECTED_WAIT_TIME);
		assertEquals(actual.getNear(), EXPECTED_NEAR);
	}

	@Test
	public void queryParamsToUrlParameters_ShouldContainSetQueryParams_WithCorrectValuesApplied() {
		// Given
		final String EXPECTED_DATACENTER = "testDC";
		final ConsistencyMode EXPECTED_MODE = ConsistencyMode.CONSISTENT;
		final long EXPECTED_WAIT = 1000L;
		final long EXPECTED_INDEX = 2000L;
		final String EXPECTED_NEAR = "_agent";

		// When
		Map<String, String> queryParams = Builder.builder().setDatacenter(EXPECTED_DATACENTER)
				.setConsistencyMode(EXPECTED_MODE).setWaitTime(EXPECTED_WAIT).setIndex(EXPECTED_INDEX)
				.setNear(EXPECTED_NEAR).build().getQueryParameters();

		// Then
		assertEquals(EXPECTED_DATACENTER, queryParams.get("dc"));
		assertNull(queryParams.get(EXPECTED_MODE.name().toLowerCase()));
		assertEquals(Utils.toSecondsString(EXPECTED_WAIT), queryParams.get("wait"));
		assertEquals(EXPECTED_INDEX, queryParams.get("index"));
		assertEquals(EXPECTED_NEAR, queryParams.get("near"));
	}

	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.forClass(QueryParams.class).verify();
		}
	}
}
