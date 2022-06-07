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
typedef unsigned int UInt32;
typedef unsigned short UInt16;

/*
==============================================================
		TYPING_DATATYPES_ARRAY
==============================================================
*/
typedef Double Array1000OfDouble[1000];
typedef Array1000OfDouble Array1000OfArrayOfDouble[1000];
typedef DoubleType *ArrayXOfDoubleType;
typedef ArrayXOfDoubleType *ArrayXOfArrayXOfDoubleType;
typedef DoubleType Array6OfDoubleType[6];
typedef UInt16 Array2OfUInt16[2];

					
#endif
