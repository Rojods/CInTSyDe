package demo
import forsyde.io.java.drivers.ForSyDeFiodlHandler


import generator.Generator
import generator.InitProcessingModule
import generator.SDFChannelProcessingModule

import template.baremetal.CircularFIFOTemplateInc
import template.baremetal.CircularFIFOTemplateSrc
import template.baremetal.DataTypeTemplateInc
import template.baremetal.SDFChannelTemplateSrc
import template.baremetal.SDFCombTemplateInc
import template.baremetal.SDFCombTemplateSrc
import template.baremetal.SpinLockTemplateInc
import template.baremetal.SpinLockTemplateSrc
import template.baremetal.uniprocessor.SubsystemTemplateInc
import template.baremetal.uniprocessor.SubsystemTemplateInc2
import template.baremetal.uniprocessor.SubsystemTemplateSrc
import utils.Load
import generator.SDFCombProcessingModule
import generator.SubsystemUniprocessorModule
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexProperty
import java.util.Map
import java.util.HashMap
import java.util.HashSet
import java.util.Set
import forsyde.io.java.drivers.ForSyDeModelHandler
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.core.EdgeTrait

class test3 {
	def static void main(String[] args){
		val path="forsyde-io\\modified2\\complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io\\modified2\\sobel-application.fiodl"


		//var model = (new ForSyDeModelHandler()).loadModel(path)
     var ForSyDeSystemGraph model= new ForSyDeSystemGraph
	val ports = #{"a","combFunctions"}
	val properties=#{"firingSlots"->VertexProperty.create(0)
		,"production"->VertexProperty.create(#{"a"->1})
	}
    var a = new Vertex("A" , ports,properties)
	a.addTraits(VertexTrait.MOC_SDF_SDFCOMB,VertexTrait.DECISION_SDF_PASSEDSDFCOMB)
	model.addVertex(a)
	
	val ports2 = #{"b","combFunctions"}
	val properties2=#{"firingSlots"->VertexProperty.create(1)
		,"consumption"->VertexProperty.create(#{"b"->1})
	}
    var a2 = new Vertex("B" , ports,properties)
	a2.addTraits(VertexTrait.MOC_SDF_SDFCOMB,VertexTrait.DECISION_SDF_PASSEDSDFCOMB)
	model.addVertex(a2)	
	
	
	
	
	
	
	val ports3 = #{"producer","consumer"}
	val properties3=#{"maximumTokens"->VertexProperty.create(1)
		
	}	
	
    var a3 = new Vertex("sig" , ports3,properties3)
	a3.addTraits(VertexTrait.MOC_SDF_SDFCHANNEL,VertexTrait.DECISION_SDF_BOUNDEDSDFCHANNEL)
	a3.addTraits(VertexTrait.IMPL_TOKENIZABLEDATABLOCK)
	
	model.addVertex(a3)	
		
	
	
	
	
	
	(new ForSyDeModelHandler).writeModel(model,"a.fiodl")
	
		
		
		println("end!")		
	}
}