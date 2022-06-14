package processingModule

import generator.Generator

import java.util.HashSet
import java.util.Set
import template.templateInterface.InitTemplate
import utils.Save

class InitProcessingModule implements ModuleInterface {
	Set<InitTemplate> templateSet

	new() {
		templateSet = new HashSet

	}

	override create() {
		templateSet.stream().forEach([t|process(t)])
	}

	def void process(InitTemplate t) {

		Save.save(t.create(), Generator.root + t.savePath())

	}

	def add(InitTemplate t) {
		templateSet.add(t)
	}

}
