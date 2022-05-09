package demo;

import org.eclipse.xtext.xbase.lib.InputOutput;

/**
 * load fiodl file
 */
@SuppressWarnings("all")
public class test {
  public static void main(final String[] args) {
    final String path = "a.fiodl";
    demo1.test(path);
    InputOutput.<String>println("end!");
  }
}
