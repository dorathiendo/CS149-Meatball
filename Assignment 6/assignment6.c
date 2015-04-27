#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <signal.h>
#include <sys/time.h>
#include <fcntl.h>
#include <unistd.h>
#include <sys/ioctl.h>
#include <time.h>

#define BUFFER_SIZE 64
#define READ_END    0
#define WRITE_END   1
#define TIMEOUT     30
#define NUMBER_OF_CHILDREN 5
#define MAX_SLEEP_TIME 3

/**
    Write a timestamp to the given buffer, representing the time elapsed since startTime
    in M:SS.sss format.
*/
void timeStamp(struct timeval startTime, char* buffer) {
    struct timeval now;
    gettimeofday(&now, NULL);
    //convert to microseconds(us):
    long int usAtStart = startTime.tv_sec * 1000000 + startTime.tv_usec;
    long int usNow = now.tv_sec * 1000000 + now.tv_usec;
    long int delta = usNow - usAtStart;
    //back to seconds
    long int seconds = delta/1000000;
    long int milliseconds = (delta % 1000000) / 1000;
    sprintf(buffer, "0:%02ld.%03ld", seconds, milliseconds);
}

void SIGALRM_handler(int signo)
{
    assert(signo == SIGALRM);
    printf("\nTime's up!\n");
    exit(0);
}



int main(void)
{
    FILE *fp;
    double elapsedTime;
    int result, nread;
    int fds[NUMBER_OF_CHILDREN][2];
    int pids[NUMBER_OF_CHILDREN];
    fd_set inputs, inputfds; //file descriptors
    FD_ZERO(&inputs);    // initialize inputs to the empty set

    struct timeval timeout, startTime, now;
    struct itimerval tval;
    timerclear(& tval.it_interval);
    timerclear(& tval.it_value);
    tval.it_value.tv_sec = TIMEOUT;
    
    signal(SIGALRM, SIGALRM_handler);
    setitimer(ITIMER_REAL, & tval, NULL); //set timer
    gettimeofday(&startTime, NULL); //set start time for timestamps
    fp = fopen("ouput.txt", "w+");

    
    int i;
    for (i = 0; i < 5; i++) {//Start 5 children

        if (pipe(fds[i]) == -1) { //create pipe
            fprintf(stderr,"pipe() failed");
            return 1;
        }
        FD_SET(fds[i][READ_END], &inputs);//insert read end


        pids[i] = fork();

        if(pids[i] == 0){
            char message[BUFFER_SIZE];
            char id[24];
            close(fds[i][READ_END]);
            if (i != 4){//first 4 are random producers
                int msgCounter = 0;
                for (;;){
                    sleep(rand() % MAX_SLEEP_TIME);
                    timeStamp(startTime, message);
                    sprintf(id, ": Child %d Message %d", i, msgCounter);
                    strcat(message, id);
                    write(fds[i][WRITE_END], message, strlen(message)+1);
                    msgCounter++;
                }
                
            }
            else {//last is prompter
                char input[BUFFER_SIZE];
                for(;;){
                    printf("Type message:");
                    scanf("%s", input);
                    timeStamp(startTime, message);
                    strcat(message, ": User Message: ");
                    strcat(message, input);
                    write(fds[i][WRITE_END], message, strlen(message)+1);
                }

            }
            close(fds[i][WRITE_END]);
            exit(0);
        }
    }
    char stampBuffer[10];//buffers for concatenation
    char readBuffer[1024];
    char stampAndRead[1024];
    for (;;)  {//Parent reads inputs to file
        inputfds = inputs;

        result = select(FD_SETSIZE, &inputfds,
            NULL, NULL, NULL);
        switch(result) {
            case -1: {
                perror("select");
                exit(1);
            }
            default: {
                for(i = 0; i < 5; i++){
                    if (FD_ISSET(fds[i][READ_END], &inputfds)) {
                        ioctl(fds[i][READ_END],FIONREAD,&nread);

                        nread = read(fds[i][READ_END],readBuffer,nread);
                        readBuffer[nread] = 0;
                        timeStamp(startTime, stampBuffer);
                        sprintf(stampAndRead, "%s || %s", stampBuffer, readBuffer);
                        fprintf(fp, "%s\n", stampAndRead);
                    }
                }
                break;
            }
        }
    }
}

