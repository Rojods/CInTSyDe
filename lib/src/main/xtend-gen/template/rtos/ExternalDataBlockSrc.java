package template.rtos;

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
  @Override
  public String create() {
    String _xblockexpression = null;
    {
      ForSyDeSystemGraph model = Generator.model;
      Set<Vertex> externDataBlocks = Query.findAllExternalDataBlocks(model);
      StringConcatenation _builder = new StringConcatenation();
      _builder.append("#include \"../inc/extern_datablock.h\"");
      _builder.newLine();
      _builder.append("#include \"FreeRTOS.h\"");
      _builder.newLine();
      _builder.append("#include \"semphr.h\"");
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("=====================================================");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("Blocking or Non Blokcing Read Write");
      _builder.newLine();
      _builder.append("=====================================================");
      _builder.newLine();
      _builder.append("*/");
      _builder.newLine();
      {
        for(final Vertex data : externDataBlocks) {
          _builder.append("SemaphoreHandle_t datablock_sem_");
          String _identifier = data.getIdentifier();
          _builder.append(_identifier);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.newLine();
      _builder.append("/*");
      _builder.newLine();
      _builder.append("=====================================================");
      _builder.newLine();
      _builder.append("\t\t");
      _builder.append("Counting Smeaphore");
      _builder.newLine();
      _builder.append("=====================================================");
      _builder.newLine();
      _builder.append("*/\t\t\t\t");
      _builder.newLine();
      {
        for(final Vertex data_1 : externDataBlocks) {
          _builder.append("SemaphoreHandle_t count_sem_");
          String _identifier_1 = data_1.getIdentifier();
          _builder.append(_identifier_1);
          _builder.append(";");
          _builder.newLineIfNotEmpty();
        }
      }
      _xblockexpression = _builder.toString();
    }
    return _xblockexpression;
  }
  
  @Override
  public String savePath() {
    throw new UnsupportedOperationException("TODO: auto-generated method stub");
  }
}
