package noop.stdlib;

import noop.graph.Controller;
import noop.model.*;
import noop.operations.NewEdgeOperation;
import noop.operations.NewProjectOperation;
import org.joda.time.Instant;

import java.util.UUID;

import static noop.graph.Edge.EdgeType.TYPEOF;

/**
 * TODO: when we have a way to share serialized noop code, remove this class
 * @author alexeagle@google.com (Alex Eagle)
 */
public class StandardLibraryBuilder {
  public Clazz intClazz;
  public Clazz consoleClazz;
  public Clazz stringClazz;
  public Clazz voidClazz;
  public Clazz booleanClazz;
  public Method printMethod;
  public Method integerPlus;
  public Method integerEquals;

  public void build(Controller controller) {

    Project project = new Project("Noop", "com.google.noop", "Apache 2");

    Library lang = new Library(UUID.randomUUID(), "lang");
    project.addLibrary(lang);

    stringClazz = new Clazz("String");
    lang.addClazz(stringClazz);

    voidClazz = new Clazz("Void");
    lang.addClazz(voidClazz);

    Library io = new Library(UUID.randomUUID(), "io");
    project.addLibrary(io);

    consoleClazz = new Clazz("Console");
    io.addClazz(consoleClazz);

    printMethod = new Method("print");
    consoleClazz.addBlock(printMethod);

    Parameter printArg = new Parameter("s");
    printMethod.addParameter(printArg);

    booleanClazz = new Clazz("Boolean");
    lang.addClazz(booleanClazz);

    intClazz = new Clazz("Integer");
    lang.addClazz(intClazz);

    integerPlus = new Method("+");
    intClazz.addBlock(integerPlus);
    intClazz.addComment(new Comment("Elements may have symbols in their names." +
        " Tools may choose to render this as infix",
        System.getProperty("user.name"), new Instant()));

    integerEquals = new Method("==");
    intClazz.addBlock(integerEquals);

    Parameter integerPlusArg = new Parameter("i");
    integerPlus.addParameter(integerPlusArg);

    controller.apply(new NewProjectOperation(project));
    controller.apply(new NewEdgeOperation(printMethod, TYPEOF, voidClazz));
    controller.apply(new NewEdgeOperation(printArg, TYPEOF, stringClazz));
    controller.apply(new NewEdgeOperation(integerPlus, TYPEOF, intClazz));
    controller.apply(new NewEdgeOperation(integerEquals, TYPEOF, booleanClazz));
    controller.apply(new NewEdgeOperation(integerPlusArg, TYPEOF, intClazz));
  }
}
