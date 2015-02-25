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
#define QUEUES_COUNT 3
#define STUDENT_MAX_CAPACITY 20

#define DURATION 120

typedef struct
{
    int id;
    int section;
} Student;

const char* priorities[] = {'GS', 'RS', 'EE'};
int section_enrollment[SECTIONS_COUNT];
int section_list[SECTIONS_COUNT][STUDENT_MAX_CAPACITY];
int queue_size[QUEUES_COUNT];
Student queues[QUEUES_COUNT][STUDENT_COUNT];

pthread_mutex_t section_mutex[SECTIONS_COUNT];
pthread_mutex_t queue_mutex[QUEUES_COUNT];
pthread_mutex_t printMutex;

sem_t queue_sem[QUEUES_COUNT];

int timesUp = 0;

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
    int priority = rand()%3;
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
    Student s;
    s.id = id;
    s.section = section;
    pthread_mutex_lock(&queue_mutex[priority]);
    queues[priority][queue_size[priority]] = s;
    queue_size[priority]++;
    pthread_mutex_unlock(&queue_mutex[priority]);
    sprintf(event, "Student %d placed in the %s queue", s.id, priorities[priority]);
    print(event);

    sem_post(%queue_sem[priority]);
}

// The queue thread.
void *queue(void *param)
{
    int priority = *((int *) param);
    if(!timesUp) {
        sem_wait(&queue_sem[priority]);
        //TODO: wait random amount of time
        pthread_mutex_lock(&queue_mutex[priority]);
        //critical region: move student from queue to class
        pthread_mutex_unlock(&queue_mutex[priority]);
        char event[80];
        sprintf(event, "Professor meets with student %d",  meetingId);//change to enrolled in section x
        print(event);
    }
}

int main(int argc, char *argv[])
{
	int students[STUDENT_COUNT];
    int i;
	// Initialize the mutexes and the semaphore.
    for (i = 0; i < SECTIONS_COUNT; i++)
    {
        pthread_mutex_init(&section_mutex[i], NULL);
    }
    for (i = 0; i < QUEUES_COUNT; i++)
    {
        pthread_mutex_init(&queue_mutex[i], NULL);
        sem_init(&queue_sem[i], 0, 0);
    }
    pthread_mutex_init(&printMutex, NULL);

    srand(time(0));
    time(&startTime);

	// Create the student threads.
    for (i = 0; i < STUDENT_COUNT; i++) {
        studentIds[i] = ID_BASE + i;
        pthread_t studentThreadId;
        pthread_attr_t studentAttr;
        pthread_attr_init(&studentAttr);
        pthread_create(&studentThreadId, &studentAttr, student, &students[i]);
    }
    // Create threads for each queue.
    for (i = 0; i < QUEUES_COUNT; i++) {
        pthread_t queueThreadId;
        pthread_attr_t queueAttr;
        pthread_attr_init(&queueAttr);
        pthread_create(&queueThreadId, &queueAttr, queue, &i);
    }
}
