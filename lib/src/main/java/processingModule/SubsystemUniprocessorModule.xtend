package processingModule

import java.util.Set

import template.templateInterface.SubsystemTemplate

import utils.Save
import java.util.HashSet
import generator.Generator

class SubsystemUniprocessorModule implements ModuleInterface {
	Set<SubsystemTemplate> templates

	new() {
		templates = new HashSet
	}

	override create() {
		process()
	}

	def process() {
		templates.stream().forEach( [ t |

			Save.save(t.create(null), Generator.root + t.savePath());

		])
	}

	def add(SubsystemTemplate t) {
		templates.add(t)
	}

}
