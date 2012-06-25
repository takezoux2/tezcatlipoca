package com.geishatokyo.tezcatlipoca.reflect.template

import com.geishatokyo.tezcatlipoca.template._
import collection.mutable.{SynchronizedMap, HashMap}
import java.lang.reflect.Type

/**
 * Created with IntelliJ IDEA.
 * User: takezou
 * Date: 12/06/22
 * Time: 2:18
 * To change this template use File | Settings | File Templates.
 */

class TemplateRegistry {

  private val templates = new HashMap[Type,Template[_]]() with SynchronizedMap[Type,Template[_]]

  def register(clazz : Type, template : Template[_]) = {
    templates +=( clazz -> template)
  }

  def get(clazz : Type) = {
    templates.get(clazz)
  }

}

object TemplateRegistry{
  def createDefault() = {
    val tr = new TemplateRegistry()
    tr.register(classOf[Int],new IntTemplate)
    tr.register(classOf[Long],new LongTemplate)
    tr.register(classOf[Short],new ShortTemplate)
    tr.register(classOf[Byte],new ByteTemplate)
    tr.register(classOf[Double],new DoubleTemplate)
    tr.register(classOf[Float],new FloatTemplate)
    tr.register(classOf[String],new StringTemplate)
    tr.register(classOf[Boolean],new BoolTemplate)
    tr
  }
}
