package com.pojo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


@Entity
@Table(name="employee_inf")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE) //该实体启用缓存，采用读写的缓存策略
/*
 * 使用整个类层次对应一个表的策略，即 使用@DiscriminatorColumn 该字段修饰该实体类，则该类的所有子类同在一张数据表中
 */
@DiscriminatorColumn(name="emp_type",discriminatorType=DiscriminatorType.INTEGER)
/*
 * 配合 @DiscriminatorColumn 使用，根据discriminatorType选定的类型，确定value 的值
 */
@DiscriminatorValue(value="1")
public class Employee implements Serializable{
	private static final long serialVersionUID = 1L;
	
//	标识属性
	@Id	//标识主键
	@Column(name="emp_id") //标识字段名称
	@GeneratedValue(strategy=GenerationType.IDENTITY)//主键生成策略，自增长主键
	private Integer id;
//	员工姓名
	@Column(name="emp_name",nullable=false,length=50,unique=true)
	private String name;
//	员工密码
	@Column(name="emp_pass", nullable=false, length=50)
	private String pass;
//	员工工资
	@Column(name="emp_salary", nullable=false)
	private double salary;
//	员工对应的经理
	@ManyToOne(targetEntity=Manager.class)
	@JoinColumn(name="mgr_id")//映射底层的外键列，无连接表，直接使用外键关联策略来处理这种关联映射
	private Manager manager;
//	员工对应的出勤记录
	@OneToMany(targetEntity=Attend.class,mappedBy="employee")
	private Set<Attend> attends = new HashSet<>();
//	员工对应的工资支付记录
	@OneToMany(targetEntity=Payment.class,mappedBy="employee")
	private Set<Payment> payments = new HashSet<>();
	
	
	public Employee() {
		super();
	}
	public Employee(Integer id, String name, String pass, double salary,
		Manager manager, Set<Attend> attends, Set<Payment> payments) {
	super();
	this.id = id;
	this.name = name;
	this.pass = pass;
	this.salary = salary;
	this.manager = manager;
	this.attends = attends;
	this.payments = payments;
}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Manager getManager() {
		return manager;
	}
	public void setManager(Manager manager) {
		this.manager = manager;
	}
	public Set<Attend> getAttends() {
		return attends;
	}
	public void setAttends(Set<Attend> attends) {
		this.attends = attends;
	}
	public Set<Payment> getPayments() {
		return payments;
	}
	public void setPayments(Set<Payment> payments) {
		this.payments = payments;
	}
	
	/**
	 * 根据name、pass来重写hashCode()方法
	 * hashcode方法只有在集合中用到
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pass == null) ? 0 : pass.hashCode());
		return result;
	}
	/**
	 * 根据name、pass来重写equals()方法，只要name、pass相同的员工即认为相等
	 * equals方法用于比较对象的内容是否相等（覆盖以后）
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Employee other = (Employee) obj;
		if (name == null)
		{
			if (other.name != null) return false;
		}
		else if (!name.equals(other.name)) return false;
		if (pass == null)
		{
			if (other.pass != null) return false;
		}
		else if (!pass.equals(other.pass)) return false;
		return true;
	}
	
	
}
