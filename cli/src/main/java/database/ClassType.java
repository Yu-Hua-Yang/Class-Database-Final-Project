package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Map;

public class ClassType implements SQLData {

    private String courseType;
    private String typeTitle;
    private final String typeName = "CLASS_TYPE_TYPE";

    /**
     * Constructor to initialize the Object
     * @param courseType
     * @param typeTitle
     */
    public ClassType(String courseType, String typeTitle) {
        this.courseType = courseType;
        this.typeTitle = typeTitle;
    }

    public ClassType() {
    }

    /**
     * addClassType is to add a specific parameter into the table by 
     * calling procedures from sql and inputting a ClassType object
     * @param conn
     */
    public void addClassType(Connection conn){
        try {
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put(this.typeName, Class.forName("database.ClassType"));
            
            CallableStatement state = conn.prepareCall("{call class_data.add_type(?)}");
            state.setObject(1, this);
            state.execute();
        }
        catch (SQLException | ClassNotFoundException e) {
        }
    }

      /**
     * gets the value courseType
     * @return the courseType
     */
    public String getCourseType() {
        return this.courseType;
    }

      /**
     * gets the value typeTitle
     * @return the typeTitle
     */
    public String getTypeTitle() {
        return this.typeTitle;
    }

      /**
     * gets the value typeName
     * @return the typeName
     */
    public String getTypeName() {
        return this.typeName;
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return typeName;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
       this.courseType = stream.readString();
       this.typeTitle = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(this.courseType);
        stream.writeString(this.typeTitle);
    }
    
}
//done