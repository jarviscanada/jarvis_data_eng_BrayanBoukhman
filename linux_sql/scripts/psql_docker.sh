#! /bin/sh

# Capture CLI arguments
cmd=$1
db_username=$2
db_password=$3

# Start docker
sudo systemctl status docker || sudo systemctl start docker

# Check container status
container_status=$(docker container inspect -f '{{.State.Status}}' jrvs-psql)


# User switch case to handle create|stop|start options
case $cmd in
  create)

  # Check if the container is already created
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
	docker run --name jrvs-psql -u="$db_username" -e POSTGRES_PASSWORD="$db_password" -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
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