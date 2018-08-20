package com.jk.ptk.util.mail;

import java.util.Collections;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.jk.ptk.util.UserLocale;

@Configuration
public class ThymeleafConfig {
	@Bean
	public LocaleResolver localeResolver() {
		final LocaleResolver localeResolver = new LocaleResolver() {

			@Override
			public void setLocale(HttpServletRequest arg0, HttpServletResponse arg1, Locale arg2) {

			}

			@Override
			public Locale resolveLocale(HttpServletRequest arg0) {
				return UserLocale.DEFAULT_LOCALE;
			}
		};

		return localeResolver;
	}

	@Bean
	public ITemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(Integer.valueOf(2));
        templateResolver.setResolvablePatterns(Collections.singleton("html/*"));
        templateResolver.setSuffix(".html");
        templateResolver.setPrefix("/templates/");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);
        return templateResolver;
    }
}
