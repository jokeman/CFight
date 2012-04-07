import ru.ifmo.enf.micelius.core.Bolet;
import ru.ifmo.enf.micelius.core.InnerRequest;
import ru.ifmo.enf.micelius.core.InnerResponse;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: Dead_off
 * Date: 29.03.12
 * Time: 21:35
 * To change this template use File | Settings | File Templates.
 */
public class BattleBolet implements Bolet {
    HashMap<Integer, BattleShip> map = new HashMap<Integer, BattleShip>();
    @Override
    public void process(InnerRequest innerRequest, InnerResponse innerResponse) {
        final int x = Integer.parseInt(innerRequest.getParameter("x"));
        final int y = Integer.parseInt(innerRequest.getParameter("y"));
        final int id = Integer.parseInt(innerRequest.getParameter("id"));
        final String firstLaunch = innerRequest.getParameter("firstLaunch");
        if(firstLaunch.equals("true")){
            map.put(id,  new BattleShip());
        }
        System.out.println(firstLaunch);
        map.get(id).shot(x,y);
        // innerResponse.addObject("computerShips", A.myShips());
        innerResponse.add("computerShipsForPlayer", map.get(id).myShipsForEnemy());
        innerResponse.add("playerShips", map.get(id).enemyShips());
        innerResponse.add("isComputerWin", map.get(id).isWin(map.get(id).enemyShips())+"");
        innerResponse.add("isHumanWin", map.get(id).isWin(map.get(id).myShips())+"");


        //  int[][][] fields = innerRequest.getParameter("fields");
        //innerResponse.addObject("result", );
    }
}
