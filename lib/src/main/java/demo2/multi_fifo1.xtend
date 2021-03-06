package demo2

import forsyde.io.java.drivers.ForSyDeModelHandler
import generator.Generator
import processingModule.InitProcessingModule
import processingModule.SDFChannelProcessingModule
import processingModule.SubsystemMultiprocessorModule
import template.baremetal_multi.SDFActorInc
import template.baremetal_multi.SDFActorSrc
import template.baremetal_multi.SDFChannelInc
import template.datatype.DataTypeInc
import template.datatype.DataTypeSrc
import template.fifo.fifo1.FIFOInc1
import template.fifo.fifo1.FIFOSrc1
import template.fifo.fifo2.FIFOInc2
import template.fifo.fifo2.FIFOSrc2

import static generator.Generator.*
import processingModule.SDFActorProcessingModule
import template.baremetal_multi.SDFChannelSrc
import template.baremetal_multi.SubsystemIncMulti
import template.baremetal_multi.SubsystemSrcMulti

class multi_fifo1 {
	def static void main(String[] args) {

		
		
		/* testing example2.fiodl*/
		val path = "example2.fiodl"
		val root = "generateCode/example2/multi_fifo1"
		var loader = (new ForSyDeModelHandler)
		var model = loader.loadModel(path)		

		
		
		var Generator gen = new Generator(model, root)
		Generator.fifoType=1
		Generator.platform=2 //2 is multi
		
		
		var sdfchannelModule = new SDFChannelProcessingModule
		sdfchannelModule.add(new SDFChannelSrc)
		sdfchannelModule.add(new SDFChannelInc)

		gen.add(sdfchannelModule)

		var actorModule = new SDFActorProcessingModule
		actorModule.add(new SDFActorSrc)
		actorModule.add(new SDFActorInc)
		gen.add(actorModule)

		var subsystem = new SubsystemMultiprocessorModule
		subsystem.add(new SubsystemSrcMulti)
		subsystem.add(new SubsystemIncMulti)

		
		gen.add(subsystem)

		var initModule = new InitProcessingModule
		initModule.add(new DataTypeInc)
		initModule.add(new DataTypeSrc)
		
		

		
		if(Generator.fifoType==1){
			initModule.add(new FIFOInc1)
			initModule.add(new FIFOSrc1)
		}
		
		
		if(Generator.fifoType==2){
			initModule.add(new FIFOInc2)
			initModule.add(new FIFOSrc2)
		}
	
		
		
		
		
		gen.add(initModule)

		gen.create()

		println("end!")	
	
	
	}			
}