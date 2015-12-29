package br.com.softplan.security.zap.sonar;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class ZapPluginTest {

	@Test
	@SuppressWarnings("rawtypes")
	public void shouldIncludeAllExtensions() {
		List extensions = new ZapPlugin().getExtensions();
		
		assertEquals(3, extensions.size());
		assertTrue(extensions.contains(ZapMetrics.class));
		assertTrue(extensions.contains(ZapSensor.class));
		assertTrue(extensions.contains(ZapWidget.class));
	}
	
}
