package com.geishatokyo.tezcatlipoca.assign

import com.geishatokyo.tezcatlipoca.description.ClassDesc

/**
 *
 * User: takeshita
 * Create: 12/07/01 19:10
 */

trait AssignTemplateBuilder {

  def buildAssignTemplate( from : ClassDesc[_], to : ClassDesc[_]) : AssignTemplate[_,_]

}
