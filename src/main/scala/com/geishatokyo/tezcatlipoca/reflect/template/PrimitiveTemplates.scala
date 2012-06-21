package com.geishatokyo.tezcatlipoca.template

/**
 * Created with IntelliJ IDEA.
 * User: takezou
 * Date: 12/06/22
 * Time: 2:08
 * To change this template use File | Settings | File Templates.
 */

class IntTemplate  extends Template[Int]{
  def toString(obj: Int) = obj.toString

  def fromString(s: String) = s.toInt
}
class LongTemplate  extends Template[Long]{
  def toString(obj: Long) = obj.toString

  def fromString(s: String) = s.toLong
}
class ByteTemplate  extends Template[Byte]{
  def toString(obj: Byte) = obj.toString

  def fromString(s: String) = s.toByte
}
class ShortTemplate  extends Template[Short]{
  def toString(obj: Short) = obj.toString

  def fromString(s: String) = s.toShort
}

class DoubleTemplate extends Template[Double]{
  def toString(obj: Double) = obj.toString

  def fromString(s: String) = s.toDouble
}

class FloatTemplate extends Template[Float]{
  def toString(obj: Float) = obj.toString

  def fromString(s: String) = s.toFloat
}

class BoolTemplate extends Template[Boolean]{
  def toString(obj: Boolean) = obj.toString

  def fromString(s: String) = s.toBoolean
}

class StringTemplate extends Template[String]{
  def toString(obj: String) = obj

  def fromString(s: String) = s
}
