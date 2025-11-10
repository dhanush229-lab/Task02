import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {
    FileManager fileManager;
    String testFile = "test_scores.csv";

    @BeforeEach
    void setup() throws IOException {
        Files.deleteIfExists(Paths.get(testFile));
        fileManager = new FileManager(testFile);
    }

    @AfterEach
    void cleanup() throws IOException {
        Files.deleteIfExists(Paths.get(testFile));
    }

    @Test
    @DisplayName("Test writing one student record")
    void testWriteOneStudent() throws IOException {
        StudentInfo student = new StudentInfo("Test Student", "T001", 85.0);
        fileManager.writeStudent(student);

        assertTrue(Files.exists(Paths.get(testFile)));
        String content = Files.readString(Paths.get(testFile));
        assertTrue(content.contains("Test Student"));
    }

    @Test
    @DisplayName("Test writing multiple students")
    void testWriteMultipleStudents() throws IOException {
        StudentInfo s1 = new StudentInfo("Student One", "T001", 80.0);
        StudentInfo s2 = new StudentInfo("Student Two", "T002", 90.0);
        
        fileManager.writeStudent(s1);
        fileManager.writeStudent(s2);

        String content = Files.readString(Paths.get(testFile));
        assertTrue(content.contains("Student One"));
        assertTrue(content.contains("Student Two"));
    }

    @Test
    @DisplayName("Test CSV format is correct")
    void testCSVFormat() {
        StudentInfo student = new StudentInfo("John Doe", "J123", 95.5);
        String csv = student.toCSV();
        
        assertEquals("John Doe,J123,95.5", csv);
    }

    @Test
    @DisplayName("Test thread completes successfully")
    void testThreadCompletion() throws InterruptedException {
        StudentInfo[] students = {
            new StudentInfo("Thread Student", "TS001", 88.0)
        };
        
        StudentThread thread = new StudentThread(fileManager, students);
        thread.start();
        thread.join(2000);
        
        assertFalse(thread.isAlive());
    }

    @Test
    @DisplayName("Test two threads writing simultaneously")
    void testTwoThreads() throws InterruptedException, IOException {
        StudentInfo[] group1 = {
            new StudentInfo("Concurrent A", "C001", 85.0),
            new StudentInfo("Concurrent B", "C002", 86.0)
        };
        
        StudentInfo[] group2 = {
            new StudentInfo("Concurrent C", "C003", 87.0),
            new StudentInfo("Concurrent D", "C004", 88.0)
        };

        StudentThread t1 = new StudentThread(fileManager, group1);
        StudentThread t2 = new StudentThread(fileManager, group2);

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        String content = Files.readString(Paths.get(testFile));
        long lineCount = content.lines().count();
        assertEquals(4, lineCount);
    }
}
