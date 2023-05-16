import java.io.File; 
import java.io.FileWriter; 
import java.io.IOException; 
 
class Database { 
    private static final String DATA_DIR = "data"; 
 
    public static void main(String[] args) { 
        // Example usage 
        createTable("CREATE TABLE mytable (col1 INTEGER, col2 STRING)"); 
        insertIntoTable("INSERT INTO mytable VALUES (1, 'Harsh')"); 
        insertIntoTable("INSERT INTO mytable VALUES (2, 'Atul')"); 
    } 
 
    public static void createTable(String query) { 
        // Extract table name and columns from the query 
        int tableNameStart = query.indexOf("CREATE TABLE") + "CREATE TABLE".length(); 
        int tableNameEnd = query.indexOf("("); 
        String tableName = query.substring(tableNameStart, tableNameEnd).trim(); 
 
        int columnsStart = tableNameEnd + 1; 
        int columnsEnd = query.lastIndexOf(")"); 
        String columnsText = query.substring(columnsStart, columnsEnd).trim(); 
        String[] columns = columnsText.split(","); 
 
        // Create table directory if it doesn't exist 
        File dataDir = new File(DATA_DIR); 
        if (!dataDir.exists()) { 
            dataDir.mkdirs(); 
        } 
 
        // Create metadata file for the table 
        File metadataFile = new File(dataDir, tableName + "_metadata.txt"); 
        try (FileWriter writer = new FileWriter(metadataFile)) { 
            for (String column : columns) { 
                writer.write(column.trim() + "\n"); 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
 
    public static void insertIntoTable(String query) { 
        // Extract table name and values from the query 
        int tableNameStart = query.indexOf("INSERT INTO") + "INSERT INTO".length(); 
        int tableNameEnd = query.indexOf("VALUES"); 
        String tableName = query.substring(tableNameStart, tableNameEnd).trim(); 
 
        int valuesStart = query.indexOf("(", tableNameEnd) + 1; 
        int valuesEnd = query.lastIndexOf(")"); 
        String valuesText = query.substring(valuesStart, valuesEnd).trim(); 
        String[] values = valuesText.split(","); 
 
        // Append values to the table file 
        File tableFile = new File(DATA_DIR, tableName + ".txt"); 
        try (FileWriter writer = new FileWriter(tableFile, true)) { 
            for (int i = 0; i < values.length; i++) { 
                writer.write(values[i].trim()); 
                if (i < values.length - 1) { 
                    writer.write(","); 
                } 
            } 
            writer.write("\n"); 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } 
    } 
} 
 