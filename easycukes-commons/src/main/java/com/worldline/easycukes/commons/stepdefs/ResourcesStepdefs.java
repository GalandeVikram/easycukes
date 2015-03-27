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
package com.worldline.easycukes.commons.stepdefs;

import java.io.File;
import java.io.IOException;


import com.worldline.easycukes.commons.DataInjector;
import com.worldline.easycukes.commons.ExecutionContext;
import com.worldline.easycukes.commons.helpers.DomHelper;
import com.worldline.easycukes.commons.helpers.FileHelper;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ResourcesStepdefs are all the step definitions you can use in your Cucumber
 * features in order to deal with common actions you may require to do with
 * resources you'd need to use for your tests.
 * <p/>
 * This includes for example stepdefs like downloading resources, unzipping
 * things, etc.
 *
 * @author aneveux
 * @version 1.0
 */
public class ResourcesStepdefs {

    /**
     * Looks like a logger...
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ResourcesStepdefs.class);

    /**
     * Allows to download a ZIP file into a specified directory and unzip it in
     * that location
     *
     * @param target the location in which the ZIP file should be downloaded and
     *               extracted
     * @param url    the URL from which the ZIP file should be downloaded
     * @throws IOException if something's going wrong while manipulating that downloaded
     *                     ZIP file
     */
    @When("^I unzip into directory \"([^\"]*)\" a zip file from \"([^\"]*)\"$")
    public void unzipADownloadedZipFileInADirectory(String target, String url)
            throws IOException {
        final String localPath = DataInjector.injectData(target);
        LOGGER.info("Downloading from " + url + " to directory " + localPath);
        FileHelper.download(url, localPath);
        LOGGER.info("Download OK");
        String zipFilePath = null;

        // Get a file zip in local repository
        for (final String filePath : new File(localPath).list())
            if (filePath.contains(".zip")) {
                zipFilePath = localPath + "/" + filePath;
                break;
            }
        LOGGER.info("Unzipping file " + zipFilePath + " in local repository");
        FileHelper.unzip(zipFilePath, localPath, true);
        LOGGER.info("Unzip Done! ");
    }

    /**
     * Allows to copy a ZIP file recovered from a local file into a specified
     * directory and unzip it in that location
     *
     * @param target the location in which the ZIP file should be copied and
     *               extracted
     * @param name   the name of ZIP file
     * @throws IOException if something's going wrong while manipulating that copied ZIP
     *                     file
     */
    @When("^I unzip into directory \"([^\"]*)\" a zip file from classpath named \"([^\"]*)\"$")
    public void unzipAZipFileFromTheClasspathInADirectory(String target,
                                                          String name) throws IOException {
        final String localPath = DataInjector.injectData(target);
        final String url = "/" + name;
        LOGGER.info("Unzipping file " + url + " in local repository");
        FileHelper.unzip(url, localPath, false);
        LOGGER.info("Unzip Done! ");
    }

    /**
     * Allows to delete a directory from the file system (useful for cleaning
     * the mess we did with all the tests :p)
     *
     * @param path the path to be deleted from the file system
     * @throws Throwable if something's going wrong...
     */
    @Then("^delete directory \"([^\"]*)\"$")
    public void deleteDirectory(String path) throws Throwable {
        LOGGER.info("Deleting directory : " + path);
        final boolean result = FileHelper.deleteDirectory(new File(DataInjector
                .injectData(path)));
        if (result)
            LOGGER.info("Delete tmp directory OK ");
        else
            LOGGER.info("Delete tmp directory KO ");
    }

    /**
     * Allows to modifiy the content of an element in xml file
     *
     * @param element the element whose the text will be changed
     * @param path    the path to xml file
     * @param value   the text to set in the element content
     * @throws Throwable if there's something wrong while dealing with the response
     */
    @When("^I change the value of element \"(.*?)\" in xml file \"([^\"]*)\" to \"(.*?)\"$")
    public void changeTheValueOfAnElementInAnXMLFile(String element,
                                                     String path, String value) throws Throwable {
        DomHelper helper = new DomHelper(new File(DataInjector.injectData(path)));
        helper.setElementContent(element,
                DataInjector.injectData(value));

    }

    @When("^I set the parameter \"(.*?)\" to value of element \"(.*?)\" in xml file \"([^\"]*)\"$")
    public void setAParameterToASpecifiedValueFromAnXMLFile(String param,
                                                            String element, String path) throws Throwable {
        DomHelper helper = new DomHelper(new File(DataInjector.injectData(path)));
        ExecutionContext.put(param.toLowerCase(), helper.getElementContent(
                element));
    }

}