package demo

import utils.Load
import org.jgrapht.alg.shortestpath.BFSShortestPath
import forsyde.io.java.core.Vertex
import forsyde.io.java.core.EdgeInfo
import org.jgrapht.GraphPath
import org.jgrapht.alg.shortestpath.AllDirectedPaths

class test2 {
	def static void main(String[] args) {
		val path1="forsyde-io\\complete-mapped-sobel-model.forsyde.xmi";
		val path2="forsyde-io\\sobel-application.fiodl"
		val root="generateCode\\c\\single"
		var model1 = Load.load(path1);
		var model2 = Load.load(path2);
		model2.mergeInPlace(model1)	
		var dj= new AllDirectedPaths<Vertex,EdgeInfo>(model2)
		var Vertex a=model2.vertexSet().stream().filter([v|v.getIdentifier()=="GrayScale"]).findAny().orElse(null)
		var Vertex b=model2.vertexSet().stream().filter([v|v.getIdentifier()=="GrayScaleImpl"]).findAny().orElse(null)
		var Vertex d=model2.vertexSet().stream().filter([v|v.getIdentifier()=="system_img_source"]).findAny().orElse(null)
		
		
//		var c = dj.getAllPaths(d,b,false,2)
//		
//		c.stream().forEach([v| 
//			v.getEdgeList().stream().forEach([e|println(e)])
//			println("=================================")]
//		)    
		

		var Vertex f=model2.vertexSet().stream().filter([v|v.getIdentifier()=="Abs"]).findAny().orElse(null)
		
		
		
	}	
}