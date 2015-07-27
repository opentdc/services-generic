/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Arbalo AG
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.opentdc.util;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.opentdc.service.exception.InternalServerErrorException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;

/**
 * FreeMarker is a template engine: a Java library to generate text output 
 * (HTML web pages, e-mails, configuration files, source code, etc.) based 
 * on templates and changing data. 
 * Templates are written in the FreeMarker Template Language (FTL).
 * @see freemarker.org
 * @author Bruno Kaiser
 */
public class FreeMarkerConfig {
	private static Configuration cfg = null;
	private static String realTemplateDirPath = null;
	private static final Logger logger = Logger.getLogger(FreeMarkerConfig.class.getName());
   
	public FreeMarkerConfig(
			ServletContext context) 
	{
		if (cfg == null) {
			String _relativeTemplateDirPath = context.getInitParameter("TemplateDir");
			realTemplateDirPath = context.getRealPath(_relativeTemplateDirPath);
			logger.info("realTemplateDirPath=<" + realTemplateDirPath + ">");
			cfg = new Configuration(Configuration.VERSION_2_3_23);
			try {
				cfg.setDirectoryForTemplateLoading(new File(realTemplateDirPath));
			} 
			catch (IOException _ex) {
				_ex.getCause();
				_ex.getMessage();
				_ex.printStackTrace();
				throw new InternalServerErrorException("could not determine the template directory");
			}
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);	
			logger.info("initConfig() -> OK");
		} else {
			logger.warning("initConfig(): cfg was already defined; did not change anything.");
		}
	}
   
 	/**
 	 * Retrieve the template by its name.
 	 * @param fileName the name of the template
 	 * @return the template
 	 * @throws InternalServerErrorException if the ftl template could not be loaded
 	 */
 	public static Template getTemplateByName(
			String fileName)
			throws InternalServerErrorException
	{
		logger.info("getTemplateByName(" + fileName + ")");
		try {
			if (cfg == null) {
				throw new InternalServerErrorException("getTemplateByName(" + fileName + "): cfg is null.");
			}
			return cfg.getTemplate(fileName);
		}
		catch(IOException _ex){
			throw new InternalServerErrorException("Error when loading ftl template: " + _ex.getMessage());
       }
   }
    
	/**
	 * Merge the template with the data into a string.
	 * @param dataModel the data
	 * @param template the Freemarker FTL template
	 * @return a string with merged data and template
	 */
	public static String getProcessedTemplate(
			Map<String, Object> dataModel, 
			Template template)
			throws InternalServerErrorException
	{
		logger.info("getProcessedTemplate(" + dataModel.toString() + ", " + template.toString() + ")");
		try {
			StringWriter textWriter = new StringWriter();
			template.process(dataModel, textWriter); 
			return textWriter.toString();
		}
		catch (Exception _ex){
			throw new InternalServerErrorException("Exception in processing template:"+_ex.getMessage());
		}
	}
}

