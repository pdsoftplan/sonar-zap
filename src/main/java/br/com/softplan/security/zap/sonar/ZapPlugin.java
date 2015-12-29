package br.com.softplan.security.zap.sonar;

import java.util.List;

import org.sonar.api.Property;
import org.sonar.api.SonarPlugin;

import com.google.common.collect.ImmutableList;

@Property(
		key          = ZapPlugin.ZAP_REPORT_PATH_PROPERTY,
		name         = "ZAP report path",
		description  = "Path (absolute or relative) to ZAP html report file.",
		defaultValue = "target/zap-reports/zapReport.html",
		category     = "ZAP",
		project      = true
)
public final class ZapPlugin extends SonarPlugin {

	public static final String ZAP_REPORT_PATH_PROPERTY = "sonar.zap.reportPath";

	@Override
	@SuppressWarnings("rawtypes")
	public List getExtensions() {
		return ImmutableList.of(ZapMetrics.class, ZapSensor.class, ZapWidget.class);
	}

}
