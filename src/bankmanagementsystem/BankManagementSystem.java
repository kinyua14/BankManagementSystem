
package bankmanagementsystem;

/**
 *
 * @author BRIO
 */
// base class for all the accounts.
class BankAccount{
    // protected balance field, accessible by subclasses
    protected double balance;
    //Constructor to initialize the balance of the account.
    public BankAccount(double initialBalance){
        this.balance=initialBalance;//sets the initial balance.
    }
    //Method to deposit amount into the account.
    public void deposit(double amount){
        if(amount>0){
            balance+=amount;// Add ammount to the balance.
            System.out.println("Deposited Amount: " + amount);
        }else{
            System.out.println("Invalid deposit amount.");
        }
    }
    //Method to withdraw amount from the account.
    public void withdraw(double amount){
        if(amount>0&& amount<=balance){
            balance-=amount;//deduct amount from the account.
            System.out.println("Withdrawal amount: " +amount);
        }else{
            System.out.println("Invalid withdrawal amount");
        }
    }
    //Method to check the current balance of the account.
    public double GetBalance(){
        return balance;
    }
}
// Creating subclasses.
class SavingsAccount extends BankAccount{
// savings account always have interestrate, so we need to add a method to calculate the interest.
    private double interestRate;
    // constructor to initialize the balance and the interestRate.
    public SavingsAccount(double interestRate, double initialBalance){
        super(initialBalance);// calls the parent class constructor.
        this.interestRate=interestRate;
    }
    //method to calaculate interestRate and add to the ballance.
    public void addinterest(){
        double interest= balance*interestRate/100;
        deposit(interest);// reuse deposit method to add interest to the balance.
        System.out.println("Interest added: " + interest);
    }
}
//currentAccount that extends BankAccount.
class currentAccount extends BankAccount{
    //currentAccount have overdraft limit, allowing the balance to go to negative to a certain point.
    private double overdraftLimit;
    // constructor to initialize the balance and the overdraftLimit.
    public currentAccount(double overdraftLimit, double initialBalance){
        super(initialBalance);// calls the parent constructor.
        this.overdraftLimit=overdraftLimit;
    }
    //overriding the withdraw method to allow overdraft.
    @Override
    public void withdraw(double amount){
        if(amount>0&&(balance+overdraftLimit>=amount)){
            balance-=amount;
            System.out.println("Withdrawn: " + amount);
        }else{
            System.out.println("Overdraft limit exceeded");
        }    
        
    }
}
// FixedDepositAccount that extends BankAccount
class FixedDepositAccount extends BankAccount{
    //FixedDepositAccount lock in the bakance for a fixed period, often with high interest.
    private double interestRate;
    private int duration; // in months
    // constructor to initialze balance, interestRate amd duration
    public FixedDepositAccount(double initialBalance, double interestRate, int duration){
        super(initialBalance);// calls the parent constructor.
        this.interestRate=interestRate;
        this.duration=duration;
    }
    // method to calaculate and add interest at the end of fixed deposit period.
    public void addinterest(){
        double interest=balance*interestRate/100;
        deposit(interest);// reuse the deposit method to add interest to the balance.
        System.out.println("Interest added: " + interest);
    }
    // method to check maturity
    public boolean isMature(int monthspassed){
        return monthspassed>=duration;
    } 
}
// main class to demonstrate polymorphism and account management.
//polymorphism allows objects of different class to be treated as if they were object of the same class.
public class BankManagementSystem {
    public static void main(String[] args) {
        // create different types of account using polymorphism.
        BankAccount savings= new SavingsAccount(5,1000);// savings account with5% interest and 1000 initialbalance.
        BankAccount current= new currentAccount(200,2000);//current account with 200 overdraftlimit and 2000 initialbalance.
        BankAccount fixed=new FixedDepositAccount(5000,5,12);//5000 initalbalance,5% interest rate and 12 months duration.

        // Perform operation on accounts using common methods from BankAccount.
        savings.deposit(5000);// deposit into savings account.
        current.withdraw(800);// withdraw from current account,overdraftlimit applied.
        fixed.deposit(10000);// deposit into fixed deposit account.
        
        // casting to specific accounts type to access subclass specific methods.
        ((SavingsAccount)savings).addinterest();// Add interest to savings account.
        ((FixedDepositAccount)fixed).addinterest();//Add interest to fixed deposit account.
        
        //Checking balance for each account.
        
        System.out.println("Savings Account Balance: " + savings.GetBalance());
        System.out.println("Current Account Balance: " + current.GetBalance());
        System.out.println("Fixed Deposit Account Balance: " + fixed.GetBalance());
        
        // check if fixed deposit has matured.
        if(((FixedDepositAccount)fixed).isMature(12)){
            System.out.println("Fixed Deposit has matured.");
        }else{
            System.out.println("Immature Fixed Deposit.");
        }
    }
    
}
