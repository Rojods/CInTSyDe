package demo;

import org.eclipse.xtext.xbase.lib.InputOutput;

@SuppressWarnings("all")
public class test {
  public static void main(final String[] args) {
    String a = "aaa;bbbbbbb;c";
    StringBuilder b = new StringBuilder(a);
    int start = 0;
    int index = 0;
    while (((index = b.indexOf(";", start)) != (-1))) {
      {
        start = (index + 1);
        b.insert((index + 1), "\n");
      }
    }
    InputOutput.<String>println(b.toString());
  }
}
