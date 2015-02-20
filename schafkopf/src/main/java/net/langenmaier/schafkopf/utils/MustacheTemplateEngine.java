/*
 * Copyright 2014
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.langenmaier.schafkopf.utils;

/*
 * #%L
 * schafkopf
 * %%
 * Copyright (C) 2015 langenmaier.net
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import java.io.IOException;
import java.io.StringWriter;

import org.eclipse.jetty.io.RuntimeIOException;

import spark.ModelAndView;
import spark.TemplateEngine;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * Defaults to the 'templates' directory under the resource path.
 *
 * @author Sam Pullara https://github.com/spullara
 */
public class MustacheTemplateEngine extends TemplateEngine {
	private MustacheFactory mustacheFactory;

	/**
	 * Constructs a mustache template engine
	 */
	public MustacheTemplateEngine() {
		mustacheFactory = new DefaultMustacheFactory("templates");
	}

	/**
	 * Constructs a mustache template engine
	 *
	 * @param resourceRoot
	 *            the resource root
	 */
	public MustacheTemplateEngine(String resourceRoot) {
		mustacheFactory = new DefaultMustacheFactory(resourceRoot);
	}

	/**
	 * Constructs a mustache template engine
	 *
	 * @param mustacheFactory
	 *            the mustache factory
	 */
	public MustacheTemplateEngine(MustacheFactory mustacheFactory) {
		this.mustacheFactory = mustacheFactory;
	}

	@Override
	public String render(ModelAndView modelAndView) {
		String viewName = modelAndView.getViewName();
		Mustache mustache = mustacheFactory.compile(viewName);
		StringWriter stringWriter = new StringWriter();
		try {
			mustache.execute(stringWriter, modelAndView.getModel()).close();
		} catch (IOException e) {
			throw new RuntimeIOException(e);
		}
		return stringWriter.toString();
	}
}