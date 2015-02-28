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
    int turnaround; //in seconds
    time_t start;
    time_t finish;
} Student;

struct itimerval timer;  // professor's office hour timer
time_t startTime;

const char* priorities[] = {"GS", "RS", "EE"};
int section_enrollment[SECTIONS_COUNT]; //number of students enrolled
int queue_total[] = {0, 0, 0}; //counts total amount of students that entered each queue
Student allStudents[STUDENT_COUNT]; //holds all students
Student section_list[SECTIONS_COUNT][STUDENT_MAX_CAPACITY]; //IDs of enrolled students
Student queues[QUEUES_COUNT][STUDENT_COUNT]; //Queue of Students for each priority
Student dropped[STUDENT_COUNT];
int dropped_count = 0;
int counter = 0; //counter for allStudents array

int heads[] = {0, 0, 0}; //heads of the queues
int tails[] = {0, 0, 0}; //tails of the queues

int totalStudentProcessed = 0;

pthread_mutex_t section_mutex[SECTIONS_COUNT];
pthread_mutex_t queue_mutex[QUEUES_COUNT];
pthread_mutex_t printMutex;

sem_t queue_sem[QUEUES_COUNT];

int timesUp = 0;
int firstPrint = 1;

//returns seconds
int convertTime(time_t t){
    double elapsed = difftime(t, startTime);
    return (int) elapsed;
}

void print(char *event)
{
    time_t now;
    time(&now);
    // double elapsed = difftime(now, startTime);

    int min = 0;
    int sec = convertTime(now);
    min = sec/60;
    sec = sec%60;
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

void studentArrives(int id, time_t startTime)
{
    char event[80];
    int priority = rand()%3;
    int section = (rand()%4);
    //section = 3 if student will take any section
    if (section == 3)
    {
        if(!sectionFull(0))
            section = 0;
        else if(!sectionFull(1))
            section = 1;
        else
            section = 2;
    }
    Student s;
    s.id = id;
    s.section = section;
    s.priority = priority;
    s.start = startTime;
    pthread_mutex_lock(&queue_mutex[s.priority]);
    queues[s.priority][tails[s.priority]] = s;
    tails[s.priority]++;
    pthread_mutex_unlock(&(queue_mutex[s.priority]));
    sprintf(event, "Student #%d.%s arrives.", s.id, priorities[s.priority]);
    print(event);

    sem_post(&queue_sem[s.priority]);
}

// The student thread.
void *student(void *param)
{
    int id = *((int *) param);
    //printf("ID: %d", id);

    // Students will arrive at random times during the office hour.
    time_t t = sleep(rand()%DURATION);
    studentArrives(id, time(&t));

    return NULL;
}

// The queue thread.
void *queue(void *param)
{
    int priority = *((int *) param);
    timer.it_value.tv_sec = DURATION;
    setitimer(ITIMER_REAL, &timer, NULL);
    
    do {
        //critical region: move student from queue to class
        if(!timesUp)
        {
            sem_wait(&(queue_sem[priority]));
            int processingTime;
                if (priority == 0)
                    processingTime = rand()%2 + 1;
                else if (priority == 1)
                    processingTime = rand()%3 + 2;
                else 
                    processingTime = rand()%4 + 3;
            sleep(processingTime);
            Student s = queues[priority][heads[priority]];
            queue_total[priority]++;
            heads[priority]++;

            pthread_mutex_lock(&queue_mutex[s.section]);      
            
            if (!sectionFull(s.section)){
                section_list[s.section][section_enrollment[s.section]] = s;
                section_enrollment[s.section]++;
                totalStudentProcessed++;

                time(&s.finish);
                s.turnaround = convertTime(s.finish) - convertTime(s.start);
                printf("START TIME: %d, FINISH TIME: %d\n", convertTime(s.start), convertTime(s.finish));
                printf("STUDENT TURNAROUND: %d\n", s.turnaround);
                allStudents[counter] = s;
                counter++;

                char event[80];
                sprintf(event, "Student #%d.%s enrolled in section %d (size = %d)", s.id,
                    priorities[priority], s.section + 1, section_enrollment[s.section]);
                print(event);
            }
            else {
                totalStudentProcessed++;
                dropped[dropped_count] = s;
                dropped_count++;

                time(&s.finish);
                s.turnaround = convertTime(s.finish) - convertTime(s.start);
                printf("START TIME: %d, FINISH TIME: %d\n", convertTime(s.start), convertTime(s.finish));
                printf("STUDENT TURNAROUND: %d\n", s.turnaround);
                allStudents[counter] = s;
                counter++;

                char event[80];
                sprintf(event, "Student #%d.%s was dropped trying to join section %d", s.id,
                    priorities[priority], s.section + 1);
                print(event);
            }
            pthread_mutex_unlock(&queue_mutex[s.section]);
        }
    } while (!timesUp); 
    
    return NULL;
}

int sectionFull(int section) //3 = all sections
{
    if (section == 3)
    {
        return (sectionFull(0) && sectionFull(1) && sectionFull(2));
    }
    else if (section_enrollment[section] >= STUDENT_MAX_CAPACITY)
    {
        return 1;
    }
    return 0;
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

    // Create threads for each queue
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

    // Print enrollment for each section.
    int j;
    for (i = 0; i < SECTIONS_COUNT; i++)
    {
        printf("\nSection %d Enrollment:\n", i + 1);
        for (j = 0; j < section_enrollment[i]; j++)
        {
            Student s = section_list[i][j];
            printf("#%d.%s ", s.id, priorities[s.priority]);
        }
        printf("\n");
    }

    // Print all students who couldn't enroll or dropped.
    printf("Students Dropped:\n");
    for (i = 0; i < dropped_count; i++)
    {
        printf("#%d.%s ", dropped[i].id, priorities[dropped[i].priority]);
    }

    // What was the turnaround time for each student (from arrival time to being enrolled or dropped).
    printf("Student Turnaround Times:\n");
    for (i = 0; i < STUDENT_COUNT; i++)
    {
        Student s = allStudents[i];
        int min = s.turnaround/60;
        int sec = s.turnaround%60;
        printf("[#%d.%s: %1d:%02d]", s.id, priorities[s.priority], min, sec);
    }

    // The average turnaround time of each queue.
    int total_turnaround[] = {0, 0, 0};
    for (i = 0; i < STUDENT_COUNT; i++)
    {
        Student s = allStudents[i];
        total_turnaround[s.priority] += s.turnaround;
    }
    printf("Average Turnaround Times:\n");
    for (i = 0; i < QUEUES_COUNT; i++)
    {
        int averageTurnaround = total_turnaround[i]/queue_total[i];
        int min = averageTurnaround/60;
        int sec = averageTurnaround%60;
        printf("%s Queue: %%1d:%02d\n", priorities[i], min, sec);
    }

    return 0;
}
