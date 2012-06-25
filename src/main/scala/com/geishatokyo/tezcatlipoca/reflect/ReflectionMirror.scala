package com.geishatokyo.tezcatlipoca.reflect

import com.geishatokyo.tezcatlipoca.{Mirror}
import com.geishatokyo.tezcatlipoca.util.{Prop, PropFinder}
import template.TemplateRegistry
import com.geishatokyo.tezcatlipoca.template.Template
import java.util.logging.Logger
import java.lang.reflect.InvocationTargetException
import com.geishatokyo.tezcatlipoca.exception.ConversionException

/**
 * Mirror using reflection
 * User: takezou
 * Date: 12/06/22
 * Time: 1:58
 */

class ReflectionMirror(templateRegistry : TemplateRegistry,ignoreConversionError : Boolean = true) extends Mirror {

  val logger = Logger.getLogger("ReflectionMirror")

  private def tryConv[T](propName : String)(func : => T) :Option[T] = {
    try {
      Some(func)
    }catch{
      case e : InvocationTargetException => {
        e.getCause match{
          case ie : Exception => {
            logger.info("Fail to convert property:%s.Exception %s".format(propName,ie.getMessage))
          }
          case _ => {
            logger.info("Fail to convert property:%s.Exception %s".format(propName,e.getMessage))
          }
        }
        if (ignoreConversionError){
          None
        } else {
          throw new ConversionException(propName,e)
        }
      }
      case e : Exception => {
        logger.info("Fail to convert property:%s.Exception %s".format(propName,e.getMessage))
        if (ignoreConversionError){
          None
        } else {
          throw new ConversionException(propName,e)
        }
      }
    }
  }

  def reflect(from: AnyRef, to: AnyRef) = {
    val fromProps = PropFinder.getProps(from.getClass)
    val toProps = PropFinder.getProps(to.getClass)
    var count = 0
    fromProps.foreach({
      case (name,p) => toProps.get(name) match{
        case Some(toProp) => {
          if (toProp.propType == p.propType){
            tryConv(name){
              toProp.setter.invoke(to,p.getter.invoke(from))
              count += 1
            }
          }
        }
        case None =>
      }
    })
    count
  }

  def reflect(from: AnyRef) = {
    val props = PropFinder.getProps(from.getClass)
    props.flatMap({
      case HasTemplate(prop,template) => {
        tryConv[(String,String)](prop.name){
          prop.name -> template.toString( prop.getter.invoke(from))
        }
      }
      case _ => None
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
      case HasTemplate(prop,template) if from.contains(prop.name) => tryConv(prop.name){
        val name = prop.name
        prop.setter.invoke(to,template.fromString(from(name)).asInstanceOf[AnyRef])
        count += 1
      }
      case _ =>
    })
    count
  }

  def reflectOnly[T](from: AnyRef)(implicit m : Manifest[T]) : Map[String, T] = {

    val props = PropFinder.getProps(from.getClass)
    props.flatMap({
      case (name,prop) if prop.propType == m.erasure=> {
        tryConv[(String,T)](prop.name){
          prop.name -> prop.getter.invoke(from).asInstanceOf[T]
        }
      }
      case _ => None
    }).toMap
  }
}

object ReflectionMirror extends ReflectionMirror(TemplateRegistry.createDefault(),true)
