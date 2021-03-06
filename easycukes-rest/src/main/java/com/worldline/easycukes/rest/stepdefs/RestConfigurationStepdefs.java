/*
 * EasyCukes is just a framework aiming at making Cucumber even easier than what it already is.
 * Copyright (C) 2014 Worldline or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package com.worldline.easycukes.rest.stepdefs;

import com.worldline.easycukes.commons.Constants;
import com.worldline.easycukes.commons.DataInjector;
import com.worldline.easycukes.commons.ExecutionContext;
import com.worldline.easycukes.rest.client.RestService;

import cucumber.api.java.en.Given;

/**
 * RestConfigurationStepdefs are all the step definitions you can use in your
 * Cucumber features in order to setup some basic configuration for testing your
 * REST services.
 *
 *
 * @author aneveux
 * @version 1.0
 */
public class RestConfigurationStepdefs {

	/**
	 * Allows to set base url to be used for calling the REST services
	 *
	 * @param url
	 */
	@Given("^the base url is \"(.*?)\"$")
	public void setBaseURL(String url) {
		ExecutionContext.put(Constants.BASEURL,
				DataInjector.injectData(url));
		RestService.getInstance().initialize(
				ExecutionContext.get(Constants.BASEURL));
	}

}
