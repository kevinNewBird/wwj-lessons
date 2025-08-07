package com.concurreny.base.c2_strategy;

/**
 * @Author:zhao.song
 * @Date:2019/10/16 13:52
 * @Description:
 */
public class TaxCalaculator {

    private Double salary;
    private Double bonus;

    private CalculatorStrategy calCulatorStrategy;

    public TaxCalaculator(Double salary, Double bonus) {
        this.salary = salary;
        this.bonus = bonus;
    }

    protected Double calcTax() {
        return calCulatorStrategy.calculate(salary, bonus);
    }

    public Double calaculate() {
        return this.calcTax();
    }


    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    /**
     * Description: 策略模式 <BR>
     *
     * @param calCulatorStrategy:
     * @return void
     * @author zhao.song    2020/5/7 14:21
     */
    public void setCalCulatorStrategy(CalculatorStrategy calCulatorStrategy) {
        this.calCulatorStrategy = calCulatorStrategy;
    }
}
