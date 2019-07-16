
import java.net.*;
import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

public class OrdersInfo
{

//    //add last deals - done
//    //add graph
//    //add change in percent from the last check
//
//    /**
//     * Gets the first price py the order type
//     * @param index the index of the currency: 1 btc, 2 ltc, 3 bch
//     * @param type the type of the order: 0 for asks, 1 for bids
//     * @return price
//     * @throws Exception throws while wrong parameter
//     */
//    public double getFirstOrder(int index, int type) throws Exception
//    {
//        int c;
//        String str = "";
//        switch (index)
//        {
//            case 1:
//                str = "https://www.bit2c.co.il/Exchanges/BtcNis/orderbook.json";
//                break;
//            case 2:
//                str = "https://www.bit2c.co.il/Exchanges/LtcNis/orderbook.json";
//                break;
//            case 3:
//                str = "https://www.bit2c.co.il/Exchanges/BchNis/orderbook.json";
//                break;
//        }
//        if (str.equals(""))
//        {
//            throw new Exception();
//        }
//
//        URL url = new URL(str);
//        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
//        InputStream in = urlCon.getInputStream();
//        StringBuilder price = new StringBuilder();
//        if (type == 1)
//        {
//            while ((c = in.read()) != 100){}
//        }
//        else if (type != 0)
//        {
//            throw new Exception();
//        }
//        while ((c = in.read()) != 44) {
//            if (c > 47 && c < 58 || c == 46) {
//                price.append((char) c);
//            }
//        }
//        in.close();
//        return Double.parseDouble(price.toString());
//    }
//
//
//    /**
//     * returns the percent difference between the "asks"
//     * and the "bids"
//     * @param index the index of the currency. 1 for btc, 2 for ltc, 3 for bch.
//     * @return difference
//     */
//    public double getPercentDifference(int index) throws Exception
//    {
//        return ((this.getFirstOrder(index,0) - this.getFirstOrder(index, 1))
//                / this.getFirstOrder(index,1)) * 100;
//    }
//
//
//    /**
//     * Counts the number of deals made in less that a hour and difference of more than the given percent
//     * @param stack the stack of orders
//     * @param percent the given percent to check
//     * @return counter of times of true condition
//     */
//    private int orderByDateAndPrice(Stack<Order> stack, double percent, long time)
//    {
//        int counter = 0;
//        Order order;
//        //checking only side by side deals, can be improved to check by time (hour)
//        while (stack.size() > 1)
//        {
//            order = stack.pop();
//            if(((order.getPrice() - stack.peek().getPrice())/stack.peek().getPrice() > percent) &&
//            order.getDate() - stack.peek().getDate() < time)
//            {
////                System.out.println(order);
////                System.out.println(stack.peek());
//                counter++;
//            }
//        }
//        return counter;
//    }
//
//
//    /**
//     * prints last numOfDeals deals
//     * @param stack the stack of deals
//     * @param numOfDeals number of deals to print
//     */
//    public void printLastDeals(Stack<Order> stack, int numOfDeals)
//    {
//        Stack<Order> copyStack = new Stack<Order>();
//        for (int i = 0;i < stack.size(); i++)
//        {
//            copyStack.push(stack.elementAt(i));
//        }
//        for(;numOfDeals != 0; numOfDeals--)
//        {
//            System.out.println(copyStack.pop());
//        }
//    }
//
//
//    /**
//     * returns the number of matched deals from all the deals
//     * @param index the index for currency
//     * @param percent the percent to bound the matching
//     * @return number of matched deals from all the deals
//     * @throws Exception any exception
//     */
//    public double getNumberOfDifferenceOrders(int index, double percent, long time) throws Exception
//    {
//        int c;
//        String str = "";
//        switch (index)
//        {
//            case 1:
//                str = "https://www.bit2c.co.il/Exchanges/BtcNis/trades.json?since=150000";
//                break;
//            case 2:
//                str = "https://www.bit2c.co.il/Exchanges/LtcNis/trades.json?since=150000";
//                break;
//            case 3:
//                str = "https://www.bit2c.co.il/Exchanges/BchNis/trades.json?";
//                break;
//        }
//        URL url = new URL(str);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        InputStream in = connection.getInputStream();
//        StringBuilder builder = new StringBuilder();
//        while((c = in.read()) != -1)
//        {
//            builder.append((char) c);
//        }
//        String[] splited = builder.toString().split("\\},\\{");
//        Stack<Order> stack = new Stack<Order>();
//        for (String item: splited)
//        {
//            stack.push(new Order(item));
//        }
//        double size = stack.size();
//        printLastDeals(stack, 1);
//        return orderByDateAndPrice(stack, percent / 100.0 , time) / size;
//    }
//
//
//
//    /**
//     * main
//     * @param args cmd arguments
//     * @throws Exception may throw any exception
//     */
//    public static void main(String args[]) throws Exception
//    {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Enter update rate in seconds: ");
//        int rate = 10;
//        rate = scanner.nextInt();
//        scanner.close();
//        Double lastBtcBuy, lastBtcSell, lastLtcBuy, lastLtcSell, lastBchBuy, lastBchSell;
//        OrdersInfo connect = new OrdersInfo();
//        lastBtcBuy = connect.getFirstOrder(1,0);
////        lastBtcSell = connect.getFirstOrder(1,1);
//        lastLtcBuy = connect.getFirstOrder(2,0);
////        lastLtcSell = connect.getFirstOrder(2,1);
//        lastBchBuy = connect.getFirstOrder(3,0);
////        lastBchSell = connect.getFirstOrder(3,1);
//        while(true)
//        {
//            System.out.println("bit2c.co.il:");
//            System.out.println("Btc difference: " + connect.getPercentDifference(1) + "%" +
//                    "\n   Can buy for: " + connect.getFirstOrder(1, 0) +
//                    "\n   Can sell for: " + connect.getFirstOrder(1, 1));
////                "\n   The difference from the last time checked is: " +
////                ((connect.getFirstOrder(1,0) / lastBtcBuy) * 100 - 100) + " %");
//            System.out.println("Ltc difference: " + connect.getPercentDifference(2) + "%" +
//                    "\n   Can buy for: " + connect.getFirstOrder(2, 0) +
//                    "\n   Can sell for: " + connect.getFirstOrder(2, 1));
////                "\n   The difference from the last time checked is: " +
////                ((connect.getFirstOrder(2,0) / lastBtcBuy) * 100 - 100) + " %");
//            System.out.println("Bch difference: " + connect.getPercentDifference(3) + "%" +
//                    "\n   Can buy for: " + connect.getFirstOrder(3, 0) +
//                    "\n   Can sell for: " + connect.getFirstOrder(3, 1));
////                "\n   The difference from the last time checked is: " +
////                ((connect.getFirstOrder(3,0) / lastBchBuy) * 100 - 100) + " %");
//            double percent = 3;
//            long time = 180;
////        Scanner scanner = new Scanner(System.in);
////        System.out.print("Enter the percent to check: ");
////        try
////        {
////            percent = scanner.nextDouble();
////        }
////        catch (InputMismatchException e)
////        {
////            System.out.println("Wrong input (should be double)");
////            percent = scanner.nextDouble();
////        }
////        scanner.close();
//            System.out.println("\nStats:\nPercent of deals made with difference of more than " +
//                    percent + "% in less than " + time / 60.0 + " hour: \n");
//
//            System.out.println("\nBtc: " + connect.getNumberOfDifferenceOrders(1, percent, time) * 100 + "%\n");
//            System.out.println("\nLtc: " + connect.getNumberOfDifferenceOrders(2, percent, time) * 100 + "%\n");
//            System.out.println("\nBch: " + connect.getNumberOfDifferenceOrders(3, percent, time) * 100 + "%\n");
//            Thread.sleep(rate * 1000);
//            for(int i = 0; i < 50; i++)
//            {
//                System.out.println("");
//            }
//        }
//    }
    public static void main(String[] args) throws Exception
    {
        int c;
        String str = "";
        str = "http://www.bit2c.co.il/Exchanges/BtcNis/trades.json";
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
//        printLastDeals(stack, 1);
        System.out.println(stack);
    }
}
