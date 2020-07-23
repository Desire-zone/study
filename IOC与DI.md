IOC即**控制反转**(Inversion of Control)，是**依赖倒置原则**(Dependency Inversion Principle，软件设计五大原则之一)一种代码设计的思路。具体实现方式为**依赖注入**(Dependency Injection, DI)。三者具体关系为：

依赖倒置原则 --> 控制反转(设计思路) --> 依赖注入(具体实现思路)

**何为依赖倒置原则？Robert C. Martin给出的定义如下：**

> 高层模块不应该依赖于底层模块，二者都应该依赖于抽象
>
> 抽象不应该依赖于细节，细节应该依赖于抽象

术语较为晦涩难懂，举个栗子：

![preview](/Users/zhiwen/study/image/汽车依赖轮胎.png)

当设计汽车时，先设计轮胎，然后底盘依赖设计好的轮胎进行设计，直至设计好整个汽车。而此时当轮胎需要变化时，地盘需要跟着变化进而适应轮胎的变化，进而整个汽车都需要变化。

我们换一种思路

![preview](/Users/zhiwen/study/image/轮胎依赖汽车.png)

如果先设计整个汽车概况，然后根据汽车概况设计车身，然后设计地盘、轮胎，此时整个依赖链条发生了反转，当轮胎发生变化时，只需要重新设计轮胎即可。**这就是依赖反转的过程！**

**下面用代码来说明:**

实体类：

```java
//汽车类(依赖车架)
class Car {
  private Framework framework;//车架
  Car() {this.framework = new framework();}
  public void run(){...}
}

//车架类(依赖底盘)
class Framework{
  private Bottom bottom;
  Framework(){this.bottom = new Bottom();}
}

//底盘类(依赖轮胎)
class Bottom{
  private Tire tire;
  Bottom(){this.tire = new Tire();}
}

//轮胎类
class Tire{
  private int size;
  Tire(){this.size = 10;}
}
```

业务类：

```java
//实例化一个汽车对象, 并执行run方法
Car car = new Car();
car.run();
```

此时依赖关系为Car -> Framework -> Bottom ->Tire，若此时业务需求发生了变化，需自定义轮胎尺寸，即Tire类size属性根据参数初始化。则上述代码需要修改。

```java
//首先修改轮胎类
Class Tire{
  private int size;
  Tire(int size){this.size = size;}
}

//然后需要修改底盘类(依赖轮胎)
Class Bottom{
  private Tire tire;
  Bottom(int tireSize){this.tire = new Tire(tireSize);}
}

//进而修改车架类(依赖底盘)
Class Framework{
  private Bottom bottom;
  Framework(int tireSize){this.bottom = new Bottom(tireSize);}
}

//最后修改汽车类(依赖车架)
Class Car {
  private Framework framework;//车架
  Car(int tireSize) {this.framework = new Framework(tireSize);}
  public void run(){...}
}

//实例化一个轮胎尺寸为30的汽车对象, 并执行run方法
Car car = new Car(30);
car.run();
```

这种高层依赖底层的依赖关系，底层模块发生变化，高层模块都要随之变化，可谓牵一发而动全身，维护成本极高。违反了依赖导致原则。要遵守依赖导致原则，达到控制反转，通常使用依赖注入的方式来实现，具体有**构造方法注入**、**setter方法注入**、**接口注入**三种实现方法，构造方法注入、setter方法注入适用于链式依赖关系（如本例）。接口注入适用于星型依赖关系。下面尝试使用构造方法注入、setter方法注入对本例进行改造。

- **构造方法注入**

实体类改造

```java
//汽车类(构造时需注入Framework属性)
Class Car {
  private Framework framework;//车架
  Car(Framework framework) {this.framework = framework;}
  public void run(){...}
}

//车架类(构造时需注入Bottom属性)
Class Framework{
  private Bottom bottom;
  Framework(Bottom bottom){this.bottom = bottom;}
}

//底盘类(构造时需注入tire属性)
Class Bottom{
  private Tire tire;
  Bottom(Tire tire){this.tire = tire;}
}

//轮胎类
Class Tire{
  private int size;
  Tire(){this.size = 10;}
}
```

业务类

```java
/*
	构造Car对象需要注入Framework对象，构造Framework对象需要注入Bottom对象，构造Bottom对象需要Tire对象，所以要先构造底层Tire对象，逐步向高层注入。进而实现了控制反转。
*/
Tire tire = new Tire(40);
Bottom bottom = new Bottom(tire);
Framework framework = new Framework(bottom);
Car car = new Car(framework);
car.run();
```

- **Setter方法注入**

实体类改造

```java
//汽车类(添加framework属性的setter方法)
Class Car {
  private Framework framework;//车架
  public void setFramework(Framework framework) {this.framework = framework;}
  public void run(){...}
}

//车架类(添加bottom属性的setter方法)
Class Framework{
  private Bottom bottom;
  public void setBottom(Bottom bottom){this.bottom = bottom;}
}

//底盘类(添加Tire属性的setter方法)
Class Bottom{
  private Tire tire;
  public void setTire(Tire tire){this.tire = tire;}
}

//轮胎类
Class Tire{
  private int size;
  Tire(){this.size = 10;}
}
```

业务类

```java
//setter注入与构造器方法类似
Tire tire = new Tire(40);
Bottom bottom = new Bottom();
bottom.setTire(tire);
Framework framework = new Framework();
framework.setBottom(bottom);
Car car = new Car();
car.setFramework(framework);
car.run();
```

**下面举一个接口注入的例子**

```java
//person类
class Person{
  private BenzCar myBenz;
  public setMyBenz(BenzCar myBenz) {this.myBenz = myBenz;}
  public void driveBenz() {
    this.myBenz.run()
  }
}

//BenzCar类
class BenzCar{
  private String name = "Benz";
  public void run() {
    System.Out.Println(this.name + " is running");
  }
}

//人驾驶奔驰
Person ps = new Person();
ps.setMyBenz(new BenzCar())
ps.driveBenz()
```

上述例子Person类依赖BenzCar类，当需求增加人驾驶宝马车，则不仅要添加宝马类，还要对Person类修改，上述代码则变为：

```java
//person类
class Person{
  private BenzCar myBenz;
  private BMWCar myBMW;
  public setMyBenz(BenzCar myBenz) {this.myBenz = myBenz;}
  public setMyBMW(BMWCar myBMW) {this.myBMW = myBMW;}
  public void driveBenz () {
    this.myBenz.run()
  }
  public void driveBMW () {
    this.myBMW.run()
  }
}

//BenzCar类
class BenzCar{
  private String name = "Benz";
  public void run() {
    System.Out.Println(this.name + " is running");
  }
}

//BMWCar类
class BMWCar{
  private String name = "BMW";
  public void run() {
    System.out.println(this.name + " is running");
  }
}

//人驾驶宝马
Person ps = new Person();
ps.setMyBMW(new BMWCar())
ps.driveBMW()
```

上述设计每次新增一个汽车类型，都要重新修改Person代码，违反了开闭原则、依赖倒置原则。下面使用**接口注入**方法进行重构：

```java
//定义Car接口
public interface Car{
  public void run() {}
}

//BenzCar类实现Car接口
class BenzCar implements Car {
  private String name;
  public BenzCar(String name) {this.name = name;}
  @Override
  public void run() {
   System.out.println(this.name + "is running"); 
  }
}

//BMWCar类实现Car接口
class BMWCar implements Car {
  private String name;
  public BMWCar(String name) {this.name = name;}
  @Override
  public void run() {
   System.out.println(this.name + "is running"); 
  }
}

//Person类针对接口编程
class Person {
   private Car car;
   public setCar(Car car) {this.car = car;}
   public void drive() {
    this.car.run()
  }
}

//业务调用
Person ps1 = new Person();
Person ps2 = new Person();
//人驾驶奔驰
Car benzCar = new BenzCar(myBenz);
ps1.setCar(benzCar);
ps1.drive();
//人驾驶宝马
Car bmwCar = new BMWCar(myBMW);
ps2.setCar(bmwCar);
ps2.drive();
```

Person类针对Car接口进行编程，当业务增加了新的车型时，只需要对Car接口进行实现即可，无需修改原有代码，减少了维护的成本。

