#! /bin/sh
# Check # of args
if [ "$#" -lt 7 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

# Capture CLI arguments
api_key=$1
psql_host=$2
psql_port=$3
psql_database=$4
psql_username=$5
psql_password=$6
symbols=${@7}

for symbol in symbols
do
  #needs jq to process the responce, could not get this part
  responce=$(curl --request GET \
  	--url 'https://alpha-vantage.p.rapidapi.com/query?interval=5min&function=TIME_SERIES_INTRADAY&symbol='$symbol'&datatype=json&output_size=compact' \
  	--header 'X-RapidAPI-Host: alpha-vantage.p.rapidapi.com' \
  	--header 'X-RapidAPI-Key: '$api_key'')

  # PSQL command: Inserts api call
  insert_stmt="INSERT INTO quote_practice(symbol, open, high, low, cpu_mhz, price, volume)
               VALUES('$responce.symbol', '$responce.open', '$responce.high', '$responce.low', '$responce.price', '$responce.volume')"

  #set up env var for pql cmd
  export PGPASSWORD=$psql_password
  #Insert date into a database
  psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"

done
