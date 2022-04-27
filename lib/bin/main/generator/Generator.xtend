package generator

import forsyde.io.java.core.ForSyDeSystemGraph
import java.util.HashSet
import java.util.Set
import java.util.stream.Collectors


class Generator {

	public static var String root = null
	public static var Set<Schedule> schedules
	public static  var ForSyDeSystemGraph model
	Set<ModuleInterface> modules = new HashSet
	

	new(ForSyDeSystemGraph model, String root) {
		Generator.root = root
		Generator.model=model
		
	}

	def create() {
		schedule()
		modules.stream().forEach([m|m.create()])

	}

	def add(ModuleInterface m) {
		modules.add(m)
	}

	def schedule() {
		schedules = model.vertexSet().stream().filter([v|v.hasTrait("platform::GenericProcessingModule")]).
			map([ v |
				new Schedule(v)
			]).collect(Collectors.toSet())
		Generator.schedules = schedules

	}
	
	def void init(){
		
	}

}
