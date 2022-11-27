package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.Map;

public class Term implements SQLData {

    private int termID;
    private String termTitle;
    private final String typeName = "TERM_TYPE";
    
    /**
     * Constructor to initialize the Object
     * @param termID 
     * @param termTitle
     */
    public Term(int termID, String termTitle) {
        this.termID = termID;
        this.termTitle = termTitle;
    }

    
    public Term() {
    }

    /**
     * addTerm is to add a specific parameter into the table by 
     * calling procedures from sql and inputting a term object
     * @param conn
     */
    public void addTerm(Connection conn){
        try {
            Map map = conn.getTypeMap();
            conn.setTypeMap(map);
            map.put(this.typeName, Class.forName("database.Term"));
            
            CallableStatement state = conn.prepareCall("{call class_data.add_term(?)}");
            state.setObject(1, this);
            state.execute();
        }
        catch (SQLException | ClassNotFoundException e) {
        }
    }
    
    /**
     * gets the value termID
     * @return the termID
     */
    public int getTermID() {
        return this.termID;
    }

    /**
     * gets the value termID
     * @return the termTitle
     */
    public String getTermTitle() {
        return this.termTitle;
    }

    /**
     * gets the value termID
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
        this.termID = stream.readInt();
        this.termTitle = stream.readString();
    }

    @Override
    public void writeSQL(SQLOutput stream) throws SQLException {
        stream.writeInt(this.termID);
        stream.writeString(this.termTitle);
    }
    
}
//done