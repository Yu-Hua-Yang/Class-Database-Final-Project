package database;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Map;

public class Courses implements SQLData {
    
    private String courseID;
    private String courseDescription;
    private String courseTitle;
    private int courseHours;
    private String ponderation;
    private int termID;
    private double credits;
    private String courseType;
    private final String typeName = "COURSES_TYPE";

    /**
     * Constructor to initialize the Object
     * @param courseID
     * @param courseDescription
     * @param courseTitle
     * @param courseHours
     * @param ponderation
     * @param termID
     * @param credits
     * @param courseType
     */
    public Courses(String courseID, String courseDescription, String courseTitle, int courseHours, String ponderation, int termID, double credits, String courseType) {
        this.courseID = courseID;
        this.courseDescription = courseDescription;
        this.courseTitle = courseTitle;
        this.courseHours = courseHours;
        this.ponderation = ponderation;
        this.termID = termID;
        this.credits = credits;
        this.courseType = courseType;
    }

    public Courses() {
    }

    /**
     * updateCourse updates the table using the courseID provided from the object
     * @param conn
     */
    public void updateCourse(Connection conn){
        try {
            CallableStatement state = conn.prepareCall("{call class_data.update_course(?)}");
            state.setObject(1, this);
            state.execute();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * addCourse adds the course taking in a object via a course object
     * @param conn
     */
    public void addCourse(Connection conn){
        try {
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put(this.typeName, Class.forName("database.Courses"));
            
            CallableStatement state = conn.prepareCall("{call class_data.add_course(?)}");
            state.setObject(1, this);
            state.execute();
        }
        catch (SQLException | ClassNotFoundException e) {
        }
    }

    /**
     * getCourseById takes a ID and checks for all the value was just a test method
     * @param conn
     * @param ID
     */
    public void getCourseById(Connection conn, String ID){
        try{
            PreparedStatement state = conn.prepareStatement("SELECT * FROM COURSES WHERE course_id = ?");
            state.setString(1, ID);
            ResultSet res = state.executeQuery();
            while(res.next()){
                this.courseDescription = res.getString(2);
                this.courseTitle = res.getString(3);
                this.courseHours = res.getInt(4);
                this.ponderation = res.getString(5);
                this.termID = res.getInt(6);
                this.credits = res.getDouble(7);
                this.courseType = res.getString(8);
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }

     /**
     * gets the value courseID
     * @return the courseID
     */
    public String getCourseID() {
        return this.courseID;
    }
    
     /**
     * gets the value courseDescription
     * @return the courseDescription
     */
    public String getCourseDescription() {
        return this.courseDescription;
    }

     /**
     * gets the value courseTitle
     * @return the courseTitle
     */
    public String getCourseTitle() {
        return this.courseTitle;
    }

     /**
     * gets the value courseHours
     * @return the courseHours
     */
    public int getCourseHours() {
        return this.courseHours;
    }

     /**
     * gets the value ponderation
     * @return the ponderation
     */
    public String getPonderation() {
        return this.ponderation;
    }

     /**
     * gets the value termID
     * @return the termID
     */
    public int getTermID() {
        return this.termID;
    }
    
     /**
     * gets the value credits
     * @return the credits
     */
    public double getCredits() {
        return this.credits;
    }

     /**
     * gets the value courseType
     * @return the courseType
     */
    public String getCourseType() {
        return this.courseType;
    }

    @Override
    public String getSQLTypeName() throws SQLException {
        return typeName;
    }

    @Override
    public void readSQL(SQLInput stream, String typeName) throws SQLException {
        this.courseID = stream.readString();
        this.courseDescription = stream.readString();
        this.courseTitle = stream.readString();
        this.courseHours = stream.readInt();
        this.ponderation = stream.readString();
        this.termID = stream.readInt();
        this.credits = stream.readDouble();
        this.courseType = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeString(this.courseID);
        stream.writeString(this.courseDescription);
        stream.writeString(this.courseTitle);
        stream.writeInt(this.courseHours);
        stream.writeString(this.ponderation);
        stream.writeInt(this.termID);
        stream.writeDouble(this.credits);
        stream.writeString(this.courseType);
    }
}
//done