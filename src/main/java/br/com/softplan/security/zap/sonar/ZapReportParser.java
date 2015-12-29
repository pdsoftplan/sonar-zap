package br.com.softplan.security.zap.sonar;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.sonar.api.batch.SensorContext;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public final class ZapReportParser {

	private static final Logger LOGGER = Loggers.get(ZapReportParser.class);
	
	private static final String ALERTS_REGEX = "<a.*href.*=.*\"#(high|medium|low|info).*<font.*>(\\d+)</font>";
	private static final Pattern ALERTS_PATTERN = Pattern.compile(ALERTS_REGEX);
	
	private double alerts;
	private Map<String, Double> alertsMap = new HashMap<>();
	
	private ZapReportParser() {}
	
	public static void parse(File htmlReport, SensorContext context) throws IOException {
		new ZapReportParser().parseReportAndSaveMeasures(htmlReport, context);
	}
	
	private void parseReportAndSaveMeasures(File htmlReport, SensorContext context) throws IOException {
		parseReport(htmlReport);
		saveMeasures(context);
	}

	private void parseReport(File htmlReport) throws IOException {
		String reportString = new String(Files.readAllBytes(htmlReport.toPath()), StandardCharsets.UTF_8);
		
		Matcher matcher = ALERTS_PATTERN.matcher(reportString);
		while (matcher.find()) {
			String alertsLevel = matcher.group(1);
			double alertsNumber = Double.parseDouble(matcher.group(2)); 
			alertsMap.put(alertsLevel, alertsNumber);
		}
	}
	
	private void saveMeasures(SensorContext context) {
		Double highAlerts   = computeAlertsNumber("high");
		Double mediumAlerts = computeAlertsNumber("medium");
		Double lowAlerts    = computeAlertsNumber("low");
		Double infoAlerts   = computeAlertsNumber("info");
		
		context.saveMeasure(ZapMetrics.ALERTS, alerts);
		context.saveMeasure(ZapMetrics.HIGH_ALERTS, highAlerts);
		context.saveMeasure(ZapMetrics.MEDIUM_ALERTS, mediumAlerts);
		context.saveMeasure(ZapMetrics.LOW_ALERTS, lowAlerts);
		context.saveMeasure(ZapMetrics.INFORMATIONAL_ALERTS, infoAlerts);
	}

	private Double computeAlertsNumber(String level) {
		Double levelAlerts = alertsMap.get(level);
		if (levelAlerts != null) {
			alerts += levelAlerts;
			LOGGER.debug("ZAP {} alerts = {}", level, levelAlerts);
		} else {
			LOGGER.warn("ZAP {} alerts number was not found in the report.", level);
		}
		return levelAlerts;
	}
	
}
