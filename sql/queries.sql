
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

INSERT INTO cd.facilities (
    facid, name, membercost, guestcost,
    initialoutlay, monthlymaintenance
)
values
    (9, 'Spa', 20, 30, 100000, 800);

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

                       
UPDATE
    cd.facilities
SET
    initialoutlay = 8000
WHERE
        name = 'Tennis Court 2';

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
                       
DELETE FROM
    cd.bookings;

DELETE FROM
    cd.members
where
        memid = 37;
    
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

SELECT
    memid,
    surname,
    firstname,
    joindate
FROM
    cd.members
WHERE
    joindate >= '2012-09-01';

SELECT
    surname
FROM
    cd.members
UNION
SELECT
    name
FROM
    cd.facilities;

SELECT
    bk.starttime
FROM
    cd.bookings bk
        INNER JOIN cd.members mb ON mb.memid = bk.memid
WHERE
    mb.firstname = 'David'
  AND mb.surname = 'Farrell';

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

SELECT
    facid,
    SUM(slots) AS "Total Slots"
FROM
    cd.bookings
GROUP BY
    facid
ORDER BY
    facid;

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

SELECT
    count(DISTINCT memid)
FROM
    cd.bookings;

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

SELECT
            surname || ', ' || firstname AS name
FROM
    cd.members;
                                                    
SELECT
    memid,
    telephone
FROM
    cd.members
WHERE
    telephone similar to '%[()]%';

SELECT
    substr (mems.surname, 1, 1) AS letter,
    count(*) AS count
FROM
    cd.members mems
GROUP BY
    letter
ORDER BY
    letter;
