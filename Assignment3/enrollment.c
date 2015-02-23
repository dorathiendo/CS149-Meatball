#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>
#include <semaphore.h>
#include <signal.h>
#include <sys/time.h>

#define ID_BASE 101

#define STUDENT_COUNT 75
#define SECTIONS_COUNT 3
#define STUDENT_MAX_CAPACITY 20

#define DURATION 120

const char* type[] = {'GS', 'RS', 'EE'};
int section_enrollment[SECTIONS_COUNT];
int section_list[SECTIONS_COUNT][STUDENT_MAX_CAPACITY];

pthread_mutex_t section_mutex[SECTIONS_COUNT];
pthread_mutex_t printMutex;

// The student thread.
void *student(void *param)
{
    int id = *((int *) param);

    // Students will arrive at random times during the office hour.
    sleep(rand()%DURATION);
    studentArrives(id);

    return NULL;
}

void studentArrives(int id)
{
    char event[80];
    arrivalsCount++;
    char* priority = type[rand()%3];
    int section = (rand()%4);
    //section = 3 if student will take any section
    if (section == 3){
        if(section_enrollment[0] < STUDENT_MAX_CAPACITY)
            section = 0;
        else if(section_enrollment[1] < STUDENT_MAX_CAPACITY)
            section = 1;
        else
            section = 2;
    }

    int enrollment = section_enrollment[section];
    if (enrollment < STUDENT_MAX_CAPACITY) {
        pthread_mutex_lock(&section_mutex[section]);
        section_list[section][enrollment] = id;
        section_enrollment[section]++;

        sprintf(event, "Student %d arrives and waits", id);
        print(event);
    }
    else {
        leavesCount++;
        sprintf(event, "Student %d arrives and leaves", id);
        print(event);
    }
}

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
