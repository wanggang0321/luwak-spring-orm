package com.luwak.orm.framework;

/**
 * 抽象类里面可以写一些默认的功能
 * 但是不能被实例化，被实例化之前必须给我把动态参数配置好
 * 通过子类继承父类，然后new子类，就会先new父类，子类会把配置信息传给父类，从而让父类功能生效
 */
public abstract class BaseDaoSupport {



}
