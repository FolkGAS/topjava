package ru.javawebinar.topjava.testrules;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DurationRule extends TestWatcher {
    public static final Logger LOG = LoggerFactory.getLogger(DurationRule.class);

    public static Map<String, Long> map = new HashMap<>();

    long start;
    long end;

    @Override
    protected void starting(Description description) {
        start = System.currentTimeMillis();
        super.starting(description);
    }

    @Override
    protected void finished(Description description) {
        super.finished(description);
        end = System.currentTimeMillis();
        LOG.info("{} {} ms", description.getMethodName(), end - start);
        map.put(description.getMethodName(), end - start);
    }
}
