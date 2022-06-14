#include "FreeRTOS.h"
#include "semphr.h"
#include "timers.h"	
#include "queue.h"
#include "../datatype/datatype_definition.h"
/*
============================================
SDFChannel s4 Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_s4;
/* maximum number of tokens in message queue */
int queue_length_s4 = 1;
/* size of token */
long item_size_s4 = sizeof(UInt32);

/*
============================================
SDFChannel s5 Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_s5;
/* maximum number of tokens in message queue */
int queue_length_s5 = 2;
/* size of token */
long item_size_s5 = sizeof(UInt32);

/*
============================================
SDFChannel s6 Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_s6;
/* maximum number of tokens in message queue */
int queue_length_s6 = 2;
/* size of token */
long item_size_s6 = sizeof(UInt32);

/*
============================================
SDFChannel s_in Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_s_in;
/* maximum number of tokens in message queue */
int queue_length_s_in = 20;
/* size of token */
long item_size_s_in = sizeof(UInt32);

/*
============================================
SDFChannel s1 Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_s1;
/* maximum number of tokens in message queue */
int queue_length_s1 = 1;
/* size of token */
long item_size_s1 = sizeof(UInt32);

/*
============================================
SDFChannel s2 Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_s2;
/* maximum number of tokens in message queue */
int queue_length_s2 = 1;
/* size of token */
long item_size_s2 = sizeof(UInt32);

/*
============================================
SDFChannel s3 Message Queue
============================================
*/
/* msg queue */
QueueHandle_t msg_queue_s3;
/* maximum number of tokens in message queue */
int queue_length_s3 = 2;
/* size of token */
long item_size_s3 = sizeof(UInt32);



