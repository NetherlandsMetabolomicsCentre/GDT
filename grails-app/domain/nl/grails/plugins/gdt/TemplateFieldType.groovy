package nl.grails.plugins.gdt

/**
 * Enum describing the type of a TemplateField.
 *
 * Revision information:
 * $Rev: 1385 $
 * $Author: business@keesvanbochove.nl $
 * $Date: 2011-01-13 00:24:47 +0100 (Thu, 13 Jan 2011) $
 */
public enum TemplateFieldType implements Serializable {
	STRING		('Short text'			, 'Text'		, 'max 255 chars'),			// string
	TEXT		('Long text'			, 'Text'		, 'unlimited number of chars'),	// text
	//INTEGER	('Integer number'		, 'Numerical'	, '1'),						// integer
	//FLOAT		('Floating-point number', 'Numerical'	, '1.0'),					// float
	DOUBLE		('Decimal number'		, 'Numerical'	, '1.31'),					// double
	STRINGLIST	('Dropdown selection of terms', 'Text'	, ''),						// string list
	ONTOLOGYTERM('Term from ontology'	, 'Other'		, 'A term that comes from one or more selected ontologies'),						// ontology reference
	DATE		('Date'					, 'Date'		, '2010-01-01'),			// date
	RELTIME		('Relative time'		, 'Date'		, '3 days'),				// relative date, e.g. days since start of study
	FILE		('File'					, 'Other'		, '')		,				// file
	BOOLEAN		('True/false'			, 'Other'		, 'true/false'),			// boolean
	TEMPLATE	('Template'				, 'Other'		, ''),						// template
	MODULE		('Omics module'			, 'Other'		, ''),						// third party connected module,
	LONG		('Natural number'		, 'Numerical'	, '100')					// long
    // TODO: add a timezone-aware date type to use for study start date

    String name
	String category
	String example

	TemplateFieldType(String name) {
		this.name = name
	}
	TemplateFieldType(String name, String category, String example) {
		this.name = name
		this.category = category
		this.example = example
	}

	static list() {
		[STRING, TEXT, DOUBLE, STRINGLIST, ONTOLOGYTERM, DATE, RELTIME, FILE, BOOLEAN, TEMPLATE, MODULE, LONG]
	}

	def getDefaultValue() {
		switch (this) {
			case [STRING, TEXT]:
				return ""
			case DOUBLE:
				return Double.MIN_VALUE
			case STRINGLIST:
				return null
			case ONTOLOGYTERM:
				return null
			case DATE:
				return null
			case RELTIME:
				return null
			case FILE:
				return ""
			case BOOLEAN:
				return false
			case TEMPLATE:
				return null
			case MODULE:
				return null
			case LONG:
				return Long.MIN_VALUE
			default:
				throw new NoSuchFieldException("Field type ${fieldType} not recognized")
		}
	}
}