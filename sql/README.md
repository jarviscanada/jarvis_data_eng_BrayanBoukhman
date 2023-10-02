# Introduction
This SQL project, created by me solely for learning purposes, focuses on developing and demonstrating my SQL proficiency. It involves using various SQL keywords to address specific questions and scenarios, serving as a valuable tool for skill enhancement. The project's objectives include hands-on skill development, real-world applicability, problem-solving mastery, immediate feedback, and portfolio enrichment. It empowers me to advance my SQL skills for potential career opportunities in data analysis or related fields.
# SQL Queries

###### Table Setup (DDL)

```sql
   CREATE TABLE cd.members
   (
       memid integer NOT NULL,
       surname character varying(200) NOT NULL,
       firstname character varying(200) NOT NULL,
       address character varying(300) NOT NULL,
       zipcode integer NOT NULL,
       telephone character varying(20) NOT NULL,
       recommendedby integer,
       joindate timestamp NOT NULL,
       CONSTRAINT members_pk PRIMARY KEY (memid),
       CONSTRAINT fk_members_recommendedby FOREIGN KEY (recommendedby)
           REFERENCES cd.members(memid) ON DELETE SET NULL
   );

    CREATE TABLE cd.facilities
    (
        facid integer NOT NULL,
        name character varying(100) NOT NULL,
        membercost numeric NOT NULL,
        guestcost numeric NOT NULL,
        initialoutlay numeric NOT NULL,
        monthlymaintenance numeric NOT NULL,
        CONSTRAINT facilities_pk PRIMARY KEY (facid)
    );
    
    CREATE TABLE cd.bookings
    (
        bookid integer NOT NULL,
        facid integer NOT NULL,
        memid integer NOT NULL,
        starttime timestamp NOT NULL,
        slots integer NOT NULL,
        CONSTRAINT bookings_pk PRIMARY KEY (bookid),
        CONSTRAINT fk_bookings_facid FOREIGN KEY (facid) REFERENCES cd.facilities(facid),
        CONSTRAINT fk_bookings_memid FOREIGN KEY (memid) REFERENCES cd.members(memid)
    );
```

###### Question 1: Insert some data into a table

```sql
INSERT INTO cd.facilities (
    facid, name, membercost, guestcost,
    initialoutlay, monthlymaintenance
)
values
    (9, 'Spa', 20, 30, 100000, 800);
```

###### Question 2: Insert calculated data into a table

```sql
INSERT INTO cd.facilities (
    facid, name, membercost, guestcost,
    initialoutlay, monthlymaintenance
)
SELECT
        (
            SELECT
                max(facid)
            from
                cd.facilities
        )+ 1,
        'Spa',
        20,
        30,
        100000,
        800;
```

###### Question 3: Update some existing data

```sql
UPDATE
    cd.facilities
SET
    initialoutlay = 8000
WHERE
    name = 'Tennis Court 2';
```

###### Question 4: Update a row based on the contents of another row

```sql
UPDATE
    cd.facilities tennis2
SET
    membercost = tennis1.membercost * 1.1,
    guestcost = tennis1.guestcost * 1.1
FROM
    (
        SELECT
            *
        FROM
            cd.facilities
        WHERE
            name = 'Tennis Court 1'
    ) tennis1
WHERE
    tennis2.name = 'Tennis Court 2';
```

###### Question 5: Delete all bookings

```sql
DELETE FROM
    cd.bookings;
```

###### Question 6: Delete a member from the cd.members table

```sql
DELETE FROM 
  cd.members 
where 
  memid = 37;
```

###### Question 7: Delete a member from the cd.members table

```sql
SELECT
    facid,
    name,
    membercost,
    monthlymaintenance
FROM
    cd.facilities fac
where
    (
        fac.membercost < fac.monthlymaintenance / 50
        )
  AND (fac.membercost > 0);
```

###### Question 8: Basic string searches

```sql
SELECT
    facid,
    name,
    membercost,
    guestcost,
    initialoutlay,
    monthlymaintenance
FROM
    cd.facilities
WHERE
    name LIKE '%Tennis%';
```

###### Question 9: Matching against multiple possible values

```sql
SELECT
    facid,
    name,
    membercost,
    guestcost,
    initialoutlay,
    monthlymaintenance
FROM
    cd.facilities
WHERE
    facid in (1, 5);
```

###### Question 10: Working with dates

```sql
SELECT
    memid,
    surname,
    firstname,
    joindate
FROM
    cd.members
WHERE
    joindate >= '2012-09-01';
```

###### Question 11: Combining results from multiple queries

```sql
SELECT
    surname
FROM
    cd.members
UNION
SELECT
    name
FROM
    cd.facilities;
```

###### Question 12: Retrieve the start times of members' bookings

```sql
SELECT
    bk.starttime
FROM
    cd.bookings bk
        INNER JOIN cd.members mb ON mb.memid = bk.memid
WHERE
    mb.firstname = 'David'
  AND mb.surname = 'Farrell';

```

###### Question 13: Work out the start times of bookings for tennis courts

```sql
SELECT
    b.starttime AS start,
    f.name AS name
FROM
    cd.bookings b
        INNER JOIN cd.facilities f on b.facid = f.facid
where
    b.starttime >= '2012-09-21'
  AND b.starttime < '2012-09-22'
  AND f.name LIKE '%Tennis%'
ORDER BY
    b.starttime;
```
###### Question 14: Produce a list of all members, along with their recommender

```sql
SELECT
    mems.firstname AS memfname,
    mems.surname AS memsname,
    recs.firstname AS recfname,
    recs.surname AS recsname
FROM
    cd.members mems
        LEFT OUTER JOIN cd.members recs ON recs.memid = mems.recommendedby
ORDER BY
    memsname,
    memfname;
```

###### Question 15: Produce a list of all members who have recommended another member

```sql
SELECT
    mems.firstname as memfname,
    mems.surname as memsname,
    recs.firstname as recfname,
    recs.surname as recsname
FROM
    cd.members mems
        LEFT OUTER JOIN cd.members recs on recs.memid = mems.recommendedby
ORDER BY
    memsname,
    memfname;
```

###### Question 16: Produce a list of all members who have recommended another member

```sql
SELECT
    DISTINCT mems.firstname || ' ' || mems.surname as member,
             (
                 SELECT
                     recs.firstname || ' ' || recs.surname as recommender
                 FROM
                     cd.members recs
                 WHERE
                     recs.memid = mems.recommendedby
             )
FROM
    cd.members mems
ORDER BY
    member;
```

###### Question 17: Count the number of recommendations each member makes.

```sql
SELECT
    count(*),
    recommendedby
FROM
    cd.members
WHERE
    recommendedby IS NOT NULL
GROUP BY
    recommendedby
ORDER BY
    recommendedby;
```

###### Question 18: List the total slots booked per facility.

```sql
SELECT
    facid,
    SUM(slots) AS "Total Slots"
FROM
    cd.bookings
GROUP BY
    facid
ORDER BY
    facid;
```

###### Question 19: List the total slots booked per facility in a given month

```sql
SELECT
    facid,
    sum(slots) AS "Total Slots"
FROM
    cd.bookings
WHERE
    starttime >= '2012-09-01'
  AND starttime < '2012-10-01'
GROUP BY
    facid
ORDER BY
    sum(slots);
```

###### Question 20: List the total slots booked per facility per month

```sql
SELECT
    facid,
    extract(
            month
            FROM
            starttime
        ) AS month,
    sum(slots) AS "Total Slots"
FROM
    cd.bookings
WHERE
        extract(
                year
                FROM
                starttime
            ) = 2012
GROUP BY
    facid,
    month
ORDER BY
    facid,
    month;
```

###### Question 20: Find the count of members who have made at least one booking

```sql
SELECT
    count(DISTINCT memid)
FROM
    cd.bookings;
```

###### Question 21: List each member's first booking after September 1st 2012

```sql
SELECT
    m.surname,
    m.firstname,
    m.memid,
    min(b.starttime) AS starttime
FROM
    cd.members m
        INNER JOIN cd.bookings b ON b.memid = m.memid
WHERE
    b.starttime >= '2012-09-01'
GROUP BY
    m.surname,
    m.firstname,
    m.memid
ORDER BY
    m.memid;
```

###### Question 22: Produce a list of member names, with each row containing the total member count

```sql
SELECT
    count(*) over(
    partition BY date_trunc('month', joindate)
    ),
    firstname,
    surname
FROM
    cd.members
ORDER BY
    joindate;
```

###### Question 23: Produce a numbered list of members

```sql
SELECT
    row_number() over(
    ORDER BY
        joindate
    ),
    firstname,
    surname
FROM
    cd.members
ORDER BY
    joindate;
```

###### Question 24: Output the facility id that has the highest number of slots booked, again

```sql
SELECT
    facid,
    total
FROM
    (
    SELECT
        facid,
        total,
        rank() over (
        ORDER BY
        total DESC
    ) rank
    FROM
        (
            SELECT
            facid,
            sum(slots) total
        FROM
            cd.bookings
        GROUP BY
            facid
        ) AS sumslots
    ) AS ranked
WHERE
    rank = 1;
```
###### Question 25: Format the names of members

```sql
SELECT
    surname || ', ' || firstname AS name
FROM
    cd.members;
```

###### Question 26: Find telephone numbers with parentheses

```sql
SELECT
    memid,
    telephone
FROM
    cd.members
WHERE
    telephone similar to '%[()]%';
```

###### Question 27: Count the number of members whose surname starts with each letter of the alphabet

```sql
SELECT
    substr (mems.surname, 1, 1) AS letter,
    count(*) AS count
FROM
    cd.members mems
GROUP BY
    letter
ORDER BY
    letter;
```