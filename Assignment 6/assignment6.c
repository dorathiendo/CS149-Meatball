#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <signal.h>
#include <sys/time.h>

#define BUFFER_SIZE 32
#define READ_END	0
#define WRITE_END	1
#define TIMEOUT 	10 //change to 30

void SIGALRM_handler(int signo)
{
    assert(signo == SIGALRM);
    printf("\nTime's up!\n");
    exit(0);
}

int main(void)
{
	char buf[BUFFER_SIZE];

	//taken from timer.c
	struct itimerval tval;
    timerclear(& tval.it_interval);
    timerclear(& tval.it_value);
    tval.it_value.tv_sec = TIMEOUT;
    
    signal(SIGALRM, SIGALRM_handler);
    setitimer(ITIMER_REAL, & tval, NULL); //set timer

    pid_t pid; //child proccess id
	int fd[2]; //file descriptors

    //Create the pipe.
    if (pipe(fd) == -1) {
        fprintf(stderr,"pipe() failed");
        return 1;
    }

    // Fifth child repeatedly prompts at terminal (stdout) and reads one line of input (stdin)
	for (;;) {

		/* WITHOUT FORK, comment this out if you want to try with fork down below */
		printf("Please enter text:\n");
		char *output = fgets(buf, BUFFER_SIZE, stdin);

		write(fd[WRITE_END], output, strlen(output)+1);
		printf("\nWriting to the pipe: %s", output);

  		read(fd[READ_END], buf, BUFFER_SIZE);
		printf("Read from the pipe: %s\n", buf);



		/* -------------- */



		/* Attempt with fork, doesn't work at all.. didn't do select() yet...
		   DON'T RUN THIS UNLESS YOU FIXED IT!
		   Right now, after running it in Debian.. it will eventually say
		   		-bash: fork: retry: Resource temporarily unavailable */

		// printf("Please enter text:\n");
		// char *output = fgets(buf, BUFFER_SIZE, stdin);

		// pid = fork();
		// if (pid > 0) { //parent reads from pipe
		// 	close(fd[WRITE_END]);

  		// 	read(fd[READ_END], buf, BUFFER_SIZE);
		// 	printf("Read from the pipe: %s\n", buf);

		// 	close(fd[READ_END]);
		// } else if (pid == 0) { //child writes to pipe
		// 	close(fd[READ_END]);

		// 	write(fd[WRITE_END], output, strlen(output)+1);
		// 	printf("Writing to the pipe: %s\n", output);

		// 	close(fd[WRITE_END]);
		// }
	}
}