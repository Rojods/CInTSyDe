#ifndef SPINLOCK_H_
#define SPINLOCK_H_
typedef struct{
	int flag;
}spinlock;
//void spinlock_init(spinlock* lock);
void spinlock_get(spinlock* lock);
void spinlock_release(spinlock* lock);
#endif
