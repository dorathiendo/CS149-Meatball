#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
#include <signal.h>
#include <sys/time.h>

#define ID_BASE 101;

#define STUDENT_COUNT 75
#define SECTIONS_COUNT 3
#define STUDENT_MAX_CAPACITY 20;

int sections[SECTIONS_COUNT];
pthread_mutex_t chairMutex;  // mutex protects chairs and wait count
pthread_mutex_t printMutex;

int main(int argc, char *argv[]){
	int students[STUDENT_COUNT];
	// Initialize the mutexes and the semaphore.
    pthread_mutex_init(&chairMutex, NULL);
    pthread_mutex_init(&printMutex, NULL);
    sem_init(&filledChairs, 0, 0);

    srand(time(0));
    time(&startTime);

	// Create the student threads.
    int i;
    for (i = 0; i < STUDENT_COUNT; i++) {
        studentIds[i] = ID_BASE + i;
        pthread_t studentThreadId;
        pthread_attr_t studentAttr;
        pthread_attr_init(&studentAttr);
        pthread_create(&studentThreadId, &studentAttr, student, &students[i]);
    }
}
