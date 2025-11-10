public class MainApp {
    public static void main(String[] args) {
        System.out.println("=== Student Score Logger Started ===\n");

        FileManager fileManager = new FileManager("scores.csv");

        StudentInfo[] group1 = {
            new StudentInfo("Amit Kumar", "A101", 85.5),
            new StudentInfo("Priya Singh", "A102", 92.0),
            new StudentInfo("Raj Patel", "A103", 78.5)
        };

        StudentInfo[] group2 = {
            new StudentInfo("Neha Sharma", "B101", 88.0),
            new StudentInfo("Suresh Reddy", "B102", 91.5),
            new StudentInfo("Kavita Iyer", "B103", 79.0)
        };

        StudentThread thread1 = new StudentThread(fileManager, group1);
        StudentThread thread2 = new StudentThread(fileManager, group2);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("\n=== All Threads Completed ===");
        
        fileManager.printAllRecords();
    }
}
