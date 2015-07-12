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

import java.util.Comparator;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.opentdc.util.LanguageCode;

/**
 * A Localized Text.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class LocalizedTextModel {
	private String id;				// sortable, server sets the id to the same value as langCode
	private LanguageCode langCode; 	// unique, ISO 639-1 code
	private String text;			// mandatory
	private Date createdAt;
	private String createdBy;
	private Date modifiedAt;
	private String modifiedBy;

	/******************************* Constructors *****************************/
	public LocalizedTextModel() {
	}

	public LocalizedTextModel(LanguageCode langCode, String text) {
		this.langCode = langCode;
		this.text = text;
	}

	/********************************** ID *****************************/
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/********************************** Title *****************************/
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	
	/**
	 * Retrieving the language code according to ISO 639-1 (2 chars).
	 * @return the langCode
	 */
	public LanguageCode getLangCode() {
		return langCode;
	}

	/**
	 * Setting the language code according to ISO 639-1 (2 chars).
	 * @see https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes
	 * @see http://www.loc.gov/standards/iso639-2/
	 * @param langCode the langCode to set
	 */
	public void setLangCode(LanguageCode langCode) {
		this.langCode = langCode;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Date modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/******************************* Comparator *****************************/
	public static Comparator<LocalizedTextModel> LocalizedTextComparator = new Comparator<LocalizedTextModel>() {

		public int compare(LocalizedTextModel obj1, LocalizedTextModel obj2) {
			if (obj1.getId() == null) {
				return -1;
			}
			if (obj2.getId() == null) {
				return 1;
			}

			String _attr1 = obj1.getId();
			String _attr2 = obj2.getId();

			// ascending order
			return _attr1.compareTo(_attr2);

			// descending order
			// return _attr2.compareTo(_attr1);
		}
	};
}
