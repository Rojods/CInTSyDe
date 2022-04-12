package util;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import forsyde.io.java.core.EdgeInfo;
import forsyde.io.java.core.EdgeTrait;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexAcessor;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.core.VertexTrait;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFComb;
import java.lang.Math;
public class Query {

	
	public static Integer getFiringSlot(SDFComb comb) {
		Map<String, VertexProperty> a = comb.getProperties();
		var  b = a.get("firingSlots").unwrap();
		var c =  (ArrayList<Integer>)b;
		
		return c.get(0);

	}


	public static int getBufferSize(SDFChannel ch){
		 Map<String, VertexProperty> a = ch.getProperties();
		Integer b = (Integer)a.get("maximumTokens").unwrap();
		
		return  b;
	}	
	
	public static long getTokenSize(SDFChannel ch) {
		Map<String, VertexProperty> a = ch.getProperties();
		long b = (Long)a.get("tokenSizeInBits").unwrap();
		
		return (long)Math.ceil( ((float)b)/8  ); 
		
	}
	
	public static String getSDFChannelName(Vertex vertex, String port,ForSyDeSystemGraph model){
		var optional = VertexAcessor.getNamedPort(model
				,vertex
				,port
				//,VertexTrait.fromName("MOC_SDF_SDFCHANNEL")
				,VertexTrait.MOC_SDF_SDFCHANNEL
				, VertexAcessor.VertexPortDirection.BIDIRECTIONAL
			);


		if(optional.isPresent()){
			return Name.name(optional.get());
		}else{
			return "UNDEFINED";
		}
	}
	
	public static  int getPortRate(SDFComb sdf, String port) {
		if(sdf.getProduction()!=null &&  sdf.getProduction().containsKey(port)) {
			return sdf.getProduction().get(port);
			
		}else if(sdf.getConsumption()!=null &&  sdf.getConsumption().containsKey(port)) {
			return sdf.getConsumption().get(port);
		}else {
			return 1;
		}	
		
	}
	public static Set<String> allDataEdges(Vertex vertex,ForSyDeSystemGraph model){
		Set<String> allDataEdges = model.outgoingEdgesOf(vertex)
										.stream()
										.filter(edgeinfo->edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE))
										.map(e->e.getTarget())
										.collect(Collectors.toSet());	
		
		allDataEdges.addAll(
				model.incomingEdgesOf(vertex)
				.stream()
				.filter(edgeinfo->edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE))
				.map(e->e.getSource())
				.collect(Collectors.toSet())				
				);
	return allDataEdges;
	}
	
	/**
	 * @param vertex	vertex must be a has trait SDFComb
	 */
	public static  long getWCET(Vertex vertex,ForSyDeSystemGraph model) {
//		Set<EdgeInfo> edgeinfos=  model.incomingEdgesOf(vertex);
//		edgeinfos.stream().filter(e->e.hasTrait(EdgeTrait.))
		return 4000;
	}
}
