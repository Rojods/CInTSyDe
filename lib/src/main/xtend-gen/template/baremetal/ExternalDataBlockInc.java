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

@FileTypeAnno(type = FileType.C_INCLUDE)
@SuppressWarnings("all")
public class ExternalDataBlockInc implements InitTemplate {
  public String create() {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      Set<Vertex> externDataBlocks = Query.findAllExternalDataBlocks(model);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#ifndef EXTERNAL_DATABLOCK_H_");
      _builder.newLine();
      _builder.append("#define EXTERNAL_DATABLOCK_H_");
      _builder.newLine();
      _builder.newLine();
      {
        for(final Vertex data : externDataBlocks) {
          _builder.append("#define ");
          String _upperCase = data.getIdentifier().toUpperCase();
          _builder.append(_upperCase);
          _builder.append("_BLOCKING 0");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      _builder.append("#endif ");
      _builder.newLine();
      _builder.newLine();
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  public String getFileName() {
    return "extern_datablock";
  }
}
