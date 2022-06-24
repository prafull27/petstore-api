package com.petstore.api.listeners;

import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import static org.apache.logging.log4j.LogManager.getLogger;

public class TestListener implements ITestListener {

    private static final Logger LOG = getLogger(TestListener.class);

    @Override
    public void onFinish(final ITestContext context) {
        LOG.info("Test execution finished for test [{}].", context.getName());
    }

    @Override
    public void onStart(final ITestContext context) {
        LOG.info("Test execution started for test [{}]...", context.getName());
    }

    @Override
    public void onTestFailure(final ITestResult result) {
        LOG.error("(-) Test [{}] failed...", result.getName());
        LOG.error(result.getThrowable());
    }

    @Override
    public void onTestSkipped(final ITestResult result) {
        LOG.warn("(*) Test [{}] skipped...", result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LOG.error("Test [{}] failed with percent...error [{}]", result.getName(), result.getThrowable());
    }

    @Override
    public void onTestStart(final ITestResult result) {
        LOG.info("====== Test execution for test [{}] started ======", result.getName());
    }

    @Override
    public void onTestSuccess(final ITestResult result) {
        LOG.info("(+) Test [{}] passed...", result.getName());
    }
}