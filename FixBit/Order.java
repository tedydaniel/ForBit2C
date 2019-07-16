
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Representing order with date, price, amount and id.
 */
public class Order
{
    private String strRep;
    private GregorianCalendar date;
    private double price;
    private double amount;
    private int id;

    public Order()
    {
        strRep = "empty order";
        GregorianCalendar date = new GregorianCalendar();
        price = 0;
        amount = 0;
        id = 0;
    }

    public Order(String str)
    {
        strRep = str;
        initialize(str);
    }

    private void initialize(String str)
    {
        String[] splited = str.split(",");
        for(int i = 0; i < splited.length; i++)
        {
            splited[i] = splited[i].replaceAll("[^0-9.]","");
        }
        date = new GregorianCalendar();
        date.setTimeInMillis(Long.parseLong(splited[0]) * 1000);
        price = Double.parseDouble(splited[1]);
        amount = Double.parseDouble(splited[2]);
        id = Integer.parseInt(splited[3]);
    }

    public String toString()
    {
        return date.getTime() + " | Price: " + price + " | Amount " + amount + " | ID: " + id;
    }

    public double getPrice()
    {
        return price;
    }

    public double getAmount()
    {
        return amount;
    }

    public long getDate()
    {
        return date.getTimeInMillis() / 1000;
    }
}
