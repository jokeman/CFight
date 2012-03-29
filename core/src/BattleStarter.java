import ru.ifmo.enf.micelius.core.BoletsContainer;
import ru.ifmo.enf.micelius.server.BoletsRequestHandler;
import ru.ifmo.enf.micelius.server.ConfigKeys;
import ru.ifmo.enf.micelius.server.Server;

import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: Dead_off
 * Date: 29.03.12
 * Time: 21:37
 * To change this template use File | Settings | File Templates.
 */
public class BattleStarter {
    public static void main(String[] args) {
        final Properties configs = new Properties();
        configs.put(ConfigKeys.PORT, "8018");
        configs.put(ConfigKeys.WORK_DIR, "C:\\Users\\Dead_off\\IdeaProjects\\CFight\\core\\src");
        configs.put(ConfigKeys.MAX_THREADS, "10");
        final BoletsContainer boletsContainer = new BoletsContainer();
        boletsContainer.add("BattleBolet", new BattleBolet());
        final BoletsRequestHandler boletsRequestHandler = new BoletsRequestHandler(boletsContainer);
        new Server(configs, boletsRequestHandler);
    }
}
