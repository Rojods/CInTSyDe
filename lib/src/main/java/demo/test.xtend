package demo

import forsyde.io.java.core.EdgeTrait
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.VertexProperty
import forsyde.io.java.core.VertexTrait
import forsyde.io.java.drivers.ForSyDeModelHandler
import java.util.ArrayList
import java.util.List

/**
 * load fiodl file
 */
class test {
	def static void main(String[] args) {
		var ForSyDeSystemGraph model = new ForSyDeSystemGraph
		
		val t =EdgeTrait.MOC_SDF_SDFDATAEDGE
		addVertex(model)
		addchannel(model)
		connectChannel(model,"p1","s1","a","producer",t)
		connectChannel(model,"s1","p2","consumer","b",t)


//		connectChannel(model,"s_in","p1","consumer","a",t)
		
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
		
		
		(new ForSyDeModelHandler).writeModel(model, "test2.fiodl")
		println("end!")
		//(new ForSyDeModelHandler).writeModel(model, "example2.forsyde.xmi")

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
		var list= #["s1"
			,"s_in"
		]
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
				,"maxSizeInBits" ->VertexProperty.create(32)
				,"tokenSizeInBits"->VertexProperty.create(32)
			}
	
			var a = new Vertex(name, ports, properties)
			
			a.addTraits(VertexTrait.MOC_SDF_SDFCHANNEL, VertexTrait.DECISION_SDF_BOUNDEDSDFCHANNEL)
			a.addTraits(VertexTrait.IMPL_TOKENIZABLEDATABLOCK)
	
			model.addVertex(a)			
			
		}
		
		
	}
	static def addVertex(ForSyDeSystemGraph model){
		///////////////////////////////////p1
		var ports = #{"a", "combFunctions"}
		var properties = #{
			"firingSlots" -> VertexProperty.create(#[0]),
			"production" -> VertexProperty.create(#{"a" -> 1})
			//"consumption" -> VertexProperty.create(#{"s_in" -> 2, "s6"->1})
		}
		
		var a = new Vertex("p1", ports, properties)
		
		a.addTraits(VertexTrait.MOC_SDF_SDFACTOR, VertexTrait.DECISION_SDF_PASSEDSDFACTOR)
		model.addVertex(a)			
	////////////////////////////////////////////p2/////
	
	
		 ports = #{"b", "combFunctions"}
		 properties = #{
			"firingSlots" -> VertexProperty.create(#[1]),
			//"production" -> VertexProperty.create(#{"s2" -> 1, "s3" -> 1}),
			"consumption" -> VertexProperty.create(#{"b" -> 1})
		}
		
		 a = new Vertex("p2", ports, properties)
		
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
		///////////////////////////////////////////////p1impl
		var List<EdgeTrait> l = new ArrayList
		
		
		ports = #{"portTypes", "a"}
		var b = #{
			"inlinedCode" -> VertexProperty.create("a=5;"),
			"inputPorts" -> VertexProperty.create(#[]),
			"outputPorts" -> VertexProperty.create(#["a"])
		}

		var impl = new Vertex("p1impl", ports, b)

		impl.addTraits(VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexTrait.TYPING_TYPEDOPERATION,
			VertexTrait.IMPL_INSTRUMENTEDEXECUTABLE)
		model.addVertex(impl)		
		
		model.connect(model.queryVertex("p1").get(),impl,"combFunctions")
		model.connect(impl,model.queryVertex("p1").get(),"a","a",l)
		model.connect(model.queryVertex("p1impl").get(),  model.queryVertex("uint32").get()   ,"a",EdgeTrait.TYPING_DATATYPES_DATADEFINITION)
	
	////////////////////////////////////////p2impl	


		ports = #{"portTypes", "b"}
		b = #{
			"inlinedCode" -> VertexProperty.create("int c = 2*b;"),
			"inputPorts" -> VertexProperty.create(#["b"]),
			"outputPorts" -> VertexProperty.create(#[])
		}

		impl = new Vertex("p2impl", ports, b)

		impl.addTraits(VertexTrait.IMPL_ANSICBLACKBOXEXECUTABLE, VertexTrait.TYPING_TYPEDOPERATION,
			VertexTrait.IMPL_INSTRUMENTEDEXECUTABLE)
		model.addVertex(impl)		
		model.connect(model.queryVertex("p2impl").get(),  model.queryVertex("uint32").get()   ,"b",EdgeTrait.TYPING_DATATYPES_DATADEFINITION)
		
		
		
		model.connect(model.queryVertex("p2").get(),impl,"combFunctions")
		
		
		model.connect(model.queryVertex("p2").get(),impl,"b","b",l)
		//model.connect(impl,model.queryVertex("p1").get(),"a","a",l)
		
	}	
}