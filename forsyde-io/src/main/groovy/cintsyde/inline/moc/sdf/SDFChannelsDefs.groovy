package cintsyde.inline.moc.sdf

import cintsyde.inline.generic.c.FIFOBuffersFile
import cintsyde.interfaces.FileComponent
import cintsyde.interfaces.StringComponent
import forsyde.io.java.core.ForSyDeSystemGraph
import forsyde.io.java.typed.viewers.moc.sdf.SDFChannel

import java.nio.file.Path

//TODO: finish this class
class SDFChannelsDefs implements FileComponent<ForSyDeSystemGraph> {

    ForSyDeSystemGraph baseModel
    Path targetPath

    FIFOBuffersFile fifoBuffersFile
    List<SDFChannel> sdfChannels

    @Override
    List<StringComponent<ForSyDeSystemGraph>> getStringComponents() {
        return List.of()
    }

    @Override
    Map<String, Object> getContextAsMap() {
        return [
                fifoBufferPath: fifoBuffersFile.targetPath.toString()
        ]
    }

    @Override
    void setContextByMap(Map<String, Object> context) {

    }

    @Override
    String getSuffixTemplateString() {
        return getClass().getResource("SDFChannels.headerOnly.h")
    }

}
