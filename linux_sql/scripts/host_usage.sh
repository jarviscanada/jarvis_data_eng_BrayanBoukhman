#! /bin/sh

# Check # of args
if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

#set input arguments
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

# Save machine statistics in MB and current machine hostname to variables
vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

# Retrieve hardware specification variables
memory_free=$(echo "$vmstat_mb" | awk '{print $4}'| tail -n1 | xargs)
cpu_idle=$(echo "$vmstat_mb" | tail -1 | awk '{print $15}' | xargs)
cpu_kernel=$(echo "$vmstat_mb" | tail -1 | awk '{print $14}' | xargs)
disk_io=$(echo "$vmstat_mb" | tail -1 | awk '{print $9}' | xargs)
disk_available=$(df -BM | tail -1 | awk '{print $4}' | sed 's/.$//' | xargs)
timestamp=$(vmstat -t | tail -1 | awk '{print $18, $19}' | xargs)

# PSQL command: Inserts server usage data into host_usage table
insert_stmt="INSERT INTO host_usage (timestamp,host_id,memory_free,cpu_idle,cpu_kernel,disk_io,disk_available)
              VALUES ('$timestamp',(SELECT id FROM host_info WHERE hostname='$hostname'),'$memory_free','$cpu_idle','$cpu_kernel','$disk_io','$disk_available');"

#set up env var for pql cmd
export PGPASSWORD=$psql_password
#Insert date into a database
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?