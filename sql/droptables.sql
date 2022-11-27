--DROPPING PACKAGE
DROP PACKAGE class_data;

--DROPPING TYPE
DROP TYPE courses_type;
DROP TYPE class_type_type;
DROP TYPE term_type;

--DROPPING TRIGGER
DROP TRIGGER trigger_insert_course;
DROP TRIGGER trigger_insert_class_type;
DROP TRIGGER trigger_insert_term;
DROP TRIGGER trigger_update_course;
DROP TRIGGER trigger_update_class_type;
DROP TRIGGER trigger_update_term;
DROP TRIGGER trigger_delete_course;
DROP TRIGGER trigger_delete_class_type;
DROP TRIGGER trigger_delete_term;

--DROPPING VIEW
DROP VIEW course_view;

--DROPPING TABLE
DROP TABLE ClassType CASCADE CONSTRAINTS;
DROP TABLE Courses CASCADE CONSTRAINTS;
DROP TABLE AuditLog CASCADE CONSTRAINTS;
DROP TABLE Term CASCADE CONSTRAINTS;
--DONE