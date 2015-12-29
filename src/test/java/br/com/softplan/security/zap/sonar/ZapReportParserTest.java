package br.com.softplan.security.zap.sonar;

import static org.mockito.Mockito.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.sonar.api.batch.SensorContext;

@RunWith(MockitoJUnitRunner.class)
public class ZapReportParserTest {

	@Mock
	private SensorContext context;
	
	@Test
	public void testReportParsing() throws IOException {
		ZapReportParser.parse(getTestReportFile("zapReport"), context);
		assertAlertsNumber(1.0+2+3+4, 1.0, 2.0, 3.0, 4.0);
	}
	
	@Test
	public void testDifferentFormattedReportParsing() throws IOException {
		ZapReportParser.parse(getTestReportFile("differentFormattedZapReport"), context);
		assertAlertsNumber(1.0+2+3+4, 1.0, 2.0, 3.0, 4.0);
	}
	
	@Test
	public void testInvalidReportParsing() throws IOException {
		ZapReportParser.parse(getTestReportFile("invalidZapReport"), context);
		assertAlertsNumber(0.0, null, null, null, null);
	}
	
	@Test
	public void testMissingLevelReportParsing() throws IOException  {
		ZapReportParser.parse(getTestReportFile("missingMediumAlertsZapReport"), context);
		assertAlertsNumber(1.0+3+4, 1.0, null, 3.0, 4.0);
	}
	
	private File getTestReportFile(String fileName) throws UnsupportedEncodingException {
		String path = getClass().getResource("/" + fileName + ".html").getFile();
		return new File(URLDecoder.decode(path, StandardCharsets.UTF_8.name()));
	}
	
	private void assertAlertsNumber(Double alerts, Double high, Double medium, Double low, Double info) {
		verify(context, times(1)).saveMeasure(ZapMetrics.ALERTS, alerts);
		verify(context, times(1)).saveMeasure(ZapMetrics.HIGH_ALERTS, high);
		verify(context, times(1)).saveMeasure(ZapMetrics.MEDIUM_ALERTS, medium);
		verify(context, times(1)).saveMeasure(ZapMetrics.LOW_ALERTS, low);
		verify(context, times(1)).saveMeasure(ZapMetrics.INFORMATIONAL_ALERTS, info);
	}
	
}
