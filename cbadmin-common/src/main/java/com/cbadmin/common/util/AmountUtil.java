package com.cbadmin.common.util;


import com.cbadmin.common.constant.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AmountUtil {

    private AmountUtil() {
    }


    /**
     * 精度
     */
    public static double PRECISION = 1000000;

    /**
     * 汇率精度 (万分之)
     */
    public static double PRECISION_EXCHANGERATE = 10000;


    /**
     * 获取原金额
     * @param rateAmount
     * @param rate
     * @return
     */
    public static long rateOriginAmount(long rateAmount, long rate) {
        return BigDecimal.valueOf(rateAmount)
                .multiply(BigDecimal.valueOf(PRECISION_EXCHANGERATE))
                .divide(BigDecimal.valueOf(rate), 0, RoundingMode.HALF_UP)
                .longValue();
    }

    /**
     * 费率金额
     * @param amount
     * @param rate
     * @return
     */
    public static long rateAmount(long amount, long rate) {
        return BigDecimal.valueOf(amount)
                .multiply(BigDecimal.valueOf(rate))
                .divide(BigDecimal.valueOf(PRECISION_EXCHANGERATE), 0, RoundingMode.HALF_UP)
                .longValue();
    }

    /**
     * 汇率转换为正常十进制
     * @param exchangeRate
     * @return
     */
    public static double exchangeRateToNormalDecimal(long exchangeRate) {
        return new BigDecimal((exchangeRate / PRECISION_EXCHANGERATE)).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 转换为高精度
     * @param exchangeRate
     * @return
     */
    public static long exchangeRateToPrecision(double exchangeRate) {
        return (long) (exchangeRate * PRECISION_EXCHANGERATE);
    }

    /**
     * 转换为正常十进制
     * @param amount
     * @param precision
     * @return
     */
    public static double amountToNormalDecimal(long amount, int precision) {
        long preval = (long) Math.pow(10, precision);
        double damount = amount;
        return Math.ceil(damount / (PRECISION / preval)) / preval;
    }

    /**
     * 转换为正常十进制
     * @param amount
     * @return
     */
    public static double amountToNormalDecimal(long amount) {
        long preval = (long) Math.pow(10, Constants.AMOUNT_PRECISION);
        double damount = amount;
        return Math.ceil(damount / (PRECISION / preval)) / preval;
    }

    /**
     * 转换为高精度
     * @param amount
     * @return
     */
    public static long amountToPrecision(double amount) {
        return (long) (amount * PRECISION);
    }


}
