#ifndef SDF_MODULE_{{actorName}}_H_
#define SDF_MODULE_{{actorName}}_H_

#include "sdf_process.hpp"
#include "{{dataTypes.targetPath}}"

{{#childModules}}
#include "{{targetPath}}"
{{childModules}}

/*******************************************************************************
 * Functions present in the model, simply inlined here
 */
#include "{{inlinedFunctions.targetPath}}"

/*******************************************************************************
 * Module generated for {{actorClassName}}
 */
class {{actorClassName}} : sdf_process
{
public:
    // first the input ports are instantiated
    {{#inPorts}}
    ForSyDe::SDF::in_port<{{portTypeName}}> {{portName}};
    {{/inPorts}}
    // and then the global output Ports
    {{#outPorts}}
    ForSyDe::SDF::out_port<{{portTypeName}}> {{portName}};
    {{/outPorts}}

    {{actorClassName}}(): {{actorClassName}}("{{actorName}}") {}

    {{actorClassName}}(sc_module_name alternatName): sdf_process(alternatName) {
        {{#containedLeafActor}}
        {{constructorCall}} *{{childActorName}} = new SDF::{{constructorCall}}({{childActorName}}, {{childInlinedFunctionName}}, {{orderedRates}});

        {{#boundPort}}
        {{childActorName}}->{{portName}}({{boundPortName}});
        {{boundPortName}}
        {{/containedLeafActor}}

        {{#childModules}}
        auto {{actorName}} = new {{actorClassName}}({{actorName}});

        // bind the ports accordingly
        {{#boundPort}}
        {{childActorName}}->{{portName}}({{boundPortName}});
        {{boundPortName}}
        {{/childModules}}
    }

private:

    // Instantiating signals
    {{#signalsContained}}
    ForSyDe::SDF::signal<{{signalTypeName}}> {{signalName}};
    {{/signalsContained}}

    void init() {
        {{#inPorts}}
        {{portName}}.resize({{portRate}});
        {{/inPorts}}
        {{#outPorts}}
        {{portName}}.resize({{portRate}});
        {{/outPorts}}
    }

    void prop() {
    }

    void exec() {
    }

    void clean() {
    }

}

#endif