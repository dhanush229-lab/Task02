public class StudentThread extends Thread {
    FileManager fileManager;
    StudentInfo[] students;

    public StudentThread(FileManager fileManager, StudentInfo[] students) {
        this.fileManager = fileManager;
        this.students = students;
    }

    @Override
    public void run() {
        for (StudentInfo student : students) {
            fileManager.writeStudent(student);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
