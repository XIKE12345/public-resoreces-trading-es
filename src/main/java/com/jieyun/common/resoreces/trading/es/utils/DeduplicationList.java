package com.jieyun.common.resoreces.trading.es.utils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * List 去重
 *
 * @author huike
 * @date 2019-12-6
 */
public class DeduplicationList {
    private static <T> List<T> getDuplicateElements(List<T> list) {
        return list.stream()
                /**获得元素出现频率的 Map，键为元素，值为元素出现的次数*/
                .collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b))
                // Set<Entry>转换为Stream<Entry>
                .entrySet().stream()
                // 过滤出元素出现次数大于 1 的 entry
                .filter(entry -> entry.getValue() > 1)
                // 获得 entry 的键（重复元素）对应的 Stream
                .map(entry -> entry.getKey())
                // 转化为 List
                .collect(Collectors.toList());
    }
}
