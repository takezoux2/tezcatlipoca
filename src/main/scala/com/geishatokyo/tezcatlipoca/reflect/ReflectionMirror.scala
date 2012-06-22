package com.geishatokyo.tezcatlipoca.reflect

import com.geishatokyo.tezcatlipoca.Mirror
import com.geishatokyo.tezcatlipoca.util.{Prop, PropFinder}
import template.TemplateRegistry
import com.geishatokyo.tezcatlipoca.template.Template

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
    props.collect({
      case HasTemplate(prop,template) => {
        prop.name -> template.toString( prop.getter.invoke(from))
      }
    }).toMap
  }

  object HasTemplate{

    def unapply(  p : (String,Prop) ) : Option[(Prop,Template[Any])] = {
      templateRegistry.get(p._2.propType).map( t => {
        p._2 -> t.asInstanceOf[Template[Any]]
      })
    }
  }


  def reflectFromMap(from: Map[String, String], to: AnyRef) = {
    val props = PropFinder.getProps(to.getClass)
    var count = 0
    props.foreach({
      case HasTemplate(prop,template) if from.contains(prop.name) => {
        val name = prop.name
        count += 1
        prop.setter.invoke(to,template.fromString(from(name)).asInstanceOf[AnyRef])
      }
      case _ =>
    })
    count
  }

  def reflectOnly[T](from: AnyRef)(implicit m : Manifest[T]) : Map[String, T] = {

    val props = PropFinder.getProps(from.getClass)
    props.collect({
      case (name,prop) if prop.propType == m.erasure=> {
        prop.name -> prop.getter.invoke(from).asInstanceOf[T]
      }
    }).toMap
  }
}

object ReflectionMirror extends ReflectionMirror(TemplateRegistry())
