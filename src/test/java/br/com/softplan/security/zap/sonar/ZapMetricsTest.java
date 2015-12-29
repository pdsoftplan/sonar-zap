package br.com.softplan.security.zap.sonar;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.sonar.api.measures.Metric;

public class ZapMetricsTest {

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldIncludeAllMetrics() {
		List<Metric> metrics = new ZapMetrics().getMetrics();
		
		assertEquals(5, metrics.size());
		assertTrue(metrics.contains(ZapMetrics.ALERTS));
		assertTrue(metrics.contains(ZapMetrics.HIGH_ALERTS));
		assertTrue(metrics.contains(ZapMetrics.MEDIUM_ALERTS));
		assertTrue(metrics.contains(ZapMetrics.LOW_ALERTS));
		assertTrue(metrics.contains(ZapMetrics.INFORMATIONAL_ALERTS));
	}
	
}
