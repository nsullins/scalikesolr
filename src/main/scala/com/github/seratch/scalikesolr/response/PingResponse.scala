/*
 * Copyright 2011 Kazuhiro Sera
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package com.github.seratch.scalikesolr.response

import common.ResponseHeader
import parser.ResponseParser
import scala.beans.BeanProperty
import com.github.seratch.scalikesolr.request.common.WriterType
import scala.xml.XML

case class PingResponse(@BeanProperty val writerType: WriterType = WriterType.Standard,
    @BeanProperty val rawBody: String = "") {

  @BeanProperty
  lazy val responseHeader: ResponseHeader = ResponseParser.getResponseHeader(
    writerType,
    rawBody
  )

  @BeanProperty
  lazy val status: String = {
    writerType match {
      case WriterType.Standard =>
        val xml = XML.loadString(rawBody)
        (xml \ "str").filter(item => (item \ "@name").text == "status").head.text
      case other =>
        throw new UnsupportedOperationException("\"" + other.wt + "\" is currently not supported.")
    }
  }

}

