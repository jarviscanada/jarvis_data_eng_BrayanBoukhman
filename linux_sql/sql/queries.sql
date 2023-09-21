-- round current ts every 5 mins
SELECT date_trunc('hour', timestamp) + date_part('minute', timestamp):: int / 5 * interval '5 min'
FROM host_usage;

-- round current ts every 5 mins in the form of a function
CREATE FUNCTION round5(ts timestamp) RETURNS timestamp AS
$$
BEGIN
    RETURN date_trunc('hour', ts) + date_part('minute', ts):: int / 5 * interval '5 min';
END;
$$
    LANGUAGE PLPGSQL;

-- Group hosts by hardware info
SELECT cpu_number, id AS host_id, total_mem FROM host_info ORDER BY cpu_number, total_mem DESC;

-- Average memory usage (needs fixing)
SELECT hu.host_id as host_id, hi.hostname as host_name, round5(hu.timestamp) AS timestamp, (AVG(hi.total_mem - hu.memory_free)) AS avg_used_mem_percentage
FROM host_usage hu
INNER JOIN public.host_info hi
on hi.id = hu.host_id
group by  hu.host_id, hi.hostname, round5(hu.timestamp)
order BY host_id, timestamp;


-- Detect host failure
SELECT host_id, round5(timestamp) AS time_stamp_rounded, COUNT(*) AS num_data_points
FROM host_usage
GROUP BY host_id, time_stamp_rounded
HAVING COUNT(*) < 3;