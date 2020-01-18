import org.redisson.Redisson;
import org.redisson.api.RPatternTopic;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.redisson.api.listener.StatusListener;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;

public class Main {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379");
        final RedissonClient redisson = Redisson.create(config);

        RTopic topic = redisson.getTopic("result");

        topic.addListener(String.class, new MessageListener<String>() {
            @Override
            public void onMessage(CharSequence channel, String msg) {
                System.out.println(msg);
            }
        });
        topic.addListener(new StatusListener() {
            @Override
            public void onSubscribe(String channel) {
                System.out.println("subscribe:" + channel);
            }

            @Override
            public void onUnsubscribe(String channel) {
                System.out.println("unsubscribe:" + channel);
            }
        });

        //RPatternTopic messages = redisson.getPatternTopic("messages");
        RTopic test = redisson.getTopic("messages");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; ++i) {
            long ss = test.publish("ss" + i);
        }
        System.out.println(System.currentTimeMillis() - start);
        //redisson.shutdown();

    }
}
