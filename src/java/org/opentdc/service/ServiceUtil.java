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
package org.opentdc.service;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.binding.BindingFactoryManager;
import org.apache.cxf.jaxrs.JAXRSBindingFactory;
import org.apache.cxf.jaxrs.client.JAXRSClientFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.opentdc.service.exception.ValidationException;

/**
 * Utility methods for calling opentdc services.
 * @author Bruno Kaiser
 *
 */
public abstract class ServiceUtil {
	// URL path element names
	public static final String ADDRESSBOOK_PATH_EL = "addressbooks";
	public static final String CONTACT_PATH_EL = "contact";
	public static final String ORG_PATH_EL = "org";
	public static final String ADDRESS_PATH_EL = "address";
	public static final String EVENT_PATH_EL = "event";
	public static final String GIFT_PATH_EL = "gift";
	public static final String INVITATION_PATH_EL = "invitation";
	public static final String RATE_PATH_EL = "rate";
	public static final String RESOURCE_PATH_EL = "resource";
	public static final String RATEREF_PATH_EL = "rateref";
	public static final String TAG_PATH_EL = "tag";
	public static final String LANG_PATH_EL = "lang";
	public static final String TEXT_PATH_EL = "text";
	public static final String USER_PATH_EL = "users";
	public static final String WORKRECORD_PATH_EL = "workrecord";
	public static final String TAGREF_PATH_EL = "tagref";
	public static final String COMPANY_PATH_EL = "company";
	public static final String PROJECT_PATH_EL = "project";
	public static final String RESREF_PATH_EL = "resource";
	
	// service API URLs
	public static final String DEFAULT_BASE_URL = "http://localhost:8080/opentdc-services-test/";
	public static final String ADDRESSBOOKS_API_URL = "/api/" + ADDRESSBOOK_PATH_EL;
	public static final String RATES_API_URL = "/api/" + RATE_PATH_EL;
	public static final String RESOURCES_API_URL = "/api/" + RESOURCE_PATH_EL;
	public static final String TAGS_API_URL = "/api/" + TAG_PATH_EL;
	public static final String USERS_API_URL = "/api/" + USER_PATH_EL;
	public static final String WORKRECORDS_API_URL = "/api/" + WORKRECORD_PATH_EL;
	public static final String WTT_API_URL = "/api/" + COMPANY_PATH_EL;
	public static final String EVENTS_API_URL = "/api/" + EVENT_PATH_EL;
	public static final String INVITATIONS_API_URL = "/api/" + INVITATION_PATH_EL;
	public static final String TEXTS_API_URL = "/api/" + TEXT_PATH_EL;
	public static final String GIFTS_API_URL = "/api/" + GIFT_PATH_EL;	
	
	/**
	 * Creates a base URL for calling the opentdc services.
	 * @param apiUrl the service specific part of the URL, e.g. 'api/rate/'
	 * @return the full URL of an opentdc service
	 */
	public static String createUrl(
		String apiUrl) 
	{
		String _serviceUrl = DEFAULT_BASE_URL;
		if(System.getProperty("service.url") != null && 
			System.getProperty("service.url").startsWith("http://")) {
			_serviceUrl = System.getProperty("service.url");
		}
		if (!_serviceUrl.endsWith("/")) {
			_serviceUrl = _serviceUrl + "/";
		}
		return _serviceUrl + apiUrl;
	}
	
	/**
	 * Creates a CXF JAX-RS web client for calling an opentdc-service. 
	 * @param apiUrl   the URL of the opentdc service
	 * @param serviceClass the type of the service
	 * @return  a CXF JAX-RS web client that can be used for calling methods on the service
	 */
	public static WebClient createWebClient(
		String apiUrl,
		Class<?> serviceClass) 
	{
		JAXRSClientFactoryBean _sf = new JAXRSClientFactoryBean();
		_sf.setResourceClass(serviceClass);
		_sf.setAddress(createUrl(apiUrl));
		BindingFactoryManager _manager = _sf.getBus().getExtension(BindingFactoryManager.class);
		JAXRSBindingFactory _factory = new JAXRSBindingFactory();
		_factory.setBus(_sf.getBus());
		_manager.registerBindingFactory(JAXRSBindingFactory.JAXRS_BINDING_ID, _factory);
		WebClient _webclient = _sf.createWebClient();
		_webclient.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		return _webclient;
	}
	
	/**
	 * Parses a stringified date in format yyyyMMdd into a Date
	 * @param dateStr stringified date
	 * @param dateFormat the format of the String (e.g. yyyyMMdd)
	 */
	public static Date parseDate(
			String dateStr,
			String dateFormat) 
			throws ValidationException
	{
		DateFormat _formatter = new SimpleDateFormat(dateFormat);
		Date _date;
		try {
			_date = _formatter.parse(dateStr);
		} catch (ParseException _ex) {
			_ex.printStackTrace();
			throw new ValidationException("query contains incorrect date format <" + dateStr + ">; must be " + dateFormat);
		}
		return _date;
	}
	
	/**
	 * Retrieves the princial name from the servlet request.
	 * @param request the servlet request
	 * @return the principal name
	 */
	public static String getPrincipal(HttpServletRequest request) {
		Principal _principal = request.getUserPrincipal();
		if (_principal == null) {
			return "undefined";
		} else {
			return _principal.getName();
		}
	}
}
