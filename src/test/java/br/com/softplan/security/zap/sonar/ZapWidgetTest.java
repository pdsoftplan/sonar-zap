package br.com.softplan.security.zap.sonar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class ZapWidgetTest {

	@Test
	public void widgetIdShouldBeTheSameAsThePluginId() {
		assertEquals(new ZapWidget().getId(), "zap");
	}
	
	@Test
	public void widgetTitleShouldBeThePluginNamePostfixedWithWidget() {
		assertEquals(new ZapWidget().getTitle(), "ZapWidget");
	}
	
	@Test
	public void templatePathShouldPointToValidFile() throws UnsupportedEncodingException, URISyntaxException {
		Path widgetTemplateFile = getWidgetTemplateFile(new ZapWidget());
		
		assertTrue(widgetTemplateFile != null);
		assertTrue(Files.isRegularFile(widgetTemplateFile));
	}

	private Path getWidgetTemplateFile(ZapWidget zapWidget) throws UnsupportedEncodingException, URISyntaxException {
		URL widgetTemplateUrl = getClass().getResource(zapWidget.getTemplatePath());
		return Paths.get(widgetTemplateUrl.toURI());
	}
	
}
