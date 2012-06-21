package com.geishatokyo.tezcatlipoca.util

import collection.mutable.HashMap
import collection.mutable.SynchronizedMap
import java.lang.reflect.Modifier

/**
 * Created with IntelliJ IDEA.
 * User: takezou
 * Date: 12/06/22
 * Time: 1:43
 * To change this template use File | Settings | File Templates.
 */

object PropFinder {


  val propRepo = new HashMap[Class[_],Map[String,Prop]]() with SynchronizedMap[Class[_],Map[String,Prop]]

  def getProps(clazz : Class[_]) = {
    propRepo.getOrElseUpdate(clazz,{
      listUpProps(clazz)
    })
  }

  def listUpProps( clazz : Class[_]) = {
    val methods = clazz.getMethods
    val maybeSetters = methods.withFilter(m => {
      Modifier.isPublic(m.getModifiers) &&
      (m.getName.endsWith("_$eq") && m.getParameterTypes.length == 1)
    }).map(m => {
      val name = m.getName
      (name.substring(0,name.length - 4)+ ":" + m.getParameterTypes()(0).getName) -> m
    }).toMap

    methods.withFilter(m => {
      Modifier.isPublic(m.getModifiers) &&
      m.getParameterTypes().length == 0 &&
      maybeSetters.contains(m.getName + ":" + m.getReturnType.getName)
    }).map( m => {
      m.getName -> Prop(m,maybeSetters(m.getName + ":" + m.getReturnType.getName))
    }).toMap

  }

}
