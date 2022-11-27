@droptables.sql
--CREATING TABLES

--CREATING CLASSTYPE TABLE
CREATE TABLE ClassType (
    course_type VARCHAR2(4) PRIMARY KEY,
    type_title VARCHAR2(30) 
);

--CREATING TERM TABLE
CREATE TABLE Term (
    term_id NUMBER(1,0) PRIMARY KEY,
    term_title VARCHAR2(6)
);                                    

--CREATING AUDITLOG TABLE
CREATE TABLE AuditLog (
    auditor VARCHAR2(30),
    audit_type VARCHAR2(30),
    audit_date DATE
    audit_table varchar2(30)
);

--CREATING COURSE TABLE
CREATE TABLE Courses (
    course_id VARCHAR2(10) PRIMARY KEY,
    course_description VARCHAR2(500),
    course_title VARCHAR2(30),
    course_hours NUMBER(3,0),
    ponderation VARCHAR2(5),
    term_id NUMBER(1,0) REFERENCES Term(term_id),
    credits NUMBER(2,1),
    course_type VARCHAR(4) REFERENCES CourseType(course_type)
)

--INSERTING SAMPLE DATA
INSERT INTO classtype(course_type, type_title)
VALUES('CONC', 'CONCENTRATION');
INSERT INTO term(term_id, term_title)
VALUES(1, 'Fall');
INSERT INTO courses(course_id, course_title, term_id, course_type, course_description, course_hours, ponderation, credits) 
VALUES('420-110-DW', 'Programming I', 1, 'CONC', 
'The course will introduce the student to the basic building blocks (sequential,
selection and repetitive control structures) and modules (methods and classes)
used to write a program. The student will use the Java programming language to
implement the algorithms studied. The array data structure is introduced, and
student will learn how to program with objects.', 90, '3-3-3', 2.0);

@packages.sql
@triggers.sql
@views.sql
--DONE
