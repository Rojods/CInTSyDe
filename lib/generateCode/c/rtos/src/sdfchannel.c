#include "semphr.h"
#include "timers.h"	
#include "FreeRTOS.h"
#include "queue.h"
/*
============================================
SDFChannel GrayScaleToAbs Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_GrayScaleToAbs;
/* maximum number of tokens in message queue */
int queue_length_GrayScaleToAbs = 1;
/* size of token */
long item_size_GrayScaleToAbs = 1;

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
long item_size_AbsY = 2;

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
long item_size_AbsX = 2;

/*
============================================
SDFChannel GrayScaleToGetPx Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_GrayScaleToGetPx;
/* maximum number of tokens in message queue */
int queue_length_GrayScaleToGetPx = 1;
/* size of token */
long item_size_GrayScaleToGetPx = 1;

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
long item_size_gysig = 1;

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
long item_size_gxsig = 1;

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
long item_size_absysig = 1;

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
long item_size_GrayScaleX = 2;

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
long item_size_absxsig = 1;

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
long item_size_GrayScaleY = 2;



