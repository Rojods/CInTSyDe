package sdf;
import forsyde.io.java.core.*;

import forsyde.io.java.drivers.ForSyDeModelHandler;
//import forsyde.io.java.graphviz.ForSyDeGraphVizDriver;
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannel;
import forsyde.io.java.typed.viewers.decision.sdf.PASSedSDFActor;
import forsyde.io.java.typed.viewers.impl.ANSICBlackBoxExecutable;
import forsyde.io.java.typed.viewers.impl.TokenizableDataBlock;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import forsyde.io.java.typed.viewers.typing.TypedOperation;
import forsyde.io.java.typed.viewers.typing.datatypes.Integer;
import forsyde.io.java.typed.viewers.visualization.Visualizable;
import forsyde.io.java.validation.SDFValidator;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.AsUndirectedGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
public class sdfcreate2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static void create() {
		final ForSyDeSystemGraph model = new ForSyDeSystemGraph();
        final SDFValidator validator = new SDFValidator();	
        
        // since we only want the SDF actor, we can do it in 1 line
        // as the vertex is still accessible by the viewer
        final SDFActor p1 = SDFActor.enforce(model.newVertex("p1"));
        p1.getPorts().addAll(Set.of("s_in_port", "s1_port","s6_port"));
        p1.setProduction(Map.of("s1_port", 1));
        p1.setConsumption(Map.of("s_in_port", 2, "s6_port",1));
        
        final SDFActor p2 = SDFActor.enforce(model.newVertex("p2"));
        p1.getPorts().addAll(Set.of("s1_port", "s2_port","s3_port"));
        p1.setProduction(Map.of("s2_port", 1,"s3_port",1));
        p1.setConsumption(Map.of("s1_port", 1));  
        
        final SDFActor p3 = SDFActor.enforce(model.newVertex("p3"));
        p1.getPorts().addAll(Set.of("s6_port", "s5_port","s3_port"));
        p1.setProduction(Map.of("s6_port", 2));
        p1.setConsumption(Map.of("s3_port", 2, "s5_port",2));  
        
//        final SDFActor p4 = SDFActor.enforce(model.newVertex("p4"));
//        p1.getPorts().addAll(Set.of("s2_port", "s4_port","s_out_port"));
//        p1.setProduction(Map.of("s4_port", 1,"s_out_port",3));
//        p1.setConsumption(Map.of("s2_port", 1));  
//        
//        final SDFActor p5 = SDFActor.enforce(model.newVertex("p5"));
//        p1.getPorts().addAll(Set.of("s5_port", "s4_port"));
//        p1.setProduction(Map.of("s5_port", 1));
//        p1.setConsumption(Map.of("s4_port", 1));  
        
 
        
        
        final SDFChannel sIn = SDFChannel.enforce(model.newVertex("s_in"));
        sIn.setConsumerPort(model, p1, "s_in_port");
        
        

        mysdfedgeconnect(model,"s1",p1,"s1_port",p2,"s1_port");
//       mysdfedgeconnect(model,"s2",p2,"s2_port",p4,"s2_port");
        mysdfedgeconnect(model,"s3",p2,"s3_port",p3,"s3_port");
//        mysdfedgeconnect(model,"s4",p4,"s4_port",p5,"s4_port");
//        mysdfedgeconnect(model,"s5",p5,"s5_port",p3,"s5_port");
        mysdfedgeconnect(model,"s6",p3,"s6_port",p1,"s6_port");
 
        //final SDFChannel sOut = SDFChannel.enforce(model.newVertex("s_out"));
       // model.connect(p4, sOut, "s_out_port", "producer", EdgeTrait.MOC_SDF_SDFDATAEDGE);    
        try {
			(new ForSyDeModelHandler()).writeModel(model,"simple2.fiodl");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}       
	}
	
	public static void mysdfedgeconnect(ForSyDeSystemGraph model,String channelname,SDFActor src,String srcport,SDFActor snk,String snkport) {
		final SDFChannel s = SDFChannel.enforce(model.newVertex("channelname"));
		model.connect(src, s, srcport, "producer", EdgeTrait.MOC_SDF_SDFDATAEDGE);
		s.setConsumerPort(model, snk, snkport);
	}
}
