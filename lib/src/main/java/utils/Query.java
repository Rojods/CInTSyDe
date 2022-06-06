package utils;

import java.util.ArrayList;



import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.alg.shortestpath.BFSShortestPath;

import forsyde.io.java.core.EdgeInfo;
import forsyde.io.java.core.EdgeTrait;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import forsyde.io.java.core.VertexAcessor;
import forsyde.io.java.core.VertexProperty;
import forsyde.io.java.core.VertexTrait;
import forsyde.io.java.core.VertexViewer;
import forsyde.io.java.typed.viewers.impl.ANSICBlackBoxExecutable;
import forsyde.io.java.typed.viewers.impl.ANSICBlackBoxExecutableViewer;
import forsyde.io.java.typed.viewers.impl.DataBlock;
import forsyde.io.java.typed.viewers.impl.Executable;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannelViewer;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActorViewer;
import forsyde.io.java.typed.viewers.platform.GenericProcessingModule;
import forsyde.io.java.typed.viewers.typing.TypedOperation;
import forsyde.io.java.typed.viewers.typing.datatypes.Array;
import forsyde.io.java.typed.viewers.typing.datatypes.ArrayViewer;
import generator.Generator;

import java.lang.Math;

public class Query {

	public static boolean isOnOneCoreChannel(ForSyDeSystemGraph model,Vertex channel) {
		var inputActor=VertexAcessor.getNamedPort(model,channel,"producer" , VertexTrait.MOC_SDF_SDFACTOR).orElse(null);
		var outputActor = VertexAcessor.getNamedPort(model,channel,"consumer" , VertexTrait.MOC_SDF_SDFACTOR).orElse(null);
		if(inputActor==null||outputActor==null) {
			return true;
		}
		
		
		Set<Vertex> tiles = model.vertexSet().stream()
				.filter(v->GenericProcessingModule.conforms(v))
				.collect(Collectors.toSet());
		BFSShortestPath<Vertex,EdgeInfo> bfs = new BFSShortestPath<>(model);
		
		Vertex tile_input=null;
		Vertex tile_output=null;
		for(Vertex tile:tiles) {
			var a=	bfs.getPath(tile,inputActor);
			if(a!=null&&a.getLength()==2) {
				tile_input = tile;
				break;
			}
		}
		for(Vertex tile:tiles) {
			var a=	bfs.getPath(tile,outputActor);
			if(a!=null&&a.getLength()==2) {
				tile_output = tile;
				break;
			}
		}		
		if(tile_output==tile_input) {
			return true;
		}
		return false;
	}
	
	public static Vertex findTile(ForSyDeSystemGraph model,Vertex vertex) {
		
		if(SDFActor.conforms(vertex)) {
			Set<Vertex> tiles = model.vertexSet().stream()
					.filter(v->GenericProcessingModule.conforms(v))
					.collect(Collectors.toSet());
			BFSShortestPath<Vertex,EdgeInfo> bfs = new BFSShortestPath<>(model);
			
			Vertex targetTile=null;
			for(Vertex tile:tiles) {
				var a=	bfs.getPath(tile,vertex);
				if(a!=null&&a.getLength()==2) {
					targetTile = tile;
					return targetTile;
				}
			}		
		}
		
			
		return null;
		
	}
	
	
	public static Set<Vertex> findAllExternalDataBlocks(ForSyDeSystemGraph model) {
		return model.vertexSet().stream().filter(v -> DataBlock.conforms(v) && !SDFChannel.conforms(v))
				.collect(Collectors.toSet());
	}

	public static Set<Vertex> findAllExternalDataBlocks(ForSyDeSystemGraph model, SDFActor actor) {
		return model.vertexSet().stream().filter(v -> DataBlock.conforms(v) && !SDFChannel.conforms(v))
				.filter(data -> model.hasConnection(SDFActor.safeCast(actor).get(), DataBlock.safeCast(data).get())
						|| model.hasConnection(DataBlock.safeCast(data).get(), SDFActor.safeCast(actor).get()))
				.collect(Collectors.toSet());
	}

	public static Vertex findVertexByName(ForSyDeSystemGraph model, String name) {
		return model.vertexSet().stream().filter(v -> v.getIdentifier().equals(name)).findAny().orElse(null);
	}

	public static String findSDFChannelDataType(ForSyDeSystemGraph model, Vertex sdfchannel) {
		EdgeInfo inputedge = model.edgeSet().stream().filter(e -> e.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE))
				.filter(e -> e.getSource().equals(sdfchannel.getIdentifier())).findAny().orElse(null);
		EdgeInfo outputedge = model.edgeSet().stream().filter(e -> e.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE))
				.filter(e -> e.getTarget().equals(sdfchannel.getIdentifier())).findAny().orElse(null);
		
		String actorname="";
		String port;
		Vertex actor;
		
		try {
			if (inputedge != null) {
				// actor's input sdf channel
				actorname = inputedge.getTarget();
				port = inputedge.getTargetPort().get();
				actor = model.queryVertex(actorname).get();   //findVertexByName(model, actorname);
				var impls = Query.findCombFuntionVertex(model, actor);


				EdgeInfo info = model.outgoingEdgesOf(actor).stream().filter(e -> impls.contains(e.getTarget()))
						.filter(e -> e.getSourcePort().get().equals(port)).findAny().get();

				String implName = info.getTarget();
				String implPort = info.getTargetPort().get();

				var implDataType = findImplPortDataType(model, findVertexByName(model, implName), implPort);
				Vertex datatypeVertex = findVertexByName(model, implDataType);

				if (!Array.conforms(datatypeVertex)) {
					return implDataType;
				} else {
					return (new ArrayViewer(datatypeVertex)).getInnerTypePort(model).get().getIdentifier();
				}

			} else if (outputedge != null) {
				// output sdf channel
				actorname = outputedge.getSource();
				port = outputedge.getSourcePort().get();
				actor = findVertexByName(model, actorname);
				var impls = Query.findCombFuntionVertex(model, actor);

				EdgeInfo info = model.incomingEdgesOf(actor).stream().filter(e -> impls.contains(e.getSource()))
						.filter(e -> e.getTargetPort().get().equals(port)).findAny().get();

				String implName = info.getSource();
				String implPort = info.getSourcePort().get();

				var implDataType = findImplPortDataType(model, findVertexByName(model, implName), implPort);
				Vertex datatypeVertex = findVertexByName(model, implDataType);
				if (!Array.conforms(datatypeVertex)) {
					return implDataType;
				} else {
					return (new ArrayViewer(datatypeVertex)).getInnerTypePort(model).get().getIdentifier();
				}
			} else {
				return "<ERROR! "+actorname+" Not Connected To Any ConbFunctions ! >";
			}			
		}catch(Exception e){
			return "<ERROR! "+actorname+" Not Connected To Any ConbFunctions ! >";
		}
		


	}

	/**
	 * 
	 * @param sdf   is an input channel of actor
	 * @param actor
	 * @return the actor's port which connected to the this sdf channel
	 */
	public static String findActorPortConnectedToInputChannel(ForSyDeSystemGraph model, Vertex sdf, Vertex actor) {
		EdgeInfo edge = model.edgeSet().stream().filter(e -> e.getSource() == sdf.getIdentifier())
				.filter(e -> e.getTarget() == actor.getIdentifier()).findAny().get();

		return edge.getTargetPort().orElse("Error!Port Not Found!");
	}

	public static Vertex findSDFChannel(ForSyDeSystemGraph model, Vertex actor, String port) {
		return VertexAcessor.getNamedPort(model, actor, port, VertexTrait.MOC_SDF_SDFCHANNEL).orElse(null);
	}

	/**
	 * Assuming there is an edge from actor port actorPort to impl port implPort,
	 * the aim of this function is to find port actorPort
	 * 
	 * @param actor
	 * @param impl
	 * @param implPort
	 * @return
	 */
	public static String findActorPortConnectedToImplInputPort(ForSyDeSystemGraph model, Vertex actor, Vertex impl,
			String implPort) {

		try {
			String actorPort = model.outgoingEdgesOf(actor).stream()
					.filter(e -> e.getTarget().equals(impl.getIdentifier()))
					.filter(e -> e.getTargetPort().isPresent() && e.getTargetPort().get().equals(implPort)).findAny()
					.get().getSourcePort().get();
			return actorPort;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * Assuming there is an edge from impl port implPort to actor port actorPort,
	 * the aim of this function is to find port actorPort
	 * 
	 * @param actor
	 * @param impl
	 * @param implPort
	 * @return
	 */
	public static String findActorPortConnectedToImplOutputPort(ForSyDeSystemGraph model, Vertex actor, Vertex impl,
			String implPort) {
		try {

			String actorPort = model.incomingEdgesOf(actor).stream()
					.filter(e -> e.getSource().equals(impl.getIdentifier()))
					.filter(e -> e.getSourcePort().isPresent() && e.getSourcePort().get().equals(implPort)).findAny()
					.get().getTargetPort().get();
			return actorPort;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * find the datatype of implPort according to the edge instead of portToType
	 * property
	 * 
	 * @param impl
	 * @param implPort
	 * @return
	 */
	public static String findImplPortDataType(ForSyDeSystemGraph model, Vertex impl, String implPort) {
			//System.out.println(impl.getIdentifier()+"  "+implPort);
		Optional<EdgeInfo> op = model.outgoingEdgesOf(impl).stream()
				.filter(e -> e.hasTrait(EdgeTrait.TYPING_DATATYPES_DATADEFINITION))
				.filter(e -> e.getSourcePort().get().equals(implPort)).findAny();
		if (op.isPresent()) {
			return op.get().getTarget();
		}

		return "";
	}

	public static List<String> findImplInputPorts(Vertex impl) {
//		var ret = TypedOperation.safeCast(impl).get().getInputPorts();
//		return ret;
		return (ArrayList<String>) impl.getProperties().get("inputPorts").unwrap();
	}

	public static List<String> findImplOutputPorts(Vertex impl) {
//		var ret = TypedOperation.safeCast(impl).get().getOutputPorts();
//		return ret;
		return (ArrayList<String>) impl.getProperties().get("outputPorts").unwrap();
	}

	public static Set<String> findCombFuntionVertex(ForSyDeSystemGraph model, Vertex actor) {

		var a =SDFActor.safeCast(actor).get().getCombFunctionsPort(model).stream().map(v->v.getIdentifier()).collect(Collectors.toSet());
		return a;
	}

	/**
	 * get sdf channels from which the actor read tokens
	 * 
	 * @param actor
	 * @return
	 */
	public static Set<Vertex> findInputSDFChannels(ForSyDeSystemGraph model, Vertex actor) {
		var inputSDFChannelSet = model.vertexSet().stream().filter(v -> SDFChannel.conforms(v)).filter(v -> model
				.hasConnection(new SDFChannelViewer(v), new SDFActorViewer(actor), EdgeTrait.MOC_SDF_SDFDATAEDGE))
				.collect(Collectors.toSet());
		return inputSDFChannelSet;
	}

	/**
	 * get all the sdf channel to which this actor writes data
	 * 
	 * @param actor
	 * @return
	 */
	public static Set<Vertex> findOutputSDFChannels(ForSyDeSystemGraph model, Vertex actor) {
		var outputSDFChannelSet = model.vertexSet().stream().filter(v -> SDFChannel.conforms(v))
				.filter(v -> model.hasConnection(SDFActor.safeCast(actor).get(), SDFChannel.safeCast(v).get(),
						EdgeTrait.MOC_SDF_SDFDATAEDGE))
				.collect(Collectors.toSet());
		return outputSDFChannelSet;
	}

	public static String findInputSDFChannelConnectedToActorPort(ForSyDeSystemGraph model, Vertex actor,
			String actorPort) {
		var edge = model.incomingEdgesOf(actor).stream()
				.filter(edgeinfo -> edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE)
						|| edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT))
				.filter(e -> e.getTargetPort().get().equals(actorPort)).findAny().orElse(null);
		if (edge != null) {
			return edge.getSource();
		}
		return actor.getIdentifier() + " port " + actorPort + " Not read from any sdf channel";

	}

	public static EdgeInfo findInputSystemChannelConnectedToActorPort(ForSyDeSystemGraph model, Vertex actor,
			String actorPort) {

		try {
			var edge = model.incomingEdgesOf(actor).stream()
					.filter(edgeinfo -> edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT))
					.filter(e -> e.getTargetPort().get().equals(actorPort)).findAny().orElse(null);
			return edge;
		} catch (Exception e) {
			return null;
		}

	}

	public static EdgeInfo findOutputSystemChannelConnectedToActorPort(ForSyDeSystemGraph model, Vertex actor,
			String actorPort) {
		var edge = model.outgoingEdgesOf(actor).stream()
				.filter(edgeinfo -> edgeinfo.hasTrait(EdgeTrait.IMPL_DATAMOVEMENT))
				.filter(e -> e.getSourcePort().get().equals(actorPort)).findAny().orElse(null);
		return edge;

	}

	public static String findOutputSDFChannelConnectedToActorPort(ForSyDeSystemGraph model, Vertex actor,
			String actorPort) {
		var edge = model.outgoingEdgesOf(actor).stream()
				.filter(edgeinfo -> edgeinfo.hasTrait(EdgeTrait.MOC_SDF_SDFDATAEDGE))
				.filter(e -> e.getSourcePort().get().equals(actorPort)).findAny().orElse(null);
		if (edge != null) {
			return edge.getTarget();
		}
		return actor.getIdentifier() + " port " + actorPort + " Not write to any sdf channel";

	}

	/**
	 * 
	 * @param v is a combFunction actor
	 * @return
	 */
	public static String getInlineCode(Vertex impl) {
		
		Map<String, VertexProperty> properties = impl.getProperties();

		var tmp2 = ANSICBlackBoxExecutable.safeCast(impl).get().getInlinedCode();
		if(properties.get("inlinedCode")!=null) {
			var tmp = properties.get("inlinedCode").unwrap();
			String inlineCode = (String) tmp;
			var b = new StringBuilder(inlineCode);
			var start = 0;
			var index = 0;

			while ((index = b.indexOf(";", start)) != -1) {
				start = index + 1;
				b.insert(index + 1, "\n");
			}

			start = 0;
			index = 0;
			while ((index = b.indexOf("}", start)) != -1) {
				start = index + 1;
				b.insert(index + 1, "\n");
			}

			start = 0;
			index = 0;
			while ((index = b.indexOf("{", start)) != -1) {
				start = index + 1;
				b.insert(index + 1, "\n");
			}
			return b.toString();			
		}else {
			return "";
		}

	}

	/**
	 * return the number of tokens in the channel.
	 * 
	 * @param ch is a channel
	 * @return the number of tokens in the channel ch
	 */
	public static int getBufferSize(Vertex ch) {

		Map<String, VertexProperty> a = ch.getProperties();
		if (a.get("maximumTokens") != null) {
			Integer ret = (Integer) a.get("maximumTokens").unwrap();
			return ret;
		} else {
//			 long b = (Long)a.get("tokenSizeInBits").unwrap();
//			 long c = (Long)a.get("maxSizeInBits").unwrap();
//			 return (int) (c/b);
			return 1;
		}

	}

	public static long getTokenSize(SDFChannel ch) {
		Map<String, VertexProperty> a = ch.getProperties();
		long b = (Long) a.get("tokenSizeInBits").unwrap();

		return (long) Math.ceil(((float) b) / 8);

	}

	public static long getTokenSize(Vertex ch) {
		Map<String, VertexProperty> a = ch.getProperties();
		if (a.get("tokenSizeInBits") != null) {
//			System.out.println(ch.getIdentifier());
			var tmp = a.get("tokenSizeInBits").unwrap();
			if (tmp instanceof Integer) {
				int b = (Integer) a.get("tokenSizeInBits").unwrap();
				return (long) Math.ceil(((float) b) / 8);
			}
			if (tmp instanceof Long) {
				long b = (Long) a.get("tokenSizeInBits").unwrap();
				return (long) Math.ceil(((float) b) / 8);
			}

			return -1;

		} else {
			return 1;
		}

	}

	public static long getTokenSizeInBits(Vertex vertex) {

		Map<String, VertexProperty> a = vertex.getProperties();
		long b = (Long) a.get("tokenSizeInBits").unwrap();
		return b;

	}

	/**
	 * 
	 * @param vertex vertex must be a has trait SDFComb
	 * @return return wcet, if there is no wcet in model, return -1
	 */
	public static int getWCET(Vertex vertex, ForSyDeSystemGraph model) {
		Optional<Vertex> a;
		Set<Vertex> wcet = new HashSet<>();
		for (Vertex v : model.vertexSet()) {
			if (v.hasTrait("WCET")) {
				wcet.add(v);

			}
		}
		for (Vertex v : wcet) {
			a = VertexAcessor.getNamedPort(model, v, "application", VertexTrait.MOC_SDF_SDFACTOR);
			if (a.isPresent() && a.get() == vertex) {
				Map<String, VertexProperty> b = v.getProperties();
				int c = (int) b.get("time").unwrap();
				return c;
			}
		}
		return 4000;
	}

	public static String getInnerType(ForSyDeSystemGraph model, Vertex arrayType) {
		var innerType = model.outgoingEdgesOf(arrayType).stream()
				.filter(e -> e.hasTrait(EdgeTrait.TYPING_DATATYPES_DATADEFINITION))
				.filter(e -> e.getSource().equals(arrayType.getIdentifier())
						&& e.getSourcePort().get().equals("innerType"))
				.findAny().get().getTarget();

		return innerType;
	}

	public static int getMaximumElems(Vertex typeVertex) {
		var maximumElems = 0;
		if (typeVertex.getProperties().get("maximumElems") != null) {
			maximumElems = ((Integer) typeVertex.getProperties().get("maximumElems").unwrap());
		} else {
			maximumElems = ((Integer) typeVertex.getProperties().get("production").unwrap());
		}
		return maximumElems;
	}

	public static String isSystemChannel(ForSyDeSystemGraph model, Vertex impl, String implPort) {
		String actorName = model.edgeSet().stream().filter(
				e -> e.getTarget().equals(impl.getIdentifier()) && e.getSourcePort().get().equals("combFunctions"))
				.findAny().get().getSource();

//		model.vertexSet().stream()
//				.filter(v->model.hasConnection(SDFComb.safeCast(v).get(), Executable.safeCast(impl).get())
//						||
//						model.hasConnection(Executable.safeCast(impl).get(),SDFComb.safeCast(v).get())
//						).findAny();

		var actor = model.queryVertex(actorName).get();
		var actorport1 = Query.findActorPortConnectedToImplInputPort(model, actor, impl, implPort);

		if (actorport1 != null) {
			var edgeinfo1 = Query.findInputSystemChannelConnectedToActorPort(model, actor, actorport1);
			var edgeinfo2 = Query.findOutputSystemChannelConnectedToActorPort(model, actor, actorport1);
			if (edgeinfo1 != null) {
				var datablock1 = edgeinfo1.getSource();
				return datablock1;
			}
			if (edgeinfo2 != null) {
				var datablock2 = edgeinfo2.getTarget();
				return datablock2;
			}
		}

		return null;
	}
}
