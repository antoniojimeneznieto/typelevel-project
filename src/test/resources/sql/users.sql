CREATE TABLE users(
  email text NOT NULL,
  hashedPassword text NOT NULL,
  firstName text,
  lastName text,
  company text,
  role text
);

ALTER TABLE users 
ADD CONSTRAINT pk_users PRIMARY KEY (email);

INSERT INTO users (
  email,
  hashedPassword,
  firstName,
  lastName,
  company,
  role
) VALUES (
  'antonio@gmail.com',
  'rockthejvm',
  'Antonio',
  'Jimenez',
  'Swissborg',
  'ADMIN'
);

INSERT INTO users (
  email,
  hashedPassword,
  firstName,
  lastName,
  company,
  role
) VALUES (
  'riccardo@gmail.com',
  'riccardorulez',
  'Ricardo',
  'cardin',
  'Swissborg',
  'RECRUITER'
);