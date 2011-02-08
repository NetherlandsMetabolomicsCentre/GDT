/**
 *  GDT, a plugin for Grails Domain Templates
 *  Copyright (C) 2011 Jeroen Wesbeek
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  $Author$
 *  $Rev$
 *  $Date$
 */
package org.dbnp.gdt

class TemplateTextField extends TemplateFieldTypeNew {
	static String type			= "TEXT"
	static String casedType		= "Text"
	static String description	= "Long text"
	static String category		= "Text"
	static String example		= "unlimited number of characters"

	/**
	 * Static validator closure
	 * @param fields
	 * @param obj
	 * @param errors
	 */
	static def validator = { fields, obj, errors ->
		genericValidator(fields, obj, errors, TemplateFieldType.STRING, { value -> (value as String) })
	}

	/**
	 * cast value to the proper type (if required and if possible)
	 * @param TemplateField field
	 * @param mixed value
	 * @return String
	 * @throws IllegalArgumentException
	 */
	static def castValue(org.dbnp.gdt.TemplateField field, java.lang.String value) {
		if (value.class != String) {
			throw new IllegalArgumentException("Argument not a String: ${value}")
		}

		return value
	}
}