package com.geishatokyo.tezcatlipoca.assign

import com.geishatokyo.tezcatlipoca.description.ClassDesc
import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConversions._

/**
 *
 * User: takeshita
 * Create: 12/07/01 19:19
 */

class AssignTemplateRepository(assignTemplateBuilder : AssignTemplateBuilder) {

  val repo : scala.collection.mutable.ConcurrentMap[(ClassDesc[_],ClassDesc[_]),AssignTemplate[_,_]] =
    new ConcurrentHashMap[(ClassDesc[_],ClassDesc[_]),AssignTemplate[_,_]] ()


  def apply( fromDesc : ClassDesc[_], toDesc : ClassDesc[_]) : AssignTemplate[_,_] = {
    val key = fromDesc -> toDesc
    if(repo.contains(key)){
      return repo(key)
    }
    fromDesc.manifest.erasure.synchronized{
      if(!repo.contains(key)){

        val template = assignTemplateBuilder.buildAssignTemplate(fromDesc,toDesc)
        repo +=( key -> template)

        template
      }else{
        repo(key)
      }
    }

  }

  def register(fromDesc : ClassDesc[_], toDesc : ClassDesc[_], assignTemplate : AssignTemplate[_,_]) : Unit = {
    val key = fromDesc -> toDesc
    repo +=( key -> assignTemplate)
  }

  def modify(fromDesc : ClassDesc[_], toDesc : ClassDesc[_],
             modifyTemplate : AssignTemplate[_,_] => AssignTemplate[_,_]) : Unit = {
    fromDesc.manifest.erasure.synchronized{
      val key = fromDesc -> toDesc
      val template = apply(fromDesc,toDesc)
      val modified = modifyTemplate(template)
      repo +=( key -> modified)
    }
  }

}
