package br.com.softplan.security.zap.sonar;

import org.sonar.api.web.AbstractRubyTemplate;
import org.sonar.api.web.RubyRailsWidget;
import org.sonar.api.web.WidgetCategory;

@WidgetCategory("ZapWidget")
public class ZapWidget extends AbstractRubyTemplate implements RubyRailsWidget {

	@Override
	public String getId() {
		return "zap";
	}

	@Override
	public String getTitle() {
		return "ZapWidget";
	}

	@Override
	protected String getTemplatePath() {
		return "/zap_widget.html.erb";
	}
	
}
