package cqwu.edu.diary.common.utils;

import cqwu.edu.diary.common.constants.ServiceResponseCode;
import cqwu.edu.diary.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

/**
 * 雪花算法生成ID
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 * 暂时使用的递增主键，可以了解后改成这种
 *
 */
@Slf4j
public final class SnowflakeDistributeId {

    //  开始时间戳 2022-11-29
    private static final long STARTTIMEMILLIS = 1669686482796L;

    //  机器ID所占位数
    private static final long WORKERIDBITS = 5L;
    //  数据标志ID所占位数
    private static final long DATACENTERIDBITS = 5L;
    //  支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
    private static final long MAXWORKERID = ~(-1L << WORKERIDBITS);
    //  支持最大数据标志ID，结果是31
    private static final long MAXDATACENTERID = ~(-1L << DATACENTERIDBITS);
    //  序列在ID中占的位数
    private static final long SEQUENCEBITS = 12L;
    //  机器ID向左移12位
    private static final long WORKERIDSHIFT = SEQUENCEBITS;
    //  数据标志向左移17位
    private static final long DATACENTERIDSHIFT = SEQUENCEBITS + WORKERIDSHIFT;
    //  时间戳向左移22位
    private static final long TIMESTAMPLEFTSHIFT = SEQUENCEBITS + WORKERIDBITS + DATACENTERIDBITS;
    //  生成序列的掩码
    private static final long SEQUENCEMASK = ~(-1L << SEQUENCEBITS);
    //  工作机器ID（0~31）
    private long workerId;
    //  数据中心ID（0~31）
    private long datacenterId;
    //  毫秒内序列（0~4095）
    private long sequence = 0L;
    //  上次生成ID的时间戳
    private long lastTimestamp = -1L;

    private SnowflakeDistributeId() {

    }

    private static SnowflakeDistributeId SNOWFLAKEDISTRIBUTEID = new SnowflakeDistributeId(0, 0);

    private SnowflakeDistributeId(long workerId, long datacenterId) {
        if (workerId > MAXWORKERID || workerId < 0) {
            throw new IllegalArgumentException("");
        }
        if (datacenterId > MAXDATACENTERID || datacenterId < 0) {
            throw new IllegalArgumentException("");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    public static SnowflakeDistributeId getInstance() {
        if (SNOWFLAKEDISTRIBUTEID == null) {
            SNOWFLAKEDISTRIBUTEID = new SnowflakeDistributeId(0, 0);
        }
        return SNOWFLAKEDISTRIBUTEID;
    }

    /**
     * 获取下一个ID
     *
     * @return {@link long}
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        //  当前时间小于上一次生成ID的时间戳，说明系统时钟回退过，需要抛出异常
        if (timestamp < lastTimestamp) {
            throw new BusinessException(ServiceResponseCode.SERVICE_INNER_ERROR, "当前时间戳：【" + timestamp + "】系统时钟有回退，请设置系统时间!");
        }
        //  如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & SEQUENCEMASK;
            //  毫秒内序列溢出
            if (sequence == 0) {
                //  阻塞到下一个毫秒获取新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            //  时间戳改变，毫秒内序列重置
            sequence = 0L;
        }
        //  上次生成ID的时间戳
        lastTimestamp = timestamp;
        //  移位并通过或运算拼到一起组成64位ID
        return ((timestamp - STARTTIMEMILLIS) << TIMESTAMPLEFTSHIFT)
                | (datacenterId << DATACENTERIDSHIFT)
                | (workerId << WORKERIDSHIFT)
                | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获取到新的时间戳
     *
     * @param lastTimestamp
     * @return {@link long}
     */
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回当前时间毫秒数
     *
     * @return {@link long}
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

}
