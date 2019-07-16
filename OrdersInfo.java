
import java.net.*;
import java.io.*;

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

    public static void main(String args[]) throws Exception
    {
        OrdersInfo connect = new OrdersInfo();
        System.out.println("Btc difference: " + connect.getPercentDifference(1) + "%");
        System.out.println("Ltc difference: " + connect.getPercentDifference(2) + "%");
        System.out.println("Bch difference: " + connect.getPercentDifference(3) + "%");
    }
}
