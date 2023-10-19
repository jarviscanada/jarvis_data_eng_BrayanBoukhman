SELECT g.gender, count(*)
FROM superhero
         join public.gender g on g.id = superhero.gender_id
         join public.alignment a on a.id = superhero.alignment_id
         join public.colour hair on superhero.hair_colour_id = hair.id
         join public.colour eye on superhero.eye_colour_id = eye.id
where a.alignment = 'good' and (hair = 'green' or (eye = 'white' and NOT hair = 'black'))
group by g.gender;


SELECT count(s), p.publisher_name,
       (SELECT  count(*) from superhero where publisher_id IS NOT NULL)/count(s)
from publisher p
         join public.superhero s on p.id = s.publisher_id
group by p.id
HAVING count(s)/(SELECT count(*) from superhero where publisher_id IS NOT NULL) > 0.1;