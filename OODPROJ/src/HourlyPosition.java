public class HourlyPosition {
    private int salary;
    private int scale;
    private String position;
    private String Field;
    public HourlyPosition(String Field,String position,int salary,int scale){
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
    public double calculatePRSI(int hours){
        double prsi=0;
        double weeklyPay = getHourlyPay() * hours;
        double credit = 0;
//https://www.gov.ie/en/publication/14ecbe-the-different-classes-of-pay-related-social-insurance-prsi/#class-a
        //We are assumming that all our employees are subject to the rates of prsi class A
        /*According to class A prsi is charged at a rate of 4.1% as of October 1st 2024 for a weekly income of 352.01 or greater
        We therefore can calculate prsi by dividing our annual week by 52 and then applying the rate of 4.1% to any additional income made over 352
         weekly tapered PRSI credit of €12 is available for employees insured at Class A whose earnings are between €352.01 and €424 in a week. The maximum PRSI credit of €12 per week applies to gross weekly earnings of €352.01.

From 1 October 2024, a person earning €352.01 pays €14.43 PRSI (4.1%). After the €12 credit is deducted they will pay PRSI of €2.43. For people earning between €352.01 and €424, the credit of €12 is reduced by one-sixth of earnings over €352.01. There is no PRSI credit once earnings exceed €424.
         */

        weeklyPay = Math.round(weeklyPay * 100.0) / 100.0;
        if (weeklyPay <352.0){
            prsi = 0;
        }else if(weeklyPay == 352.01){
            credit = 12;
            prsi = ((weeklyPay) * 0.041 ) - credit;

        }
        else if (weeklyPay > 352.01 && weeklyPay <= 424.0){
            credit = 12 - (weeklyPay-352)/6;

            prsi = ((weeklyPay) * 0.041 ) - credit;

        }else{
            credit = 0;
            prsi = ((weeklyPay) * 0.041 ) - credit;

        }
        prsi = Math.round(prsi * 100.0) / 100.0;


        return prsi;
    }
    public double calculatePAYE(int hours){
        double HigherRate;

        double grosspay = getHourlyPay()* hours;
        double part1;//Tax on first 42000
        double part2;//Tax on over 42000
        /*
        https://www.revenue.ie/en/personal-tax-credits-reliefs-and-exemptions/tax-relief-charts/index.aspx
        For the sake of ease I will be assuming that all employees are Single or widowed or surviving civil partner without qualifying children
        This means that 42000 of their income is subject to 20% and anything over this is subject to 40%
        All PAYE taxpayers are entitled to a tax credit known as the Employee Tax Credit (formerly known as the PAYE tax credit). This is worth €1,875 in 2024 (€1,775 in 2023). If you are married and taxed under joint assessment, then you and your spouse may both claim an Employee Tax Credit.
        https://www.citizensinformation.ie/en/money-and-tax/tax/income-tax-credits-and-reliefs/employment-tax-credits-and-reliefs/#:~:text=All%20PAYE%20taxpayers%20are%20entitled,claim%20an%20Employee%20Tax%20Credit.
        The following tax credits will increase by €100 to €1,875:
        Personal Tax Credit

         */
        if(grosspay<= 42000/52){
            HigherRate = 0;
            part1 = grosspay * 0.2;
            part2 = HigherRate * 0.4;
        }else{
            HigherRate = grosspay - 42000/52;
            part1 = (42000/52) * 0.2;
            part2 = HigherRate * 0.4;
        }
        double part3 = part1 + part2;
        double Paye = part3 - (1875*2)/52; //sum of tax - employee tax credit and personal tax credit
        return Math.max(Paye,0);
    }
    public double calculateUSC(int hours){
        double USC=0;
        double grosspay = (getHourlyPay()) * hours;
        double[] rates = {0.005,0.02,0.04,0.08};
        double[] thresholds = {12012/52, 25760/52, 70044/52};
        /*
        https://www.citizensinformation.ie/en/money-and-tax/tax/income-tax/universal-social-charge/#:~:text=The%20Universal%20Social%20Charge%20is,do%20not%20pay%20any%20USC.
        If your total income is €13,000 or less per year, you do not pay any USC. If it is more than €13,000 per year, you pay USC on your full income.
         */
        //For ease, we will be assuming that our employees are not eligible to receive reduced rates of USC

        if(grosspay <= 13000/52){
            return USC;
        }else {
            USC += thresholds[0] * rates[0];
        }
        if(grosspay <= thresholds[1]){
            USC += (grosspay- thresholds[0])*rates[1];
            return USC;
        }
        else{
            USC += (thresholds[1] - thresholds[0]) * rates[1];
        }
        if (grosspay <= thresholds[2]){
            USC += (grosspay - thresholds[1])*rates[2];
            return USC;
        }
        else{
            USC+= (thresholds[2] - thresholds[1]) * rates[2];
            USC += (grosspay - thresholds[2])*rates[3];
        }
        return USC;
    }

    public double getHourlyPay(){
        double hourlypay =  (double)((getSalary()/52.0)/40.0);
        hourlypay = Math.round(hourlypay * 100.0) / 100.0;
        return hourlypay;
    }


    @Override
    public String toString(){
        return "Field : " + getField() + "\n Position : " + getPosition() + "\n Salary : " + getSalary() + "\n Scale : " + getScale();
    }

}
