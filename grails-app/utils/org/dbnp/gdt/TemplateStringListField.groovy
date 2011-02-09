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

class TemplateStringListField extends TemplateFieldTypeNew {
	static String type			= "STRINGLIST"
	static String casedType		= "StringList"
	static String description	= "Dropdown selection of items"
	static String category		= "Text"
	static String example		= ""

	/**
	 * Static validator closure
	 * @param fields
	 * @param obj
	 * @param errors
	 */
	static def validator = { fields, obj, errors ->
		genericValidator(fields, obj, errors, TemplateFieldListItem, { value -> (value as TemplateFieldListItem) })
	}

	/**
	 * cast value to the proper type (if required and if possible)
	 * @param TemplateField field
	 * @param mixed value
	 * @return String
	 * @throws IllegalArgumentException
	 */
	static TemplateFieldListItem castValue(org.dbnp.gdt.TemplateField field, java.lang.String value, def currentValue) {
		def escapedLowerCaseValue = value.toLowerCase().replaceAll("([^a-z0-9])", "_")
		def item = field.listEntries.find { listEntry ->
			listEntry.name.toLowerCase().replaceAll("([^a-z0-9])", "_") == escapedLowerCaseValue
		}

		if (!item) {
			throw new IllegalArgumentException("Stringlist item not recognized: ${value}")
		}

		return item
	}
}
