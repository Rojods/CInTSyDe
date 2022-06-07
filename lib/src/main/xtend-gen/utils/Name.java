package utils;

import forsyde.io.java.core.Vertex;
import forsyde.io.java.typed.viewers.moc.sdf.SDFActor;
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel;

@SuppressWarnings("all")
public class Name {
  public static String name(final Vertex vertex) {
    return vertex.getIdentifier().replace("/", "_");
  }
  
  public static String name(final SDFActor sdf) {
    return sdf.getIdentifier().replace("/", "_");
  }
  
  public static String name(final SDFChannel ch) {
    return ch.getIdentifier().replace("/", "_");
  }
}
