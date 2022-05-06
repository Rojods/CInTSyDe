package template.baremetal;

import fileAnnotation.FileType;
import fileAnnotation.FileTypeAnno;
import forsyde.io.java.core.ForSyDeSystemGraph;
import forsyde.io.java.core.Vertex;
import generator.Generator;
import java.util.Set;
import org.eclipse.xtend2.lib.StringConcatenation;
import template.templateInterface.InitTemplate;
import utils.Query;

@FileTypeAnno(type = FileType.C_SOURCE)
@SuppressWarnings("all")
public class ExternalDataBlockSrc implements InitTemplate {
  public String create() {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      Set<Vertex> externDataBlocks = Query.findAllExternalDataBlocks(model);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../inc/spinlock.h\"");
      _builder.newLine();
      _builder.append("#include \"../inc/extern_datablock.h\"");
      _builder.newLine();
      {
        for(final Vertex data : externDataBlocks) {
          _builder.append("spinlock spinlock_");
          String _identifier = data.getIdentifier();
          _builder.append(_identifier);
          _builder.append("={.flag=0};");
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public String getFileName() {
    return "extern_datablock";
  }
}
