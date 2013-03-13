package com.colinalworth.gwtdriver.gxt.models;

/*
 * #%L
 * Sencha GXT classes for gwt-driver
 * %%
 * Copyright (C) 2012 - 2013 Sencha Labs
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.colinalworth.gwtdriver.by.ByNearestWidget;
import com.colinalworth.gwtdriver.by.ByWidget;
import com.colinalworth.gwtdriver.by.FasterByChained;
import com.colinalworth.gwtdriver.gxt.models.Field.FieldFinder;
import com.colinalworth.gwtdriver.models.GwtWidget;
import com.colinalworth.gwtdriver.models.GwtWidgetFinder;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

public class Field extends GwtWidget<FieldFinder> {

	public Field(WebDriver driver, WebElement element) {
		super(driver, element);
	}

	public void sendKeys(String text) {
		getElement().findElement(By.tagName("input")).sendKeys(text);
	}

	public static class FieldFinder extends GwtWidgetFinder<Field> {
		private String text;
		public FieldFinder withText(String text) {
			this.text = text;
			return this;
		}
		
		private String fieldLabel;
		public FieldFinder withLabel(String label) {
			this.fieldLabel = label;
			return this;
		}
		@Override
		public FieldFinder withDriver(WebDriver driver) {
			return (FieldFinder) super.withDriver(driver);
		}
		@Override
		public Field done() {
			if (fieldLabel != null) {
				elt = elt.findElement(new FasterByChained(By.xpath(".|.//*[contains(text(), '"+fieldLabel+"')]"),
						new ByNearestWidget(driver, FieldLabel.class),
						new FasterByChained(
								By.xpath("*|*/*"),
								new ByWidget(driver, com.sencha.gxt.widget.core.client.form.Field.class)
						)));
				return new Field(driver, elt);
			} else if (text == null) {
				return new Field(driver, elt.findElement(new FasterByChained(
						By.xpath(".|.//*[contains(text(), '"+fieldLabel+"')]"),
						new ByNearestWidget(driver, com.sencha.gxt.widget.core.client.form.Field.class)
						)));
			} else {
				return new Field(driver, elt.findElement(new ByNearestWidget(driver, com.sencha.gxt.widget.core.client.form.Field.class)));
			}
		}
	}
}
