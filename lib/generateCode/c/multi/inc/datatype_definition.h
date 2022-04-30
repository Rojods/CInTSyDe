#ifndef DATATYPE_DEFINITION_
#define DATATYPE_DEFINITION_
#include <stdio.h>
/*
==============================================================
		TYPING_DATATYPES_DOUBLE
==============================================================
*/
typedef double DoubleType;
typedef double Double;

/*
==============================================================
		TYPING_DATATYPES_FLOAT
==============================================================
*/

/*
==============================================================
		TYPING_DATATYPES_INTEGER
==============================================================
*/
typedef unsigned short UInt16;
typedef unsigned int UInt32;

/*
==============================================================
		TYPING_DATATYPES_ARRAY
==============================================================
*/
typedef DoubleType Array6OfDoubleType[6];
typedef UInt16 Array2OfUInt16[2];
typedef DoubleType *ArrayXOfDoubleType;
typedef ArrayXOfDoubleType *ArrayXOfArrayXOfDoubleType;

#endif
