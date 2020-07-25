### 什么是索引

索引是一种**有序**的数据结构。

### 索引数据结构

- **二叉树(二叉排序树)**

  极端情况退化为链表

- **红黑树(二叉平衡树)**

  树的高度高(查找层数高)

- **Hash表**

- **B-Tree**

对于树形索引结构，查找次数等于树的高度。通常高度越低查找速度更快。

B-Tree相比于二叉排序树（或二叉平衡树）的优势，每一个节点尽可能放多个索引（这里在节点内部为内存中查找，查找时间可以忽略），这样下一层可以有更多的节点，使得树的高度越小。

**结论：存储相同的数据，每个节点存放的数据越多，整个树的高度越低，查找次数越少。**

![B树](/Users/zhiwen/study/image/B树.png)

而Mysql真正选取的是一种优化的B-Tree —> B+Tree。

B+树与B树的区别：

![image-20200724093626358](/Users/zhiwen/study/image/B+树.png)

1. B+树只有叶子结点存放数据，非叶子结点只存索引。

   好处：非叶子结点只存在索引，会使每个非叶子结点存储更多的索引值，整个树的高度

2. B+树叶子结点之间存在指针，可以实现顺序访问。

### Mysql索引

Mysql存储引擎常用InnoDB、MyISAM、是==针对表==而言的

#### MyISAM

索引结构：非聚集索引，主键索引与非主键结构相同

![image-20200725103605551](/Users/zhiwen/study/image/非聚集索引.png)

MyISAM表文件

- ${table_name}.frm 表结构文件

- ${table_name}.myd 表数据文件

- ${table_name}.myi 表索引文件

  

#### InnoDB

索引结构：主键为聚集索引、非主键为非聚集索引（结构与MyISAM相同）

- 主键(聚集)索引：

![image-20200725104217001](/Users/zhiwen/study/image/聚集索引.png)

- 非主键(索引)：

![image-20200725110338861](/Users/zhiwen/study/image/InnoDB非主键索引.png)

​	非主键索引域存放主键值，查找时还需回表至聚集索引中(利用主键)查找数据。

InnoDB表文件

- ${table_name}.frm 表结构文件(高版本Mysql移除？)
- ${table_name}.ibd 聚集索引

#### 联合索引

![image-20200725111618600](/Users/zhiwen/study/image/联合索引.png)

```sql
select * from employees where name = 'Bill' and age = 31;	#走索引
select * from employees where age = '30' and position = 'dev';	#不走索引
select * from employees where position = 'manager;'	#不走索引
select * from employees where age = '12' and position = 'manager' and name = 'Bill';	#走索引
```



### 问题

1. 为什么InnoDB推荐建一个整型自增主键？	

   答：索引查找过程需要比较大小，整型比较大小快；便于B+树的维护(插入)。

2. 为什么Mysql不推荐用Hash索引(Mysql可选)？

   答：不支持范围查找

3. 为什么InnoDB非主键索引不用聚集索引呢(索引中存放数据)？

   答：一致性和节省空间

