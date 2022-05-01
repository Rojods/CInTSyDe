package demo

import utils.Load
import org.jgrapht.alg.shortestpath.BFSShortestPath
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.EdgeInfo
import org.jgrapht.GraphPath
import org.jgrapht.alg.shortestpath.AllDirectedPaths
import utils.Query
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb
import java.util.stream.Collectors

class test2 {
	def static void main(String[] args) {
		val path1="forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io\\sobel-application.fiodl"
		val root="generateCode\\c\\single"
		var model1 = Load.load(path1);
		var model2 = Load.load(path2);
		model2.mergeInPlace(model1)	
		var dj= new AllDirectedPaths<Vertex,EdgeInfo>(model2)
		test(model2)
		
		
//		var Vertex abs=model2.vertexSet().stream().filter([v|v.getIdentifier()=="GrayScale"]).findAny().orElse(null)
//		var Vertex absimpl=model2.vertexSet().stream().filter([v|v.getIdentifier()=="GrayScaleImpl"]).findAny().orElse(null)
//		var yy = Query.findImplInputPortSet(absimpl)
//		println(yy)
//		for(String p:yy){
//			var datatype = Query.findImplPortDataType(model2,absimpl,p)
//			var b = Query.findActorPortConnectedToImplInputPort(model2,abs,absimpl,p)
//			println("vertex port "+b+" ---> impl port "+p+"--->type "+datatype);
//		}
//		println("=================================")
//		yy = Query.findImplOutputPortSet(absimpl)
//		for(String p:yy){
//			var datatype = Query.findImplPortDataType(model2,absimpl,p)
//			var b = Query.findActorPortConnectedToImplOutputPort(model2,abs,absimpl,p)
//			println("vertex port "+b+" <---- impl port "+p+"--->type "+datatype);
//		}
				
	}
	def static test(ForSyDeSystemGraph model){
		var actors = model.vertexSet().stream().filter([v|SDFComb.conforms(v)]).collect(Collectors.toSet())
		
		for(Vertex actor:actors){
			println("in sdfcomb "+actor.getIdentifier()+"========================================")
			var impls=Query.findCombFuntionVertex(model,actor)
			for(String impl:impls){
				var actorimpl=model.vertexSet().stream().filter([v|v.getIdentifier().equals(impl)]).findAny().orElse(null)
				var inSet = Query.findImplInputPortSet(actorimpl)
				for(String inport:inSet){
					var datatype = Query.findImplPortDataType(model,actorimpl,inport)
					var actorPort = Query.findActorPortConnectedToImplInputPort(model,actor,actorimpl,inport)
					var sdf= Query.findSDFChannel(model,actor,actorPort)
					println("sdfchannel "+"--->"+actor.getIdentifier()+" port "+actorPort+" ---> impl port "+inport+"--->type "+datatype);
				}				
				var outSet = Query.findImplOutputPortSet(actorimpl)
				for(String p:outSet){
					var datatype = Query.findImplPortDataType(model,actorimpl,p)
					var actorPort = Query.findActorPortConnectedToImplOutputPort(model,actor,actorimpl,p)
					var sdf= Query.findSDFChannel(model,actor,actorPort)
					println("sdfchannel "+"<---"+actor.getIdentifier()+" port "+actorPort+" <---- impl port "+p+"--->type "+datatype);
				}			
			}
		}
	}	
}