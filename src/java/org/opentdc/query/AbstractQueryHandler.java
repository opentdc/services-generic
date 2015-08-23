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

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.opentdc.query.QueryOperator;
import org.opentdc.query.SortPredicate;
import org.opentdc.service.ServiceUtil;
import org.opentdc.service.exception.ValidationException;

/**
 * Abstract class for service-specific query handlers. Provides generic functionality used in all the specific handlers.
 * @author Bruno Kaiser
 *
 */
public abstract class AbstractQueryHandler {
	protected List<SortPredicate> sortPredicates = null;
	protected static final Logger logger = Logger.getLogger(AbstractQueryHandler.class.getName());
	
	/**
	 * @param realValue
	 * @param operator
	 * @param expectedValues
	 * @return
	 */
	protected boolean evaluateStringOperation(
			String realValue, 
			QueryOperator operator, 
			String[] expectedValues) 
	{
		boolean _retVal = true;
		if (realValue == null) {
			return false;
		}
		switch(operator) {
			case EQUALTO:					
				_retVal = realValue.compareToIgnoreCase(expectedValues[0]) == 0; 
				break;
			case GREATERTHAN:				
				_retVal = realValue.compareToIgnoreCase(expectedValues[0]) > 0; 
				break;
			case GREATERTHANOREQUALTO:		
				_retVal = realValue.compareToIgnoreCase(expectedValues[0]) >= 0; 
				break;
			case LESSTHAN:					
				_retVal = realValue.compareToIgnoreCase(expectedValues[0]) < 0; 
				break;
			case LESSTHANOREQUALTO:			
				_retVal = realValue.compareToIgnoreCase(expectedValues[0]) <= 0; 
				break;
			case NOTEQUALTO:				
				_retVal = realValue.compareToIgnoreCase(expectedValues[0]) != 0; 
				break;
			case ISLIKE:	
				_retVal = realValue.toLowerCase().contains(expectedValues[0].toLowerCase()); 
				break;
			case NONE:	
			default:						_retVal = true; break;
		}
		logger.info("evaluateStringOperation(<" + realValue + ">.<" + 
				operator + ">=<" + expectedValues[0] + ">) -> " + _retVal);
		return _retVal;
	}
	
	/**
	 * @param realValue
	 * @param operator
	 * @param expectedValues
	 * @return
	 */
	protected boolean evaluateDateOperation(
			Date realValue, 
			QueryOperator operator, 
			String[] expectedValues) 
			throws ValidationException
	{
		Date _expectedDate = ServiceUtil.parseDate(expectedValues[0], "yyyyMMdd");
		boolean _retVal = true;
		if (realValue == null) {
			return false;
		}
		switch(operator) {
			case EQUALTO:					_retVal = realValue.compareTo(_expectedDate) == 0; break;
			case GREATERTHAN:				_retVal = realValue.compareTo(_expectedDate) > 0; break;
			case GREATERTHANOREQUALTO:		_retVal = realValue.compareTo(_expectedDate) >= 0; break;
			case LESSTHAN:					_retVal = realValue.compareTo(_expectedDate) < 0; break;
			case LESSTHANOREQUALTO:			_retVal = realValue.compareTo(_expectedDate) <= 0; break;
			case NOTEQUALTO:				_retVal = realValue.compareTo(_expectedDate) != 0; break;
			case ISLIKE:					throw new ValidationException("ISLIKE is not implemented on dates (unclear what that would mean)");
			case NONE:
			default:						break;
		}
		logger.info("evaluateDateOperation(>" + realValue + ">.<" + operator + ">=<" + _expectedDate + ">) -> " + _retVal);
		return _retVal;
	}
	
	/**
	 * @param realValue
	 * @param operator
	 * @param expectedValues
	 * @return
	 */
	protected boolean evaluateBooleanOperation(
			boolean realValue, 
			QueryOperator operator, 
			String[] expectedValues) 
			throws ValidationException
	{
		logger.info("evaluateBooleanOperation(" + realValue + ", " + operator + ", " + expectedValues[0] + ")");
		boolean _retVal = true;
		boolean _expectedValue = Boolean.parseBoolean(expectedValues[0]);
		switch(operator) {
			case EQUALTO:					_retVal = realValue == _expectedValue;  break;
			case NOTEQUALTO:				_retVal = realValue != _expectedValue;  break;
			case GREATERTHAN:				
			case GREATERTHANOREQUALTO:	
			case LESSTHAN:					
			case LESSTHANOREQUALTO:		
			case ISLIKE:					
			case NONE:	
			default:						throw new ValidationException("operator " + operator + " is not implemented for boolean value.");
		}
		logger.info("evaluateBooleanOperation(" + realValue + ", " + operator + ", " + _expectedValue + ") -> " + _retVal);
		return _retVal;
	}
	
	/**
	 * @param realValue
	 * @param operator
	 * @param expectedValue
	 * @return
	 */
	protected boolean evaluateIntOperation(
			int realValue, 
			QueryOperator operator, 
			String[] expectedValues) 
			throws ValidationException
	{
		boolean _retVal = true;
		int _expectedValue = Integer.parseInt(expectedValues[0]);
		switch(operator) {
			case EQUALTO:					_retVal = realValue == _expectedValue; break;
			case NOTEQUALTO:				_retVal = realValue != _expectedValue; break;
			case GREATERTHAN:				_retVal = realValue > _expectedValue; break;
			case GREATERTHANOREQUALTO:		_retVal = realValue >= _expectedValue; break;
			case LESSTHAN:					_retVal = realValue < _expectedValue; break;
			case LESSTHANOREQUALTO:			_retVal = realValue <= _expectedValue; break;
			case ISLIKE:					
			case NONE:	
			default:						throw new ValidationException("operator " + operator + " is not implemented for int value.");
		}
		logger.info("evaluateIntOperation(" + realValue + ", " + operator + ", " + expectedValues[0] + ") -> " + _retVal);
		return _retVal;
	}
}
