package seedu.duke.currency;
import seedu.duke.commands.Commands;
import seedu.duke.expense.BudgetManager;
import seedu.duke.expense.Expense;

import java.io.FileWriter;
import java.util.HashMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Currency {
    private String currentCurrency;
    private final HashMap<String, Double> exchangeRates = new HashMap<>();
    private Scanner scanner;
    private BudgetManager budgetManager;

    public Currency(Scanner scanner, BudgetManager budgetManager){
        initializeExchangeRates();
        this.scanner = scanner;
        this.budgetManager = budgetManager;
        try{
            File f = new File("./currencyCurrency");
            Scanner s = new Scanner(f);
            String line;

            if(s.hasNextLine()){
                line = s.nextLine();
                currentCurrency = line;
            }

            else{
                currentCurrency = Commands.DEFAULT_CURRENCY;
            }
        }
        catch(FileNotFoundException e){
            File f = new File("./currencyCurrency");
            System.out.println("file not found...");
            System.out.println("A new file to load your current currency will be created for you!");
            currentCurrency = Commands.DEFAULT_CURRENCY;
        }
    }

    public void initializeExchangeRates() {
        exchangeRates.put("AED", 2.748584);
        exchangeRates.put("ALL", 79.961652);
        exchangeRates.put("AMD", 0.001251);
        exchangeRates.put("ANG", 1.336142);
        exchangeRates.put("AOA", 0.001251);
        exchangeRates.put("ARS", 799.616516);
        exchangeRates.put("AUD", 1.194296);
        exchangeRates.put("AWG", 1.336142);
        exchangeRates.put("AZN", 1.336142);
        exchangeRates.put("BAM", 1.445209);
        exchangeRates.put("BBD", 1.336142);
        exchangeRates.put("BDT", 1.336142);
        exchangeRates.put("BGN", 1.445209);
        exchangeRates.put("BHD", 0.281407);
        exchangeRates.put("BMD", 0.748423);
        exchangeRates.put("BND", 1.000000);
        exchangeRates.put("BOB", 1.336142);
        exchangeRates.put("BRL", 4.292024);
        exchangeRates.put("BSD", 0.748423);
        exchangeRates.put("BTN", 64.361442);
        exchangeRates.put("BWP", 10.196884);
        exchangeRates.put("BYN", 1.336142);
        exchangeRates.put("BZD", 1.336142);
        exchangeRates.put("CAD", 1.073865);
        exchangeRates.put("CDF", 1.336142);
        exchangeRates.put("CHF", 0.661593);
        exchangeRates.put("CLP", 1.336142);
        exchangeRates.put("CNY", 5.424991);
        exchangeRates.put("COP", 1.336142);
        exchangeRates.put("CRC", 1.336142);
        exchangeRates.put("CUP", 1.336142);
        exchangeRates.put("CVE", 1.336142);
        exchangeRates.put("CZK", 17.292999);
        exchangeRates.put("DJF", 1.336142);
        exchangeRates.put("DKK", 5.162989);
        exchangeRates.put("DOP", 1.336142);
        exchangeRates.put("DZD", 1.336142);
        exchangeRates.put("EGP", 1.336142);
        exchangeRates.put("ERN", 1.336142);
        exchangeRates.put("ETB", 1.336142);
        exchangeRates.put("EUR", 0.691941);
        exchangeRates.put("FJD", 1.336142);
        exchangeRates.put("FKP", 1.336142);
        exchangeRates.put("FOK", 1.336142);
        exchangeRates.put("GBP", 0.578381);
        exchangeRates.put("GEL", 1.336142);
        exchangeRates.put("GGP", 1.336142);
        exchangeRates.put("GHS", 1.336142);
        exchangeRates.put("GIP", 1.336142);
        exchangeRates.put("GMD", 1.336142);
        exchangeRates.put("GNF", 1.336142);
        exchangeRates.put("GTQ", 1.336142);
        exchangeRates.put("GYD", 1.336142);
        exchangeRates.put("HKD", 5.817946);
        exchangeRates.put("HNL", 1.336142);
        exchangeRates.put("HRK", 1.336142);
        exchangeRates.put("HTG", 1.336142);
        exchangeRates.put("HUF", 274.889545);
        exchangeRates.put("IDR", 12344.396139);
        exchangeRates.put("ILS", 2.779378);
        exchangeRates.put("IMP", 1.336142);
        exchangeRates.put("INR", 64.361442);
        exchangeRates.put("IQD", 1.336142);
        exchangeRates.put("IRR", 31435.719162);
        exchangeRates.put("ISK", 99.686052);
        exchangeRates.put("JEP", 1.336142);
        exchangeRates.put("JMD", 1.336142);
        exchangeRates.put("JOD", 1.336142);
        exchangeRates.put("JPY", 111.753542);
        exchangeRates.put("KES", 1.336142);
        exchangeRates.put("KGS", 1.336142);
        exchangeRates.put("KHR", 1.336142);
        exchangeRates.put("KID", 1.336142);
        exchangeRates.put("KMF", 1.336142);
        exchangeRates.put("KRW", 1.336142);
        exchangeRates.put("KWD", 0.231281);
        exchangeRates.put("KYD", 1.336142);
        exchangeRates.put("KZT", 376.317462);
        exchangeRates.put("LAK", 1.336142);
        exchangeRates.put("LBP", 1.336142);
        exchangeRates.put("LKR", 221.768240);
        exchangeRates.put("LRD", 1.336142);
        exchangeRates.put("LSL", 1.336142);
        exchangeRates.put("LYD", 3.600065);
        exchangeRates.put("MAD", 1.336142);
        exchangeRates.put("MDL", 1.336142);
        exchangeRates.put("MGA", 1.336142);
        exchangeRates.put("MKD", 1.336142);
        exchangeRates.put("MMK", 1.336142);
        exchangeRates.put("MNT", 1.336142);
        exchangeRates.put("MOP", 1.336142);
        exchangeRates.put("MRU", 1.336142);
        exchangeRates.put("MUR", 33.828650);
        exchangeRates.put("MVR", 1.336142);
        exchangeRates.put("MWK", 1.336142);
        exchangeRates.put("MXN", 15.146553);
        exchangeRates.put("MYR", 3.308730);
        exchangeRates.put("MZN", 1.336142);
        exchangeRates.put("NAD", 1.336142);
        exchangeRates.put("NGN", 1.336142);
        exchangeRates.put("NIO", 1.336142);
        exchangeRates.put("NOK", 7.908614);
        exchangeRates.put("NPR", 103.026579);
        exchangeRates.put("NZD", 1.305470);
        exchangeRates.put("OMR", 0.288067);
        exchangeRates.put("PAB", 1.336142);
    }

    public String getCurrentCurrency() {
        return currentCurrency;
    }

    public Double getExchangeRate(String currencyCode) {
        if (exchangeRates.containsKey(currencyCode)) {
            return exchangeRates.get(currencyCode);
        } else {
            System.out.println("Currency code " + currencyCode + " not found.");
            return null; // or you can throw an exception if that's the desired behavior
        }
    }

    public void changeCurrency(){
        System.out.println("If you'd like to enter your own exchange rate from your current currency, enter 1");
        System.out.println("If you'd like to switch currencies with an estimated exchange rate based off of 24/3/2025 exchange rates, enter 2");
        System.out.println("Please enter a number");
        String method = scanner.nextLine().trim();
        try{
            String newCurrency = null;
            Double finalExchangeRate = null;
            Integer intMethod = Integer.parseInt(method);
            if(intMethod != 1 && intMethod != 2){
                System.out.println("Please input either '1' or '2'");
                return;
            }
            if(intMethod == 1) {
                System.out.println("Please enter a currency to change to");
                newCurrency = scanner.nextLine().trim();
                System.out.println("Please input your exchange rate from " + getCurrentCurrency() + " to a new currency");
                String input = scanner.nextLine().trim();
                finalExchangeRate = Double.parseDouble(input);
            }
            if(intMethod == 2) {
                System.out.println("Note: Please enter currency based on ISO 4217 standard (eg: SGD, USD, JPY");
                System.out.println("Please enter a currency to change to");
                newCurrency = scanner.nextLine().trim();
                Double exchangeRate = getExchangeRate(newCurrency);
                if(exchangeRate == null){
                    System.out.println("Currency not found!");
                    return;
                }
                finalExchangeRate = exchangeRate/getExchangeRate(currentCurrency);
            }
            editExpenseCurrency(finalExchangeRate, newCurrency);
        }
        catch(NumberFormatException e){
            System.out.println("Please input a valid number");
        }
    }

    public void editExpenseCurrency(Double finalExchangeRate, String newCurrency){
        budgetManager.editExpenseCurrency(finalExchangeRate);
        currentCurrency = newCurrency;
    }
}
