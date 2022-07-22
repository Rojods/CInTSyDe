#ifndef SDF_MODULE_{{actorName}}_H_
#define SDF_MODULE_{{actorName}}_H_

#include "forsyde.hpp"
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
SC_MODULE({{actorClassName}})
{
    // first the input ports are instantiated
    {{#inPorts}}
    ForSyDe::SDF::in_port<{{portTypeName}}> {{portName}};
    {{inPorts}}
    // and then the global output Ports
    {{#outPorts}}
    ForSyDe::SDF::out_port<{{portTypeName}}> {{portName}};
    {{outPorts}}


    // Instantiating signals
    {{#signalsContained}}
    ForSyDe::SDF::signal<{{signalTypeName}}> {{signalName}};
    {{signalsContained}}

    SC_CTOR({{actorClassName}}) {
        {{#containedLeafActor}}
        {{constructorCall}} *{{childActorName}} = new SDF::{{constructorCall}}({{childActorName}}, {{childInlinedFunctionName}}, {{orderedRates}});

        {{#boundPort}}
        {{childActorName}}->{{portName}}({{boundPortName}});
        {{boundPortName}}
        {{containedLeafActor}}

        {{#childModules}}
        auto {{actorName}} = new {{actorClassName}}({{actorName}});

        // bind the ports accordingly
        {{#boundPort}}
        {{childActorName}}->{{portName}}({{boundPortName}});
        {{boundPortName}}
        {{childModules}}

    }

}

#endif