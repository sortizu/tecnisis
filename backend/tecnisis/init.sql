-- Create users table
CREATE TABLE users (
                       id_user SERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password VARCHAR(255) NOT NULL
);

-- Create persons table, which references users and stores common data
CREATE TABLE persons (
                         id_person SERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         id_number VARCHAR(20) NOT NULL UNIQUE,
                         address VARCHAR(255),
                         gender CHAR(1) CHECK (gender IN ('M', 'F')),
                         phone VARCHAR(20),
                         user_role VARCHAR(50) NOT NULL,
                         id_user INT UNIQUE,  -- Relationship with the users table
                         CONSTRAINT fk_person_user_id FOREIGN KEY (id_user) REFERENCES users(id_user)
);

-- Create artists table
CREATE TABLE artists (
                         id_artist SERIAL PRIMARY KEY,
                         id_person INT UNIQUE NOT NULL,
                         CONSTRAINT fk_artist_person_id FOREIGN KEY (id_person) REFERENCES persons(id_person)
);

-- Create managers table
CREATE TABLE managers (
                          id_manager SERIAL PRIMARY KEY,
                          id_person INT UNIQUE NOT NULL,
                          CONSTRAINT fk_manager_person_id FOREIGN KEY (id_person) REFERENCES persons(id_person)
);

-- Create specialists table
CREATE TABLE specialists (
                             id_specialist SERIAL PRIMARY KEY,
                             id_person INT UNIQUE NOT NULL,
                             availability BOOLEAN,
                             CONSTRAINT fk_specialist_person_id FOREIGN KEY (id_person) REFERENCES persons(id_person)
);

-- Create techniques table
CREATE TABLE techniques (
                            id_technique SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            description TEXT
);

-- Create documents table (moved here to ensure foreign key dependencies are met)
CREATE TABLE documents (
                           id_document SERIAL PRIMARY KEY,
                           document_path VARCHAR(255) NOT NULL
);

-- Create artworks table
CREATE TABLE artworks (
                          id_artwork SERIAL PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          creation_date DATE NOT NULL,
                          image_address VARCHAR(255) NOT NULL,
                          height DECIMAL(10, 2),
                          width DECIMAL(10, 2),
                          id_technique INT NOT NULL,
                          id_artist INT NOT NULL,
                          CONSTRAINT fk_artwork_technique_id FOREIGN KEY (id_technique) REFERENCES techniques(id_technique),
                          CONSTRAINT fk_artwork_artist_id FOREIGN KEY (id_artist) REFERENCES artists(id_artist)
);

-- Create requests table
CREATE TABLE requests (
                          id_request SERIAL PRIMARY KEY,
                          request_date DATE NOT NULL,
                          status VARCHAR(50) CHECK (status IN ('Pending', 'Approved', 'Rejected')) NOT NULL,
                          id_artwork INT NOT NULL,
                          CONSTRAINT fk_request_artwork_id FOREIGN KEY (id_artwork) REFERENCES artworks(id_artwork)
);

-- Create economic evaluations table
CREATE TABLE economic_evaluations (
                                      id_evaluation SERIAL PRIMARY KEY,
                                      evaluation_date DATE NOT NULL,
                                      sale_price DECIMAL(10, 2) NOT NULL,
                                      gallery_percentage DECIMAL(5, 2) NOT NULL,
                                      id_specialist INT NOT NULL,
                                      id_request INT NOT NULL,
                                      id_document INT,  -- To link the generated document
                                      CONSTRAINT fk_economic_evaluation_specialist_id FOREIGN KEY (id_specialist) REFERENCES specialists(id_specialist),
                                      CONSTRAINT fk_economic_evaluation_request_id FOREIGN KEY (id_request) REFERENCES requests(id_request),
                                      CONSTRAINT fk_economic_evaluation_document_id FOREIGN KEY (id_document) REFERENCES documents(id_document)
);

-- Create artistic evaluations table
CREATE TABLE artistic_evaluations (
                                      id_evaluation SERIAL PRIMARY KEY,
                                      evaluation_date DATE NOT NULL,
                                      rating DECIMAL(3, 1),
                                      result VARCHAR(1) CHECK (result IN ('A', 'R')),
                                      id_specialist INT NOT NULL,
                                      id_request INT NOT NULL,
                                      id_document INT,  -- To link the generated document
                                      CONSTRAINT fk_artistic_evaluation_specialist_id FOREIGN KEY (id_specialist) REFERENCES specialists(id_specialist),
                                      CONSTRAINT fk_artistic_evaluation_request_id FOREIGN KEY (id_request) REFERENCES requests(id_request),
                                      CONSTRAINT fk_artistic_evaluation_document_id FOREIGN KEY (id_document) REFERENCES documents(id_document)
);
