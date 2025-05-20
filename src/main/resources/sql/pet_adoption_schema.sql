CREATE TYPE gender_enum AS ENUM ('Male', 'Female');
CREATE TYPE size_enum AS ENUM ('Small', 'Medium', 'Large');
CREATE TYPE pet_status_enum AS ENUM ('Available', 'Pending', 'Adopted');
CREATE TYPE adoption_status_enum AS ENUM ('Pending', 'Approved', 'Rejected');


CREATE TABLE pets(
                     pet_id SERIAL PRIMARY KEY,
                     species VARCHAR(30) NOT NULL,
                     age INT NOT NULL CHECK (age > 0),
                     gender gender_enum NOT NULL,
                     size size_enum NOT NULL,
                     status pet_status_enum NOT NULL DEFAULT 'Available'
);

CREATE TABLE adopters (
                          adopter_id SERIAL PRIMARY KEY,
                          first_name VARCHAR(50) NOT NULL,
                          last_name VARCHAR(50) NOT NULL,
                          email VARCHAR(100) NOT NULL UNIQUE,
                          phone_number VARCHAR(20) NOT NULL,
                          location VARCHAR(100) NOT NULL
);

CREATE TABLE staff (
                       staff_id SERIAL PRIMARY KEY,
                       first_name VARCHAR(50) NOT NULL,
                       last_name VARCHAR(50) NOT NULL,
                       email VARCHAR(100) NOT NULL UNIQUE,
                       phone_number VARCHAR(20) NOT NULL,
                       position VARCHAR(50) NOT NULL
);

CREATE TABLE adoptions (
                           adoption_id SERIAL PRIMARY KEY,
                           pet_id INT NOT NULL,
                           adopter_id INT NOT NULL,
                           adoption_date DATE NOT NULL,
                           adoption_fee NUMERIC(10,2) NOT NULL CHECK (adoption_fee > 0),
                           status adoption_status_enum NOT NULL DEFAULT 'Pending',
                           staff_id INT NOT NULL,
                           FOREIGN KEY (pet_id) REFERENCES pets(pet_id),
                           FOREIGN KEY (adopter_id) REFERENCES adopters(adopter_id),
                           FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
);

ALTER TABLE staff
ADD COLUMN password VARCHAR(100) NOT NULL;