package br.com.softplan.security.zap.sonar;

import java.io.File;
import java.io.IOException;

import org.sonar.api.batch.Sensor;
import org.sonar.api.batch.SensorContext;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.config.Settings;
import org.sonar.api.resources.Project;
import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;

public class ZapSensor implements Sensor {

	private static final Logger LOGGER = Loggers.get(ZapSensor.class);

	private FileSystem fileSystem;
	private Settings settings;

	public ZapSensor(FileSystem fileSystem, Settings settings) {
		this.fileSystem = fileSystem;
		this.settings = settings;
	}

	@Override
	public boolean shouldExecuteOnProject(Project project) {
		return true;
	}

	@Override
	public void analyse(Project module, SensorContext context) {
		String path = settings.getString(ZapPlugin.ZAP_REPORT_PATH_PROPERTY);
		File report = fileSystem.resolvePath(path);
		if (report == null || !report.isFile()) {
			LOGGER.warn("ZAP report not found at {}", path);
			return;
		}
		parseReport(report, context);
	}

	private void parseReport(File htmlReport, SensorContext context) {
		LOGGER.info("Parsing {}", htmlReport);
		try {
			ZapReportParser.parse(htmlReport, context);
		} catch (IOException e) {
			LOGGER.error("ZAP sensor failed to parse the report file.", e);
		}
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
	
}
