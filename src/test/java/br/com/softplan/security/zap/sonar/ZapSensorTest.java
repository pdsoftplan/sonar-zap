package br.com.softplan.security.zap.sonar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;

@RunWith(MockitoJUnitRunner.class)
public class ZapSensorTest {

	@Mock
	private FileSystem fileSystem;
	
	@Mock
	private Settings settings;
	
	@Mock
	private SensorContext context;
	
	@Mock
	private File report;
	
	private ZapSensor zapSensor;
	
	@Before
	public void initZapSensor() {
		zapSensor = new ZapSensor(fileSystem, settings);
	}
	
	@Test
	public void shouldExecuteOnAnyProject() {
		assertTrue(zapSensor.shouldExecuteOnProject(new Project("")));
	}
	
	@Test
	public void testNullReportAnalysis() {
		when(settings.getString(ZapPlugin.ZAP_REPORT_PATH_PROPERTY)).thenReturn("/anyPath");
		when(fileSystem.resolvePath("/anyPath")).thenReturn(null);
		
		zapSensor.analyse(new Project(""), context);
		
		verifyZeroInteractions(context);
	}
	
	@Test
	public void testNonFileReportAnalysis() {
		when(settings.getString(ZapPlugin.ZAP_REPORT_PATH_PROPERTY)).thenReturn("/anyPath");
		when(fileSystem.resolvePath("/anyPath")).thenReturn(new File("/"));
		
		zapSensor.analyse(new Project(""), context);
		
		verifyZeroInteractions(context);
	}
	
	@Test
	public void testInvalidReportAnalysis() {
		when(settings.getString(ZapPlugin.ZAP_REPORT_PATH_PROPERTY)).thenReturn("/anyPath");
		when(fileSystem.resolvePath("/anyPath")).thenReturn(report);
		when(report.isFile()).thenReturn(true);
		when(report.toPath()).thenReturn(new File("/invalid").toPath());
		
		zapSensor.analyse(new Project(""), context);
		
		verifyZeroInteractions(context);
	}
	
	@Test
	public void testReportAnalysis() throws UnsupportedEncodingException {
		when(settings.getString(ZapPlugin.ZAP_REPORT_PATH_PROPERTY)).thenReturn(getTestReportPath());
		when(fileSystem.resolvePath(getTestReportPath())).thenReturn(getTestReportFile());
		
		zapSensor.analyse(new Project(""), context);
		
		verify(context, times(1)).saveMeasure(ZapMetrics.ALERTS, 1.0 + 2 + 3 + 4);
		verify(context, times(1)).saveMeasure(ZapMetrics.HIGH_ALERTS, 1.0);
		verify(context, times(1)).saveMeasure(ZapMetrics.MEDIUM_ALERTS, 2.0);
		verify(context, times(1)).saveMeasure(ZapMetrics.LOW_ALERTS, 3.0);
		verify(context, times(1)).saveMeasure(ZapMetrics.INFORMATIONAL_ALERTS, 4.0);
	}
	
	private File getTestReportFile() throws UnsupportedEncodingException {
		return new File(getTestReportPath());
	}
	
	private String getTestReportPath() throws UnsupportedEncodingException {
		String path = getClass().getResource("/zapReport.html").getFile();
		return URLDecoder.decode(path, StandardCharsets.UTF_8.name());
	}
	
	@Test
	public void toStringTest() {
		assertEquals(ZapSensor.class.getSimpleName(), zapSensor.toString());
	}
	
}
