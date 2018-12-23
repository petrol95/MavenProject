package HomeWork2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static java.lang.Integer.parseInt;

public class HomeWork2 {

    public static void main(String[] args) {
        BufferedReader keyboard;
        keyboard = new BufferedReader(new InputStreamReader(System.in));

        Shop shop = new Shop("MyShop.db");

        shop.createTable();

        try {
            shop.createGoods(1000);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        while(true){
            String msg = null;

            System.out.println("\nPlease input the command");

            try {
                msg = keyboard.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] data = msg.split("\\s");
            if (data[0].startsWith("/")){

                if (data[0].startsWith("/cost")){ // /cost title
                    shop.getCostByTitle(data[1]);

                } else if (data[0].startsWith("/changecost")){ // /changecost title newcost
                    shop.changeCostByTitle(data[1], parseInt(data[2]));
                    shop.getCostByTitle(data[1]);

                } else if (data[0].startsWith("/goodsinrange")){ // //goodsinrange fromcost tocost
                    List<Good> goodsInRange = shop.getGoodsByRange(parseInt(data[1]), parseInt(data[2]));
                    for (Good good : goodsInRange) {
                        System.out.println(good);
                    }

                } else if (data[0].startsWith("/end"))
                    break;
            } else {
                System.out.println("Input the correct command");;
            }
        }


//        shop.getCostByTitle("good1");
//        shop.getCostByTitle("good45");

//        shop.changeCostByTitle("good1", 555);

//        List<Good> allGoods = shop.getAllGoods();
//        for (Good good : allGoods) {
//            System.out.println(good);
//        }

//        System.out.println()
//
//        List<Good> goodsInRange = shop.getGoodsByRange(10, 16);
//        for (Good good : goodsInRange) {
//            System.out.println(good);
//        }

    }
}
