package com.geishatokyo.tezcatlipoca.reflect

import com.geishatokyo.tezcatlipoca.Mirror
import com.geishatokyo.tezcatlipoca.util.PropFinder
import template.TemplateRegistry

/**
 * Mirror using reflection
 * User: takezou
 * Date: 12/06/22
 * Time: 1:58
 */

class ReflectionMirror(templateRegistry : TemplateRegistry) extends Mirror {

  def reflect(from: AnyRef, to: AnyRef) = {
    val fromProps = PropFinder.getProps(from.getClass)
    val toProps = PropFinder.getProps(to.getClass)
    var count = 0
    fromProps.foreach({
      case (name,p) => toProps.get(name) match{
        case Some(toProp) => {
          if (toProp.propType == p.propType){
            count += 1
            toProp.setter.invoke(to,p.getter.invoke(from))
          }
        }
        case None =>
      }
    })
    count
  }

  def reflect(from: AnyRef) = {
    val props = PropFinder.getProps(from.getClass)
    props.map({
      case (name,p) => {
        p.name -> p.getter.invoke(from).toString
      }
    }).toMap
  }

  def reflectFromMap(from: Map[String, String], to: AnyRef) = {
    val props = PropFinder.getProps(to.getClass)
    var count = 0
    props.foreach({
      case (name,prop) if from.contains(name) => {
        templateRegistry.get(prop.propType) match{
          case Some(template) => {
            count += 1
            prop.setter.invoke(to,template.fromString(from(name)).asInstanceOf[AnyRef])
          }
          case None =>
        }
      }
      case _ =>
    })
    count
  }
}
