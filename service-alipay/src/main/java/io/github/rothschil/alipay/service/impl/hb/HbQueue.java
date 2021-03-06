package io.github.rothschil.alipay.service.impl.hb;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import io.github.rothschil.alipay.model.hb.SysTradeInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/** 保存交易数据的队列，添加队列每次只加入1条交易，读取队列每次尽量读满30条交易
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @description //TODO
 * 
 * @date 2018/4/23 - 10:14
 * @since 1.0.0
 */
public class HbQueue {
    private static Log log = LogFactory.getLog(HbQueue.class);

    /**
     * 最多同时保存300条交易记录
      */
    public static final int QUEUE_SIZE = 300;
    private static final BlockingQueue<SysTradeInfo> queue = new ArrayBlockingQueue<SysTradeInfo>(QUEUE_SIZE);

    public synchronized static void offer(SysTradeInfo info) {
        // blockingQueue不需要考虑队列满的情况，生产者会被阻塞直到队列被消耗
        if (info != null) {
            try {
                // 使用阻塞put
                queue.put(info);
            } catch (InterruptedException e) {
                log.warn("interrupted for tradeInfo:" + info);
                e.printStackTrace();
            }
        }
    }

    public synchronized static List<SysTradeInfo> poll() {
        if (queue.isEmpty()) {
            // 如果队列为空，则直接返回
            return null;
        }

        int size = 30;
        List<SysTradeInfo> tradeInfoList = new ArrayList<SysTradeInfo>(size);
        for (int i = 0; i < size; i++) {
            // 使用非阻塞poll
            SysTradeInfo info = queue.poll();
            if (info == null) {
                break;
            }
            tradeInfoList.add(info);
        }
        return tradeInfoList;
    }
}
