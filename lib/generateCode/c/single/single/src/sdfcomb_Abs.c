/* Includes-------------------------- */
#include "../inc/config.h"
#include "../inc/datatype_definition.h"
#include "../inc/circular_fifo_lib.h"
#include "../inc/sdfcomb_Abs.h"



/*
========================================
	Declare Extern Channal Variables
========================================
*/
/* Input FIFO */
extern circular_fifo_UInt16 fifo_GrayScaleToAbs;
extern spinlock spinlock_GrayScaleToAbs;
extern circular_fifo_UInt16 fifo_AbsY;
extern spinlock spinlock_AbsY;
extern circular_fifo_UInt16 fifo_AbsX;
extern spinlock spinlock_AbsX;
extern circular_fifo_DoubleType fifo_absysig;
extern spinlock spinlock_absysig;
extern circular_fifo_DoubleType fifo_absxsig;
extern spinlock spinlock_absxsig;
/* Output FIFO */
/*
========================================
	Declare Extern Global Variables
========================================
*/			
extern ArrayXOfArrayXOfDoubleType system_img_sink_global;
extern Array1000OfArrayOfDouble outputImage;

/*
========================================
	Actor Function
========================================
*/			
void actor_Abs(){
				
	/* Initilize Memo
y */
	UInt16 offsetX; 
	UInt16 offsetY; 
	Array2OfUInt16 dims; 
	ArrayXOfArrayXOfDoubleType system_img_sink_address = system_img_sink_global; 
	DoubleType resy; 
	DoubleType resx; 
/* Read From Input Port  */
printf("%s\n","read");
int ret=0;
#if ABSXSIG_BLOCKING==0
ret=read_non_blocking_DoubleType(&fifo_absxsig,&resx);
if(ret==-1){
	printf("fifo_absxsig read error\n");
}

#else
read_blocking_DoubleType(&fifo_absxsig,&resx,&spinlock_absxsig);
#endif

#if ABSYSIG_BLOCKING==0
ret=read_non_blocking_DoubleType(&fifo_absysig,&resy);
if(ret==-1){
	printf("fifo_absysig read error\n");
}

#else
read_blocking_DoubleType(&fifo_absysig,&resy,&spinlock_absysig);
#endif

for(int i=0;i<2;++i){
	
	#if GRAYSCALETOABS_BLOCKING==0
	ret=read_non_blocking_UInt16(&fifo_GrayScaleToAbs,&dims[i]);
	if(ret==-1){
		printf("fifo_GrayScaleToAbs read error\n");
	}
	#else
	read_blocking_UInt16(&fifo_GrayScaleToAbs,&dims[i],&spinlock_GrayScaleToAbs);
	#endif
}

#if ABSX_BLOCKING==0
ret=read_non_blocking_UInt16(&fifo_AbsX,&offsetX);
if(ret==-1){
	printf("fifo_AbsX read error\n");
}

#else
read_blocking_UInt16(&fifo_AbsX,&offsetX,&spinlock_AbsX);
#endif

#if ABSY_BLOCKING==0
ret=read_non_blocking_UInt16(&fifo_AbsY,&offsetY);
if(ret==-1){
	printf("fifo_AbsY read error\n");
}

#else
read_blocking_UInt16(&fifo_AbsY,&offsetY,&spinlock_AbsY);
#endif


			
/* Inline Code           */
printf("%s\n","inline code");
/* in combFunction AbsImpl */
if(resx<0.0)resx=-resx;
if(resy<0.0)resy=-resy;
if(offsetX>=dims[0]-2){
offsetY+=1;
offsetX=0;
}
if(offsetY>=dims[1]-2){
offsetY=0;
}
system_img_sink_address[offsetX][offsetY]=resx+resy;

	/* Write To Output Ports */
	printf("%s\n","write");
	#if ABSX_BLOCKING==0
	write_non_blocking_UInt16(&fifo_AbsX,offsetX);
	#else
	write_blocking_UInt16(&fifo_AbsX,offsetX,&spinlock_AbsX);
	#endif
							
	#if ABSY_BLOCKING==0
	write_non_blocking_UInt16(&fifo_AbsY,offsetY);
	#else
	write_blocking_UInt16(&fifo_AbsY,offsetY,&spinlock_AbsY);
	#endif
							
}
