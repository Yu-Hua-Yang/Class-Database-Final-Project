--CREATING TYPES FOR SQL
CREATE TYPE courses_type AS OBJECT (
    course_id VARCHAR2(10),
    course_description VARCHAR2(500),
    course_title VARCHAR2(30),
    course_hours NUMBER(3,0),
    ponderation VARCHAR2(5),
    term_id NUMBER(1,0),
    credits NUMBER(2,1),
    course_type VARCHAR(4)
);
/
CREATE TYPE class_type_type AS OBJECT (
    course_type VARCHAR2(4),
    type_title VARCHAR2(30) 
);
/
CREATE TYPE term_type AS OBJECT (
    term_id NUMBER(1,0),
    term_title VARCHAR2(6)
);
/

--PACKAGE SPECIFICATION WITH PROCEDURES FOR SQL DATABASE
CREATE OR REPLACE PACKAGE class_data AS

    PROCEDURE add_course(cour IN courses_type);
    PROCEDURE delete_course(courseID VARCHAR2);
    PROCEDURE update_course(cour IN courses_type);

    PROCEDURE add_type(cour_type IN class_type_type);
    PROCEDURE delete_type(courseType VARCHAR2);
    PROCEDURE update_type(cour_type IN class_type_type);

    PROCEDURE add_term(term IN term_type);
    PROCEDURE delete_term(termID NUMBER);
    PROCEDURE update_term(term IN term_type);

    PROCEDURE add_audit(audit_type IN VARCHAR2, audit_table IN varchar2);

END class_data;
/

--PACKAGE BODY WITH PROCEDURES FOR SQL DATABASE
CREATE OR REPLACE PACKAGE BODY class_data AS

    PROCEDURE add_course(cour IN courses_type) AS
    BEGIN
        INSERT INTO Courses VALUES(
            cour.course_id,
            cour.course_description,
            cour.course_title,
            cour.course_hours,
            cour.ponderation,
            cour.term_id,
            cour.credits,
            cour.course_type
        );
    END;

    PROCEDURE add_type(cour_type IN class_type_type) AS
    BEGIN
        INSERT INTO ClassType VALUES(
            cour_type.course_type,
            cour_type.type_title
        );
    END;

    PROCEDURE add_term(term IN term_type) AS
    BEGIN
        INSERT INTO Term VALUES(
            term.term_id,
            term.term_title
        );
    END;

    PROCEDURE delete_course(courseID IN VARCHAR2) AS
    BEGIN
        DELETE FROM Courses
        WHERE course_id = courseID;
    END;

    PROCEDURE delete_type(courseType IN VARCHAR2) AS
    BEGIN
        DELETE FROM ClassType
        WHERE course_type = courseType;
    END;
    
    PROCEDURE delete_term(termID IN NUMBER) AS
    BEGIN
        DELETE FROM Term
        WHERE term_id = termID;
    END;

    PROCEDURE update_course(cour IN courses_type) AS
    BEGIN
        UPDATE Courses 
        SET
            course_description = cour.course_description,
            course_title = cour.course_title,
            course_hours = cour.course_hours,
            ponderation = cour.ponderation,
            term_id = cour.term_id,
            credits = cour.credits,
            course_type = cour.course_type
        WHERE course_id = cour.course_id;
    END;

    PROCEDURE update_type(cour_type IN class_type_type) AS
    BEGIN
        UPDATE ClassType
        SET
            type_title = cour_type.type_title
        WHERE course_type = cour_type.course_type;
    END;

    PROCEDURE update_term(term IN term_type) AS
    BEGIN
        UPDATE Term 
        SET
            term_title = term.term_title
        WHERE term_id = term.term_id;
    END;

    PROCEDURE add_audit(audit_type IN VARCHAR2, audit_table IN varchar2) AS
    BEGIN
        INSERT INTO AuditLog VALUES(
        USER(),
        audit_type,
        SYSDATE(),
        audit_table
        );
    END;

END class_data;
/
--DONE