/**
 *  GDT, a plugin for Grails Domain Templates
 *  Copyright (C) 2011 Jeroen Wesbeek, Kees van Bochove
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

import grails.test.*

class TemplateEntityTests extends GrailsUnitTestCase {

	def testStudy;
	def eventTemplate1;
	def eventTemplate2;
	def sampleTemplate1;

	protected void setUp() {
		// We create a study with 2 events (with 2 different templates),
		// 2 samples with the same template + 1 sample without a template and
		// 2 subjects without a template
		// There are no sampling events
		super.setUp()

		// Create the template itself
		eventTemplate1 = new Template(
			name: 'Event Template 1',
			entity: dbnp.studycapturing.Event,
			fields: [
				new TemplateField(
					name: 'testStartDate',
					type: TemplateFieldType.DATE,
					entity: dbnp.studycapturing.Event
				)
			]
		);

		// Create the template itself
		eventTemplate2 = new Template(
			name: 'Event Template 2',
			entity: dbnp.studycapturing.Event,
			fields: [
				new TemplateField(
					name: 'testStartDate',
					type: TemplateFieldType.RELTIME,
					entity: dbnp.studycapturing.Event
				)
			]
		);

		// Create the template itself
		sampleTemplate1 = new Template(
			name: 'Sample Template 1',
			entity: dbnp.studycapturing.Event,
			fields: [
				new TemplateField(
					name: 'testStartDate',
					type: TemplateFieldType.RELTIME,
					entity: dbnp.studycapturing.Event
				)
			]
		);

		testStudy = new dbnp.studycapturing.Study(
			title: 'Test study',
			events: [
				new Event(
					template: eventTemplate1,
					startTime: 3600,
					endTime: 7200
				),
				new Event(
					template: eventTemplate2,
					startTime: 3600,
					endTime: 7200
				)
			],
			samples: [
				new Sample(
					template: sampleTemplate1
				),
				new Sample(
					template: sampleTemplate1
				),
				new Sample(

				)
			],
			subjects: [
				new Subject(
					name: 'Wihout template 1'
				),
				new Subject(
					name: 'Wihout template 2'
				)
			]

		)

	}

	protected void tearDown() {
		super.tearDown()
	}

	void testGiveTemplates() {
		def eventTemplates = TemplateEntity.giveTemplates(testStudy.events);
		assert eventTemplates.size() == 2
		assert eventTemplates.contains(eventTemplate1);
		assert eventTemplates.contains(eventTemplate2);

		def sampleTemplates = TemplateEntity.giveTemplates(testStudy.samples);
		assert sampleTemplates.size() == 1
		assert sampleTemplates.contains(sampleTemplate1)

		def subjectTemplates = TemplateEntity.giveTemplates(testStudy.subjects);
		assert subjectTemplates.size() == 0
	}

	/** *
	 *  This test assures that adding of a TemplateField that has another entity than the Template fails.
	 */
	void testTemplateFieldEntity() {
		eventTemplate1.addToFields(
			new TemplateField(name: 'Should fail',type: TemplateFieldType.STRING, entity: dbnp.studycapturing.Sample)
		)
		assert !eventTemplate1.validate()
		assert !eventTemplate1.save()
	}


}
