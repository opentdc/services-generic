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

/**
 * A Reference to a Tag.
 * We only keep a reference (= ID) as the foreign key to a TagModel in TagsService
 * plus the commonly used derived attribute text (in the current language).
 * A TagRefModel can not be modified; therefore, modifiedAt/By are not needed.
 * @author Bruno Kaiser
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class TagRefModel {
	private String id; 		// sortable
	private String tagId; 	// mandatory
	private Date createdAt;
	private String createdBy;
	
	/******************************* Constructors *****************************/
	public TagRefModel() {
	}
	
	public TagRefModel(String tagId) {
		this.setTagId(tagId);
	}

	/********************************** setters / getters *****************************/
	/**
	 * @return the id of the TagRefModel
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Retrieve the id of the referenced TagModel
	 * @return the id of the referenced TagModel
	 */
	public String getTagId() {
		return tagId;
	}

	/**
	 * Set the id of the referenced TagModel
	 * @param tagId the id of the referenced TagModel
	 */
	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	/**
	 * Retrieve the creation date of this TagRef
	 * @return the creation date of this object
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Set the creation date of this TagRef
	 * @param createdAt the creation date of this TagRef
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Retrieve the creator of this TagRef
	 * @return the creator of this TagRef
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Set the creator of this TagRef
	 * @param createdBy the loginid of the creator of this TagRef
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	/******************************* Comparator *****************************/
	public static Comparator<TagRefModel> TagRefComparator = new Comparator<TagRefModel>() {

		public int compare(TagRefModel obj1, TagRefModel obj2) {
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
