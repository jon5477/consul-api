package com.ecwid.consul.v1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Duration;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.ecwid.consul.Utils;

import nl.jqno.equalsverifier.EqualsVerifier;

class QueryParamsTest {
	@Test
	void queryParamsBuilder_ShouldReturnAllDefaults_WhenNoValuesAdded() {
		// Given
		ConsistencyMode expectedMode = ConsistencyMode.DEFAULT;
		long expectedIndex = -1;
		String expectedNear = null;

		// When
		QueryParams actual = new QueryParams.Builder().build();

		// Then
		assertNull(actual.getDatacenter());
		assertEquals(actual.getConsistencyMode(), expectedMode);
		assertNull(actual.getWaitTime());
		assertEquals(actual.getIndex(), expectedIndex);
		assertEquals(actual.getNear(), expectedNear);
	}

	@Test
	void queryParamsBuilder_ShouldReturnQueryParams_WithCorrectValuesApplied() {
		// Given
		String expectedDatacenter = "testDC";
		ConsistencyMode expectedMode = ConsistencyMode.CONSISTENT;
		long expectedIndex = 100;
		Duration expectedWaitTime = Duration.ofMinutes(10);
		String expectedNear = "_agent";

		// When
		QueryParams actual = new QueryParams.Builder().setDatacenter(expectedDatacenter)
				.setConsistencyMode(expectedMode).setWaitTime(expectedWaitTime).setIndex(expectedIndex)
				.setNear(expectedNear).build();

		// Then
		assertEquals(actual.getDatacenter(), expectedDatacenter);
		assertEquals(actual.getConsistencyMode(), expectedMode);
		assertEquals(actual.getIndex(), expectedIndex);
		assertEquals(actual.getWaitTime(), expectedWaitTime);
		assertEquals(actual.getNear(), expectedNear);
	}

	@Test
	void queryParamsToUrlParameters_ShouldContainSetQueryParams_WithCorrectValuesApplied() {
		// Given
		String expectedDatacenter = "testDC";
		ConsistencyMode expectedMode = ConsistencyMode.CONSISTENT;
		Duration expectedWaitTime = Duration.ofMinutes(1);
		long expectedIndex = 2000L;
		String expectedNear = "_agent";

		// When
		Map<String, String> queryParams = new QueryParams.Builder().setDatacenter(expectedDatacenter)
				.setConsistencyMode(expectedMode).setWaitTime(expectedWaitTime).setIndex(expectedIndex)
				.setNear(expectedNear).build().getQueryParameters();

		// Then
		assertEquals(expectedDatacenter, queryParams.get("dc"));
		assertNull(queryParams.get(expectedMode.name().toLowerCase(Locale.ROOT)));
		assertEquals(Utils.toConsulDuration(expectedWaitTime), queryParams.get("wait"));
		assertEquals(String.valueOf(expectedIndex), queryParams.get("index"));
		assertEquals(expectedNear, queryParams.get("near"));
	}

	@Nested
	class EqualsAndHashCode {
		@Test
		void shouldVerify() {
			EqualsVerifier.forClass(QueryParams.class).verify();
		}
	}
}