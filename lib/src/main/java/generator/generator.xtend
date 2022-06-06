package generator

import forsyde.io.java.core.ForSyDeSystemGraph
import java.util.HashSet
import java.util.Set
import java.util.stream.Collectors
import forsyde.io.java.core.Vertex
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import java.util.TreeMap
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor
import java.util.ArrayList

class Generator {

	public static var String root = null

	public static var ForSyDeSystemGraph model
	public static var Set<Vertex> sdfchannelSet
	public static var Set<Vertex> sdfcombSet
	
	public static var Set<Schedule> multiProcessorSchedules
	public static var TreeMap<Integer, Vertex> uniprocessorSchedule


	public static int TESTING=1
	public static int PC=1
	public static int NUCLEO=0
	public static int fifoType=2
	public static int platform=0
	Set<ModuleInterface> modules = new HashSet

	new(ForSyDeSystemGraph model, String root) {
		Generator.root = root
		Generator.model = model
		
		Generator.sdfchannelSet = Generator.model.vertexSet().stream().filter([v|SDFChannel.conforms(v)]).collect(
			Collectors.toSet())
		Generator.sdfcombSet = Generator.model.vertexSet().stream().filter([v|SDFActor.conforms(v)]).collect(
			Collectors.toSet())	
					
		createMultiprocessorSchedule()
		createUniprocessorSchedule()
		var a = 1
	}

	def create() {
		
		modules.stream().forEach([m|m.create()])

	}

	def add(ModuleInterface m) {
		modules.add(m)
	}

	def createMultiprocessorSchedule() {
		var schedules = model.vertexSet().stream().filter([v|v.hasTrait("platform::GenericProcessingModule")]).map([ v |
			new Schedule(v)
		]).collect(Collectors.toSet())
		Generator.multiProcessorSchedules = schedules

	}

	def void createUniprocessorSchedule(){
		Generator.uniprocessorSchedule = new TreeMap
		//var Set<Integer> tmp = new HashSet;
		
		for(Vertex actor: Generator.sdfcombSet){
			
			var tmp=getFiringSlot(actor)
			for(var int i =0; i < tmp.size; i =i+1){
				Generator.uniprocessorSchedule.put(tmp.get(i),actor)
			}
			//Generator.uniprocessorSchedule.put(getFiringSlot(actor),actor)
		}

	}
	private def ArrayList<Integer>  getFiringSlot(Vertex actor){
		var firingSlots=actor.getProperties().get("firingSlots")
		if(firingSlots!==null){
			
			var slot = firingSlots.unwrap() as ArrayList<Integer>
			//return slot.get(0)
			return slot;
		}
		return null;
	}

}
