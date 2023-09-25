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
SELECT
    host_usage.host_id,
    host_info.hostname,
    round5(host_usage.timestamp) AS timestamp,
    ROUND(AVG(((host_info.total_mem-(host_usage.memory_free*1000))::numeric/host_info.total_mem)*100),2) AS avg_used_mem_percentage
FROM host_usage
         INNER JOIN host_info ON host_usage.host_id = host_info.id
GROUP BY host_usage.host_id, host_info.hostname, round5(host_usage.timestamp)
HAVING COUNT(*) = 5
ORDER BY host_usage.host_id, round5(host_usage.timestamp);


-- Detect host failure
SELECT host_id, round5(timestamp) AS time_stamp_rounded, COUNT(*) AS num_data_points
FROM host_usage
GROUP BY host_id, time_stamp_rounded
HAVING COUNT(*) < 3;