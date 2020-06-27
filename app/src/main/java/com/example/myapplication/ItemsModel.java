package com.example.myapplication;

public class ItemsModel {
     String cname,cflag,tcase;
    String todayCases,deaths,todayDeaths,recovered,todayRecover,active,critical,population,tests;

    public ItemsModel() {
    }

    public ItemsModel(String cname, String cflag, String tcase, String todayCases, String deaths, String todayDeaths, String recovered, String todayRecover, String active, String critical, String population, String tests) {
        this.cname = cname;
        this.cflag = cflag;
        this.tcase = tcase;
        this.todayCases = todayCases;
        this.deaths = deaths;
        this.todayDeaths = todayDeaths;
        this.recovered = recovered;
        this.todayRecover = todayRecover;
        this.active = active;
        this.critical = critical;
        this.population = population;
        this.tests = tests;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCflag() {
        return cflag;
    }

    public void setCflag(String cflag) {
        this.cflag = cflag;
    }

    public String getTcase() {
        return tcase;
    }

    public void setTcase(String tcase) {
        this.tcase = tcase;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public void setTodayCases(String todayCases) {
        this.todayCases = todayCases;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public void setTodayDeaths(String todayDeaths) {
        this.todayDeaths = todayDeaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getTodayRecover() {
        return todayRecover;
    }

    public void setTodayRecover(String todayRecover) {
        this.todayRecover = todayRecover;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getCritical() {
        return critical;
    }

    public void setCritical(String critical) {
        this.critical = critical;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getTests() {
        return tests;
    }

    public void setTests(String tests) {
        this.tests = tests;
    }
}
