```java
set(String s, List l, long, l)
doNothing().when(redisDao).set(Mockito.anyString(), Mockito.anyObject(), Mockito.anyLong(  //不报异常
  
doNothing().when(redisDao).set(Mockito.anyString(), Mockito.anyList(), Mockito.anyLong());   //报异常
```

```
Tag tag = new Tag();
tag.setIsVisible(true);
tags.add(tag);
Mockito.when(redisDao.get(Mockito.anyString())).thenReturn(tags);
tags = null;
Mockito.when(redisDao.get(Mockito.anyString())).thenReturn(tags);
```

```java
Mockito.when(shareTemplateMapper.selectById(1)).thenThrow(new NullPointerException());
Mockito.when(shareTemplateMapper.selectById(1)).thenReturn(shareTemplate);//报错
Mockito.when(shareTemplateMapper.selectById(2)).thenReturn(shareTemplate);//不报错

Mockito.when(shareTemplateMapper.updateById(shareTemplate)).thenReturn(1);
Mockito.when(shareTemplateMapper.updateById(shareTemplate)).thenThrow(new NullPointerException());//不报错


```





