public class StudentInfo {
    String fullName;
    String idNumber;
    double marks;

    public StudentInfo(String fullName, String idNumber, double marks) {
        this.fullName = fullName;
        this.idNumber = idNumber;
        this.marks = marks;
    }

    public String toCSV() {
        return fullName + "," + idNumber + "," + marks;
    }
}
