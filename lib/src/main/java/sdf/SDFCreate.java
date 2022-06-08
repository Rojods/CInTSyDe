package sdf;
import forsyde.io.java.core.*;

import forsyde.io.java.drivers.ForSyDeModelHandler;
//import forsyde.io.java.graphviz.ForSyDeGraphVizDriver;
import forsyde.io.java.typed.viewers.decision.sdf.BoundedSDFChannel;
import forsyde.io.java.typed.viewers.decision.sdf.PASSedSDFActor;
import forsyde.io.java.typed.viewers.impl.ANSICBlackBoxExecutable;
import forsyde.io.java.typed.viewers.impl.TokenizableDataBlock;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule;
import forsyde.io.java.typed.viewers.platform.runtime.RoundRobinScheduler;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import forsyde.io.java.typed.viewers.typing.TypedOperation;
import forsyde.io.java.typed.viewers.typing.datatypes.Array;
import forsyde.io.java.typed.viewers.typing.datatypes.Integer;
import forsyde.io.java.typed.viewers.values.IntegerValue;
import forsyde.io.java.typed.viewers.visualization.Visualizable;
import forsyde.io.java.validation.SDFValidator;
import org.jgrapht.Graph;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.AsUndirectedGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
public class SDFCreate {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			create();
	}
	public static void create() {
        final ForSyDeSystemGraph model = new ForSyDeSystemGraph();
        final SDFValidator validator = new SDFValidator();

        
        
        final SDFActor p3 = SDFActor.enforce(model.newVertex("p3"));
        p3.getPorts().addAll(Set.of("s6_port","s3_port","s5_port"));
        p3.setProduction(Map.of("s6_port", 2));
        p3.setConsumption(Map.of("s3_port", 2,"s5_port",2)); 
        
        
        final SDFActor p4 = SDFActor.enforce(model.newVertex("p4"));
        p4.getPorts().addAll(Set.of("s2_port","s4_port","s_out_port"));
        p4.setProduction(Map.of("s4_port", 1,"s_out_port",3));
        p4.setConsumption(Map.of("s2_port", 1));      
 
        final SDFActor p5 = SDFActor.enforce(model.newVertex("p5"));
        p5.getPorts().addAll(Set.of("s5_port","s4_port"));
        p5.setProduction(Map.of("s5_port", 1));
        p5.setConsumption(Map.of("s4_port", 1));     
        
        
        
        
        
        
        // since we only want the SDF actor, we can do it in 1 line
        // as the vertex is still accessible by the viewer
        final SDFActor p1 = SDFActor.enforce(model.newVertex("p1"));
        p1.getPorts().addAll(Set.of("s_in_port", "s1_port", "s6_port"));
        p1.setProduction(Map.of("s1_port", 1));
        p1.setConsumption(Map.of("s_in_port", 2, "s6_port",1));

        // this could be the way it both as always accessible
        final Vertex p2Vertex = new Vertex("p2");
        final SDFActor p2 = SDFActor.enforce(p2Vertex);
        model.addVertex(p2Vertex); // same as using the viewer as above
        p2.getPorts().add("s1_port");
        p2.getPorts().add("s3_port");
        p2.getPorts().add("s2_port");
        p2.setConsumption(Map.of("s1_port", 1));
        p2.setProduction(Map.of("s3_port", 1,"s2_port",1));
        
        
        //////////////////////////////////////////////////////////////////////////////////
        // or a mix of both previous syntaxes
        final SDFChannel s1 = SDFChannel.enforce(new Vertex("s1"));
        model.addVertex(s1.getViewedVertex());

        final SDFChannel s2 = SDFChannel.enforce(new Vertex("s2"));
        model.addVertex(s2.getViewedVertex());
        
        final SDFChannel s3 = SDFChannel.enforce(new Vertex("s3"));
        model.addVertex(s3.getViewedVertex());
        
        final SDFChannel s4 = SDFChannel.enforce(new Vertex("s4"));
        model.addVertex(s4.getViewedVertex());       
 
        
        final SDFChannel s5 = SDFChannel.enforce(new Vertex("s5"));
        model.addVertex(s5.getViewedVertex());  
        
        final SDFChannel s6 = SDFChannel.enforce(new Vertex("s6"));
        model.addVertex(s6.getViewedVertex());
        
       
        
        final SDFChannel sIn = SDFChannel.enforce(model.newVertex("s_in"));
        
        // you can use the veiwer setters to connect the vertexes, which
        // also takes care of edge traits.
        // this syntax is presetn from ForSyDe 0.5.9 onwards
        sIn.setConsumerPort(model, p1, "s_in_port"); // this is currently the recommended way

        // you can also connect things directly, but
        // then you need to know which syntactially correct ports
        // the vertexes connect to each other. Like 'producer' here, for example.
        // the hint is that the viewer's set and get functions denote the ports
        model.connect(p1, s1, "s1_port", "producer", EdgeTrait.MOC_SDF_SDFDATAEDGE);

        // the order is inverted here to symbolize that the outgoing port
        // is part of s1 and not p2.
        s1.setConsumerPort(model, p2, "s1_port");

        
        model.connect(p2, s3, "s3_port", "producer", EdgeTrait.MOC_SDF_SDFDATAEDGE);
        s3.setConsumerPort(model, p3, "s3_port");
        
        
        model.connect(p3, s6, "s6_port", "producer", EdgeTrait.MOC_SDF_SDFDATAEDGE);
        s6.setConsumerPort(model, p1, "s6_port");       
 
        
        model.connect(p2, s2, "s2_port", "producer", EdgeTrait.MOC_SDF_SDFDATAEDGE);
        s2.setConsumerPort(model, p4, "s2_port");  
        
        model.connect(p4, s4, "s4_port", "producer", EdgeTrait.MOC_SDF_SDFDATAEDGE);
        s4.setConsumerPort(model, p5, "s4_port");         
        
        model.connect(p5, s5, "s5_port", "producer", EdgeTrait.MOC_SDF_SDFDATAEDGE);
        s5.setConsumerPort(model, p3, "s5_port");  
        
//        final SDFChannel sOut = SDFChannel.enforce(model.newVertex("s_out"));
//        model.connect(p4, sOut, "s_out_port", "producer", EdgeTrait.MOC_SDF_SDFDATAEDGE);
 ////////////////////////////////////to be continued//////////////////////////////////////////////////////////////////       
        
        // this model admits a sime schedule
        final PASSedSDFActor p1pass = PASSedSDFActor.enforce(p1);
        p1pass.setFiringSlots(List.of(0,4));

        final PASSedSDFActor p2pass = PASSedSDFActor.enforce(p2);
        p2pass.setFiringSlots(List.of(1,5));
        
        final PASSedSDFActor p3pass = PASSedSDFActor.enforce(p3);
        p3pass.setFiringSlots(List.of(8));
        
        final PASSedSDFActor p4pass = PASSedSDFActor.enforce(p4);
        p4pass.setFiringSlots(List.of(2,6));
        
        final PASSedSDFActor p5pass = PASSedSDFActor.enforce(p5);
        p5pass.setFiringSlots(List.of(3,7));

        final BoundedSDFChannel sInBounded = BoundedSDFChannel.enforce(sIn);
        sInBounded.setMaximumTokens(10);

        final BoundedSDFChannel s1Bounded = BoundedSDFChannel.enforce(s1);
        s1Bounded.setMaximumTokens(1);
        
        final BoundedSDFChannel s2Bounded = BoundedSDFChannel.enforce(s2);
        s2Bounded.setMaximumTokens(1);      
        
        final BoundedSDFChannel s3Bounded = BoundedSDFChannel.enforce(s3);
        s3Bounded.setMaximumTokens(2);      
 
        final BoundedSDFChannel s4Bounded = BoundedSDFChannel.enforce(s4);
        s4Bounded.setMaximumTokens(1);  
        
        final BoundedSDFChannel s5Bounded = BoundedSDFChannel.enforce(s5);
        s5Bounded.setMaximumTokens(2);   
        
        final BoundedSDFChannel s6Bounded = BoundedSDFChannel.enforce(s6);
        s6Bounded.setMaximumTokens(2);          
//////////////////////////////////////////////////////////////////////////////////////////////////////
        // now lets put some types and code
        final Integer uint32Type = Integer.enforce(model.newVertex("UInt32"));
        uint32Type.setNumberOfBits(32);
        
        final  Array Array2OfUint32Type =Array.enforce(model.newVertex("Array2OfUInt32Type"));
        Array2OfUint32Type.setMaximumElems(2);
        
        final  Array Array3OfUint32Type =Array.enforce(model.newVertex("Array3OfUInt32Type"));
        Array3OfUint32Type.setMaximumElems(3);
        
        model.connect(Array2OfUint32Type, uint32Type,"innerType",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        model.connect(Array3OfUint32Type, uint32Type,"innerType",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        
  ////////////////////////////////sdf delays////////////////////////////////////////////      
        
        final IntegerValue integerValue1= IntegerValue.enforce(model.newVertex("ZeroValue1"));
        integerValue1.setIntValue(0);
        final IntegerValue integerValue2= IntegerValue.enforce(model.newVertex("ZeroValue2"));
        integerValue2.setIntValue(0);
        ///set sdf delay
        s6.setNumOfInitialTokens(2);
        s6.setInitialTokenValuesPort(model, List.of(integerValue1,integerValue2));
        
///////////////////////////////////////////////////////////////
        // the body for p1
        final ANSICBlackBoxExecutable p1Body = ANSICBlackBoxExecutable.enforce(model.newVertex("p1Body"));
        p1Body.getPorts().add("s_in");
        p1Body.getPorts().add("s6");
        p1Body.getPorts().add("s1");
        
        p1Body.setInlinedCode("s1 = s_in[0]+s_in[1]+s6;");
        // its types
        final TypedOperation p1TypedOp = TypedOperation.enforce(p1Body);
        p1TypedOp.setInputPorts(List.of("s_in","s6"));
        p1TypedOp.setInputPortTypesPort(model, List.of(Array2OfUint32Type,uint32Type));
        
        
        
        p1TypedOp.setOutputPorts(List.of("s1"));
        p1TypedOp.setOutputPortTypesPort(model, List.of(uint32Type));
        // and we connect it to the actual SDF actor
        p1.setCombFunctionsPort(model, Set.of(p1Body));
        // just for consistency, we also connec the ports of the sdf to the body
        model.connect(p1, p1Body,"s_in_port", "s_in", EdgeTrait.MOC_ABSTRACTIONEDGE); //input
        model.connect(p1, p1Body,"s6_port", "s6", EdgeTrait.MOC_ABSTRACTIONEDGE); //input
        model.connect(p1Body, p1,"s1", "s1_port", EdgeTrait.MOC_ABSTRACTIONEDGE); //output
        
      
        model.connect(p1Body, Array2OfUint32Type,"s_in",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        model.connect(p1Body, uint32Type,"s1",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        model.connect(p1Body, uint32Type,"s6",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        
///////////////////////////////////////////////////////////////////////////////////////////////////////////////        
        
        
        
        
        ///////////////////////////////////////////////////////////////////////////////
        // now we do the same for p2
        final ANSICBlackBoxExecutable p2Body = ANSICBlackBoxExecutable.enforce(model.newVertex("p2Body"));
        p2Body.getPorts().add("s1");
        p2Body.getPorts().add("s2");
        p2Body.getPorts().add("s3");
        p2Body.setInlinedCode("s2=s1;s3=s1+1;");
        // its types
        final TypedOperation p2TypedOp = TypedOperation.enforce(p2Body);
        p2TypedOp.setInputPorts(List.of("s1"));
        p2TypedOp.setInputPortTypesPort(model, List.of(uint32Type));
        
        p2TypedOp.setOutputPorts(List.of("s2","s3"));
        p2TypedOp.setOutputPortTypesPort(model, List.of(uint32Type,uint32Type));       
        
        
        // and we connect it to the actual SDF actor
        p2.setCombFunctionsPort(model, Set.of(p2Body));
        // just for consistency, we also connec the ports of the sdf to the body
        model.connect(p2, p2Body,"s1_port", "s1", EdgeTrait.MOC_ABSTRACTIONEDGE); //input
        model.connect(p2Body, p2,"s3", "s3_port", EdgeTrait.MOC_ABSTRACTIONEDGE); //output
        model.connect(p2Body, p2,"s2", "s2_port", EdgeTrait.MOC_ABSTRACTIONEDGE); //output

        
   
        model.connect(p2Body, uint32Type,"s1",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        model.connect(p2Body, uint32Type,"s2",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        model.connect(p2Body, uint32Type,"s3",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION);       
        
        ///////////////////////////////////////////////////////////////////////////////
        // now we do the same for p3
        final ANSICBlackBoxExecutable p3Body = ANSICBlackBoxExecutable.enforce(model.newVertex("p3Body"));
        p3Body.getPorts().add("s3");
        p3Body.getPorts().add("s6");
        p3Body.getPorts().add("s5");
        p3Body.setInlinedCode("s6[0]=s3[0]+s3[1];s6[1]=s5[0]+s5[1];");
        // its types
        final TypedOperation p3TypedOp = TypedOperation.enforce(p3Body);
        p3TypedOp.setInputPorts(List.of("s3","s5"));
        p3TypedOp.setInputPortTypesPort(model, List.of(uint32Type,uint32Type));
        
        p3TypedOp.setOutputPorts(List.of("s6"));
        p3TypedOp.setOutputPortTypesPort(model, List.of(uint32Type));       
        
        
        // and we connect it to the actual SDF actor
        p3.setCombFunctionsPort(model, Set.of(p3Body));
        // just for consistency, we also connec the ports of the sdf to the body
        model.connect(p3, p3Body,"s3_port", "s3", EdgeTrait.MOC_ABSTRACTIONEDGE); //input
        model.connect(p3, p3Body,"s5_port", "s5", EdgeTrait.MOC_ABSTRACTIONEDGE); //input
        model.connect(p3Body, p3,"s6", "s6_port", EdgeTrait.MOC_ABSTRACTIONEDGE); //output
    
        
        
        
        model.connect(p3Body, Array2OfUint32Type,"s3",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        model.connect(p3Body, Array2OfUint32Type,"s5",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        model.connect(p3Body, Array2OfUint32Type,"s6",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION);        
        
        
        
        
        
        ///////////////////////////////////////////////////////////////////////////////
        // now we do the same for p4
        final ANSICBlackBoxExecutable p4Body = ANSICBlackBoxExecutable.enforce(model.newVertex("p4Body"));
        p4Body.getPorts().add("s2");
        p4Body.getPorts().add("s4");

        p4Body.setInlinedCode("s4=s2;int out[3];out[0]=s2;out[1]=s2+1;out[2]=s2+2;");
        // its types
        final TypedOperation p4TypedOp = TypedOperation.enforce(p4Body);
        p4TypedOp.setInputPorts(List.of("s2"));
        p4TypedOp.setInputPortTypesPort(model, List.of(uint32Type));
        
        p4TypedOp.setOutputPorts(List.of("s4"));
        p4TypedOp.setOutputPortTypesPort(model, List.of(uint32Type));       
        
        
        // and we connect it to the actual SDF actor
        p4.setCombFunctionsPort(model, Set.of(p4Body));
        // just for consistency, we also connec the ports of the sdf to the body
        model.connect(p4, p4Body,"s2_port", "s2", EdgeTrait.MOC_ABSTRACTIONEDGE); //input

        model.connect(p4Body, p4,"s4", "s4_port", EdgeTrait.MOC_ABSTRACTIONEDGE); //output       
        
        
        
        
        model.connect(p4Body, uint32Type,"s2",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        model.connect(p4Body, uint32Type,"s4",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION);        
        
        
        
        
        
        ///////////////////////////////////////////////////////////////////////////////
        // now we do the same for p5
        final ANSICBlackBoxExecutable p5Body = ANSICBlackBoxExecutable.enforce(model.newVertex("p5Body"));
        p5Body.getPorts().add("s5");
        p5Body.getPorts().add("s4");

        p5Body.setInlinedCode("s5=s4+1;");
        // its types
        final TypedOperation p5TypedOp = TypedOperation.enforce(p5Body);
        p5TypedOp.setInputPorts(List.of("s4"));
        p5TypedOp.setInputPortTypesPort(model, List.of(uint32Type));
        
        p5TypedOp.setOutputPorts(List.of("s5"));
        p5TypedOp.setOutputPortTypesPort(model, List.of(uint32Type));       
        
        
        // and we connect it to the actual SDF actor
        p5.setCombFunctionsPort(model, Set.of(p5Body));
        // just for consistency, we also connec the ports of the sdf to the body
        model.connect(p5, p5Body,"s4_port", "s4", EdgeTrait.MOC_ABSTRACTIONEDGE); //input

        model.connect(p5Body, p5,"s5", "s5_port", EdgeTrait.MOC_ABSTRACTIONEDGE); //output         
        
        
        
        
        
        
        
        
        model.connect(p5Body, uint32Type,"s4",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION); 
        model.connect(p5Body, uint32Type,"s5",  EdgeTrait.TYPING_DATATYPES_DATADEFINITION);      
        
        
        
        
        

        // lets also make the input token be a tokenizeable datablock
        final TokenizableDataBlock sInDB = TokenizableDataBlock.enforce(sIn);
        sInDB.setTokenSizeInBits(32L);
        sInDB.setMaxSizeInBits(32L);

        // lets make everything visualizeable just for the sake of it
        Visualizable.enforce(p1);
        Visualizable.enforce(p2);
        Visualizable.enforce(p3);
        Visualizable.enforce(p4);
        Visualizable.enforce(p5);
        
        
        
        
        Visualizable.enforce(sIn);
        Visualizable.enforce(s1);
        
        Visualizable.enforce(s2);
        Visualizable.enforce(s3);
        Visualizable.enforce(s4);
        Visualizable.enforce(s5);
        Visualizable.enforce(s6);
        
        // and make the connections between them
        model.connect(sIn, p1, EdgeTrait.VISUALIZATION_VISUALCONNECTION);
        model.connect(p1, s1, EdgeTrait.VISUALIZATION_VISUALCONNECTION);
        model.connect(s1, p2, EdgeTrait.VISUALIZATION_VISUALCONNECTION);



        model.connect(p2, s3, EdgeTrait.VISUALIZATION_VISUALCONNECTION);
        model.connect(s3, p3, EdgeTrait.VISUALIZATION_VISUALCONNECTION);

        model.connect(p3, s6, EdgeTrait.VISUALIZATION_VISUALCONNECTION);
        model.connect(s6, p1, EdgeTrait.VISUALIZATION_VISUALCONNECTION);

        model.connect(p2, s2, EdgeTrait.VISUALIZATION_VISUALCONNECTION);
        model.connect(s2, p4, EdgeTrait.VISUALIZATION_VISUALCONNECTION);
        
        model.connect(p4, s4, EdgeTrait.VISUALIZATION_VISUALCONNECTION);
        model.connect(s4, p5, EdgeTrait.VISUALIZATION_VISUALCONNECTION);
        
        model.connect(p5, s5, EdgeTrait.VISUALIZATION_VISUALCONNECTION);
        model.connect(s5, p3, EdgeTrait.VISUALIZATION_VISUALCONNECTION);
        
        
        ////////////////////////////////////////create tiles////
        final GenericProcessingModule tile0 = GenericProcessingModule.enforce(model.newVertex("tile0"));       
        tile0.getPorts().addAll(Set.of("communication", "contained", "execution"));
        
        final GenericProcessingModule tile1 = GenericProcessingModule.enforce(model.newVertex("tile1"));       
        tile1.getPorts().addAll(Set.of("communication", "contained", "execution"));        
 
        final RoundRobinScheduler order0 = RoundRobinScheduler.enforce(model.newVertex("order_0"));
        order0.getPorts().addAll(Set.of("contained", "slot_0", "slot_1", "slot_2", "slot_3","slot_4"));
        
        final RoundRobinScheduler order1 = RoundRobinScheduler.enforce(model.newVertex("order_1"));
        order1.getPorts().addAll(Set.of("contained", "slot_0", "slot_1", "slot_2", "slot_3"));      
        
        
        
        model.connect(tile0, order0,"execution",  EdgeTrait.DECISION_ABSTRACTALLOCATION); 
        model.connect(tile1, order1,"execution",  EdgeTrait.DECISION_ABSTRACTALLOCATION);
        
        model.connect(tile0, order0,"contained",  EdgeTrait.VISUALIZATION_VISUALCONTAINMENT); 
        model.connect(tile1, order1,"contained",  EdgeTrait.VISUALIZATION_VISUALCONTAINMENT); 
 
        
        
    /////////////////////////order on multi core///////
        
        
        // the edge trait is not correct. It should be AbstractScheduling, but I did not find
        // it in EdgeTrait.
        model.connect(order0,p1,"slot_0",  EdgeTrait.VISUALIZATION_VISUALCONTAINMENT); 
        model.connect(order0,p2,"slot_1",  EdgeTrait.VISUALIZATION_VISUALCONTAINMENT); 
        model.connect(order0,p1,"slot_2",  EdgeTrait.VISUALIZATION_VISUALCONTAINMENT); 
        model.connect(order0,p2,"slot_3",  EdgeTrait.VISUALIZATION_VISUALCONTAINMENT); 
        model.connect(order0,p3,"slot_4",  EdgeTrait.VISUALIZATION_VISUALCONTAINMENT); 
        
        
        model.connect(order1,p4,"slot_0",  EdgeTrait.VISUALIZATION_VISUALCONTAINMENT); 
        model.connect(order1,p5,"slot_1",  EdgeTrait.VISUALIZATION_VISUALCONTAINMENT); 
         
        
        try {
			(new ForSyDeModelHandler()).writeModel(model,"example2.fiodl");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //forSyDeModelHandler.writeModel(model, "simple.fiodl");


	}
}
