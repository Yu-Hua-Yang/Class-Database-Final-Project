--CREATING TRIGGERS TO UPDATE AUDIT LOGS
CREATE TRIGGER trigger_insert_course AFTER INSERT ON Courses
BEGIN
class_data.add_audit('INSERT', 'Courses');
END;
/
CREATE TRIGGER trigger_insert_class_type AFTER INSERT ON ClassType
BEGIN
class_data.add_audit('INSERT', 'ClassType');
END;
/
CREATE TRIGGER trigger_insert_term AFTER INSERT ON Term
BEGIN
class_data.add_audit('INSERT', 'Term');
END; 
/
CREATE TRIGGER trigger_update_course AFTER UPDATE ON Courses
BEGIN
class_data.add_audit('UPDATE', 'Courses');
END;
/
CREATE TRIGGER trigger_update_class_type AFTER UPDATE ON ClassType
BEGIN
class_data.add_audit('UPDATE', 'ClassType');
END;
/
CREATE TRIGGER trigger_update_term AFTER UPDATE ON Term
BEGIN
class_data.add_audit('UPDATE', 'Term');
END;
/
CREATE TRIGGER trigger_delete_course AFTER DELETE ON Courses
BEGIN
class_data.add_audit('DELETE', 'Courses');
END;
/
CREATE TRIGGER trigger_delete_class_type AFTER DELETE ON ClassType
BEGIN
class_data.add_audit('DELETE', 'ClassType');
END;
/
CREATE TRIGGER trigger_delete_term AFTER DELETE ON Term
BEGIN
class_data.add_audit('DELETE', 'Term');
END;
/
--DONE