package com.geishatokyo.tezcatlipoca.reflection

import assign.ReflectionAssignTemplateBuilder
import com.geishatokyo.tezcatlipoca.{ManifestMirrorWrapper, ManifestMirror, Mirror}
import com.geishatokyo.tezcatlipoca.util.{Prop, PropFinder}
import description.ScalaPlainObjectDescBuilder
import template.TemplateRegistry
import com.geishatokyo.tezcatlipoca.template.Template
import java.util.logging.Logger
import java.lang.reflect.InvocationTargetException
import com.geishatokyo.tezcatlipoca.exception.ConversionException
import com.geishatokyo.tezcatlipoca.description.ClassDescRepository
import com.geishatokyo.tezcatlipoca.assign.AssignTemplateRepository
import com.geishatokyo.tezcatlipoca.basic.BasicManifestMirror

/**
 * Mirror using reflection
 * User: takezou
 * Date: 12/06/22
 * Time: 1:58
 */

class ReflectionMirror(classDescRepository : ClassDescRepository,
                       assignTemplateRepository : AssignTemplateRepository)
  extends BasicManifestMirror(
    classDescRepository,assignTemplateRepository,false
  ) {

  val logger = Logger.getLogger("ReflectionMirror")



}

object ReflectionMirror extends ManifestMirrorWrapper(
  new ReflectionMirror(
    new ClassDescRepository(List(new ScalaPlainObjectDescBuilder())),
    new AssignTemplateRepository(new ReflectionAssignTemplateBuilder)
  )) {



}
