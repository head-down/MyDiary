package cqwu.edu.diary.common.constants;

import cqwu.edu.diary.common.utils.MapUtil;

import java.util.*;

public final class ServiceConstants {

    private ServiceConstants() {

    }

    /**
     * 分页号,默认是1
     */
    public static final int PAGE_NUM = 1;
    /**
     * 分页大小,默认是10
     */
    public static final int PAGE_SIZE = 10;

    public static final String[] PROFILE_SUFFIX_ARRAY = new String[]{"png","jpeg","jpg","gif"};

    /**
     * 文件上传类型：1、头像
     */
    public static final int FILE_PROFILE_TYPE = 1;

}
