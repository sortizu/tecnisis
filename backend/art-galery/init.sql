
-- Create the database if it doesn't exist
DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_database
            WHERE datname = 'tecnisis'
        ) THEN
            CREATE DATABASE tecnisis;
        END IF;
    END $$;

DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1
            FROM pg_roles
            WHERE rolname = 'tecnisis_user'
        ) THEN
            CREATE ROLE tecnisis_user WITH LOGIN PASSWORD 'tecnisis_password';
        END IF;
    END $$;

-- Asignar permisos al rol para el esquema o la base de datos
GRANT ALL PRIVILEGES ON DATABASE tecnisis TO tecnisis_user;
-- Otorgar permisos al rol para el esquema
GRANT USAGE ON SCHEMA tecnisis TO tecnisis_user;

-- Otorgar permisos para las tablas existentes
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA tecnisis TO tecnisis_user;

-- Otorgar permisos para las secuencias existentes
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA tecnisis TO tecnisis_user;


-- Switch to the created database
\c tecnisis

-- Create users table
CREATE TABLE users (
    id_user BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Create documents table
CREATE TABLE documents (
    id_document BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    document_path TEXT NOT NULL
);

-- Create techniques table
CREATE TABLE techniques (
    id_technique BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    description TEXT
);

-- Create persons table
CREATE TABLE persons (
    id_person BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(255) NOT NULL,
    id_number VARCHAR(20) NOT NULL UNIQUE,
    address VARCHAR(255),
    gender VARCHAR(1) NOT NULL CHECK (gender IN ('M', 'F')),
    phone VARCHAR(9),
    user_role VARCHAR(50) NOT NULL CHECK (user_role IN ('ARTIST', 'ART-EVALUATOR', 'ECONOMIC-EVALUATOR', 'MANAGER')),
    id_user BIGINT UNIQUE,
    CONSTRAINT fk_person_user_id FOREIGN KEY (id_user) REFERENCES users(id_user)
);

-- Create artists table
CREATE TABLE artists (
    id_artist BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_person BIGINT UNIQUE NOT NULL,
    CONSTRAINT fk_artist_person_id FOREIGN KEY (id_person) REFERENCES persons(id_person)
);

-- Create managers table
CREATE TABLE managers (
    id_manager BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_person BIGINT UNIQUE NOT NULL,
    CONSTRAINT fk_manager_person_id FOREIGN KEY (id_person) REFERENCES persons(id_person)
);

-- Create specialists table
CREATE TABLE specialists (
    id_specialist BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    id_person BIGINT UNIQUE NOT NULL,
    availability BOOLEAN DEFAULT true,
    CONSTRAINT fk_specialist_person_id FOREIGN KEY (id_person) REFERENCES persons(id_person)
);

-- Create artworks table
CREATE TABLE artworks (
    id_artwork BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    title VARCHAR(255) NOT NULL,
    creation_date DATE NOT NULL,
    image_address TEXT NOT NULL,
    height DECIMAL(10, 2),
    width DECIMAL(10, 2),
    id_technique BIGINT NOT NULL,
    id_artist BIGINT NOT NULL,
    CONSTRAINT fk_artwork_technique_id FOREIGN KEY (id_technique) REFERENCES techniques(id_technique),
    CONSTRAINT fk_artwork_artist_id FOREIGN KEY (id_artist) REFERENCES artists(id_artist)
);

-- Create requests table
CREATE TABLE requests (
    id_request BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    request_date DATE NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING' CHECK (status IN ('APPROVED', 'PENDING', 'REJECTED')),
    id_artwork BIGINT NOT NULL,
    CONSTRAINT fk_request_artwork_id FOREIGN KEY (id_artwork) REFERENCES artworks(id_artwork)
);

-- Create economic evaluations table
CREATE TABLE economic_evaluations (
    id_evaluation BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    evaluation_date DATE NOT NULL,
    sale_price DECIMAL(10, 2),
    gallery_percentage DECIMAL(5, 2),
    status VARCHAR(50) CHECK (status IN ('APPROVED', 'PENDING', 'REJECTED')) NOT NULL,
    id_specialist BIGINT,
    id_request BIGINT NOT NULL,
    id_document BIGINT,
    CONSTRAINT fk_economic_evaluation_specialist_id FOREIGN KEY (id_specialist) REFERENCES specialists(id_specialist),
    CONSTRAINT fk_economic_evaluation_request_id FOREIGN KEY (id_request) REFERENCES requests(id_request),
    CONSTRAINT fk_economic_evaluation_document_id FOREIGN KEY (id_document) REFERENCES documents(id_document)
);

-- Create artistic evaluations table
CREATE TABLE artistic_evaluations (
   id_evaluation BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
   evaluation_date DATE NOT NULL,
   rating DECIMAL(3, 1),
   status VARCHAR(50) CHECK (status IN ('APPROVED', 'PENDING', 'REJECTED')) NOT NULL,
   result VARCHAR(50) CHECK (result IN ('APPROVED', 'REJECTED')),
   id_specialist BIGINT,
   id_request BIGINT NOT NULL,
   id_document BIGINT,
   CONSTRAINT fk_artistic_evaluation_specialist_id FOREIGN KEY (id_specialist) REFERENCES specialists(id_specialist),
   CONSTRAINT fk_artistic_evaluation_request_id FOREIGN KEY (id_request) REFERENCES requests(id_request),
   CONSTRAINT fk_artistic_evaluation_document_id FOREIGN KEY (id_document) REFERENCES documents(id_document)
);

CREATE TABLE specialist_techniques (
   id_specialist BIGINT NOT NULL,
   id_technique BIGINT NOT NULL,
   PRIMARY KEY (id_specialist, id_technique),
   CONSTRAINT fk_specialist_technique_specialist_id FOREIGN KEY (id_specialist) REFERENCES specialists(id_specialist) ON DELETE CASCADE,
   CONSTRAINT fk_specialist_technique_technique_id FOREIGN KEY (id_technique) REFERENCES techniques(id_technique) ON DELETE CASCADE
);

INSERT INTO techniques (name, description) VALUES
                                               ('Acuarela', 'Pintura con colores diluidos en agua sobre papel.'),
                                               ('Óleo', 'Técnica de pintura con pigmentos mezclados con aceites.'),
                                               ('Escultura', 'Creación de figuras tridimensionales usando materiales como piedra o madera.'),
                                               ('Grabado', 'Técnica de impresión mediante matrices grabadas.'),
                                               ('Collage', 'Creación de obras utilizando recortes y materiales pegados.'),
                                               ('Dibujo a lápiz', 'Arte de trazar líneas con lápices sobre papel.'),
                                               ('Carboncillo', 'Dibujo usando palos de carbón comprimido.'),
                                               ('Técnica mixta', 'Uso de múltiples técnicas en una misma obra.'),
                                               ('Pasteles', 'Uso de pigmentos secos en forma de barra sobre papel.'),
                                               ('Acrílico', 'Pintura con pigmentos sintéticos solubles en agua.'),
                                               ('Talla en madera', 'Esculpir madera para crear formas y diseños.'),
                                               ('Arte digital', 'Creación de obras utilizando herramientas digitales.'),
                                               ('Fotografía artística', 'Uso de la fotografía como medio de expresión artística.'),
                                               ('Serigrafía', 'Impresión mediante el uso de mallas y tinta.'),
                                               ('Vidrieras', 'Creación de imágenes usando vidrio coloreado ensamblado.'),
                                               ('Mosaico', 'Arte de crear imágenes ensamblando pequeñas piezas de vidrio, cerámica o piedra.'),
                                               ('Técnica de temple', 'Uso de pigmentos mezclados con un medio como huevo o goma.'),
                                               ('Arte textil', 'Creación de obras usando materiales textiles como hilos o telas.'),
                                               ('Graffiti', 'Expresión artística realizada en muros usando aerosoles.'),
                                               ('Arte corporal', 'Uso del cuerpo humano como lienzo para expresarse.'),
                                               ('Frescos', 'Pintura sobre superficies húmedas con pigmentos.'),
                                               ('Cerámica', 'Creación de objetos artísticos con arcilla cocida.'),
                                               ('Origami', 'Arte de plegar papel para crear formas y figuras.'),
                                               ('Caligrafía artística', 'Diseño y escritura decorativa de letras.'),
                                               ('Papel maché', 'Creación de objetos moldeados con papel y adhesivo.');