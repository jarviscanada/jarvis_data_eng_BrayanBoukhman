#! /bin/sh

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

# Start docker
# || operator is used as an else statement when the first command fails.
sudo systemctl status docker || sudo systemctl start docker

# Check container status
# -f flag is used to specify a specific go template (https://docs.docker.com/engine/reference/commandline/inspect/)
#'{{.State.Status}}' represents the value we are trying to find check the link for formatting information
#this one does not seem to work for some reason
container_status=$(docker container inspect -f '{{.State.Status}}' jrvs-psql)
#Alternative solution, we can use grep to get line with status from inspect command then find
#the actual value which is in the 4th position when the line is seperated by "
#container_status=$(docker container inspect jrvs-psqll | grep Status | awk -F'"' '{print $4}')


# User switch case to handle create|stop|start options
case $cmd in
  create)

  # Check if the container is already created
  #(https://www.diskinternals.com/linux-reader/bash-if-string-not-empty/#:~:text=%2Dn%20operator,it%20won't%20return%20true.)
  if [ -n "$container_status" ]; then
		echo 'Container already exists'
		exit 1
	fi

  # Check # of CLI arguments
  # checks if number of arguments it 3
  if [ $# -ne 3 ]; then
    echo 'Create requires username and password'
    exit 1
  fi

  # Create container
	docker volume create pgdate
  # Start the container use username and password provided use -u and -e tags to add them then run
  # (https://docs.docker.com/engine/reference/run/#:~:text=Name%20(%2D%2Dname),-The%20operator%20can&text=If%20you%20do%20not%20assign,container%20within%20a%20Docker%20network.)
	docker run --name jrvs-psql -u="$db_username" -e POSTGRES_PASSWORD="$db_password" -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
  #The exit $? command is commonly used in Unix-like shell scripts to exit a script with the exit status code of the last executed command or operation.
	exit $?
	;;

  start|stop)
  # Check instance status; exit 1 if container has not been created
  if [ -z "$container_status" ]; then
  		echo 'Container does not exist'
  		exit 1
  elif [ "$cmd" = "start" ] && [ "$container_status" = "running" ]; then
        echo 'Container already running'
        exit 1
  elif [ "$cmd" = "stop" ] && [ "$container_status" = "exited" ]; then
        echo 'Container already stopped'
        exit 1
  fi

  # Start or stop the container
	docker container "$cmd" jrvs-psql
	exit $?
	;;

  *)
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1
	;;
esac