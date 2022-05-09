#include "../inc/spinlock.h"
#include "../inc/extern_datablock.h"
spinlock spinlock_system_img_source_global={.flag=0};
spinlock spinlock_system_img_sink_global={.flag=0};
spinlock spinlock_dimX_global={.flag=0};
spinlock spinlock_dimY_global={.flag=0};
spinlock spinlock_outputImage={.flag=0};
spinlock spinlock_inputImage={.flag=0};
