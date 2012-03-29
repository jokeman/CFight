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
        //innerResponse.addObject("result", );
    }
}
