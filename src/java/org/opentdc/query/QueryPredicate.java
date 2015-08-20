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
package org.opentdc.query;

import java.util.logging.Logger;

import org.opentdc.service.exception.ValidationException;

/**
 * Represents a single query predicate with the following syntax:
 * {quantor}{feature}().{operator}(values);
 * quantor ::= "thereExists" | "forAll";
 * feature ::= identifier;
 * operator ::= "equalTo" | "greaterThan" | "greaterThanOrEqualTo" |
 * "lessThan" | "lessThanOrEqualTo" | "notEqualTo" | "isLike";
 * values ::=  value { "," value};
 * value ::= ":dateTime:" date | [":integer:"] integer | ":decimal:"
 * decimal | [":string:"] string | ":boolean:" boolean.
 * 
 * @author Bruno Kaiser
 *
 */
public abstract class QueryPredicate {
	protected String predicate = null;
	protected QueryQuantor quantor = null;
	protected String feature = null;
	protected QueryOperator operator = null;
	protected String[] values = null;
	protected static final Logger logger = Logger.getLogger(QueryPredicate.class.getName());
	
	/**
	 * Parse a quantor from a stringified query predicate.
	 * @param predicate the stringified query predicate (part of the query string)
	 * @return the quantor parsed
	 * @throws ValidationException if the predicate was null or empty
	 */
	protected static QueryQuantor parseQuantor(
			String predicate) 
			throws ValidationException {
		QueryQuantor _quantor = null;
		if (predicate == null || predicate.isEmpty()) {
			throw new ValidationException("empty predicate found");
		}
		if (predicate.startsWith("forAll")) {
			_quantor = QueryQuantor.FOR_ALL;
		} else {
			if (predicate.startsWith("thereExists")) {
				_quantor = QueryQuantor.THERE_EXISTS;
			} else {
				_quantor = QueryQuantor.NONE;
			}
		}	
		logger.info("parseQuantor(" + predicate + ") -> " + _quantor);
		return _quantor;
	}
	
	/**
	 * Parse a feature from a stringified query predicate.
	 * @param predicate the stringified query predicate (part of the query string)
	 * @param token the feature token (part of the predicate)
	 * @param offset the start of the feature string
	 * @return the parsed feature string
	 * @throws ValidationException if the feature token is missing or has a wrong syntax.
	 */
	protected static String parseFeature(
			String predicate,
			String token,
			int offset)
			throws ValidationException {
		logger.info("parseFeature(" + predicate + ", " + token + ", " + offset + ")");
		if (token.length() <= offset + 3) {
			throw new ValidationException("invalid predicate found: <" + predicate + ">. Feature missing. Correct syntax is {quantor}{feature}().{operator}(values).");
		}
		logger.info("parseFeature() -> " + token.substring(offset, token.length() - 2));
		return (token.substring(offset, token.length() - 2));
	}
	
	/**
	 * Parse an operator from a stringified query predicate.
	 * @param predicate the stringified query predicate (part of the query string)
	 * @param token the operator token (part of the predicate)
	 * @return the parsed operator
	 * @throws ValidationException if the operator token is missing or has a wrong syntax.
	 */
	protected static QueryOperator parseOperator(
			String predicate,
			String token)
			throws ValidationException {
		logger.info("parseOperator(" + predicate + ", " + token + ")");
		QueryOperator _queryOperator = null;
		if (token.length() <= 0) {
			throw new ValidationException("invalid predicate found: <" + predicate + ">. Operator missing. Correct syntax is {quantor}{feature}().{operator}(values).");
		}
		try {
			_queryOperator = QueryOperator.valueOf(token.toUpperCase());
		}
		catch (Exception _ex) {
			throw new ValidationException("invalid predicate found: <" + predicate + ">. Invalid operator found: <" + token + ">. Correct syntax is {quantor}{feature}().{operator}(values).");			
		}
		logger.info("parseOperator() -> " + _queryOperator);
		return _queryOperator;
	}
	
	/**
	 * Determine the offset within the predicate after the quantor string.
	 * @param quantor
	 * @return the offset
	 */
	protected static int getOffset(QueryQuantor quantor) {
		int _offset = 0;
		switch (quantor) {
		case FOR_ALL:		_offset = 6; break;
		case THERE_EXISTS:	_offset = 11; break;
		default:			_offset = 0; break;
		}
		
		logger.info("getOffset(" + quantor + ") -> " + _offset);
		return _offset;
	}
	
	/**
	 * Parse the values from a stringified query predicate.
	 * @param predicate the stringified query predicate (part of the query string)
	 * @param token the token of values (part of the predicate) with the following syntax:   token ::= value { "," value } ")"
	 * @return the parsed array of value strings
	 * @throws ValidationException
	 */
	protected static String[] parseValues(
			String predicate,
			String token) 
			throws ValidationException {
		logger.info("parseValues(" + predicate + ", " + token + ")");
		return token.split(",");		
	}

	/**
	 * @return the quantor
	 */
	public QueryQuantor getQuantor() {
		return quantor;
	}

	/**
	 * Set the quantor
	 * @param quantor
	 */
	public void setQuantor(QueryQuantor quantor) {
		this.quantor = quantor;
	}

	/**
	 * @return the feature
	 */
	public String getFeature() {
		return feature;
	}

	/**
	 * Set the feature
	 * @param feature
	 */
	public void setFeature(String feature) {
		this.feature = feature;
	}

	/**
	 * @return the operator
	 */
	public QueryOperator getOperator() {
		return operator;
	}

	/**
	 * Set the operator
	 * @param operator
	 */
	public void setOperator(QueryOperator operator) {
		this.operator = operator;
	}

	/**
	 * @return the values
	 */
	public String[] getValues() {
		return values;
	}

	/**
	 * Set the values
	 * @param values
	 */
	public void setValues(String[] values) {
		this.values = values;
	}

	/**
	 * @return the predicate
	 */
	public String getPredicate() {
		return predicate;
	}

	/**
	 * Set the predicate
	 * @param predicate
	 */
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}
}
