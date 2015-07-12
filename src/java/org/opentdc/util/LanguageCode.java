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

import java.util.HashMap;
import java.util.Map;

/**
 * The language code according to ISO 639-1 (2 chars).
 * Only a commonly used sub-selection of languages is supported.
 * Additional languages can be added if needed.
 * @see https://en.wikipedia.org/wiki/List_of_ISO_639-1_codes
 * @see http://www.loc.gov/standards/iso639-2/
 * @author Bruno Kaiser
 *
 */
public enum LanguageCode {
	// AB("Abkhaz"),
	// AA("Afar"),
	// AF("Afrikaans"),
	// ...
	EN("English"),
	FR("French"),
	DE("German"),
	IT("Italian"),
	RM("Rumantsch Grischun"),
	ES("Spanish");

	private String label;
	private static Map<String, LanguageCode> stringToEnumMapping;

	/**
	 * Constructor.
	 * @param label the label of the language code.
	 */
	private LanguageCode(String label) {
		this.label = label;
	}

	/**
	 * Returns the language code based on its label.
	 * @param label the label of the language code.
	 * @return the language code
	 */
	public static LanguageCode getLanguageCode(String label) {
		if (stringToEnumMapping == null) {
			initMapping();
		}
		return stringToEnumMapping.get(label);
	}

	/**
	 * Initializes the mappings between label and language code.
	 */
	private static void initMapping() {
		stringToEnumMapping = new HashMap<String, LanguageCode>();
		for (LanguageCode _lc : values()) {
			stringToEnumMapping.put(_lc.label, _lc);
		}
	}

	/**
	 * Get the label. The label is the usual name of the language code in english.
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Get the default language code (EN). 
	 * @return the default language code
	 */
	public static LanguageCode getDefaultLanguageCode() {
		return EN;
	}
}
