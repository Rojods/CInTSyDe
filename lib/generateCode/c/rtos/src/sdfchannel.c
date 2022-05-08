#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#include "../inc/datatype_definition.h"
/*
============================================
SDFChannel GrayScaleToAbs Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_GrayScaleToAbs;
/* maximum number of tokens in message queue */
int queue_length_GrayScaleToAbs = 2;
/* size of token */
long item_size_GrayScaleToAbs = sizeof(UInt16);

/*
============================================
SDFChannel AbsY Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_AbsY;
/* maximum number of tokens in message queue */
int queue_length_AbsY = 1;
/* size of token */
long item_size_AbsY = sizeof(UInt16);

/*
============================================
SDFChannel gysig Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_gysig;
/* maximum number of tokens in message queue */
int queue_length_gysig = 6;
/* size of token */
long item_size_gysig = sizeof(DoubleType);

/*
============================================
SDFChannel AbsX Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_AbsX;
/* maximum number of tokens in message queue */
int queue_length_AbsX = 1;
/* size of token */
long item_size_AbsX = sizeof(UInt16);

/*
============================================
SDFChannel GrayScaleToGetPx Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_GrayScaleToGetPx;
/* maximum number of tokens in message queue */
int queue_length_GrayScaleToGetPx = 6;
/* size of token */
long item_size_GrayScaleToGetPx = sizeof(DoubleType);

/*
============================================
SDFChannel gxsig Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_gxsig;
/* maximum number of tokens in message queue */
int queue_length_gxsig = 6;
/* size of token */
long item_size_gxsig = sizeof(DoubleType);

/*
============================================
SDFChannel absysig Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_absysig;
/* maximum number of tokens in message queue */
int queue_length_absysig = 1;
/* size of token */
long item_size_absysig = sizeof(DoubleType);

/*
============================================
SDFChannel absxsig Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_absxsig;
/* maximum number of tokens in message queue */
int queue_length_absxsig = 1;
/* size of token */
long item_size_absxsig = sizeof(DoubleType);

/*
============================================
SDFChannel GrayScaleX Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_GrayScaleX;
/* maximum number of tokens in message queue */
int queue_length_GrayScaleX = 1;
/* size of token */
long item_size_GrayScaleX = sizeof(UInt16);

/*
============================================
SDFChannel GrayScaleY Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_GrayScaleY;
/* maximum number of tokens in message queue */
int queue_length_GrayScaleY = 1;
/* size of token */
long item_size_GrayScaleY = sizeof(UInt16);



