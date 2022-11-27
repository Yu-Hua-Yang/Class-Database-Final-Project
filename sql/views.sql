--CREATING VIEW TO DISPLAY
CREATE VIEW course_view AS
SELECT course_id, course_title, type_title, course_description, course_hours, ponderation, term_id, credits FROM courses
JOIN ClassType using (course_type);
--DONE