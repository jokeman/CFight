import ru.ifmo.enf.micelius.core.Bolet;
import ru.ifmo.enf.micelius.core.InnerRequest;
import ru.ifmo.enf.micelius.core.InnerResponse;

/**
 * Created by IntelliJ IDEA.
 * User: Dead_off
 * Date: 29.03.12
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
public class BattleBolet implements Bolet {
    @Override
    public void process(InnerRequest innerRequest, InnerResponse innerResponse) {
        final int x = Integer.parseInt(innerRequest.getParameter("X"));
        final int y = Integer.parseInt(innerRequest.getParameter("Y"));
        final String myS = innerRequest.getParameter("myS");
        final String enemyS = innerRequest.getParameter("enemyS");
        String[] temp = myS.split("!");
        String[][] myShipsSt = new String[10][10];
        String[][] enemyShipsSt = new String[10][10];
        String[] tempE = enemyS.split("!");
        for (int i = 0; i < 10; i++) {
            myShipsSt[i] = temp[i].split(";");
            enemyShipsSt[i] = tempE[i].split(";");
        }
        int[][] myShips = new int[10][10];
        int[][] enemyShips = new int[10][10];
        for (int i = 0; i < myShips.length; i++) {
            for (int j = 0; j < myShips.length; j++) {
                myShips[i][j] = Integer.parseInt(myShipsSt[i][j]);
                enemyShips[i][j] = Integer.parseInt(enemyShipsSt[i][j]);
            }
        }
        BattleShip A = new BattleShip(myShips, enemyShips);
        innerResponse.addObject("result", A.shot(x, y));
    }
}
