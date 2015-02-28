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
	int priority;
	int turnaround;
} Student;

time_t startTime;

const char* priorities[] = {"GS", "RS", "EE"};
int section_enrollment[SECTIONS_COUNT]; //number of students enrolled
int section_list[SECTIONS_COUNT][STUDENT_MAX_CAPACITY]; //IDs of enrolled students
Student queues[QUEUES_COUNT][STUDENT_COUNT]; //Queue of Students for each priority
int heads[] = {0, 0, 0}; //heads of the queues
int tails[] = {0, 0, 0}; //tails of the queues

pthread_mutex_t section_mutex[SECTIONS_COUNT];
pthread_mutex_t queue_mutex[QUEUES_COUNT];
pthread_mutex_t printMutex;

sem_t queue_sem[QUEUES_COUNT];

int timesUp = 0;
int firstPrint = 1;

void print(char *event)
{
    time_t now;
    time(&now);
    double elapsed = difftime(now, startTime);
    int min = 0;
    int sec = (int) elapsed;
    if (sec >= 60) {
        min++;
        sec -= 60;
    }
    // Acquire the mutex lock to protect the printing.
    pthread_mutex_lock(&printMutex);

    if (firstPrint) {
        printf("TIME | EVENT\n");
        firstPrint = 0;
    }
    // Elapsed time.
    printf("%1d:%02d | %s\n", min, sec, event);
    // Release the mutex lock.
    pthread_mutex_unlock(&printMutex);
}

void studentArrives(int id)
{
    char event[80];
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
	s.priority = priority;
    pthread_mutex_lock(&queue_mutex[s.priority]);
    queues[s.priority][tails[s.priority]] = s;
    tails[s.priority]++;
    pthread_mutex_unlock(&queue_mutex[s.priority]);
    sprintf(event, "Student #%d.%s arrives.", s.id, priorities[s.priority]);
    print(event);

    sem_post(&queue_sem[priority]);
}

// The student thread.
void *student(void *param)
{
    int id = *((int *) param);
    //printf("ID: %d", id);

    // Students will arrive at random times during the office hour.
    sleep(rand()%DURATION);
    studentArrives(id);

    return NULL;
}

// The queue thread.
void *queue(void *param)
{
    int priority = *((int *) param);
    do {
        sem_wait(&(queue_sem[priority]));
        pthread_mutex_lock(&queue_mutex[priority]);
        //critical region: move student from queue to class
        int processingTime;
        if(priority == 0)
            processingTime = rand()%2 + 1;
        else if(priority == 1)
            processingTime = rand()%3 + 2;
        else 
            processingTime = rand()%4 + 3;
        sleep(processingTime);
        Student s = queues[priority][heads[priority]];
        heads[priority]++;
        section_list[s.section][section_enrollment[s.section]] = s.id;
        section_enrollment[s.section]++;
        pthread_mutex_unlock(&queue_mutex[priority]);
        char event[80];
        sprintf(event, "Student #%d.%s enrolled in section %d", s.id,
            priorities[priority], s.section + 1);
        print(event);
    } while (!timesUp);
    return NULL;
}

int sectionFull(int section) //3 = checks all sections
{
	if (section == 3)
	{
		return sectionFull(0) && sectionFull(1) && sectionFull(2);
	}
	if ()
	{
	}
	return 0; // not full
}

void timerHandler(int signal)
{
    timesUp = 1;  // office hour is over
}

int main(int argc, char *argv[])
{
	int students[STUDENT_COUNT];
    int i;
    srand(time(0));
    time(&startTime);

	// Initialize the mutexes and the semaphore.
    for (i = 0; i < SECTIONS_COUNT; i++)
    {
        pthread_mutex_init(&section_mutex[i], NULL);
    }
    for (i = 0; i < QUEUES_COUNT; i++)
    {
        pthread_mutex_init(&queue_mutex[i], NULL);
        sem_init(&(queue_sem[i]), 0, 0);
    }
    pthread_mutex_init(&printMutex, NULL);

	// Create the student threads.
    for (i = 0; i < STUDENT_COUNT; i++) {
        students[i] = ID_BASE + i;
        pthread_t studentThreadId;
        pthread_attr_t studentAttr;
        pthread_attr_init(&studentAttr);
        pthread_create(&studentThreadId, &studentAttr, student, &students[i]);
    }

    // Create threads for each queue.
    int GS = 0;
	pthread_attr_t queueAttr0;
    pthread_attr_init(&queueAttr0);
    pthread_t queueThreadId0;
    pthread_create(&queueThreadId0, &queueAttr0, queue, &GS);
    
    int RS = 1;
	pthread_attr_t queueAttr1;
    pthread_attr_init(&queueAttr1);
    pthread_t queueThreadId1;
    pthread_create(&queueThreadId1, &queueAttr1, queue, &RS);

    int EE = 2;
	pthread_attr_t queueAttr2;
    pthread_attr_init(&queueAttr2);
    pthread_t queueThreadId2;
    pthread_create(&queueThreadId2, &queueAttr2, queue, &EE);
    
    signal(SIGALRM, timerHandler);
    pthread_join(queueThreadId0, NULL);
    pthread_join(queueThreadId1, NULL);
    pthread_join(queueThreadId2, NULL);

    return 0;
}
