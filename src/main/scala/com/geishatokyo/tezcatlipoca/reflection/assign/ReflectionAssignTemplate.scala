package com.geishatokyo.tezcatlipoca.reflection.assign

import java.lang.reflect.{InvocationTargetException, Method}
import com.geishatokyo.tezcatlipoca.assign.AssignTemplate
import com.geishatokyo.tezcatlipoca.exception.ConversionException
import java.util.logging.Logger

/**
 *
 * User: takeshita
 * Create: 12/07/02 0:53
 */

class ReflectionAssignTemplate[A,B]( assignPairs : Seq[ (String,Method,Method)],
                                     ignoreConversionError : Boolean) extends AssignTemplate[A,B]{

  val logger = Logger.getLogger("ReflectionAssignTemplate")

  def assign(from: A, to: B): Int = {
    var count = 0
    assignPairs.foreach({
      case (name,fromGetter, toSetter) => {
        tryConv(name){
          toSetter.invoke(to,fromGetter.invoke(from))
          count += 1
        }
      }
    })

    count
  }

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


}
