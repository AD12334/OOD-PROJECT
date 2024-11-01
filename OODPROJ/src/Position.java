public class Position {
    private int salary;
    private int scale;
    private String position;
    private String Field;
    public Position(String Field,String position,int salary,int scale){
        this.salary = salary;
        this.scale = scale;
        this.position = position;
        this.Field = Field;
    }
    public int getSalary() {
        return salary;
    }

    public int getScale() {
        return scale;
    }
    public String getPosition() {
        return position;
    }
    public String getField() {
        return Field;
    }
    @Override
    public String toString(){
        return "Field : " + getField() + "\n Position : " + getPosition() + "\n Salary : " + getSalary() + "\n Scale : " + getScale() + "\n";
    }

}
