package utils
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor


import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel
import forsyde.io.java.core.Vertex
class Name {
	public static def String name(Vertex vertex){
		return vertex.getIdentifier().replace("/","_")
	}
	
	
	public static def String name(SDFActor sdf){
		return sdf.getIdentifier().replace("/","_")
	}
	
	public static def String name(SDFChannel ch){
		return ch.getIdentifier().replace("/","_")
	}	
}