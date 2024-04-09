USE sql6691212;


CREATE TABLE category_jobs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    category_name VARCHAR(255),
    category_image VARCHAR(255),
    created_on DATETIME,
    updated_on DATETIME,
    PRIMARY KEY (id)
) ENGINE=InnoDB;



CREATE TABLE jobs (
    id BIGINT NOT NULL AUTO_INCREMENT,
    name_job VARCHAR(255),
    image_job VARCHAR(255),
    price DOUBLE,
    category_id BIGINT,
    created_on DATETIME,
    updated_on DATETIME,
    PRIMARY KEY (id),
    CONSTRAINT FK_category_id FOREIGN KEY (category_id) REFERENCES category_jobs(id) ON DELETE CASCADE
) ENGINE=InnoDB;


CREATE TABLE labors (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(255),
    address VARCHAR(255),
    birthday datetime,
    number_experience int,
    status int,
    address_work_official varchar(255),
    free_time_from DATETIME,
	free_time_to DATETIME,
    created_on DATETIME,
    updated_on DATETIME

) ENGINE=InnoDB;

INSERT INTO jobs(id, name_job, image_job, price, category_id)
VALUES(1, 'phu ho', 'sdad', 250000, 1);

INSERT INTO labors (full_name, address, birthday, number_experience, status, address_work_official, free_time_from, free_time_to, created_on, updated_on) 
VALUES 
('John Doe', '123 Main St, Anytown, USA', '1990-05-15 00:00:00', 5, 1, '456 Work Blvd, Anytown, USA', '2024-03-15 08:00:00', '2024-03-15 17:00:00', NOW(), NOW()),
('Jane Smith', '456 Elm St, Othertown, USA', '1985-10-20 00:00:00', 8, 1, '789 Office Ave, Othertown, USA', '2024-03-15 09:00:00', '2024-03-15 18:00:00', NOW(), NOW()),
('Alice Johnson', '789 Oak St, Anycity, USA', '1988-03-10 00:00:00', 3, 0, '1011 Business Rd, Anycity, USA', '2024-03-15 10:00:00', '2024-03-15 19:00:00', NOW(), NOW());




select  category_jobs.id, count(*) as socongviec 
from category_jobs, jobs
where category_jobs.id = jobs.category_id
group by  category_jobs.id;

SELECT count(*) FROM jobs 
WHERE jobs.category_id = 1;

SELECT count(*) FROM jobs 
WHERE jobs.category_id = 2;


SELECT sum(jobs.price) FROM bookings, job_details, jobs 
WHERE job_details.id = bookings.job_detail_id AND
jobs.id = job_details.job_id AND
YEAR(checkout) = 2024 AND
MONTH(checkout) = 4 AND 
DAY(checkout) = 6 AND
accept = 1;
SELECT * FROM bookings, job_details, jobs 
WHERE job_details.id = bookings.job_detail_id AND
jobs.id = job_details.job_id AND
YEAR(checkin) = 2024 AND
MONTH(checkin) = 4 AND 
DAY(checkin) = 1 AND
accept = 0;

SELECT * FROM bookings
WHERE checkout >= '2024-04-07';

SELECT * FROM bookings
WHERE '2024-03-28' <= checkout AND 
'2024-03-30' >= checkin
AND status = 1;

SELECT * FROM bookings
WHERE '2024-03-28' <= checkout AND 
'2024-03-30' >= checkin
AND status = 0;

select * from job_details where job_details.job_id = 31;

select * from labors, job_details
where labors.id = job_details.labor_id;

select * from bookings, job_details, labors
where job_details.id = bookings.job_detail_id
and labors.id = job_details.labor_id;

select * from bookings, customers, users
where users.id = customers.user_id and
customers.id = bookings.customer_id;

select count(*) from bookings, customers, users
where users.id = customers.user_id and
customers.id = bookings.customer_id and 
users.id = 5;

select count(*) from bookings where customer_id = 4;

select * from users ,labors
where users.id = labors.user_id;

select DISTINCT labors.full_name, address, 
phone_number, number_experience, status, 
address_work_official, name_job, price, city_name
from job_details, jobs, labors, cities, category_jobs
where jobs.id = job_details.job_id and
labors.id = job_details.labor_id and
category_jobs.id = jobs.category_id and
cities.id = labors.city_id and
name_job = 'phu ho' and price = 0 
and cities.id = 1;

select labors.full_name, address, 
phone_number, number_experience, status, 
address_work_official, name_job, price, city_name
from job_details, jobs, labors, cities, category_jobs
where jobs.id = job_details.job_id and
labors.id = job_details.labor_id and
category_jobs.id = jobs.category_id and
cities.id = labors.city_id and
name_job like '%P%';

select * from jobs, job_details 
where jobs.id = job_details.job_id order by price asc;

select DISTINCT price from jobs;

ALTER TABLE jobs MODIFY description longtext;

UPDATE bookings SET accept = 1;

select * from bookings where '2024-04-01' >= created_on AND id = 79;

select * from bookings where checkin <= '2024-03-10';

select COUNT(*) from posts, users 
where users.id = posts.user_id and
email = '';

select count(*) from posts;

SELECT * FROM spring_session;
select * from category_jobs;
select * from jobs;
select * from labors;
select * from customers;
select * from job_details;
select * from users;
select * from roles;
select * from users_roles;
select * from bookings;

ALTER TABLE posts MODIFY COLUMN description text;
ALTER TABLE posts MODIFY COLUMN requirement text;
ALTER TABLE applies MODIFY COLUMN about text;
ALTER TABLE applies MODIFY COLUMN image_apply text;

alter table category_jobs drop column address;
alter table category_jobs drop column address_work_official;
alter table category_jobs drop column full_name;
alter table category_jobs drop column status;
alter table category_jobs drop column birthday;
alter table category_jobs drop column number_experience;
alter table category_jobs drop column phone_number;
alter table category_jobs drop column free_time_from;
alter table category_jobs drop column free_time_to;





