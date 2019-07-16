
import java.net.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class OrdersInfo
{


    /**
     * Gets the first price py the order type
     * @param index the index of the currency: 1 btc, 2 ltc, 3 bch
     * @param type the type of the order: 0 for asks, 1 for bids
     * @return price
     * @throws Exception throws while wrong parameter
     */
    public double getFirstOrder(int index, int type) throws Exception
    {
        int c;
        String str = "";
        switch (index)
        {
            case 1:
                str = "https://www.bit2c.co.il/Exchanges/BtcNis/orderbook.json";
                break;
            case 2:
                str = "https://www.bit2c.co.il/Exchanges/LtcNis/orderbook.json";
                break;
            case 3:
                str = "https://www.bit2c.co.il/Exchanges/BchNis/orderbook.json";
                break;
        }
        if (str.equals(""))
        {
            throw new Exception();
        }

        URL url = new URL(str);
        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
        InputStream in = urlCon.getInputStream();
        StringBuilder price = new StringBuilder();
        if (type == 1)
        {
            while ((c = in.read()) != 100){}
        }
        else if (type != 0)
        {
            throw new Exception();
        }
        while ((c = in.read()) != 44) {
            if (c > 47 && c < 58 || c == 46) {
                price.append((char) c);
            }
        }
        in.close();
        return Double.parseDouble(price.toString());
    }


    /**
     * returns the percent difference between the "asks"
     * and the "bids"
     * @param index the index of the currency. 1 for btc, 2 for ltc, 3 for bch.
     * @return difference
     */
    public double getPercentDifference(int index) throws Exception
    {
        return ((this.getFirstOrder(index,0) - this.getFirstOrder(index, 1))
                / this.getFirstOrder(index,1)) * 100;
    }


    /**
     * Counts the number of deals made in less that a hour and difference of more than the given percent
     * @param stack the stack of orders
     * @param percent the given percent to check
     * @return counter of times of true condition
     */
    private int orderByDateAndPrice(Stack<Order> stack, double percent)
    {
        int counter = 0;
        Order order;
        while (stack.size() > 1)
        {
            order = stack.pop();
            if(((order.getPrice() - stack.peek().getPrice())/stack.peek().getPrice() > percent) &&
            order.getDate() - stack.peek().getDate() < 3600)
            {
                counter++;
            }
        }
        return counter;
    }


    /**
     * returns the number of matched deals from all the deals
     * @param index the index for currency
     * @param percent the percent to bound the matching
     * @return number of matched deals from all the deals
     * @throws Exception any exception
     */
    public double getNumberOfDifferenceOrders(int index, double percent) throws Exception
    {
        int c;
        String str = "";
        switch (index)
        {
            case 1:
                str = "https://www.bit2c.co.il/Exchanges/BtcNis/trades.json?since=150000";
                break;
            case 2:
                str = "https://www.bit2c.co.il/Exchanges/LtcNis/trades.json";
                break;
            case 3:
                str = "https://www.bit2c.co.il/Exchanges/BchNis/trades.json?";
                break;
        }
        URL url = new URL(str);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream in = connection.getInputStream();
        StringBuilder builder = new StringBuilder();
        while((c = in.read()) != -1)
        {
            builder.append((char) c);
        }
        String[] splited = builder.toString().split("\\},\\{");
        Stack<Order> stack = new Stack<Order>();
        for (String item: splited)
        {
            stack.push(new Order(item));
        }
        double size = stack.size();
        return orderByDateAndPrice(stack, percent / 100.0) / size;
    }



    public static void main(String args[]) throws Exception
    {
        OrdersInfo connect = new OrdersInfo();
        System.out.println("bit2c.co.il:");
        System.out.println("Btc difference: " + connect.getPercentDifference(1) + "%" +
                " Can buy for: " + connect.getFirstOrder(1,0) +
                " Can sell for: " + connect.getFirstOrder(1,1));
        System.out.println("Ltc difference: " + connect.getPercentDifference(2) + "%" +
                " Can buy for: " + connect.getFirstOrder(2,0) +
                " Can sell for: " + connect.getFirstOrder(2,1));
        System.out.println("Bch difference: " + connect.getPercentDifference(3) + "%" +
                " Can buy for: " + connect.getFirstOrder(3,0) +
                " Can sell for: " + connect.getFirstOrder(3,1));
        double percent = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the percent to check: ");
        try
        {
            percent = scanner.nextDouble();
        }
        catch (InputMismatchException e)
        {
            System.out.println("Wrong input (should be double)");
            percent = scanner.nextDouble();
        }
        scanner.close();
        System.out.println("\nStats:\n Percent of deals made with difference of more than " +
                percent + "% in less than 1 hour: \n");

        System.out.println("Btc: " + connect.getNumberOfDifferenceOrders(1,percent) * 100 + "%");
        System.out.println("Ltc: " + connect.getNumberOfDifferenceOrders(2,percent) * 100 + "%");
        System.out.println("Bch: " + connect.getNumberOfDifferenceOrders(3,percent) * 100 + "%");
    }
}
