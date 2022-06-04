package demo
import forsyde.io.java.core.ForSyDeSystemGraph


import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexProperty
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.drivers.ForSyDeModelHandler
import forsyde.io.java.core.EdgeInfo
import forsyde.io.java.core.EdgeTrait
import java.util.Set
import java.util.Map
import java.util.List
import java.util.ArrayList

class test4 {
	def static void main(String[] args) {
		var ForSyDeSystemGraph model = new ForSyDeSystemGraph
		
		val t =EdgeTrait.MOC_SDF_SDFDATAEDGE
		addVertex(model)
		addchannel(model)
		
		
//		connectChannel(model,"s_in","p1","consumer","s_in",t)
//		connectChannel(model,"s1","p2","consumer","s1",t)
//		connectChannel(model,"s2","p4","consumer","s4",t)
//		connectChannel(model,"s3","p3","consumer","s3",t)
//		connectChannel(model,"s4","p5","consumer","s4",t)
//		connectChannel(model,"s5","p3","consumer","s5",t)
//		connectChannel(model,"s6","p1","consumer","s6",t)
//		
//		
//		connectChannel(model,"p1","s1","s1","producer",t)
//		connectChannel(model,"p2","s2","s2","producer",t)
//		connectChannel(model,"p2","s3","s3","producer",t)
//		connectChannel(model,"p4","s4","s4","producer",t)
//		connectChannel(model,"p5","s5","s5","producer",t)
//		connectChannel(model,"p3","s6","s6","producer",t)
//		connectChannel(model,"p4","s_out","s_out","producer",t)
		
		
		(new ForSyDeModelHandler).writeModel(model, "example2.fiodl")
		(new ForSyDeModelHandler).writeModel(model, "example2.forsyde.xmi")

	}
	static def connectChannel(ForSyDeSystemGraph model
		,String srcname
		,String dstname
		,String srcport
		,String dstport
		,EdgeTrait t
		//
	){
		model.connect(model.queryVertex(srcname).get(), model.queryVertex(dstname).get(), srcport, dstport, t)
	}
	
	
	static def addchannel(ForSyDeSystemGraph model){
		var list= #["s_in","s_out","s1","s2","s3","s4","s5","s6"]
//		var list= #["s1","s2","s3","s4","s5","s6"]
		for(String name :list){
			var ports = #{"producer", "consumer"}
			
			var int maxtoken=0;
			if(name == "s1"){
				maxtoken=1
			}
			if(name == "s2"){
				maxtoken=1
			}
			if(name == "s3"){
				maxtoken=2
			}
			if(name == "s4"){
				maxtoken=1
			}						
			if(name == "s5"){
				maxtoken=2
			}			
			if(name == "s6"){
				maxtoken=2
			}			
			if(name == "s_in"){
				maxtoken=10
			}
			if(name == "s_out"){
				maxtoken=10
			}
			
						
			var properties = #{
				"maximumTokens" -> VertexProperty.create(maxtoken)
			}
	
			var a = new Vertex(name, ports, properties)
			
			a.addTraits(VertexTrait.MOC_SDF_SDFCHANNEL, VertexTrait.DECISION_SDF_BOUNDEDSDFCHANNEL)
			a.addTraits(VertexTrait.IMPL_TOKENIZABLEDATABLOCK)
	
			model.addVertex(a)			
			
		}
		
		
	}
	static def addVertex(ForSyDeSystemGraph model){
		///////////////////////////////////p1
		var ports = #{"s_in","s6","s1", "combFunctions"}
		var properties = #{
			"firingSlots" -> VertexProperty.create(#[0,4]),
			"production" -> VertexProperty.create(#{"s1" -> 1}),
			"consumption" -> VertexProperty.create(#{"s_in" -> 2, "s6"->1})
		}
		
		var a = new Vertex("p1", ports, properties)
		
		a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR)
		model.addVertex(a)			
	/////////////////////////////////////////////////
	
	
		 ports = #{"s1","s2","s3", "combFunctions"}
		 properties = #{
			"firingSlots" -> VertexProperty.create(#[1,5]),
			"production" -> VertexProperty.create(#{"s2" -> 1, "s3" -> 1}),
			"consumption" -> VertexProperty.create(#{"s1" -> 1})
		}
		
		 a = new Vertex("p2", ports, properties)
		
		a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR)	
		model.addVertex(a)	
	//////////////////////////////////p3/////////////////
		 ports = #{"s5","s6","s3", "combFunctions"}
		 properties = #{
			"firingSlots" -> VertexProperty.create(#[8]),
			"production" -> VertexProperty.create(#{"s6" -> 2}),
			"consumption" -> VertexProperty.create(#{"s3" -> 2, "s5"->2})
		}
		
		 a = new Vertex("p3", ports, properties)
		
		a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR)	
		model.addVertex(a)	
		
		
	//////////////////////////////////p4/////////////////
		 ports = #{"s2","s4","s_out", "combFunctions"}
		 properties = #{
			"firingSlots" -> VertexProperty.create(#[2,6]),
			"consumption" -> VertexProperty.create(#{"s2" -> 1}),
			"production" -> VertexProperty.create(#{"s4" -> 1, "s_out"->3})
		}
		
		 a = new Vertex("p4", ports, properties)
		
		a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR)	
		model.addVertex(a)			
		
		
	//////////////////////////////////p5/////////////////
		 ports = #{"s5","s4", "combFunctions"}
		 properties = #{
			"firingSlots" -> VertexProperty.create(#[3,7]),
			"production" -> VertexProperty.create(#{"s5" -> 1}),
			"consumption" -> VertexProperty.create(#{"s4" -> 1})
		}
		
		 a = new Vertex("p5", ports, properties)
		
		a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR)	
		model.addVertex(a)	
		//////////////////////////data type
		var type = new Vertex("uint32")
		type.addTraits(VertexTrait.TYPING_DATATYPES_INTEGER)
		 properties = #{
			"numberOfBits" -> VertexProperty.create(32)
			}
		
		type.properties=properties
		model.addVertex(type)
		///////////////////////////////////////////////
		
		ports = #{"portTypes", "s_in","s6","s1"}
		var b = #{
			"inlinedCode" -> VertexProperty.create("s1=s6;"),
			"inputPorts" -> VertexProperty.create(#["s_in","s6"]),
			"outputPorts" -> VertexProperty.create(#["s1"])
		}

		var impl = new Vertex("p1impl", ports, b)

		impl.addTraits(VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexTrait.TYPING_TYPEDOPERATION,
			VertexTrait.IMPL_INSTRUMENTEDEXECUTABLE)
		model.addVertex(impl)		
		
		
		
		model.connect(model.queryVertex("p1").get(),impl,"combFunctions")
		
		var List<EdgeTrait> l = new ArrayList
		
		model.connect(model.queryVertex("p1").get(),impl,"s_in","s_in",l)
		model.connect(model.queryVertex("p1").get(),impl,"s6","s6",l)
		model.connect(impl,model.queryVertex("p1").get(),"s1","s1",l)
		
		
		
		model.connect(model.queryVertex("p1impl").get(),impl,"s_in",EdgeTrait.TYPING_DATATYPES_DATADEFINITION)
		model.connect(model.queryVertex("p1impl").get(),impl,"s1",EdgeTrait.TYPING_DATATYPES_DATADEFINITION)
		model.connect(model.queryVertex("p1impl").get(),impl,"s6",EdgeTrait.TYPING_DATATYPES_DATADEFINITION)			
		
	
	}	
		
	
	
	
}