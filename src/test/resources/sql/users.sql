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
  '$2a$10$CwRev.QE8pBqST/NWE7LNuRWuSFbaWaa3JRHyy1sz/uvuOf3nsMpG',
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
  '$2a$10$x6Ly5NFeuA9R3A/JTpS13.BKSo.hSfh.EgNLKFR582Klurykge5vW',
  'Ricardo',
  'cardin',
  'Swissborg',
  'RECRUITER'
);