import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: Dead_off
 * Date: 03.03.12
 * Time: 18:32
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {
//        for (int k = 0; k < 20; k++) {
//        System.out.println(new Random().nextInt(3));
//        }
        BattleShip A = new BattleShip();
        A.printField();
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("вводите 2 числа, куда стреляете через точку с запяток (;)");
            String[] arr = s.next().split(";");
            //System.out.println(Arrays.toString(arr));
            if (A.shot(Integer.parseInt(arr[0]), Integer.parseInt(arr[1])) == null) {
                return;
            }
            A.printField();
        }
    }
}
