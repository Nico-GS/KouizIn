DROP TABLE IF EXISTS answers_user;
DROP TABLE IF EXISTS answers_question;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS quiz;
DROP TABLE IF EXISTS users;

CREATE SEQUENCE IF NOT EXISTS user_id_seq;
CREATE SEQUENCE IF NOT EXISTS quiz_id_seq;
CREATE SEQUENCE IF NOT EXISTS question_id_seq;
CREATE SEQUENCE IF NOT EXISTS answers_question_id_seq;
CREATE SEQUENCE IF NOT EXISTS answers_user_id_seq;

CREATE TABLE "users"
(
    "id"           BIGSERIAL    NOT NULL,
    "company"      VARCHAR(255) NULL DEFAULT NULL,
    "email"        VARCHAR(255) NOT NULL,
    "first_name"   VARCHAR(255) NULL DEFAULT NULL,
    "last_name"    VARCHAR(255) NULL DEFAULT NULL,
    "password"     VARCHAR(255) NOT NULL,
    "phone_number" INTEGER      NOT NULL,
    "role"         VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "users_role_check" CHECK ((((role)::text = ANY ((ARRAY['DEFAULT':: character varying, 'ADMIN':: character varying, 'STAGIAIRE':: character varying])::text[])
) )));

CREATE UNIQUE INDEX "uk_6dotkott2kjsp8vw4d0m25fb7" ON users("email");

CREATE TABLE "quiz"
(
    "id"          BIGSERIAL    NOT NULL,
    "description" VARCHAR(255) NOT NULL,
    "is_active"   BOOLEAN      NOT NULL,
    "name"        VARCHAR(255) NOT NULL,
    PRIMARY KEY ("id")
);

CREATE TABLE "question"
(
    "id"          BIGSERIAL    NOT NULL,
    "category"    SMALLINT     NOT NULL,
    "description" VARCHAR(255) NOT NULL,
    "quiz_id"     BIGINT NULL DEFAULT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "question_category_check" CHECK ((((category >= 0) AND (category <= 4)))),
    CONSTRAINT "fk_question_quiz" FOREIGN KEY ("quiz_id") REFERENCES "quiz" ("id") ON UPDATE NO ACTION ON DELETE SET NULL
);

CREATE TABLE "answers_question"
(
    "id"          BIGSERIAL NOT NULL,
    "description" VARCHAR(255) NULL DEFAULT NULL,
    "is_correct"  BOOLEAN   NOT NULL,
    "question_id" BIGINT    NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fk43267yj1uhnojhu9511cdmrej" FOREIGN KEY ("question_id") REFERENCES "question" ("id") ON UPDATE NO ACTION ON DELETE SET NULL
);

CREATE TABLE "answers_user"
(
    "id"          BIGSERIAL    NOT NULL,
    "is_true"     BOOLEAN      NOT NULL,
    "response"    VARCHAR(255) NOT NULL,
    "user_id"     BIGINT       NOT NULL,
    "question_id" BIGINT       NOT NULL,
    PRIMARY KEY ("id"),
    CONSTRAINT "fksa9xlk911k1pbh347wx9481iu" FOREIGN KEY ("question_id") REFERENCES "question" ("id") ON UPDATE NO ACTION ON DELETE NO ACTION
);

------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------------------------------------
INSERT INTO question (description, category)
VALUES ('Quel est le prénom de Potter ?', 4);
INSERT INTO answers_question (question_id, description, is_correct)
VALUES (1, 'Harry', TRUE);
INSERT INTO answers_question (question_id, description, is_correct)
VALUES (1, 'Hermione', FALSE);
------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------
INSERT INTO question (description, category)
VALUES ('Quel est le nom du meilleur ami de Harry?', 4);
INSERT INTO answers_question (question_id, description, is_correct)
VALUES (2, 'Ron', TRUE);
INSERT INTO answers_question (question_id, description, is_correct)
VALUES (2, 'Draco', FALSE);
------------------------------------------------------------------------------------------------
------------------------------------------------------------------------------------------------
INSERT INTO answers_user (response, user_id, question_id, is_true)
VALUES ('Harry', 1, 1, TRUE); -- User 1 répond correctement
INSERT INTO answers_user (response, user_id, question_id, is_true)
VALUES ('Hermione', 2, 1, FALSE); -- User 2 répond incorrectement
INSERT INTO answers_user (response, user_id, question_id, is_true)
VALUES ('Ron', 1, 2, TRUE); -- User 1 répond correctement
INSERT INTO answers_user (response, user_id, question_id, is_true)
VALUES ('Draco', 2, 2, FALSE); -- User 2 répond incorrectement

-- password : password --
INSERT INTO "users" ("id", "company", "email", "first_name", "last_name", "password", "phone_number", "role")
VALUES (1, '', 'admin@test.com', '', '', '$2a$10$9NtuAimeae1UfWpAyuBoTeCD.sLH3QtNnSgTjUdZxs7mQR5iL6QA.', 0, 'ADMIN');

INSERT INTO "users" ("id", "company", "email", "first_name", "last_name", "password", "phone_number", "role")
VALUES (3, '', 'stagiaire@test.com', '', '', '$2a$10$9NtuAimeae1UfWpAyuBoTeCD.sLH3QtNnSgTjUdZxs7mQR5iL6QA.', 0, 'STAGIAIRE');