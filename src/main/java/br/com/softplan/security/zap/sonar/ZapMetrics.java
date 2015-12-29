package br.com.softplan.security.zap.sonar;

import java.util.List;

import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;

import com.google.common.collect.ImmutableList;

public final class ZapMetrics implements Metrics {

	public static final Metric<Integer> ALERTS = new Metric.Builder("zap-alerts", "ZAP Alerts", Metric.ValueType.INT)
			.setDescription("Number of alerts identified by ZAP.")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(true)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.setBestValue(0.0)
			.create();
	
	public static final Metric<Integer> HIGH_ALERTS = new Metric.Builder("zap-high-alerts", "ZAP High Alerts", Metric.ValueType.INT)
			.setDescription("Number of high risk level alerts identified by ZAP.")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(true)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.setBestValue(0.0)
			.create();
	
	public static final Metric<Integer> MEDIUM_ALERTS = new Metric.Builder("zap-medium-alerts", "ZAP Medium Alerts", Metric.ValueType.INT)
			.setDescription("Number of medium risk level alerts identified by ZAP.")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(true)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.setBestValue(0.0)
			.create();
	
	public static final Metric<Integer> LOW_ALERTS = new Metric.Builder("zap-low-alerts", "ZAP Low Alerts", Metric.ValueType.INT)
			.setDescription("Number of low risk level alerts identified by ZAP.")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(true)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.setBestValue(0.0)
			.create();
	
	public static final Metric<Integer> INFORMATIONAL_ALERTS = new Metric.Builder("zap-informational-alerts", "ZAP Informational Alerts", Metric.ValueType.INT)
			.setDescription("Number of informational risk level alerts identified by ZAP.")
			.setDirection(Metric.DIRECTION_WORST)
			.setQualitative(true)
			.setDomain(CoreMetrics.DOMAIN_GENERAL)
			.setBestValue(0.0)
			.create();
	
	@Override
	@SuppressWarnings("rawtypes")
	public List<Metric> getMetrics() {
		return ImmutableList.<Metric>of(ALERTS, HIGH_ALERTS, MEDIUM_ALERTS, LOW_ALERTS, INFORMATIONAL_ALERTS);
	}

}
