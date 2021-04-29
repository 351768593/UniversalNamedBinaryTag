# 通用二进制命名标签 Universal Named Binary Tag

## 概述

是的, 看名字应该能发现这是对Minecraft的数据格式**NBT**的一个效仿

> 但是这个库并**不**处理Minecraft的NBT数据文件

设计初衷为

* 将内存数据转换为紧凑的二进制数据用于储存或传输
* 可自行扩展

## 改动

### 0.3.0

* 为`TagCompact`增加更多接口方法

### 0.2.0

* 新增数据类型支持
  * byte[]
  * int[]
  * long[]
  * float
  * float[]
  * double
  * double[]
  * java.util.Date
  * java.math.BigInteger
  * java.math.BigDecimal
* 为`TagCompact`增加更多接口方法

### 0.1.0

* 初次提交, 目前支持序列化的数据类型有
  * int
  * long
  * String
  * UNBT
