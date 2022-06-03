#include "{{fifoBufferPath}}"

{{#sdfChannels}}
{{bufferType}}[{{maximumTokens}}] channel_{{channelname}}_buffer;

fifo_{{channelType}} {{channelName}};
{{/sdfChannels}}

void init_sdf_channels() {
    {{#sdfChannels}}
    ref_init(&{{channelName}}, channel_{{channelname}}_buffer, {{maximumTokens}})

    {{#initialTokens}}
    write_non_blocking(&{{channelName}}, {{value}});
    {{/initialTokens}}
    {{/sdfChannels}}
}